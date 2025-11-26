package com.example.backend.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.example.backend.dto.GMoneyHeadJsonDto;
import com.example.backend.dto.GMoneyJsonDto;
import com.example.backend.dto.GMoneyRegionJsonDto;
import com.example.backend.dto.GMoneyRootJsonDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAPIService {

    public GMoneyRootJsonDto getGMoneyJson(int idx, int size) {
        StringBuilder result = new StringBuilder();
        try {
            String encode = "UTF-8";
            String getApiKey = "f31e992fda78415c9f4d75248fd47c8c";
            String apiUrl = "https://openapi.gg.go.kr/RegionMnyFacltStus";

            String type = java.net.URLEncoder.encode("json", encode);
            String pIndex = java.net.URLEncoder.encode(String.valueOf(idx), encode);
            String pSize = java.net.URLEncoder.encode(String.valueOf(size), encode);
            String apiKey = java.net.URLEncoder.encode(getApiKey, encode);

            URL url = new URL(
                    apiUrl
                            + "?&type=" + type
                            + "&pIndex=" + pIndex
                            + "&pSize=" + pSize
                            + "&KEY=" + apiKey);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), encode));
            String line;

            // 모든 줄을 StringBuilder에 저장
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close(); // BufferedReader 닫기

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            GMoneyRootJsonDto rootJsonDto = mapper.readValue(result.toString(), GMoneyRootJsonDto.class);

            // OpenAPI JSON 형태와 동일하게 변경
            List<GMoneyRegionJsonDto> regionJsonDtos = rootJsonDto.getRegionJsonDtos();
            GMoneyRegionJsonDto regionJsonDto = regionJsonDtos.get(0);
            regionJsonDto.setRow(regionJsonDtos.get(1).getRow());
            List<GMoneyHeadJsonDto> headJsonDtos = regionJsonDto.getHeadJsonDto();

            headJsonDtos.get(0).setReultJsonDto(headJsonDtos.get(1).getReultJsonDto());
            headJsonDtos.get(0).setVersion(headJsonDtos.get(2).getVersion());

            return rootJsonDto;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    // 지역화폐 가맹점 정보 가져오기
    public List<GMoneyJsonDto> getGmoneyData() {
        try {
            // 1. 가맹점 개수 추출
            GMoneyRootJsonDto rootJsonDto = null;
            rootJsonDto = getGMoneyJson(1, 1);
            Long count = rootJsonDto.getRegionJsonDtos().get(0).getHeadJsonDto().get(0).getCount();
            System.out.println("Total Count: " + count); // 414050

            // 2. 각 가맹점 데이터 추출
            // repeatIdx 만큼 API 호출 (한번에 최대 1000건까지 조회 가능)
            Long repeatIdx = count / 1000 + (count % 1000 == 0 ? 0 : 1);
            List<GMoneyJsonDto> rowJsonDtos = new LinkedList<GMoneyJsonDto>();

            for (int i = 0; i < repeatIdx; i++) {
                rootJsonDto = getGMoneyJson(i + 1, 1000);
                rowJsonDtos.addAll(rootJsonDto.getRegionJsonDtos().get(0).getRow());
            }

            return rowJsonDtos;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    // 온누리상품권 가맹점 정보 가져오기
}
