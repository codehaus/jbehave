<?php get_header(); ?>

		<div id="content" class="narrowcolumn">

<?php /* OPTION VARIABLE FOR DISPLAYING BLOG.TXT HOME: IF IS CUSTOM, THEN... */ global $blogtxt; if ($blogtxt->option['homepage'] == 'custom') { ?>

<?php if ( have_posts()) : query_posts(''.blogtxt_customslug().''); while (have_posts()) : the_post(); ?>

			<div id="post-front" class="post">
				<h2 class="post-title"><?php blogtxt_customsticky(); ?><?php the_title(); ?></h2>
				<div class="post-entry">
					<?php the_content('<span class="more-link">Continue Reading &raquo;</span>'); ?>
					<?php link_pages('<p class="paged-link">', '</p>', 'next', 'Continued &raquo;', '&laquo; Previously', '%'); ?>
				</div><!-- END POST-ENTRY -->
			</div><!-- END POST -->

<?php endwhile; else : endif; ?>

<?php if (have_posts()) : query_posts('showposts=1'); while (have_posts()) : the_post(); ?>

			<div id="post-<?php the_ID(); ?>" class="post">
				<h2 class="post-title"><?php blogtxt_customlatest(); ?><a href="<?php the_permalink() ?>" title="Permalink to <?php the_title(); ?>" rel="bookmark"><?php the_title(); ?></a></h2>
				<div class="post-entry">
					<?php the_content('<span class="more-link">Continue Reading &raquo;</span>'); ?>
					<?php link_pages('<p class="paged-link">', '</p>', 'next', 'Continued &raquo;', '&laquo; Previously', '%'); ?>
				</div><!-- END POST-ENTRY -->
				<p class="post-footer">&para; Posted <a href="<?php the_permalink() ?>" title="Permalink to <?php the_title(); ?>" rel="permalink"><?php the_time('d F Y'); ?></a> &sect; <?php the_category(', ') ?> <?php edit_post_link('Edit', ' &radic; ', ''); ?> &Dagger; <?php comments_popup_link('Comments (0)', 'Comments (1)', 'Comments (%)'); ?></p>
				<!-- <?php trackback_rdf(); ?> -->
			</div><!-- END POST -->

<?php endwhile; else : ?>

			<div id="post-error" class="post">
				<h2 class="post-title"><span class="pre-title">404</span> Page Not Found</h2>
				<div class="post-entry">
					<p>There's been a problem finding the page you're looking for.</p>
					<p>The most likely problem is that the data for the the "Featured Post/Page" option hasn't been configured correctly. You must enter the <em>exact</em> information, otherwise this option won't work.</p>
					<ol>
						<li>First, find the post or page you want to display here. Note the id. You can find the ids in the far left column in either the <em>Manage > Posts</em> or <em>Manage > Pages</em> menus.</li>
						<li>Next, go the blog.txt Theme Options menu. If you have selected to show a page, you will use <code>page_id=</code> and the page id number. If you have selected to show a post, you will use <code>p=</code> and the post id number.</li>
					</ol>
					<ul>
						<li>For example, if I wanted to show my "About" page and its id is 1, then I would use <code>page_id=1</code>. If I wanted to show some post I wrote about dogs, and it had an id of 231, then I would use <code>p=231</code>.</li>
					</ul>
				</div><!-- END POST-ENTRY -->
			</div><!-- END POST -->

<?php endif; ?>

<?php } else { /* END OF BLOG.TXT HOME, BEGIN IF REGULAR INDEX IS BEING USED: IF NOT CUSTOM, THEN... */ ?>

<?php if (have_posts()) : ?> 
<?php while (have_posts()) : the_post(); ?>

			<div id="post-<?php the_ID(); ?>" class="post">
				<h2 class="post-title"><a href="<?php the_permalink() ?>" title="Permalink to <?php the_title(); ?>" rel="bookmark"><?php the_title(); ?></a></h2>
				<div class="post-entry">
					<?php the_content('<span class="more-link">Continue Reading &raquo;</span>'); ?>
					<?php link_pages('<p class="paged-link">Pages: ', '</p>', 'number'); ?>
				</div><!-- END POST-ENTRY -->
				<p class="post-footer">&para; Posted <a href="<?php the_permalink() ?>" title="Permalink to <?php the_title(); ?>" rel="permalink"><?php the_time('d F Y'); ?></a> &sect; <?php the_category(', ') ?> <?php edit_post_link('Edit', ' &radic; ', ''); ?> &Dagger; <?php comments_popup_link('Comments (0)', 'Comments (1)', 'Comments (%)'); ?></p>
				<!-- <?php trackback_rdf(); ?> -->
			</div><!-- END POST -->

<?php endwhile; ?>

			<div class="navigation">
				<div class="alignleft"><?php next_posts_link('&laquo; Earlier Posts') ?></div>
				<div class="alignright"><?php previous_posts_link('Later Posts &raquo;') ?></div>
			</div>

<?php else : ?>

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

<?php } /* FINISHED ASKING IF CUSTOM / DEFAULT LAYOUT HAS BEEN CHOSEN  */  ?>

		</div><!-- END CONTENT / NARROWCOLUMN -->
	</div><!-- END CONTAINER  -->

<?php get_sidebar(); ?>
<?php get_footer(); ?>