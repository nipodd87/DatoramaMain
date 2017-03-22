package com.ignitionone.datastorm.datorama;

import com.google.api.services.dfareporting.Dfareporting;
import com.google.api.services.dfareporting.model.File;
import com.google.api.services.dfareporting.model.Report;
import com.ignitionone.datastorm.datorama.DCMUtil.*;
import com.ignitionone.datastorm.datorama.apiUtil.APIRequestBodyGenerator;
import com.ignitionone.datastorm.datorama.apiUtil.APIUtil;
import com.ignitionone.datastorm.datorama.datoramaUtil.DatoramaCSVUtil;
import com.ignitionone.datastorm.datorama.datoramaUtil.JsonParser;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.util.ETLUtil;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;
import static io.restassured.path.json.JsonPath.from;

/**
 * Created by nitin.poddar on 3/9/2017.
 */
public class DCMCreative extends BaseClass{

    private static final String REPORT_HEADER = "DCM Creative Level Test";
    private static final String REPORT_TITLE = "This test is to verify Google DCM Creative Level Data against Datorama.";
    String SOURCE_TABLE = "Datorama API Response";
    String DESTINATION_TABLE = "Google DCM";
    public long reportId;
    public JsonParser parser = new JsonParser();
    String envt;
    String reportStartDate;
    String reportEndDate;
    public static final String DELIMITER = "\\|@\\|";
    public int recordCount;

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt,REPORT_HEADER, REPORT_TITLE);
    }


    @Test
    public void domainDeliveryToDatorama() throws Exception {

        //Authenticate Datorama to fetch Authentication Token
        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");


        //Execute the API query to fetch the Start and End date for the selected stream
        String Resp2 = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getJobStreamInfo(brandId, "DCM Creative Delivery - Motel 6"), token);
        JSONObject jsonObject2 = parser.convertStringtoJsonObj(Resp2);
        List<String> jobStreamStatusList = parser.convertJsonToList(jsonObject2);

        //Get the Status Value into class variables
/*        String statusResult[] = jobStreamStatusList.get(1).split(DELIMITER);
        reportStartDate=statusResult[1];
        reportEndDate=statusResult[2];
        recordCount = Integer.parseInt(statusResult[3].replace(".0", ""));*/
        reportStartDate="2017-03-20";
        reportEndDate="2017-03-20";

        //Initialize the Dfareporting object from the DfaReportingFactory
        Dfareporting reporting = DfaReportingFactory.getInstance();

        //Build the report
        DfaReportBuilder reportBuilder = new DfaReportBuilder();
        Report report = reportBuilder.getDfaReport(reportEndDate, reportEndDate, "DCM Creative Delivery - Motel 6", "dfa:advertiserId",
                "4279063", System.getProperty("user.dir")+"/DCM_Motel6_Reporter.xlsx");
        Report result = reporting.reports().insert(USER_PROFILE, report).execute();
        reportId = result.getId();

        //Build the mapper between DCM and Datorama columns
        RecordLevel recordLevel = new RecordLevel();
        ETLUtil etlUtil = new ETLUtil();
        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir")+"/"+"DCM_Motel6_Mapper.xlsx", "DCM_Motel6_Mapper");

        //Get the API Response data from Datorama and convert into list of Strings
        String Resp1 = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getDCMReport1(reportEndDate, reportEndDate, brandId, "4279063"), token);
        JSONObject jsonObject1 = parser.convertStringtoJsonObj(Resp1);
        List<String> dcmReportAsAPI = parser.convertJsonToList(jsonObject1);

        //Run the report to get the file Id
        File reportFile=DfaReportRunner.runReport(reporting, USER_PROFILE,reportId);
        Thread.sleep(180000);
        //Download the Report
        List<String> dcmReportList=DfaReportDownloader.downloadFileAsList(reporting, USER_PROFILE, reportId, reportFile.getId());

        //Generate the source and destination Mapping using ETL util and excel reader


        extentReportUtil.logInfo("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE+" Report Start Date: "+reportStartDate+" Report End Date: "+reportEndDate);
        recordLevel.verifySrcWithDestData(mapper,dcmReportAsAPI,dcmReportList);

        //Delete the Report
        Long reportToBeDeleted = reportId;
        if (reportToBeDeleted != null){
            DfaReportDelete.deleteReport(reporting, USER_PROFILE, reportId);
        }

    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
