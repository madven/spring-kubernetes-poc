package com.selcukc.kubernetes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Name service controller.
 */
@RestController
@Slf4j
public class NameController {

	private final String hostName = System.getenv("HOSTNAME");

	@RequestMapping("/")
	public String ribbonPing() {
		log.info("Ribbon ping");
		return this.hostName;
	}

	/**
	 * Endpoint to get a name with a capability to delay a response for some number of
	 * milliseconds.
	 * @param delayValue Milliseconds for how long the response should be delayed.
	 * @return Host name.
	 */
	@RequestMapping("/name")
	public String getName(@RequestParam(value = "delay", defaultValue = "0") int delayValue) {
		log.info(String.format("Returning a name '%s' with a delay '%d'", this.hostName, delayValue));
		delay(delayValue);
		return this.hostName;
	}

	private void delay(int delayValue) {
		try {
			Thread.sleep(delayValue);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
