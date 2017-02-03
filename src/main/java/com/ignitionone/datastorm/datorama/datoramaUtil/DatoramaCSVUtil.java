
package com.ignitionone.datastorm.datorama.datoramaUtil;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import model.CreativeDeliveryBean;
import model.DeliveryMetrics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nitin.poddar on 2/2/2017.
 */

public class DatoramaCSVUtil {

    public List<String> getCreativeDeliveryCSVData(String fileName, char separator, String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<CreativeDeliveryBean> mapper = new ColumnPositionMappingStrategy<CreativeDeliveryBean>();
        mapper.setType(CreativeDeliveryBean.class);
        String[] columns = new String[]{"Date", "BU_ID", "Campaign_ID", "Campaign_Name", "Campaign_Flightdate_Start", "Campaign_Flightdate_End", "Account_Manager_ID", "Campaign_Status",
                "Advertiser_Source_ID", "Advertiser_Source_Name", "Campaign_Target_ID", "Campaign_Target_Name", "Campaign_Target_Flightdate_Start", "Campaign_Target_Flightdate_End", "Campaign_Target_Status", "Creative_ID",
                "Creative_Name", "Creative_Message_ID", "Creative_Message_Name", "Adserver_Placement_ID", "Adserver_Placement_Name", "Integration_ID", "Integration_Name", "Currency_Code", "Impressions", "Clicks", "Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<CreativeDeliveryBean> csv = new CsvToBean<CreativeDeliveryBean>();
        List<CreativeDeliveryBean> creativeDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName + ".csv"), ','));

        List<String> creativeDeliveryModifiedList = new ArrayList<String>();
        for (int i = 0; i < creativeDeliveryList.size(); i++) {
            //Get csv data
            StringBuffer lineItem = new StringBuffer();
            lineItem.append(creativeDeliveryList.get(i).getDate());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getBuId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignName());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getAccountManagerId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignStatus());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getAdvertiserSourceId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getAdvertiserSourceName());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignTargetId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignTargetName());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignTargetFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignTargetFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCampaignTargetStatus());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCreativeId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCreativeName());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCreativeMessageId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCreativeMessageName());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getAdserverPlacementId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getAdserverPlacementName());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getIntegrationId());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getIntegrationName());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCurrencyCode());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getImpressions());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getClicks());
            lineItem.append(delimiter);
            lineItem.append(creativeDeliveryList.get(i).getCost());

            creativeDeliveryModifiedList.add(lineItem.toString());
        }
        return creativeDeliveryModifiedList;
    }

    public static DeliveryMetrics getCreativeDeliveryMeasurementTotal(String fileName, char separator) throws FileNotFoundException {
        DeliveryMetrics metrics = new DeliveryMetrics();
        long totalImpression = 0;
        long totalClicks = 0;
        double totalCost = 0;
        int recordCount = 0;
        ColumnPositionMappingStrategy<CreativeDeliveryBean> mapper = new ColumnPositionMappingStrategy<CreativeDeliveryBean>();
        mapper.setType(CreativeDeliveryBean.class);
        String[] columns = new String[]{"Date", "BU_ID", "Campaign_ID", "Campaign_Name", "Campaign_Flightdate_Start", "Campaign_Flightdate_End", "Account_Manager_ID", "Campaign_Status",
                "Advertiser_Source_ID", "Advertiser_Source_Name", "Campaign_Target_ID", "Campaign_Target_Name", "Campaign_Target_Flightdate_Start", "Campaign_Target_Flightdate_End", "Campaign_Target_Status", "Creative_ID",
                "Creative_Name", "Creative_Message_ID", "Creative_Message_Name", "Adserver_Placement_ID", "Adserver_Placement_Name", "Integration_ID", "Integration_Name", "Currency_Code", "Impressions", "Clicks", "Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<CreativeDeliveryBean> csv = new CsvToBean<CreativeDeliveryBean>();
        List<CreativeDeliveryBean> creativeDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        for (int i = 1; i < creativeDeliveryList.size(); i++) {
            totalImpression = totalImpression + Integer.parseInt(creativeDeliveryList.get(i).getImpressions());
            totalClicks = totalClicks + Integer.parseInt(creativeDeliveryList.get(i).getClicks());
            totalCost = totalCost + Double.parseDouble(creativeDeliveryList.get(i).getCost());
            recordCount++;
        }
        metrics.setTotalImpressions(totalImpression);
        metrics.setTotalClicks(totalClicks);
        metrics.setTotalCost(Math.floor(totalCost * 1000) / 1000);
        metrics.setRecordCount(recordCount);
        return metrics;
    }
}
