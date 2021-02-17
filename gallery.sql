--drop
drop table gallery;
drop SEQUENCE seq_gallery_no;

commit;
rollback;

--생성
create table gallery(
    no number,
    user_no number,
    content VARCHAR2(1000),
    filePath VARCHAR2(500),
    orgName VARCHAR2(200),
    saveName VARCHAR2(500),
    fileSize number,
    PRIMARY KEY (no),
    CONSTRAINT gallery_fk FOREIGN KEY (user_no)
    REFERENCES users(no)
);

create SEQUENCE seq_gallery_no
INCREMENT by 1
start with 1
nocache;

--출력
SELECT  g.no,
        g.user_no userNo,
        u.name userName,
        content,
        filePath,
        orgName,
        saveName,
        fileSize
FROM gallery g, users u
where g.user_no = u.no
order by g.no asc;

--입력
insert into gallery
values(seq_gallery_no.nextval, 1, '내용', '경로', '원래이름', '저장이름', 10);

--사진 정보
SELECT  g.no,
        g.user_no userNo,
        u.name userName,
        content,
        saveName
FROM gallery g, users u
where g.user_no = u.no
and g.no=3;

--삭제
delete from gallery
where no=2;