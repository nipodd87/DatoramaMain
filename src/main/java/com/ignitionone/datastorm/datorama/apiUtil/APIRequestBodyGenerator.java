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

    public static String getCompanyStore(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [],\"dimensions\": [\"Advertiser ID\",\"Advertiser\",\"Company ID\",\"Company\",\"Agency ID\",\"Agency \",\"Division ID\",\"Division\",\"Region ID\",\"Region\",\"Time Zone\"],\"groupDimensionFilters\": [{\"vals\": [\"Meta Data - Company Store\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversion(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\t\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Conversion\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getTraitDelivery(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Trait Impressions\"},{\"name\": \"Trait Clicks\"},{\"name\": \"Trait Cost\"}],\"dimensions\": [\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Segment ID\",\"Segment\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Trait Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDelivery(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Impressions\"},{\"name\": \"Clicks\"},{\"name\": \"Cost\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\t\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getTraitConversion(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Trait Click Based Conversions\"},{\"name\": \"Trait View Based Conversions\"}],\"dimensions\": [\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Segment ID\",\"Segment\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Trait Conversion\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getDomainConversion(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Domain Click Based Conversions\"},{\"name\": \"Domain View Based Conversions\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\",\"Domain\"],\"groupDimensionFilters\": [{\"vals\":[\"Domain Conversion\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getDomainDelivery(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Domain Impressions\"},{\"name\": \"Domain Clicks\"},{\"name\": \"Domain Cost\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\",\"Domain\"],\"groupDimensionFilters\": [{\"vals\":[\"Domain Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelCampaignTarget(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions\"},{\"name\": \"Cost\"},{\"name\": \"Clicks\"}],\"dimensions\": [\"Line Item ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Line Item ID\",\"sortOrder\": \"ASC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelCreative(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions\"},{\"name\": \"Cost\"},{\"name\": \"Clicks\"}],\"dimensions\": [\"Creative ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Creative ID\",\"sortOrder\": \"ASC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelCampaignTarget(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\": [\"Line Item ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Line Item ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelCreative(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\": [\"Creative ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Creative ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelCampaign(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\": [\"Campaign ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Campaign ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversionLevelAdvertiser(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\": [\"Advertiser ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Creative Conversion\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelAdvertiser(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions\"},{\"name\": \"Cost\"},{\"name\": \"Clicks\"}],\"dimensions\": [\"Advertiser ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"ASC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDeliveryLevelCampaign(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\":[{\"name\": \"Impressions\"},{\"name\": \"Cost\"},{\"name\": \"Clicks\"}],\"dimensions\": [\"Campaign ID\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Campaign ID\",\"sortOrder\": \"ASC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }


    public static String getTraitDeliveryLevelAdvertiser(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Trait Impressions\"},{\"name\": \"Trait Cost\"},{\"name\": \"Trait Clicks\"}],\"dimensions\": [\"Advertiser ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Trait Delivery\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getTraitDeliveryLevelCampaign(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Trait Impressions\"},{\"name\": \"Trait Cost\"},{\"name\": \"Trait Clicks\"}],\"dimensions\": [\"Campaign ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Trait Delivery\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Campaign ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getTraitDeliveryLevelCampaignTarget(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Trait Impressions\"},{\"name\": \"Trait Cost\"},{\"name\": \"Trait Clicks\"}],\"dimensions\": [\"Line Item ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Trait Delivery\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Line Item ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getTraitDeliveryLevelTrait(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Trait Impressions\"},{\"name\": \"Trait Cost\"},{\"name\": \"Trait Clicks\"}],\"dimensions\": [\"Segment ID\"],\"groupDimensionFilters\": [{\"vals\": [\"Trait Delivery\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Segment ID\",\"sortOrder\": \"ASC\",\"topResults\": \"5000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

}



