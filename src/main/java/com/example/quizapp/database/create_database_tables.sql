
CREATE TABLE `Quiz` (
	`quizId` int NOT NULL AUTO_INCREMENT,
	`teacherId` int,
	`createdAt` timestamp NULL DEFAULT current_timestamp(),
	`updatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`startAt` datetime NOT NULL,
	`duration` int NOT NULL,
	`quizName` varchar(255),
	`passwordQuiz` varchar(20),
	PRIMARY KEY (`quizId`),
	UNIQUE KEY `quizId` (`quizId`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;


CREATE TABLE `QuizResult` (
	`quizResultId` int NOT NULL AUTO_INCREMENT,
	`studentId` int NOT NULL,
	`quizId` int NOT NULL,
	`mark` float NOT NULL,
	`createdAt` timestamp NULL DEFAULT current_timestamp(),
	PRIMARY KEY (`quizResultId`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE `StudentQuiz` (
	`quizId` int NOT NULL,
	`studentId` int NOT NULL,
	PRIMARY KEY (`quizId`, `studentId`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE `StudentResponse` (
	`userId` int NOT NULL,
	`quizId` int NOT NULL,
	`questionId` int NOT NULL,
	`questionId` int NOT NULL,
	`madeChoice` char(1)  NOT NULL,
	`createdAt` timestamp NULL DEFAULT current_timestamp(),
	PRIMARY KEY (`studentResponseId`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;


CREATE TABLE `User` (
	`userId` int NOT NULL AUTO_INCREMENT,
	`firstname` varchar(100) NOT NULL,
	`lastname` varchar(100) NOT NULL,
	`email` varchar(100) NOT NULL,
	`password` varchar(100) NOT NULL,
	`role` varchar(50) NOT NULL,
	`sexe` enum('female', 'male') NOT NULL,
	PRIMARY KEY (`userId`),
	UNIQUE KEY `email` (`email`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;

-------------------------------------------------------
CREATE TABLE `Question` (
	`questionId` int NOT NULL AUTO_INCREMENT,
	`quizId` int NOT NULL,
	`text` text NOT NULL,
	`image` varchar(500),
	`createdAt` timestamp NULL DEFAULT current_timestamp(),
	`updatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`firstChoice` varchar(255) NOT NULL,
	`secondChoice` varchar(255) NOT NULL,
	`thirdChoice` varchar(255),
	`fourthChoice` varchar(255),
	`fifthChoice` varchar(255) ,
	`questionMark` int NOT NULL,
	`correctChoice` varchar(3) NOT NULL,

	PRIMARY KEY (`questionId`)
);

CREATE TABLE `StudentResponse` (
    `userId` int NOT NULL,
    `quizId` int NOT NULL,
    `questionId` int NOT NULL,
    `chosenResponse` char(1) NOT NULL,  -- Updated column name
    `createdAt` timestamp NULL DEFAULT current_timestamp()
);


-- Inserting Sample Questions
INSERT INTO `Question` (`quizId`, `text`, `image`, `firstChoice`, `secondChoice`, `thirdChoice`, `fourthChoice`, `fifthChoice`, `questionMark`, `correctChoice`)
VALUES
    (1, 'Java is a statically typed language. (True/False)', NULL, 'True', 'False', NULL, NULL, NULL, 5, '1'),

    (1, 'Which keyword is used to define a constant variable in Java?', NULL, 'const', 'final', 'static', 'var', 'let', 10, '2'),

    (1, 'What is the output of the following Java code snippet? \n `System.out.println(5 + "Java");`', NULL, '5Java', 'Error', 'Java5', '55', NULL, 8, '1'),

    (1, 'In Java, what is the purpose of the `super` keyword?', NULL, 'To call the superclass constructor', 'To access the superclass method or field', 'To create an instance of the superclass', NULL, NULL, 7, '2'),

    (1, 'Which of the following is a valid declaration of a Java array?', NULL, 'int[] numbers = new int[5];', 'int numbers[] = new int[5];', 'int numbers[5] = new int[];', 'int numbers[5] = new int[5];', NULL, 12, '1'),

    (1, 'What is the purpose of the `this` keyword in Java?', NULL, 'To refer to the current instance of the class', 'To create a new instance of the class', 'To access the superclass method or field', NULL, NULL, 6, '1');
