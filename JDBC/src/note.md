## JDBC

### JDBC 访问数据库步骤
1. 加载一个Driver驱动
2. 创建数据库连接
3. 创建Sql命令发送器Statement
4. 通过Statement发送Sql命令并得到结果
5. 处理结果
4. 关闭数据库资源
    - ResultSet
    - Statement
    - Connection
## jdbc  常用接口

### 1. Statement 接口
用于执行静态SQl 语句并返回它所生成的结果的对象

#### 三种 Statement 类
 1. Statement ： 用于发送简单的Sql 语句
 2. PreparedStatement 可以发送包含参数的语句，比上一个效率更好，而且可以防止SQL注入，
 3. CallableStatement ： 继承则PreparedStatement ，用于调用存储过程
 - 优先使用PreparedStatement 可以避免Sql 注入风险

#### 常用的 Statement
 - execute 运行语句，返回是否有结果集
 - eecuteQuery 运行select 语句，返回ResultSet 结果集
 - executeUpdate 运行inset/update/delete操作，返回更新的行数

### 事务管理
- 在JDBC 中，事务操作 是自动提交的
- 系统自动 调用 commit  ，否则调用rollback 回滚

- 可以改成手动提交，调用 setAutoCommit（False）来禁止自动提交


### 时间类型
#### 1. java.util.Date
 - 子类 ： java.sql.Date 表示年月日
 - 子类 ： java.sql.Time 表示时分秒
 - 子类 ： java.sql.TimeStmp  表示年月日时分秒

#### 日期比较处理
 - 插入随机日期
 - 取出指定日期范围的记录



## 文本存储方式
### CLOB
 - 用来存储大量的文本数据
 - 大字段有些特殊，不同的数据库处理方式不一样，大字段的操作常常是以流的方式来处理的，
#### mysql 中的相关类型
 - TINYTEXT
 - TEXT[(M)]
 - MEDIUMTEXT
 - LONGTEXT
 - 上面四种依次变大容量
### BLOB
 - 二进制大对象的使用
 - 大字段数据通常是以流的形式，，而非一般的字段，一次即可读出数据
#### Mysql 中相关类型
  - TINYTEXT
  - TEXT[(M)]
  - MEDIUMTEXT
  - LONGTEXT
  - 上面四种依次变大容量


