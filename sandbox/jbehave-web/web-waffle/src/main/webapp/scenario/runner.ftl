<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "scenarioRunner" "Scenario Runner"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/scenario/runner.action">
    
        <h2><@i.messageFor "scenarioRunner" "Scenario Runner"/></h2>    

        <#include "/ftl/errors.ftl" parse="true">
        <div id="actions">
           <fieldset>
                <legend><@i.messageFor "scenarioRunner" "Scenario Runner"/></legend>
		        <p>
                <p>
                	<@w.textarea "scenarioContext.input" "${scenarioContext.input}" "rows='20' cols='60'"/>
                </p>
                <a href="javascript:fireActionMethod('run');"><@i.messageFor "runScenario" "Run Scenario"/></a> 
            </fieldset>
        </div>

        <#if scenarioContext.output?? >
        <div id="files">    
           <fieldset>
                <legend><@i.messageFor "scenarioContext.output" "Scenario Output"/></legend>
                <p>
                    <@w.textarea "scenarioContext.output" "${scenarioContext.output}" "rows='20' cols='60'"/>
                </p>
            </fieldset>
         </div>        
         </#if>        
                    
		<#assign messages = scenarioContext.messages />
        <#if (messages.size() > 0) >
 		 <fieldset>
             <legend><@i.messageFor "scenarioMessages" "Scenario Messages"/></legend>
            <#list messages as message>
                <p>${message}</p>
            </#list>
         </table>           
		</#if>
		
         
    </form>
</div>
</body>
</html>