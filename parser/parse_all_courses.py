from bs4 import BeautifulSoup
import urllib2


COURSES = "courses.html"
with open(COURSES, 'r') as f:
	course_markup = f.read()



course_soup = BeautifulSoup(course_markup.decode('utf-8', 'ignore'))

course_names_id = course_soup.find_all(id="course")

for course in course_names_id:
	print course.find('a')['href']
	
#print(course_soup.prettify())

