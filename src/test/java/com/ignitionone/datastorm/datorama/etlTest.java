package com.ignitionone.datastorm.datorama;

import com.google.api.services.dfareporting.Dfareporting;
import com.ignitionone.datastorm.datorama.BaseClass;
import com.ignitionone.datastorm.datorama.DCMUtil.DfaReportingFactory;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.etl.TableLevel;
import com.ignitionone.datastorm.datorama.etl.ValidationStyle;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ignitionone.datastorm.datorama.etl.DataType.INT;
import static com.ignitionone.datastorm.datorama.etl.DataType.VARCHAR;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.SUBSTRING;

/**
 * Created by ravi.peddi on 12/28/2016.
 */
public class etlTest extends BaseClass {

    //sql nan
    private String SOURCE_TABLE = "AdGroup_Base";
    //sql postgress
    private String DESTINATION_TABLE = "AdGroup_Base";
    private final String REPORT_HEADER = "File level tests <BR>  Verify Row Count <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    private final String REPORT_TITLE = "Verify Total Row count between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    protected String environment;

    String srcSqlFile = "sql/dmsReadCache.sql";
    //String srcSqlFile = "sql/postgres.sql";

    String destSqlFile = "sql/dmsReadCache.sql";
    //String destSqlFile = "sql/sqlNan.sql";


    @Test
    public void etlValidation() throws Exception {
        Dfareporting reportingFactory = DfaReportingFactory.getInstance();

    }


}
