package com.swiffshot.media;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {

	//@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey = "AKIAIHIQQ5APNI2YD2NA";

	//@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey = "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";

	//@Value("${cloud.aws.region}")
	private String region = "us-west-2"; //Oregon

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
		AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
		amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
		return amazonS3Client;
	}
}