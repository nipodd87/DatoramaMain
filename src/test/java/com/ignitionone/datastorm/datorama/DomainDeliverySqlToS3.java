package com.ignitionone.datastorm.datorama;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ignitionone.datastorm.datorama.AmazonServices.S3Functions;
import com.ignitionone.datastorm.datorama.datoramaUtil.DatoramaCSVUtil;
import com.ignitionone.datastorm.datorama.etl.DatoramaNanETL;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.model.DeliveryMetrics;
import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ETLUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * Created by karthik.inuganti on 2/7/2017.
 */
public class DomainDeliverySqlToS3 extends BaseClass {
    protected String environment;
    String SOURCE_TABLE = "SQL Nan tables";
    String DESTINATION_TABLE = "Amazon S3 CSV Files";
    private final String REPORT_HEADER = "Domain Delivery SQL Nan to Amazon S3 Test Case 1 <BR> Verify File Status in Audit Log Table";
    private final String REPORT_TITLE = "Verify that File Status ID is updated correctly in the log table";
    String envt;

    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    File domainDeliveryFile;
    String domainDeliveryFilePath;
    String Bucket_Name = "thirdpartyreporting";
    String domainDeliveryDirectory = "Datorama/Archive/EventData/Summarized/Domain/Delivery";
    String sqlFile = "sql/sqlNan.sql";
    String storeProcFile = "sql/datorama_stored_procedure.sql";
    public static final int FILE_UPLOAD_SUCCESS=3;
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    int recordCount;
    int fileStatusID;
    String domainDeliveryFileName;
    ResultSet thirdPartyFileInfoResultSet;
    int spRecordCount;
    DeliveryMetrics metrics;
    public int total_impressions;
    public int total_clicks;
    public double total_cost;
    ETLUtil etlUtil = new ETLUtil();
    RecordLevel recordLevel = new RecordLevel();


    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        init(envt, REPORT_HEADER, REPORT_TITLE);

    }
    @Test
    public void domainDeliveryToS3() throws Exception {
        //Execute the Third Party File Info Query to get the Corresponding Information (Report Start, End date, File Status ID, Record Count, File Name
        executor = new DatoramaNanETL();
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", "7");
        reportStartDate=DatoramaNanETL.reportStartDate;
        reportEndDate=DatoramaNanETL.reportEndDate;
        domainDeliveryFileName =DatoramaNanETL.fileName;
        recordCount=DatoramaNanETL.recordCount;
        fileStatusID=DatoramaNanETL.fileStatusID;

        //Execute the Stored Procedure to get Start and End Date
        List<String> spResultData = executor.getStoredProcedure(storeProcFile, envt,"thirdPartyFileGeneration_DomainDelivery", "$START_DATE$", reportStartDate, "$END_DATE$", reportEndDate);
        spRecordCount = spResultData.size()-1;

        //Check File Status in the Audit Log Table
        CommonUtil.compareNumberEquals(FILE_UPLOAD_SUCCESS, fileStatusID, "File Status ID Check", "same as expected in the Audit Log table");
        extentReportUtil.endTest();
        extentReportUtil.startTest("Domain Delivery SQL Nan to Amazon S3 Test Case 2 <BR> Verify Record Count <BR> Source Table : " + "NAN Stat" + " and Destination Table : " + "Store Procedure", "Verify record count between Source Table: " + "NAN STAT table" + " and Destination Table : " + "Store Procedure");
        CommonUtil.compareNumberEquals(recordCount, spRecordCount, "Record Count Test between Table and Store Procedure", " between Nan Stat tables and Store Procedure" );
        extentReportUtil.endTest();
        
        //Check if the file is present and uploaded properly in Amazon S3 bucket
        extentReportUtil.startTest("Domain Delivery SQL Nan to Amazon S3 Test Case 3 <BR> Verify File Upload Status", "Verify to see if the file with correct name and format has been uploaded in Amazon S3 Bucket");
        domainDeliveryFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, domainDeliveryFileName, domainDeliveryDirectory);
        domainDeliveryFile =s3Functions.DownloadCSVFromS3(Bucket_Name,s3, domainDeliveryFilePath,"DomainDeliverySummarizedData");
        
        //Check File Existence in Amazon S3
        if (domainDeliveryFile != null){
            extentReportUtil.logPass("Check File Existence", "File was uploaded successfully in Amazon S3");
        } else {
            extentReportUtil.logFail("Check File Existence", "File was not uploaded properly");
        }
        extentReportUtil.endTest();
        
        //Check Measurement Counts for Impressions, Cost and Clicks from the table
        executor.getMeasurementCount(sqlFile, envt, "getMeasurementCountDomainDelivery", "$START_DATE$", reportStartDate, "$END_DATE$", reportEndDate);
        total_impressions=DatoramaNanETL.total_impressions;
        total_clicks=DatoramaNanETL.total_clicks;
        total_cost=DatoramaNanETL.total_cost;
        
        //get the count of the measurements (Impressions, Clicks and Cost) from downloaded file from S3
        extentReportUtil.startTest("Domain Delivery SQL Nan to Amazon S3 Test Case 4 <BR> Check the Sum of Measurements between SQL NAN Stat table and CSV file Generated", "Check sum of Impressions, Clicks and Costs");
        metrics= DatoramaCSVUtil.getDomainDeliveryMeasurementTotal("DomainDeliverySummarizedData.csv", ',');
        
        //Compare the count of the measurement between stored procedure and datorama
        CommonUtil.compareNumberEquals(total_impressions, metrics.getTotalImpressions(), "Check Sum of Impressions", " between actual NAN table and Amazon S3 csv file");
        CommonUtil.compareNumberEquals(total_clicks, metrics.getTotalClicks(), "Check Sum of Clicks", " between actual NAN table and Amazon S3 csv file");
        CommonUtil.compareNumberEquals(total_cost, metrics.getTotalCost(), 0.9, "Check Sum of Costs", " between actual NAN table and Amazon S3 csv file");
        extentReportUtil.endTest();
        
        //Check the record count between Store Proc and Amazon Csv
        extentReportUtil.startTest("Domain Delivery SQL Nan to Amazon S3 Test Case 5<BR> Check the Record Count between Store Procedure and Amazon S3 CSV file", "between Store Procedure and Amazon S3 file");
        CommonUtil.compareNumberEquals(spRecordCount, metrics.getRecordCount(), "Record Count Between Stored Procedure and Amazon S3", "between Store Procedure and Amazon S3 csv file");
        extentReportUtil.endTest();

        //Get List of String from CSV files
        extentReportUtil.startTest("Domain Delivery SQL Nan to Amazon S3 Test Case 6<BR> Compare first 200 rows between Store Procedure and Amazon S3 CSV file", "between Store Procedure and Amazon S3 file");
        List<String> domainDeliveryS3List = DatoramaCSVUtil.getDomainDeliveryCSVData("DomainDeliverySummarizedData.csv", ',', "|@|");
        //Reduce the List to Only 200 rows
        List<String> domainDeliverySPList200 = DatoramaNanETL.getFirstNRows(spResultData, 200);
        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir")+"/"+"Datorama_Mapping.xlsx", "Domain_Delivery_S3_Mapper");
        recordLevel.verifySrcWithDestData(mapper, domainDeliverySPList200, domainDeliveryS3List);
    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }

}
