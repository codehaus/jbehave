<?php header("HTTP/1.1 404 Not Found"); ?>
<?php get_header(); ?>

		<div id="content" class="narrowcolumn">
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
		</div><!-- END CONTENT / NARROWCOLUMN -->
	</div><!-- END CONTAINER  -->

<?php get_sidebar(); ?>
<?php get_footer(); ?>