	<div id="col1" class="sidebar">
		<ul>
<?php /* HOME PAGE LINK FOR OTHER PAGES */ if ( !is_home() || is_paged() ) { ?>
		<li id="home-link">
			<h2><a href="<?php echo get_settings('home'); ?>/" title="<?php bloginfo('name'); ?>">&laquo; Home</a></h2>
		</li>
<?php } ?>
<?php /* FUNCTION FOR SIDEBAR WIDGETS */ if ( !function_exists('dynamic_sidebar') || !dynamic_sidebar('Main Sidebar') ) : ?>
<?php /* DISPLAYS THE SIDEBAR ADD-IN TEXT, IF SELECTED IN THE THEME OPTIONS MENU */ blogtxt_sidebartext(); ?>
			<?php wp_list_pages('title_li=<h2>Contents</h2>' ); ?>
<?php /* IF THIS IS THE HOME PAGE */ if ( is_home() ) { ?>
<?php /* DISPLAYS CUSTOM/DEFAULT VARIABLE CONTENT FOR LAYOUTS: PREVIOUS POSTS FOR CUSTOM LAYOUT */
global $blogtxt; 
if ($blogtxt->option['homepage'] == 'custom') { ?>
			<li id="recent-posts">
				<h2>Previous Posts</h2>
				<ul>
			<?php get_posts('numberposts=4&offset=1'); foreach($posts as $post) : setup_postdata($post); ?>
					<li>
						<strong><a href="<?php the_permalink() ?>" title="Continue reading <?php the_title(); ?>" rel="bookmark"><?php the_title(); ?></a></strong>
						<p><?php the_excerpt_rss(); ?> <a href="<?php the_permalink() ?>#comments" title="Comments to <?php the_title(); ?>"><?php comments_number('(0)','(1)','(%)'); ?></a></p>
					</li>
			<?php endforeach; ?>
				</ul>
			</li>
<?php } else { ?>
<?php /* DISPLAYS RECENT COMMENTS ON THE HOME PAGE FOR DEFAULT LAYOUT */ ?>
			<li id="recent-comments">
				<h2>Recent Comments</h2>
				<?php blogtxt_src(5, 75, '', ''); ?>
				<?php /* YOU CAN CHANGE VARIABLES FOR THE RECENT COMMENTS. (X, Y, 'BEFORE', 'AFTER') WHERE X=NUMBER OF COMMENTS, Y=COMMENT LENGTH, BEFORE=TEXT BEFORE, AND AFTER=TEXT AFTER */ ?>
			</li>
<?php } ?>
			<li id="category-links">
				<h2>Categories</h2>
				<ul>
					<?php wp_list_cats('sort_column=name&hierarchical=1'); ?>
				</ul>
			</li>
<?php /* IF THIS IS A CATEGORY-BASED ARCHIVE */ } elseif ( is_category() ) { ?>
			<li id="category-links">
				<h2>Categories</h2>
				<ul>
					<?php wp_list_cats('sort_column=name&hierarchical=1'); ?>
				</ul>
			</li>
<?php /* IF THIS IS A DATE-BASED ARCHIVE */ } elseif ( is_date() ) { ?>
			<li id="archive-links">
				<h2>Archives</h2>
				<ul>
					<?php wp_get_archives('type=monthly'); ?>
				</ul>
			</li>
<?php } /* IF THIS IS THE HOME PAGE, AGAIN */ if ( is_home() ) { ?>
			<li id="rss-links">
				<h2>RSS Feeds</h2>
				<ul>
					<li class="rss-link"><a href="<?php bloginfo('rss2_url'); ?>" title="<?php bloginfo('name'); ?> RSS 2.0 (XML) Feed" rel="alternate" type="application/rss+xml">All posts</a></li>
					<li class="rss-link"><a href="<?php bloginfo('comments_rss2_url'); ?>" title="<?php bloginfo('name'); ?> Comments RSS 2.0 (XML) Feed" rel="alternate" type="application/rss+xml">All comments</a></li>
				</ul>
			</li>
			<li id="info-copyright">
				<h2><?php bloginfo('name'); ?></h2>
				<ul>
					<li>&copy; <?php echo(date('Y')); ?> jbehave.org</li>
					<li>Powered by <a href="http://wordpress.org/" title="WordPress">WordPress</a></li>
					<li>Compliant: <a href="http://validator.w3.org/check?uri=<?php echo get_settings('home'); ?>&amp;outline=1&amp;verbose=1" title="Valid XHTML 1.0 Strict" rel="nofollow">XHTML</a> &amp; <a href="http://jigsaw.w3.org/css-validator/validator?uri=<?php bloginfo('stylesheet_url'); ?>&amp;profile=css2&amp;warning=no" title="Valid CSS" rel="nofollow">CSS</a></li>
					<?php wp_register(); ?>
					<li><?php wp_loginout(); ?></li>
					<?php wp_meta(); ?>
				</ul>
			</li>
<?php } ?>
			<li id="search">
				<h2><label for="s">Search</label></h2>
				<?php include (TEMPLATEPATH . '/searchform.php'); ?>
			</li>
<?php /* END FOR WIDGETS CALL */ endif; ?>
		</ul>
	</div><!-- END COL2 / SIDEBEAR -->
