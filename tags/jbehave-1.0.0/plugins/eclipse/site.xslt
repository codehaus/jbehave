<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<site>
		<description url="http://jbehave.codehaus.org/eclipse">JBehave plugin</description>

		<xsl:for-each select="feature">
		<feature >		
		
		<xsl:attribute name="url">features/<xsl:value-of select="name"/>_<xsl:value-of select="version"/>.jar</xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="name"/></xsl:attribute>
		<xsl:attribute name="version"><xsl:value-of select="version"/></xsl:attribute>
		
			<category name="JBehave Plugin"/>
		
		</feature>
		</xsl:for-each>
		
		<category-def name="JBehave Plugin" label="JBehave Plugin"/>
	
	</site>
	</xsl:template>
</xsl:stylesheet>
