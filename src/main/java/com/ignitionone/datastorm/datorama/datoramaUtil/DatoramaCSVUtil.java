
package com.ignitionone.datastorm.datorama.datoramaUtil;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.ignitionone.datastorm.datorama.model.ConversionMetrics;
import com.ignitionone.datastorm.datorama.model.CreativeConversionBean;
import com.ignitionone.datastorm.datorama.model.CreativeDeliveryBean;
import com.ignitionone.datastorm.datorama.model.DeliveryMetrics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nitin.poddar on 2/2/2017.
 */

public class DatoramaCSVUtil {

    public static List<String> getCreativeDeliveryCSVData(String fileName, char separator, String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<CreativeDeliveryBean> mapper = new ColumnPositionMappingStrategy<CreativeDeliveryBean>();
        mapper.setType(CreativeDeliveryBean.class);
        String[] columns = new String[]{"Date", "BUID", "CampaignID", "CampaignName", "CampaignFlightdateStart", "CampaignFlightdateEnd", "AccountManagerID", "CampaignStatus",
                "AdvertiserSourceID", "AdvertiserSourceName", "CampaignTargetID", "CampaignTargetName", "CampaignTargetFlightdateStart", "CampaignTargetFlightdateEnd", "CampaignTargetStatus", "CreativeID",
                "CreativeName", "CreativeMessageID", "CreativeMessageName", "AdserverPlacementID", "AdserverPlacementName", "IntegrationID", "IntegrationName", "CurrencyCode", "Impressions", "Clicks", "Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<CreativeDeliveryBean> csv = new CsvToBean<CreativeDeliveryBean>();
        List<CreativeDeliveryBean> creativeDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), ','));

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
        String[] columns = new String[]{"Date", "BUID", "CampaignID", "CampaignName", "CampaignFlightdateStart", "CampaignFlightdateEnd", "AccountManagerID", "CampaignStatus",
                "AdvertiserSourceID", "AdvertiserSourceName", "CampaignTargetID", "CampaignTargetName", "CampaignTargetFlightdateStart", "CampaignTargetFlightdateEnd", "CampaignTargetStatus", "CreativeID",
                "CreativeName", "CreativeMessageID", "CreativeMessageName", "AdserverPlacementID", "AdserverPlacementName", "IntegrationID", "IntegrationName", "CurrencyCode", "Impressions", "Clicks", "Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<CreativeDeliveryBean> csv = new CsvToBean<CreativeDeliveryBean>();
        List<CreativeDeliveryBean> creativeDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        mapper.getColumnMapping();
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

    public static ConversionMetrics getCreativeConversionMeasurementTotal(String fileName, char separator) throws FileNotFoundException {

        ConversionMetrics metrics = new ConversionMetrics();
        int total_click_based_conversion = 0;
        int total_view_based_conversion = 0;
        int recordCount = 0;
        ColumnPositionMappingStrategy<CreativeConversionBean> mapper = new ColumnPositionMappingStrategy<CreativeConversionBean>();
        mapper.setType(CreativeConversionBean.class);
        String[] columns = new String[]{"Date","BUID","CampaignID","CampaignName","CampaignFlightdateStart","CampaignFlightdateEnd","AccountManagerID",
                "CampaignStatus","AdvertiserSourceID","AdvertiserSourceName","CampaignTargetID","CampaignTargetName","CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd","CampaignTargetStatus","CreativeID","CreativeName","CreativeMessageID","CreativeMessageName",
                "AdserverPlacementID","AdserverPlacementName","IntegrationID","IntegrationName","CurrencyCode","ClickBasedConversions","ImpressionBasedConversions"};
        mapper.setColumnMapping(columns);

        CsvToBean<CreativeConversionBean> csv = new CsvToBean<CreativeConversionBean>();
        List<CreativeConversionBean> creativeConversionList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        for (int i = 1; i <creativeConversionList.size(); i++) {
            total_click_based_conversion = total_click_based_conversion + Integer.parseInt(creativeConversionList.get(i).getClickBasedConversions());
            total_view_based_conversion = total_view_based_conversion + Integer.parseInt(creativeConversionList.get(i).getImpressionBasedConversions());
            recordCount++;
        }
        metrics.setTotalClickBasedConversion(total_click_based_conversion);
        metrics.setTotalViewBasedConversion(total_view_based_conversion);
        metrics.setRecordCount(recordCount);
        return metrics;
    }

    public static List<String> getCreativeConversionCSVData(String fileName, char separator, String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<CreativeConversionBean> mapper = new ColumnPositionMappingStrategy<CreativeConversionBean>();
        mapper.setType(CreativeConversionBean.class);
        String[] columns = new String[]{"Date","BUID","CampaignID","CampaignName","CampaignFlightdateStart","CampaignFlightdateEnd","AccountManagerID",
                "CampaignStatus","AdvertiserSourceID","AdvertiserSourceName","CampaignTargetID","CampaignTargetName","CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd","CampaignTargetStatus","CreativeID","CreativeName","CreativeMessageID","CreativeMessageName",
                "AdserverPlacementID","AdserverPlacementName","IntegrationID","IntegrationName","CurrencyCode","ClickBasedConversions","ImpressionBasedConversions"};
        mapper.setColumnMapping(columns);

        CsvToBean<CreativeConversionBean> csv = new CsvToBean<CreativeConversionBean>();
        List<CreativeConversionBean> creativeConversionList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        List<String> creativeConversionModifiedList = new ArrayList<String>();
        for (int i = 0; i < creativeConversionList.size(); i++) {
            //Get csv data
            StringBuffer lineItem = new StringBuffer();
            lineItem.append(creativeConversionList.get(i).getDate());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getBuId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignName());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getAccountManagerId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignStatus());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getAdvertiserSourceId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getAdvertiserSourceName());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignTargetId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignTargetName());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignTargetFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignTargetFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCampaignTargetStatus());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCreativeId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCreativeName());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCreativeMessageId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCreativeMessageName());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getAdserverPlacementId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getAdserverPlacementName());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getIntegrationId());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getIntegrationName());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getCurrencyCode());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getClickBasedConversions());
            lineItem.append(delimiter);
            lineItem.append(creativeConversionList.get(i).getImpressionBasedConversions());

            creativeConversionModifiedList.add(lineItem.toString());
        }
        return creativeConversionModifiedList;
    }
}
