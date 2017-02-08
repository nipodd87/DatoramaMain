dbEnvt=sqlserver

getCount=select COUNT(*) cnt from DMS.dbo.<<tableName>>;
getColumnInfo=USE [DMS] select COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, DATETIME_PRECISION, IS_NULLABLE from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = '<<tableName>>'
getrows=select <<columnName>> from DMS.dbo.<<tableName>>;
insertRecords=INSERT INTO DMS.dbo.<<tableName>> (<<columnName>>) VALUES (<<columnValues>>);

getThirdPartyFileInfo=SELECT TOP 1 ReportStartDate, ReportEndDate, FileName, FileRecordCount, FileStatusID FROM [DMS].[dbo].[ThirdPartyReportFileRequest_Queue] where FileTypeId = $fileTypeID$ order by ThirdPartyReportFileRequestQueueID desc;

getMeasurementCountCreativeDelivery=SELECT sum([impressions]) as total_impressions,sum([cost]) as total_cost ,sum([clicks]) as total_clicks FROM [NAN_STATS].[dbo].[impression_creatives_stats] (nolock) where campaign_ID > 1000000 and convert(date, dateadd(dd, date_id,'1999-12-31'), 101) between '$START_DATE$' and '$END_DATE$'
getMeasurementCountCreativeConversion=SELECT sum([click_based]) as total_click_based_conversion, (count(1) - sum([click_based])) as total_view_based_conversion FROM [NAN_STATS].[dbo].[conversion_stats_date] (nolock) where campaign_ID > 1000000 and convert(date, dateadd(dd, date_id,'1999-12-31'), 101) between '$START_DATE$' and '$END_DATE$'
getMeasurementCountTraitConversion=SELECT sum([click_based]) as total_click_based_conversion, (count(1) - sum([click_based])) as total_view_based_conversion FROM [NAN_STATS].[dbo].[conversion_stats_date] (NOLOCK) WHERE campaign_id > 1000000  AND convert(date, dateadd(dd, date_id,'1999-12-31'), 101) BETWEEN '$START_DATE$' and '$END_DATE$'
getMeasurementCountTraitDelivery=SELECT sum([impressions]) as total_impressions,sum([cost]) as total_cost ,sum([clicks]) as total_clicks FROM [NAN_STATS].[dbo].[impression_categories_stats] (nolock) where campaign_ID > 1000000 and convert(date, dateadd(dd, date_id,'1999-12-31'), 101) between '$START_DATE$' and '$END_DATE$'
getMeasurementCountDomainConversion=SELECT sum([click_based]) as total_click_based_conversion, (count(1) - sum([click_based])) as total_view_based_conversion FROM [NAN_STATS].[dbo].[conversion_stats_date] (NOLOCK) WHERE campaign_id > 1000000  AND convert(date, dateadd(dd, date_id,'1999-12-31'), 101) BETWEEN '$START_DATE$' and '$END_DATE$'
getMeasurementCountDomainDelivery=SELECT sum([impressions]) as total_impressions,sum([cost]) as total_cost ,sum([clicks]) as total_clicks FROM [NAN_STATS].[dbo].[impression_creatives_stats] (nolock) where campaign_ID > 1000000 and convert(date, dateadd(dd, date_id,'1999-12-31'), 101) between '$START_DATE$' and '$END_DATE$'