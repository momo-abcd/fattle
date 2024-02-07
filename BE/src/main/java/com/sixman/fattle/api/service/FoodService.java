package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.response.FoodInfoResponse;
import com.sixman.fattle.dto.response.TodaysFoodResponse;
import com.sixman.fattle.entity.Food;
import com.sixman.fattle.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public TodaysFoodResponse todaysFood(long userCode) {
        List<Food> foodList = foodRepository.todaysFood(userCode);

        return TodaysFoodResponse.builder()
                .list(foodList)
                .build();
    }

    public String saveImage(long userCode, int foodCode, MultipartFile file){

        if(file == null){
            return null;
        }

        if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            System.out.println("경고! 업로드된 파일이 이미지 타입이 아닙니다.");
            return null;
        }

        String uploadPath = "/home/ubuntu/resources/food_image/"+userCode;
        String orginalName = file.getOriginalFilename();
//        assert orginalName != null;
        String fileName = orginalName.substring(orginalName.lastIndexOf("/") + 1);

        System.out.println("fileName: "+fileName);

        // 날짜 폴더 생성
        String folderPath = makeFolder(uploadPath, foodCode);


        // UUID
        String uuid = UUID.randomUUID().toString();

        // 저장할 파일 이름 중간에 "_"를 이용해서 구현
        String saveName = uploadPath + "/" + folderPath + "/"
                + uuid + "_" + fileName;

        System.out.println(saveName);

        Path savePath = Paths.get(saveName);

        try {
            file.transferTo(savePath); // 실제 이미지 저장
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadPath + "/" + folderPath;
    }

    /*날짜 폴더 생성*/
    private String makeFolder(String uploadPath, int foodCode) {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str + "/" + String.valueOf(foodCode);

        // make folder --------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(!uploadPathFolder.exists()) {
            boolean mkdirs = uploadPathFolder.mkdirs();
            System.out.println("make directory : "+uploadPath);
        }

        return folderPath;

    }
    public FoodInfoResponse getFoodInfo(String folderPath) {
        final String uri = "http://i10e106.p.ssafy.io:5000/food_detect/";

        Map<String, String> body = new HashMap<>();
        body.put("source", folderPath);

        return WebClient.create()
                .post()
                .uri(uri)
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(FoodInfoResponse.class)
                .block();

//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("source", folderPath);
//        HttpEntity<String> request =
//                new HttpEntity<String>(requestBody.toString(), headers);
//        JSONObject result = restTemplate.postForObject(uri, request, JSONObject.class);
//
//        System.out.println("food Info : " + result);
//        return result;
    }

}
