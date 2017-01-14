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
}
