<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "scenarioUpload" "Scenario Upload"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/scenario/upload.action">
    
        <h2><@i.messageFor "scenarioUpload" "Scenario Upload"/></h2>    
        
        <div id="actions">
           <fieldset>
                <legend><@i.messageFor "upload" "Upload"/></legend>
                <p><label for="importFile"><@i.messageFor "uploadDirectory" "Upload Directory"/></label>
                	<@w.text "uploadDirectory" ""/>
                </p>
                <p><label for="importFile"><@i.messageFor "uploadFile" "Upload File"/></label><@w.file "importFile" />
                    <a class="buttonUpload" onclick="fireMultipartActionMethod('upload');"><@i.messageFor "upload" "Upload"/></a>        
                </p>
            </fieldset>
        </div>

       <#assign errors = controller.errors />
       <#if (errors.size() > 0) >
         <table>
            <th><@i.messageFor "errors" "Errors"/></th>
            <#list errors as error>
                <tr>
                    <td>${error}</td>
                </tr>
            </#list>
         </table>   
       </#if>

       <#if controller.uploadedPath?? >
         <p>Last upload to ${uploadedPath}</p>
       </#if>                   
    </form>
</div>
</body>
</html>