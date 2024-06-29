package api.client.api_client.repository.remote;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class RemoteApiClient {

    private static RestTemplate apiClient;

    public RemoteApiClient() {
        apiClient = new RestTemplateBuilder()
                .rootUri("baseUrl") // TODO: プロパティ参照
                .interceptors(
                        new RetryableClientHttpRequestInterceptorImpl(
                                1000, // TODO: プロパティ参照
                                2 // TODO: プロパティ参照
                        ))
                .setConnectTimeout(Duration.ofMillis(1000)) // TODO: プロパティ参照
                .setReadTimeout(Duration.ofMillis(1000)) // TODO: プロパティ参照
                .errorHandler(null)
                // TODO: DefaultResponseHandlerを継承するクラスを作成。
                // エラー発生時にログを出力しておく。
                .build();
    }

    // 1. application.ymlにRemoteAPIの各プロパティを定義する

    // 2. RemoteApiClientConfig.javaを作成

    // 3. RemoteApiClientクラスを作る（汎用的なもの）

    // 4. dummyの切替をできるようにする

}
