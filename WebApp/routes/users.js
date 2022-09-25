var express = require("express");
var router = express.Router();
var contests = require("../controls/contests");

var authController = require('../controls/auth')
var enforceAuthentication = authController.enforceAuthentication;   // for checking the user/ admin logged in

/**POST: user login */
router.post('/login', enforceAuthentication(false, false), authController.postLogin);

/**Loggin out the user, redirected to the homepage */
router.get('/logout', enforceAuthentication(true, false), authController.getLogout);

/**POST: user signup */
router.post('/signup', enforceAuthentication(false, false), authController.postSignUp);

/**Display the user profile/ dashboard page */
router.get("/profile", enforceAuthentication(true, false), authController.showProfile);

/**Display the user submission history table */
router.get("/submissions", enforceAuthentication(true, false), authController.submissionHistory);

/**Display the user's particular submission by it's submID */
router.get("/submission/:subID", enforceAuthentication(true, false), authController.submission_subID);

/**Display the user update profile page */
router.get("/updateProfile", enforceAuthentication(true, false), authController.getUpdateProfile);

/**POST: user profile update */
router.post("/updateProfile", enforceAuthentication(true, false), authController.postUpdateProfile);


/**Display the quiz questions with qID */
//router.get("/quiz/running/:qID", enforceAuthentication(true, false), contests.getQuizproblems);

module.exports = router;
