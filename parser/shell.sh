cat 'course_urls' | while read LINE
do
       let count++
       echo "$LINE"
       echo "$count"
       curl "https://mit.itu.dk$LINE" -o "datatest/$count.html" -H "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3" -H "Accept-Encoding: gzip,deflate,sdch" -H "Host: mit.itu.dk" -H "Accept-Language: en-US,en;q=0.8" -H "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" -H "Referer: https://mit.itu.dk/ucs/cb/index.sml" -H "Cookie: __utma=240077531.1656084586.1365578195.1366622964.1366807902.7; __utmz=240077531.1366807902.7.6.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); user_id=1342200; user_lang=en; last_login=Wed+May++1+16:53:40+2013; session_id=a236f2d02c9de486f1a3d86f27ac4409" -H "Connection: keep-alive" -H "Cache-Control: max-age=0"
	   #echo "https://mit.itu.dk$LINE" -H "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3" -H "Accept-Encoding: gzip,deflate,sdch" -H "Host: mit.itu.dk" -H "Accept-Language: en-US,en;q=0.8" -H "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" -H "Referer: https://mit.itu.dk/ucs/cb/index.sml" -H "Cookie: __utma=240077531.1656084586.1365578195.1366622964.1366807902.7; __utmz=240077531.1366807902.7.6.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); user_id=1342200; user_lang=en; last_login=Wed+May++1+15:44:41+2013; session_id=1a517fd36dc3279edea3abb22c12b852" -H "Connection: keep-alive" -H "Cache-Control: max-age=0" -o "data/$count.html"
	   #curl "https://mit.itu.dk$LINE" -o "data/$count.html" -H "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3" -H "Accept-Encoding: gzip,deflate,sdch" -H "Host: mit.itu.dk" -H "Accept-Language: en-US,en;q=0.8" -H "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" -H "Referer: https://mit.itu.dk/ucs/cb/index.sml" -H "Cookie: __utma=240077531.1656084586.1365578195.1366622964.1366807902.7; __utmz=240077531.1366807902.7.6.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); user_id=1342200; user_lang=en; last_login=Wed+May++1+16:21:51+2013; session_id=d9a7f8e561d9847845c526a61c20a529" -H "Connection: keep-alive" -H "Cache-Control: max-age=0"
       #curl "https://mit.itu.dk/ucs/cb/course.sml?course_id=1322834&mode=search&semester_id=1315139&lang=en&goto=1367418108.000" -o "test.html" -H "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3" -H "Accept-Encoding: gzip,deflate,sdch" -H "Host: mit.itu.dk" -H "Accept-Language: en-US,en;q=0.8" -H "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" -H "Referer: https://mit.itu.dk/ucs/cb/index.sml" -H "Cookie: __utma=240077531.1656084586.1365578195.1366622964.1366807902.7; __utmz=240077531.1366807902.7.6.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); user_id=1342200; user_lang=en; last_login=Wed+May++1+16:21:51+2013; session_id=d9a7f8e561d9847845c526a61c20a529" -H "Connection: keep-alive" -H "Cache-Control: max-age=0"
done