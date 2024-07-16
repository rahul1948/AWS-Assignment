package com.aws.communicationApp;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


@Component
public class AmazonClient {


    @Autowired
    ReadProfileProperties readProfileProperties;
   
    AWSCredentials awsCredentials = new BasicAWSCredentials(readProfileProperties.getClintId(), readProfileProperties.getClintSecretId());

    /**
     * This method is invoked only once & after all the beans/dependencies are initialized.
     * This method creates the connection to AWS S3
     */
    @PostConstruct
    public AmazonS3 amazonS3()
    {
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.AP_SOUTH_1) // Use AP_SOUTH_1 for Mumbai region
                    .build();
    }
    
    
    @PostConstruct
    public AmazonEC2 amazonEC2() {
        return AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }

}

