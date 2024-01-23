CREATE TABLE `Question` (
	`questionId` int NOT NULL AUTO_INCREMENT,
	`quizId` int NOT NULL,
	`text` text NOT NULL,
	`image` varchar(500),
	`createdAt` timestamp NULL DEFAULT current_timestamp(),
	`updatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	PRIMARY KEY (`questionId`)
) ENGINE InnoDB,
  CHARSET utf8mb4,
  COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE `Quiz` (
	`quizId` int NOT NULL AUTO_INCREMENT,
	`moduleId` int NOT NULL,
	`createdAt` timestamp NULL DEFAULT current_timestamp(),
	`updatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`startAt` datetime NOT NULL,
	`duration` int NOT NULL,
	`quizName` varchar(255),
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

CREATE TABLE `Response` (
	`responseId` int NOT NULL AUTO_INCREMENT,
	`questionId` int NOT NULL,
	`text` text NOT NULL,
	`image` varchar(500),
	`isCorrect` tinyint(1) NOT NULL,
	PRIMARY KEY (`responseId`)
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
	`studentResponseId` int NOT NULL AUTO_INCREMENT,
	`userId` int NOT NULL,
	`responseId` int NOT NULL,
	`isMatch` tinyint(1) NOT NULL,
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