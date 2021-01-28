---
title: UpdateTeamspeakClientVersionsJob -
---
//[musicnova](../../index.md)/[eu.musicnova.musicnova.scheduler.jobs](../index.md)/[UpdateTeamspeakClientVersionsJob](index.md)



# UpdateTeamspeakClientVersionsJob  
 [jvm] @Component()  
  
class [UpdateTeamspeakClientVersionsJob](index.md) : Job

update teamspeak versions index every 12 hours

   


## Constructors  
  
|  Name|  Summary| 
|---|---|
| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/UpdateTeamspeakClientVersionsJob/#/PointingToDeclaration/"></a>[UpdateTeamspeakClientVersionsJob](-update-teamspeak-client-versions-job.md)| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/UpdateTeamspeakClientVersionsJob/#/PointingToDeclaration/"></a> [jvm] fun [UpdateTeamspeakClientVersionsJob](-update-teamspeak-client-versions-job.md)()   <br>


## Types  
  
|  Name|  Summary| 
|---|---|
| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob.Companion///PointingToDeclaration/"></a>[Companion](-companion/index.md)| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob.Companion///PointingToDeclaration/"></a>[jvm]  <br>Content  <br>object [Companion](-companion/index.md)  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/detail/#/PointingToDeclaration/"></a>[detail](detail.md)| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/detail/#/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@Bean()  <br>@Qualifier(value = "UpdateTeamspeakClientVersions_DETAIL")  <br>  <br>fun [detail](detail.md)(): JobDetail  <br><br><br>
| <a name="kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/"></a>[equals](../../eu.musicnova.musicnova.web/-web-auth-config/index.md#%5Bkotlin%2FAny%2Fequals%2F%23kotlin.Any%3F%2FPointingToDeclaration%2F%5D%2FFunctions%2F-89046061)| <a name="kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open operator fun [equals](../../eu.musicnova.musicnova.web/-web-auth-config/index.md#%5Bkotlin%2FAny%2Fequals%2F%23kotlin.Any%3F%2FPointingToDeclaration%2F%5D%2FFunctions%2F-89046061)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/execute/#org.quartz.JobExecutionContext/PointingToDeclaration/"></a>[execute](execute.md)| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/execute/#org.quartz.JobExecutionContext/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open override fun [execute](execute.md)(context: JobExecutionContext)  <br><br><br>
| <a name="kotlin/Any/hashCode/#/PointingToDeclaration/"></a>[hashCode](../../eu.musicnova.musicnova.web/-web-auth-config/index.md#%5Bkotlin%2FAny%2FhashCode%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-89046061)| <a name="kotlin/Any/hashCode/#/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open fun [hashCode](../../eu.musicnova.musicnova.web/-web-auth-config/index.md#%5Bkotlin%2FAny%2FhashCode%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-89046061)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| <a name="kotlin/Any/toString/#/PointingToDeclaration/"></a>[toString](../../eu.musicnova.musicnova.web/-web-auth-config/index.md#%5Bkotlin%2FAny%2FtoString%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-89046061)| <a name="kotlin/Any/toString/#/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>open fun [toString](../../eu.musicnova.musicnova.web/-web-auth-config/index.md#%5Bkotlin%2FAny%2FtoString%2F%23%2FPointingToDeclaration%2F%5D%2FFunctions%2F-89046061)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/trigger/#org.quartz.JobDetail/PointingToDeclaration/"></a>[trigger](trigger.md)| <a name="eu.musicnova.musicnova.scheduler.jobs/UpdateTeamspeakClientVersionsJob/trigger/#org.quartz.JobDetail/PointingToDeclaration/"></a>[jvm]  <br>Content  <br>@Bean()  <br>@Qualifier(value = "UpdateTeamspeakClientVersions_TRIGGER")  <br>  <br>fun [trigger](trigger.md)(@Qualifier(value = "UpdateTeamspeakClientVersions_DETAIL")detail: JobDetail): Trigger  <br><br><br>

