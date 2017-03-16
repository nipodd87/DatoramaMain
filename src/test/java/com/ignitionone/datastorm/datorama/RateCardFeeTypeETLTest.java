package com.ignitionone.datastorm.datorama;

import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.etl.TableLevel;
import com.ignitionone.datastorm.datorama.util.ETLUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by nitin.poddar on 3/15/2017.
 */
public class RateCardFeeTypeETLTest extends BaseClass {

    //sql postgres
    private String SOURCE_TABLE = "rate_card_fee_types";
    //sql nan
    private String DESTINATION_TABLE = "Display2_Rate_Card_Fee_Types";
    private final String REPORT_HEADER = "File level tests <BR>  Verify Row Count <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    private final String REPORT_TITLE = "Verify Total Row count between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    protected String environment;
    public String srcSqlFile = "sql/postgres.sql";
    public String destSqlFile = "sql/sqlNan.sql";
    public ETLUtil etlUtil = new ETLUtil();

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

        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir")+"/src/test/resources/Data/"+"Rate_Card.xlsx", "Rate_Card_Fee_Type_Mapper");

        String srcColumnNames="type_id,type_name,value_type";
        String destColumnNames="Type_Id,Type_Name,Value_Type";

        tableLevel.verifyTableCount(environment, srcSqlFile, SOURCE_TABLE, destSqlFile, DESTINATION_TABLE);
        extentReportUtil.endTest();

/*        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        tableLevel.verifyTableDataTypes(environment, mapper, srcSqlFile, SOURCE_TABLE, destSqlFile, DESTINATION_TABLE);

        extentReportUtil.endTest();*/

        extentReportUtil.startTest("Record level tests <BR> Verify Data <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        recordLevel.verifyTableData(environment, mapper, srcSqlFile, SOURCE_TABLE, srcColumnNames, destSqlFile, DESTINATION_TABLE,destColumnNames);
    }

    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
