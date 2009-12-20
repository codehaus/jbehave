<html>
<head>
<title>${name}</title>
<style type="text/css" media="all">
@import url( "./style/jbehave-reports.css" );
</style>
</head>
<body>
<#if format == "html">
${body}
<#else>
<div class="brush: ${format}">
<pre>
${body}
</pre>
</div>
</#if>
</body>
</html>
