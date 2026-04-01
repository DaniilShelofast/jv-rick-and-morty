package mate.academy.rickandmorty.mapper;

import java.util.List;
import mate.academy.rickandmorty.config.MapperConfig;
import mate.academy.rickandmorty.dto.external.CharacterResultDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CharacterMapper {
    CharacterDto toDto(Character character);

    @Mapping(source = "id", target = "externalId")
    Character toEntity(CharacterResultDto resultDto);

    List<Character> toEntities(List<CharacterResultDto> list);

    List<CharacterDto> toDtos(List<Character> characters);
}
