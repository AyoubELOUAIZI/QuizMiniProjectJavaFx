-- create_database_tables.sql

-- Create the database
CREATE DATABASE IF NOT EXISTS DatabaseQuiz;
-- Use the database
USE DatabaseQuiz;

-- Create tables
CREATE TABLE IF NOT EXISTS Student (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    studentCode VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Teacher (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);


-- Create the Module table
CREATE TABLE IF NOT EXISTS Module (
    moduleId INT PRIMARY KEY AUTO_INCREMENT,
    moduleName VARCHAR(100) NOT NULL,
    modulePassword VARCHAR(100) NOT NULL, -- Add the new field for module password
    teacherId INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- New field for creation timestamp
    FOREIGN KEY (teacherId) REFERENCES Teacher(userId) ON DELETE CASCADE, -- Assuming Teacher table exists
    UNIQUE (moduleName)
);

CREATE TABLE IF NOT EXISTS Quiz (
    quizId INT PRIMARY KEY AUTO_INCREMENT,
    moduleId INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    startAt DATETIME NOT NULL,
    duration INT NOT NULL,
    FOREIGN KEY (moduleId) REFERENCES Module(moduleId) ON DELETE CASCADE,
    UNIQUE (quizId)
);

CREATE TABLE IF NOT EXISTS Question (
    questionId INT PRIMARY KEY AUTO_INCREMENT,
    quizId INT NOT NULL,
    text TEXT NOT NULL,
    image VARCHAR(500), -- URL or path to the image
    responses TEXT, -- Assuming responses are stored as a serialized form, adjust based on your schema
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (quizId) REFERENCES Quiz(quizId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Response (
    responseId INT PRIMARY KEY AUTO_INCREMENT,
    questionId INT NOT NULL,
    text TEXT NOT NULL,
    image VARCHAR(500), -- URL or path to the image
    isCorrect BOOLEAN NOT NULL,
    FOREIGN KEY (questionId) REFERENCES Question(questionId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS StudentResponse (
    studentResponseId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    responseId INT NOT NULL,
    isMatch BOOLEAN NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Student(userId) ON DELETE CASCADE,
    FOREIGN KEY (responseId) REFERENCES Response(responseId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS QuizResult (
    quizResultId INT PRIMARY KEY AUTO_INCREMENT,
    studentId INT NOT NULL,
    quizId INT NOT NULL,
    mark FLOAT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (studentId) REFERENCES Student(userId) ON DELETE CASCADE,
    FOREIGN KEY (quizId) REFERENCES Quiz(quizId) ON DELETE CASCADE
);

---- probably we can add this later if we got the time ---and also in that time we need to modified them
CREATE TABLE IF NOT EXISTS Notification (
    notificationId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    message TEXT NOT NULL,
    isRead BOOLEAN DEFAULT false,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Feedback (
    feedbackId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    quizId INT NOT NULL,
    comment TEXT,
    rating INT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Student(userId) ON DELETE CASCADE,
    FOREIGN KEY (quizId) REFERENCES Quiz(quizId) ON DELETE CASCADE
);

------------------------executed code works in planets cale---------------------------
--Because foreign key constraints are not allowed in planets cale
-- Create tables
CREATE TABLE IF NOT EXISTS Student (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    studentCode VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Teacher (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Module (
    moduleId INT PRIMARY KEY AUTO_INCREMENT,
    moduleName VARCHAR(100) NOT NULL,
    modulePassword VARCHAR(100) NOT NULL,
    teacherId INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (moduleName)
);

CREATE TABLE IF NOT EXISTS Quiz (
    quizId INT PRIMARY KEY AUTO_INCREMENT,
    moduleId INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    startAt DATETIME NOT NULL,
    duration INT NOT NULL,
    UNIQUE (quizId)
);

CREATE TABLE IF NOT EXISTS Question (
    questionId INT PRIMARY KEY AUTO_INCREMENT,
    quizId INT NOT NULL,
    text TEXT NOT NULL,
    image VARCHAR(500), -- URL or path to the image
    responses TEXT, -- Assuming responses are stored as a serialized form, adjust based on your schema
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Response (
    responseId INT PRIMARY KEY AUTO_INCREMENT,
    questionId INT NOT NULL,
    text TEXT NOT NULL,
    image VARCHAR(500), -- URL or path to the image
    isCorrect BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS StudentResponse (
    studentResponseId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    responseId INT NOT NULL,
    isMatch BOOLEAN NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS QuizResult (
    quizResultId INT PRIMARY KEY AUTO_INCREMENT,
    studentId INT NOT NULL,
    quizId INT NOT NULL,
    mark FLOAT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
----end


