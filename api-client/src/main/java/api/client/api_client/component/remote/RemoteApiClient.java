package api.client.api_client.component.remote;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RemoteApiClient {

        private Logger LOGGER = LoggerFactory.getLogger(RemoteApiClient.class);

        private RestTemplate apiClient;
        private boolean isDummy;

        public RemoteApiClient(
                        String baseUri,
                        long connectionTimeout,
                        long readTimeout,
                        long backOffInterval,
                        long backOffMaxAttempts,
                        boolean isDummy) {
                this.isDummy = isDummy;
                apiClient = new RestTemplateBuilder()
                                .rootUri(baseUri)
                                .setConnectTimeout(Duration.ofMillis(backOffMaxAttempts))
                                .setReadTimeout(Duration.ofMillis(readTimeout))
                                .interceptors(new RetryableClientHttpRequestInterceptorImpl(
                                                backOffInterval,
                                                backOffMaxAttempts))
                                // .errorHandler(null)
                                // TODO: DefaultResponseHandlerを継承するクラスを作成。
                                // エラー発生時にログを出力しておく。
                                .build();
        }

        public <T> ResponseEntity<T> get(String path, Class<T> responseType, Map<String, Object> urlVariableMap) {
                LOGGER.info("" + isDummy);
                if (isDummy) {
                        LOGGER.info("DummyAPIとして作動します。");
                        try {
                                T emptyInstance = responseType.getDeclaredConstructor().newInstance();
                                return ResponseEntity.ok(emptyInstance);
                        } catch (Exception ex) {
                                throw new RuntimeException(ex);
                        }
                }
                return this.apiClient.getForEntity(path, responseType, urlVariableMap);
        }

}
