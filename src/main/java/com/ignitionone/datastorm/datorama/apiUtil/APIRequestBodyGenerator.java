package com.ignitionone.datastorm.datorama.apiUtil;

/**
 * Created by nitin.poddar on 1/13/2017.
 */
public class APIRequestBodyGenerator {

    public static String getAuthRequestBody(){
        return "{\"email\": \"Harold.Cuebas@IgnitionOne.com\",\"password\": \"G@m3*0v3r\"}";
    }

    public static String getCompanyStoreRequestBody(String startDate, String endDate){
        return "[{\"brandId\": \"12160\",\"dateRange\": \"THIS_MONTH\",\"startDate\": \""+startDate+"\",\"endDate\": \""+endDate+"\",\"measurements\": [],\"dimensions\": [\"Advertiser ID\",\"Advertiser\",\"Company ID\",\"Company\",\"Agency ID\",\"Agency \",\"Division ID\",\"Division\",\"Region ID\",\"Region\",\"Time Zone\"],\"groupDimensionFilters\": [],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversion(String startDate, String endDate){
        return "[{\"brandId\": \"12160\",\"dateRange\": \"THIS_MONTH\",\"startDate\": \""+startDate+"\",\"endDate\": \""+endDate+"\",\"measurements\": [{\"name\": \"Click Based Conversions\"},{\"name\": \"View Based Conversions\"}],\"dimensions\":[\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight End Date\",\"Day\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\t\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"DESC\",\"topResults\": \"50000\",\"groupOthers\": true,\"topPerDimension\": true,\"totalDimensions\": []}]";
    }
}
