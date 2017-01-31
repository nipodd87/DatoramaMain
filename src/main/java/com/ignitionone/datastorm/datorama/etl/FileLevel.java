package com.ignitionone.datastorm.datorama.etl;

import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ETLUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ravi.peddi on 1/6/2017.
 */
public class FileLevel {

    ETLUtil etlUtil = new ETLUtil();

    public void verifyTableCount(List<String> srcData, String srcInfoForReporting, List<String> destData, String destInfoForReporting) throws Exception {
        Integer getSrcCount = srcData.size();
        Integer getDestCount = destData.size();

        CommonUtil.compareNumbers(getSrcCount, getDestCount, "Verify Count of records between " + srcInfoForReporting + " and " + destInfoForReporting, "Verify Count of records between " + srcInfoForReporting + " and " + destInfoForReporting);

    }

    public void verifyTableDataTypes(Map<String, DestinationTable> mapSet, List<String> srcData, List<String> destData) throws Exception {
        Map<String, String> mappingDoc = new HashMap<>();
        for (String key : mapSet.keySet()) {
            mappingDoc.put(key.toString(), mapSet.get(key).getColumnName());
        }

        etlUtil.verifySrcDestination(mappingDoc, srcData, destData);
    }


}

