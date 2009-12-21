<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>JBehave Reports</title>

<style type="text/css" media="all">
@import url( "./style/jbehave.css" );
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
</head>

<body class="composite">
<div id="banner"><img src="images/jbehave-logo.png" alt="jbehave" />
<div class="clear"></div>
</div>

<div id="bodyColumn">
<div id="contentBox">
<div class="section">

<table>
<tr><th>Name</th><th>Formats</th></tr>
<#list reports as report>
<#assign filesByFormat = report.filesByFormat >
<tr>
<td>${report.name}</td>
<td><#list filesByFormat.keySet() as format><#assign file = filesByFormat.get(format)><a href="${file.path}">${format}</a><#if format_has_next>,</#if></#list></td>
</tr>
</#list>
</table>
<br />
</div>
</div>
</div>
<div class="clear"></div>
<div id="footer">
<div class="left">Published on ${publishedDate?string("dd/MM/yyyy")}</div>
<div class="right">&#169; 2003-2009</div>
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
