package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchBookRequest {
    @NotBlank
    private String searchKey;
    @NotBlank
    private String searchValue;
    @NotBlank
    private String operator;
    private static Set<String> allowedKeys = new HashSet<>();
    private static HashMap<String, List<String>> allowedOperatorMap = new HashMap<>();
    SearchBookRequest() {
        allowedKeys.addAll(Arrays.asList("bookName", "authorName", "genre", "pages", "id"));
        allowedOperatorMap.put("bookName", Arrays.asList("=", "like"));
        allowedOperatorMap.put("authorName", Arrays.asList("="));
        allowedOperatorMap.put("pages", Arrays.asList("=", "<=", ">=", ">", "<"));
        allowedOperatorMap.put("genre", Arrays.asList("="));
        allowedOperatorMap.put("id", Arrays.asList("="));
    }
    public boolean validate() {
        if(!allowedKeys.contains(searchKey))
            return false;
        List<String> validOperators = allowedOperatorMap.get(this.searchKey);
        if(!validOperators.contains(this.operator))
            return false;
        return true;
    }
}
