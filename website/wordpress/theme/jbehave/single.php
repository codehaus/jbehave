<?php get_header(); ?>

		<div id="content" class="narrowcolumn">

<?php if (have_posts()) : while (have_posts()) : the_post(); ?>

			<div id="post-<?php the_ID(); ?>" class="post">
				<h2 class="post-title"><?php the_title(); ?></h2>
				<div class="post-entry">
					<?php the_content(); ?>
					<?php link_pages('<p class="paged-link">Pages: ', '</p>', 'number'); ?>
					<?php edit_post_link('Edit this post.', '<p>', '</p>'); ?>
				</div><!-- END POST-ENTRY -->
				<!-- <?php trackback_rdf(); ?> -->
			</div><!-- END POST -->

<?php comments_template(); ?>

			<div class="navigation">
				<div class="alignleft"><?php previous_post_link('&laquo; %link') ?></div>
				<div class="alignright"><?php next_post_link('%link &raquo;') ?></div>
			</div><!-- END NAVIGATION -->

<?php endwhile; else : ?>

			<div id="post-error" class="post">
				<h2 class="post-title"><span class="pre-title">404</span> Page Not Found</h2>
				<div class="post-entry">
					<p>There's been a problem finding the page you're looking for. Apologies. Perhaps . . .</p>
					<ul>
						<li>the page your looking for was moved;</li>
						<li>your referring site gave you an incorrect address; or</li>
						<li>something went terribly wrong.</li>
					</ul>
					<p>Use the search box and see if you can't find what you're looking for.</p>
				</div><!-- END POST-ENTRY -->
			</div><!-- END POST -->

<?php endif; ?>

		</div><!-- END CONTENT / NARROWCOLUMN -->
	</div><!-- END CONTAINER  -->

	<div id="col1" class="sidebar">
		<ul>
			<li id="home-link">
				<h2><a href="<?php echo get_settings('home'); ?>/" title="<?php bloginfo('name'); ?>">&laquo; Home</a></h2>
			</li>
			<li id="about-this-post">
				<h2>About This Post</h2>
				<ul>
					<li>Written by <?php the_author(); ?></li>
					<li><?php the_time('F jS, Y') ?> at <?php the_time() ?></li>
				</ul>
			</li>
			<li id="category-links">
				<h2>Categories</h2>
				<ul>
					<li><?php the_category('</li><li>') ?></li>
				</ul>
			</li>
<?php if (('open' == $post-> comment_status) && ('open' == $post->ping_status)) { ?>
			<li id="interact-links">
				<h2>Interact</h2>
				<ul>
					<li class="comment-link"><a href="#respond">Post a comment</a></li>
					<li class="trackback-link"><a href="<?php trackback_url(true); ?>" rel="trackback">Trackback URI</a></li>
				</ul>
			</li>
<?php } elseif (!('open' == $post-> comment_status) && ('open' == $post->ping_status)) { ?>
			<li id="interact-links">
				<h2>Interact</h2>
				<ul>
					<li class="trackback-link"><a href="<?php trackback_url(true); ?>" rel="trackback">Trackback URI</a></li>
				</ul>
			</li>
<?php } elseif (('open' == $post-> comment_status) && !('open' == $post->ping_status)) { ?>
			<li id="interact-links">
				<h2>Interact</h2>
				<ul>
					<li class="comment-link"><a href="#respond">Post a comment</a></li>
				</ul>
			</li>
<?php } elseif (!('open' == $post-> comment_status) && !('open' == $post->ping_status)) { ?>
<?php } ?>
			<li id="rss-links">
				<h2>RSS Feeds</h2>
				<ul>
					<li class="rss-link"><?php comments_rss_link('Comments to this post'); ?></li>
					<li class="rss-link"><a href="<?php bloginfo('rss2_url'); ?>" title="<?php bloginfo('name'); ?> RSS 2.0 (XML) Feed" rel="alternate" type="application/rss+xml">All posts</a></li>
					<li class="rss-link"><a href="<?php bloginfo('comments_rss2_url'); ?>" title="<?php bloginfo('name'); ?> Comments RSS 2.0 (XML) Feed" rel="alternate" type="application/rss+xml">All comments</a></li>
				</ul>
			</li>
			<li id="search">
				<h2><label for="s">Search</label></h2>
				<?php include (TEMPLATEPATH . '/searchform.php'); ?>
			</li>
		</ul>
	</div><!-- END COL2 / SIDEBEAR -->

<?php get_footer(); ?>