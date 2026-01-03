package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JusoRootJsonDto {

    @JsonProperty("results")
    private JusoResultJsonDto jusoResultJsonDto;
}
