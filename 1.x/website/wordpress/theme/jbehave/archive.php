<?php get_header(); ?>

		<div id="content" class="narrowcolumn">

<?php if (have_posts()) : ?> 
<?php while (have_posts()) : the_post(); ?>

			<div id="post-<?php the_ID(); ?>" class="post">
				<h2 class="post-title"><a href="<?php the_permalink() ?>" title="Permalink to <?php the_title(); ?>" rel="bookmark"><?php the_title(); ?></a></h2>
				<div class="post-entry">
					<?php the_excerpt('<span class="more-link">Continue Reading &raquo;</span>'); ?>
				</div><!-- END POST-ENTRY -->
				<p class="post-footer">&para; Posted <a href="<?php the_permalink() ?>" title="Permalink to <?php the_title(); ?>" rel="permalink"><?php the_date(); ?></a> &sect; <?php the_category(', ') ?> <?php edit_post_link('Edit', ' &radic; ', ''); ?> &Dagger; <?php comments_popup_link('Comments (0)', 'Comments (1)', 'Comments (%)'); ?></p>
			</div><!-- END POST -->

<?php endwhile; ?>

			<div class="navigation">
				<div class="alignleft"><?php next_posts_link('&laquo; Earlier Posts') ?></div>
				<div class="alignright"><?php previous_posts_link('Later Posts &raquo;') ?></div>
			</div>

<?php else : ?>


			<div id="post-error" class="post">
				<h2 class="post-title"><span style="background:#fff;color:#aaa;">404</span> Page Not Found</h2>
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

<?php get_sidebar(); ?>
<?php get_footer(); ?>