package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterResultDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CharacterService {
    List<CharacterDto> saveAll(List<CharacterResultDto> resultsDto);

    Page<CharacterDto> getRandomCharacter(Pageable pageable);

    Page<CharacterDto> findAllByNameContainsIgnoreCase(Pageable pageable, String name);
}
