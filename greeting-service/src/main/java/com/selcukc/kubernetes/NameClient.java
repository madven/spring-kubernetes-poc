package com.selcukc.kubernetes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("name-service")
public interface NameClient {

    @GetMapping("/name")
    @CrossOrigin
    String getName(@RequestParam(value = "delay") int delay);
}
