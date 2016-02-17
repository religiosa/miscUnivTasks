#!/bin/bash

# Part of find-max-or-min.sh
# Jenny Tyrväinen 2014

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

echo "Hottest temperature $hottest_temp C is found in file $hottest_file"
cat $hottest_file

exit 0
