package com.fch.buffetorder.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: BuffetOrder
 * @description: 请求工具
 * @CreatedBy: fch
 * @create: 2023-02-17 13:19
 **/
@Component
@RequiredArgsConstructor
public class RequestUtil {

    private static final String BUCKET_NAME = "buffet-order";

    private static final String URL = "http://192.168.23.128:8080/file/upload";

    private final RestTemplate restTemplate;

    public String uploadImg(MultipartFile file, String name, String path){
        try {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()){
                @Override
                public String getFilename() {
                    return name;
                }
            };
            params.add("file", resource);
            params.add("bucketName", BUCKET_NAME);
            params.add("path", path);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(params, headers);
            return restTemplate.postForObject(URL, entity, String.class);
        } catch (IOException | RestClientException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
}
