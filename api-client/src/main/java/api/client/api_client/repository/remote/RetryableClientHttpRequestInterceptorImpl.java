package api.client.api_client.repository.remote;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.web.client.ResourceAccessException;

public class RetryableClientHttpRequestInterceptorImpl implements ClientHttpRequestInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(RetryableClientHttpRequestInterceptorImpl.class);
    private List<Integer> RETRYABLE_ERROR_RESPONSE_STATUSES = new ArrayList<>(Arrays.asList(
            408, // Request Timeout
            502, // Bad Gateway
            503, // Service Unavailable
            504 // Gateway Timeout
    ));

    private BackOff backOff;

    public RetryableClientHttpRequestInterceptorImpl(
            long backOffInterval,
            long backOffMaxAttempts) {
        this.backOff = new FixedBackOff(backOffInterval, backOffMaxAttempts);
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) {

        // 1. nextBackOff取得
        // 2. API実行1回目
        // 3. リトライ可能であればリトライする (1の結果に依存する)
        // 4. nextBackOff取得
        // 5. API実行2回目
        // 6. リトライ可能であればリトライする(4の結果に依存する)

        BackOffExecution backOffexecution = backOff.start();
        while (true) {
            // リトライOKの場合、待機時間(ミリ秒)
            // リトライNGの場合、BackOffExecution.STOP
            long nextBackOff = backOffexecution.nextBackOff();

            try {
                // APIコール
                ClientHttpResponse response = execution.execute(request, body);
                // OK/NGを含めて、リトライするか終了するかを判定する
                boolean isOK = !response.getStatusCode().isError();
                boolean isRetryableResponseStatus = RETRYABLE_ERROR_RESPONSE_STATUSES
                        .contains(response.getStatusCode().value());
                if (isOK || !isRetryableResponseStatus) {
                    return response;
                }
                // ここまで通過したらリトライ可否確認へ移動する。
            } catch (Exception ex) {
                boolean isRetryableIOException = ex instanceof ResourceAccessException;
                boolean isRetryableTimeoutException = ex instanceof SocketTimeoutException;
                if (!isRetryableIOException || !isRetryableTimeoutException) {
                    throw new RuntimeException("リトライ不可能なシステムエラーが発生しました。", ex);
                }
                // ここまで通過したらリトライ可否確認へ移動する。
            } finally {
                // レスポンスを返却しなかった場合にここにたどり着く。
                // リトライ可能であれば 後続の待機処理を経て次ループへ
                if (nextBackOff != BackOffExecution.STOP) {
                    throw new RuntimeException("リトライ上限回数を超過したため処理を中断します。");
                }
            }

            // 待機処理
            LOGGER.info("Wait interval ({})", nextBackOff);
            try {
                Thread.sleep(nextBackOff);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
