package com.selcukc.kubernetes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Greeting service controller.
 */
@RestController
public class GreetingController {

	private final NameService nameService;

	public GreetingController(NameService nameService) {
		this.nameService = nameService;
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * Endpoint to get a greeting. This endpoint uses a name server to get a name for the
	 * greeting.
	 *
	 * Request to the name service is guarded with a circuit breaker. Therefore if a name
	 * service is not available or is too slow to response fallback name is used.
	 *
	 * Delay parameter can be used to make name service response slower.
	 * @param delay Milliseconds for how long the response from name service should be
	 * delayed.
	 * @return Greeting string.
	 */
	@RequestMapping("/greeting")
	public String getGreeting(
			@RequestParam(value = "delay", defaultValue = "0") int delay) {
		return String.format("Hello from %s!", this.nameService.getName(delay));
	}

	@GetMapping("/")
	public String load() {

//		RestTemplate restTemplate = new RestTemplate();

		String serviceList = "";
		if (discoveryClient != null) {
			List<String> services = this.discoveryClient.getServices();

			for (String service : services) {

				List<ServiceInstance> instances = this.discoveryClient.getInstances(service);

				serviceList += ("[" + service + " : " + ((!CollectionUtils.isEmpty(instances)) ? instances.size() : 0) + " instances ]");
			}
		}

		return serviceList;
//		return "Up";
	}

}
