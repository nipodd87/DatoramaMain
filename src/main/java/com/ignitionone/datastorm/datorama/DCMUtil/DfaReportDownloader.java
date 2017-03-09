package com.ignitionone.datastorm.datorama.DCMUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.Charsets;
import com.google.api.services.dfareporting.Dfareporting;

/**
 * Created by nitin.poddar on 3/9/2017.
 */
public class DfaReportDownloader {

    public static List<String> downloadFileAsList(Dfareporting reporting, long profileId, long reportId, long fileId) throws IOException {
        List<String> reportFile = new ArrayList<String>();
        HttpResponse fileContents = reporting.reports().files().get(profileId, reportId, fileId)
                .executeMedia();

        //Convert the file content to List of String
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(fileContents.getContent(), Charsets.UTF_8));

            String line;
            int counter=0;
            while ((line = reader.readLine()) != null) {
                if (counter > 9){
                    String newLine=line.replaceAll(",", "|@|");
                    reportFile.add(newLine);
                }
                counter++;
            }
        } finally {
            fileContents.disconnect();
        }
        return reportFile;
    }
}
