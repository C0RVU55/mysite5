--계층형 게시판 (게시글(read)에 댓글을 달면 list 제목아래 댓글이 달림)

--drop
drop table rboard;
drop SEQUENCE seq_rboard_no;

commit;
rollback;

--생성
create table rboard(
    no number,
    user_no number not null,
    title VARCHAR2(500),
    content VARCHAR2(4000),
    hit number default 0,
    reg_date date,
    group_no number,
    order_no number DEFAULT 1,
    depth number DEFAULT 0,
    PRIMARY KEY (no),
    CONSTRAINT board_fk2 FOREIGN KEY (user_no)
    REFERENCES users(no)
);

create SEQUENCE seq_rboard_no
INCREMENT by 1
start with 1
nocache;

--출력
SELECT  no,
        user_no,
        title,
        content,
        hit,
        TO_CHAR(reg_date, 'YYYY-MM-DD') regDate,
        group_no,
        order_no,
        depth
FROM rboard;

--입력
--(no, user_no, title, content, hit, reg_date, group_no, order_no, depth)
insert into rboard
values(seq_rboard_no.nextval, 1, '제목테스트', 'xxx', 0, sysdate, seq_rboard_no.nextval, 1, 0);

--글정보
SELECT  no,
        user_no,
        title,
        content,
        hit,
        TO_CHAR(reg_date, 'YYYY-MM-DD') regDate,
        group_no,
        order_no,
        depth
FROM rboard
where no=1;

--조회수
update rboard
set hit = hit + 1
where no =1;

