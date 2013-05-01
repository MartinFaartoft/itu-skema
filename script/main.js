$(document).ready(function() {
	ko.applyBindings(new CourseListViewModel());
});

var rowHeight = 42;
var colWidth = 136;
var rowMargin = 5;
var timeColWidth = 56;

function Lecture(data) {
    this.title = ko.observable(data.title);
    this.left = data.weekday * colWidth + timeColWidth; //number between 0-7
    this.top = (data.startTime - 7) * rowHeight - rowMargin; //int
    this.height = data.duration * rowHeight - 1; //int
}

function Course(data) {
	this.title = data.title;
	this.lectures = data.lectures;
}

function CourseListViewModel() {
    var self = this;
    self.courses = ko.observableArray([]);

    self.courses.push(new Course({title: "MDD", lectures: [new Lecture({title: "MDD", weekday: 0, startTime: 8, duration: 2}),
    	new Lecture({title: "MDD", weekday: 1, startTime: 10, duration: 2}),
    	new Lecture({title: "MDD", weekday: 2, startTime: 12, duration: 2})]}));
}