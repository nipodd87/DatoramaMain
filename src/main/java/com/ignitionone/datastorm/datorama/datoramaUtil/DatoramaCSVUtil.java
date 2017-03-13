
package com.ignitionone.datastorm.datorama.datoramaUtil;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.ignitionone.datastorm.datorama.model.CompanyStoreBean;
import com.ignitionone.datastorm.datorama.model.ConversionMetrics;
import com.ignitionone.datastorm.datorama.model.CreativeDeliveryBean;
import com.ignitionone.datastorm.datorama.model.DeliveryMetrics;
import com.ignitionone.datastorm.datorama.model.CreativeConversionBean;
import com.ignitionone.datastorm.datorama.model.TraitDeliveryBean;
import com.ignitionone.datastorm.datorama.model.TraitConversionBean;
import com.ignitionone.datastorm.datorama.model.DomainConversionBean;
import com.ignitionone.datastorm.datorama.model.DomainDeliveryBean;


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
        List<CreativeDeliveryBean> creativeDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));

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
    public static List<String> getTraitDeliveryCSVData(String fileName, char separator, String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<TraitDeliveryBean> mapper = new ColumnPositionMappingStrategy<TraitDeliveryBean>();
        mapper.setType(TraitDeliveryBean.class);
        String[] columns = new String[]{"Date", "BUID", "CampaignID", "CampaignName", "CampaignFlightdateStart", "CampaignFlightdateEnd", "AccountManagerID", "CampaignStatus",
                "AdvertiserSourceID", "AdvertiserSourceName", "CampaignTargetID", "CampaignTargetName", "CampaignTargetFlightdateStart", "CampaignTargetFlightdateEnd", "CampaignTargetStatus", "TraitID",
                "TraitName","IntegrationID", "IntegrationName", "CurrencyCode", "Impressions", "Clicks", "Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<TraitDeliveryBean> csv = new CsvToBean<TraitDeliveryBean>();
        List<TraitDeliveryBean> traitDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));

        List<String> traitDeliveryModifiedList = new ArrayList<String>();
        for (int i = 0; i < traitDeliveryList.size(); i++) {
            //Get csv data
            StringBuffer lineItem = new StringBuffer();
            lineItem.append(traitDeliveryList.get(i).getDate());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getBuId());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignId());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignName());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getAccountManagerId());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignStatus());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getAdvertiserSourceId());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getAdvertiserSourceName());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignTargetId());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignTargetName());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignTargetFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignTargetFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCampaignTargetStatus());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getTraitId());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getTraitName());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getIntegrationId());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getIntegrationName());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCurrencyCode());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getImpressions());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getClicks());
            lineItem.append(delimiter);
            lineItem.append(traitDeliveryList.get(i).getCost());

            traitDeliveryModifiedList.add(lineItem.toString());
        }
        return traitDeliveryModifiedList;
    }

    public static DeliveryMetrics getTraitDeliveryMeasurementTotal(String fileName, char separator) throws FileNotFoundException {
        DeliveryMetrics metrics = new DeliveryMetrics();
        long totalImpression = 0;
        long totalClicks = 0;
        double totalCost = 0;
        int recordCount = 0;
        ColumnPositionMappingStrategy<TraitDeliveryBean> mapper = new ColumnPositionMappingStrategy<TraitDeliveryBean>();
        mapper.setType(TraitDeliveryBean.class);
        String[] columns = new String[]{"Date", "BUID", "CampaignID", "CampaignName", "CampaignFlightdateStart", "CampaignFlightdateEnd", "AccountManagerID", "CampaignStatus",
                "AdvertiserSourceID", "AdvertiserSourceName", "CampaignTargetID", "CampaignTargetName", "CampaignTargetFlightdateStart", "CampaignTargetFlightdateEnd", "CampaignTargetStatus", "TraitID",
                "TraitName","IntegrationID", "IntegrationName", "CurrencyCode", "Impressions", "Clicks", "Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<TraitDeliveryBean> csv = new CsvToBean<TraitDeliveryBean>();
        List<TraitDeliveryBean> traitDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        mapper.getColumnMapping();
        for (int i = 1; i < traitDeliveryList.size(); i++) {
            totalImpression = totalImpression + Integer.parseInt(traitDeliveryList.get(i).getImpressions());
            totalClicks = totalClicks + Integer.parseInt(traitDeliveryList.get(i).getClicks());
            totalCost = totalCost + Double.parseDouble(traitDeliveryList.get(i).getCost());
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


    public static List<String> getCompanyStoreCSVData(String fileName, char separator,String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<CompanyStoreBean> mapper = new ColumnPositionMappingStrategy<CompanyStoreBean>();
        mapper.setType(CompanyStoreBean.class);
        String[] columns = new String[]{"BUID", "BUName", "CompanyID", "CompanyName", "AgencyID", "AgencyName",
                "DivisionID", "DivisionName", "RegionID", "RegionName", "TimeZoneName"};
        mapper.setColumnMapping(columns);

        CsvToBean<CompanyStoreBean> csv = new CsvToBean<CompanyStoreBean>();
        List<CompanyStoreBean> companyStoreList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));

        List<String> companyStoreModifiedList = new ArrayList<String>();
        for (int i = 0; i < companyStoreList.size(); i++) {
            //Get csv data
            StringBuffer lineItem = new StringBuffer();
            lineItem.append(companyStoreList.get(i).getBuId());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getBuName());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getCompanyId());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getCompanyName());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getAgencyId());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getAgencyName());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getDivisionId());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getDivisionName());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getRegionId());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getRegionName());
            lineItem.append(delimiter);
            lineItem.append(companyStoreList.get(i).getTimeZoneName());

            companyStoreModifiedList.add(lineItem.toString());
    }
        return companyStoreModifiedList;
    }

    public static int getCompanyStoreMeasurementTotal(String fileName, char separator) throws FileNotFoundException {
        int recordCount = 0;
        ColumnPositionMappingStrategy<CompanyStoreBean> mapper = new ColumnPositionMappingStrategy<CompanyStoreBean>();
        mapper.setType(CompanyStoreBean.class);
        String[] columns = new String[]{"BUID", "BUName", "CompanyID", "CompanyName", "AgencyID", "AgencyName",
                "DivisionID", "DivisionName", "RegionID", "RegionName", "TimeZoneName"};
        mapper.setColumnMapping(columns);

        CsvToBean<CompanyStoreBean> csv = new CsvToBean<CompanyStoreBean>();
        List<CompanyStoreBean> companyStoreList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        for (int i = 1; i < companyStoreList.size(); i++) {
            recordCount++;
        }
        return recordCount;
    }

    public static List<String> getTraitConversionCSVData(String fileName, char separator, String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<TraitConversionBean> mapper = new ColumnPositionMappingStrategy<TraitConversionBean>();
        mapper.setType(TraitConversionBean.class);
        String[] columns = new String[]{"Date","BUID","CampaignID","CampaignName","CampaignFlightdateStart","CampaignFlightdateEnd","AccountManagerID",
                "CampaignStatus","AdvertiserSourceID","AdvertiserSourceName","CampaignTargetID","CampaignTargetName","CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd","CampaignTargetStatus","TraitID","TraitName","IntegrationID","IntegrationName","CurrencyCode","ClickBasedConversions","ImpressionBasedConversions"};
        mapper.setColumnMapping(columns);

        CsvToBean<TraitConversionBean> csv = new CsvToBean<TraitConversionBean>();
        List<TraitConversionBean> traitConversionList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        List<String> traitConversionModifiedList = new ArrayList<String>();
        for (int i = 0; i < traitConversionList.size(); i++) {
            //Get csv data
            StringBuffer lineItem = new StringBuffer();
            lineItem.append(traitConversionList.get(i).getDate());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getBuId());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignId());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignName());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getAccountManagerId());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignStatus());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getAdvertiserSourceId());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getAdvertiserSourceName());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignTargetId());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignTargetName());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignTargetFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignTargetFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCampaignTargetStatus());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getTraitId());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getTraitName());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getIntegrationId());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getIntegrationName());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getCurrencyCode());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getClickBasedConversions());
            lineItem.append(delimiter);
            lineItem.append(traitConversionList.get(i).getImpressionBasedConversions());

            traitConversionModifiedList.add(lineItem.toString());
        }
        return traitConversionModifiedList;
    }

    public static ConversionMetrics getTraitConversionMeasurementTotal(String fileName, char separator) throws FileNotFoundException {
        ConversionMetrics metrics = new ConversionMetrics();
        int total_click_based_conversion = 0;
        int total_view_based_conversion = 0;
        int recordCount = 0;
        
        ColumnPositionMappingStrategy<TraitConversionBean> mapper = new ColumnPositionMappingStrategy<TraitConversionBean>();
        mapper.setType(TraitConversionBean.class);
        String[] columns = new String[]{"Date", "BUID", "CampaignID", "CampaignName", "CampaignFlightdateStart", "CampaignFlightdateEnd", "AccountManagerID",
                "CampaignStatus", "AdvertiserSourceID", "AdvertiserSourceName", "CampaignTargetID", "CampaignTargetName", "CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd", "CampaignTargetStatus", "TraitID", "TraitName", "IntegrationID", "IntegrationName", "CurrencyCode", "ClickBasedConversions", "ImpressionBasedConversions"};
        mapper.setColumnMapping(columns);

        CsvToBean<TraitConversionBean> csv = new CsvToBean<TraitConversionBean>();
        List<TraitConversionBean> traitConversionList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        List<String> traitConversionModifiedList = new ArrayList<String>();
        for (int i = 1; i <traitConversionList.size(); i++) {
            total_click_based_conversion = total_click_based_conversion + Integer.parseInt(traitConversionList.get(i).getClickBasedConversions());
            total_view_based_conversion = total_view_based_conversion + Integer.parseInt(traitConversionList.get(i).getImpressionBasedConversions());
            recordCount++;
        }
        metrics.setTotalClickBasedConversion(total_click_based_conversion);
        metrics.setTotalViewBasedConversion(total_view_based_conversion);
        metrics.setRecordCount(recordCount);
        return metrics;
    }

    public static List<String> getDomainConversionCSVData(String fileName, char separator, String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<DomainConversionBean> mapper = new ColumnPositionMappingStrategy<DomainConversionBean>();
        mapper.setType(DomainConversionBean.class);
        String[] columns = new String[]{"Date","BUID","CampaignID","CampaignName","CampaignFlightdateStart","CampaignFlightdateEnd","AccountManagerID",
                "CampaignStatus","AdvertiserSourceID","AdvertiserSourceName","CampaignTargetID","CampaignTargetName","CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd","CampaignTargetStatus","CreativeID","CreativeName","CreativeMessageID","CreativeMessageName","PlacementPixelSize",
                "AdserverPlacementID","AdserverPlacementName","IntegrationID","IntegrationName","CurrencyCode","SiteURL","ClickBasedConversions","ImpressionBasedConversions"};
        mapper.setColumnMapping(columns);

        CsvToBean<DomainConversionBean> csv = new CsvToBean<DomainConversionBean>();
        List<DomainConversionBean> domainConversionList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        List<String> domainConversionModifiedList = new ArrayList<String>();
        for (int i = 0; i < domainConversionList.size(); i++) {
            //Get csv data
            StringBuffer lineItem = new StringBuffer();
            lineItem.append(domainConversionList.get(i).getDate());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getBuId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignName());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getAccountManagerId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignStatus());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getAdvertiserSourceId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getAdvertiserSourceName());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignTargetId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignTargetName());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignTargetFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignTargetFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCampaignTargetStatus());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCreativeId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCreativeName());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCreativeMessageId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCreativeMessageName());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getPlacementPixelSize());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getAdserverPlacementId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getAdserverPlacementName());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getIntegrationId());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getIntegrationName());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getCurrencyCode());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getSiteUrl());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getClickBasedConversions());
            lineItem.append(delimiter);
            lineItem.append(domainConversionList.get(i).getImpressionBasedConversions());

            domainConversionModifiedList.add(lineItem.toString());
        }
        return domainConversionModifiedList;
    }

    public static ConversionMetrics getDomainConversionMeasurementTotal(String fileName, char separator) throws FileNotFoundException {
        ConversionMetrics metrics = new ConversionMetrics();
        int total_click_based_conversion = 0;
        int total_view_based_conversion = 0;
        int recordCount = 0;

        ColumnPositionMappingStrategy<DomainConversionBean> mapper = new ColumnPositionMappingStrategy<DomainConversionBean>();
        mapper.setType(DomainConversionBean.class);
        String[] columns = new String[]{"Date","BUID","CampaignID","CampaignName","CampaignFlightdateStart","CampaignFlightdateEnd","AccountManagerID",
                "CampaignStatus","AdvertiserSourceID","AdvertiserSourceName","CampaignTargetID","CampaignTargetName","CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd","CampaignTargetStatus","CreativeID","CreativeName","CreativeMessageID","CreativeMessageName","PlacementPixelSize",
                "AdserverPlacementID","AdserverPlacementName","IntegrationID","IntegrationName","CurrencyCode","SiteURL","ClickBasedConversions","ImpressionBasedConversions"};
        mapper.setColumnMapping(columns);

        CsvToBean<DomainConversionBean> csv = new CsvToBean<DomainConversionBean>();
        List<DomainConversionBean> domainConversionList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        for (int i = 1; i <domainConversionList.size(); i++) {
            total_click_based_conversion = total_click_based_conversion + Integer.parseInt(domainConversionList.get(i).getClickBasedConversions());
            total_view_based_conversion = total_view_based_conversion + Integer.parseInt(domainConversionList.get(i).getImpressionBasedConversions());
            recordCount++;
        }
        metrics.setTotalClickBasedConversion(total_click_based_conversion);
        metrics.setTotalViewBasedConversion(total_view_based_conversion);
        metrics.setRecordCount(recordCount);
        return metrics;
    }


    public static List<String> getDomainDeliveryCSVData(String fileName, char separator, String delimiter) throws FileNotFoundException {
        ColumnPositionMappingStrategy<DomainDeliveryBean> mapper = new ColumnPositionMappingStrategy<DomainDeliveryBean>();
        mapper.setType(DomainDeliveryBean.class);
        String[] columns = new String[]{"Date","BUID","CampaignID","CampaignName","CampaignFlightdateStart","CampaignFlightdateEnd","AccountManagerID",
                "CampaignStatus","AdvertiserSourceID","AdvertiserSourceName","CampaignTargetID","CampaignTargetName","CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd","CampaignTargetStatus","CreativeID","CreativeName","CreativeMessageID","CreativeMessageName","CreativeSize",
                "AdserverPlacementID","AdserverPlacementName","IntegrationID","IntegrationName","CurrencyCode","SiteURL","Impressions","Clicks","Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<DomainDeliveryBean> csv = new CsvToBean<DomainDeliveryBean>();
        List<DomainDeliveryBean> domainDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));

        List<String> domainDeliveryModifiedList = new ArrayList<String>();
        for (int i = 0; i < domainDeliveryList.size(); i++) {
            //Get csv data
            StringBuffer lineItem = new StringBuffer();
            lineItem.append(domainDeliveryList.get(i).getDate());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getBuId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignName());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getAccountManagerId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignStatus());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getAdvertiserSourceId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getAdvertiserSourceName());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignTargetId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignTargetName());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignTargetFlightDateStart());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignTargetFlightDateEnd());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCampaignTargetStatus());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCreativeId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCreativeName());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCreativeMessageId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCreativeMessageName());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCreativeSize());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getAdserverPlacementId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getAdserverPlacementName());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCreativeSize());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getIntegrationId());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getIntegrationName());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCurrencyCode());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getSiteUrl());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getImpressions());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getClicks());
            lineItem.append(delimiter);
            lineItem.append(domainDeliveryList.get(i).getCost());

            domainDeliveryModifiedList.add(lineItem.toString());
        }
        return domainDeliveryModifiedList;
    }

    public static DeliveryMetrics getDomainDeliveryMeasurementTotal(String fileName, char separator) throws FileNotFoundException {
        DeliveryMetrics metrics = new DeliveryMetrics();
        long totalImpression = 0;
        long totalClicks = 0;
        double totalCost = 0;
        int recordCount = 0;
        ColumnPositionMappingStrategy<DomainDeliveryBean> mapper = new ColumnPositionMappingStrategy<DomainDeliveryBean>();
        mapper.setType(DomainDeliveryBean.class);
        String[] columns = new String[]{"Date","BUID","CampaignID","CampaignName","CampaignFlightdateStart","CampaignFlightdateEnd","AccountManagerID",
                "CampaignStatus","AdvertiserSourceID","AdvertiserSourceName","CampaignTargetID","CampaignTargetName","CampaignTargetFlightdateStart",
                "CampaignTargetFlightdateEnd","CampaignTargetStatus","CreativeID","CreativeName","CreativeMessageID","CreativeMessageName","CreativeSize",
                "AdserverPlacementID","AdserverPlacementName","IntegrationID","IntegrationName","CurrencyCode","SiteURL","Impressions","Clicks","Cost"};
        mapper.setColumnMapping(columns);

        CsvToBean<DomainDeliveryBean> csv = new CsvToBean<DomainDeliveryBean>();
        List<DomainDeliveryBean> domainDeliveryList = csv.parse(mapper, new CSVReader(new FileReader(System.getProperty("user.dir") + "/" + fileName), separator));
        mapper.getColumnMapping();
        for (int i = 1; i < domainDeliveryList.size(); i++) {
            totalImpression = totalImpression + Integer.parseInt(domainDeliveryList.get(i).getImpressions());
            totalClicks = totalClicks + Integer.parseInt(domainDeliveryList.get(i).getClicks());
            totalCost = totalCost + Double.parseDouble(domainDeliveryList.get(i).getCost());
            recordCount++;
        }
        metrics.setTotalImpressions(totalImpression);
        metrics.setTotalClicks(totalClicks);
        metrics.setTotalCost(Math.floor(totalCost * 1000) / 1000);
        metrics.setRecordCount(recordCount);
        return metrics;
    }
}
