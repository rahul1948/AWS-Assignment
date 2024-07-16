package com.aws.communicationApp;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySource("classpath:sql/manageSql.yml")
@ConfigurationProperties(prefix = "manage-sql")
@Getter
@Setter
public class ManageSqlQueryConfig 
{
    @Value("${setJobStatusSql}")
    private String setJobStatusSql;
    
    @Value("${persistBucketsSql}")
    private String persistBucketsSql;
    
    @Value("${persistInstancesSql}")
    private String persistInstancesSql;
    
    @Value("${persistBucketObjectsSql}")
    private String persistBucketObjectsSql;
    
    @Value("${fetchS3BucketObjectCount}")
    private String fetchS3BucketObjectCount;
    
    @Value("${fetchS3BucketObjectLike}")
    private String fetchS3BucketObjectLike;
    
    @Value("${fetchJobStatusByJobId}")
    private String fetchJobStatusByJobId;
  		
}