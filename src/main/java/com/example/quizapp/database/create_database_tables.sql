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

CREATE TABLE IF NOT EXISTS User (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
);


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

-------------------inserting some fake data generated by chatgpt for test---------------------

-- Inserting data into the Student table
INSERT INTO Student (firstname, lastname, email, password, role, studentCode)
VALUES
('John', 'Doe', 'john.doe@example.com', 'password123', 'student', 'S001'),
('Alice', 'Smith', 'alice.smith@example.com', 'password456', 'student', 'S002'),
('Bob', 'Johnson', 'bob.johnson@example.com', 'password789', 'student', 'S003'),
('Eva', 'Miller', 'eva.miller@example.com', 'passwordabc', 'student', 'S004'),
('Michael', 'Brown', 'michael.brown@example.com', 'passworddef', 'student', 'S005');

-- Inserting data into the Teacher table
INSERT INTO Teacher (firstname, lastname, email, password, role)
VALUES
('John', 'Smith', 'john.smith@example.com', 'teacherPass123', 'teacher'),
('Emma', 'Johnson', 'emma.johnson@example.com', 'teacherPass456', 'teacher'),
('Michael', 'Davis', 'michael.davis@example.com', 'teacherPass789', 'teacher'),
('Sophia', 'Wilson', 'sophia.wilson@example.com', 'teacherPassABC', 'teacher'),
('Daniel', 'Thomas', 'daniel.thomas@example.com', 'teacherPassDEF', 'teacher');


-- Assuming you have teacherIds from the Teacher table
-- Adjust the teacherIds accordingly based on your actual data
-- Inserting data into the Module table
INSERT INTO Module (moduleName, modulePassword, teacherId)
VALUES
('Mathematics 101', 'math123', 1),  -- Associated with Teacher John Smith
('Physics Fundamentals', 'physics456', 2),  -- Associated with Teacher Emma Johnson
('History Through Ages', 'history789', 3),  -- Associated with Teacher Michael Davis
('Literature Exploration', 'litABC', 4),  -- Associated with Teacher Sophia Wilson
('Computer Science Basics', 'csDEF', 5);  -- Associated with Teacher Daniel Thomas


-- Inserting data into the Quiz table
INSERT INTO Quiz (moduleId, startAt, duration)
VALUES
(1, '2024-03-01 10:00:00', 60),  -- Quiz for Mathematics 101, starts on 2024-03-01 at 10:00 AM, duration 60 minutes
(2, '2024-03-05 14:30:00', 45),  -- Quiz for Physics Fundamentals, starts on 2024-03-05 at 2:30 PM, duration 45 minutes
(3, '2024-03-10 09:15:00', 75),  -- Quiz for History Through Ages, starts on 2024-03-10 at 9:15 AM, duration 75 minutes
(4, '2024-03-15 13:45:00', 90),  -- Quiz for Literature Exploration, starts on 2024-03-15 at 1:45 PM, duration 90 minutes
(5, '2024-03-20 11:30:00', 120);  -- Quiz for Computer Science Basics, starts on 2024-03-20 at 11:30 AM, duration 120 minutes


-- Insert data into Question table
INSERT INTO Question (quizId, text, image, createdAt, updatedAt)
VALUES
  -- Question 1 for Quiz 1
  (1, 'What is the capital of France?', NULL, NOW(), NOW()),

  -- Question 2 for Quiz 1
  (1, 'Who wrote Romeo and Juliet?', NULL, NOW(), NOW()),

  -- Question 1 for Quiz 2
  (2, 'What is the largest mammal?', NULL, NOW(), NOW()),

  -- Question 2 for Quiz 2
  (2, 'Which planet is known as the Red Planet?', NULL, NOW(), NOW());
-- Add more rows as needed


-- Insert data into Response table
INSERT INTO Response (questionId, text, image, isCorrect)
VALUES
  -- Responses for Question 1
  (1, 'Paris', NULL, true), -- Correct answer
  (1, 'Berlin', NULL, false),
  (1, 'London', NULL, false),
  (1, 'Madrid', NULL, false),

  -- Responses for Question 2
  (2, 'William Shakespeare', NULL, true), -- Correct answer
  (2, 'Jane Austen', NULL, false),
  (2, 'Charles Dickens', NULL, false),
  (2, 'Mark Twain', NULL, false),

  -- Responses for Question 3
  (3, 'Blue Whale', NULL, true), -- Correct answer
  (3, 'Elephant', NULL, false),
  (3, 'Giraffe', NULL, false),
  (3, 'Polar Bear', NULL, false),

  -- Responses for Question 4
  (4, 'Mars', NULL, true), -- Correct answer
  (4, 'Venus', NULL, false),
  (4, 'Jupiter', NULL, false),
  (4, 'Saturn', NULL, false);
-- Add more rows as needed


-- Insert data into StudentResponse table
INSERT INTO StudentResponse (userId, responseId, isMatch)
VALUES
  (1, 1, true), -- Student 1 matched Response 1
  (2, 2, false), -- Student 2 didn't match Response 2
  (3, 3, true), -- Student 3 matched Response 3
  (4, 4, false), -- Student 4 didn't match Response 4
  (5, 5, true); -- Student 5 matched Response 5
-- Add more rows as needed

-- Insert data into QuizResult table
INSERT INTO QuizResult (studentId, quizId, mark)
VALUES
  (1, 1, 85.5), -- Student 1 scored 85.5 in Quiz 1
  (2, 1, 72.0), -- Student 2 scored 72.0 in Quiz 1
  (3, 2, 90.5), -- Student 3 scored 90.5 in Quiz 2
  (4, 2, 68.0), -- Student 4 scored 68.0 in Quiz 2
  (5, 3, 78.5); -- Student 5 scored 78.5 in Quiz 3
-- Add more rows as needed
