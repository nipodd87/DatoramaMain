package com.ignitionone.datastorm.datorama;

import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.etl.TableLevel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ignitionone.datastorm.datorama.etl.DataType.INT;
import static com.ignitionone.datastorm.datorama.etl.DataType.VARCHAR;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;

public class CreativeETLTest extends BaseClass {

    //sql postgres
    private String SOURCE_TABLE = "creatives";
    //sql nan
    private String DESTINATION_TABLE = "Display2_Creatives";

    private final String REPORT_HEADER = "File level tests <BR>  Verify Row Count <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    private final String REPORT_TITLE = "Verify Total Row count between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    protected String environment;

    //String srcSqlFile = "sql/dmsReadCache.sql";
    String srcSqlFile = "sql/postgres.sql";

    //String destSqlFile = "sql/dmsReadCache.sql";
    String destSqlFile = "sql/sqlNan.sql";

    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        this.environment = environment;
        init(this.environment, REPORT_HEADER, REPORT_TITLE);

    }

    @Test
    public void etlValidation() throws Exception {
        RecordLevel recordLevel = new RecordLevel();
        TableLevel tableLevel = new TableLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);

        Map<String, DestinationTable> validate = new HashMap<>();

        //sql nan                            Column_name, Unique Key, Type of field, validation
        DestinationTable creativeID = new DestinationTable("Creative_ID", true, INT, MATCH);
        DestinationTable messageID = new DestinationTable("Message_ID", false, INT, MATCH);
        DestinationTable creativeName = new DestinationTable("Creative_Name", false, VARCHAR, MATCH);
        DestinationTable creativeType = new DestinationTable("Creative_Type", false, VARCHAR, MATCH);
        DestinationTable creativeStatus = new DestinationTable("Creative_Status", false, VARCHAR, MATCH);
        DestinationTable advertiserID = new DestinationTable("Advertiser_ID", false, INT, MATCH);
        DestinationTable creativeSizeID = new DestinationTable("Creative_Size_ID", false, INT, MATCH);
        DestinationTable auditStatus = new DestinationTable("Audit_Status", false, VARCHAR, MATCH);
        DestinationTable adserverPlacementName = new DestinationTable("Adserver_Placement_Name", false, VARCHAR, MATCH);
        DestinationTable adserverPlacementId = new DestinationTable("Adserver_Placement_ID", false, VARCHAR, MATCH);
        DestinationTable creativeMessageName = new DestinationTable("Creative_Message_Name", false, VARCHAR, MATCH);

        // Postgress
        validate.put("creative_id", creativeID);
        validate.put("message_id", messageID);
        validate.put("creative_name", creativeName);
        validate.put("creative_type", creativeType);
        validate.put("creative_status", creativeStatus);
        validate.put("advertiser_id", advertiserID);
        validate.put("creative_size_id", creativeSizeID);
        validate.put("audit_status", auditStatus);
        validate.put("adserver_placement_name", adserverPlacementName);
        validate.put("adserver_placement_id", adserverPlacementId);
        validate.put("creative_name", creativeMessageName);

        String srcColumnNames="creative_id,message_id,creative_name,creative_type,creative_status,advertiser_id,creative_size_id,audit_status,adserver_placement_name,adserver_placement_id,creative_name";
        String destColumnNames="Creative_ID,Message_ID,Creative_Name,Creative_Type,Creative_Status,Advertiser_ID,Creative_Size_ID,Audit_Status,Adserver_Placement_Name,Adserver_Placement_ID,Creative_Message_Name";


        tableLevel.verifyTableCount(environment, srcSqlFile, SOURCE_TABLE, destSqlFile, DESTINATION_TABLE);
        extentReportUtil.endTest();

        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        tableLevel.verifyTableDataTypes(environment, validate, srcSqlFile, SOURCE_TABLE, destSqlFile, DESTINATION_TABLE);

        extentReportUtil.endTest();

        extentReportUtil.startTest("Record level tests <BR> Verify Data <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        recordLevel.verifyTableData(environment, validate, srcSqlFile, SOURCE_TABLE, srcColumnNames, destSqlFile, DESTINATION_TABLE,destColumnNames);

    }

    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}

