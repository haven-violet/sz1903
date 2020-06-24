show tables;
create table teacher(
    name string,
    age int,
    height double,
    married boolean,
    children int
);

insert into table teacher
values
 ("zhangsan" ,  23,   175.0  , false ,  0),
 ("lisi",24  ,    180.0 ,  false,   0),
 ("wangwu",25  ,    175.0 ,  false,   0),
 ("zhaoliu",26  ,    195.0 ,  true ,   1),
 ("zhouqi",27  ,    165.0 ,  true ,   2),
 ("weiba",28  ,    185.0 ,  true ,   3);

set hive.exec.mode.local.auto=true;

select * from teacher;

create table test(
    line string
);

insert into table test
values
("hello you"),
("hello me"),
("hello she");

select *
from stu;

select current_database();

create table sale_log(
    time string,
    profit double
);

insert into table sale_log
values
("2020-02-02", 89.0),
("2020-02-03", 819.0),
("2020-02-04", 100.0);

