#!/bin/bash
unset csv
unset program
unset dirname
print_help() {
  echo "\
Usage: `basename $0` [-t] -c | -w -p dir
Arguments:
  -t        output as tab-separated values
  -c        find the coldest temperature
  -w        find the warmest temperature
  -p dir    search all subdirs of dir"
}

tabs=0
hotcool=0

dir=$(pwd)
dirset=0

while getopts ":htcwp:" opt; do
	case $opt in

	t)
		tabs=1
		;;
	c)
		hotcool=0
		;;
	w)
		hotcool=1
		;;
	p)	
		if [ -d $OPTARG ]
		then
			dir=$OPTARG
			dirset=1
		else
			echo "Not a directory."
			exit 1
		fi
		;;
	h)
		print_help
		exit 
		;;
	\?)
		echo "Invalid option -$OPTARG!"
		exit 1
		;;
	:)
		echo "Option -$OPTARG reguires an argument."
		exit 1
	esac
done

if [ $dirset -eq 0 ]
then
	echo "No directory specified, searching from current directory $dir."
fi

if [ $tabs -eq 0 ]
then
	if [ $hotcool -eq 0 ]
	then
		./min-temp.sh $dir
	else
		./hottest-new.sh $dir
	fi
else
	if [ $hotcool -eq 0 ]
	then
		./min-temp-tabs.sh $dir
	else
		./hottest-new-tabs.sh $dir
	fi
fi

exit 0
