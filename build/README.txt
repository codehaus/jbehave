The default target "rebuild" cleans out the delete_me working dir and builds
the jbehave.jar and the full source binary jbehave-src.zip.

To build a release:

1. Take a svn copy under https://svn.codehaus.org/jbehave/releases
2. Log into beaver.codehaus.org as jbehave
3. Check out under ~jbehave/work on beaver.codehaus.org
4. In the build directory, ant -Ddist_dir=$HOME/dist -Dwebsite_dist_dir=$HOME/public_html dist