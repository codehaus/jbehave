<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://gmpg.org/xfn/11">
<title><?php wp_title(''); ?> <?php if ( !( is_404() ) && ( is_single() ) or ( is_page() ) or ( is_archive() ) ) { ?> at <?php } ?> <?php bloginfo('name'); ?></title>
<meta http-equiv="content-type" content="<?php bloginfo('html_type'); ?>; charset=<?php bloginfo('charset'); ?>" />
<meta name="generator" content="WordPress <?php bloginfo('version'); ?>" /><!-- LEAVE FOR STATS, PLEASE -->
<meta name="description" content="<?php bloginfo('description'); ?>" />
<link rel="stylesheet" title="blog.txt" href="<?php bloginfo('stylesheet_url'); ?>" type="text/css" media="all" />
<link rel="alternate" type="application/rss+xml" title="<?php bloginfo('name'); ?> RSS 2.0" href="<?php bloginfo('rss2_url'); ?>" />
<link rel="alternate" type="application/rss+xml" title="<?php bloginfo('name'); ?> Comments RSS 2.0" href="<?php bloginfo('comments_rss2_url'); ?>" />
<link rel="pingback" href="<?php bloginfo('pingback_url'); ?>" />
<link rel="start" href="<?php echo get_settings('home'); ?>/" title="<?php bloginfo('name'); ?>" />
<?php wp_get_archives('type=monthly&format=link'); ?>
<?php wp_head(); ?>
<style type="text/css" media="all">
/*<![CDATA[*/
<?php /* CSS FOR THEME OPTIONS */
	blogtxt_bodyfontsize();
	blogtxt_bodyfontfamily();
	blogtxt_titlefontfamily();
	blogtxt_headerfontfamily();
	blogtxt_miscfontfamily();
	blogtxt_postentryalignment();
	blogtxt_wrapperwidth();
?>
/*]]>*/
</style>
</head>
<body>

<div id="wrapper">

	<div id="container">

		<div id="header">
			<h1 id="title"><a href="<?php echo get_settings('home'); ?>/" title="<?php bloginfo('name'); ?>"><?php bloginfo('name'); ?></a></h1>
<?php /* IF THIS IS A CATEGORY LISTING */ if ( is_category() ) { ?>
			<p id="description"><?php single_cat_title(''); ?> &mdash; <?php echo category_description(); ?></p>
<?php /* IF THIS IS A YEARLY ARCHIVE */ } elseif ( is_day() ) { ?>
			<p id="description">Archives for <?php the_time('l, F jS, Y'); ?></p>
<?php /* IF THIS IS A MONTHLY ARCHIVE */ } elseif ( is_month() ) { ?>
			<p id="description">Archives for <?php the_time('F Y'); ?></p>
<?php /* IF THIS IS A YEARLY ARCHIVE */ } elseif ( is_year() ) { ?>
			<p id="description">Archives for <?php the_time('Y'); ?></p>
<?php /* IF THIS IS A MONTHLY ARCHIVE */ } else { ?>
			<p id="description"><?php bloginfo('description'); ?></p>
<?php } ?>
		</div><!-- END HEADER -->
