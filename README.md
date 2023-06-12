# Android_Studio_Sports_private_education
安卓Android初中生体育私教平台app可导入Studio毕业源码案例设计
## 开发环境: Myclipse/Eclipse/Idea都可以(服务器端) + Eclipse(手机客户端) + mysql数据库
## 系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！
## 服务器和客户端数据通信格式： XML格式(用于传输查询的记录集)和json格式(用于传输单个的对象信息)
### （1）登录、注册。
体育私教、学生家长Android端。
管理员web端（通过超级管理员登录成功后，建立注册、注销普通管理员）
### （2）私教用户角色。
信息发布：自身情况介绍，收费情况，同时公布自身姓名、性别、年龄、手机号、城市、现状态（空闲、在忙）等相关信息
查询初中生对体育私教的需求信息：通过年龄、性别、城市、现状态筛选。
体育私教基本信息管理：自身姓名、性别、年龄、手机号、城市、现状态（空闲、在忙）等相关信息
### （3）学生家长用户角色。
信息发布：学生情况介绍，需求介绍。同时公布学生家长姓名、手机号、城市、学生年龄、学生性别、学生学校、现状态（空闲、在忙）等相关信息
查询体育私教信息：通过年龄、性别、城市、收费情况、现状态筛选。
初中生及其家长个人信息的管理：家长姓名、手机号、城市、学生年龄、学生性别、学生学校、现状态（空闲、在忙）等相关信息
### （4）管理员。
体育私教和初中生家长的身份审核，用户注册即可以审核，通过则用户可以登录，不通过则不可以登录。
信息发布即可审核，不通过不可发布，通过成功才有显示
## 实体ER属性：
私教: 用户名,登录密码,姓名,性别,年龄范围,年龄,手机号,城市,现状态,收费价格区间,价格(元/小时),教练照片,教练简介,审核状态,注册时间

学生家长: 用户名,登录密码,家长姓名,手机号,城市,学生性别,年龄范围,学生年龄,学生学校,现状态,学生照片,学生介绍,审核状态,注册时间

城市: 城市编号,城市名称

现状态: 状态id,状态名称

价格范围: 价格范围id,起始价,结束价,显示信息

年龄范围: 年龄范围id,开始年龄,结束年龄,显示信息

普通管理员: 管理员用户名,登录密码,姓名,性别,出生日期,联系电话