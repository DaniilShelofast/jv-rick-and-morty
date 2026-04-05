package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService service;

    @GetMapping("/random")
    @Operation(summary = "Find a random character", description = "Find a random character")
    public Page<CharacterDto> getRandomCharacter(Pageable pageable) {
        return service.getRandomCharacter(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "Find all characters", description = "Find all characters by symbol")
    public Page<CharacterDto> findAllByNameContainsIgnoreCase(Pageable pageable,
                                                              @RequestParam String name) {
        return service.findAllByNameContainsIgnoreCase(pageable, name);
    }
}
