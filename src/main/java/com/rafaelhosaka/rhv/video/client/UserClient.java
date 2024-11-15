package com.rafaelhosaka.rhv.video.client;

import com.rafaelhosaka.rhv.video.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user", url = "http://localhost:8082")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET,value = "/api/user/pb/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable("id") Integer id);
}
