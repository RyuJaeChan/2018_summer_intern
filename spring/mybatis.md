Spring 4 Mybatis 연동
===


### 1. dependency 등록
---------------

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
	        sqlSessionFactoryBean.setTypeAliasesPackage("com.nts.connect.pjt3.dto");
	        // 모든 XML을 매퍼로 등록
	        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));
	        return sqlSessionFactoryBean;
	    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        	sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);	//dto camelcase 적용되게
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
```

#### dbconfig 설정

- 기존과 동일

#### application config 설정
- import에 mybatis 설정 추가
- @MapperScan 추가


```java
package com.nts.connect.pjt3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {"com.nts.connect.pjt3"})
@MapperScan("com.nts.connect.pjt3.mapper")
@Import({DBConfig.class, MyBatisConfig.class})
public class ApplicationConfig {

}
```

### 3. query 작성
-----------

- xml 파일을 생성한다. 보통 mapper.xml이라고 하더라.
- 아마도 여러 파일을 만들어서 해야할 것이다. mapper의 속성으로 namepace가 있는데 이걸로 해당하는 Mapper 인터페이스와 연동이 된다.
- namepace를 mapper interface의 패키지.클래스명과 일치시킨다(4. mapper interface 작성 참조)
- ```<select>```의 id는 mapper interface에서 사용할 메소드 이름과 일치시킨다(4. mapper interface 작성 참조)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nts.connect.pjt3.mapper.CategoryMapper">
    <select id="getCategories" resultType="Category">
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
- \#{} 형식으로 인자를 설정할 수 있다. (${} 도 있는데 차이가 있다.. 추후 포스팅)
- 인자 설정할 때 DTO를 주면 자동으로 매핑되는거 같은데 확인해봐야겠다.
- ```@Mapper```를 안붙이더라도 인터페이스로 정의되어 있으면 scan을 수행할 때 알아서 mapper로 로드된다. : [링크 중간에 scan 설명 중...](http://www.mybatis.org/spring/mappers.html#register)
- Component scan은 mapper를 등록하지 못한다.

```java
package com.nts.connect.pjt3.mapper;

import java.util.List;

import com.nts.connect.pjt3.dto.Category;

public interface CategoryMapper {
	//인자가 필요한 경우 인자에 @Param 어노테이션 추가
    //ex (Param("productId") Integer productId)
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

##### LocalDatetime이 null이 될 때

- mybatis 버전을 3.4로 올리고
- ```mybatis-typehandlers-jsr310```dependency 추가

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-typehandlers-jsr310 -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-typehandlers-jsr310</artifactId>
    <version>1.0.2</version>
</dependency>
```

Data Binding
====
- Data 타입에 어노테이션으로 형식 지정이 가능하다. : ```@DateTimeFormat(patern = "yyyy/mm/dd")``` 
- 숫자 역시 ```@NumberFormat(pattern = "#,###")``` 식으로 매핑이 됨
- ```jackson-datatype-jsr310 dependency``` 추가
- 이후 LocalDateTime의 어노테이션으로 ```@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")```을 하면 올바른 형식을 포매팅이 됩니다.

```xml
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>${jackson2.version}</version>
</dependency>
```
- LocalDateTime보다 Date가 더 좋은것 같다.
  - 이런 쓸데없는 추가 작업이 필요 없다
  - 포매팅 관련 문제가 많다 : Serializer/Deserializer를 직접 만들어서 쓴다.
- LocalDateTime을 ```yyyy-MM-dd```로 포매팅이 안되서 저런 포맷을 쓰는데는 LocalDate로 바꿨다...
- 사실 DTO에서 지정하는게 별로 좋지는 않은거 같다.



MyBatis Mapper
====

- Mapper의 xml파일을 통해 SQL 관리를 편리하게 할 수 있다.


- ```<sql>```를 통해 반복되는 구문을 묶을 수 있다.

```xml
<sql id="SELECT_FIELD">
	SELECT ri.id
		, ri.product_id
		, ri.reservation_name
		, ri.reservation_email
		, ri.reservation_tel
		, ri.reservation_date
		, ri.create_date
		, ri.modify_date
</sql>

<select id="selectReservationInfoById">
	<include refid="SELECT_FIELD" />
	FROM reservation_info AS ri
	WHERE ri.id = #{id}
</select>
```


Join
====

- MyBatis로 join을 간편히 수행할 수 있다.

#### 1대다 조인

- 기준이 될 DTO의 멤버로 가져올 DTO의 리스트를 추가

```java
ReservationInfo{

    //...

	private List<ReservationPrice> reservationPriceList;

    long totalPrice

    //Getter/Setter
    //....
}
```

- ReservationPrice Mapper에 SQL 작성

```xml
<select id="selectReservationPrices" resultType="ReservationPrice">
		SELECT id
			, reservation_info_id
			, product_price_id
			, count
		FROM reservation_info_price
		WHERE reservation_info_id = #{reservationInfoId};
	</select>

	<select id="selectTotalPrice" resultType="long">
		SELECT SUM(rip.count * pp.price) AS 'totalSum'
		FROM reservation_info_price AS rip
			INNER JOIN product_price AS pp
				ON rip.product_price_id = pp.id
		WHERE rip.reservation_info_id = #{reservationInfoId}
			GROUP BY rip.reservation_info_id
	</select>
```

- ReservatoinInfo Mapper의 xml에 ResultMap으로 join 결과를 가져올수 있도록 매핑해 준다

```xml
<resultMap type="com.nts.connect.pjt3.dto.ReservationInfo" id="ReservationInfoResult">
 		<result property="id" column="id" />
 		<collection
 			property="reservationPriceList"
 			column="id"
 			javaType="java.util.ArrayList"
 			ofType="ReservationPrice"
 			select="com.nts.connect.pjt3.mapper.ReservationPriceMapper.selectReservationPrices">
 		</collection>
	</resultMap>
	
	<resultMap type="com.nts.connect.pjt3.dto.ReservationInfo" id="ReservationInfoWithPrice">
 		<result property="id" column="id" />
 		<collection
 			property="totalPrice"
 			column="id"
 			javaType="long"
 			ofType="long"
 			select="com.nts.connect.pjt3.mapper.ReservationPriceMapper.selectTotalPrice">
 		</collection>
	</resultMap>
```

- sql 작성

```xml
<select id="selectReservationInfoById" resultMap="ReservationInfoResult">
		<include refid="SELECT_FIELD" />
		FROM reservation_info AS ri
		WHERE ri.id = #{id}
	</select>
    
<select id="selectUsedReservationInfoByEmail" resultMap="ReservationInfoWithPrice">
		<include refid="SELECT_FIELD" />
		FROM reservation_info AS ri
		WHERE ri.reservation_email = #{email}
			<![CDATA[
				AND ri.reservation_date < DATE_FORMAT(NOW()-1, "%Y-%c-%e") 
			]]>	
			AND ri.cancel_flag = 0
	</select>
```


