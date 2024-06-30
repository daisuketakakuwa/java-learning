package api.client.api_client.repository.remote.fuga.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FetchFugaDataResponse {
    private List<Event> events;
}

@Getter
@Setter
@NoArgsConstructor
class Event {
    private Long id;
    private String name;
    private String date;
}
