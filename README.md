PFA-Nethack
===========

Nethack-3.4.3 can be downloaded, installed and patched for mods with the
nh-setup.sh script. A Makefile is also provided for simpler usage.

The folder 'architecture' contains more informations about the architecture and
the database used to store all the games details.

The folder 'install' contains some patches needed to work with this modified
version of NetHack, those patches are mainly adding hooks in the game. A
modified version of NetHack's Makefile is also contained in this folder,
ensuring that the files from the PFA will be used.

The folder 'patches' contains some patches that disable hunger, monsters or
other features of NetHack. Those patches are mainly modifying the game
experience but aren't needed to run bots (even if the bots performance are
strongly increased by their application).

The folder 'bots' contains all the bots developed during the project

The folder 'include' contains the headers of the used in addition to the basic
NetHack version.

The folder 'src' contains the implementation of the additional functionalities
provided by our PFA.

The folder 'scripts' contains scripts allowing to run several games, to generate
graphs or other utilities based on our programs. Every script is described in
'scripts/README.md'

The folder 'viewer' contains a program called `viewer.pl` which can show
replays of games played by bots. It also contains a program called `toTikZ.pl`
which can produce beautiful images summarising bots movements during a game.


## Used package

Here's the list of the packages required for the provided bots programmed in Java
* libunixsocket-java _The package might also be named libmatthew-java_
* libsqlite3

## Downloading the project

If you wish to download our project (either to use it or to participate), run
this command from the directory where you want the project to be downloaded:
`git clone https://github.com/medrimonia/PFA-Nethack.git`

## Compiling

The pached version of NetHack binary can be built using the script nh-setup.sh.
For more convenience a Makefile is available, thus just run `make`.

Some bots need to be built by using the appropriate script. Again, Makefiles
are provided and all the bots can be built by just running `make bots` from
the project's root directory. Hint: when using `make`, bots will also be built.

The perl modules viewer and dummy_client require some dependencies in order to
run (refer to the appropriate README).

## Getting started

Once the project is compiled you can start to play with the modified version
of NetHack by using:
`make play`

You can also select a bot and see its movements by using:
`make run`

## Further development

If you wish to develop something for this project, please checkout the branch
`dev` before. Once on this branch, you can create another branch to write your
own code. You may want sometimes to merge your branch with the branch `dev` so
that other contributors can test your code. Then, once everything is ready and
tested, `dev` can be merged with `master`.

