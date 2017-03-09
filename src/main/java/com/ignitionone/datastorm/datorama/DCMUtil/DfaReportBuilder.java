package com.ignitionone.datastorm.datorama.DCMUtil;

import com.google.api.client.util.DateTime;
import com.google.api.services.dfareporting.model.DateRange;
import com.google.api.services.dfareporting.model.DimensionValue;
import com.google.api.services.dfareporting.model.Report;
import com.google.api.services.dfareporting.model.SortedDimension;
import com.ignitionone.datastorm.datorama.util.ExcelReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nitin.poddar on 3/8/2017.
 */
public class DfaReportBuilder {

    public static Report report = new Report();
    ExcelReader reader = new ExcelReader();

    public Report getDfaReport(String startDate, String endDate, String reportName, String dimensionFilterName, String dimensionFilterValue, String filePath) throws Exception {

        //Add List of Dimension
        List<SortedDimension> dimensionList = getDimensionList(filePath, "Dimension");
        List<String> metricsList = reader.getExcelasList(filePath, "Metrics");

        //Set the Dimensions Filter for the report
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setDimensionName(dimensionFilterName);
        dimensionValue.setValue(dimensionFilterValue);

        //Add Dimension Filter to an ArrayList
        List<DimensionValue> dimensionFilterList = new ArrayList<>();
        dimensionFilterList.add(dimensionValue);

        //Set the date range
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(new DateTime(startDate));
        dateRange.setEndDate(new DateTime(endDate));

        //Create Report Criteria
        Report.Criteria criteria = new Report.Criteria();
        criteria.setDateRange(dateRange);
        criteria.setDimensions(dimensionList);
        criteria.setMetricNames(metricsList);
        criteria.setDimensionFilters(dimensionFilterList);

        //Set Report Required Parameters
        report.setName(reportName);
        report.setCriteria(criteria);
        report.setType("STANDARD");
        report.setFormat("CSV");
        return report;
    }

    public List<SortedDimension> getDimensionList(String filePath, String sheetName) throws Exception {

        List<SortedDimension> dimensionList = new ArrayList<SortedDimension>();
        List<String> dimensions = reader.getExcelasList(filePath, sheetName);

        for (String dimension: dimensions){
            SortedDimension sortedDimension = new SortedDimension();
            sortedDimension.setName(dimension);
            dimensionList.add(sortedDimension);
        }
        return dimensionList;
    }
}
