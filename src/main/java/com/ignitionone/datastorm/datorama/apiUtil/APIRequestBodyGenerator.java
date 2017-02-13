package com.ignitionone.datastorm.datorama.apiUtil;

/**
 * Created by nitin.poddar on 1/13/2017.
 */
public class APIRequestBodyGenerator {

    public static String getAuthRequestBody(){
        return "{\"email\": \"Harold.Cuebas@IgnitionOne.com\",\"password\": \"G@m3*0v3r\"}";
    }

    public static String getCompanyStore(String startDate, String endDate){
        return "[{\"brandId\": \"12547\",\"dateRange\": \"THIS_MONTH\",\"startDate\": \""+startDate+"\",\"endDate\": \""+endDate+"\",\"measurements\": [],\"dimensions\": [\"Advertiser ID\",\"Advertiser\",\"Company ID\",\"Company\",\"Agency ID\",\"Agency \",\"Division ID\",\"Division\",\"Region ID\",\"Region\",\"Time Zone\"],\"groupDimensionFilters\": [{\"vals\": [\"Meta Data - Company Store\"],\"dimension\": \"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeConversion(String startDate, String endDate){
        return "[{\"brandId\": \"12547\",\"dateRange\": \"THIS_MONTH\",\"startDate\": \""+startDate+"\",\"endDate\": \""+endDate+"\",\"measurements\": [{\"name\": \"Total_Click Based Conversions\"},{\"name\": \"Total_View Based Conversions\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\t\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Conversion\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getTraitDelivery(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \""+startDate+"\",\"endDate\": \""+endDate+"\",\"measurements\":[{\"name\": \"Trait Impressions\"},{\"name\": \"Trait Clicks\"},{\"name\": \"Trait Media Cost\"}],\"dimensions\": [\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Segment ID\",\"Segment\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [],\"dimension\": \"Publisher ID\",\"operator\":\"IN\"}"+"],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Day\",\"sortOrder\": \"DESC\",\"topResults\": \"500\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getCreativeDelivery(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"THIS_MONTH\",\"startDate\": \""+startDate+"\",\"endDate\": \""+endDate+"\",\"measurements\": [{\"name\": \"Total_Impressions\"},{\"name\": \"Total_Clicks\"},{\"name\": \"Total_Cost\"}],\"dimensions\":[\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Creative ID\",\t\"Creative\",\"Message Group ID\",\"Message Group\",\"Adserver Placement ID\",\"AdServer Placement\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Creative Delivery\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }

    public static String getTraitConversion(String startDate, String endDate) {
        return "[{\"brandId\": \"12547\",\"dateRange\": \"CUSTOM\",\"startDate\": \"" + startDate + "\",\"endDate\": \"" + endDate + "\",\"measurements\": [{\"name\": \"Total_Click Based Conversions\"},{\"name\": \"Total_View Based Conversions\"}],\"dimensions\": [\"Day\",\"Advertiser ID\",\"Campaign ID\",\"Campaign\",\"Campaign Flight Start Date\",\"Campaign Flight End Date\",\"Account Manager ID\",\"Campaign Status\",\"Advertiser Source ID\",\"Advertiser Source\",\"Line Item ID\",\"Line Item\",\"Line Item Flight Start Date\",\"Line Item Flight End Date\",\"Line Item Status\",\"Segment ID\",\"Segment\",\"Publisher ID\",\"Publisher\",\"Currency (Original)\"],\"groupDimensionFilters\": [{\"vals\":[\"Trait Conversion\"],\"dimension\":\"Data View\",\"operator\": \"IN\"}],\"stringDimensionFilters\": [],\"stringDimensionFiltersOperator\": \"AND\",\"numberDimensionFiltersOperator\": \"AND\",\"numberMeasurementFilter\": [],\"sortBy\": \"Advertiser ID\",\"sortOrder\": \"DESC\",\"topResults\": \"50\",\"groupOthers\": false,\"topPerDimension\": false,\"totalDimensions\": []}]";
    }
}
