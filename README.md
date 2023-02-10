# QuizProject
A project for my Java programming class. It gives users an option to select a category, number of questions and play a quiz. 
The program uses an H2 database to load questions.
The program also includes a register and login screen so that each user can track their own progress and high score.
There are two types of users: admins and standard users. An admin can modify quizzes, create new questions and delete them. If you want to login as an admin type in "admin" for both username and password.
Each new user registered will be marked as a standard user.
There is a leaderboards screen which shows the history of all played quizzes by each user registered and their current high score which is updated by threads.
The program also includes a history screen which shows all changes admin made to the database including deleting, modifying and adding new questions.
The number of questions the user can pick are: 5, 10, 15 and 20 by default but it can be easily modified to include more if wanted.
The default points given for answering a correct questions is 10. It is any easy change to make if wanted.
