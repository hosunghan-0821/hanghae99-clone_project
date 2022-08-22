package com.hanghae.clone_project.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        //AwsBasicCredentials 클래스는 AWS 서비스 접근에 사용되는 AWS credentials(access key ID, secret access key) 에 대한 접근을 제공한다.
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        // credentialProvider()
        //AWS 와 관련해 인증하는데 사용되는 credentials 를 구성한다.
        //기본 provider 는 다음의 사항들을 체크하며 credentials 를 자동으로 인증하려 시도한다.
        //1) Java System Properties - aws.accessKeyId, aws.secretKey
        //2) Environment Variables - AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY
        //3) Credential profiles at default locatio

    }
}
