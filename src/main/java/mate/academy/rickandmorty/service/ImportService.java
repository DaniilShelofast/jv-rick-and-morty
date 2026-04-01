package mate.academy.rickandmorty.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterResultDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImportService {
    private final CharacterRepository repository;
    private final ExternalApiService apiService;
    private final CharacterMapper mapper;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onApplicationReady() {
        List<CharacterResultDto> data = apiService.fetchAllPages();
        List<Character> characters = data.stream()
                .map(mapper::toEntity)
                .toList();
        repository.saveAll(characters);
    }
}
