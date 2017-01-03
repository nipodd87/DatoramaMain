dbEnvt=sqlserver

getCount=select COUNT(*) cnt from DMS.dbo.<<tableName>>;
getColumnInfo=USE [DMS] select COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, DATETIME_PRECISION, IS_NULLABLE from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = '<<tableName>>'
getrows=select <<columnName>> from DMS.dbo.<<tableName>>;