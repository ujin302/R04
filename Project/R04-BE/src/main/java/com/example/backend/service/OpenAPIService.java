package com.example.backend.service;
import lombok.extern.slf4j.Slf4j;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import com.example.backend.dto.*;
import com.example.backend.entity.StoreEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenAPIService {


    // 지역화폐 Open API 호출
    private GMoneyRootJsonDto getGMoneyJson(int idx, int size) {
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
            log.info("Total Count: " + count); // 414050

            // 2. 각 가맹점 데이터 추출
            // repeatIdx 만큼 API 호출 (한번에 최대 1000건까지 조회 가능)
            Long repeatIdx = count / 1000 + (count % 1000 == 0 ? 0 : 1);
            List<GMoneyJsonDto> rowJsonDtos = new LinkedList<>();

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

    // 온누리 Open API 호출
    private OnnuriRootJsonDto getOnnuriJson(int idx, int size) {
        StringBuilder result = new StringBuilder();
        try {
            String encode = "UTF-8";
            String getApiKey = "38e211bf945f102266c33a2b95c35173a42eecb23374c6f673b3c4f22fcd8ba2";
            String apiUrl = "https://api.odcloud.kr/api/3060079/v1/uddi:3f4d042d-e40d-4ce0-b0c5-97e490c47f78";

            String type = java.net.URLEncoder.encode("JSON", encode);
            String pIndex = java.net.URLEncoder.encode(String.valueOf(idx), encode);
            String pSize = java.net.URLEncoder.encode(String.valueOf(size), encode);
            String apiKey = java.net.URLEncoder.encode(getApiKey, encode);

            URL url = new URL(
                    apiUrl
                            + "?returnType=" + type
                            + "&page=" + pIndex
                            + "&perPage=" + pSize
                            + "&serviceKey=" + apiKey);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), encode));
            String line;

            // 모든 줄을 StringBuilder에 저장
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close(); // BufferedReader 닫기

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            OnnuriRootJsonDto rootJsonDto = mapper.readValue(result.toString(), OnnuriRootJsonDto.class);

            return rootJsonDto;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    // 온누리 가맹점 정보 가져오기
    public List<OnnuriDataJsonDto> getOnnuriData() {
        try {
            // 1. 가맹점 개수 추출
            int totalCount = getOnnuriJson(1,1).getTotalCount();
            log.info("Total Count: " + totalCount);

            // 2. 각 가맹점 데이터 추출
            OnnuriRootJsonDto rootJsonDto = new OnnuriRootJsonDto();
            int repeatIdx = totalCount / 10000 + (totalCount % 10000);
            List<OnnuriDataJsonDto> onnuriDataJsonDtos = new ArrayList<>();

            for (int i=0; i < repeatIdx; i++) {
                rootJsonDto = getOnnuriJson(i+1, 10000);
                onnuriDataJsonDtos.addAll(rootJsonDto.getDataJsonDtoList());
            }

            return  onnuriDataJsonDtos;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }
    
    // 주소 Open API 호출
    private JusoRootJsonDto getJusoRootJson(int idx, int size, String findKeyword) {
        StringBuilder result = new StringBuilder();
        try {
            String encode = "UTF-8";
            String getApiKey = "devU01TX0FVVEgyMDI1MTIwNzIxMzQ0NzExNjU0MTA=";
            String apiUrl = "https://www.juso.go.kr/addrlink/addrLinkApi.do";

            String type = java.net.URLEncoder.encode("JSON", encode);
            String keyword = java.net.URLEncoder.encode(findKeyword, encode);
            String pIndex = java.net.URLEncoder.encode(String.valueOf(idx), encode);
            String pSize = java.net.URLEncoder.encode(String.valueOf(size), encode);
            String apiKey = java.net.URLEncoder.encode(getApiKey, encode);

            URL url = new URL(
                    apiUrl
                            + "?resultType=" + type
                            + "&keyword=" + keyword
                            + "&currentPage=" + pIndex
                            + "&countPerPage=" + pSize
                            + "&confmKey=" + apiKey);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), encode));
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            ObjectMapper mapper = new ObjectMapper();
            JusoRootJsonDto rootJsonDto = mapper.readValue(result.toString(), JusoRootJsonDto.class);

            return rootJsonDto;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    // 좌표 Open API 호출
    public CoordinatesRootJsonDto getCoordinateRootJson(JusoDataJsonDto jusoDataJsonDto) {
        StringBuilder result = new StringBuilder();
        try {
            String encode = "UTF-8";
            String getApiKey = "U01TX0FVVEgyMDI1MTIwMTIzMjIxNDExNjUxOTY=";
            String apiUrl = "https://business.juso.go.kr/addrlink/addrCoordApi.do";

            // 추출 데이터 >> JusoDataJsonDto
            // admCd    : 행정구역코드
            // rnMgtSn  : 도로명코드
            // udrtYn   : 지하여부 (0:지상, 1:지하)
            // buldMnnm : 건물본번
            // buldSlno : 건물부번 (부번이 없는 경우 0)
            String type = java.net.URLEncoder.encode("json", encode);
            String admCd = java.net.URLEncoder.encode(jusoDataJsonDto.getAdmCd(), encode);
            String rnMgtSn = java.net.URLEncoder.encode(jusoDataJsonDto.getRnMgtSn(), encode);
            String udrtYn = java.net.URLEncoder.encode(jusoDataJsonDto.getUdrtYn(), encode);
            String buldMnnm = java.net.URLEncoder.encode(jusoDataJsonDto.getBuldMnnm(), encode);
            String buldSlno = java.net.URLEncoder.encode(jusoDataJsonDto.getBuldSlno(), encode);
            String apiKey = java.net.URLEncoder.encode(getApiKey, encode);


            URL url = new URL(
                    apiUrl
                            + "?resultType=" + type
                            + "&admCd=" + admCd
                            + "&rnMgtSn=" + rnMgtSn
                            + "&udrtYn=" + udrtYn
                            + "&buldMnnm=" + buldMnnm
                            + "&buldSlno=" + buldSlno
                            + "&confmKey=" + apiKey);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), encode));
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            ObjectMapper mapper = new ObjectMapper();
            CoordinatesRootJsonDto rootJsonDto = mapper.readValue(result.toString(), CoordinatesRootJsonDto.class);
            log.info("CoordinatesRootJsonDto > TotalCount: " + rootJsonDto.getResultsJsonDto().getCommonJsonDto().getTotalCount());

            
            return rootJsonDto;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }

    // 좌표 정보 가져오기
    public Map<String, Double> getPosition(JusoDataJsonDto jusoDataDto) {
        try {
            Map<String, Double> resultMap = new HashMap<>();

            CoordinatesJusoJsonDto coordinatesJusoJsonDto = getCoordinateRootJson(jusoDataDto).getResultsJsonDto().getJusoJsonDto().get(0);

            // 1) 좌표계 선언
            CoordinateReferenceSystem srcCRS = CRS.decode("EPSG:5179"); // 공공API: 한국에서 많이 사용하는 투영 좌표계
            CoordinateReferenceSystem dstCRS = CRS.decode("EPSG:4326"); // 지도 화면 표시: 전 세계 표준 위경도 좌표계

            // 2) 변환 객체 생성
            MathTransform transform = CRS.findMathTransform(srcCRS, dstCRS, true);

            // 변환할 좌표
            double x = Double.valueOf(coordinatesJusoJsonDto.getEntX());
            double y = Double.valueOf(coordinatesJusoJsonDto.getEntY());

            DirectPosition2D srcPos = new DirectPosition2D(srcCRS, x, y);
            DirectPosition2D dstPos = new DirectPosition2D();

            // 3) 변환 실행
            transform.transform(srcPos, dstPos);

            log.info("경도(lng) = " + dstPos.getX());
            log.info("위도(lat) = " + dstPos.getY());

            // 4) 결과 저장
            resultMap.put("lng", dstPos.getX());
            resultMap.put("lat", dstPos.getY());
            return resultMap;
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    // 주소 정보을 바탕으로 부족한 정보 채우기
    public StoreEntity getJusoRootData(StoreEntity storeEntity) {
        try {
            int count = 0;
            JusoRootJsonDto rootJsonDto = null;
            List<JusoDataJsonDto> jusoDataJsonDtolist = null;
            JusoDataJsonDto resultJusoDataDto = null; // 최종 상세 주소 정보 저장

            String jibun = storeEntity.getAddrLot().split("번지")[0].replace("번지", "");
            log.info("가공한 지번주소: " + jibun);

            // 상세 주소 Open API 호출
            // 추출방법 1순위. 도로명 주소 -> 도로명 주소 + 지번 정확하게 일치 조건 만족
            if(storeEntity.getAddrRoad() != null) {
                log.info("추출방법 1순위. 도로명 주소");
                rootJsonDto = getJusoRootJson(1, 100, storeEntity.getAddrRoad());
                count = rootJsonDto.getJusoResultJsonDto().getJusoCommonJsonDto().getTotalCount();

                if(count > 0) {
                    jusoDataJsonDtolist = rootJsonDto.getJusoResultJsonDto().getJusoDataJsonDto(); // 주소 상세 정보 리스트 저장
                    log.info("Total Count: " + count + " / 검색 주소: " + jibun);

                    // 도로명 주소 + 지번 정확하게 일치 조건 만족
                    for(JusoDataJsonDto dto : jusoDataJsonDtolist) {
                        if(dto.getJibunAddr().equals(jibun) && dto.getRoadAddrPart2().equals(storeEntity.getAddrRoad())) {
                            resultJusoDataDto = dto;

                            break;
                        }
                    }
                } else {
                    log.info("Total Count: 0");
                }
            }

            // 추출방법 2순위. 지번 주소 -> 지번이랑 정확하게 일치 조건 만족
            if(resultJusoDataDto == null && storeEntity.getAddrLot() != null){
                log.info("추출방법 2순위. 지번 주소");
                rootJsonDto = getJusoRootJson(1, 100, jibun);
                count = rootJsonDto.getJusoResultJsonDto().getJusoCommonJsonDto().getTotalCount();

                if(count > 0) {
                    jusoDataJsonDtolist = rootJsonDto.getJusoResultJsonDto().getJusoDataJsonDto(); // 주소 상세 정보 리스트 저장
                    log.info("Total Count: " + count + " / 검색 주소: " + storeEntity.getAddrLot());

                    // 도로명 주소 + 지번 정확하게 일치 조건 만족
                    for(JusoDataJsonDto dto : jusoDataJsonDtolist) {
                        if(dto.getJibunAddr().equals(jibun)) {
                            resultJusoDataDto = dto;

                            break;
                        }
                    }
                } else {
                    log.info("Total Count: 0");
                }
            }

            // 추출방법 3순위. 만약 없을 경우, 예외 발생
            if (resultJusoDataDto == null) {
                throw new Exception("데이터 없음");
            }

            // 최종 JusoDataJsonDto를 통해, 경도 & 위도 구하는 함수
            Map<String, Double> resultMap = getPosition(resultJusoDataDto);
            storeEntity.setLng(resultMap.get("lng")); // 경도
            storeEntity.setLat(resultMap.get("lat")); // 위도
            if(storeEntity.getAddrRoad() == null && resultJusoDataDto.getRoadAddrPart1() != null) {
                storeEntity.setAddrRoad(resultJusoDataDto.getRoadAddrPart1());
            }
            if(storeEntity.getAddrLot() == null && resultJusoDataDto.getJibunAddr() != null) {
                storeEntity.setAddrLot(resultJusoDataDto.getJibunAddr());
            }
            if(storeEntity.getZipcode() == null && resultJusoDataDto.getZipNo() != null) {
                storeEntity.setZipcode(resultJusoDataDto.getZipNo());
            }
            if(storeEntity.getSido() == null && resultJusoDataDto.getSiNm() != null) {
                storeEntity.setSido(resultJusoDataDto.getSiNm());
            }
            if(storeEntity.getSigungu() == null && resultJusoDataDto.getSggNm() != null) {
                storeEntity.setSigungu(resultJusoDataDto.getSggNm());
            }
            if(storeEntity.getEupmyeon() == null && resultJusoDataDto.getEmdNm() != null) {
                storeEntity.setSigungu(resultJusoDataDto.getSggNm());
            }

            log.info(storeEntity.toString());
            return storeEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            return storeEntity;
        }

    }

}
