<#ftl strip_whitespace=true>
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
<#assign brushFormat = format> <#if format == "stats"><#assign brushFormat = "plain"> </#if>
<pre class="brush: ${brushFormat}">
${body}
</pre>
</#if>
</body>
<!--  SyntaxHighlighter resources:  should be included at end of body -->
<link rel="stylesheet" type="text/css" href="./style/sh-2.0.320/shCore.css"/>
<link rel="stylesheet" type="text/css" href="./style/sh-2.0.320/shThemeRDark.css"/>
<script language="javascript" src="./js/sh-2.0.320/shCore.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushJava.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushPlain.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushRuby.js"></script>
<script language="javascript" src="./js/sh-2.0.320/shBrushXml.js"></script>
<script language="javascript" src="./js/shBrushTxt.js"></script>
<script type="text/javascript">
	SyntaxHighlighter.config.clipboardSwf = './js/sh-2.0.320/clipboard.swf';
    SyntaxHighlighter.defaults['gutter'] = false;
    SyntaxHighlighter.defaults['toolbar'] = true;    
	SyntaxHighlighter.all();
</script>
</html>
