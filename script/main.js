$(document).ready(function () {

    coursesList = $.map(courses, function (item) { return item.name + " (" + item.studyprogram + ")"; });
    coursesList = renameDuplicates(coursesList);
    courseIds = $.map(courses, function (item) {return item.id; });

    window.viewmodel = new CourseListViewModel();
    ko.applyBindings(viewmodel);

    var input = $('.typeahead').first();
    $('.typeahead').typeahead({
        items: 24,
        source: function (query, process) {
            return coursesList;
        }
    }).focus();

    $(window).bind("beforeunload", function (e) {
        viewmodel.save();
    });
	
	$('.lecture').tooltip();
});

var rowHeight = 42;
var colWidth = 141;
var rowMargin = 5;
var timeColWidth = 56;


ko.bindingHandlers.executeOnEnter = {
    init: function (element, valueAccessor, allBindingsAccessor, viewModel) {
        var allBindings = allBindingsAccessor();
        $(element).keypress(function (event) {
            var keyCode = (event.which ? event.which : event.keyCode);
            if (keyCode === 13) {
                allBindings.executeOnEnter.call(viewModel);
                return false;
            }
            return true;
        });
    }
};

function Lecture(data, course) {
    this.title = ko.observable(data.type);
    this.location = ko.observable(data.location);
    this.tooltip = course.title + " (" + course.studyprogram + ", " + course.ects + " ects) @ " + data.location;
    this.order = ko.observable(0);
    this.left = function () { return (data.day * colWidth + timeColWidth - 1) + (this.isColiding() ? 70 * this.order() : 0) + 'px'; };
    this.startTime = ko.observable(data.from);
    this.duration = ko.observable(data.to - data.from);
    this.weekday = ko.observable(data.day);
    this.top = ko.observable((((data.from - 480) / 60.0 + 1) * rowHeight - rowMargin - 1) + 'px');
    this.height = ko.observable((((data.to - data.from) / 60.0) * rowHeight - 20) + 'px');
    this.isColiding = ko.observable(false);
    this.width = function () { return (this.isColiding() ? 50 : 120) + 'px'; };
}

function Course(data) {
    this.title = data.name;
    var self = this;
    this.studyprogram = data.studyprogram;
    this.color = getNextColor();
    this.visible = ko.observable(true);
    this.url = "https://mit.itu.dk/ucs/cb_www/course.sml?course_id=" + data.id + "&mode=search&semester_id=" + semester_id;
    this.id = data.id;
    this.order = ko.observable(0);
	var ects = data.ects.replace(',','.');
	this.ects = Number(ects);
    var lectures = $.map(data.lectures, function (item) { return new Lecture(item, self); });
    this.lectures = ko.observableArray(lectures);
}
 

function CourseListViewModel() {
    var self = this;

    self.courses = ko.observableArray([]);

    //Data is from data.js
    for (var i = 0; i < courses.length; i++) {
        var course = courses[i];
        self.courses.push(new Course(course));
    }

    self.selectedCourses = ko.observableArray([]);
    var selectedCourses = localStorage.getItem("selectedCourses");

    if (selectedCourses !== null) {

        var data = JSON.parse(selectedCourses);

        for (var i = 0; i < data.length; i++) {
            var id = data[i];

            var coursePlace = $.inArray(id, courseIds);
            if(coursePlace > -1) {
                var course = self.courses()[coursePlace];
                CheckColiding(course);
                self.selectedCourses.push(course);
            }
        }
    }

    self.toggleCourseVisible = function (course) {
        var isVisible = course.visible();
        course.visible(isVisible);
        if (isVisible) {
            CheckColiding(course);
        }
        else {
            CheckNotColiding(course);
        }

        return true;
    }
	
	self.totalects = ko.computed(function(){
    var total = 0;
    for(var p = 0; p < self.selectedCourses().length; p++)
    {
		var course = self.selectedCourses()[p]; 
		var etcs = course.ects;
        total += course.visible() ? etcs : 0;
    }
    return total;
});

    self.addCourse = function () {
        var courseTitle = $("#course").val();

        if (courseTitle === "") {
            newAlert("alert", "Please fill in a name!");
            return;
        }

        var found = $.inArray(courseTitle, coursesList) > -1;

        if (!found) {
            newAlert("alert", "Not a valid course... Try again");
            return;
        }

        var selectedList = $.map(self.selectedCourses(), function (item) { return item.title; });

        found = $.inArray(courseTitle, selectedList) > -1;

        if (found) {
            newAlert("alert", "You already selected this course. Please select another");
            return;
        }

        var coursePlace = $.inArray(courseTitle, coursesList)

        var course = self.courses()[coursePlace];

        CheckColiding(course);

        self.selectedCourses.push(course);
        $("#course").val("");
		
		$('.lecture').tooltip();
        //viewModel.save();
    };

    function CheckColiding(course) {
        var lectures = course.lectures();

        //  Check coliding lectures and put them next to each other
        //  Does not work if a lecture overlaps another lecture
        for (var i = 0; i < lectures.length; i++) {
            var lecture = lectures[i];

            var start = lecture.startTime();
            var weekday = lecture.weekday();
            var end = start + lecture.duration();

            var selectedCourses = self.selectedCourses();

            for (var j = 0; j < selectedCourses.length; j++) {
                var currentCourse = selectedCourses[j];

                if (!currentCourse.visible() || course.id === currentCourse.id) {
                    continue;
                }

                var currentCourseLectures = currentCourse.lectures();

                for (var k = 0; k < currentCourseLectures.length; k++) {
                    var otherLecture = currentCourseLectures[k];

                    var otherStart = otherLecture.startTime();
                    var otherWeekday = otherLecture.weekday();
                    var otherEnd = otherStart + otherLecture.duration();

                    if (weekday !== otherWeekday) {
                        continue;
                    }

                    if (start >= otherStart && start < otherEnd) {
                        //Starts inside other lecture
                        lecture.isColiding(true);
                        otherLecture.isColiding(true);
                        lecture.order(0);
                        otherLecture.order(1);
                    }

                    if (end >= otherStart && end < otherEnd) {
                        //Ends inside other lecture
                        lecture.isColiding(true);
                        otherLecture.isColiding(true);
                        lecture.order(1);
                        otherLecture.order(0);
                    }
                }
            }
        }
    }

    function CheckNotColiding(course) {
        //Check coliding lectures and put them next to each other
        //Does not work if a lecture overlaps another lecture

        var lectures = course.lectures();

        for (var i = 0; i < lectures.length; i++) {
            var lecture = lectures[i];

            var start = lecture.startTime();
            var weekday = lecture.weekday();
            var end = start + lecture.duration();

            var selectedCourses = self.selectedCourses();

            for (var j = 0; j < selectedCourses.length; j++) {
                var currentCourse = selectedCourses[j];

                if (!currentCourse.visible() || course.title === currentCourse.title) {
                    continue;
                }

                var currentCourseLectures = currentCourse.lectures();

                for (var k = 0; k < currentCourseLectures.length; k++) {
                    var otherLecture = currentCourseLectures[k];

                    var otherStart = otherLecture.startTime();
                    var otherWeekday = otherLecture.weekday();
                    var otherEnd = otherStart + otherLecture.duration();
                    var isColiding = otherLecture.isColiding();

                    if (!isColiding || weekday !== otherWeekday) {
                        continue;
                    }

                    if (start >= otherStart && start < otherEnd) {
                        //Starts inside other lecture
                        lecture.isColiding(false);
                        otherLecture.isColiding(false);

                        lecture.order(0);
                        otherLecture.order(0);
                    }

                    if (end >= otherStart && end < otherEnd) {
                        //Ends inside other lecture
                        lecture.isColiding(false);
                        otherLecture.isColiding(false);

                        lecture.order(0);
                        otherLecture.order(0);
                    }
                }
            }
        }
    }


    self.removeCourse = function (course) {
        CheckNotColiding(course)
        self.selectedCourses.remove(course);
    };

    self.save = function () {
        var selectedList = $.map(self.selectedCourses(), function (item) { return item.id; });
        var courses = ko.toJSON(selectedList);
        localStorage.setItem("selectedCourses", courses);
    };
}

var step = 89;
var min = 100;
var max = 256;
var r = min;
var g = min;
var b = min;
function getNextColor() {
    r += step;
    if(r > max) {
        r = r % max + min;
        g += step;
        if(g > max) {
            g = g % max + min;
            b += step;
            if(b > max) {
                b = b % max + min;
            }
        }
    }
    return "rgb(" + r + "," + g + "," + b +")";
}


function newAlert(type, message) {
    $("#alert-area").append($("<div class='alert-message " + type + " fade in' data-alert><strong>Warning: </strong> " + message + "</div>"));
    $(".alert-message").delay(2000).fadeOut("slow", function () { $(this).remove(); });
}

function takePicture() {
    html2canvas($('#week'), {
        onrendered: function (canvas) {
            var image = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
            window.location = image;
        }
    });
}

function reset() {
    if(confirm("This will remove all your selected courses, are you sure?")) {
        window.viewmodel.selectedCourses.removeAll();
    }
}

function renameDuplicates(arr) {
    for(var i = 0; i <  arr.length - 1; i ++) {
        for (var j = i + 1; j < arr.length; j++) {
            if(i != j && arr[i] == arr[j]) {
                arr[j] = arr[j] + " 2";
            }
        }
    }

    return arr;
}
