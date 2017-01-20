dbEnvt=postgresql

getCount=select COUNT(1) cnt from display.<<tableName>>;
getColumnInfo=Select COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, DATETIME_PRECISION, IS_NULLABLE from INFORMATION_SCHEMA.COLUMNS where table_schema = 'display' and table_name = '<<tableName>>'
getrows=select <<columnName>> from display.<<tableName>>;
insertRecords=INSERT INTO display.<<tableName>> (<<columnName>>) VALUES (<<columnValues>>);