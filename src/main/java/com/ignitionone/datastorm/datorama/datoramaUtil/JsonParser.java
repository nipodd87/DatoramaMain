package com.ignitionone.datastorm.datorama.datoramaUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi.peddi on 1/10/2017.
 */
public class JsonParser {

    public List<String> convertJsonToList(String jsonString) throws IOException, ParseException {
        JSONArray jsonArray = (JSONArray) JSONValue.parse(jsonString);
        return convertJsonToList((JSONObject) jsonArray.get(0));
    }

    public List<String> convertJsonToList(JSONObject jsonObject) throws IOException, ParseException {
        List<String> jsonString = new ArrayList<String>();

        JSONObject queryResponseData = (JSONObject) jsonObject.get("queryResponseData");
        JSONArray header = (JSONArray) queryResponseData.get("headers");
        JSONArray rows = (JSONArray) queryResponseData.get("rows");
        jsonString.add(header.toString().replace("\"","").replace("[","").replace("]","").replace(",","|"));
        for (int i=0;i<rows.size();i++){
            List<String> rowItem = (List<String>) rows.get(i);
            StringBuffer columnItem = new StringBuffer();
            for (int j=0;j<rowItem.size();j++){
                if (rowItem.get(j) instanceof String){
                    String column = rowItem.get(j).replace("\"","").replace("[","").replace("]","").replace("null","").replace(" 00:00:00.0", "");
                    columnItem.append(column);
                } else if (rowItem.get(j) == null){
                    //do Nothing
                }else{
                    Object obj = rowItem.get(j);
                    columnItem.append(obj.toString());
                }
                if (j<rowItem.size()-1){
                    columnItem.append("|");
                }
            }
            jsonString.add(columnItem.toString());
        }
        return jsonString;
    }
    public JSONObject convertStringtoJsonObj(String response){
        JSONArray jsonArray = (JSONArray) JSONValue.parse(response);
        return (JSONObject) jsonArray.get(0);
    }
}
