#!/usr/bin/env python
# -*- coding: utf-8 -*- 
from bs4 import BeautifulSoup
import urllib2
import re
import json
from os import listdir
from os.path import isfile, join
import glob

def parse_day(day):
	if 'Man' in day:
		return 0
	if 'Tirs' in day:
		return 1
	if 'Ons' in day:
		return 2
	if 'Tors' in day:
		return 3
	if 'Fre' in day:
		return 4
	if 'L' in day:
		return 5
	if 'S' in day:
		return 6
	print "GIGAFEJL"

def parse_time(time):
	hour = int(time[0:2])
	minute = int(time[3:5])
	

	return hour*60 + minute




markup = glob.glob("datatest/*.html")
courses = []

for filename in markup:
	#print i
	course_id = int(filename[9:16])
	

	#COURSES = "datatest/"+ str(i)+".html"
	with open(filename, 'r') as f:
		course_markup = f.read()



	
	course_soup = BeautifulSoup(course_markup.decode('utf-8', 'ignore'))


	name = course_soup.find(text=re.compile('Kursusnavn \(engelsk')).parent.parent.parent.findAll('td')[1].getText().encode('utf-8').strip()
	#print name
	#course_names_days = course_soup.findAll('table')[6]
	course_names_days = course_soup.find_all(text=re.compile('Kurset afholdes'))
	#print dir(course_names_days[0])
	course = {'name': name, 'id': course_id}
	#print course
	if len(course_names_days) == 0:
		print "Fejl ved ",filename
		continue
	leg =  course_names_days[0].find_next_sibling().find_all('tr')[1:]
	lectures = []
	for obj in leg:
		trs =  obj.find_all('td')
		day = parse_day(trs[0].getText())
		from_time = parse_time(trs[1].getText())
		to_time = parse_time(trs[1].getText())
		lectures.append({'day': day, 'from': from_time, 'to':to_time})
	course['lecture'] = lectures
	courses.append(course)




fh = open("data.json", 'w') 
fh.write(json.dumps(courses))
fh.close()
print "DONE"
		




