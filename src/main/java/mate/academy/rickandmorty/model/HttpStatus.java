package mate.academy.rickandmorty.model;

import lombok.Getter;

@Getter
public enum HttpStatus {
    OK(200, "OK");

    private final int code;
    private final String description;

    HttpStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
