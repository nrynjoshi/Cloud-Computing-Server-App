package com.narayanjoshi.awslearningapp.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ConnnectionConfig {

    @Value("${access.key.id}")
    private String accessId;

    @Value("${access.key.secret}")
    private String secret;

    @Value("${s3.region.name}")
    private String regionName;



    @Bean
    public AmazonS3 getS3Connection() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessId, secret);
        // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(regionName)
                .build();
    }


}
