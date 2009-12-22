<#ftl strip_whitespace=true>
<#macro stat name stats><#assign value = stats.get(name)!"N/A">${value}</#macro>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>JBehave Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<style type="text/css" media="all">
@import url( "./style/jbehave-reports.css" );
</style>
</head>

<body>
<div id="banner"><img src="images/jbehave-logo.png" alt="jbehave" />
<div class="clear"></div>
</div>

<div class="reports">

<h2>Story Reports</h2>

<table>
<tr><th>Name</th><th>Statistics</th><th>View</th></tr>
<#list reports as report>
<#assign filesByFormat = report.filesByFormat>
<tr>
<td>${report.name}</td>
<td>
<#assign stats = report.asProperties("stats")>
<#if (stats.size() > 0)>
Scenarios: <@stat "scenarios" stats/> (Failed: <@stat "scenariosFailed" stats/>)<br/>
Steps: <@stat "steps" stats/> (Success: <@stat "stepsSuccessful" stats/>; Pending: <@stat "stepsPending" stats/>; Not Performed: <@stat "stepsNotPerformed" stats/>; Failed: <@stat "stepsFailed" stats/>)<br/>
<#else>
N/A
</#if>
</td>
<td><#list filesByFormat.keySet() as format><#assign file = filesByFormat.get(format)><a href="${file.path}">${format}</a><#if format_has_next>|</#if></#list></td>
</tr>
</#list>
</table>
<br />
</div>

<div class="clear"></div>
<div id="footer">
<div class="left">Generated at ${date?string("dd/MM/yyyy HH:mm:ss")}</div>
<div class="right">JBehave &#169; 2003-2009</div> 
<div class="clear"></div>
</div>

</body>

<!--  SyntaxHighlighter resources:  should be included at end of body -->
<link rel="stylesheet" type="text/css" href="./style/sh-2.0.320/shCore.css"/>
<link rel="stylesheet" type="text/css" href="./style/sh-2.0.320/shThemeRDark.css"/>
<script language="javascript" src="./js/sh-2.0.320/shCore.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushJava.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushPlain.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushRuby.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushXml.js"></script>
<script language="javascript" src="./js/shBrushBdd.js"></script>
<script type="text/javascript">
	SyntaxHighlighter.config.clipboardSwf = './js/sh-2.0.320/clipboard.swf';
    SyntaxHighlighter.defaults['gutter'] = false;
    SyntaxHighlighter.defaults['toolbar'] = true;    
	SyntaxHighlighter.all();
</script>

</html>
