# SQLitePractice

Android开发中数据库常见案例：
1. 使用时间和日期函数，增，查时间字段。

2. 利用ContentProvider,CursorLoader,SQLite实现数据库的观察者模式。

3. RxJava,SQLBrite实现数观察者模式。

4. 拷贝外部db文件到数据库，这里实现城市选择的功能。

## SQLite ##

**1. 简介：**

![](https://www.sqlite.org/images/sqlite370_banner.gif)



SQLite 是一个软件库，实现了自给自足的、无服务器的、零配置的、事务性的 SQL 数据库引擎。

**2. 字段类型：**

 1. NULL： null值
 
 2. INTEGER： 一个带有符号整数，根据值的大小存储在1-4字节中
 
 3. REAL：一个浮点数，储存为8字节的IEEE浮点数
 
 4. TEXT：一个文字字符串，根据数据库编码存储（UTF-8/UTF-16BE/UTF-16LE）
 
 5. BLOB：一个 blob 数据，完全根据它的输入存储。
 
更多其他数据库（例如MYSQL）中字段类型对应SQLite的类型，请参考[SQlite类型官方介绍](https://www.sqlite.org/datatype3.html)，看不懂英文的小伙伴，请看中文翻译的[SQLite中数据类型](http://www.runoob.com/sqlite/sqlite-data-types.html)。

**3. SQLite的增删查改操作及事务：**
 
  SQLite的增删查改操作比较常见，事务使用也比较常见，这里不多做介绍。更多具体详情，[SQLite教程的增删查改]([http://www.runoob.com/sqlite/sqlite-tutorial.html).

**4. SQLite特殊数据存储(重点)：**

1. **存储日期和时间数据类型：**

   SQLite没有专门提供存储日期和时间存储类型，通常可以TEXT , REAL和INTEGER类型来替代的方式存储。
   - TEXT对应的数据：`"YYYY-MM-DD HH:MM:SS.SSS"`格式的数据
   
   - REAL对应的数据： Julian日期格式存储，即从公元前 4714 年 11 月 24 日格林尼治时间的正午开始算起的天数。
   
   - INTEGER对应的数据：Unix时间形式的数据 , 即从 1970-01-01 00:00:00 UTC 算起的秒数。
  
   可以以任何上述格式来存储日期和时间，并且可以使用内置的日期和时间函数来自由转换不同格式。
 
2. **布尔数据类型：**

    采用true用1替代和false用0替代的方式存储。
