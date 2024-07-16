package com.aws.communicationApp;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import java.util.UUID;


@Service
public class AwsCloudDataService{
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private ManageSqlQueryConfig manageSqlQueryConfig;
	
	 @Autowired
	 private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	/**
	  * Fetched discovery result for a given service name.
	 */
    public String getDiscoveryResult(String serviceName)
    {
        if ("S3".equalsIgnoreCase(serviceName)) {
            List<Bucket> buckets = amazonClient.amazonS3().listBuckets();
            return buckets.stream().map(Bucket::getName).collect(Collectors.joining(", "));
        } else if ("EC2".equalsIgnoreCase(serviceName)) {
        	List<Instance> instances = this.getEC2Instances();
        	return instances.stream()
        	        .map(Instance::getInstanceId)
        	        .collect(Collectors.joining(", "));
        } else {
            return "Service not supported";
        }
    }
    
    
    /**
     * Initiates discovery of specified AWS services asynchronously
     */
    public String discoverServices(List<String> services)
    {
    	this.setJobStatus(null,"In Progress",UUID.randomUUID().toString());
        services.forEach(service -> {
            if ("S3".equalsIgnoreCase(service)) {
                discoverS3Buckets().thenAccept(buckets -> {
                	captureBuckets(buckets);
                	this.setJobStatus(service,"Success",UUID.randomUUID().toString());

                }).exceptionally(ex -> {
                	this.setJobStatus(service,"Failed",UUID.randomUUID().toString());
                    return null;
                });
            } else if ("EC2".equalsIgnoreCase(service)) {
                discoverEC2Instances().thenAccept(instances -> {
                	captureInstances(instances);
                	this.setJobStatus(service,"Success",UUID.randomUUID().toString());

                }).exceptionally(ex -> {
                	this.setJobStatus(service,"Failed",UUID.randomUUID().toString());
                    return null;
                });
            }
        });
        return UUID.randomUUID().toString();
    }
    
	
    /**
     * Fetches EC2 instances from AWS
     */
    public List<Instance> getEC2Instances()
    {
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        DescribeInstancesResult response = amazonClient.amazonEC2().describeInstances(request);
        List<Instance> instances = new ArrayList<>();
        for (Reservation reservation : response.getReservations()) {
            instances.addAll(reservation.getInstances());
        }
        return instances;
    }

    
    /**
     * Asynchronously discovers S3 buckets from AWS
     */
    @Async
    public CompletableFuture<List<Bucket>> discoverS3Buckets() 
    {
        List<Bucket> buckets = amazonClient.amazonS3().listBuckets();
        return CompletableFuture.completedFuture(buckets);
    }
    
    /**
     * Asynchronously discovers EC2 instances from AWS
     */
    @Async
    public CompletableFuture<List<Instance>> discoverEC2Instances()
    {
    	List<Instance> instances = this.getEC2Instances();
        return CompletableFuture.completedFuture(instances);
    }
    
    
    /**
     * Sets job status for a specified service
     */
    public void setJobStatus(String service,String jobStatus,String jobId)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("service", service);
        params.put("jobStatus", jobStatus);
        params.put("jobId", jobId);
        namedParameterJdbcTemplate.update(manageSqlQueryConfig.getSetJobStatusSql(), params);
    }
    
    /**
     * Captures discovered S3 buckets into the database
     */
    public void captureBuckets(List<Bucket> buckets)
    {        
        for (Bucket bucket : buckets)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("name", bucket.getName());
            params.put("creationDate", new java.sql.Timestamp(bucket.getCreationDate().getTime()));
            params.put("ownerId", bucket.getOwner().getId());
            namedParameterJdbcTemplate.update(manageSqlQueryConfig.getPersistBucketsSql(), params);
        }
    }
    
    
    /**
     * Captures discovered EC2 instances into the database
     */
    public void captureInstances(List<Instance> instances) 
    {
        for (Instance instance : instances)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("instanceId", instance.getInstanceId());
            params.put("instanceType", instance.getInstanceType());
            params.put("state", instance.getState().getName());
            params.put("launchTime", new Timestamp(instance.getLaunchTime().getTime()));
            params.put("privateIp", instance.getPrivateIpAddress());
            params.put("publicIp", instance.getPublicIpAddress());
            params.put("availabilityZone", instance.getPlacement().getAvailabilityZone());
            namedParameterJdbcTemplate.update(manageSqlQueryConfig.getPersistInstancesSql(), params);
        }
    }
    
    
    /**
     * Fetch job status for a specified job identifier
     */
    public String getJobStatus(String jobId) 
    {
        Map<String, Object> params = new HashMap<>();
        params.put("jobId", jobId);
        return namedParameterJdbcTemplate.queryForObject(manageSqlQueryConfig.getFetchJobStatusByJobId(), params, String.class);
    }
    
    
    /**
     * Fetch the count of objects in a specified S3 bucket
     */
    public int getS3BucketObjectCount(String bucketName) 
    {
        Map<String, Object> params = new HashMap<>();
        params.put("bucketName", bucketName);
        return namedParameterJdbcTemplate.queryForObject(manageSqlQueryConfig.getFetchS3BucketObjectCount(), params, Integer.class);
    }
    
    /**
     * Fetched S3 bucket objects that match a specified pattern
     */
    public List<String> getS3BucketObjectLike(String bucketName, String pattern)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("bucketName", bucketName);
        params.put("pattern", "%" + pattern + "%");
        return namedParameterJdbcTemplate.queryForList(manageSqlQueryConfig.getFetchS3BucketObjectLike(), params, String.class);
    }
    
    
    /**
     * Fetch objects from a specified S3 bucket
     */
    public String getS3BucketObjects(String bucketName) 
    {
        String jobId = UUID.randomUUID().toString();
    	this.setJobStatus(bucketName,"In Progress",jobId);
        discoverS3BucketObjects(bucketName).thenAccept(objects -> {
        	captureBucketObjects(bucketName, objects);
        	this.setJobStatus(bucketName,"Success",UUID.randomUUID().toString());
        }).exceptionally(ex -> {
        	this.setJobStatus(bucketName,"Failed",UUID.randomUUID().toString());
            return null;
        });
        return jobId;
    }
    
    
    /**
     * Asynchronously discovers objects in a specified S3 bucket
     */
    @Async
    public CompletableFuture<List<S3ObjectSummary>> discoverS3BucketObjects(String bucketName) 
    {
        List<S3ObjectSummary> objectSummaries = amazonClient.amazonS3().listObjects(bucketName).getObjectSummaries();
        return CompletableFuture.completedFuture(objectSummaries);
    }
    
    /**
     * Captures discovered S3 bucket objects into the database
     */
    public void captureBucketObjects(String bucketName, List<S3ObjectSummary> objects)
    {
        for (S3ObjectSummary object : objects) {
            Map<String, Object> params = new HashMap<>();
            params.put("bucketName", bucketName);
            params.put("objectKey", object.getKey());
            params.put("size", object.getSize());
            namedParameterJdbcTemplate.update(manageSqlQueryConfig.getPersistBucketObjectsSql(), params);
        }
    }

}
