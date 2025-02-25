package com.example.buddy_match.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * This is a generated BaseService for demonstration purposes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPageImpl<T>  extends PageImpl<T> {
    public CustomPageImpl() {
        super(new ArrayList<>());
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CustomPageImpl(@JsonProperty("content") List<T> content, @JsonProperty("number") Integer number, @JsonProperty("size") Integer size, @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") Boolean last, @JsonProperty("totalPages") Integer totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("numberOfElements") Integer numberOfElements) {
        super(content, PageRequest.of(number, 1), 10);
    }

    public CustomPageImpl(List<T> content) {
        super(content);
    }

    public CustomPageImpl(List<T> content, Pageable pageable, Long totalElements) {
        super(content, pageable, totalElements);
    }
}