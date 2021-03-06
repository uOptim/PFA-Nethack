/**
 * @file   main.h
 * @author B. Ruelle, D. Bitonneau, L. Hofer
 * @date   March, 2013
 * @brief  Documentation main page
 *
 * @mainpage
 * @section Description
 *
 * This project is aimed at defining a generic way to make bots for NetHack
 * and to evaluate them. It has been built to be compatible with UNIX systems
 * but could be ported to others systems with some modifications.
 *
 * @section download Downloading the project
 *
 * Our project can be found on the following repository:
 * https://github.com/medrimonia/PFA-Nethack/
 *
 * If you want to have the latest version of the project, go in the desired
 * directory and run this command from a shell:
 *
 * @code
 * git clone https://github.com/medrimonia/PFA-Nethack.git
 * @endcode
 *
 * @section getting_started Getting started
 *
 * You can read the READMEs to have some details about the different folders
 * and which packages are required to have everything working properly.
 *
 * To build the project from scratch just go in the main directory
 * (PFA-Nethack) and run from a shell:
 *
 * @code
 * make
 * @endcode
 *
 * This will download NetHack sources if they have not already been
 * downloaded, patch them with our own sources, and build everything.
 *
 * Then you can run bots on this modified version of the game using:
 *
 * @code
 * make run
 * @endcode
 *
 * @section Modules
 *
 * You will find detailed explanations of the main functions in the following
 * files :
 * - @link database_manager.h @endlink
 * - @link game_result.h      @endlink
 * - @link game_statistics.h  @endlink
 * - @link middleman.h        @endlink
 * - @link pfamain.h          @endlink
 */
