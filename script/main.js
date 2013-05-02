$(document).ready(function () {
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

var coursesList = ["3D Game Art", "Advanced Models and Programs", "Affective Computing", "Algoritmer og datastrukturer", "Anskaffelse og kravspecifikation"];

var rowHeight = 42;
var colWidth = 141;
var rowMargin = 5;
var timeColWidth = 56;

function Lecture(data) {
    this.title = ko.observable(data.title);
    this.left = data.weekday * colWidth + timeColWidth - 1; //number between 0-7
    this.top = (data.startTime - 7) * rowHeight - rowMargin - 1; //int
    this.height = data.duration * rowHeight - 20; //int
}

function Course(data) {
    this.title = data.title;
    this.lectures = data.lectures;
    this.color = getRandomColor();
}

function CourseListViewModel() {
    var self = this;
    self.courses = ko.observableArray([]);
    self.selectedCourses = ko.observableArray([]);

    var savedCourses = localStorage.getItem("courses");

    if (savedCourses != null) {
        var savedJSON = JSON.parse(savedCourses);
        for (var i = 0; i < savedJSON.length; i++) {
            var current = savedJSON[i];

            self.selectedCourses.push(current);
        }
    }


    self.addCourse = function () {
        var courseTitle = $("#course").val();

        if (courseTitle == "")
        {
            newAlert("alert", "Please fill in a name!")
            return;
        }

        var found = $.inArray(courseTitle, coursesList) > -1;

        if (!found)
        {
            newAlert("alert", "Not a valid course... Try again")
            return;
        }

        var course = new Course({ title: courseTitle, lectures: [new Lecture({ title: "Lecture", weekday: getRandomInt(0, 5), startTime: getRandomInt(8, 20), duration: getRandomInt(2, 4) })] });
        self.selectedCourses.push(course);
        $("#course").val("");
    }

    self.removeCourse = function (course) {
        self.selectedCourses.remove(course);
    }

    self.save = function () {
        localStorage.setItem("courses", ko.toJSON(self.selectedCourses));
    }
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function getRandomColor() {
    var r = function () { return getRandomInt(100, 256) };
    return "rgb(" + r() + "," + r() + "," + r() + ")";
}


function newAlert(type, message) {
    $("#alert-area").append($("<div class='alert-message " + type + " fade in' data-alert><strong>Warning: </strong> " + message + "</div>"));
    $(".alert-message").delay(2000).fadeOut("slow", function () { $(this).remove(); });
}
newAlert('success', 'Oh yeah!');