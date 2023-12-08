package com.narayanjoshi.awslearningapp.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.narayanjoshi.awslearningapp.exception.UploadException;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

@Component
public class AwsS3Service {

    private final Region awsRegion =  Region.US_EAST_1;
    private final String BUCKET = "profile-img-bucket";

    private S3Client s3Client;

    public void init(Region awsRegion) {
        AWSCredentials credentials = new BasicAWSCredentials(
                "<AWS accesskey>",
                "<AWS secretkey>"
        );

    }

    public void upload(InputStream inputStream, String fileName) throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials(
                "<AWS accesskey>",
                "<AWS secretkey>"
        );

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();


        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));

        S3Waiter s3Waiter= S3Waiter.create();
        HeadObjectRequest waitRequest= HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();
        WaiterResponse<HeadObjectResponse> headObjectResponseWaiterResponse = s3Waiter.waitUntilObjectExists(waitRequest);
        boolean present = headObjectResponseWaiterResponse.matched().response().isPresent();
        if(!present){
            throw new UploadException("File upload error.");
        }
    }
}
