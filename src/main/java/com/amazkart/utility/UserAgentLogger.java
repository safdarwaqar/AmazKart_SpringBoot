package com.amazkart.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua_parser.Client;
import ua_parser.Parser;

public class UserAgentLogger {

	private static final Logger logger = LoggerFactory.getLogger(UserAgentLogger.class);

	public static void logUserAgent(String userAgent) {
		
		if(userAgent == null || userAgent.isEmpty()) {
            logger.error("User-Agent string is null");
            return;
		}
		
		// Parse User-Agent for browser details
		String browserName = "Unknown";
		String browserVersion = "Unknown";
		String os = "Unknown";
		String device = "Unknown";
		String userAgentString = userAgent;
		try {
			Parser uaParser = new Parser();
			Client client = uaParser.parse(userAgentString);

			browserName = client.userAgent.family.contains("Postman") ? "Postman API Testing Tool"
					: client.userAgent.family;

			browserVersion = client.userAgent.major;

			os = client.os.family;

			device = client.device.family;

		} catch (Exception e) {

			System.out.println("Error parsing User-Agent string: " + e.getMessage());

		}
		logger.info("Browser Name: {}", browserName);
		logger.info("Browser Version: {}", browserVersion);
		logger.info("OS: {}", os);
		logger.info("Device: {}", device);
	}

}
