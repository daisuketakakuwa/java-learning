package api.client.api_client.service;

import org.springframework.stereotype.Service;

import api.client.api_client.repository.remote.fuga.FugaRepository;
import api.client.api_client.repository.remote.fuga.response.FetchFugaDataResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeneralService {

    private final FugaRepository fugaRepository;

    public FetchFugaDataResponse fetchFuga() {
        // fugaAPIを呼び出す
        FetchFugaDataResponse fugaRes = fugaRepository.getFugaData();
        return fugaRes;
    }

}
