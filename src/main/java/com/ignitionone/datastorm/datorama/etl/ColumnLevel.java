package com.ignitionone.datastorm.datorama.etl;

import com.ignitionone.datastorm.datorama.sqlUtil.ExecuteSqlUtil;
import com.ignitionone.datastorm.datorama.util.ETLUtil;
import com.ignitionone.datastorm.datorama.util.ExcelReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ravi.peddi on 12/28/2016.
 */
public class ColumnLevel {
    private String SQL_QUERY_NAME = "getrows";
    ExecuteSqlUtil execSqlUtil = new ExecuteSqlUtil();
    ExcelReader excelReader = new ExcelReader();
    ETLUtil etlUtil = new ETLUtil();


    public void verifyDataBetweenTwoTables(String environment, String inputTestDataExcelFileName, String inputTestDataExcelSheetName, Map<String, DestinationTable> mapSet, String srcSqlFile, String expectedDataExcelSheetName, String srcTable, String destSqlFile, String destTable) throws Exception {
        List<String> testData = excelReader.getExcelasList(inputTestDataExcelFileName, inputTestDataExcelSheetName);
  //      execSqlUtil.insertRecords(srcSqlFile, environment, srcTable, testData);
        //call store proc
        List<String> expectedData = excelReader.getExcelasList(inputTestDataExcelFileName, expectedDataExcelSheetName);
        List<String> getColumnsDist = execSqlUtil.getRows(destSqlFile, environment, SQL_QUERY_NAME, "<<tableName>>", destTable, "<<columnName>>", expectedData.get(0));
        Map<String, DestinationTable> modifiedMap = new HashMap<>();
        for ( String key : mapSet.keySet() ) {
            modifiedMap.put(mapSet.get(key).getColumnName(),mapSet.get(key));
        }
        etlUtil.verifySrcDestinationData(modifiedMap, expectedData, getColumnsDist);
    }


}

