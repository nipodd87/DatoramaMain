package com.ignitionone.datastorm.datorama.etl;

import com.ignitionone.datastorm.datorama.sqlUtil.ExecuteSqlUtil;
import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ETLUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ravi.peddi on 12/28/2016.
 */
public class TableLevel {


    private  String GET_COLUMN_INFO = "getColumnInfo";
    private  String GET_COUNT = "getCount";


    ExecuteSqlUtil sqlUtil = new ExecuteSqlUtil();
    ETLUtil etlUtil = new ETLUtil();

    public void verifyTableCount(String environment,String srcSqlFile,String srcTable, String destSqlFile, String destTable) throws Exception {
        Integer getCountTable1 = sqlUtil.countOfRows(srcSqlFile, environment, GET_COUNT, "<<tableName>>", srcTable);
        Integer getCountTable2 = sqlUtil.countOfRows(destSqlFile, environment, GET_COUNT, "<<tableName>>", destTable);

        CommonUtil.compareNumbers(getCountTable1, getCountTable2, "Verify Count of records between " + srcTable + " and " + destTable, "Verify Count of records between " + srcTable + " and " + destTable);

    }

    public void verifyTableDataTypes(String environment,Map<String, DestinationTable> mapSet, String srcSqlFile, String srcTable, String destSqlFile, String destTable) throws Exception {
        Map<String, String> mappingDoc = new HashMap<>();
        for (String key : mapSet.keySet()) {
            mappingDoc.put(key.toString(), mapSet.get(key).getColumnName());
        }

        List<String> getColumnsSrc = sqlUtil.getRows(srcSqlFile, environment, GET_COLUMN_INFO, "<<tableName>>", srcTable);
        List<String> getColumnsDist = sqlUtil.getRows(destSqlFile, environment, GET_COLUMN_INFO, "<<tableName>>", destTable);

        etlUtil.verifySrcDestination(mappingDoc, getColumnsSrc, getColumnsDist);
    }




}

