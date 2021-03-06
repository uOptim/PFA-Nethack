#!/bin/bash

if [ $# -lt 1 ]
then
		echo "usage: `basename $0` <database_name>"
		exit
fi

DATABASE=$1;

if [ ! -f $DATABASE ]
then
		echo "'$DATABASE' doesn't exist, please provide a valid database"
		exit
fi

DB_PATH=$(dirname $DATABASE)

LINE_DATA="${DB_PATH}/sc_line_data.csv"
COLUMN_DATA="${DB_PATH}/sc_col_data.csv"

LINE_REQUEST="'select sc_line, count(*) as nb_scorrs
                 from scorrs
                 group by sc_line'"
COLUMN_REQUEST="'select sc_column, count(*) as nb_scorrs
                   from scorrs
                   group by sc_column'"

# This particular syntax is due to a problem when processing argument with
# 'select ... '
echo $LINE_REQUEST | xargs sqlite3 -header -csv $DATABASE > $LINE_DATA
echo $COLUMN_REQUEST | xargs sqlite3 -header -csv $DATABASE > $COLUMN_DATA

#Line graph
gnuplot -persist <<PLOT
set xrange [0:21]
set yrange [0:*]

set xlabel "Line"
set ylabel "NbScorrs"

set datafile separator ","
set key autotitle columnhead

set tics out
set style fill solid

#In case for building an eps-file ...
#set terminal postscript enhanced color solid eps 15
#set output "${DB_PATH}/sc_line_graph.eps"

#In case for building a png-file ...
set terminal png size 1024,768
set output "${DB_PATH}/sc_line_graph.png"

plot '${LINE_DATA}' with boxes
quit
PLOT

#Column graph
gnuplot -persist <<PLOT
set xrange [0:80]
set yrange [0:*]

set xlabel "Column"
set ylabel "NbScorrs"

set datafile separator ","
set key autotitle columnhead

set tics out
set style fill solid

#In case for building an eps-file ...
#set terminal postscript enhanced color solid eps 15
#set output "${DB_PATH}/sc_column_graph.eps"

#In case for building a png-file ...
set terminal png size 1024,768
set output "${DB_PATH}/sc_column_graph.png"

plot '${COLUMN_DATA}' with boxes
quit
PLOT