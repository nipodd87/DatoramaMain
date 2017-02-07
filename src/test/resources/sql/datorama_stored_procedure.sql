dbEnvt=sqlserver

thirdPartyFileGeneration_CreativeDelivery=exec [dbo].[SI_SP_ThirdPartyReportFileGeneration_CreativeDelivery] @StartDate = '$START_DATE$', @EndDate = '$END_DATE$'
thirdPartyFileGeneration_CreativeConversion=exec [dbo].[SI_SP_ThirdPartyReportFileGeneration_CreativeConversion] @StartDate = '$START_DATE$', @EndDate = '$END_DATE$'
thirdPartyFileGeneration_CompanyStore=exec [dbo].[SI_SP_ThirdPartyReportFileGeneration_CompanyStoreMetaData]
thirdPartyFileGeneration_TraitConversion=exec [dbo].[SI_SP_ThirdPartyReportFileGeneration_TraitConversion] @StartDate = '$START_DATE$', @EndDate = '$END_DATE$'