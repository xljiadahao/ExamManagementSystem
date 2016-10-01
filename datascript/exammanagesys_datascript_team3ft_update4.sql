drop table EXAM_QUESTION_ITEM_ANSWER;
drop table EXAM_SESSION;
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
create table EXAM_QUESTION_ITEM_ANSWER(
PK_EXAM_QUESTION_ITEM_ANSWER_ID int primary key auto_increment,
FK_EXAM_SESSION_ID int not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_EXAM_SESSION_ID foreign key(FK_EXAM_SESSION_ID) references EXAM_SESSION(PK_EXAM_SESSION_ID),
FK_QUESTION_ID int not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID),
FK_STUDENT_ID varchar(10) not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_STUDENT_ID foreign key(FK_STUDENT_ID) references USER(USER_ID),
CREATED_TIME timestamp not null,
ANSWER varchar(254) not null
);