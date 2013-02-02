#!/bin/bash

#TO CHANGE FOR EACH EXPERIMENT (use as parameter, or detect later maybe)
FOLDER="99_games_java_sp"
DATABASE="games_result.db"

# DATAS DEPENDING OF COLUMNS
FIELDS[0]="nb_squares_explored"
FIELDS[1]="nb_squares_reachable"
FIELDS[2]="nb_sdoors_found"
FIELDS[3]="nb_sdoors_reachable"
FIELDS[4]="nb_scorrs_found"
FIELDS[5]="nb_scorrs_reachable"
YMAX[0]=6
YMAX[1]=4
YMAX[2]=25
YMAX[3]=20
YMAX[4]=40
YMAX[5]=30


for ((i = 0; i < 6 ; i++))
do
		sqlite3 ${FOLDER}/${DATABASE} "select ${FIELDS[i]}, count(*) from seek_secret group by ${FIELDS[$i]}" > ${FOLDER}/${FIELDS[i]}_result.txt

		gnuplot -persist <<PLOT
set yrange [0:${YMAX[i]}]
set xrange [-1:*]

set title "distribution of random data"
set datafile separator "|"

#In case for building an eps-file ...
set terminal postscript enhanced color solid eps 15
set output "${FOLDER}/${FIELDS[i]}.eps"

plot '${FOLDER}/${FIELDS[i]}_result.txt' with impulses

#replot

quit
PLOT
		rm ${FOLDER}/${FIELDS[i]}_result.txt
done