dbEnvt=sqlserver
uploadFileToS3=[dbo].[SI_SP_ExecuteSSISPackage] @PackageName = 'ThirdPartyReportFileGeneration_Parent_v1', @FileType=$fileType$, @ServerName= 'ATL02SQLDEV02.searchignite.local'