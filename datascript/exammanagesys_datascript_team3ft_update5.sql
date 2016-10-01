drop table EXAM_QUESTION_ITEM_ANSWER;
drop table EXAM_SESSION_USER;
drop table EXAM_SESSION;
drop table EXAM_SECTION_QUESTION;

-- -----------------------------------------------------
-- Table EXAM_SECTION_QUESTION
-- One exam paper cannot have two same questions
-- The related questions are the latest version ones
-- -----------------------------------------------------
create table EXAM_SECTION_QUESTION(
FK_EXAM_SECTION_ID int not null, 
constraint EXAM_SECTION_QUESTION_FK_EXAM_SECTION_ID foreign key(FK_EXAM_SECTION_ID) references EXAM_SECTION(PK_EXAM_SECTION_ID) on delete cascade,
FK_QUESTION_ID int not null, 
constraint EXAM_SECTION_QUESTION_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID) on delete cascade,
constraint FK_EXAM_SECTION_ID_FK_QUESTION_ID_COMPOSITE_PK primary key(FK_EXAM_SECTION_ID,FK_QUESTION_ID)
);

insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_QUESTION_ID) values(1,2);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_QUESTION_ID) values(1,3);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_QUESTION_ID) values(2,4);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_QUESTION_ID) values(3,10);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_QUESTION_ID) values(4,11);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_QUESTION_ID) values(5,12);


-- -------------------------------------------------------
-- Table EXAM_SESSION
-- Column DURATION unit is minutes
-- -------------------------------------------------------
create table EXAM_SESSION(
PK_EXAM_SESSION_ID int primary key auto_increment,
FK_EXAM_ID int not null, 
constraint EXAM_SESSION_FK_EXAM_ID foreign key(FK_EXAM_ID) references EXAM_PAPER(PK_EXAM_ID),
EXAM_START_TIME timestamp not null,
EXAM_END_TIME timestamp not null,
LOCATION varchar(100) not null
);

insert into EXAM_SESSION(FK_EXAM_ID,EXAM_START_TIME,EXAM_END_TIME,LOCATION) values(1,'2014-11-27 13:00:00','2014-11-27 16:00:00','ISS, NUS');
insert into EXAM_SESSION(FK_EXAM_ID,EXAM_START_TIME,EXAM_END_TIME,LOCATION) values(2,'2014-11-28 10:00:00','2014-11-28 12:00:00','ISS, NUS');


-- -----------------------------------------------------
-- Table SUBJECT_TAG_QUESTION
-- One exam paper cannot have two same questions
-- The related questions are the latest version ones
-- -----------------------------------------------------
create table EXAM_SESSION_USER(
FK_EXAM_SESSION_ID int not null, 
constraint EXAM_SESSION_USER_FK_EXAM_SESSION_ID foreign key(FK_EXAM_SESSION_ID) references EXAM_SESSION(PK_EXAM_SESSION_ID) on delete cascade,
FK_USER_ID varchar(10) not null, 
constraint EXAM_SESSION_USER_FK_USER_ID foreign key(FK_USER_ID) references USER(USER_ID) on delete cascade,
constraint FK_EXAM_SESSION_ID_FK_USER_ID_COMPOSITE_PK primary key(FK_EXAM_SESSION_ID,FK_USER_ID)
);

insert into EXAM_SESSION_USER(FK_EXAM_SESSION_ID,FK_USER_ID) values(1,'A0120014R');
insert into EXAM_SESSION_USER(FK_EXAM_SESSION_ID,FK_USER_ID) values(1,'A0120010R');
insert into EXAM_SESSION_USER(FK_EXAM_SESSION_ID,FK_USER_ID) values(2,'A0121901X');
insert into EXAM_SESSION_USER(FK_EXAM_SESSION_ID,FK_USER_ID) values(2,'A0120014R');


-- -------------------------------------------------------
-- Table EXAM_ANSWER
-- -------------------------------------------------------
create table EXAM_QUESTION_ANSWER(
PK_EXAM_QUESTION_ANSWER_ID int primary key auto_increment,
FK_EXAM_SESSION_ID int not null, 
constraint EXAM_QUESTION_ANSWER_FK_EXAM_SESSION_ID foreign key(FK_EXAM_SESSION_ID) references EXAM_SESSION(PK_EXAM_SESSION_ID),
FK_QUESTION_ID int not null, 
constraint EXAM_QUESTION_ANSWER_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID),
FK_STUDENT_ID varchar(10) not null, 
constraint EXAM_QUESTION_ANSWER_FK_STUDENT_ID foreign key(FK_STUDENT_ID) references USER(USER_ID),
CREATED_TIME timestamp not null,
ANSWER varchar(254) not null
);

--@NamedQuery(name = "ExamSession.findCurrentExam", query = "SELECT e FROM ExamSession e where CURRENT_TIMESTAMP>e.examStartTime AND CURRENT_TIMESTAMP<e.examEndTime"),
--@NamedQuery(name = "ExamSession.findFollowingExam", query = "SELECT e FROM ExamSession e where CURRENT_TIMESTAMP<e.examStartTime"),

--@NamedQuery(name = "Question.enableQuestionsCount", query = "SELECT COUNT(q) FROM Question q WHERE q.questionStatus='ENABLE'"),