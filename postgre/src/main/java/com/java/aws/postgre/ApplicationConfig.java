package com.java.aws.postgre;

import com.amazonaws.secretsmanager.caching.SecretCache;
import org.springframework.boot.jdbc.DataSourceBuilder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    private Gson gson = new Gson();
    private SecretCache cache;

    @Bean
    @Primary
    public DataSource dataSource() {
        AwsSecrets secrets = getSecret();
        System.out.println("Host Name: " + secrets.getHost());
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://" + secrets.getHost() + "/Database_Name?sslmode=disable")
                .username(secrets.getUsername())
                .password(secrets.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    private AwsSecrets getSecret() {
        String secretName = "SecreteName";
        Region region = Region.US_EAST_1;

        if (cache == null) {
            SecretsManagerClient client = SecretsManagerClient.builder()
                    .region(region)
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();
            cache = new SecretCache(client);
        }

        String secretString = cache.getSecretString(secretName);
        if (secretString != null) {
            return gson.fromJson(secretString, AwsSecrets.class);
        }

        return null;
    }
}
