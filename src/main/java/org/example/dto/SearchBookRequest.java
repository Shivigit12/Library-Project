package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchBookRequest {
    @NotBlank
    private String searchKey;
    @NotBlank
    private String searchValue;
    @NotBlank
    private String operator;

    private static final Set<String> ALLOWED_KEYS = Set.of("bookName", "authorName", "genre", "pages", "id");
    private static final Map<String, Set<String>> ALLOWED_OPERATOR_MAP = Map.of(
            "bookName", Set.of("=", "like"),
            "authorName", Set.of("="),
            "pages", Set.of("=", "<=", ">=", ">", "<"),
            "genre", Set.of("="),
            "id", Set.of("=")
    );

    public boolean validate() {
        if(!ALLOWED_KEYS.contains(searchKey))
            return false;
        Set<String> validOperators = ALLOWED_OPERATOR_MAP.get(this.searchKey);
        if(validOperators == null || !validOperators.contains(this.operator))
            return false;
        return true;
    }
}
