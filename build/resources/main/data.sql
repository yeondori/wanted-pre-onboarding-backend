INSERT INTO MEMBER (MEMBER_ID, NAME) VALUES(1L, '사용자1');
INSERT INTO MEMBER (MEMBER_ID, NAME) VALUES(2L, '사용자2');

INSERT INTO COMPANY (COMPANY_ID, NAME, COUNTRY, REGION) VALUES(1L, '원티드랩', '한국', '서울');
INSERT INTO COMPANY (COMPANY_ID, NAME, COUNTRY, REGION) VALUES(2L, '원티드코리아', '한국', '부산');
INSERT INTO COMPANY (COMPANY_ID, NAME, COUNTRY, REGION) VALUES(3L, '네이버', '한국', '판교');
INSERT INTO COMPANY (COMPANY_ID, NAME, COUNTRY, REGION) VALUES(4L, '카카오', '한국', '판교');

INSERT INTO JOB_POSTING (COMPANY_ID, POST_ID ,JOB_POSITION, RECRUITMENT_COMPENSATION, RECRUITMENT_DETAILS, TECHNOLOGY_USED) VALUES (1L, 1234L,'백엔드 주니어 개발자', 1000000, '원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..', 'Python');
INSERT INTO JOB_POSTING (COMPANY_ID, POST_ID ,JOB_POSITION, RECRUITMENT_COMPENSATION, RECRUITMENT_DETAILS, TECHNOLOGY_USED) VALUES (2L, 2314L,'프론트엔드 개발자', 500000, '원티드코리아에서 프론트엔드 개발자를 채용합니다. 자격요건은..', 'javascript');
INSERT INTO JOB_POSTING (COMPANY_ID, POST_ID ,JOB_POSITION, RECRUITMENT_COMPENSATION, RECRUITMENT_DETAILS, TECHNOLOGY_USED) VALUES (3L, 4324L,'Django 백엔드 개발자', 1000000, '네이버에서 Django 백엔드 개발자를 채용합니다. 자격요건은..', 'Django');
INSERT INTO JOB_POSTING (COMPANY_ID, POST_ID ,JOB_POSITION, RECRUITMENT_COMPENSATION, RECRUITMENT_DETAILS, TECHNOLOGY_USED) VALUES (4L, 5644L,'Django 백엔드 개발자', 500000, '카카오에서 Django 백엔드 개발자를 채용합니다. 자격요건은..', 'Python');