Spring 4 Mybatis 연동
===


### 1. dependency 등록
---------------

- 어떤 버전을 사용해야 되는지는 아직 잘 모르겠다. 보통 spring4에는 mybatis3을 사용하는 거 같다.

```xml
	<!-- MyBatis -->
	<dependency>
	    <groupId>org.mybatis</groupId>
	    <artifactId>mybatis</artifactId>
	    <version>3.2.2</version>
	</dependency>
	<dependency>
	    <groupId>org.mybatis</groupId>
	    <artifactId>mybatis-spring</artifactId>
	    <version>1.2.0</version>
	</dependency>

```

### 2. java config 작성
----------------

#### MyBatisContext 작성
- 여기서 DTO를 등록?하여 ```query.xml```에서 resultType으로 DTO 객체를 사용할 수 있게 된다.
- configuration.xml을 등록하지 않아도 되는거 같은뎁..


```java
package com.nts.connect.pjt3.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig {
	@Bean
	 public SqlSessionFactoryBean sqlSessionFactoryBean(
	            DataSource dataSource, ApplicationContext applicationContext) throws IOException {
	        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
	        sqlSessionFactoryBean.setDataSource(dataSource);
	        // 마이바티스 설정파일 위치 설정
	        //factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/configuration.xml"));
	        // com.lge.apip.mgmt.ocpo.*.model 패키지 이하의 model 클래스 이름을 짧은 별칭으로 등록
	        sqlSessionFactoryBean.setTypeAliasesPackage("com.nts.connect.pjt3.dto");
	        // 모든 XML을 매퍼로 등록
	        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:database/*.xml"));
	        return sqlSessionFactoryBean;
	    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
```

#### dbconfig 설정

- 기존과 동일

### #application config 설정
- import에 mybatis 설정 추가

```java
package com.nts.connect.pjt3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {"com.nts.connect.pjt3"}, excludeFilters = @Filter({Controller.class}))
@Import({DBConfig.class, MyBatisContext.class})
public class ApplicationConfig {

}
```

### 3. query 작성
-----------

- xml 파일을 생성한다. 보통 mapper.xml이라고 하더라.
- 이 쿼리를 적는 xml 파일을 여러개로 분리해서 관리하는지 아니면 하나로 관리해도 되는지 모르겠따.
- 아마도 여러 파일을 만들어서 해야할 것이다. mapper의 속성으로 namepace가 있는데 이걸로 해당하는 Mapper 인터페이스와 연동이 된다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nts.connect.pjt3.mapper.CategoryMapper">
    <select id="foo" resultType="Category">
        SELECT category.id, category.name, COUNT(category.id) as count
      	FROM category LEFT JOIN product
		ON category.id = product.category_id
		GROUP BY category_id
    </select>
</mapper>
```

### 4. mapper interface 작성

- 이 interface는 기존 dao를 대체한다. interface이기 때문에 dao에 비해 구현이 쉽다.
- 이 interface를 위에서 작성한 xml파일과 일치시켜야한다. : namespace = 패키지명, 쿼리의 id = 메소드 이름
- \#{} 형식으로 인자를 설정할 수 있다.
- 인자 설정할 때 DTO를 주면 자동으로 매핑되는거 같은데 확인해봐야겠따.
- ```@Mapper```를 안붙이더라도 인터페이스로 정의되어 있으면 scan을 수행할 때 알아서 mapper로 로드된다. : [링크 중간에 scan 설명 중...](http://www.mybatis.org/spring/mappers.html#register)
- Component scan은 mapper를 등록하지 못한다.

```java
package com.nts.connect.pjt3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.nts.connect.pjt3.dto.Category;

public interface CategoryMapper {
	List<Category> getCategories();
}
```

### 5. Service에서 사용

- Mapper를 가져와서 그냥 쓰면 됨
- 이 부분부터 컨트롤러까지는 크게 달라지는게 없다.

```java
package com.nts.connect.pjt3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.connect.pjt3.dao.CategoryDao;
import com.nts.connect.pjt3.dto.Category;
import com.nts.connect.pjt3.mapper.CategoryMapper;

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	public List<Category> getCategories() {
		//return dao.selectCategoriesAll();
		return categoryMapper.getCategories();
	}
}
```


Data Binding
====
- Data 타입에 어노테이션으로 형식 지정이 가능하다. : ```@DateTimeFormat(patern = "yyyy/mm/dd")
- 숫자 역시 ```@NumberFormat(pattern = "#,###")``` 식으로 매핑이 됨


