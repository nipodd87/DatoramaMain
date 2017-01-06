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
import static com.ignitionone.datastorm.datorama.etl.DataType.VARCHAR;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;

public class CampaignTargetBudgetCapETLTest extends BaseClass {
    //sql postgres
    private String SOURCE_TABLE = "campaign_target_budget_caps";
    //sql nan
    private String DESTINATION_TABLE = "Display2_Campaign_Targets_Budget_Caps";

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
        DestinationTable campaignTargetBudgetCapID = new DestinationTable("Campaign_Target_Budget_Cap_ID", true, INT, MATCH);
        DestinationTable campaignTargetID = new DestinationTable("Campaign_Target_ID", false, INT, MATCH);
        DestinationTable campaignTargetAmount = new DestinationTable("Campaign_Target_Amount", false, INT, MATCH);
        DestinationTable campaignTargetBudgetType = new DestinationTable("Campaign_Target_Budget_Type", false, VARCHAR, MATCH);
        DestinationTable budgetPeriod = new DestinationTable("Budget_Period", false, VARCHAR, MATCH);


        // Postgress
        validate.put("campaign_target_budget_cap_id", campaignTargetBudgetCapID);
        validate.put("campaign_target_id", campaignTargetID);
        validate.put("amount", campaignTargetAmount);
        validate.put("budget_type", campaignTargetBudgetType);
        validate.put("budget_period", budgetPeriod);

        String srcColumnNames="campaign_target_budget_cap_id,campaign_target_id,amount,budget_type,budget_period";
        String destColumnNames="Campaign_Target_Budget_Cap_ID,Campaign_Target_ID,Campaign_Target_Amount,Campaign_Target_Budget_Type,Budget_Period";


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
