# 住院管理系统
使用的是MySQL数据库，有三个表：＜/br＞ 
mysql> show tables;＜/br＞
+--------------------+  
| Tables_in_dbdesign |  
+--------------------+  
| department         |  
| patient            |  
| room               |  
+--------------------+  
病人表：  
mysql> desc patient;  
+--------------+-------------+------+-----+---------+-------+  
| Field        | Type        | Null | Key | Default | Extra |  
+--------------+-------------+------+-----+---------+-------+  
| login_number | char(15)    | NO   | PRI | NULL    |       |  
| patient_name | char(10)    | NO   |     | NULL    |       |  
| gender       | char(2)     | YES  |     | NULL    |       |  
| age          | int         | NO   |     | NULL    |       |  
| phone        | varchar(20) | NO   |     | NULL    |       |  
| department   | char(5)     | YES  |     | NULL    |       |  
| room_number  | char(8)     | YES  |     | NULL    |       |  
| bed_number   | char(10)    | YES  | UNI | NULL    |       |  
| login_date   | date        | NO   |     | NULL    |       |  
| cost         | int         | NO   |     | NULL    |       |  
+--------------+-------------+------+-----+---------+-------+  
病房表：  
mysql> desc room;  
+-------------+-------------+------+-----+---------+-------+  
| Field       | Type        | Null | Key | Default | Extra |  
+-------------+-------------+------+-----+---------+-------+  
| room_number | char(5)     | NO   | PRI | NULL    |       |  
| department  | char(5)     | NO   |     | NULL    |       |  
| bed_info    | varchar(20) | YES  |     | NULL    |       |  
+-------------+-------------+------+-----+---------+-------+  
科室表：  
mysql> desc department;  
+-----------+-------------+------+-----+---------+-------+  
| Field     | Type        | Null | Key | Default | Extra |  
+-----------+-------------+------+-----+---------+-------+  
| number    | char(5)     | NO   | PRI | NULL    |       |  
| name      | char(20)    | NO   |     | NULL    |       |  
| room_info | varchar(20) | NO   |     | NULL    |       |  
+-----------+-------------+------+-----+---------+-------+  
在Java程序中只允许对病人表进行插入、修改以及删除操作。  
住院表编号（login_number）在表中是主键，由程序通过时间  
自动生成，且不可修改；格式："MM-dd-KKmmss".  
