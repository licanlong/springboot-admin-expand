# springboot-admin-expand
拓展springboot-admin 保存java监控信息到数据库



添加依赖pom.xml 即可生效：

```xml
<dependency>
    <groupId>com.ylzinfo</groupId>
    <artifactId>springboot-admin-expand</artifactId>
    <version>1.0</version>
</dependency>
```

 application.yml

```yaml
spring:
  boot:
    admin:
      expand:
        #默认间隔十秒记录一次
        record-interval: 10s
        #默认开启记录
        enable-record: true
```

保存数据的表结构（mysql）

```mysql
CREATE TABLE `monitor_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `app_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `baseUnit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位',
  `datatime` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1349 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

需要自己导入配置数据源
目前记录的指标可在com.ylzinfo.admin.enumerate.MetricsEnum中查看