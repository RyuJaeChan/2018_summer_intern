project3
========

그냥 알게된거
------

#### Code Template

- window - perference - code template - types에 추가하기

![](https://i.imgur.com/6eW96Kq.png)

- ```${user}``` 바꾸는법

이클립스 설치 경로의 ```eclipse.ini```에 한 줄 추가
```
-Duser.name=유재찬
```




#### ApplicationConfig

```excludeFilters```를 통해 ```Controller```를 제외해서 ```ComponentScan```을 수행할 수 있다.

```
package com.nts.connect.pjt3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {"com.nts.connect"}, excludeFilters = @Filter({Controller.class}))
@Import({DBConfig.class})
public class ApplicationConfig {

}

```



컨트롤러 에러
-----

```
No converter found for return value of type: class java.util.HashMap
```
위의 에러가 발생했다. 확인해보니 ```service```와 ```controller```까지는 동작이 정상적으로 되는데 ```json```객체를 반환하지 못하는 것이었다.

##### 해결
```pom.xml```을 수정하다가 ```jackson```라이브러리를 삭제한게 원인이었다. ```jackson-databind```가 있어야 map 객체를 반환할 수 있다.
```
<!-- Jackson -->
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-core</artifactId>
	<version>${jackson2.version}</version>
</dependency>
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
	<version>${jackson2.version}</version>
</dependency>
```