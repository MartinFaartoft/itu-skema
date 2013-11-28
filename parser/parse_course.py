#!/usr/bin/env python
# -*- coding: utf-8 -*- 
from bs4 import BeautifulSoup
import urllib2
import re
import json
from os import listdir
from os.path import isfile, join
import glob
import urllib2
DO_EXAM = False

def get_course_ids():
	content = urllib2.urlopen("https://mit.itu.dk/ucs/cb_www/")
	course_soup = BeautifulSoup(content)

	course_names_id = course_soup.find_all(id="course")
	course_ids = []
	for course in course_names_id:
		try:
			href = course.find('a')['href']
			to_find = 'course_id='
			a = href.find('course_id=') + len(to_find)
			b = href[a:]
			c = b.find('&')
			course_id = b[:c]
			course_ids.append(course_id)

		except:
			continue
	return course_ids

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

def parse_type(the_type):
	if 'ore' in the_type:
		return 'Lecture'
	if 'vel' in the_type:
		return 'Exercises'


def parse_time(time):
	from_hour = int(time[0:2])
	from_minute = int(time[3:5])
	

	to_hour = int(time[6:8])
	to_minute = int(time[9:11])

	return (from_hour*60 + from_minute, to_hour*60 + to_minute)

def conform_study(study):
	if '(dim)' in study:
		temp = ('(dim)')
	elif '(E-Business)' in study:
		temp = '(ebuss)'
	else:
		temp = study
	return temp[1:-1]

courses = []

course_ids = get_course_ids()
for course_id in course_ids:
	


	content = urllib2.urlopen("https://mit.itu.dk/ucs/cb_www/course.sml?lang=en&course_id="+str(course_id)+"&semester_id=1376480")

	course_soup = BeautifulSoup(content)


	name = course_soup.find(text=re.compile('Kursusnavn \(engelsk')).parent.parent.parent.findAll('td')[1].getText()
	ects = course_soup.find(text=re.compile('ECTS')).parent.parent.parent.findAll('td')[1].getText()
	temp = course_soup.find(text=re.compile('Udbydes under:')).parent.parent.parent.findAll('td')[1].getText()
	study = conform_study(re.findall('\(.*\)', temp)[0])

	course_names_days = course_soup.find_all(text=re.compile('Kurset afholdes'))

	exam_days = course_soup.find_all(text=re.compile('Eksamen afholdes'))
	course = {'name': name, 'id': course_id, 'ects': ects, 'studyprogram': study}
	if len(course_names_days) == 0:
		print "Fejl ved ",str(course_id)
		continue
	leg =  course_names_days[0].find_next_sibling().find_all('tr')[1:]
	lectures = []
	for obj in leg:
		trs =  obj.find_all('td')
		day = parse_day(trs[0].getText())
		

		from_time,to_time = parse_time(trs[1].getText())
		std_type = parse_type(trs[2].getText())
		location = trs[4].getText()
		

		lectures.append({'day': day, 'from': from_time, 'to':to_time, 'type': std_type, 'location': location})
		
	course['lectures'] = lectures
	if (DO_EXAM):
		if len(exam_days) == 0:
			print "Fejl ved exam_days",str(course_id)
		else:
			leg =  exam_days[0].find_next_sibling().find_all('tr')[1:]
			exam_dates = []
			for obj in leg:
				#YYYY-MM-DD
				trs =  obj.find_all('td')
				date = trs[0].getText()
				yyyy = date[0:4]
				mm = date[5:7]
				dd = date[8:10]
				date = yyyy+'-'+mm+'-'+dd
				exam_dates.append(date)
		course['exam_dates'] = exam_dates

	courses.append(course)




fh = open("data.json", 'w') 
fh.write(json.dumps(courses).encode("utf-8"))
fh.close()
print "DONE"