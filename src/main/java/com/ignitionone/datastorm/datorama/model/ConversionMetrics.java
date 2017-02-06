package com.ignitionone.datastorm.datorama.model;

/**
 * Created by nitin.poddar on 2/4/2017.
 */
public class ConversionMetrics {

    private int totalClickBasedConversion;
    private int totalViewBasedConversion;
    private int recordCount;

    public int getTotalClickBasedConversion() {
        return totalClickBasedConversion;
    }

    public void setTotalClickBasedConversion(int totalClickBasedConversion) {
        this.totalClickBasedConversion = totalClickBasedConversion;
    }

    public int getTotalViewBasedConversion() {
        return totalViewBasedConversion;
    }

    public void setTotalViewBasedConversion(int totalViewBasedConversion) {
        this.totalViewBasedConversion = totalViewBasedConversion;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
