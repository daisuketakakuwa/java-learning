package api.client.api_client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;

@Configuration
@Setter
public class FugaApiConfiguration {

    @Value("${fuga.api.baseUri}")
    private String baseUri;

    @Value("${fuga.api.connectionTimeout}")
    private String connectionTimeout;

    @Value("${fuga.api.readTimeout}")
    private String readTimeout;

    @Value("${fuga.api.backOffInterval}")
    private String backOffInterval;

    @Value("${fuga.api.backOffMaxAttempts}")
    private String backOffMaxAttempts;

    @Value("${fuga.api.isDummy}")
    private String isDummy;

    public String getBaseUri() {
        return this.baseUri;
    }

    public long getConnectionTimeout() {
        return Long.parseLong(connectionTimeout);
    }

    public long getReadTimeout() {
        return Long.parseLong(readTimeout);
    }

    public long getBackOffInterval() {
        return Long.parseLong(backOffInterval);
    }

    public long getBackOffMaxAttempts() {
        return Long.parseLong(backOffMaxAttempts);
    }

    public boolean getIsDummy() {
        return Boolean.parseBoolean(isDummy);
    }

}
