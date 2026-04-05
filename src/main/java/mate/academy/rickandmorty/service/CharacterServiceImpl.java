package mate.academy.rickandmorty.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.exception.EntityNotFoundException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private final CharacterRepository repository;
    private final CharacterMapper mapper;

    @Override
    public CharacterDto getRandomCharacter() {
        long count = repository.count();
        if (count == ZERO) {
            throw new EntityNotFoundException("database empty");
        }
        int random = new Random().nextInt(ZERO, (int) count);
        Page<Character> page = repository.findAll(PageRequest.of(random, ONE));
        return mapper.toDto(page.getContent().get(ZERO));
    }

    @Override
    public Page<CharacterDto> findAllByNameContainsIgnoreCase(Pageable pageable, String name) {
        return repository.findAllByNameContainsIgnoreCase(pageable, name)
                .map(mapper::toDto);
    }
}
