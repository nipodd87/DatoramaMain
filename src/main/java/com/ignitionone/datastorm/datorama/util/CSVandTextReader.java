package com.ignitionone.datastorm.datorama.util;

import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//java8 file reader

/**
 * Created by ravi.peddi on 1/8/2017.
 */
public class CSVandTextReader {
    public List<String> getCSVData(String fileNameWithPath) throws IOException {
        List<String> csvData = new ArrayList<String>();
        try {
            CsvReader csvReader = new CsvReader(fileNameWithPath);
            while (csvReader.readRecord()) {
                csvData.add(csvReader.getRawRecord());
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            CommonUtil.logError("File " + fileNameWithPath + " not Found Exception", "Exception : <BR>" + e.getMessage());
        } catch (IOException e) {
            CommonUtil.logError("File " + fileNameWithPath + " IO Exception", "Exception : <BR>" + e.getMessage());
        }
        return csvData;
    }


}
