package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.dto.external.CharacterResultDto;
import mate.academy.rickandmorty.exception.FetchingInterruptedException;
import mate.academy.rickandmorty.exception.InvalidResponseDataException;
import mate.academy.rickandmorty.model.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExternalApiService {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final int SLEEP = 500;
    @Value("${service.url}")
    private String url;
    private final ObjectMapper objectMapper;

    public CharacterResponseDataDto fetchPage(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .timeout(REQUEST_TIMEOUT)
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpStatus.OK.getCode()) {
                throw new RuntimeException("Failed request, status: " + response.statusCode());
            }
            return objectMapper.readValue(response.body(), CharacterResponseDataDto.class);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while fetching page", e);
        }
    }

    public List<CharacterResultDto> fetchAllPages() {
        final List<CharacterResultDto> result = new ArrayList<>();
        String url = this.url;
        while (url != null) {
            CharacterResponseDataDto page = fetchPage(url);
            if (page == null || page.getResults() == null) {
                throw new InvalidResponseDataException("error occurred with data storage");
            }
            result.addAll(page.getResults());
            url = page.getInfo() != null ? page.getInfo().getNext() : null;
            if (url != null) {
                try {
                    Thread.sleep(SLEEP);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new FetchingInterruptedException("Interrupted during fetching", e);
                }
            }
        }
        return result;
    }
}
