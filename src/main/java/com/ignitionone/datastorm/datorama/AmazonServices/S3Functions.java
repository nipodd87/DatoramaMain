package com.ignitionone.datastorm.datorama.AmazonServices;

/**
 * Created by karthik.inuganti on 12/13/2016.
 */

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class S3Functions {
//~ Methods --------------------------------------------------------------------------------------------------------

    /*
           * Read and print the folder structure of a AWS Bucket.
              */


    public String getFilePathFromBucket(String bucketName, AmazonS3 s3, String fileName) throws AmazonServiceException {

        final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
        ListObjectsV2Result result;

        do {
            result = s3.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                if (objectSummary.getKey().contains("/" + fileName)) {
                    return objectSummary.getKey();
                }
            }
            req.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated() == true);
        return "";
    }


    public long getFileSize(String bucketName, AmazonS3 s3, String filePath) throws AmazonServiceException {


        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(filePath));

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            //   System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            return objectSummary.getSize();
        }
        System.out.println();
        return 0;
    }


    public String getMetaDataOfFile(String bucketName, AmazonS3 s3, String filePath) throws IOException {

        final ListObjectsV2Request req2 = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(filePath).withMaxKeys(2);
        ListObjectsV2Result result2;
        do {
            result2 = s3.listObjectsV2(req2);

            for (S3ObjectSummary objectSummary : result2.getObjectSummaries()) {
                String key1 = objectSummary.getKey();

                String bucketName1 = objectSummary.getBucketName();

                S3Object object1 = s3.getObject(new GetObjectRequest(bucketName1, key1));
                System.out.println("Content-Type: " + object1.getObjectMetadata().getContentType());

                //  displayTextInputStream(object1.getObjectContent());
                BufferedReader reader = new BufferedReader(new InputStreamReader(object1.getObjectContent()));
                String line = reader.readLine();
                if (line == null) break;
                System.out.println("    " + line);
                return line;
            }
            //   System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
            req2.setContinuationToken(result2.getNextContinuationToken());
        } while (result2.isTruncated() == true);
        return "";
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
