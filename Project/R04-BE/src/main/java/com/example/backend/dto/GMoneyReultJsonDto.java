package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GMoneyReultJsonDto {

    @JsonProperty("CODE")
    private String code;

    @JsonProperty("MESSAGE")
    private String msg;

}
