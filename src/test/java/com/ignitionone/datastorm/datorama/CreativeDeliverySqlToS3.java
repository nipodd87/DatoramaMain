package com.ignitionone.datastorm.datorama;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ignitionone.datastorm.datorama.AmazonServices.S3Functions;
import com.ignitionone.datastorm.datorama.etl.DatoramaNanETL;
import com.ignitionone.datastorm.datorama.util.CSVandTextReader;
import com.ignitionone.datastorm.datorama.util.CommonUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by nitin.poddar on 1/31/2017.
 */
public class CreativeDeliverySqlToS3  extends BaseClass  {

    protected String environment;
    String SOURCE_TABLE = "SQL Nan";
    String DESTINATION_TABLE = "Amazon S3";
    private final String REPORT_HEADER = "File level tests <BR>  Verify Row Count <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    private final String REPORT_TITLE = "Verify Total Row count between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    String envt;

    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    File creativeDeliveryFile;
    String creativeDeliveryFilePath;
    String Bucket_Name = "thirdpartyreporting";
    String creativeDeliveryDirectory = "Datorama/Final/EventData/Summarized/Creative/Delivery";
    String sqlFile = "sql/sqlNan.sql";
    String storeProcFile = "sql/datorama_stored_procedure.sql";
    public static final int FILE_UPLOAD_SUCCESS=3;
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    int recordCount;
    int fileStatusID;
    String creativeDeliveryFileName;
    ResultSet thirdPartyFileInfoResultSet;
    int spRecordCount;
    public int total_impressions;
    public int total_clicks;
    public double total_cost;


    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        init(envt, REPORT_HEADER, REPORT_TITLE);

    }

    @Test
    public void creativeDeliveryToS3() throws Exception {
        //Execute the Third Party File Info Query to get the Corresponding Information (Report Start, End date, File Status ID, Record Count, File Name
        executor = new DatoramaNanETL();
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", "1");
        reportStartDate=DatoramaNanETL.reportStartDate;
        reportEndDate=DatoramaNanETL.reportEndDate;
        creativeDeliveryFileName=DatoramaNanETL.fileName;
        recordCount=DatoramaNanETL.recordCount;
        fileStatusID=DatoramaNanETL.fileStatusID;
        //Execute the Stored Procedure to get Start and End Date
        spRecordCount=executor.getStoreProcedureCount(storeProcFile, envt,"thirdPartyFileGeneration_CreativeDelivery", "$START_DATE$", reportStartDate, "$END_DATE$", reportEndDate);
        //Check File Status and record counts match
        CommonUtil.compareNumberEquals(FILE_UPLOAD_SUCCESS, fileStatusID, "File Upload Test", "File has been uploaded successfully in Amazon S3");
        CommonUtil.compareNumberEquals(recordCount, spRecordCount, "Record Count Test between Table and Store Procedure", "Comparing count between table and Store Procedure" );
        //Download the File from Amazon S3
        creativeDeliveryFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, creativeDeliveryFileName, creativeDeliveryDirectory);
        creativeDeliveryFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3, creativeDeliveryFilePath,"CreativeDeliverySummarizedData");
        //Check File Existence in Amazon S3
        if (creativeDeliveryFile != null){
            extentReportUtil.logPass("Check File Existence", "File was uploaded successfully in Amazon S3");
        } else {
            extentReportUtil.logFail("Check File Existence", "File was not uploaded properly");
        }
        //Check Measurment Counts for Impressions, Cost and Clicks from the table
        executor.getMeasurementCounts(sqlFile, envt, "getMeasurementCountCreativeDelivery", "$START_DATE$", reportStartDate, "$END_DATE$", reportEndDate);
        total_impressions=DatoramaNanETL.total_impressions;
        total_clicks=DatoramaNanETL.total_clicks;
        total_cost=DatoramaNanETL.total_cost;
        System.out.println("Impressions: "+total_impressions);
        System.out.println("Cost: "+total_cost);
        System.out.println("Clicks: "+total_clicks);
        //
        CSVandTextReader csvReader = new CSVandTextReader();

        List<String> creativeConversionDestList = csvReader.getCSVData(System.getProperty("user.dir")+"/"+"CreativeConversionSummarizedData.csv");

    }
}
