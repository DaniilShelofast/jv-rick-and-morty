package mate.academy.rickandmorty.dto.external;

import lombok.Data;

@Data
public class CharacterResultDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
