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
                    
		<#assign messages = scenarioContext.messages />
        <#if (messages.size() > 0) >
        <div id="scenarioMessages">  
	 		 <fieldset>
	             <legend><@i.messageFor "scenarioMessages" "Scenario Messages"/></legend>
	            <#list messages as message>
	                <p>${message}</p>
	            </#list>
	         </fieldset>           
         </div>
		</#if>
		
		<#assign failureCause = scenarioContext.failureCauseAsString />
        <#if (failureCause.length() > 0) >
        <div id="failureCause">  
	 		 <fieldset>
	             <legend><@i.messageFor "failureCause" "Failure Cause"/></legend>
				<p>
					<@w.textarea "failureCause" "${failureCause}" "rows='35' cols='70' disabled='true'"/>
				</p>
	         </fieldset>           
         </div>
		</#if>

         
    </form>
</div>
</body>
</html>