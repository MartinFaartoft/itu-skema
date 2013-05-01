$(document).ready(function() {
	ko.applyBindings(new LectureListViewModel());
});

var rowHeight = 42;
var colWidth = 136;
var rowMargin = 5;
var timeColWidth = 56;

function Lecture(data) {
    this.title = ko.observable(data.title);
    this.left = (data.weekday) * colWidth + timeColWidth; //number between 0-7
    this.top = (data.startTime - 7) * rowHeight - rowMargin; //int
    this.height = data.duration * rowHeight - 1; //int
}

function LectureListViewModel() {
    var self = this;
    self.lectures = ko.observableArray([]);

    self.lectures.push(new Lecture({title: "MDD", weekday: 0, startTime: 8, duration: 2}));
    self.lectures.push(new Lecture({title: "Discrete Math", weekday: 1, startTime: 8, duration: 1}));
    self.lectures.push(new Lecture({title: "Discrete Math Ex", weekday: 2, startTime: 8, duration: 4}));
}