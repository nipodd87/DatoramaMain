package com.ignitionone.datastorm.datorama;

/**
 * Created by nitin.poddar on 1/5/2017.
 */

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
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;

/**
 * Created by ravi.peddi on 1/3/2017.
 */
public class AdvertiserETLTest extends BaseClass {
    //sql postgres
    private String SOURCE_TABLE = "advertisers";
    //sql nan
    private String DESTINATION_TABLE = "Display2_Advertisers";

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
        DestinationTable advertiserID = new DestinationTable("Advertiser_ID", true, INT, MATCH);
        DestinationTable legacyID = new DestinationTable("Legacy_ID", false, INT, MATCH);
        DestinationTable sourceID = new DestinationTable("Source_ID", false, INT, MATCH);

        // Postgress
        validate.put("advertiser_id", advertiserID);
        validate.put("legacy_id", legacyID);
        validate.put("source_id", sourceID);

        String srcColumnNames="advertiser_id,legacy_id,source_id";
        String destColumnNames="Advertiser_ID,Legacy_ID,Source_ID";


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


