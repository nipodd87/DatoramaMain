package com.ignitionone.datastorm.datorama.model;

/**
 * Created by nitin.poddar on 2/2/2017.
 */
public class DeliveryMetrics {

    private long totalImpressions;
    private long totalClicks;
    private double totalCost;
    private int recordCount;

    public long getTotalImpressions() {
        return totalImpressions;
    }

    public void setTotalImpressions(long totalImpressions) {
        this.totalImpressions = totalImpressions;
    }

    public long getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(long totalClicks) {
        this.totalClicks = totalClicks;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
