package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JusoResultJsonDto {

    @JsonProperty("common")
    private JusoCommonJsonDto jusoCommonJsonDto;

    @JsonProperty("juso")
    private List<JusoDataJsonDto> jusoDataJsonDto;
}
