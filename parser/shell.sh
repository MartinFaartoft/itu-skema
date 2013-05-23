cat 'course_urls' | while read LINE
do
       echo "$LINE"
       #curl "https://mit.itu.dk$LINE ?lang=en" -o "datatest/$count.html"
       

       curl "https://mit.itu.dk/ucs/cb_www/course.sml?lang=en&course_id=$LINE&semester_id=1376480" -o "datatest/$LINE.html"
done