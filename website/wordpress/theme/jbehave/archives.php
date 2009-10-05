<?php
/*
TEMPLATE NAME: Archives Page
*/
?>
<?php get_header(); ?>

		<div id="content" class="narrowcolumn">

<?php if (have_posts()) : while (have_posts()) : the_post(); ?>

			<div id="post-<?php the_ID(); ?>" class="post">
				<h2 class="post-title"><?php the_title(); ?></h2>
				<div class="post-entry">
					<?php the_content(); ?>
					<h3>Category Archives</h3>
					<ul>
						<?php wp_list_cats('sort_column=name&optioncount=1&feed=(RSS)&feed_image='.get_bloginfo('template_url').'/images/feed.png&hierarchical=1'); ?>
					</ul>
					<h3>Monthly Archives</h3>
					<ul>
						<?php wp_get_archives('type=monthly&show_post_count=1'); ?>
					</ul>
					<?php edit_post_link('Edit this page.', '<p>', '</p>'); ?>
				</div><!-- END POST-ENTRY -->
				<!-- <?php trackback_rdf(); ?> -->
			</div><!-- END POST -->

<?php endwhile; endif; ?>

		</div><!-- END CONTENT / NARROWCOLUMN -->
	</div><!-- END CONTAINER  -->

<?php get_sidebar(); ?>
<?php get_footer(); ?>