var contests = require("../models/contests");
var participation = require("../models/participation");
var moment = require("moment");
var problems = require("../models/problems");
var quiz = require("../models/quiz");
var Quiz_participants = require("../models/quiz_participants");
const { localsName } = require("ejs");

exports.showQuiz =async (req, res, next) => {
	quiz.find({
		alwaysVisible:true,
	})
	.then((data)=>{
		//console.log("hhhh",data);
		res.render("quiz",{
			"data":data
		});
	})
	.catch((err) => {
		console.log(err);
	})
	
}



/**Display the user contests page 
 * route: /contests
*/
exports.showContests = async (req, res, next) => {
	
	contests.find({
			visible: true,
			endDate: {$gt:Date.now()}
		}).sort({
			date: 1
		})
		.then((data) => {
			/**Changing the date-time format to display in the contest table */
			for (var i = 0; i < data.length; i++) {
				data[i].dt = moment(data[i].date).format("ll");
				data[i].tm = moment(data[i].date).format("H:mm:ss");
				var duration = data[i].duration;
				var x = duration%60;
				var temp = x+"m ";
				duration = Math.floor(duration/60);
				x = duration%24;
				if(duration){
					temp = x+"h "+temp;
					console.log(duration,temp,x)
					duration = Math.floor(duration/24);
					x = duration
					if(duration){
						temp = x+"d "+temp;
						console.log(temp);
					}
				}
				data[i].dur = temp;
			}
			console.log(data);
			// for (var i = 0; i < external.length; i++) {
				// external[i].dt = moment(external[i].date).format("ll");
				// external[i].tm = moment(external[i].date).format("H:mm:ss");
			// }
			contests.find({
				visible: true,
				endDate: {$lt:Date.now()}
			}). sort({
				date:1
			})
			.then((pastdata)=>{
				for (var i = 0; i < pastdata.length; i++) {
					pastdata[i].dt = moment(pastdata[i].date).format("ll");
					pastdata[i].tm = moment(pastdata[i].date).format("H:mm:ss");
					var duration = pastdata[i].duration;
					var x = duration%60;
					var temp = x+"m ";
					duration = Math.floor(duration/60);
					x = duration%24;
					if(x){
						temp = x+"h "+temp;
						console.log(duration,temp,x)
						duration = Math.floor(duration/24);
						x = duration
						if(x){
							temp = x+"d "+temp;
							console.log(temp);
						}
					}
					pastdata[i].dur = temp;
				}
				// console.log(pastdata)
				res.render("users_contests", {
					"contests": data,		
					"external": [],	
					"pastcontests": pastdata
				});
			});
		})
		.catch((err) => {
			console.log(err);
		})
}

/***
 * Display a user quiz with questions
 * route:/quiz/:qId
	 */

exports.showQuizId = async (req, res, next) => {
	quiz.findOne({quizID: req.params.qId})
	.then((data) => {
		//console.log("HERe",data);
		res.render("quizid",{ data: data });
	})
	.catch((err) => {
		console.log(err);
	})
}

/**Get the questions for a given quiz with qID 
 * 
 * 
*/


exports.getQuizproblems = async (req, res, next) => {
	
	if(req.isAuthenticated()===false){
		res.redirect("/quiz");
		return
	}
	
	var ID = req.params.qId;
	
	var state = true;
	
	var exp;
	Quiz_participants.find({quizID:ID})
	.then((file)=>{
		for(var i=0;i< file.length; i++){
			//console.log(i,file[i].user);
			if(file[i].user==res.locals.user.username){
				state=false;
				exp = file[i];
				break;
			}
		}
	});
		
	//console.log("HERERERE",res.locals.user.username);
	quiz.findOne({quizID:ID})
	.then(async(data)=>{
		//console.log(data);
		

		//console.log("flaaaag",state);
		if(state===true){
			var datetime = new Date();
			//console.log(datetime);
			var q={
				quizID: ID,
				user:res.locals.user.username,
				startTime:datetime,
				duration:data.duration,
				correct:0,
				total:data.questionCount
				
			}
			
			await Quiz_participants.create(q)
            .then((val) => {
				exp = val;
                //console.log(val);
            })
		}
		else{
			// This allows us to only have one submission from one user
			//res.redirect("/quiz");
			//return;
		}
	
		

		res.render("quizrunning",{data: data,participants:exp});
	})
	.catch((err) => {
		console.log(err);
	})
	
}

/**
 * Get results for the quiz or just submit the data for whenever to view   
 *
 */

exports.getQuizResults = async (req, res, next) => {
	var qID = req.params.qId;
	var user = req.params.user;
	var opt = req.params.opt;

	let bufferObj = Buffer.from(opt, "base64");
	let decodedString = bufferObj.toString("utf8");
	//console.log(decodedString);
	var score = 0;
	quiz.findOne({quizID: qID})
	.then(async(data) => {
		
		score=0;
		
		//calculating the score 
		for (var i = 0; i < data.questionCount; i++) {
			if(data.questions[i].Options[parseInt(decodedString[i])-1]== data.questions[i].Correct){
				score++;
			}
		}

		try{
			await Quiz_participants.findOneAndUpdate({ "quizID": qID,"user": user},{$set: {"correct": score}});
		}
		catch(err){
			console.log(err);
		}
	})
	.catch((err) => {
		console.log(err);
	})

	Quiz_participants.findOne({ "quizID": qID,"user": user})
	.then((result) => {
		quiz.findOne({quizID: qID})
		.then((data) => {
			res.render("quizresult",{result:result,data:data})
		});
		
	});

	
}

/* 


 * Display a user contest with problems 
 * route: /contests/:contestCode
*/
exports.showContest = async (req, res, next) => {
	var contest = req.params.contestCode;
	contests.findOne({
		code:contest
	}).then( async (data) => {
		// console.log("data   ---------------------");
		// console.log(data);
		var contest_start = moment(data.date).format("YYYY-MM-DD H:mm:ss") > moment(Date.now()).format("YYYY-MM-DD H:mm:ss")
		if(contest_start){
			res.redirect("/contests/");
			return;
		}
		if(req.user!=null){
			var user = req.user.username;
			var contestId = contest;
			participation.find({
				contestCode:contestId,
				username:user
			}).then(async(partData)=>{
				if(partData.length===0){
					participation.create({
						contestCode:contestId,
						username:user,
						startTime:data.date,
						score:0,
						penalty:0,
						endTime:(moment(data.date).add(data.duration,'m').toDate())
					});
				}
				var problemsList = [];
				var problemsID = data.problemsID;
				for (var i=0; i<problemsID.length; i++) {
					await problems.findOne({
						qID: problemsID[i]
					}).then((problem) => {
						problemsList.push(problem);
					});
				}
				res.render("contest_display", {problems: problemsList, contest: data});
			})
		}
		else{
			var problemsList = [];
			var problemsID = data.problemsID;
			for (var i=0; i<problemsID.length; i++) {
				await problems.findOne({
					qID: problemsID[i]
				}).then((problem) => {
					problemsList.push(problem);
				});
			}
			res.render("contest_display", {problems: problemsList, contest: data});
		}
	});
}

/*
 * Sort the participation collection based on score and render the ranklist.
 * /contests/:code/standings/
 */
exports.ranklist = async(req,res,next) =>{
	var contest = req.params.contestCode;
	console.log(contest);
	contests.find({code:contest}).then((data)=>{
		console.log(data);
		if(data.length === 0){
			res.render("404");
			return;
		}
		console.log(data);
		console.log(typeof(contest));
		participation.find({"contestCode":contest}).sort({'score': -1, 'penalty': 1}).then(async(participations) => {
			res.render("ranklist",{contest:data,list: participations});
		});
	});
}