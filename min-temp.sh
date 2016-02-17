#!/bin/bash

min_temp=100
min_file="null"

if [ ! -d $1 ]	#check if the input is a directory, if not exit and print usage
then
	echo "Usage: $0 <directory>"
	exit
fi

all_files=$(find $1 -name "hp-temps.txt")


for file in $all_files
do
		current_temp=`grep --no-filename PROCESSOR_ZONE $file | sed -e 's/\ \+/,/g' -e 's/\//,/g' | cut -d , -f 3 | sed 's/C//g'`

		if [ $current_temp -lt $min_temp ]
		then
			min_temp=$current_temp
			min_file=$file
		fi
done

echo "Coolest temperature $min_temp C is found in file $min_file"
cat $min_file

exit 0
