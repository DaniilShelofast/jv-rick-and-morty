package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.dto.external.CharacterResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExternalApiService {
    @Value("${service.url}")
    private String url;
    private final ObjectMapper objectMapper;

    public CharacterResponseDataDto fetchPage(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
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
                throw new RuntimeException("error occurred with data storage");
            }
            result.addAll(page.getResults());
            url = page.getInfo() != null ? page.getInfo().getNext() : null;
            if (url != null) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during fetching", e);
                }
            }
        }
        return result;
    }
}
