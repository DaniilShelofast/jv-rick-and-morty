package mate.academy.rickandmorty.dto.external;

import lombok.Data;

@Data
public class CharacterInfoDto {
    private Integer count;
    private Integer pages;
    private String next;
    private String prev;
}
