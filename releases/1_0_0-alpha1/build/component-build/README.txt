These are not the build droids you are looking for...

This directory is an "external", which means it is a copy of a directory elsewhere in the code base.

The actual directory is in jbehave/build/component-build, which is shared as "build" by the core
and all the components. Clever, eh?

Any changes you make here cannot be committed. If you want to change the component build process,
you will need to edit the originals in jbehave/build/component-build, but *PLEASE* ask Dan or Damian
first, because they know how this nest of snakes hangs together :)
