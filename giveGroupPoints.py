#!/usr/bin/python3

# coding: UTF-8

# Get groups and people from file input and points by group.
# Return list of people and points by person in alphabetical order.

# A helper program to grading and assigning points on a course.
# Jenny Tyrväinen, fall 2015

import collections
import json

import sys, getopt

def main(argv):
	inputfile = ''
	pointsfile = ''
	outputfile = ''

	if len(sys.argv) == 1:
		print("giveGroupPoints.py -i <input group file> -p <points file> -o <outputfile>")
		sys.exit(2)

	try:
		opts, args = getopt.getopt(argv,"hi:p:o:", ["ifile=", "pfile=", "ofile="])
	except getopt.GetoptError:
		print("giveGroupPoints.py -i <input group file> -p <points file> -o <outputfile>")
		sys.exit(2)
	for opt, arg in opts:
		if opt == '-h':
			print ("giveGroupPoints.py -i <input group file> -p <points file> -o <outputfile>")
			sys.exit()
		elif opt in ("-i", "--ifile"):
			inputfile = arg
		elif opt in ("-p", "--pfile"):
			pointsfile = arg
		elif opt in ("-o", "--ofile"):
			outputfile = arg
			


	lines = [line.rstrip('\n').split(':') for line in open(inputfile, 'r')]

	groupDict = {}
	for x in lines:
		groupDict[x[0]] = x[1:]


	points = [line.rstrip('\n').split() for line in open(pointsfile, 'r')]

	pointsDict = {}
	for x in points:
		pointsDict[x[0]] = x[1:]


	students = {}

	for x in groupDict:
		for y in groupDict[x]:
			students[y] = pointsDict[x]

	od = collections.OrderedDict(sorted(students.items(), key=lambda student: student[0].split()[1]))

	#f = open('result.json', 'w')
	#f.write(json.dumps(od))
	#f.close()

	w = open(outputfile, 'w')
	for key, val in od.items():
		print('\t'.join(val))
		w.write(key +"," + '\t'.join(val) + "\n")

if __name__ == "__main__":
	main(sys.argv[1:])
