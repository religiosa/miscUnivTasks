#!/bin/bash

hottest_temp=0
hottest_file="null"

if [ ! -d $1 ]	#check if the input is a directory, if not exit and print usage
then
	echo "Usage: $0 <directory>"
	exit
fi

all_files=$(find $1 -name "hp-temps.txt")


for file in $all_files
do
		current_temp=`grep --no-filename PROCESSOR_ZONE $file | sed -e 's/\ \+/,/g' -e 's/\//,/g' | cut -d , -f 3 | sed 's/C//g'`

		if [ $current_temp -gt $hottest_temp ]
		then
			hottest_temp=$current_temp
			hottest_file=$file
		fi
done

parsed_date=$(echo $hottest_file | sed -n 's/.*\([0-9][0-9][0-9][0-9].[0-9][0-9].[0-9][0-9]\).*/\1/p')
parsed_time=$(echo $hottest_file | sed -n 's/.*\([0-9][0-9]:[0-9][0-9]\).*/\1/p')

echo "$parsed_date;$parsed_time;$hottest_temp"

exit 0
