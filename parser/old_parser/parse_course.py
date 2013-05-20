from bs4 import BeautifulSoup
import urllib2
NUM_COURSES = 113


for i in range(1,NUM_COURSES):
	COURSES = "data/"+ str(i)+".html"
	with open(COURSES, 'r') as f:
		course_markup = f.read()



	course_soup = BeautifulSoup(course_markup.decode('utf-8', 'ignore'))
	course_names_days = course_soup.findAll('table')[16]
	course_names_exam = course_soup.findAll('table')[17]
	#print course_names_exam


	timeSlots = course_names_days.findAll('tr')[1:]
	for time in timeSlots:
		info = time.findAll('td')
		day = info[0].getText()
		time_of_day = info[1].getText()
		#print day
		#print time_of_day
		if day[0] not in ['M','T','W','F','S']:
			#print i

			#print "GIGA FEJL"
			#print day
			break

		print day
		print time_of_day

#course_names_id = course_soup.find_all(id="course")

#for course in course_names_id:
	#print course.find('a')['href']
	
#print(course_soup.prettify())

