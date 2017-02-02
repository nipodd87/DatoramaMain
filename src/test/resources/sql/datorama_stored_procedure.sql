dbEnvt=sqlserver

thirdPartyFileGeneration_CreativeDelivery=exec [dbo].[SI_SP_ThirdPartyReportFileGeneration_CreativeDelivery] @StartDate = '$START_DATE$', @EndDate = '$END_DATE$'