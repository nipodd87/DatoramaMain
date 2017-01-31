package com.ignitionone.datastorm.datorama.util;

import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.stream.MalformedJsonException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by ravi.peddi on 1/10/2017.
 */
public class JsonReader {

    public JsonObject readJsonFile(String fileName) throws IOException {
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        Object obj = (Object) jsonParser.parse(getFile(fileName));
        jsonObject = (JsonObject) obj;
        return jsonObject;
    }


    public JSONObject readJsonString(String jsonString) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
        JSONObject baseObj = (JSONObject) jsonArray.get(0);
        JSONObject queryResponseData = (JSONObject) baseObj.get(0);
        return queryResponseData;
    }

    /**
     * Read the file
     *
     * @param fileName in value
     * @return out value
     * @throws IOException on error
     */
    private Reader getFile(String fileName) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        Reader rd = new InputStreamReader(is);
        return rd;
    }
}
