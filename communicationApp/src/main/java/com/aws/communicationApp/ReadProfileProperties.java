package com.aws.communicationApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySources(value = {
		@PropertySource("classpath:application.properties"),
		})
@Getter
@Setter
public class ReadProfileProperties {

	/**AWS Configuration*/
	@Value("${aws.clintId}")
	private String clintId;
	
	@Value("${aws.clintSecretId}")
	private String clintSecretId;
}


