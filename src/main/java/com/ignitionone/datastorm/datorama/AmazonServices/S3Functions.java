package com.ignitionone.datastorm.datorama.AmazonServices;

/**
 * Created by karthik.inuganti on 12/13/2016.
 */

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;


import java.io.*;

public class S3Functions {
//~ Methods --------------------------------------------------------------------------------------------------------

    /*
           * Read and print the folder structure of a AWS Bucket.
              */


    public String getFilePathFromBucket(String bucketName, AmazonS3 s3, String fileName, String Dir) throws AmazonServiceException {

        final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(Dir).withMaxKeys(100);
        ListObjectsV2Result result;
        String ObjKey="";
        do {
            result = s3.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                if (objectSummary.getKey().toLowerCase().contains(fileName.toLowerCase())) {
                    objectSummary.getBucketName();

                    ObjKey= objectSummary.getKey();
                    break;
                }
            }
            req.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated() == true);
        return ObjKey;
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

    public  File DownloadCSVFromS3(String bucketName, AmazonS3 s3, String filePath, String fileName) throws IOException {


        final ListObjectsV2Request req2 = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(filePath).withMaxKeys(10);
        ListObjectsV2Result result2;

        do {
            result2 = s3.listObjectsV2(req2);

            for (S3ObjectSummary objectSummary : result2.getObjectSummaries()) {
                String key1 = objectSummary.getKey();
                String bucketName1 = objectSummary.getBucketName();
                S3Object object1 = s3.getObject(new GetObjectRequest(bucketName1, key1));

                InputStream reader = new BufferedInputStream(
                        object1.getObjectContent());
                File file = new File(System.getProperty("user.dir") + "/"+fileName+".csv");
                OutputStream writer = new BufferedOutputStream(new FileOutputStream(file,false));
                int read = -1;

                while ((read = reader.read()) != -1) {
                    writer.write(read);
                }
                writer.flush();
                writer.close();
                reader.close();
                return file;
            }
            req2.setContinuationToken(result2.getNextContinuationToken());
        } while (result2.isTruncated() == true);
        return null;
    }


    /*
        public CSVRecord getColumsCompanyStore(String bucketName, AmazonS3 s3, String filePath) throws IOException {


            final ListObjectsV2Request req2 = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(filePath).withMaxKeys(10);
            ListObjectsV2Result result2;

            do {
                result2 = s3.listObjectsV2(req2);

                for (S3ObjectSummary objectSummary : result2.getObjectSummaries()) {
                    String key1 = objectSummary.getKey();
                    String bucketName1 = objectSummary.getBucketName();
                    S3Object object1 = s3.getObject(new GetObjectRequest(bucketName1, key1));

                    InputStream reader = new BufferedInputStream(
                            object1.getObjectContent());
                    File file = new File(System.getProperty("user.dir") + "/CompanyStore.csv");
                    OutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
                    int read = -1;

                    while ((read = reader.read()) != -1) {
                        writer.write(read);
                    }
                    writer.flush();
                    writer.close();
                    reader.close();
                    String PATH1 = System.getProperty("user.dir") + "/CompanyStore.csv";

                    Reader in = new InputStreamReader(new BOMInputStream(new FileInputStream(PATH1)), "UTF-16");
                    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
                    for (CSVRecord record : records) {
                        for (int i = 0; i < 11; i++) {
                            record.get(i);
                        }
                        return record;
                    }
                }

                req2.setContinuationToken(result2.getNextContinuationToken());
            }

            while (result2.isTruncated() == true);
            return null;
        }

    */
    public int csvTotalCountRecords(String filePath) throws IOException {

        try {

            File file = new File(filePath);


            if (file.exists()) {

                FileReader fr = new FileReader(file);
                LineNumberReader lnr = new LineNumberReader(fr);

                int linenumber = 0;

                while (lnr.readLine() != null) {
                    linenumber++;
                }
                System.out.println("Total number of lines : " + linenumber);


                lnr.close();
                return linenumber;

            } else {
                System.out.println("File does not exists!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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