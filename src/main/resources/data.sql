insert into member(MEMBER_ID, LOGIN_ID, NAME, PASSWORD, ROLE) values (1000, 'M1_loginId','M1_name', '{noop}password', 'ADMIN')
insert into member(MEMBER_ID, LOGIN_ID, NAME, PASSWORD, ROLE) values (2000, 'M2_loginId','M2_name', '{noop}password', 'USER')

insert into Post (post_id, title, content, writer_id) values(1000, 'P1_title', 'P1_content', 'M1_loginId')
