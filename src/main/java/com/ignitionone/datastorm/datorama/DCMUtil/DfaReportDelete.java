package com.ignitionone.datastorm.datorama.DCMUtil;

import com.google.api.services.dfareporting.Dfareporting;

import java.io.IOException;

/**
 * Created by nitin.poddar on 3/9/2017.
 */
public class DfaReportDelete {
    public static void deleteReport(Dfareporting reporting, long profileId, long reportId) throws IOException {
        // Delete the report.
        reporting.reports().delete(profileId, reportId).execute();
        System.out.printf("Report with ID %d was successfully deleted.", reportId);
    }
}
