<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "scenarioRunner" "Scenario Runner"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/scenario/scenario.action">
    
        <#include "/ftl/errors.ftl" parse="true">
        <div id="scenarioInput">
           <fieldset>
                <legend><@i.messageFor "scenarioInput" "Scenario Input"/></legend>
		        <p>
                <p>
                	<@w.textarea "scenarioContext.input" "${scenarioContext.input}" "rows='20' cols='70'"/>
                </p>
                <a href="javascript:fireActionMethod('run');"><@i.messageFor "runScenario" "Run Scenario"/></a> 
            </fieldset>
        </div>

        <#if scenarioContext.output?? >
        <div id="scenarioOutput">    
           <fieldset>
                <legend><@i.messageFor "scenarioOutput" "Scenario Output"/></legend>
                <p>
                    <@w.textarea "scenarioContext.output" "${scenarioContext.output}" "rows='20' cols='70' disabled='true'"/>
                </p>
            </fieldset>
         </div>        
         </#if>        
                    
		<#assign failureMessages = scenarioContext.failureMessages />
        <#if (failureMessages.size() > 0) >
        <div id="scenariofailureMessages">  
	 		 <fieldset>
	             <legend><@i.messageFor "scenariofailureMessages" "Scenario Failure Messages"/></legend>
	            <#list failureMessages as message>
	                <p>${message}</p>
	            </#list>
	         </fieldset>           
         </div>
		</#if>
		
		<#assign failureStackTrace = scenarioContext.failureStackTrace />
        <#if (failureStackTrace.length() > 0) >
        <div id="failureStackTrace">  
	 		 <fieldset>
	             <legend><@i.messageFor "failureStackTrace" "Failure Stack Trace"/></legend>
				<p>
					<@w.textarea "failureStackTrace" "${failureStackTrace}" "rows='35' cols='70' disabled='true'"/>
				</p>
	         </fieldset>           
         </div>
		</#if>

         
    </form>
</div>
</body>
</html>