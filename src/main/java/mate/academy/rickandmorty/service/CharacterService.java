package mate.academy.rickandmorty.service;

import mate.academy.rickandmorty.dto.internal.CharacterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CharacterService {
    CharacterDto getRandomCharacter();

    Page<CharacterDto> findAllByNameContainsIgnoreCase(Pageable pageable, String name);
}
