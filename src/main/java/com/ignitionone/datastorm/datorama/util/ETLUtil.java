package com.ignitionone.datastorm.datorama.util;

import com.ignitionone.datastorm.datorama.etl.DataType;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.ValidationStyle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ravi.peddi on 1/3/2017.
 */
public class ETLUtil {

    public void verifySrcDestination(Map<String, String> mappingDoc, List<String> srcList, List<String> distList) {
        String[] srcTableColumnsNames = srcList.get(0).split(",");
        String[] distTableColumnsNames = distList.get(0).split(",");
        Boolean foundFlag;
        for (String srcTableColumns : srcList) {
            String[] srcTableColumnsValues = srcTableColumns.split(",");
            foundFlag = false;
            String destTableColumnName = mappingDoc.get(srcTableColumnsValues[0]);
            if (destTableColumnName != null) {
                for (String destColumns : distList) {
                    String[] destTableColumnsValues = destColumns.split(",");
                    if (destTableColumnsValues[0].equals(destTableColumnName)) {
                        VerifySrcDestinationValues(srcTableColumnsNames, srcTableColumnsValues, distTableColumnsNames, destTableColumnsValues);
                        foundFlag = true;
                    }
                }
                if (foundFlag == false) {
                    CommonUtil.logError("Verify destination Column :" + destTableColumnName, "Cannot find destination Column :" + destTableColumnName + " which is mapped to Source Column : " + srcTableColumnsValues[0]);
                }
            }
        }
    }

    public void VerifySrcDestinationValues(String[] srcTableColumnNames, String[] srcTableColumnsValues, String[] destTableColumnsNames, String[] destTableColumnsValues) {
        for (int i = 1; i < srcTableColumnsValues.length; i++) {
            if (srcTableColumnNames[i].toUpperCase().equals("DATA_TYPE")) {
                CommonUtil.verifyTextContains(srcTableColumnsValues[i], destTableColumnsValues[i], "Verify " + srcTableColumnNames[i] + " for " + srcTableColumnsValues[0], "Verify " + srcTableColumnNames[i] + " for " + srcTableColumnsValues[0]);
            } else {
                CommonUtil.compareText(srcTableColumnsValues[i], destTableColumnsValues[i], "Verify " + srcTableColumnNames[i] + " for " + srcTableColumnsValues[0], "Verify " + srcTableColumnNames[i] + " for " + srcTableColumnsValues[0]);
            }
        }
    }


    public void verifySrcDestinationData(Map<String, DestinationTable> mapSet, List<String> srcList, List<String> destList) throws ParseException {
        Boolean foundFlag;
        String[] srcTableColumnsNames = srcList.get(0).split(",");
        String[] distTableColumnsNames = destList.get(0).split(",");

        List<Integer> srcKeyColumns = getKeyColumns(mapSet, srcTableColumnsNames);
        List<Integer> destKeyColumns = getKeyColumns(mapSet, distTableColumnsNames);

        List<String> modifiedSrcList = appendSrcListWithUniqueKeyColumn(srcKeyColumns, srcList);
        List<String> modifiedDestList = appendSrcListWithUniqueKeyColumn(destKeyColumns, destList);
        // List<String> modifiedDestList = appendDestListWithUniqueKeyColumn(destKeyColumns, srcList, destList);

        String[] modifiedSrcTableColumnsNames = modifiedSrcList.get(0).split(",");
        String[] modifiedDistTableColumnsNames = modifiedDestList.get(0).split(",");

        for (int srcCounter = 1; srcCounter < modifiedSrcList.size(); srcCounter++) {
            String[] srcValues = modifiedSrcList.get(srcCounter).split(",");
            foundFlag = false;
            for (int destCounter = 1; destCounter < modifiedDestList.size(); destCounter++) {
                String[] destValues = modifiedDestList.get(destCounter).split(",");
                if (srcValues[0].equals(destValues[0])) {
                    verifySrcDestinationDataBasedOnMapping(mapSet, modifiedSrcTableColumnsNames, modifiedDistTableColumnsNames, srcValues, destValues);
                    foundFlag = true;

                }

            }
            if (foundFlag == false) {
                CommonUtil.logError("Verify destination data for :" + modifiedDistTableColumnsNames, "Cannot find destination Column Names :" + modifiedDistTableColumnsNames + " having source data as : " + srcValues[0]);
            }
        }
    }

    private void verifySrcDestinationDataBasedOnMapping(Map<String, DestinationTable> mapSet, String[] srcTableColumnsNames, String[] distTableColumnsNames, String[] srcValues, String[] destValues) throws ParseException {

        for (int counter = 0; counter < srcTableColumnsNames.length; counter++) {
            Object srcVal = new Object();
            Object destVal = new Object();
            // if (!mapSet.get(key).getUniqueColumn()) {
            String columnUnderTest = srcTableColumnsNames[counter];
            String info = "between source Table :" + columnUnderTest + " and Destination Table :" + distTableColumnsNames[counter];

            if (mapSet.get(columnUnderTest).getDataType().equals(DataType.INT)) {
                srcVal = new Integer(srcValues[counter]);
                destVal = new Integer(destValues[counter]);
                CommonUtil.compareNumberEquals((int) srcVal, (int) destVal, "Verify data " + info, info);
            } else if (mapSet.get(columnUnderTest).getDataType().equals(DataType.DOUBLE)) {
                srcVal = new Double(srcValues[counter]);
                destVal = new Double(destValues[counter]);
                CommonUtil.compareNumberEquals((Double) srcVal, (Double) destVal, "Verify data " + info, info);
            } else if (mapSet.get(columnUnderTest).getDataType().equals(DataType.DATE)) {
                srcVal = srcValues[counter];
                destVal = destValues[counter];
                CommonUtil.compareDateEquals(srcVal.toString(), destVal.toString(), "Verify date " + info, info);
            } else if (mapSet.get(columnUnderTest).getDataType().equals(DataType.VARCHAR)) {
                srcVal = srcValues[counter];
                destVal = destValues[counter];
                if (mapSet.get(columnUnderTest).getValidationStyle().equals(ValidationStyle.MATCH)) {
                    CommonUtil.compareText(srcVal.toString(), destVal.toString(), "Verify data " + info, info);
                } else if (mapSet.get(columnUnderTest).getValidationStyle().equals(ValidationStyle.SUBSTRING)) {
                    CommonUtil.verifyTextContains(srcVal.toString(), destVal.toString(), "Verify data " + info, info);
                }
            }
            // }
        }
    }

    private void verifySrcDestinationDataBasedOnMapping1(Map<String, DestinationTable> mapSet, String[] srcTableColumnsNames, String[] distTableColumnsNames, String[] srcValues, String[] destValues) {
        int counter = 1;
        for (String key : mapSet.keySet()) {
            Object srcVal = new Object();
            Object destVal = new Object();
            // if (!mapSet.get(key).getUniqueColumn()) {
            counter = 1;
            String info = "between source Table :" + srcTableColumnsNames[counter] + " and Destination Table :" + distTableColumnsNames[counter];

            if (mapSet.get(key).getDataType().equals(DataType.INT)) {
                srcVal = new Integer(srcValues[counter]);
                destVal = new Integer(destValues[counter]);
                CommonUtil.compareNumberEquals((int) srcVal, (int) destVal, "Verify data " + info, info);
            } else if (mapSet.get(key).getDataType().equals(DataType.DOUBLE)) {
                srcVal = new Double(srcValues[counter]);
                destVal = new Double(destValues[counter]);
                CommonUtil.compareNumberEquals((Double) srcVal, (Double) destVal, "Verify data " + info, info);
            } else {
                srcVal = srcValues[counter];
                destVal = destValues[counter];
            }
            if (mapSet.get(key).getValidationStyle().equals(ValidationStyle.MATCH)) {
                CommonUtil.compareText(srcVal.toString(), destVal.toString(), "Verify data " + info, info);
            } else if (mapSet.get(key).getValidationStyle().equals(ValidationStyle.SUBSTRING)) {
                CommonUtil.verifyTextContains(srcVal.toString(), destVal.toString(), "Verify data " + info, info);
            }
            // }
            counter++;
        }
    }


    private List<String> appendDestListWithUniqueKeyColumn(List<Integer> keyColumns, List<String> srcList, List<String> destList) {
        List<String> modifiedDestList = new ArrayList<>();

        for (int i = 0; i < destList.size(); i++) {
            String[] destTableColumns = destList.get(i).split(",");
            StringBuffer sb = new StringBuffer();
            for (Integer destColumnId : keyColumns) {
                sb.append(destTableColumns[destColumnId]);
            }
            modifiedDestList.add(sb.toString() + "," + destList.get(i));
        }
        return modifiedDestList;
    }

    private List<Integer> getKeyColumns(Map<String, DestinationTable> mapSet, String[] tableColumnsNames) {
        List<Integer> destColumnIds = new ArrayList<>();
        for (String tableColumnName : tableColumnsNames) {
            Integer columnCounter = new Integer(0);
            for (String key : mapSet.keySet()) {
                if (tableColumnName.toLowerCase().equals(mapSet.get(key).getColumnName().toLowerCase()) && mapSet.get(key).getUniqueColumn()) {
                    destColumnIds.add(columnCounter);
                }
            }
            columnCounter++;
        }
        return destColumnIds;
    }

    private List<String> appendSrcListWithUniqueKeyColumn(List<Integer> keyColymns, List<String> srcList) {

        List<String> modifiedSrcList = new ArrayList<>();

        for (int i = 0; i < srcList.size(); i++) {
            String[] srcTableColumns = srcList.get(i).split(",");
            StringBuffer sb = new StringBuffer();
            for (Integer srcColumnId : keyColymns) {
                sb.append(srcTableColumns[srcColumnId]);
            }
            modifiedSrcList.add(sb.toString() + "," + srcList.get(i));
        }
        return modifiedSrcList;
    }
}
