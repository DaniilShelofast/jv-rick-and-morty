package mate.academy.rickandmorty.service;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterResultDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.exception.EntityNotFoundException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository repository;
    private final CharacterMapper mapper;

    @Transactional
    @Override
    public List<CharacterDto> saveAll(List<CharacterResultDto> resultsDto) {
        List<Character> characters = resultsDto.stream()
                .map(mapper::toEntity)
                .toList();
        repository.saveAll(characters);
        return mapper.toDtos(characters);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CharacterDto> getRandomCharacter(Pageable pageable) {
        long count = repository.count();
        if (count == 0) {
            throw new EntityNotFoundException("database empty");
        }
        int random = new Random().nextInt(0, (int) count - 1);
        return repository.findAll(PageRequest.of(random, 1)).map(mapper::toDto);
    }

    @Override
    public Page<CharacterDto> findAllByNameContainsIgnoreCase(Pageable pageable, String name) {
        return repository.findAllByNameContainsIgnoreCase(pageable, name)
                .map(mapper::toDto);
    }
}
