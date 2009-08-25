<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<feature>
<xsl:for-each select="feature">
<name>
<xsl:value-of select="@id"/>
</name>

<version>
<xsl:value-of select="@version"/>
</version>
    </xsl:for-each>
</feature>
</xsl:template>
</xsl:stylesheet>