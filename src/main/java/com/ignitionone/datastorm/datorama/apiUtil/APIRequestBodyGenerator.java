package com.ignitionone.datastorm.datorama.apiUtil;

import com.ignitionone.datastorm.datorama.util.Encryption;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by nitin.poddar on 1/13/2017.
 */
public class APIRequestBodyGenerator {

    public static String getAuthRequestBody() throws GeneralSecurityException, IOException {
        String encryptedEmail = APIUtil.getPropertyValue("datorama.qa.email", "datastorm.properties");
        String encryptedPassword = APIUtil.getPropertyValue("datorama.qa.password", "datastorm.properties");
        String email = Encryption.decrypt(encryptedEmail);
        String password = Encryption.decrypt(encryptedPassword);
        return "{\"email\": \"" + email + "\",\"password\": \"" + password + "\"}";
    }

    public static String getCompanyStore(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [],\"dimensions\": [\"Advertiser ID\",\"Advertiser\",\"Company ID\",\"Company\",\"Agency ID\",\"Agency \",\"Division ID\",\"Division\",\"Region ID\",\"Region\",\"Time Zone\"],\"groupDimensionFilters\": [{\"vals\": [\"Meta Data - Company Store\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversion(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions (IOne)\"},{\"name\": \"View Based Conversions (IOne)\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\t\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Conversion\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getTraitDelivery(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions (Segment)\"},{\"name\": \"Clicks (Segment)\"},{\"name\": \"Media Cost (Segment)\"}],\"dimensions\": [\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Segment ID\",\"Segment\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Segment Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDelivery(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Impressions (IOne)\"},{\"name\": \"Clicks (IOne)\"},{\"name\": \"Media Cost (IOne)\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\t\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getTraitConversion(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions (Segment)\"},{\"name\": \"View Based Conversions (Segment)\"}],\"dimensions\": [\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Segment ID\",\"Segment\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Segment Conversion\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getDomainConversion(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\"Creative\",\"Message Group ID\",\"Message Group\",\"Placement Pixel Size\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\",\"Domain\"],\"groupDimensionFilters\": [{\"vals\":[\"Display Conversion\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getDomainDelivery(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions\"},{\"name\": \"Clicks\"},{\"name\": \"Media Cost\"},{\"name\": \"Third Party Cost\"},{\"name\": \"Total Cost\"},{\"name\": \"Client Spend\"},{\"name\": \"3rd Party Rate\"},{\"name\": \"Client Rate\"}],\"dimensions\": [\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\"Creative\",\"Message Group ID\",\"Message Group\",\"Placement Pixel Size\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\",\"Domain\",\"Third_Party_CPM_Rate\",\"Client_dCPM_Rate\"],\"groupDimensionFilters\": [{\"vals\":[\"Display Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [{\"operator\": \"NOT_EQUALS\",\"val\": \".00\",\"dimension\": \"Third_Party_CPM_Rate\"},{\"operator\": \"NOT_EQUALS\",\"val\": \".00\",\"dimension\": \"Client_dCPM_Rate\"}],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelCampaignTarget(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions (IOne)\"},{\"name\": \"Media Cost (IOne)\"},{\"name\": \"Clicks (IOne)\"}],\"dimensions\": [\"Line Item ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Line Item ID\",\"sortOrder\": \"ASC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelCreative(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions (IOne)\"},{\"name\": \"Media Cost (IOne)\"},{\"name\": \"Clicks (IOne)\"}],\"dimensions\": [\"Creative ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Creative ID\",\"sortOrder\": \"ASC\",\"topResults\": \"2500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelCampaignTarget(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions (IOne)\"},{\"name\": \"View Based Conversions (IOne)\"}],\"dimensions\": [\"Line Item ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Line Item ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelCreative(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions (IOne)\"},{\"name\": \"View Based Conversions (IOne)\"}],\"dimensions\": [\"Creative ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Creative ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelCampaign(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions (IOne)\"},{\"name\": \"View Based Conversions (IOne)\"}],\"dimensions\": [\"Campaign ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Campaign ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelAdvertiser(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions (IOne)\"},{\"name\": \"View Based Conversions (IOne)\"}],\"dimensions\": [\"Advertiser ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelAdvertiser(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions (IOne)\"},{\"name\": \"Media Cost (IOne)\"},{\"name\": \"Clicks (IOne)\"}],\"dimensions\": [\"Advertiser ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"ASC\",\"topResults\": \"2500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelPublisher(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions (IOne)\"},{\"name\": \"Media Cost (IOne)\"},{\"name\": \"Clicks (IOne)\"}],\"dimensions\": [\"Adserver Placement ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Adserver Placement ID\",\"sortOrder\": \"ASC\",\"topResults\": \"2500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelCampaign(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions (IOne)\"},{\"name\": \"Media Cost (IOne)\"},{\"name\": \"Clicks (IOne)\"}],\"dimensions\": [\"Campaign ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Campaign ID\",\"sortOrder\": \"ASC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }


    public static String getTraitDeliveryLevelAdvertiser(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Impressions (Segment)\"},{\"name\": \"Media Cost (Segment)\"},{\"name\": \"Clicks (Segment)\"}],\"dimensions\": [\"Advertiser ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Segment Delivery\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getTraitDeliveryLevelCampaign(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Impressions (Segment)\"},{\"name\": \"Media Cost (Segment)\"},{\"name\": \"Clicks (Segment)\"}],\"dimensions\": [\"Campaign ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Segment Delivery\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Campaign ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getTraitDeliveryLevelCampaignTarget(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Impressions (Segment)\"},{\"name\": \"Media Cost (Segment)\"},{\"name\": \"Clicks (Segment)\"}],\"dimensions\": [\"Line Item ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Segment Delivery\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Line Item ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getTraitDeliveryLevelTrait(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Impressions (Segment)\"},{\"name\": \"Media Cost (Segment)\"},{\"name\": \"Clicks (Segment)\"}],\"dimensions\": [\"Segment ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Segment Delivery\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Segment ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getTraitConversionLevelTrait(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions (Segment)\"},{\"name\": \"View Based Conversions (Segment)\"}],\"dimensions\": [\"Segment ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Segment Conversion\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Segment ID\",\"sortOrder\": \"DESC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getDomainDeliveryLevelDomain(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Impressions\"},{\"name\": \"Media Cost\"},{\"name\": \"Clicks\"}],\"dimensions\": [\"Domain\"],\"groupDimensionFilters\": [{\"vals\": [\"Display Delivery\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Domain\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}\n]";
    }

    public static String getDomainConversionLevelDomain(String startDate, String endDate, String brandId) {
        return "[{\"brandId\": \""+brandId+"\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\": [\"Domain\"],\"groupDimensionFilters\": [{\"vals\": [\"Display Conversion\"],\"dimension\": \"Data Set\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Domain\",\"sortOrder\": \"DESC\",\"topResults\": \"5000\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getJobStreamInfo(String brandId, String streamName){
        return "[{\"brandId\":\"" + brandId + "\",\"dateRange\":\"THIS_MONTH\",\"measurements\":[{\"name\": \"Data Stream Job Source Rows\"}],\"dimensions\":[\"Data Stream Job Id\",\"Data Stream Job Data Start\",\"Data Stream Job Data End\"],\"groupDimensionFilters\":[{\"vals\":[\""+streamName+"\"],\"dimension\":\"Data Set\",\"operator\":\"IN\"},{\"vals\":[\"SUCCESS\"],\"dimension\":\"Data Stream Job Status\",\"operator\":\"IN\"}],\"stringDimensionFilters\":[],\"stringDimensionFiltersOperator\":\"AND\",\"numberDimensionFiltersOperator\":\"AND\",\"numberMeasurementFilter\":[],\"sortBy\":\"Data Stream Job Id\",\"sortOrder\":\"DESC\",\"topResults\":\"1\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\":[]}]";
    }

    public static String getDCMReport(String startDate, String endDate, String brandId, String advertiserId){
        return "[{\"brandId\":\""+brandId+"\",\"dateRange\":\"CUSTOM\",\"startDate\":\""+startDate+",\"endDate\":\""+endDate+",\"measurements\":[{\"name\":\"Booked Cost\"},{\"name\":\"Rich Media Backup Images\"},{\"name\":\"Booked Impressions\"},{\"name\":\"Clicks\"},{\"name\":\"Rich Media Expansion Time\"},{\"name\":\"Expansions\"},{\"name\":\"Rich Media Full Screen Impressions\"},{\"name\":\"Full Screen Video Completions\"},{\"name\":\"Full Screen Video Plays\"},{\"name\":\"Impressions\"},{\"name\":\"Rich Media Interactive Impressions\"},{\"name\":\"Media Cost\"},{\"name\":\"Rich Media Clicks\"},{\"name\":\"Rich Media Impressions\"},{\"name\":\"Rich Media Display Time\"},{\"name\":\"Total Interaction Time\"},{\"name\":\"Interactions\"},{\"name\":\"Video Companion Clicks\"},{\"name\":\"Video Fully Played\"},{\"name\":\"Video Completions 25%\"},{\"name\":\"Video Full Screens\"},{\"name\":\"Video Interactions\"},{\"name\":\"Video Completions 50%\"},{\"name\":\"Video Mutes\"},{\"name\":\"Video Pauses\"},{\"name\":\"Video Plays\"},{\"name\":\"Video Replays\"},{\"name\":\"Video Skips\"},{\"name\":\"Video Stops\"},{\"name\":\"Video Completions 75%\"},{\"name\":\"Video True Views\"},{\"name\":\"Video Unmutes\"},{\"name\":\"Video Views\"}],\"dimensions\":[\"Campaign Advertiser\",\"Campaign Advertiser ID\",\"Line Item\",\"Line Item ID\",\"Placement Content Category\",\"Creative Format\",\"Day\",\"Package Roadblock\",\"Package Roadblock ID\",\"Placement\",\"Placement ID\",\"Placement Pixel Size\",\"Site Name\",\"Domain\",\"Creative Click URL\",\"Creative\",\"Creative ID\",\"Placement Strategy\"],\"groupDimensionFilters\":[{\"vals\":[\""+advertiserId+"\"],\"dimension\":\"Campaign Advertiser ID\",\"operator\":\"IN\"}],\"stringDimensionFilters\":[],\"stringDimensionFiltersOperator\":\"AND\",\"numberDimensionFiltersOperator\":\"AND\",\"numberMeasurementFilter\":[],\"sortBy\":\"Campaign Advertiser\",\"sortOrder\":\"DESC\",\"topResults\":\"500\",\"groupOthers\":false,\"topPerDimension\":false,\"totalDimensions\": []}]";
    }

    public static String getDCMReport1(String startDate, String endDate, String brandId, String advertiserId){
        return "[{\n" +
                "\t\"brandId\": \""+brandId+"\",\n" +
                "\t\"dateRange\": \"CUSTOM\",\n" +
                "\t\"startDate\": \""+startDate+"\",\n" +
                "\t\"endDate\": \""+endDate+"\",\n" +
                "\t\"measurements\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Booked Cost\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Rich Media Backup Images\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Booked Impressions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Clicks\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Rich Media Expansion Time\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Expansions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Rich Media Full Screen Impressions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Full Screen Video Completions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Full Screen Video Plays\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Impressions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Rich Media Interactive Impressions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Media Cost\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Rich Media Clicks\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Rich Media Impressions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Rich Media Display Time\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Total Interaction Time\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Interactions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Companion Clicks\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Fully Played\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Completions 25%\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Full Screens\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Interactions\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Completions 50%\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Mutes\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Pauses\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Plays\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Replays\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Skips\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Stops\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Completions 75%\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video True Views\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Unmutes\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Video Views\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"dimensions\": [\n" +
                "\t\t\"Campaign Advertiser\",\n" +
                "\t\t\"Campaign Advertiser ID\",\n" +
                "\t\t\"Line Item\",\n" +
                "\t\t\"Day\",\n" +
                "\t\t\"Placement\",\n" +
                "\t\t\"Placement ID\",\n" +
                "\t\t\"Creative ID\"\n" +
                "\t],\n" +
                "\t\"groupDimensionFilters\": [{\"vals\":[\"" +"DCM Creative Delivery - Motel 6"+ "\"],\"dimension\":\"Data Set\",\"operator\":\"IN\"}],\n" +
                "\t\"stringDimensionFilters\": [],\n" +
                "\t\"stringDimensionFiltersOperator\": \"AND\",\n" +
                "\t\"numberDimensionFiltersOperator\": \"AND\",\n" +
                "\t\"numberMeasurementFilter\": [],\n" +
                "\t\"sortBy\": \"Placement ID\",\n" +
                "\t\"sortOrder\": \"DESC\",\n" +
                "\t\"topResults\": \"100\",\n" +
                "\t\"groupOthers\": false,\n" +
                "\t\"topPerDimension\": false,\n" +
                "\t\"totalDimensions\": []\n" +
                "}]";
    }
}



