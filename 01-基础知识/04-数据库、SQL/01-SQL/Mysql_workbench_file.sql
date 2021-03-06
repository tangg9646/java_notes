show databases;
show tables;

select * from students;

-- 基本查询
select * from classes;

-- 条件查询
select * from students where score >= 90;
select * from students where score >= 80 and gender='M';
select * from students where score >= 90 or gender='M';
select * from students where not class_id = 2;
-- 多个条件，使用括号指定先后顺序
select * from students where (score<80 or score>90) and gender = 'M';

-- 投影查询
-- 让查询结果只包含指定的列（指定的字段）
select id, score, name from students;
-- 让结果集的列名与原表的列名不同
select id, score points, name from students;
select id, score points, name from students where gender='M';



-- 排序（对查询的结果进行排序）
-- 默认为升序排列（ASC）
select id, name, gender, score from students order by score;
select id, name, gender, score from students order by score desc;
-- 如果有相同列，再指定额外的排序字段
select id, name, gender, score from students order by score desc, gender;

-- 分页查询
-- 从结果集中“截取”出第M~N条记录
-- `LIMIT`总是设定为`pageSize`；
-- `OFFSET`计算公式为`pageSize * (pageIndex - 1)`。
SELECT 
    id, name, gender, score
FROM
    students
ORDER BY score DESC , gender
	limit 3 offset 0;

-- 聚合查询
-- 查询表的总行数
select count(*) from students;
-- 设置结果集的列名
select count(*) num from students;
-- 统计有多少男生
SELECT 
    COUNT(*) num_boys
FROM
    students
WHERE
    gender = 'M';
    
-- 计算男生平均成绩
SELECT 
    AVG(score) avg_sco_boy
FROM
    students
WHERE
    gender = 'M';
    
-- 计算总分页数量
-- 已知，设定每页3行，计算总的页数
-- （向上取整）
SELECT 
    CEILING(COUNT(*) / 3) total_pages
FROM
    students;


-- 分组
-- 查询每个班分别有多少人
select class_id, count(*) class_num from students group by class_id;
-- 统计每个班 男生 和 女生 的数量
select class_id, gender, count(*) num from students group by class_id, gender;
-- 查出每个班级的平均分
select class_id, AVG(score) avg_score from students group by class_id;
-- 查找每个班 男女生分别的平均成绩
SELECT 
    class_id, gender, AVG(score) avg_score
FROM
    students
GROUP BY class_id , gender;
-- 查找每个班男生的平均成绩，并按照降序排列结果集
SELECT 
    class_id, AVG(score) avg_score
FROM
    students
WHERE
    gender = 'M'
GROUP BY class_id
ORDER BY avg_score DESC;

-- 
-- 多表查询 
--
SELECT 
    s.id sid, s.name, s.gender, s.score, c.id cid, c.name cname
FROM
    students s,
    classes c
WHERE
    s.gender = 'M' AND c.id = 1;
    
-- 查询每个班男女生分别有多少人，班级名称从classes中提取
SELECT 
    c.name '班级', s.gender '性别', COUNT(*) '人数'
FROM
    students s,
    classes c
WHERE
    c.id = s.class_id
GROUP BY s.class_id , s.gender
ORDER BY '班级', '人数';



--
-- 连接查询JOIN
-- 

-- 列出所有学生，并返回班级名称（要求students表中的class_id是在classes表中存在的）
SELECT 
    s.name '姓名', c.name '班级'
FROM
    students s
        INNER JOIN
    classes c ON s.class_id = c.id;
    
    
    
-- 
-- 
-- 修改数据（增、改、删）
-- 
-- 

-- 插入数据
-- 一次插入一条
insert into students ( class_id, name, gender, score ) value (2, '大牛', 'M', 86);
select * from students;

-- 一次插入多条
insert into students ( class_id, name, gender, score ) 
value 
	(2, '大牛', 'M', 86), 
    (3, '大大牛', 'M', 96);
select * from students;

-- UODATE
-- 更新指定条件的记录，更新指定的字段
update students set name='小唐9646', score=96 where id=1;
select * from students where id=1;


-- 指定值，更新多条（满足条件的）
select * from students;
update students set name='测试新增', score=88 where id>=12 and id<=14;
select * from students;
update students set score = score+2 where id=1;


-- 删除Delect语句

-- 删除一条
delete from students where id=12;
select * from students;

-- 删除多条，搭配where语句
delete from students where id>=12 and id <= 14;
select * from students;


-- 设定为唯一索引约束
-- 例如，名字不能相同
-- （唯一约束，这种方式不添加索引）
alter table students add constraint unit_name unique(name);
select * from students;
-- 由于设置了name列的唯一约束限制，因此，不能插入重复名字的记录
insert into students (class_id, name, gender, score) value(1, '小红2', 'F', 85);
select * from students;


-- SQL相关命令

show databases;
create database test_database;
use test_database;
show tables;



use test;
show tables;
-- 查看表的描述
desc students;
-- 查看表创建的时候的描述
show create table students;

-- 创建表
CREATE TABLE `students2` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `class_id` bigint NOT NULL,
   `name` varchar(100) NOT NULL,
   `gender` varchar(1) NOT NULL,
   `score` int NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `unit_name` (`name`)
 ) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
 
-- 删除表
drop table students2;

-- 创建表
create table `tangg_students` (
	`id` bigint not null auto_increment,
    `class_id` bigint not null,
    `name` varchar(100) not null,
    `score` int not null,
    primary key (`id`),
    unique key `unit_name`(`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- 在表中插入记录
insert into tangg_students (class_id, name, score) value (2, '1号', 88);
insert into tangg_students (class_id, name, score) value (1, '2号', 92), (3, '4号', 82), (1, '5号', 78);

select * from tangg_students;
desc tangg_students;

-- 修改表
-- 给表新增列
alter table tangg_students add column birth varchar(10) not null;
select * from tangg_students;
-- 修改列名
alter table tangg_students change column birth birthday varchar(20) not null;
select * from tangg_students;
-- 删除列
alter table tangg_students drop column birthday;
select * from tangg_students;

-- 
-- 使用SQL语句
-- 

-- 插入或者替换
-- 如果记录已经存在，就先删除原记录，再插入新记录
select * from students;
replace into students (id, class_id, name, gender, score) value (16, 1, '小小唐', 'F',  89);
select * from students;
-- 若`id=16`的记录不存在，`REPLACE`语句将插入新记录，否则，当前`id=1`的记录将被删除，然后再插入新记录。

-- 插入或者更新
-- 如果记录已经存在，就更新该记录，如果不存在就插入新纪录
select * from students;
insert into students (id, class_id, name, gender, score) value (1, 1, '唐广同学', 'M', 92) on duplicate key update name= '小唐同学', score=93;
select * from students;

-- 快照
-- 复制当前表的数据到一个新表
-- 将一班的所有记录复制到新表
create table students_class_1 select * from students where class_id = 1;
select * from students_class_1;
desc students_class_1;
show create table students_class_1;

-- 将查询结果写入新表
create table quary_res (
id bigint not null auto_increment,
class_id bigint not null,
average double not null,
primary key(id)
);
insert into quary_res (class_id, average) select class_id, avg(score) from students group by class_id;
select class_id '班级', average '平均成绩' from quary_res;



-- 测试回滚
select * from students where id=1;
begin;
update students set name = '小唐改名字3' where id=1;
select * from students where id=1;
rollback;

select * from students where id=1;
