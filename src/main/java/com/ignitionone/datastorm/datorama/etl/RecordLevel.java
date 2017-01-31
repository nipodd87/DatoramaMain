package com.ignitionone.datastorm.datorama.etl;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.ignitionone.datastorm.datorama.sqlUtil.ExecuteSqlUtil;
import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ETLUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ravi.peddi on 12/28/2016.
 */
public class RecordLevel {
    private String SQL_QUERY_NAME = "getrows";
    ExecuteSqlUtil execSqlUtil = new ExecuteSqlUtil();

    ETLUtil etlUtil = new ETLUtil();


    public void verifyTableData(String environment, Map<String, DestinationTable> mapSet, String srcSqlFile, String srcTable, String srcColumnNames, String destSqlFile, String destTable, String destColumnNames) throws Exception {
        verifyTableData(SQL_QUERY_NAME, environment, mapSet, srcSqlFile, srcTable, srcColumnNames, destSqlFile, destTable, destColumnNames);
    }

    public void verifyTableData(String sqlQueryName, String environment, Map<String, DestinationTable> mapSet, String srcSqlFile, String srcTable, String srcColumnNames, String destSqlFile, String destTable, String destColumnNames) throws Exception {

        List<String> getColumnsSrc = execSqlUtil.getRows(srcSqlFile, environment, SQL_QUERY_NAME, "<<tableName>>", srcTable, "<<columnName>>", srcColumnNames);
        List<String> getColumnsDist = execSqlUtil.getRows(destSqlFile, environment, SQL_QUERY_NAME, "<<tableName>>", destTable, "<<columnName>>", destColumnNames);

        etlUtil.verifySrcDestinationData(mapSet, getColumnsSrc, getColumnsDist);
    }

    public void verifySrcWithDestData(Map<String, DestinationTable> mapSet, List<String> srcData, List<String> destData) throws Exception {
        etlUtil.verifySrcDestinationData(mapSet, srcData, destData);
    }

}

