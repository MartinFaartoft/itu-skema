$(document).ready(function() {
	ko.applyBindings(new LectureListViewModel());
});


function Lecture(data) {
    this.title = ko.observable(data.title);
    this.weekday = data.weekday * 120; //number between 0-7
    this.startTime = (data.startTime - 7) * 25; //int
    this.duration = data.duration * 25; //int
}

function LectureListViewModel() {
    // Data
    var self = this;
    self.lectures = ko.observableArray([]);
    //self.selectedCourses = ko.observableArray([]);

    self.lectures.push(new Lecture({title: "MDD", weekday: 1, startTime: 8, duration: 2}));
    self.lectures.push(new Lecture({title: "Discrete Math", weekday: 3, startTime: 14, duration: 1}));
    // Operations
    // self.addCourse = function() {
    //     self.courses.push(new Course({ title: this.newTaskText() }));
    //     self.newTaskText("");
    // };
    // self.removeTask = function(task) { self.tasks.remove(task) };
}