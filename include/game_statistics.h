#ifndef GAME_STATISTICS_H
#define GAME_STATISTICS_H

/* Add a door to the number of secret doors that can be found. This function
 * must be called for the doors which haven't been added in an update_nb_sdoor()
 */
void statistic_add_sdoor();

/* Shall be called once by secret door discovery */
void statistic_add_sdoor_discovery();

/* Add a door to the number of secret corridors that can be found. This function
 * must be called for the secret corridors which haven't been added in an
 * update_nb_sdoor()
 */
void statistic_add_scorr();

/* Shall be called once by secret corridor discovery */
void statistic_add_scorr_discovery();

/* Must be called each time a new level is reached, but not twice for the same
 * level. */
void update_nb_sdoors();

/* Must be called each time a new level is reached, but not twice for the same
 * level. */
void update_nb_scorrs();

/* Must be called each time a new level is reached, but not twice for the same
 * level. */
void update_reachable_squares();

/* Must be called at least once each time a new square is reached (might be
 * called more than once on the same square)
 */
void update_reached_squares();

/* Return the number of secret doors reachable in all the level reached. */
int get_nb_sdoors();

/* Return the number of secret doors found during the game. */
int get_nb_sdoors_found();

/* Return the number of secret corridors reachable in all the level reached.
 */
int get_nb_scorrs();

/* Return the number of secret corridors found during the game. */
int get_nb_scorrs_found();

/* Return the number of squares reachable in all the level reached. */
int get_nb_squares_reachable();

/* Return the number of square reached during the game. */
int get_nb_squares_reached();


#endif