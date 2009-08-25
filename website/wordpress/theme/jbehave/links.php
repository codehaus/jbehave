<?php
/*
TEMPLATE NAME: Links Page
*/
?>
<?php get_header(); ?>

		<div id="content" class="narrowcolumn">

<?php if (have_posts()) : while (have_posts()) : the_post(); ?>

			<div id="post-<?php the_ID(); ?>" class="post">
				<h2 class="post-title"><?php the_title(); ?></h2>
				<div class="post-entry">
					<?php the_content(); ?>
					<?php $link_cats = $wpdb->get_results("SELECT cat_id, cat_name FROM $wpdb->linkcategories");
						foreach ($link_cats as $link_cat) { ?>
						<h3><?php echo $link_cat->cat_name; ?></h3>
						<ul id="linkcat-<?php echo $link_cat->cat_id; ?>">
							<?php wp_get_links($link_cat->cat_id); ?>
						</ul>
					<?php } ?>
					<?php edit_post_link('Edit this page.', '<p>', '</p>'); ?>
				</div><!-- END POST-ENTRY -->
				<!-- <?php trackback_rdf(); ?> -->
			</div><!-- END POST -->

<?php endwhile; endif; ?>

		</div><!-- END CONTENT / NARROWCOLUMN -->
	</div><!-- END CONTAINER  -->

<?php get_sidebar(); ?>
<?php get_footer(); ?>