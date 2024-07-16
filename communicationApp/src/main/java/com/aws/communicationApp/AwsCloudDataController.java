package com.aws.communicationApp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AwsCloudDataController {
	
	@Autowired
	private AwsCloudDataService awsCloudDataService;

    
	/**
	 * Fetches data for a specified service
	 * @param serviceName - The name of the service
	 * @return The result of service discovery
	 */
    @GetMapping("/data")
    public String getData(@RequestParam("serviceName") String serviceName) {
        return awsCloudDataService.getDiscoveryResult(serviceName);
    }

    /**
     * Discovers services & persist them into the database based on provided service names
     */
    @PostMapping("/discover")
    public String discoverServices(@RequestBody List<String> services) {
        return awsCloudDataService.discoverServices(services);
    }

    
    /**
     * Fetch the status of a job by given jobId
     */
    @GetMapping("/getJobResult/{jobId}")
    public String getJobResult(@PathVariable String jobId) {
        return awsCloudDataService.getJobStatus(jobId);
    }

    
    /**
     * Fetches list of s3 buckets if selected service param is S3
     * Fetches list of Instance IDs if selected service param is EC2
     */
    @GetMapping("/getDiscoveryResult")
    public String getDiscoveryResult(@RequestParam String service) {
        return awsCloudDataService.getDiscoveryResult(service);
    }

    /**
     * Discover all the File Names in the S3 bucket and persist in the DB.
     * @param bucketName
	 * @return JobID 
     */
    @PostMapping("/getS3BucketObjects")
    public String getS3BucketObjects(@RequestParam String bucketName) {
        return awsCloudDataService.getS3BucketObjects(bucketName);
    }

    /**
     * Fetches the count of the S3 objects for the selected bucket name
     * @param bucketName
     */
    @GetMapping("/getS3BucketObjectCount")
    public int getS3BucketObjectCount(@RequestParam String bucketName) {
        return awsCloudDataService.getS3BucketObjectCount(bucketName);
    }

    /**
     * Fetches the count of the S3 objects for the selected bucket name and pattern
     * @param bucketName
     * @param pattern
     * @Return List<String> of s3 bucket objects
     */
    @GetMapping("/getS3BucketObjectLike")
    public List<String> getS3BucketObjectLike(@RequestParam String bucketName, @RequestParam String pattern) {
        return awsCloudDataService.getS3BucketObjectLike(bucketName, pattern);
    }
    
    
}
