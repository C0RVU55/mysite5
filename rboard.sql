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
    group_no number DEFAULT 1,
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
SELECT  rb.no,
        user_no,
        title,
        content,
        hit,
        TO_CHAR(reg_date, 'YYYY-MM-DD') regDate,
        group_no,
        order_no,
        depth,
        u.name
FROM rboard rb, users u
where rb.user_no = u.no
order by group_no desc, order_no asc;

--입력
--(no, user_no, title, content, hit, reg_date, group_no, order_no, depth)
insert into rboard
values(seq_rboard_no.nextval, 1, '제목1', 'xxx', 0, sysdate, 1, 1, 0);

insert into rboard
values(seq_rboard_no.nextval, 1, '제목2', 'ccc', 0, sysdate, seq_rboard_no.currval, 1, 0);

insert into rboard
values(seq_rboard_no.nextval, 1, '제목3', 'vvv', 0, sysdate, seq_rboard_no.nextval, 1, 0);

--글정보
SELECT  rb.no,
        user_no,
        title,
        content,
        hit,
        TO_CHAR(reg_date, 'YYYY-MM-DD') regDate,
        group_no,
        order_no,
        depth,
        u.name
FROM rboard rb, users u
where rb.user_no = u.no
and rb.no=1;

--조회수
update rboard
set hit = hit + 1
where no =1;

--순서번호 갱신
update rboard 
set order_no = order_no + 1 
where group_no = 3 
and order_no >= 1;

--들여쓰기 갱신
update rboard 
set depth = depth + 1 
where group_no = 3;