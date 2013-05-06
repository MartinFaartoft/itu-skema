$(document).ready(function () {

    coursesList = $.map(courses, function (item) { return item.title; });

    var viewmodel = new CourseListViewModel();
    ko.applyBindings(viewmodel);


    $('.typeahead').typeahead({
        source: function (query, process) {
            return coursesList;
        }
    });

    $(window).bind("beforeunload", function (e) {
        viewmodel.save();
    });
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

function Lecture(data) {
    this.title = ko.observable(data.title);
    this.left = ko.observable((data.weekday * colWidth + timeColWidth - 1) + 'px'); //number between 0-7
    this.startTime = ko.observable(data.startTime);
    this.duration = ko.observable(data.duration);
    this.weekday = ko.observable(data.weekday);
    this.top = ko.observable(((data.startTime - 7) * rowHeight - rowMargin - 1) + 'px'); //int
    this.height = ko.observable((data.duration * rowHeight - 20) + 'px'); //int
    this.isColiding = ko.observable(false);
    this.width = ko.observable(120 + 'px');
}

function Course(data) {
    this.title = data.title;
    var lectures = $.map(data.lectures, function (item) { return new Lecture(item); });
    this.lectures = ko.observableArray(lectures);
    this.color = getRandomColor();
    this.visible = ko.observable(true);
    this.url = data.url;
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
            var title = data[i];

            var coursePlace = $.inArray(title, coursesList)
            var course = self.courses()[coursePlace];
            self.selectedCourses.push(course);
        }

        //self.selectedCourses = ko.mapping.fromJSON(selectedCourses);
    }

    self.toggleCourseVisible = function (course) {
        var isVisible = course.visible();
        course.visible(isVisible);
        return true;
    }

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

        var lectures = course.lectures();

        CheckColiding(lectures);

        self.selectedCourses.push(course);
        $("#course").val("");
    };

    function CheckColiding(lectures) {
        // Check coliding lectures and put them next to each other
        // Does not work if a lecture overlaps another lecture
        //for (var i = 0; i < lectures.length; i++) {
        //    var lecture = lectures[i];

        //    var start = lecture.startTime();
        //    var weekday = lecture.weekday();
        //    var end = start + lecture.duration();

        //    var selectedCourses = self.selectedCourses();

        //    for (var j = 0; j < selectedCourses.length; j++) {
        //        var currentCourse = selectedCourses[j];

        //        var currentCourseLectures = currentCourse.lectures();

        //        for (var k = 0; k < currentCourseLectures.length; k++) {
        //            var otherLecture = currentCourseLectures[k];

        //            var otherStart = otherLecture.startTime();
        //            var otherWeekday = otherLecture.weekday();
        //            var otherEnd = otherStart + otherLecture.duration();

        //            if (weekday !== otherWeekday) {
        //                continue;
        //            }

        //            if (start >= otherStart && start < otherEnd) {
        //                //Starts inside other lecture
        //                lecture.isColiding(true);
        //                otherLecture.isColiding(true);

        //                lecture.width(50 + 'px');
        //                otherLecture.width(50 + 'px');


        //                //should add 70 px;
        //                lecture.left((lecture.weekday() * colWidth + timeColWidth - 1 + 70) + 'px');
        //            }

        //            if (end >= otherStart && end < otherEnd) {
        //                //Ends inside other lecture
        //                lecture.isColiding(true);
        //                otherLecture.isColiding(true);

        //                lecture.width(50 + 'px');
        //                otherLecture.width(50 + 'px');

        //                //should add 70 px;
        //                otherLecture.left((otherLecture.weekday() * colWidth + timeColWidth - 1 + 70) + 'px');
        //            }
        //        }
        //    }
        //}
    }

    function CheckNotColiding(lectures) {
        // Check coliding lectures and put them next to each other
        // Does not work if a lecture overlaps another lecture
        //for (var i = 0; i < lectures.length; i++) {
        //    var lecture = lectures[i];

        //    var start = lecture.startTime();
        //    var weekday = lecture.weekday();
        //    var end = start + lecture.duration();

        //    var selectedCourses = self.selectedCourses();

        //    for (var j = 0; j < selectedCourses.length; j++) {
        //        var currentCourse = selectedCourses[j];

        //        var currentCourseLectures = currentCourse.lectures();

        //        for (var k = 0; k < currentCourseLectures.length; k++) {
        //            var otherLecture = currentCourseLectures[k];

        //            var otherStart = otherLecture.startTime();
        //            var otherWeekday = otherLecture.weekday();
        //            var otherEnd = otherStart + otherLecture.duration();
        //            var isColiding = otherLecture.isColiding();

        //            if (!isColiding || weekday !== otherWeekday) {
        //                continue;
        //            }

        //            //Not coliding anymore
        //            //Ends inside other lecture
        //            otherLecture.isColiding(false);

        //            otherLecture.width(120 + 'px');

        //            //should add 70 px;
        //            otherLecture.left((otherLecture.weekday() * colWidth + timeColWidth - 1 - 70) + 'px');
        //        }
        //    }
        //}
    }


    self.removeCourse = function (course) {
        var lectures = course.lectures();
        CheckNotColiding(lectures)

        self.selectedCourses.remove(course);
    };

    self.save = function () {
        var selectedList = $.map(self.selectedCourses(), function (item) { return item.title; });
        var courses = ko.toJSON(selectedList);
        localStorage.setItem("selectedCourses", courses);
    };
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function getRandomColor() {
    var r = function () { return getRandomInt(100, 256); };
    return "rgb(" + r() + "," + r() + "," + r() + ")";
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
