package com.ignitionone.datastorm.datorama.etl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ravi.peddi on 12/28/2016.
 */
public class DestinationTable {

    private String columnName;
    private Boolean uniqueColumn;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Boolean getUniqueColumn() {
        return uniqueColumn;
    }

    public void setUniqueColumn(Boolean uniqueColumn) {
        this.uniqueColumn = uniqueColumn;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public ValidationStyle getValidationStyle() {
        return validationStyle;
    }

    public void setValidationStyle(ValidationStyle validationStyle) {
        this.validationStyle = validationStyle;
    }

    private DataType dataType;

     private ValidationStyle validationStyle;

    public DestinationTable(String columnName, Boolean uniqueColumn, DataType dataType, ValidationStyle validationStyle) {
        this.columnName = columnName;
        this.uniqueColumn = uniqueColumn;
        this.dataType = dataType;
        this.validationStyle = validationStyle;
    }




}
