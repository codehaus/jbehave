<?php get_header(); ?>

		<div id="content" class="narrowcolumn">

<?php if (have_posts()) : ?>

			<div id="post-search" class="post">
				<h2 class="post-title">Search Results</h2>
				<div class="post-entry">
					<p>Search completed successfully for &#8220;<strong><?php echo wp_specialchars($s); ?></strong>&#8221;. Results are below.</p>
					<ol class="searchresults">
<?php while (have_posts()) : the_post(); ?>
						<li>
							<p><strong><a href="<?php the_permalink() ?>" rel="bookmark" title="Permalink to <?php the_title(); ?>"><?php the_title(); ?></a></strong> <small>Posted <?php the_date('d M Y'); ?> under <?php the_category(', ') ?></small></p>
							<p class="excerpt"><?php the_excerpt_rss(); ?></p>
						</li>
<?php endwhile; ?>
					</ol>
				</div><!-- END POST-ENTRY -->
			</div><!-- END POST -->

			<div class="navigation">
				<div class="alignleft"><?php next_posts_link('&laquo; Earlier Posts') ?></div>
				<div class="alignright"><?php previous_posts_link('Later Posts &raquo;') ?></div>
			</div>

<?php else : ?>

			<div id="post-search" class="post">
				<div class="post-header">
					<h2 class="post-title">Search Results</h2>
				</div><!-- END POST-HEADER -->
				<div class="post-entry">
					<p>No results matching &#8220;<strong><?php echo wp_specialchars($s); ?></strong>&#8221;. Please change your search criteria and <label for="s">try again</label>.</p>
				</div><!-- END POST-ENTRY -->
			</div><!-- END POST -->

<?php endif; ?>

		</div><!-- END CONTENT / NARROWCOLUMN -->
	</div><!-- END CONTAINER  -->

<?php get_sidebar(); ?>
<?php get_footer(); ?>