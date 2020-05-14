# [SQL教程](https://www.liaoxuefeng.com/wiki/1177760294764384)

==增改删查==  

SQL就是访问和处理==关系数据库==的计算机标准语言。
SQL是==结构化查询语言(Structured Query Language)==的缩写，用来访问和操作数据库系统。
也就是说，无论用什么编程语言（Java、Python、C++……）编写程序，只要涉及到操作关系数据库，比如，一个电商网站需要把用户和商品信息存入数据库，或者一个手机游戏需要把用户的道具、通关信息存入数据库，都必须通过SQL来完成。





# SQL快捷键

SQL Workbench

1、执行整篇sql脚本：【Ctrl】+【Shift】+【Enter】

2、执行当前行：【Ctrl】+【Enter】

3、注释/取消注释：【Ctrl】+【/】

4、格式化sql语句（美化sql语句）：【Ctrl】+【B】 

# 1.概述

SQL是结构化查询语言的缩写，用来访问和操作数据库系统。SQL语句既可以查询数据库中的数据，也可以添加、更新和删除数据库中的数据，还可以对数据库进行管理和维护操作。

### 数据类型

对于一个关系表，除了定义每一列的名称外，还需要定义每一列的数据类型。关系数据库支持的标准数据类型包括数值、字符串、时间等：

| 名称         | 类型           | 说明                                                         |
| :----------- | :------------- | :----------------------------------------------------------- |
| INT          | 整型           | 4字节整数类型，范围约+/-21亿                                 |
| BIGINT       | 长整型         | 8字节整数类型，范围约+/-922亿亿                              |
| REAL         | 浮点型         | 4字节浮点数，范围约+/-1038                                   |
| DOUBLE       | 浮点型         | 8字节浮点数，范围约+/-10308                                  |
| DECIMAL(M,N) | 高精度小数     | 由用户指定精度的小数，例如，DECIMAL(20,10)表示一共20位，其中小数10位，通常用于财务计算 |
| CHAR(N)      | 定长字符串     | 存储指定长度的字符串，例如，CHAR(100)总是存储100个字符的字符串 |
| VARCHAR(N)   | 变长字符串     | 存储可变长度的字符串，例如，VARCHAR(100)可以存储0~100个字符的字符串 |
| BOOLEAN      | 布尔类型       | 存储True或者False                                            |
| DATE         | 日期类型       | 存储日期，例如，2018-06-22                                   |
| TIME         | 时间类型       | 存储时间，例如，12:20:59                                     |
| DATETIME     | 日期和时间类型 | 存储日期+时间，例如，2018-06-22 12:20:59                     |

上面的表中列举了最常用的数据类型。很多数据类型还有别名，例如，`REAL`又可以写成`FLOAT(24)`。还有一些不常用的数据类型，例如，`TINYINT`（范围在0~255）。各数据库厂商还会支持特定的数据类型，例如`JSON`。

选择数据类型的时候，要根据业务规则选择合适的类型。通常来说，`BIGINT`能满足整数存储的需求，`VARCHAR(N)`能满足字符串存储的需求，这两种类型是使用最广泛的。

### SQL操作数据库的能力

- **DDL：Data Definition Language**

  DDL允许用户定义数据，也就是创建表、删除表、修改表结构这些操作。通常，DDL由数据库管理员执行。

- **DML：Data Manipulation Language**

  DML为用户提供添加、删除、更新数据的能力，这些是应用程序对数据库的日常操作。

- **DQL：Data Query Language**

  DQL允许用户查询数据，这也是通常最频繁的数据库日常操作。

### 语法特点

SQL语言关键字不区分大小写！！！
但是，针对不同的数据库，对于表名和列名，有的数据库区分大小写，有的数据库不区分大小写。同一个数据库，有的在Linux上区分大小写，有的在Windows上不区分大小写。

教程约定：SQL关键字总是大写，以示突出，表名和列名均使用小写。

# 2. 安装MySQL

MySQL本身实际上只是一个SQL接口，它的内部还包含了多种数据引擎，常用的包括：

- InnoDB：由Innobase Oy公司开发的一款支持事务的数据库引擎，2006年被Oracle收购；
- MyISAM：MySQL早期集成的默认数据库引擎，不支持事务。

MySQL接口和数据库引擎的关系就好比某某浏览器和浏览器引擎（IE引擎或Webkit引擎）的关系。
对用户而言，切换浏览器引擎不影响浏览器界面，切换MySQL引擎不影响自己写的应用程序使用MySQL的接口。



[安装教程](https://zhuanlan.zhihu.com/p/37152572)



### 运行MySQL

MySQL安装后会自动在后台运行。为了验证MySQL安装是否正确，我们需要通过`mysql`这个命令行程序来连接MySQL服务器。

在命令提示符下输入`mysql -u root -p`，然后输入口令，如果一切正确，就会连接到MySQL服务器，同时提示符变为`mysql>`。

输入`exit`退出MySQL命令行。注意，MySQL服务器仍在后台运行。

# 3. 关系模型

## 3.1 概述

表的每一行称为==记录（Record）==，记录是一个逻辑意义上的==数据==。

表的每一列称为==字段（Column）==，同一个表的每一行记录都拥有相同的若干字段。

字段定义了数据类型（整型、浮点型、字符串、日期等），以及是否允许为`NULL`。
注意`NULL`表示字段数据不存在。
一个整型字段如果为`NULL`不表示它的值为`0`，同样的，一个字符串型字段为`NULL`也不表示它的值为空串`''`。

> 通常情况下，字段应该避免允许为NULL。
> 不允许为NULL可以简化查询条件，加快查询速度，也利于应用程序读取数据后无需判断是否为NULL。

### 表关系

关系数据库的表和表之间需要建立“一对多”，“多对一”和“一对一”的关系，这样才能够按照应用程序的逻辑来组织和存储数据。

- 一对多：一张班级表，对应多张学生表
- 多对一：多张学生表，对应一张班级表
- 一对一：一张班级表，对应一张教师表

在关系数据库中，关系是通过<font color=red>主键</font>和<font color=red>外键</font>来维护的。我们在后面会分别深入讲解。

## 3.2 主键

对于关系表，有个很重要的约束，就是任意两条记录不能重复。
不能重复不是指两条记录不完全相同，而是指能够通过某个字段唯一区分出不同的记录，这个字段被称为<font color=red>主键</font>。

选取主键的一个基本原则是：不使用任何业务相关的字段作为主键。
身份证号、手机号、邮箱地址这些看上去可以唯一的字段，均*不可*用作主键。
作为主键最好是完全业务无关的字段，我们一般把这个字段命名为`id`。

常见的可作为`id`字段的类型有：

1. 自增整数类型：数据库会在插入数据时自动为每一条记录分配一个自增整数，这样我们就完全不用担心主键重复，也不用自己预先生成主键；
2. 全局唯一GUID类型：使用一种全局唯一的字符串作为主键，类似`8f55d96b-8acc-4636-8cb8-76bf8abc2f57`。GUID算法通过网卡MAC地址、时间戳和随机数保证任意计算机在任意时间生成的字符串都是不同的，大部分编程语言都内置了GUID算法，可以自己预算出主键。
   也称：**通用唯一识别码**（英语：***U***niversally **U**nique **Id**entifier，缩写：***UUID***）

对于大部分应用来说，通常自增类型的主键就能满足需求。

### 联合主键

关系数据库实际上还允许通过多个字段唯一标识记录，即两个或更多的字段都设置为主键，这种主键被称为联合主键。

对于联合主键，允许一列有重复，只要不是所有主键列都重复即可：

| id_num | id_type | other columns... |
| :----- | :------ | :--------------- |
| 1      | A       | ...              |
| 2      | A       | ...              |
| 2      | B       | ...              |

如果我们把上述表的`id_num`和`id_type`这两列作为联合主键，那么上面的3条记录都是允许的，因为没有两列主键组合起来是相同的。

没有必要的情况下，我们尽量不使用联合主键，因为它给关系表带来了复杂度的上升。

### 小结

主键是关系表中记录的唯一标识。主键的选取非常重要：主键不要带有业务含义，而应该使用BIGINT自增或者GUID类型。主键也不应该允许`NULL`。

可以使用多个列作为联合主键，但联合主键并不常用。

## 3.3 外键

### 定义

`students`表：

| id   | name | other columns... |
| :--- | :--- | :--------------- |
| 1    | 小明 | ...              |
| 2    | 小红 | ...              |

`classes`表：

| id   | name | other columns... |
| :--- | :--- | :--------------- |
| 1    | 一班 | ...              |
| 2    | 二班 | ...              |

student表 与 classes表 的关系为：**一对多**

为了表达这种一对多的关系，
需要在`students`表中加入一列`class_id`，让它的值与`classes`表的某条记录相对应：

| id   | class_id | name | other columns... |
| :--- | :------- | :--- | :--------------- |
| 1    | 1        | 小明 | ...              |
| 2    | 1        | 小红 | ...              |
| 5    | 2        | 小白 | ...              |

在`students`表中，通过`class_id`的字段，可以把数据与另一张表关联起来，这种列称为`外键`。

### 实现

- 定义外键

外键并不是通过列名实现的，而是通过定义外键约束实现的：

```mysql
ALTER TABLE students
ADD CONSTRAINT fk_class_id  //外键约束的名称（可以任意）
FOREIGN KEY (class_id)	//指定外键名称
REFERENCES classes (id);	//指定外键将关联到 哪个表 的 哪一列
```

其中，外键约束的名称`fk_class_id`可以任意，`FOREIGN KEY (class_id)`指定了`class_id`作为外键，`REFERENCES classes (id)`指定了这个外键将关联到`classes`表的`id`列（即`classes`表的主键）。

通过定义外键约束，关系数据库可以==保证无法插入无效的数据==。即如果`classes`表不存在`id=99`的记录，`students`表就无法插入`class_id=99`的记录。

由于外键约束会降低数据库的性能，大部分互联网应用程序为了追求速度，并不设置外键约束，
而是仅靠应用程序自身来保证逻辑的正确性。
这种情况下，`class_id`仅仅是一个普通的列，只是它起到了外键的作用而已。

- 删除外键（删除外键约束，而不是删除列）

要删除一个外键约束，也是通过`ALTER TABLE`实现的：

```mysql
ALTER TABLE students
DROP FOREIGN KEY fk_class_id;
```

注意：删除外键约束并没有删除外键这一列。删除列是通过`DROP COLUMN ...`实现的。

### 多对多

通过一个表的外键关联到另一个表，我们可以定义出一对多关系。有些时候，还需要定义“多对多”关系。例
如，一个老师可以对应多个班级，一个班级也可以对应多个老师，因此，班级表和老师表存在多对多关系。

多对多关系实际上是通过两个一对多关系实现的，即通过一个==中间表==，关联两个一对多关系，就形成了多对多关系：

`teachers`表：

| id   | name   |
| :--- | :----- |
| 1    | 张老师 |
| 2    | 王老师 |
| 3    | 李老师 |
| 4    | 赵老师 |

`classes`表：

| id   | name |
| :--- | :--- |
| 1    | 一班 |
| 2    | 二班 |

中间表`teacher_class`关联两个一对多关系：

| id   | teacher_id | class_id |
| :--- | :--------- | :------- |
| 1    | 1          | 1        |
| 2    | 1          | 2        |
| 3    | 2          | 1        |
| 4    | 2          | 2        |
| 5    | 3          | 1        |
| 6    | 4          | 2        |

### 一对一

一对一关系是指，一个表的记录对应到另一个表的唯一一个记录。

例如，`students`表的每个学生可以有自己的联系方式，如果把联系方式存入另一个表`contacts`，我们就可以得到一个“一对一”关系：

| id   | student_id | mobile      |
| :--- | :--------- | :---------- |
| 1    | 1          | 135xxxx6300 |
| 2    | 2          | 138xxxx2209 |
| 3    | 5          | 139xxxx8086 |

有细心的童鞋会问，既然是一对一关系，那为啥不给`students`表增加一个`mobile`列，这样就能合二为一了？

如果业务允许，完全可以把两个表合为一个表。但是，有些时候，如果某个学生没有手机号，那么，`contacts`表就不存在对应的记录。实际上，一对一关系准确地说，是`contacts`表一对一对应`students`表。

还有一些应用会把一个大表拆成两个一对一的表，目的是把经常读取和不经常读取的字段分开，以获得更高的性能。例如，把一个大的用户表分拆为用户基本信息表`user_info`和用户详细信息表`user_profiles`，大部分时候，只需要查询`user_info`表，并不需要查询`user_profiles`表，这样就提高了查询速度。

### 小结

- 关系数据库通过外键可以实现一对多、多对多和一对一的关系。
- 外键既可以通过数据库来约束，也可以不设置约束，仅依靠应用程序的逻辑来保证。

## 3.4 索引

### 普通索引

索引是关系数据库中对某一列或多个列的值进行预排序的数据结构。
通过使用索引，可以让数据库系统不必扫描整个表，而是直接定位到符合条件的记录，大大加快了查询速度。

- 索引效率

索引的效率取决于索引列的值是否散列，
即该列的值如果越互不相同，那么索引效率越高。
反过来，如果记录的列存在大量相同的值，例如`gender`列，大约一半的记录值是`M`，另一半是`F`，因此，对该列创建索引就没有意义。

对于主键，关系数据库会自动对其创建主键索引。使用主键索引的效率是最高的，因为主键会保证绝对唯一。

- 索引优缺点

可以对一张表创建多个索引。
索引的优点是提高了查询效率，
缺点是在插入、更新和删除记录时，需要同时修改索引，因此，索引越多，插入、更新和删除记录的速度就越慢。

- 例子：创建索引

`students`表：

| id   | class_id | name | gender | score |
| :--- | :------- | :--- | :----- | :---- |
| 1    | 1        | 小明 | M      | 90    |
| 2    | 1        | 小红 | F      | 95    |
| 3    | 1        | 小军 | M      | 88    |

如果要经常根据`score`列进行查询，就可以对`score`列创建索引：

```mysql
ALTER TABLE students
ADD INDEX idx_score (score);
```

使用`ADD INDEX idx_score (score)`就创建了一个名称为`idx_score`，使用列`score`的索引。
索引名称是任意的，索引如果有==多列==，可以在括号里依次写上，例如：

```mysql
ALTER TABLE students
ADD INDEX idx_name_score (name, score);
```

### 唯一索引

通过创建唯一索引，可以==保证某一列的值具有唯一性==。

- 唯一索引

例如：根据业务要求，某一列具有唯一性约束：
例如，我们假设`students`表的`name`不能重复：

```mysql
ALTER TABLE students
ADD UNIQUE INDEX uni_name (name);
```

通过`UNIQUE`关键字我们就添加了一个唯一索引。

- 唯一约束

也可以只对某一列添加一个唯一约束而不创建唯一索引：

```mysql
ALTER TABLE students
ADD CONSTRAINT uni_name UNIQUE (name);
```

这种情况下，`name`列没有索引，但仍然具有唯一性保证。

无论是否创建索引，对于用户和应用程序来说，使用关系数据库不会有任何区别。
这里的意思是说，当我们在数据库中查询时，
如果有相应的索引可用，数据库系统就会自动使用索引来提高查询效率，
如果没有索引，查询也能正常执行，只是速度会变慢。因此，索引可以在使用数据库的过程中逐步优化。

### 小结

- 通过对数据库表创建索引，可以提高查询速度。
- 通过创建唯一索引，可以保证某一列的值具有唯一性。
- 数据库索引对于用户和应用程序来说都是透明的。



# 4. 查询数据

**数据表**：students表

| 1    | 1    | 小明 | M    | 90   |
| ---- | ---- | ---- | ---- | ---- |
| 2    | 1    | 小红 | F    | 95   |
| 3    | 1    | 小军 | M    | 88   |
| 4    | 1    | 小米 | F    | 73   |
| 5    | 2    | 小白 | F    | 81   |
| 6    | 2    | 小兵 | M    | 55   |
| 7    | 2    | 小林 | M    | 85   |
| 8    | 3    | 小新 | F    | 91   |
| 9    | 3    | 小王 | M    | 89   |
| 10   | 3    | 小丽 | F    | 85   |


## 4.1 基本查询

```mysql
SELECT * FROM students;
SELECT 100+200;
```

`SELECT`可以用作计算，但它并不是SQL的强项。
但是，不带`FROM`子句的`SELECT`语句有一个有用的用途，就是用来判断当前到数据库的==连接是否有效==。
许多检测工具会执行一条`SELECT 1;`来测试数据库连接。

**小结**

- 使用SELECT查询的基本语句`SELECT * FROM <表名>`可以查询一个表的**所有行**和**所有列**的数据。
- SELECT查询的结果是一个二维表。

## 4.2 条件查询

SELECT语句可以通过`WHERE`条件来设定查询条件，查询结果是满足查询条件的记录。
例如，要指定条件“分数在80分或以上的学生”，写成`WHERE`条件就是
`SELECT * FROM students WHERE score >= 80`。

其中，`WHERE`关键字后面的`score >= 80`就是条件。`score`是列名，该列存储了学生的成绩，因此，`score >= 80`就筛选出了指定条件的记录：

- 单个条件

  ```
  SELECT * FROM students WHERE score >= 80;
  ```

- AND 两个条件

  ```
  SELECT * FROM students WHERE score >= 80 AND gender = 'M';
  ```

- OR 两个条件

  ```
  SELECT * FROM students WHERE score >= 80 OR gender = 'M';
  ```

- NOT 条件
  表示“不符合该条件”的记录。例如，“不是2班的学生” ：`NOT class_id = 2`
  上述`NOT`条件`NOT class_id = 2`其实等价于`class_id <> 2`，因此，`NOT`查询不是很常用。

  ```
  SELECT * FROM students WHERE NOT class_id = 2;
  ```

- 多个条件查询
  三个或者更多的条件，就需要用小括号`()`表示如何进行条件运算。

  ```mysql
  SELECT * FROM students WHERE (score < 80 OR score > 90) AND gender = 'M';
  ```

常用的条件表达式

| 使用<>判断不相等 | score <> 80     | name <> 'abc'    |                                                   |
| ---------------- | --------------- | ---------------- | ------------------------------------------------- |
| 使用LIKE判断相似 | name LIKE 'ab%' | name LIKE '%bc%' | %表示任意字符，例如'ab%'将匹配'ab'，'abc'，'abcd' |

**小结**

通过`WHERE`条件查询，可以筛选出符合指定条件的记录，而不是整个表的所有记录。

## 4.3 投影查询

让结果集仅包含指定列。这种操作称为投影查询。

- 从`students`表中返回`id`、`score`和`name`这三列：

  ```
  SELECT id, score, name FROM students;
  ```

- 结果集的列名就可以与原表的列名不同
  将列名`score`==重命名==为`points`

  ```
  SELECT id, score points, name FROM students;
  ```

投影查询结合WHERE条件

```
SELECT id, score points, name FROM students WHERE gender = 'M';
```

**小结**

- 使用`SELECT *`表示查询表的所有列，使用`SELECT 列1, 列2, 列3`则可以仅返回指定列，
  这种操作称为投影。
- `SELECT`语句可以对结果集的列进行重命名。

## 4.4 排序

使用SELECT查询，查询结果集通常是按照主键排序。

- 升序
  加上`ORDER BY`子句，可以设定按照其他条件排序。

  默认的排序规则是`ASC`：“升序”，即从小到大。`ASC`可以省略，即`ORDER BY score ASC`和`ORDER BY score`效果一样。
  成绩从低到高

```
SELECT id, name, gender, score FROM students ORDER BY score;
```

- 倒序
  成绩从高到低

```
SELECT id, name, gender, score FROM students ORDER BY score DESC;
```

- 有相同列情况
  如果`score`列有相同的数据，要进一步排序，可以继续添加列名。
  先按`score`列倒序，如果有相同分数的，再按`gender`列排序：

  ```
  SELECT id, name, gender, score FROM students ORDER BY score DESC, gender;
  
  ```

排序+WHERE子句
如果有`WHERE`子句，那么`ORDER BY`子句要放到`WHERE`子句后面。例如，查询一班的学生成绩，并按照倒序排序：

```mysql
-- 带WHERE条件的ORDER BY:

SELECT id, name, gender, score
FROM students
WHERE class_id = 1
ORDER BY score DESC;
```

## 4.5 分页查询

分页实际上就是从结果集中“截取”出第M~N条记录。
这个查询可以通过==`LIMIT  OFFSET `==子句实现
如：把结果集分页，使用`LIMIT 3 OFFSET 0`

- 对结果集从0号记录开始，每页最多取**3**条。注意SQL记录集的索引从**0**开始。

- 获取第1页的记录 `LIMIT 3 OFFSET 0`
- 获取第2页的记录 `LIMIT 3 OFFSET 3`
- 获取第3页的记录 `LIMIT 3 OFFSET 6`

分页查询的关键在于，
首先要确定每页需要显示的结果数量`pageSize`（这里是3），
然后根据当前页的索引`pageIndex`（从1开始），
确定`LIMIT`和`OFFSET`应该设定的值：

- `LIMIT`总是设定为`pageSize`；
- `OFFSET`计算公式为`pageSize * (pageIndex - 1)`。

OFFSET设置超过结果集长度：
`OFFSET`超过了查询的最大数量并不会报错，而是得到一个空的结果集。

**注意**

- `OFFSET`是可选的，如果只写`LIMIT 15`，那么相当于`LIMIT 15 OFFSET 0`。
- 在MySQL中，`LIMIT 15 OFFSET 30`还可以==简写成`LIMIT 30, 15`==。
- 使用`LIMIT  OFFSET `分页时，随着`N`越来越大，查询效率也会越来越低。

**小结**

- 使用`LIMIT  OFFSET `可以对结果集进行分页，每次查询返回结果集的一部分；
- 分页查询需要先确定每页的数量和当前页数，然后确定`LIMIT`和`OFFSET`的值。

## 4.6 聚合查询

对于统计==总数、平均数==这类计算，SQL提供了专门的==聚合函数==，使用聚合函数进行查询，就是聚合查询，它可以快速获得结果。

SQL提供了如下聚合函数：

| 函数 | 说明                                   |
| :--- | :------------------------------------- |
|COUNT |查询所有列的行数|
| SUM  | 计算某一列的合计值，该列必须为数值类型 |
| AVG  | 计算某一列的平均值，该列必须为数值类型 |
| MAX  | 计算某一列的最大值                     |
| MIN  | 计算某一列的最小值                     |

`MAX()`和`MIN()`函数并不限于数值类型。如果是字符类型，`MAX()`和`MIN()`会返回排序最后和排序最前的字符。

```mysql
SELECT COUNT(*) FROM students;
-- 使用聚合查询并设置结果集的列名为num:
SELECT COUNT(*) num FROM students;
-- 统计出有多少男生
-- 配合WHERE语句
SELECT COUNT(*) boys FROM students WHERE gender = 'M';
-- 计男生的平均成绩
SELECT AVG(score) average FROM students WHERE gender = 'M';
```

**特别注意：**
如果聚合查询的`WHERE`条件没有匹配到任何行，`COUNT()`会返回0，
而`SUM()`、`AVG()`、`MAX()`和`MIN()`会返回`NULL`：

### 计算分页页数

已经设定，每页显示3条记录，通过聚合查询计算总分页数(**向上取整**)

```mysql
 SELECT CEILING(COUNT(*) / 3) FROM students;
```

### 分组（GROUP BY）

- 统计一班学生数量

  ```mysql
  SELECT COUNT(*) num FROM students WHERE class_id = 1;
  ```

但是，要同级2班、3班、...其他所有班的学生人数呢？不能每一个班级写一个语句

- 聚合查询

  ```mysql
  SELECT COUNT(*) num FROM students GROUP BY class_id;
  ```

  上述语句只会得到3个数字，为了看出是哪个班的，加上 class_id

  ```
  SELECT class_id, COUNT(*) num FROM students GROUP BY class_id;
  ```

  ![image-20200325162401675](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/img/image-20200325162401675.png)
聚合查询的列中，只能放入分组的列。
  
- 使用多个列进行分组

  例如，我们想统计各班的男生和女生人数：

  ```mysql
  -- 按class_id, gender分组:
  SELECT class_id, gender, COUNT(*) num FROM students GROUP BY class_id, gender;
  ```

  ![image-20200325162839419](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/img/image-20200325162839419.png)

### 查询练习：

```mysql
-- 查出每个班级的平均分，结果集应当有3条记录:
SELECT class_id, AVG(score) avg_score FROM students GROUP BY class_id;


-- 查出每个班级的平均分，结果集应当有6条记录:
SELECT class_id, gender, AVG(score) avg_sco FROM students GROUP BY class_id, gender;


-- 查出每个班级的平均分，并按照从高到低排序，结果集应当有6条记录,:
SELECT class_id, gender, AVG(score) avg_sco FROM students GROUP BY class_id, gender ORDER BY avg_sco DESC;

-- 配合WHERE语句
SELECT class_id, AVG(score) avg_sco_boys FROM students WHERE gender='M' GROUP BY class_id;
```

![image-20200325164248281](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/image-20200325164248281.png)

![image-20200325164351963](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/image-20200325164351963.png)

![image-20200325164316714](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/image-20200325164316714.png)

![image-20200325164428506](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/image-20200325164428506.png)

## 4.7 多表查询

查询多张表的语法是：`SELECT * FROM <表1> <表2>`。

```mysql
-- FROM students, classes:
SELECT * FROM students, classes;
```

一次查询两个表的数据，查询的结果也是一个二维表，它是`students`表和`classes`表的“乘积”，即`students`表的每一行与`classes`表的每一行都两两拼在一起返回。结果集的列数是`students`表和`classes`表的列数之和，行数是`students`表和`classes`表的行数之积。

这种多表查询又称笛卡尔查询,

- 列名设置别名
- 表设置别名
- 配合WHERE语句

```mysql
SELECT
    s.id sid,
    s.name,
    s.gender,
    s.score,
    c.id cid,
    c.name cname
FROM students s, classes c
WHERE s.gender = 'M' AND c.id = 1;
```

例子：

查询每个班男女生分别有多少人，并且列名班级是从 classes中取出的

```mysql
SELECT
 c.name '班级',
 s.gender '性别',
 COUNT(s.gender) '人数'
FROM students s, classes c
WHERE c.id = s.class_id
GROUP BY s.class_id, s.gender;
```

![image-20200325172116107](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/image-20200325172116107.png)

## 4.8 连接查询

连接查询对多个表进行JOIN运算，简单地说，就是先确定一个==主表==作为结果集，然后，把==其他表的行==有选择性地==“连接”==在主表结果集上。

### 四种链接方式

- INNER JOIN 只返回同时存在于两张表的行数据
- RIGHT OUTER JOIN 返回右表都存在的行。
  如果某一行仅在右表存在，那么结果集就会以`NULL`填充剩下的字段。
- LEFT OUTER JOIN则返回左表都存在的行。
- FULL OUTER JOIN，它会把两张表的所有记录全部选择出来，并且，自动把对方不存在的列填充为NULL



- **内连接——INNER JOIN**

  INNER JOIN只返回同时存在于两张表的行数据

例子：

从students表中列出所有学生
根据classes表中的班级 **name**列，连接到students主表中去
students表中的班级和classes中的name列的对应关系是根据
s.class_id = c.id
来保证的

```mysql
-- 选出所有学生，同时返回班级名称
SELECT s.id, s.name, s.class_id, c.name class_name, s.gender, s.score
FROM students s
INNER JOIN classes c
ON s.class_id = c.id;
```

注意INNER JOIN查询的写法是：

1. 先确定主表，仍然使用`FROM <表1>`的语法；
2. 再确定需要连接的表，使用`INNER JOIN <表2>`的语法；
3. 然后确定连接条件，使用`ON <条件...>`，这里的条件是`s.class_id = c.id`，表示`students`表的`class_id`列与`classes`表的`id`列相同的行需要连接；
4. 可选：加上`WHERE`子句、`ORDER BY`等子句。

使用别名不是必须的，但可以更好地简化查询语句。

### 如何选用

假设查询语句是：

```mysql
SELECT ... FROM tableA ??? JOIN tableB ON tableA.column1 = tableB.column2;
```

![image-20200325211635268](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/image-20200325211635268.png)

### 小结

- JOIN查询需要先确定主表，然后把另一个表的数据“附加”到结果集上；
- INNER JOIN是最常用的一种JOIN查询，
  它的语法是`SELECT ... FROM <表1> INNER JOIN <表2> ON <条件...>`；
- JOIN查询仍然可以使用`WHERE`条件和`ORDER BY`排序。

# 5. 修改数据

关系数据库的基本操作就是==增删改查==

对于增、删、改，对应的SQL语句分别是：

- INSERT：插入新记录；
- UPDATE：更新已有记录；
- DELETE：删除已有记录。

## 5.1 INSERT

`INSERT`语句的基本语法是：

```
INSERT INTO <表名> (字段1, 字段2, ...) VALUES (值1, 值2, ...);
```

例如，我们向`students`表插入一条新记录，先列举出需要插入的字段名称，然后在`VALUES`子句中依次写出对应字段的值：

- 一次添加一条

```mysql
-- 添加一条新记录 
INSERT INTO students (class_id, name, gender, score) VALUES (2, '大牛', 'M', 80);
-- 查询并观察结果:
SELECT * FROM students;
```

- 一次添加多条记录
  只需要在`VALUES`子句中指定多个记录值，每个记录是由`(...)`包含的一组值

```mysql
INSERT INTO students (class_id, name, gender, score) VALUES
  (1, '大宝', 'M', 87),
  (2, '二宝', 'M', 81);

SELECT * FROM students;
```

## 5.2 UPDATE

`UPDATE`语句的基本语法是：

```mysql
UPDATE <表名> SET 字段1=值1, 字段2=值2, ... WHERE ...;
```

`UPDATE`语句会返回更新的行数以及`WHERE`条件匹配的行数。

例如，我们想更新`students`表`id=1`的记录的`name`和`score`这两个字段，
先写出`UPDATE students SET name='大牛', score=66`，
然后在`WHERE`子句中写出需要更新的行的筛选条件`id=1`：

- 指定值，单条更新

```mysql
-- 更新id=1的记录

UPDATE students SET name='大牛', score=66 WHERE id=1;
-- 查询并观察结果:
SELECT * FROM students WHERE id=1;
```

- 指定值，多条更新

```mysql
UPDATE students SET name='小牛', score=77 WHERE id>=5 AND id<=7;
-- 查询并观察结果:
SELECT * FROM students;
```

- 使用表达式更新

```mysql
UPDATE students SET score=score+10 WHERE score<80;
-- 查询并观察结果:
SELECT * FROM students;
```

如果`WHERE`条件没有匹配到任何记录，`UPDATE`语句不会报错，也不会有任何记录被更新。

**特别注意**

`UPDATE`语句可以没有`WHERE`条件，例如：

```
UPDATE students SET score=60;
```

这时，整个表的所有记录都会被更新。所以，在执行`UPDATE`语句时要非常小心，最好先用`SELECT`语句来测试`WHERE`条件是否筛选出了期望的记录集，然后再用`UPDATE`更新。

## 5.3 DELET

`DELETE`语句也会返回删除的行数以及`WHERE`条件匹配的行数

`DELETE`语句的基本语法是：

```
DELETE FROM <表名> WHERE ...;
```

- 删除`students`表中`id=1`的记录

```mysql
DELETE FROM students WHERE id=1;
```

- 删除多条（WHERE条件）

```mysql
DELETE FROM students WHERE id>=5 AND id<=7;
-- 查询并观察结果:
SELECT * FROM students;
```

如果`WHERE`条件没有匹配到任何记录，`DELETE`语句不会报错，也不会有任何记录被删除。

**特别注意**

别小心的是，和`UPDATE`类似，不带`WHERE`条件的`DELETE`语句会删除整个表的数据：

```
DELETE FROM students;
```

# 6.MySQL					

| MySQL Server     | 真正的MySQL服务器                               | 可执行程序是mysqld |
| ---------------- | ----------------------------------------------- | ------------------ |
| MySQL Client程序 | 一个命令行客户端，可以通过MySQL Client登录MySQL | 可执行程序是mysql  |

MySQL Client和MySQL Server的关系如下：

![image-20200325221828721](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/image-20200325221828721.png)

**小结**

命令行程序`mysql`实际上是MySQL客户端，真正的MySQL服务器程序是`mysqld`，在后台运行。

## 6.1 管理MySQL

很多时候，通过SSH远程连接时，只能使用SQL命令，所以，了解并掌握常用的SQL管理操作是必须的。

数据库：

- 列出所有数据库
  `SHOW DATABASES;`

- 创建一个新数据库
  `CREATE DATABASE test;`

- 删除一个数据库

  注意：删除一个数据库将导致该数据库的所有表全部被删除。
  `DROP DATABASE test;`

- 切换为当前数据库
  `USE test;`

表：

- 列出当前数据库的所有表
  `SHOW TABLES;`
- 查看一个表的结构
  `DESC students;`
  ![image-20200325223347063](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/picgo/image-20200325223347063.png)

- 查看创建表的SQL语句
  `SHOW CREATE TABLE students;`
  ![image-20200326103000071](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/picgo/image-20200326103000071.png)

- 创建表 `CREATE TABLE`

  即上述语句

- 删除表 `DROP TABLE students;`

- ==修改表==就比较复杂。

  - 要给`students`表新增一列`birth`

    ```mysql
    ALTER TABLE students ADD COLUMN birth VARCHAR(10) NOT NULL;
    ```

  - 要修改`birth`列，例如把列名改为`birthday`，类型改为`VARCHAR(20)`：

    ```mysql
    ALTER TABLE students CHANGE COLUMN birth birthday VARCHAR(20) NOT NULL;
    ```

  - 要删除列，使用：

    ```mysql
    ALTER TABLE students DROP COLUMN birthday;
    ```

- 退出MySQL

  使用`EXIT`命令退出MySQL：
  注意`EXIT`仅仅断开了客户端和服务器的连接，MySQL服务器仍然继续运行。

## 6.2 实用SQL语句

### 插入或替换

如果我们希望插入一条新记录（INSERT），但如果记录已经存在，就先删除原记录，再插入新记录。此时，可以使用==`REPLACE`==语句，这样就不必先查询，再决定是否先删除再插入：

```
REPLACE INTO students (id, class_id, name, gender, score) VALUES (1, 1, '小明', 'F', 99);
```

若`id=1`的记录不存在，`REPLACE`语句将插入新记录，否则，当前`id=1`的记录将被删除，然后再插入新记录。

### 插入或更新

如果我们希望插入一条新记录（INSERT），但如果记录已经存在，就更新该记录，此时，可以使用`INSERT INTO ... ON DUPLICATE KEY UPDATE ...`语句：

```
INSERT INTO students (id, class_id, name, gender, score) VALUES (1, 1, '小明', 'F', 99) ON DUPLICATE KEY UPDATE name='小明', gender='F', score=99;
```

若`id=1`的记录不存在，`INSERT`语句将插入新记录，否则，当前`id=1`的记录将被更新，更新的字段由`UPDATE`指定。

### 插入或忽略

如果我们希望插入一条新记录（INSERT），但如果记录已经存在，就啥事也不干直接忽略，此时，可以使用`INSERT IGNORE INTO ...`语句：

```
INSERT IGNORE INTO students (id, class_id, name, gender, score) VALUES (1, 1, '小明', 'F', 99);
```

若`id=1`的记录不存在，`INSERT`语句将插入新记录，否则，不执行任何操作。

### 快照

如果想要对一个表进行快照，即复制一份当前表的数据到一个新表，可以结合`CREATE TABLE`和`SELECT`：

```
-- 对class_id=1的记录进行快照，并存储为新表students_of_class1:
CREATE TABLE students_of_class1 SELECT * FROM students WHERE class_id=1;
```

新创建的表结构和`SELECT`使用的表结构完全一致。

### 写入查询结果集

如果查询结果集需要写入到表中，可以结合`INSERT`和`SELECT`，将`SELECT`语句的结果集直接插入到指定表中。

例如，创建一个统计成绩的表`statistics`，记录各班的平均成绩：

```mysql
CREATE TABLE statistics (
    id BIGINT NOT NULL AUTO_INCREMENT,
    class_id BIGINT NOT NULL,
    average DOUBLE NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO statistics (class_id, average) SELECT class_id, AVG(score) FROM students GROUP BY class_id;
```

确保`INSERT`语句的列和`SELECT`语句的列能一一对应，就可以在`statistics`表中直接保存查询的结果：

![image-20200326114449503](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/picgo/image-20200326114449503.png)

### 强制使用指定索引

在查询的时候，数据库系统会自动分析查询语句，并选择一个最合适的索引。但是很多时候，数据库系统的查询优化器并不一定总是能使用最优索引。如果我们知道如何选择索引，可以使用`FORCE INDEX`强制查询使用指定的索引。例如：

```mysql
> SELECT * FROM students FORCE INDEX (idx_class_id) WHERE class_id = 1 ORDER BY id DESC;
```

指定索引的前提是索引`idx_class_id`必须存在。

# 7. 事务

把多条语句作为一个整体进行操作的功能，被称为数据库==*事务*==。
数据库事务可以确保该事务范围内的所有操作都可以全部成功或者全部失败。如果事务失败，那么效果就和没有执行这些SQL一样，不会对数据库数据有任何改动。

数据库事务具有ACID这4个特性：

- A：Atomic，原子性，将所有SQL作为原子工作单元执行，要么全部执行，要么全部不执行；
- C：Consistent，一致性，事务完成后，所有数据的状态都是一致的，即A账户只要减去了100，B账户则必定加上了100；
- I：Isolation，隔离性，如果有多个事务并发执行，每个事务作出的修改必须与其他事务隔离；
- D：Duration，持久性，即事务完成后，对数据库数据的修改被持久化存储。

对于单条SQL语句，数据库系统自动将其作为一个事务执行，这种事务被称为*隐式事务*。

手动把多条SQL语句作为一个事务执行，
使用`BEGIN`开启一个事务，使用`COMMIT`提交一个事务，这种事务被称为*显式事务*，
例如，把上述的转账操作作为一个显式事务：

```mysql
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
COMMIT;
```

多条SQL语句要想作为一个事务执行，就必须使用显式事务。
`COMMIT`是指提交事务，即试图把事务内的所有SQL所做的修改永久保存。如果`COMMIT`语句执行失败了，整个事务也会失败。

## 7.1 ROLLBACK

有些时候，我们希望主动让事务失败，这时，可以用`ROLLBACK`回滚事务，整个事务会失败：

```mysql
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;
ROLLBACK;
```

数据库事务是由数据库系统保证的，我们只需要根据业务逻辑使用它就可以。

提交事务，即确认DML的改动，使用commit;
回滚事务，即要回退掉之前的操作，使用rollback;
另外：如果==不提交也不回滚==，
执行的DML只是在当前会话有效，在其他会话是不生效的（不信你再重新打开一个连接窗口看看），
所以begin一开始，要么以commit结束，要么以rollback结束。

意思是，
在执行较大量的update或delete时，一定要用事务事务
可以在执行出错后，回滚到BEGIN语句还未开始的状态

例子：

```mysql
SELECT * FROM statistics;
BEGIN;
UPDATE statistics SET average=average-60 WHERE id=1;
SELECT * FROM statistics;
UPDATE statistics SET ave=average-60 WHERE id=2;
ROLLBACK;
SELECT * FROM statistics;
```

执行结果：

1.
![image-20200326133321253](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/picgo/image-20200326133321253.png)

2、3、4
![image-20200326133408683](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/picgo/image-20200326133408683.png)

5.SQL语句报错（或者自己进行了误操作想进行回滚）

6、7.执行回滚操作并显示回滚后的结果
![image-20200326133617582](https://raw.githubusercontent.com/tangg9646/my_github_image_bed/master/picgo/image-20200326133617582.png)

### 隔离级别

对于两个并发执行的事务，如果涉及到操作同一条记录的时候，可能会发生问题。
因为并发操作会带来数据的不一致性，包括脏读、不可重复读、幻读等。数据库系统提供了隔离级别来让我们有针对性地选择事务的隔离级别，避免数据不一致的问题。

SQL标准定义了4种隔离级别，分别对应可能出现的数据不一致的情况：

| Isolation Level  | 脏读（Dirty Read） | 不可重复读<br/>（Non Repeatable Read） | 幻读（Phantom Read） |
| :--------------- | :----------------- | :------------------------------------- | :------------------- |
| Read Uncommitted | Yes                | Yes                                    | Yes                  |
| Read Committed   | -                  | Yes                                    | Yes                  |
| Repeatable Read  | -                  | -                                      | Yes                  |
| Serializable     | -                  | -                                      | -                    |

我们会依次介绍4种隔离级别的数据一致性问题。

### 默认隔离级别

如果没有指定隔离级别，数据库就会使用默认的隔离级别。在MySQL中，如果使用InnoDB，默认的隔离级别是Repeatable Read。

## 7.2 Read Uncommitted（脏读）

在Read Uncommitted隔离级别下，一个事务可能读取到另一个事务更新但未提交的数据，这个数据有可能是脏数据。

准备好`students`表的数据，该表仅一行记录：

```
mysql> select * from students;
+----+-------+
| id | name  |
+----+-------+
|  1 | Alice |
+----+-------+
1 row in set (0.00 sec)
```

然后，分别开启两个MySQL客户端连接，按顺序依次执行事务A和事务B：

| 时刻 | 事务A                                             | 事务B                                             |
| :--- | :------------------------------------------------ | :------------------------------------------------ |
| 1    | SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; | SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; |
| 2    | BEGIN;                                            | BEGIN;                                            |
| 3    | UPDATE students SET name = 'Bob' WHERE id = 1;    |                                                   |
| 4    |                                                   | SELECT * FROM students WHERE id = 1;              |
| 5    | ROLLBACK;                                         |                                                   |
| 6    |                                                   | SELECT * FROM students WHERE id = 1;              |
| 7    |                                                   | COMMIT;                                           |

当事务A执行完第3步时，它更新了`id=1`的记录，但并未提交，
而事务B在第4步读取到的数据就是未提交的数据。

随后，事务A在第5步进行了回滚，事务B再次读取`id=1`的记录，发现和上一次读取到的数据不一致，这就是==脏读==。

## 7.3 Read Committed（不可重复读）

在Read Committed隔离级别下，事务==不可重复读==同一条记录，因为很可能读到的结果不一致。

不可重复读（Non Repeatable Read）的问题是指，在一个事务内，多次读同一数据，在这个事务还没有结束时，如果另一个事务恰好修改了这个数据，两次读取的数据可能不一致。

| 时刻 | 事务A                                           | 事务B                                           |
| :--- | :---------------------------------------------- | :---------------------------------------------- |
| 1    | SET TRANSACTION ISOLATION LEVEL READ COMMITTED; | SET TRANSACTION ISOLATION LEVEL READ COMMITTED; |
| 2    | BEGIN;                                          | BEGIN;                                          |
| 3    |                                                 | SELECT * FROM students WHERE id = 1;            |
| 4    | UPDATE students SET name = 'Bob' WHERE id = 1;  |                                                 |
| 5    | COMMIT;                                         |                                                 |
| 6    |                                                 | SELECT * FROM students WHERE id = 1;            |
| 7    |                                                 | COMMIT;                                         |

## 7.4 Repeatable Read（幻读）

在Repeatable Read隔离级别下，一个事务可能会遇到==幻读（Phantom Read）==的问题。

幻读就是没有读到的记录，以为不存在，但其实是可以更新成功的，并且，更新成功后，再次读取，就出现了。

`students`表的数据：

```
mysql> select * from students;
+----+-------+
| id | name  |
+----+-------+
|  1 | Alice |
+----+-------+
1 row in set (0.00 sec)
```

然后，分别开启两个MySQL客户端连接，按顺序依次执行事务A和事务B：

| 时刻 | 事务A                                               | 事务B                                             |
| :--- | :-------------------------------------------------- | :------------------------------------------------ |
| 1    | SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;    | SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;  |
| 2    | BEGIN;                                              | BEGIN;                                            |
| 3    |                                                     | SELECT * FROM students WHERE id = 99;             |
| 4    | INSERT INTO students (id, name) VALUES (99, 'Bob'); |                                                   |
| 5    | COMMIT;                                             |                                                   |
| 6    |                                                     | SELECT * FROM students WHERE id = 99;             |
| 7    |                                                     | UPDATE students SET name = 'Alice' WHERE id = 99; |
| 8    |                                                     | SELECT * FROM students WHERE id = 99;             |
| 9    |                                                     | COMMIT;                                           |

事务B在第3步第一次读取`id=99`的记录时，读到的记录为空，说明不存在`id=99`的记录。
随后，事务A在第4步插入了一条`id=99`的记录并提交。
事务B在第6步再次读取`id=99`的记录时，读到的记录仍然为空，
但是，事务B在第7步试图更新这条不存在的记录时，竟然成功了，
并且，事务B在第8步再次读取`id=99`的记录时，记录出现了。

## 7.5 Serializable

Serializable是最严格的隔离级别。在Serializable隔离级别下，所有事务按照次序依次执行，因此，脏读、不可重复读、幻读都不会出现。

虽然Serializable隔离级别下的事务具有最高的安全性，但是，由于事务是串行执行，所以效率会大大下降，应用程序的性能会急剧降低。如果没有特别重要的情景，一般都不会使用Serializable隔离级别。

# 8. SQL语句汇总

```mysql
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

```

