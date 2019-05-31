package com.selcukc.kubernetes;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service invoking name-service via REST and guarded by Hystrix.
 */
@Service
public class NameService {

//	@Autowired
//	NameClient nameClient;

	private final RestTemplate restTemplate;

	public NameService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@HystrixCommand(fallbackMethod = "getFallbackName", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
	public String getName(int delay) {
		return this.restTemplate.getForObject(
				String.format("http://name-service/name?delay=%d", delay), String.class);
	}

//	public String getName(int delay) {
//		return nameClient.getName(delay);
//	}

	private String getFallbackName(int delay) {
		return "Fallback";
	}

}
