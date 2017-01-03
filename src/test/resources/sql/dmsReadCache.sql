dbEnvt=dmsReadCache

getCount=select COUNT(*) CNT from DMS_ReadCache.dbo.<<tableName>>;
getColumnInfo=Select COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, DATETIME_PRECISION, IS_NULLABLE from INFORMATION_SCHEMA.COLUMNS where table_name='<<tableName>>'
getrows=select TOP 10 ClientID, AdCategoryID, AdCategoryStatusID from DMS_ReadCache.dbo.<<tableName>>;