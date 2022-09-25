var mongoose = require('mongoose');

var Quiz_participant= new mongoose.Schema({
    quizID: String,
    user: String,
    startTime: Date,
    duration: Number,
    correct:Number,
    total: Number,
});
var Quiz_participants = mongoose.model('Quiz_participants', Quiz_participant);
module.exports = Quiz_participants;
