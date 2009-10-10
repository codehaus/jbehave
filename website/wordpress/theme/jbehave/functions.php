<?php
/*
File Name: Wordpress Theme Toolkit
Version: 1.1.x
Author: Ozh  *** Modified by Scott Allan Wallick for his purposes (themes): http://www.plaintxt.org/themes/
Author URI: http://planetOzh.com/
*/
/************************************************************************************
 * THEME USERS : Don't touch anything !! Or don't ask the theme author for support (:-0
 ************************************************************************************/
include(dirname(__FILE__).'/themetoolkit.php');

/************************************************************************************
 * FUNCTION ARRAY
 ************************************************************************************/
themetoolkit(
	'blogtxt', 
	array(
	'separ1' => 'Typography {separator}',
	'bodyfontsize' => 'Base Font Size ## The base font size globally affects all font sizes throughout your blog. This can be in any unit (e.g., px, pt, em), but I suggest using a percentage (%). Default is 80%.<br/><em>Format: <strong>Xy</strong> where X = a number and y = its units.</em>',
	'bodyfontfamily' => 'Base Font Family {radio|arial, helvetica, sans-serif|<span style="font-family:arial, helvetica, sans-serif !important;font-weight:bold;">Arial</span> (Helvetica, sans serif)|"courier new", courier, monospace|<span style="font-family:courier new, courier, monospace !important;font-weight:bold;">Courier New</span> (Courier, monospace)|georgia, times, serif|<span style="font-family:georgia, times, serif !important;font-weight:bold;">Georgia</span> (Times, serif)|"lucida console", monaco, monospace|<span style="font-family:lucida console, monaco, monospace !important;font-weight:bold;">Lucida Console</span> (Monaco, monospace)|"lucida sans unicode", lucida grande, sans-serif|<span style="font-family:lucida sans unicode, lucida grande !important;font-weight:bold;">Lucida Sans Unicode</span> (Lucida Grande, sans serif)|tahoma, geneva, sans-serif|<span style="font-family:tahoma, geneva, sans-serif !important;font-weight:bold;">Tahoma</span> (Geneva, sans serif)|"times new roman", times, serif|<span style="font-family:times new roman, times, serif !important;font-weight:bold;">Times New Roman</span> (Times, serif)|"trebuchet ms", helvetica, sans-serif|<span style="font-family:trebuchet ms, helvetica, sans-serif !important;font-weight:bold;">Trebuchet MS</span> (Helvetica, sans serif)|verdana, geneva, sans-serif|<span style="font-family:verdana, geneva, sans-serif !important;font-weight:bold;">Verdana</span> (Geneva, sans serif)} ## The base font family sets the font the post entry text. A fall-back font and the font family are in parentheses. Default is Georgia.',
	'titlefontfamily' => 'Blog Title Font Family {radio|arial, helvetica, sans-serif|<span style="font-family:arial, helvetica, sans-serif !important;font-weight:bold;">Arial</span> (Helvetica, sans serif)|"courier new", courier, monospace|<span style="font-family:courier new, courier, monospace !important;font-weight:bold;">Courier New</span> (Courier, monospace)|georgia, times, serif|<span style="font-family:georgia, times, serif !important;font-weight:bold;">Georgia</span> (Times, serif)|"lucida console", monaco, monospace|<span style="font-family:lucida console, monaco, monospace !important;font-weight:bold;">Lucida Console</span> (Monaco, monospace)|"lucida sans unicode", lucida grande, sans-serif|<span style="font-family:lucida sans unicode, lucida grande !important;font-weight:bold;">Lucida Sans Unicode</span> (Lucida Grande, sans serif)|tahoma, geneva, sans-serif|<span style="font-family:tahoma, geneva, sans-serif !important;font-weight:bold;">Tahoma</span> (Geneva, sans serif)|"times new roman", times, serif|<span style="font-family:times new roman, times, serif !important;font-weight:bold;">Times New Roman</span> (Times, serif)|"trebuchet ms", helvetica, sans-serif|<span style="font-family:trebuchet ms, helvetica, sans-serif !important;font-weight:bold;">Trebuchet MS</span> (Helvetica, sans serif)|verdana, geneva, sans-serif|<span style="font-family:verdana, geneva, sans-serif !important;font-weight:bold;">Verdana</span> (Geneva, sans serif)} ## Sets the font family for the blog title. Use carefully, as you may need to manually adjust the line height in order to get the bottom line just so. A fall-back font and the font family are in parentheses. Default is Times New Roman.',
	'headerfontfamily' => 'Post Header Font Family {radio|arial, helvetica, sans-serif|<span style="font-family:arial, helvetica, sans-serif !important;font-weight:bold;">Arial</span> (Helvetica, sans serif)|"courier new", courier, monospace|<span style="font-family:courier new, courier, monospace !important;font-weight:bold;">Courier New</span> (Courier, monospace)|georgia, times, serif|<span style="font-family:georgia, times, serif !important;font-weight:bold;">Georgia</span> (Times, serif)|"lucida console", monaco, monospace|<span style="font-family:lucida console, monaco, monospace !important;font-weight:bold;">Lucida Console</span> (Monaco, monospace)|"lucida sans unicode", lucida grande, sans-serif|<span style="font-family:lucida sans unicode, lucida grande !important;font-weight:bold;">Lucida Sans Unicode</span> (Lucida Grande, sans serif)|tahoma, geneva, sans-serif|<span style="font-family:tahoma, geneva, sans-serif !important;font-weight:bold;">Tahoma</span> (Geneva, sans serif)|"times new roman", times, serif|<span style="font-family:times new roman, times, serif !important;font-weight:bold;">Times New Roman</span> (Times, serif)|"trebuchet ms", helvetica, sans-serif|<span style="font-family:trebuchet ms, helvetica, sans-serif !important;font-weight:bold;">Trebuchet MS</span> (Helvetica, sans serif)|verdana, geneva, sans-serif|<span style="font-family:verdana, geneva, sans-serif !important;font-weight:bold;">Verdana</span> (Geneva, sans serif)} ## This selects the font for the description and post headings and headings within blog posts. A fall-back font and the font family are in parentheses. Default is Arial.',
	'miscfontfamily' => 'Miscellanea Font Family {radio|arial, helvetica, sans-serif|<span style="font-family:arial, helvetica, sans-serif !important;font-weight:bold;">Arial</span> (Helvetica, sans serif)|"courier new", courier, monospace|<span style="font-family:courier new, courier, monospace !important;font-weight:bold;">Courier New</span> (Courier, monospace)|georgia, times, serif|<span style="font-family:georgia, times, serif !important;font-weight:bold;">Georgia</span> (Times, serif)|"lucida console", monaco, monospace|<span style="font-family:lucida console, monaco, monospace !important;font-weight:bold;">Lucida Console</span> (Monaco, monospace)|"lucida sans unicode", lucida grande, sans-serif|<span style="font-family:lucida sans unicode, lucida grande !important;font-weight:bold;">Lucida Sans Unicode</span> (Lucida Grande, sans serif)|tahoma, geneva, sans-serif|<span style="font-family:tahoma, geneva, sans-serif !important;font-weight:bold;">Tahoma</span> (Geneva, sans serif)|"times new roman", times, serif|<span style="font-family:times new roman, times, serif !important;font-weight:bold;">Times New Roman</span> (Times, serif)|"trebuchet ms", helvetica, sans-serif|<span style="font-family:trebuchet ms, helvetica, sans-serif !important;font-weight:bold;">Trebuchet MS</span> (Helvetica, sans serif)|verdana, geneva, sans-serif|<span style="font-family:verdana, geneva, sans-serif !important;font-weight:bold;">Verdana</span> (Geneva, sans serif)} ## This selects the font for the input areas, post footers, the sidebar content, and other minor (forgotten) elements. Default is Verdana.',
	'postentryalignment' => 'Post Text Alignment {radio|justify|Justified|left|Left aligned ("Ragged right")|right|Right aligned ("Ragged left")} ## Choose one for the text alignment of the post body text. Default is left aligned.',
	'separ2' => 'Layout {separator}',
	'wrapperwidth' => 'Layout Width ## Sets the overall width of content in the browser window. This can be in any unit (e.g., px, pt, %), but I suggest using em for an elastic layout. <strong>Beware!</strong> Setting a % width may anger Internet Explorer. Use a % width with caution. Default is 65em.<br/><em>Format: <strong>Xy</strong> where X = a number and y = its units.</em>',
	'homepage' => 'Home Layout {radio|default|Default layout|custom|Custom layout} ## Choose the layout for the <code>home.php</code>, the home page. The default layout looks the same as the regular index. The custom layout features one specific page/post (the "Featured" page/post) and then the latest blog entry below it. Default is Default layout.<br/><br/><em><strong>Note:</strong> The following three options are only applicable if the Custom layout is selected. If you select custom layout, you must correct the "Featured Page/Post" option below.</em>',
	'customslug' => 'Featured Page/Post ## Use either <code>page_id=X</code> or <code>p=Y</code> to select the "featured" page/post for the custom layout. To display a page, enter the id where X is, e.g., <code>page_id=2</code>. To display a post, enter the id where Y is, e.g., <code>p=42</code>. You can find the id for a page or post in <em>Manage > Pages</em> / <em>> Pages</em> menus. Default is page_id=2.<br/><em><strong>Advanced users:</strong> This function inserts data into <code>query_posts</code>; using <code>query_posts</code> terms other than <code>page_id=</code> or <code>p=</code> above may produce cool/terrible results. Experiment with at your own risk: <a href="http://codex.wordpress.org/Template_Tags/query_posts" title="Template tags/query posts">Codex: query_posts</a>.</em>',
	'customsticky' => 'Featured Page/Post Prefix ## Enter text to prefix the featured page/post title. Example: "Featuring [post/page title]". Leave blank for no text. Default is Featuring.',
	'customlatest' => 'Latest Post Prefix ## Enter text to prefix the latest post title. Example: "Latest [latest post title]". Leave blank for no text. Default is Latest.',
	'separ3' => 'Content {separator}',
	'sidebaraddin' => 'Sidebar Add-in {checkbox|showsidebaraddin|yes|Display the add-in sidebar content} ## If checked, the sidebar content below will appear in the sidebar throughout the blog, except on single post pages. Default is unchecked.<br/><em><strong>Note to Widgets users:</strong> If you are actively using the Widgets plugin, then the sidebar add-in text will not appear.</em>',
	'sidebartext' => 'Sidebar Add-in Content {textarea|10|55} ## Add/edit content for the sidebar section. This text must be parsed in HTML tags. You can use HTML, but beware of special characters: i.e., &amp; = <code>&amp;amp;</code>. Remember that this text <em>will not appear</em> unless "Sidebar Add-in" is checked above. Default is Lorem ipsum&hellip; .',
	'footeraddin' => 'Footer Add-in {checkbox|showfooteraddin|yes|Display the add-in footer text} ## If checked, the footer text below will appear in the footer throughout the blog. Default is unchecked.',
	'footertext' => 'Footer Add-in Text {textarea|10|55} ## Add/edit content for the footer. This text is placed within <code>&lt;p&gt;...&lt;/p&gt;</code> tags. Beware of special characters: i.e., &amp; = <code>&amp;amp;</code>. Remember that this text <em>will not appear</em> unless "Footer Add-in" is checked above. Default is Lorem ipsum&hellip; .',
	),
	__FILE__
);

/************************************************************************************
 * FUNCTION CALLS
 ************************************************************************************/
function blogtxt_bodyfontsize() {
	global $blogtxt;
	if ( $blogtxt->option['bodyfontsize'] ) {
		print 'body { font: ';
		print $blogtxt->option['bodyfontsize'];
		print "/150% ";
	}
}
function blogtxt_bodyfontfamily() {
	global $blogtxt;
	if ( $blogtxt->option['bodyfontfamily'] ) {
		print $blogtxt->option['bodyfontfamily'];
		print "; }\n";
	}
}
function blogtxt_titlefontfamily() {
	global $blogtxt;
	if ( $blogtxt->option['titlefontfamily'] ) {
		print 'h1#title { font-family:';
		print $blogtxt->option['titlefontfamily'];
		print "; }\n";
	}
}
function blogtxt_headerfontfamily() {
	global $blogtxt;
	if ( $blogtxt->option['headerfontfamily'] ) {
		print 'p#description, h2, h3, h4, h5, h6 { font-family: ';
		print $blogtxt->option['headerfontfamily'];
		print "; }\n";
	}
}
function blogtxt_miscfontfamily() {
	global $blogtxt;
	if ( $blogtxt->option['miscfontfamily'] ) {
		print 'p.post-footer, input#author, input#email, input#url, textarea#comment, div.sidebar ul, div.post-entry p.paged-link { font-family: ';
		print $blogtxt->option['miscfontfamily'];
		print "; }\n";
	}
}
function blogtxt_postentryalignment() {
	global $blogtxt;
	if ( $blogtxt->option['postentryalignment'] ) {
		print 'div.post-entry p { text-align: ';
		print $blogtxt->option['postentryalignment'];
		print "; }\n";
	}
}
function blogtxt_wrapperwidth() {
	global $blogtxt;
	if ( $blogtxt->option['wrapperwidth'] ) {
		print 'div#wrapper { width: ';
		print $blogtxt->option['wrapperwidth'];
		print "; }\n";
	}
}
function blogtxt_sidebartext() {
	global $blogtxt;
	if ($blogtxt->option['showsidebaraddin'] == 'yes') {
		print $blogtxt->option['sidebartext'];
	}
}
function blogtxt_footertext() {
	global $blogtxt;
	if ($blogtxt->option['showfooteraddin'] == 'yes') {
		print $blogtxt->option['footertext'];
	}
}
function blogtxt_homepage() {
	global $blogtxt;
	if ($blogtxt->option['custom']) {
		return true;
	} else { 
		return false; } 
}
function blogtxt_customslug() {
	global $blogtxt;
	return $blogtxt->option['customslug'];
}
function blogtxt_customsticky() {
	global $blogtxt;
	if ( $blogtxt->option['customsticky'] ) {
		print '<span class="pre-title">';
		print $blogtxt->option['customsticky'];
		print "</span>&nbsp;";
	}
}
function blogtxt_customlatest() {
	global $blogtxt;
	if ( $blogtxt->option['customlatest'] ) {
		print '<span class="pre-title">';
		print $blogtxt->option['customlatest'];
		print "</span>&nbsp;";
	}
}

/************************************************************************************
 * FUNCTION DEFAULTS
 ************************************************************************************/
if ( !$blogtxt->is_installed() ) {
	$set_defaults['bodyfontsize'] = '80%';
	$set_defaults['bodyfontfamily'] = 'georgia, times, serif';
	$set_defaults['titlefontfamily'] = '"times new roman", times, serif';
	$set_defaults['headerfontfamily'] = 'arial, helvetica, sans-serif';
	$set_defaults['miscfontfamily'] = 'verdana, geneva, sans-serif';
	$set_defaults['postentryalignment'] = 'left';
	$set_defaults['wrapperwidth'] = '65em';
	$set_defaults['homepage'] = 'default';
	$set_defaults['sidebartext'] = '<li><h2>More About</h2><p>Lorem ipusm text here can be customized in the <em>Presentation > blog.txt Themes Options</em> menu. You can also select within the options to exclude this section completely. <em>Most</em> XHTML <strong>tags</strong> will <span style="text-decoration:underline;">work</span>.</p></li>';
	$set_defaults['footertext'] = 'Lorem ipsum text here can be customized in the <em>Presentation > blog.txt Themes Options</em> menu. Inline (non-block) XHTML <strong>elements</strong> will <span style="text-decoration:underline;">work</span>.';
	$set_defaults['customslug'] = 'page_id=2';
	$set_defaults['customsticky'] = 'Featuring';
	$set_defaults['customlatest'] = 'Latest';
	$result = $blogtxt->store_options($set_defaults) ;
}

/************************************************************************************
 * RAOUL'S SIMPLE RECENT COMMENTS (mod) W/ PERMISSION http://www.raoul.shacknet.nu/2006/01/15/simple-recent-comments-wordpress-plugin/
 ************************************************************************************/
function blogtxt_src($src_count=7, $src_length=75, $pre_HTML='', $post_HTML='') {
	global $wpdb;
	$sql = "SELECT DISTINCT ID, post_title, post_password, comment_ID, comment_post_ID, comment_author, comment_date_gmt, comment_approved, comment_type, 
			SUBSTRING(comment_content,1,$src_length) AS com_excerpt 
		FROM $wpdb->comments 
		LEFT OUTER JOIN $wpdb->posts ON ($wpdb->comments.comment_post_ID = $wpdb->posts.ID) 
		WHERE comment_approved = '1' AND comment_type = '' AND post_password = '' 
		ORDER BY comment_date_gmt DESC 
		LIMIT $src_count";
	$comments = $wpdb->get_results($sql);
	$output = $pre_HTML;
	$output .= "\n<ul>";
	if ($comments)
    {
		foreach ($comments as $comment) {
			$output .= "\n\t<li><strong><a href=\"" . get_permalink($comment->ID) . "#comment-" . $comment->comment_ID  . "\" title=\"on " . $comment->post_title . "\">" . $comment->comment_author . "</a></strong> <small> on <em>" . $comment->post_title . "</em></small><p>" . strip_tags($comment->com_excerpt) . "...</p></li>";
		}
	}
    else
    {
        $output .= "\n\t<li>No comments yet</li>";
    }
	$output .= "\n</ul>";
	$output .= $post_HTML;
	echo $output;
}

/************************************************************************************
 * CALL FOR WIDGETS PLUGIN, V.1
 ************************************************************************************/
if( function_exists('register_sidebar') ) {
	register_sidebar( array (
		'name' => 'Main Sidebar',
	) );
}
function widget_mytheme_search() {
?>
<li id="search">
	<h2><label for="s">Search</label></h2>
	<form id="searchform" method="get" action="<?php bloginfo('home'); ?>/">
		<div>
			<input id="s" name="s" type="text" value="<?php echo wp_specialchars($s, 1); ?>" size="5" tabindex="1" />
			<input id="searchsubmit" name="searchsubmit" type="submit" value="Find" tabindex="2"/>
		</div>
	</form> 
</li>
<?php
}
if ( function_exists('register_sidebar_widget') )
    register_sidebar_widget(__('Search'), 'widget_mytheme_search');
?>
