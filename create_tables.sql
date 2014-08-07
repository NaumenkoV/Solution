USE interview;

CREATE TABLE ApplicantInterview
(
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  applicantId VARCHAR(255),
  finalComment VARCHAR(255)
);
CREATE TABLE ApplicantInterview_InterviewQuestions
(
  ApplicantInterview_id VARCHAR(255) NOT NULL,
  questions_question_id VARCHAR(255) NOT NULL
);
CREATE TABLE ApplicantInterview_usersId
(
  ApplicantInterview_id VARCHAR(255) NOT NULL,
  usersId VARCHAR(255)
);
CREATE TABLE Applicants
(
  Id VARCHAR(255) PRIMARY KEY NOT NULL,
  Birthday BIGINT,
  Email VARCHAR(255),
  Name VARCHAR(255),
  Phone VARCHAR(255),
  Surname VARCHAR(255)
);
CREATE TABLE ApplicantsWithStatus
(
  Id VARCHAR(255) NOT NULL,
  ApplicantsStatus TINYBLOB,
  ApplicantId VARCHAR(255) NOT NULL,
  PRIMARY KEY ( Id, ApplicantId )
);
CREATE TABLE Appointment_userIdList
(
  Appointment_Id VARCHAR(255) NOT NULL,
  UserIdList VARCHAR(255)
);
CREATE TABLE Appointments
(
  Id VARCHAR(255) PRIMARY KEY NOT NULL,
  ActualStartTime BIGINT,
  ApplicantId VARCHAR(255),
  DurationTime BIGINT,
  GroupId VARCHAR(255),
  StartTime BIGINT
);
CREATE TABLE Course
(
  Id VARCHAR(255) PRIMARY KEY NOT NULL,
  ImageReference VARCHAR(255),
  Name VARCHAR(255)
);
CREATE TABLE Groups
(
  Id VARCHAR(255) PRIMARY KEY NOT NULL,
  Address VARCHAR(255),
  Capacity INT,
  EndTime BIGINT,
  GroupName VARCHAR(255),
  StartBoardingTime BIGINT,
  StartTime BIGINT,
  course_Id VARCHAR(255)
);
CREATE TABLE InterviewQuestions
(
  question_id VARCHAR(255) PRIMARY KEY NOT NULL,
  comment VARCHAR(255) NOT NULL,
  mark INT,
  question VARCHAR(255) NOT NULL,
  weight INT
);
CREATE TABLE Questions
(
  question_id VARCHAR(255) PRIMARY KEY NOT NULL,
  Question VARCHAR(255),
  Weight INT
);
CREATE TABLE Roles
(
  Id VARCHAR(255) PRIMARY KEY NOT NULL,
  Name VARCHAR(255)
);
CREATE TABLE Users
(
  Id VARCHAR(255) PRIMARY KEY NOT NULL,
  Active TINYINT,
  Email VARCHAR(255),
  Name VARCHAR(255),
  Password VARCHAR(255),
  Phone VARCHAR(255),
  Surname VARCHAR(255),
  role_Id VARCHAR(255)
);
CREATE TABLE Users_Questions
(
  Users_Id VARCHAR(255) NOT NULL,
  questions_question_id VARCHAR(255) NOT NULL
);
ALTER TABLE ApplicantInterview_InterviewQuestions ADD FOREIGN KEY ( ApplicantInterview_id ) REFERENCES ApplicantInterview ( id );
ALTER TABLE ApplicantInterview_InterviewQuestions ADD FOREIGN KEY ( questions_question_id ) REFERENCES InterviewQuestions ( question_id );
CREATE UNIQUE INDEX UK_1y7dguxqmd0tvis6ceicfovde ON ApplicantInterview_InterviewQuestions ( questions_question_id );
CREATE INDEX FK_1y7dguxqmd0tvis6ceicfovde ON ApplicantInterview_InterviewQuestions ( questions_question_id );
CREATE INDEX FK_7xoqbjboirwts35vgj0v8fgjj ON ApplicantInterview_InterviewQuestions ( ApplicantInterview_id );
ALTER TABLE ApplicantInterview_usersId ADD FOREIGN KEY ( ApplicantInterview_id ) REFERENCES ApplicantInterview ( id );
CREATE INDEX FK_gjnrhckwrjhnm0ofw6n06w21a ON ApplicantInterview_usersId ( ApplicantInterview_id );
CREATE UNIQUE INDEX UK_cmfw5titdbpbaakmenpthap2q ON Applicants ( Email );
ALTER TABLE ApplicantsWithStatus ADD FOREIGN KEY ( Id ) REFERENCES Groups ( Id );
CREATE INDEX FK_c2xcnsvqkndh86y3o6ikeo3em ON ApplicantsWithStatus ( Id );
ALTER TABLE Appointment_userIdList ADD FOREIGN KEY ( Appointment_Id ) REFERENCES Appointments ( Id );
CREATE INDEX FK_ru4e64txcphwjb2f18auoatd2 ON Appointment_userIdList ( Appointment_Id );
CREATE UNIQUE INDEX UK_761rlm7xexhrx5w5xdg79g86c ON Course ( ImageReference );
CREATE UNIQUE INDEX UK_n8s02upv6qsg0ifdp27jyqmje ON Course ( Name );
ALTER TABLE Groups ADD FOREIGN KEY ( course_Id ) REFERENCES Course ( Id );
CREATE UNIQUE INDEX UK_lysadmheq7r9mxfllh6byrp0k ON Groups ( GroupName );
CREATE INDEX FK_p0odf6ljc4mbfwh9yhissfa6q ON Groups ( course_Id );
ALTER TABLE Users ADD FOREIGN KEY ( role_Id ) REFERENCES Roles ( Id );
CREATE UNIQUE INDEX UK_jdfr6kjrxekx1j5vrr77rp44t ON Users ( Email );
CREATE INDEX FK_cd72g22xtwhcbveah8umq8c9n ON Users ( role_Id );
ALTER TABLE Users_Questions ADD FOREIGN KEY ( Users_Id ) REFERENCES Users ( Id );
ALTER TABLE Users_Questions ADD FOREIGN KEY ( questions_question_id ) REFERENCES Questions ( question_id );
CREATE UNIQUE INDEX UK_29ywdxiwein9kvh34bub0ras5 ON Users_Questions ( questions_question_id );
CREATE INDEX FK_29ywdxiwein9kvh34bub0ras5 ON Users_Questions ( questions_question_id );
CREATE INDEX FK_oknbhrdr1xvid2q20p2kqbe2d ON Users_Questions ( Users_Id );
