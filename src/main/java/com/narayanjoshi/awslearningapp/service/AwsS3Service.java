package com.narayanjoshi.awslearningapp.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.narayanjoshi.awslearningapp.exception.UploadException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class AwsS3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket.name}")
    private String bucketName;

    @Value("${temp.file.location}")
    private String tempFileLocation;

    public void uploadFile(byte[] bytes, long userId) {

        String fileName = prepareFileName(userId);

        String tempFileSaved = tempFileLocation + UUID.randomUUID().toString();

        Path filepath = Paths.get(tempFileSaved, fileName);


        try {

            File file = filepath.toFile();
            FileUtils.writeByteArrayToFile(file, bytes);

            amazonS3.putObject(bucketName, fileName, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            FileUtils.deleteQuietly(filepath.toFile());
        }

    }

    private static String prepareFileName(long userId) {
        String fileName = userId + "profile-img.jpg";
        return fileName;
    }

    public String generatePresignedUrl(long userId) {

        String fileName = prepareFileName(userId);

        boolean doesObjectExist = amazonS3.doesObjectExist(bucketName, fileName);

        if(!doesObjectExist){
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1); // Generated URL will be valid for 24 hours
        return amazonS3.generatePresignedUrl(bucketName, fileName, calendar.getTime(), HttpMethod.GET).toString();
    }

}
