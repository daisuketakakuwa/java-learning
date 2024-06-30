package api.client.api_client.repository.remote.fuga;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import api.client.api_client.component.remote.RemoteApiClient;
import api.client.api_client.configuration.FugaApiConfiguration;
import api.client.api_client.repository.remote.fuga.response.FetchFugaDataResponse;

@Repository
public class FugaRepository {

    private RemoteApiClient fugaApiClient;

    @Autowired
    public FugaRepository(FugaApiConfiguration fugaApiConfig) {
        this.fugaApiClient = new RemoteApiClient(
                fugaApiConfig.getBaseUri(),
                fugaApiConfig.getConnectionTimeout(),
                fugaApiConfig.getReadTimeout(),
                fugaApiConfig.getBackOffInterval(),
                fugaApiConfig.getBackOffMaxAttempts(),
                fugaApiConfig.getIsDummy());
    }

    public FetchFugaDataResponse getFugaData() {
        ResponseEntity<FetchFugaDataResponse> response = fugaApiClient.get(
                "/events",
                FetchFugaDataResponse.class,
                new HashMap<>());

        return response.getBody();
    }

}
