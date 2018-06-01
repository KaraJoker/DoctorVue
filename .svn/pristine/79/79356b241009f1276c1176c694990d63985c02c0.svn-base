package com.chero.client.utils;

import com.chero.client.domain.DoctorInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServerUtil {

    public static String userIdToMobile(String token, String userId, String mobile) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "bearer " + token);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userIds", userId);
        if(mobile != null){
            map.add("mobile", mobile);
        }
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        String restful_api_findUser = ServerURLConstant.RESTFUL_API_UsernameLike;
        ResponseEntity<Object> data = restTemplate.exchange(restful_api_findUser, HttpMethod.POST, httpEntity, Object.class);
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data.getBody();
        String mobile1 = null;
//        doctorInfo.setMobile(((Map<String, String>) responseMap.get("content")).get("mobile"));
        if (responseMap.containsKey("content")){
            List<LinkedHashMap<String, String>> list = (java.util.List<LinkedHashMap<String, String>>) responseMap.get("content");
            if (list != null && list.size() > 0){
                LinkedHashMap<String, String> map1 = list.get(0);
                if(map1.containsKey("mobile")){
                    mobile1 = map1.get("mobile");
                }
            }
        }
        return mobile1;
    }

    public static List<String> userIdsToMobile(String token, List<String> userId, String mobile) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "bearer " + token);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("userIds", StringUtils.join(userId, ","));
        if(mobile != null){
            map.add("mobile", mobile);
        }
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        String restful_api_findUser = ServerURLConstant.RESTFUL_API_UsernameLike;
//        String restful_api_findUser = "http://192.168.30.59:8003/users/findUserByIdInAndUsernameLike";
        ResponseEntity<Object> data = restTemplate.exchange(restful_api_findUser, HttpMethod.POST, httpEntity, Object.class);
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data.getBody();
        List<String> mobile1 = new ArrayList<>();
//        doctorInfo.setMobile(((Map<String, String>) responseMap.get("content")).get("mobile"));
        if (responseMap.containsKey("content")){
            List<LinkedHashMap<String, String>> list = (java.util.List<LinkedHashMap<String, String>>) responseMap.get("content");
            if (list != null && list.size() > 0){
                for (int i = 0; i < list.size(); i++){
                    LinkedHashMap<String, String> map1 = list.get(i);
                    if(map1.containsKey("userId")){
                        mobile1.add(map1.get("userId"));
                    }
                }
            }
        }
        return mobile1;
    }

    public static List<String> findUserIdByMobile(String token, List<String> userId, String mobile) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "bearer " + token);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("userIds", userId);
        if(mobile != null){
            map.add("mobile", mobile);
        }
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        String restful_api_findUser = ServerURLConstant.RESTFUL_API_UsernameLike;
        ResponseEntity<Object> data = restTemplate.exchange(restful_api_findUser, HttpMethod.POST, httpEntity, Object.class);
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data.getBody();
        List<String> mobile1 = new ArrayList<>();
//        doctorInfo.setMobile(((Map<String, String>) responseMap.get("content")).get("mobile"));
        if (responseMap.containsKey("content")){
            List<LinkedHashMap<String, String>> list = (java.util.List<LinkedHashMap<String, String>>) responseMap.get("content");
            if (list != null && list.size() > 0){
                for (LinkedHashMap<String, String> map1 : list){
                    if(map1.containsKey("mobile")){
                        mobile1.add(map1.get("mobile"));
                    }
                }

            }
        }
        return mobile1;
    }

    public static String mobileToUserId(String token, String mobile) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "bearer " + token);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);
        String restful_api_findUser = ServerURLConstant.RESTFUL_API_FINDBYMOBILE +"?mobile=" + mobile;
        ResponseEntity<Object> data = restTemplate.exchange(restful_api_findUser, HttpMethod.GET, httpEntity, Object.class);
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data.getBody();
        if (responseMap.containsKey("content") ){
            if (((Map<String, String>) responseMap.get("content")) != null && ((Map<String, String>) responseMap.get("content")).containsKey("userId")){
                String userId = ((Map<String, String>) responseMap.get("content")).get("userId");
                return userId;
            }else {
                return null;
            }
        }else {
            return null;
        }



    }
}
