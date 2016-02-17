#!/bin/bash

# Jenny Tyrväinen 2014

# A task on Bash programming course. 
# Creates hipstafied versions of .jpg -images.

if [ ! -d $1 ]
then
    echo "Usage: $0 directory"
    exit
fi

PICS=`find $1 -name '*.jpg'`

for i in $PICS
do
    inputfile=$i
    prefix=${inputfile%.jpg}
    outputfile=$prefix-hipstah.jpg
    echo "Processing $i"
    convert -sepia-tone 60% +polaroid $inputfile $outputfile
done

exit 0
