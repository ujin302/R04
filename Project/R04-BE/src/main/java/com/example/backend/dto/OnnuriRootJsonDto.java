package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OnnuriRootJsonDto {
    @JsonProperty("currentCount")
    private int currentCount;

    @JsonProperty("data")
    private List<OnnuriDataJsonDto> dataJsonDtoList;

    @JsonProperty("matchCount")
    private Long matchCount;

    @JsonProperty("page")
    private int page;

    @JsonProperty("perPage")
    private int perPage;

    @JsonProperty("totalCount")
    private int totalCount;
}
