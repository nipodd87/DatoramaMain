package com.ignitionone.datastorm.datorama.DCMUtil;


import com.google.api.services.dfareporting.model.File;
import com.google.api.services.dfareporting.Dfareporting;
import java.io.IOException;

/**
 * Created by nitin.poddar on 3/9/2017.
 */
public class DfaReportRunner {

 public static File runReport(Dfareporting reporting, long profileId, long reportId) throws IOException {
     File file = reporting.reports().run(profileId, reportId).execute();
     return file;
 }
}
