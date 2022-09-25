var mongoose = require('mongoose');

var quiz = new mongoose.Schema({
    quizID: String,
    name: String,
    topic:String,
    alwaysVisible:Boolean,
    resultDisplay:Boolean,
    rules: String,
    questionCount: Number,
    quizSetter: String,
    startTime: Date,
    endTime: Date,
    duration: Number,
    questions :[{Name: String,Options:[String],Correct:String}],
});
var Quiz = mongoose.model('Quiz', quiz);
module.exports = Quiz;
