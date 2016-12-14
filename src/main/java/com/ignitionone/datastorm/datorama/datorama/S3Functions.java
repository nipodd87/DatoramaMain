package com.ignitionone.datastorm.datorama.datorama;

/**
 * Created by karthik.inuganti on 12/13/2016.
 */

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class S3Functions {
//~ Methods --------------------------------------------------------------------------------------------------------

    /*
           * Read and print the folder structure of a AWs Bucket.
              */


    public void getFolderStructureOfBucket(String bucketName, AmazonS3 s3) throws AmazonServiceException {

        final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
        ListObjectsV2Result result;

        do {
            result = s3.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                        "(size = " + objectSummary.getSize() +
                        ")");
            }
            //   System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
            req.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated() == true);


    }


    public void getAllFileContentsOfBucket(String bucketName, AmazonS3 s3) throws IOException {

        final ListObjectsV2Request req2 = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
        ListObjectsV2Result result2;
        do {
            result2 = s3.listObjectsV2(req2);

            for (S3ObjectSummary objectSummary : result2.getObjectSummaries()) {
                String key1 = objectSummary.getKey();

                String bucketName1 = objectSummary.getBucketName();

                S3Object object1 = s3.getObject(new GetObjectRequest(bucketName1, key1));
                System.out.println("Content-Type: " + object1.getObjectMetadata().getContentType());
                displayTextInputStream(object1.getObjectContent());

            }
            //   System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
            req2.setContinuationToken(result2.getNextContinuationToken());
        } while (result2.isTruncated() == true);



    }
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }

}
