package com.ignitionone.datastorm.datorama.AmazonServices;

/**
 * Created by karthik.inuganti on 12/13/2016.
 */

import au.com.bytecode.opencsv.CSVReader;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
//import com.csvreader.CsvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class S3Functions {
//~ Methods --------------------------------------------------------------------------------------------------------

    /*
           * Read and print the folder structure of a AWS Bucket.
              */


    public String getFilePathFromBucket(String bucketName, AmazonS3 s3, String fileName,String Dir) throws AmazonServiceException {

        final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(Dir).withMaxKeys(100);
        ListObjectsV2Result result;

        do {
            result = s3.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                if (objectSummary.getKey().toLowerCase().contains(fileName.toLowerCase())) {
                    objectSummary.getBucketName();

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
