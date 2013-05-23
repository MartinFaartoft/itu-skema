from bs4 import BeautifulSoup
import urllib2


COURSES = "courses.html"
with open(COURSES, 'r') as f:
	course_markup = f.read()

course_soup = BeautifulSoup(course_markup.decode('utf-8', 'ignore'))

course_names_id = course_soup.find_all(id="course")

for course in course_names_id:
	try:
		href = course.find('a')['href']
		to_find = 'course_id='
		a = href.find('course_id=') + len(to_find)
		b = href[a:]
		c = b.find('&')
		course_id = b[:c]
		print course_id

	except:
		continue

#print(course_soup.prettify())

