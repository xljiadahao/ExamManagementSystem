drop table EXAM_QUESTION_ITEM_ANSWER;
drop table EXAM_SESSION;
drop table EXAM_SECTION_QUESTION;
drop table SUBJECT_TAG_QUESTION;
drop table QUESTION_MULTIPLE_CHOICE_OPTIONS;
drop table QUESTION_ITEM;
drop table QUESTION;

-- -----------------------------------------------------
-- Table QUESTION
-- Column VCERSION_NUMBER format like 'V1'
-- -----------------------------------------------------
create table QUESTION(
PK_QUESTION_ID int primary key auto_increment,
QUESTION_SEQUENCE int not null,
VERSION_NUMBER varchar(2) not null,
constraint QUESTION_SEQUENCE_VERSION_NUMBER_COMPOSITE_UK unique(QUESTION_SEQUENCE,VERSION_NUMBER),
CREATED_ON timestamp not null,
FK_CREATED_BY varchar(10) not null, 
constraint QUESTION_FK_CREATED_BY foreign key(FK_CREATED_BY) references USER(USER_ID),
MARK int not null,
QUESTION_TYPE varchar(2) not null check(QUESTION_ITEM_TYPE in('MS','MM','WA')),
QUESTION_TEXT varchar(254) not null,
QUESTION_STATUS varchar(7) not null check(QUESTION_STATUS in('ENABLE','DISABLE'))
);

insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(1,'V1','L0120000L',10,'DISABLE','WA','What is EJB?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(1,'V2','L0120000L',20,'ENABLE','WA','What is the typical feature of EJB?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(2,'V1','L0120000L',20,'ENABLE','MS','Is JSF component oriented programming or action oriented programming?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(3,'V1','L0120000L',30,'ENABLE','MM','What are the status under persistence context of JPA?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(4,'V1','L0120000L',20,'ENABLE','WA','What is JPA?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(5,'V1','L0120001L',20,'ENABLE','WA','Give one example for the transition strategy from analysis to design.');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(6,'V1','L0120001L',30,'ENABLE','MM','Which of the following items are the servlet when we develop the basic JavaEE application?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(7,'V1','L0120011L',10,'ENABLE','WA','What is sequence diagram?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(8,'V1','L0120011L',10,'ENABLE','WA','What is the relationship between controller and enetity?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(9,'V1','L0120002L',20,'ENABLE','WA','What is logic view?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(10,'V1','L0120002L',20,'ENABLE','WA','When we build one big enterprise web application, what should we consider about?');
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK,QUESTION_STATUS,QUESTION_TYPE,QUESTION_TEXT) values(11,'V1','L0120002L',20,'ENABLE','WA','Give 3 example of the software security problems?');


-- -------------------------------------------------------
-- Table QUESTION_MULTIPLE_CHOICE_OPTIONS
-- -------------------------------------------------------
create table QUESTION_MULTIPLE_CHOICE_OPTIONS(
PK_QUESTION_MULTIPLE_CHOICE_OPTIONS int primary key auto_increment,
FK_QUESTION_ID int not null, 
constraint QUESTION_MULTIPLE_CHOICE_OPTIONS_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID),
QUESTION_MULTIPLE_CHOICE_OPTION_TEXT varchar(50) not null
);

insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(3,'Component Oriented Programming');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(3,'Action Oriented Programming');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(3,'Neither');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(3,'Both');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'New Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'Managed Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'Detached Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'Removed Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(7,'Presentation Layer (JSP)');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(7,'Controller');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(7,'Business Layer');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(7,'Entity');


-- -----------------------------------------------------
-- Table SUBJECT_TAG_QUESTION
-- One exam paper cannot have two same questions
-- The related questions are the latest version ones
-- -----------------------------------------------------
create table SUBJECT_TAG_QUESTION(
FK_TAG_ID int not null, 
constraint SUBJECT_TAG_QUESTION_FK_TAG_ID foreign key(FK_TAG_ID) references SUBJECT_TAG(PK_TAG_ID) on delete cascade,
FK_QUESTION_ID int not null, 
constraint SUBJECT_TAG_QUESTION_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID) on delete cascade,
constraint FK_TAG_ID_FK_QUESTION_ID_COMPOSITE_PK primary key(FK_TAG_ID,FK_QUESTION_ID)
);

insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(1,1);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(2,1);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(3,3);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(4,2);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(5,2);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(6,6);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(6,7);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(7,8);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(7,9);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(8,7);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(9,6);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(9,7);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(10,14);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(11,15);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(11,16);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(11,17);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(11,18);
insert into SUBJECT_TAG_QUESTION(FK_QUESTION_ID,FK_TAG_ID) values(12,15);


-- -----------------------------------------------------
-- Table EXAM_SECTION_QUESTION
-- One exam paper cannot have two same questions
-- The related questions are the latest version ones
-- -----------------------------------------------------
create table EXAM_SECTION_QUESTION(
FK_EXAM_SECTION_ID int not null, 
constraint EXAM_SECTION_QUESTION_FK_EXAM_SECTION_ID foreign key(FK_EXAM_SECTION_ID) references EXAM_SECTION(PK_EXAM_SECTION_ID) on delete cascade,
FK_EXAM_ID int not null, 
constraint EXAM_SECTION_QUESTION_FK_EXAM_ID foreign key(FK_EXAM_ID) references EXAM_PAPER(PK_EXAM_ID) on delete cascade,
FK_QUESTION_ID int not null, 
constraint EXAM_SECTION_QUESTION_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID) on delete cascade,
constraint FK_EXAM_SECTION_ID_FK_QUESTION_ID_COMPOSITE_PK primary key(FK_EXAM_SECTION_ID,FK_QUESTION_ID),
constraint FK_EXAM_ID_FK_QUESTION_ID_COMPOSITE_UK unique(FK_EXAM_ID,FK_QUESTION_ID)
);

insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_EXAM_ID,FK_QUESTION_ID) values(1,1,2);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_EXAM_ID,FK_QUESTION_ID) values(1,1,3);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_EXAM_ID,FK_QUESTION_ID) values(2,1,4);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_EXAM_ID,FK_QUESTION_ID) values(3,2,10);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_EXAM_ID,FK_QUESTION_ID) values(4,2,11);
insert into EXAM_SECTION_QUESTION(FK_EXAM_SECTION_ID,FK_EXAM_ID,FK_QUESTION_ID) values(5,2,12);


-- -------------------------------------------------------
-- Table EXAM_SESSION
-- Column DURATION unit is minutes
-- -------------------------------------------------------
create table EXAM_SESSION(
PK_EXAM_SESSION_ID int primary key auto_increment,
FK_EXAM_ID int not null, 
constraint EXAM_SESSION_FK_EXAM_ID foreign key(FK_EXAM_ID) references EXAM_PAPER(PK_EXAM_ID),
EXAM_DATE date not null,
EXAM_START_TIME time not null,
DURATION int not null,
LOCATION varchar(100) not null
);

insert into EXAM_SESSION(FK_EXAM_ID,EXAM_DATE,EXAM_START_TIME,DURATION,LOCATION) values(1,'2014-11-27','13:00:00',180,'ISS, NUS');
insert into EXAM_SESSION(FK_EXAM_ID,EXAM_DATE,EXAM_START_TIME,DURATION,LOCATION) values(2,'2014-11-28','10:00:00',180,'ISS, NUS');


-- -------------------------------------------------------
-- Table EXAM_ANSWER
-- -------------------------------------------------------
create table EXAM_QUESTION_ITEM_ANSWER(
PK_EXAM_QUESTION_ITEM_ANSWER_ID int primary key auto_increment,
FK_EXAM_SESSION_ID int not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_EXAM_SESSION_ID foreign key(FK_EXAM_SESSION_ID) references EXAM_SESSION(PK_EXAM_SESSION_ID),
FK_QUESTION_ID int not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_QUESTION_ITEM_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID),
FK_STUDENT_ID varchar(10) not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_STUDENT_ID foreign key(FK_STUDENT_ID) references USER(USER_ID),
CREATED_TIME timestamp not null,
ANSWER varchar(254) not null
);


--this is mapping to teacher and module
--this is mapping student and module
--cascade = CascadeType.ALL, cascade = CascadeType.PERSIST
