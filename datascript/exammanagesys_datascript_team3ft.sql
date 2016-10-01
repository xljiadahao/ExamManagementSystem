drop database exammanagementsystem;
create database exammanagementsystem;
use exammanagementsystem;

-- -----------------------------------------------------
-- Table MODULE
-- -----------------------------------------------------
--drop table if exists MODULE cascade constraints purge;
create table MODULE(
PK_MODULE_CODE varchar(10) primary key,
UK_MODULE_NAME varchar(50) not null unique
);

insert into MODULE(PK_MODULE_CODE,UK_MODULE_NAME) values('SG5101','OO Analysis and Design');
insert into MODULE(PK_MODULE_CODE,UK_MODULE_NAME) values('SG5208','OO Design Patterns');
insert into MODULE(PK_MODULE_CODE,UK_MODULE_NAME) values('SG5209','Enterprise Java');
insert into MODULE(PK_MODULE_CODE,UK_MODULE_NAME) values('SG5225','Architecting Software Solutions');
insert into MODULE(PK_MODULE_CODE,UK_MODULE_NAME) values('SG5103','Software Quality Management');


-- -----------------------------------------------------
-- Table SUBJECT_TAG
-- -----------------------------------------------------
--drop table if exists SUBJECT_TAG cascade constraints purge;
create table SUBJECT_TAG(
PK_TAG_ID int primary key auto_increment,
UK_TAG_NAME varchar(50) not null unique,
FK_MODULE_CODE varchar(10) not null, 
constraint SUBJECT_TAG_FK_MODULE_CODE foreign key(FK_MODULE_CODE) references MODULE(PK_MODULE_CODE) on delete cascade
);

insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('EJB','SG5209');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('JPA','SG5209');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('JSF','SG5209');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('PrimeFaces','SG5209');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Software Requirement Modeling','SG5101');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Software Analysis Modeling','SG5101');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Software Design Modeling','SG5101');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Servlet','SG5101');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('JSP','SG5101');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('MVC','SG5101');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('OO Creational Patterns','SG5208');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('OO Structural Patterns','SG5208');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Software Design Principle','SG5208');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Architecting Design','SG5225');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Architecting for Security','SG5225');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Architecting for Performance','SG5225');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Architecting for Capacity','SG5225');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Architecting for Maintainability','SG5209');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('ISO','SG5103');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Software Testing','SG5103');
insert into SUBJECT_TAG(UK_TAG_NAME,FK_MODULE_CODE) values('Peer Review','SG5103');


-- -----------------------------------------------------
-- Table EXAM_PAPER
-- -----------------------------------------------------
--drop table if exists EXAM_PAPER cascade constraints purge;
CREATE TABLE EXAM_PAPER( 
PK_EXAM_ID int primary key auto_increment,
--CREATE_DATE datetime not null default CURDATE(), 
CREATE_DATE timestamp not null,
FK_MODULE_CODE varchar(10) not null, 
constraint EXAM_PAPER_FK_MODULE_CODE foreign key(FK_MODULE_CODE) references MODULE(PK_MODULE_CODE) on delete cascade
); 

--alter table ADMIN_INFO change ENROLLDATE ENROLLDATE timestamp;
--alter table ADMIN_INFO change PASSWORD PASSWORD varchar(30) not null default '123456';

insert into EXAM_PAPER(FK_MODULE_CODE) values('SG5209');  
--insert into EXAM_PAPER(FK_MODULE_CODE) values('SG5209');
--insert into EXAM_PAPER(FK_MODULE_CODE) values('SG5208');  
--insert into EXAM_PAPER(FK_MODULE_CODE) values('SG5101');
insert into EXAM_PAPER(FK_MODULE_CODE) values('SG5225');


-- ---------------------------------------------------------------
-- Table EXAM_SECTION
-- Column SECTION_TYPE, 'M' means manual, 'A' means automatic
-- ---------------------------------------------------------------
--drop table account_z cascade constraints purge;
--drop table if exists EXAM_SECTION cascade constraints purge;
create table EXAM_SECTION(
PK_EXAM_SECTION_ID int primary key auto_increment,
SECTION_NAME varchar(30) not null, 
FK_EXAM_ID int not null, 
constraint EXAM_SECTION_FK_EXAM_ID foreign key(FK_EXAM_ID) references EXAM_PAPER(PK_EXAM_ID) on delete cascade,
--constraint EXAM_SECTION_COMPOSITE_PK PRIMARY KEY(SECTION_NAME,FK_EXAM_ID),
constraint SECTION_NAME_FK_EXAM_ID_COMPOSITE_UK unique(SECTION_NAME,FK_EXAM_ID),
TOTAL_MARKS int not null,
SECTION_TYPE varchar(1) not null check(SECTION_TYPE in('M','A'))
);

insert into EXAM_SECTION(SECTION_NAME,FK_EXAM_ID,TOTAL_MARKS,SECTION_TYPE) values('SECTION A',1,40,'M'); 
insert into EXAM_SECTION(SECTION_NAME,FK_EXAM_ID,TOTAL_MARKS,SECTION_TYPE) values('SECTION B',1,30,'M');
insert into EXAM_SECTION(SECTION_NAME,FK_EXAM_ID,TOTAL_MARKS,SECTION_TYPE) values('SECTION A',2,20,'M'); 
insert into EXAM_SECTION(SECTION_NAME,FK_EXAM_ID,TOTAL_MARKS,SECTION_TYPE) values('SECTION B',2,20,'M'); 
insert into EXAM_SECTION(SECTION_NAME,FK_EXAM_ID,TOTAL_MARKS,SECTION_TYPE) values('SECTION C',2,20,'M'); 


-- -----------------------------------------------------------------------------------------------
-- Table USER
-- column USER_ROLE, 'Y' means new user (need to change the password), 'N' means old user
-- -----------------------------------------------------------------------------------------------
create table USER(
USER_ID varchar(10) primary key,
PASSWORD varchar(30) not null default '123456',
USER_NAME varchar(30) not null,
IS_NEW_USER varchar(1) not null check(IS_NEW_USER in('Y','N'))
);

insert into USER(USER_ID,USER_NAME,IS_NEW_USER) values('A0120010R','XU LEI','Y'); 
insert into USER(USER_ID,PASSWORD,USER_NAME,IS_NEW_USER) values('A0121910X','111111','SHAO XI','N');
insert into USER(USER_ID,USER_NAME,IS_NEW_USER) values('A0120619M','CHANDRAKALA','Y');

insert into USER(USER_ID,PASSWORD,USER_NAME,IS_NEW_USER) values('L0120000L','111111','CHUK LEE','N'); 
insert into USER(USER_ID,PASSWORD,USER_NAME,IS_NEW_USER) values('L0120001L','111111','HENG BOON KUI','N');
insert into USER(USER_ID,PASSWORD,USER_NAME,IS_NEW_USER) values('L0120011L','111111','SWARNALATHA','N');
insert into USER(USER_ID,USER_NAME,IS_NEW_USER) values('L0120002L','YUNGHANS IRAWAN','Y');

insert into USER(USER_ID,PASSWORD,USER_NAME,IS_NEW_USER) values('D0000001D','111111','HOWARD','N'); 
insert into USER(USER_ID,PASSWORD,USER_NAME,IS_NEW_USER) values('D0000002D','111111','ROBERT','N');


-- -----------------------------------------------------------------------------------------------
-- Table USER_GROUP
-- -----------------------------------------------------------------------------------------------
create table GROUP_USER(
GROUP_ID varchar(10) not null check(GROUP_ID in('admin','lecturer','student')),
USER_ID varchar(10) not null unique,
constraint GROUP_USER_USER_ID foreign key(USER_ID) references USER(USER_ID) on delete cascade,
constraint GROUP_ID_USER_ID_COMPOSITE_PK primary key(GROUP_ID,USER_ID)
);

insert into GROUP_USER(USER_ID,GROUP_ID) values('A0120010R','student'); 
insert into GROUP_USER(USER_ID,GROUP_ID) values('A0121910X','student');
insert into GROUP_USER(USER_ID,GROUP_ID) values('A0120619M','student');

insert into GROUP_USER(USER_ID,GROUP_ID) values('L0120000L','lecturer'); 
insert into GROUP_USER(USER_ID,GROUP_ID) values('L0120001L','lecturer');
insert into GROUP_USER(USER_ID,GROUP_ID) values('L0120011L','lecturer');
insert into GROUP_USER(USER_ID,GROUP_ID) values('L0120002L','lecturer');

insert into GROUP_USER(USER_ID,GROUP_ID) values('D0000001D','admin'); 
insert into GROUP_USER(USER_ID,GROUP_ID) values('D0000002D','admin');


-- -----------------------------------------------------------------------------------------------
-- Table STUDENT
-- column IS_NEW_USER, 'Y' means new user (need to change the password), 'N' means old user
-- -----------------------------------------------------------------------------------------------
--drop table if exists STUDENT cascade constraints purge;
--create table STUDENT(
--PK_STUDENT_ID varchar(10) primary key,
--PASSWORD varchar(30) not null default '123456',
--STUDENT_NAME varchar(30) not null,
--IS_NEW_USER varchar(1) not null check(IS_NEW_USER in('Y','N'))
--);

--insert into STUDENT(PK_STUDENT_ID,STUDENT_NAME,IS_NEW_USER) values('A0120010R','XU LEI','Y'); 
--insert into STUDENT(PK_STUDENT_ID,STUDENT_NAME,IS_NEW_USER) values('A0121910X','SHAO XI','N');
--insert into STUDENT(PK_STUDENT_ID,STUDENT_NAME,IS_NEW_USER) values('A0120619M','CHANDRAKALA','N');


-- -----------------------------------------------------
-- Table STUDENT_MODULES_ENROLLED
-- -----------------------------------------------------
--drop table if exists STUDENT_MODULES_ENROLLED cascade constraints purge;
create table STUDENT_MODULES_ENROLLED(
FK_STUDENT_ID varchar(10) not null, 
constraint STUDENT_MODULES_ENROLLED_FK_STUDENT_ID foreign key(FK_STUDENT_ID) references USER(USER_ID) on delete cascade,
FK_MODULE_CODE varchar(10) not null, 
constraint STUDENT_MODULES_ENROLLED_FK_MODULE_CODE foreign key(FK_MODULE_CODE) references MODULE(PK_MODULE_CODE) on delete cascade,
constraint FK_STUDENT_ID_FK_MODULE_CODE_COMPOSITE_PK primary key(FK_STUDENT_ID,FK_MODULE_CODE)
);

insert into STUDENT_MODULES_ENROLLED(FK_STUDENT_ID,FK_MODULE_CODE) values('A0120010R','SG5209'); 
insert into STUDENT_MODULES_ENROLLED(FK_STUDENT_ID,FK_MODULE_CODE) values('A0120010R','SG5101');
insert into STUDENT_MODULES_ENROLLED(FK_STUDENT_ID,FK_MODULE_CODE) values('A0120619M','SG5101');


-- -----------------------------------------------------------------------------------------------
-- Table LECTURER
-- column IS_NEW_USER, 'Y' means new user (need to change the password), 'N' means old user
-- -----------------------------------------------------------------------------------------------
--drop table if exists LECTURER cascade constraints purge;
--create table LECTURER(
--PK_LECTURER_ID varchar(10) primary key,
--PASSWORD varchar(30) not null default '123456',
--LECTURER_NAME varchar(30) not null,
--IS_NEW_USER varchar(1) not null check(IS_NEW_USER in('Y','N'))
--);

--insert into LECTURER(PK_LECTURER_ID,LECTURER_NAME,IS_NEW_USER) values('L0120000L','CHUK LEE','N'); 
--insert into LECTURER(PK_LECTURER_ID,LECTURER_NAME,IS_NEW_USER) values('A0120001M','HENG BOON KUI','N');
--insert into LECTURER(PK_LECTURER_ID,LECTURER_NAME,IS_NEW_USER) values('A0120011M','SWARNALATHA','N');
--insert into LECTURER(PK_LECTURER_ID,LECTURER_NAME,IS_NEW_USER) values('A0120002W','YUNGHANS IRAWAN','N');


-- -----------------------------------------------------
-- Table LECTURER_MODULES_TAUGHT
-- -----------------------------------------------------
--drop table if exists LECTURER_MODULES_TAUGHT cascade constraints purge;
create table LECTURER_MODULES_TAUGHT(
FK_LECTURER_ID varchar(10) not null, 
constraint LECTURER_MODULES_TAUGHT_FK_LECTURER_ID foreign key(FK_LECTURER_ID) references USER(USER_ID) on delete cascade,
FK_MODULE_CODE varchar(10) not null, 
constraint LECTURER_MODULES_TAUGHT_FK_MODULE_CODE foreign key(FK_MODULE_CODE) references MODULE(PK_MODULE_CODE) on delete cascade,
constraint FK_LECTURER_ID_FK_MODULE_CODE_COMPOSITE_PK primary key(FK_LECTURER_ID,FK_MODULE_CODE)
);

insert into LECTURER_MODULES_TAUGHT(FK_LECTURER_ID,FK_MODULE_CODE) values('L0120000L','SG5209'); 
insert into LECTURER_MODULES_TAUGHT(FK_LECTURER_ID,FK_MODULE_CODE) values('L0120001L','SG5101');
insert into LECTURER_MODULES_TAUGHT(FK_LECTURER_ID,FK_MODULE_CODE) values('L0120011L','SG5101');
insert into LECTURER_MODULES_TAUGHT(FK_LECTURER_ID,FK_MODULE_CODE) values('L0120002L','SG5225');


-- -----------------------------------------------------------------------------------------------
-- Table ADMINISTRATOR
-- column IS_NEW_USER, 'Y' means new user (need to change the password), 'N' means old user
-- -----------------------------------------------------------------------------------------------
--drop table if exists ADMINISTRATOR cascade constraints purge;
--create table ADMINISTRATOR(
--PK_ADMINISTRATOR_ID varchar(10) primary key,
--PASSWORD varchar(30) not null default '123456',
--ADMINISTRATOR_NAME varchar(30) not null
--);

--insert into ADMINISTRATOR(PK_ADMINISTRATOR_ID,ADMINISTRATOR_NAME) values('A0000001D','HOWARD'); 
--insert into ADMINISTRATOR(PK_ADMINISTRATOR_ID,ADMINISTRATOR_NAME) values('A0000002D','ROBERT');


-- -----------------------------------------------------
-- Table QUESTION
-- Column VCERSION_NUMBER format like 'V1'
-- -----------------------------------------------------
--drop table if exists QUESTION cascade constraints purge;
--create index account_z_id_idx on account_z(id);  
create table QUESTION(
PK_QUESTION_ID int primary key auto_increment,
QUESTION_SEQUENCE int not null,
VERSION_NUMBER varchar(2) not null,
constraint QUESTION_SEQUENCE_VERSION_NUMBER_COMPOSITE_UK unique(QUESTION_SEQUENCE,VERSION_NUMBER),
CREATED_ON timestamp not null,
FK_CREATED_BY varchar(10) not null, 
constraint QUESTION_FK_CREATED_BY foreign key(FK_CREATED_BY) references USER(USER_ID),
MARK int not null
);

insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(1,'V1','L0120000L',10);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(1,'V2','L0120000L',20);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(2,'V1','L0120000L',20);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(3,'V1','L0120000L',30);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(4,'V1','L0120000L',20);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(5,'V1','L0120001L',20);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(6,'V1','L0120001L',30);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(7,'V1','L0120011L',10);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(8,'V1','L0120011L',10);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(9,'V1','L0120002L',20);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(10,'V1','L0120002L',20);
insert into QUESTION(QUESTION_SEQUENCE,VERSION_NUMBER,FK_CREATED_BY,MARK) values(11,'V1','L0120002L',20);


-- ---------------------------------------------------------------------------------------------------------------------------------
-- Table QUESTION_ITEM
-- Column QUESTION_ITEM_TYPE, 'MS': Multiple Choice, Single Answer; 'MM': Multiple Choice, Multiple Answer; 'WA': Written Answer
-- ---------------------------------------------------------------------------------------------------------------------------------
--drop table if exists QUESTION_ITEM cascade constraints purge;
create table QUESTION_ITEM(
PK_QUESTION_ITEM_ID int primary key auto_increment,
FK_QUESTION_ID int not null, 
constraint QUESTION_ITEM_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID),
QUESTION_ITEM_TYPE varchar(2) not null check(QUESTION_ITEM_TYPE in('MS','MM','WA')),
QUESTION_ITEM_TEXT varchar(254) not null
);

insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(1,'WA','What is EJB?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(2,'WA','What is the typical feature of EJB?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(3,'MS','Is JSF component oriented programming or action oriented programming?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(4,'MM','What are the status under persistence context of JPA?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(5,'WA','What is JPA?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(6,'WA','Give one example for the transition strategy from analysis to design.');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(7,'WA','What is servlet?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(7,'MM','Which of the following items are the servlet when we develop the basic JavaEE application?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(8,'WA','What is sequence diagram?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(9,'WA','What is the relationship between controller and enetity?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(10,'WA','What is logic view?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(11,'WA','When we build one big enterprise web application, what should we consider about?');
insert into QUESTION_ITEM(FK_QUESTION_ID,QUESTION_ITEM_TYPE,QUESTION_ITEM_TEXT) values(12,'WA','Give 3 example of the software security problems?');


-- -------------------------------------------------------
-- Table QUESTION_MULTIPLE_CHOICE_OPTIONS
-- -------------------------------------------------------
--drop table service_z cascade constraints purge;
--drop table if exists QUESTION_MULTIPLE_CHOICE_OPTIONS cascade constraints purge;
create table QUESTION_MULTIPLE_CHOICE_OPTIONS(
FK_QUESTION_ITEM_ID int not null, 
constraint QUESTION_MULTIPLE_CHOICE_OPTIONS_FK_QUESTION_ITEM_ID foreign key(FK_QUESTION_ITEM_ID) references QUESTION_ITEM(PK_QUESTION_ITEM_ID),
QUESTION_MULTIPLE_CHOICE_OPTION_TEXT varchar(50) not null
);

insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(3,'Component Oriented Programming');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(3,'Action Oriented Programming');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'New Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'Managed Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'Detached Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(4,'Removed Entity');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(8,'Presentation Layer (JSP)');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(8,'Controller');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(8,'Business Layer');
insert into QUESTION_MULTIPLE_CHOICE_OPTIONS(FK_QUESTION_ITEM_ID,QUESTION_MULTIPLE_CHOICE_OPTION_TEXT) values(8,'Entity');


-- -----------------------------------------------------
-- Table SUBJECT_TAG_QUESTION
-- One exam paper cannot have two same questions
-- The related questions are the latest version ones
-- -----------------------------------------------------
--drop table if exists SUBJECT_TAG_QUESTION cascade constraints purge; 
create table SUBJECT_TAG_QUESTION(
FK_TAG_ID int not null, 
constraint SUBJECT_TAG_QUESTION_FK_TAG_ID foreign key(FK_TAG_ID) references SUBJECT_TAG(PK_TAG_ID) on delete cascade,
FK_QUESTION_ID int not null, 
constraint SUBJECT_TAG_QUESTION_FK_QUESTION_ID foreign key(FK_QUESTION_ID) references QUESTION(PK_QUESTION_ID) on delete cascade,
constraint FK_TAG_ID_FK_QUESTION_ID_COMPOSITE_PK primary key(FK_TAG_ID,FK_QUESTION_ID)
);

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
--drop table if exists EXAM_SECTION_QUESTION cascade constraints purge; 
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
--drop table if exists EXAM_SESSION cascade constraints purge;
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
--drop table if exists EXAM_QUESTION_ITEM_ANSWER cascade constraints purge;
create table EXAM_QUESTION_ITEM_ANSWER(
PK_EXAM_QUESTION_ITEM_ANSWER_ID int primary key auto_increment,
FK_EXAM_SESSION_ID int not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_EXAM_SESSION_ID foreign key(FK_EXAM_SESSION_ID) references EXAM_SESSION(PK_EXAM_SESSION_ID),
FK_QUESTION_ITEM_ID int not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_QUESTION_ITEM_ID foreign key(FK_QUESTION_ITEM_ID) references QUESTION_ITEM(PK_QUESTION_ITEM_ID),
FK_STUDENT_ID varchar(10) not null, 
constraint EXAM_QUESTION_ITEM_ANSWER_FK_STUDENT_ID foreign key(FK_STUDENT_ID) references USER(USER_ID),
CREATED_TIME timestamp not null,
ANSWER varchar(254) not null
);
