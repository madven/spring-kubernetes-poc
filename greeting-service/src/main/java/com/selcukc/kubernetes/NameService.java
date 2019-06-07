package com.selcukc.kubernetes;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service invoking name-service via REST and guarded by Hystrix.
 */
@Service
@Slf4j
public class NameService {

	@Autowired
	NameClient nameClient;

//	@Autowired
//	RestTemplate restTemplate;

//	@HystrixCommand(fallbackMethod = "getFallbackName", commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
//	public String getName(int delay) {
//		return this.restTemplate.getForObject(
//				String.format("http://name-service/name?delay=%d", delay), String.class);
//	}

	@HystrixCommand(fallbackMethod = "fallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
	public String getName(int delay) {
		log.info("from name service");
		return nameClient.getName(delay);
	}

	private String fallback(int delay) {
		return "Fallback";
	}

}
