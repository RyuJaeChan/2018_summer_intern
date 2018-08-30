JPA
=====




개요
----
mybatis를 통해 프로젝트 개발을 해본 결과 골치아픈 점이 많았다. 프로젝트 특성 상 여러 테이블을 JOIN한 결과를 가져와야 했는데 테이블 매핑을 위해 일일히 SQL을 작성하기 힘들었다. 사실 처음 사용해보느라 애먹었던 점이 많았던것도 있다. mybatis는 sql 지향적?이라 여기서 생기는 장단점이 있는 것 같다.
우리가 사용하는 데이터베이스는 대부분 관계형 데이터베이스(RDBMS)이다. RDBMS는 유연하고 견고한 데이터 관리 기능을 바탕으로 많은 곳에서 사용되고 있다. RDBMS가 성공한 가장 큰 이유 중 하나는 데이터베이스 통신을 위한 표준 언어인 SQL이 있었기 때문이다. SQL을 통해 데이터 베이스에 접근 및 관리의 용이성을 확보하였다. Java 어플리케이션에서RDBMS 를 사용할 때는 JDBCAPI를 통해 SQL을 실행한다.


> - 객체지향 프로그래밍은 애플리케이션의 복잡성을 해결하기 위한 다양한 방법들을 제공한다.
> - 객체지향 모델링에서 중요한 것은 어떤 클래스가 필요한가가 아니라 어떤 객체들이 어떤 메
시지를 주고 받으며 협력하는가다.
- 객체지향의 핵심은 적절한 책임을 수행하는 역할 간의 유연하고 견고한 협력 관계를 구축하
는 것이다.
- 관계형 데이터베이스는 데이터 중심으로 구조화되어 있고, 집합적인 사고를 요구한다.
- 객체와 관계형 데이터베이스는 지향하는 목적이 다르므로 둘의 기능과 구현 방법도 다르다.
- 객체 구조를 테이블 구조에 저장하는 데는 한계가 있다.

현대의 어플리케이션 대부분은 객체지항 언어로 개발되고 있다. 객체에 영속성을 부여하기 위해 객체의 데이터를 SQL 로 변환하여 RDBMS 에 저장한다. 객체의 영속성 문제를 해결하기 위해 비싼 비용을 치르고 있다.

> - 객체지향 프로그래밍은 애플리케이션의 복잡성을 해결하기 위한 다양한 방법들을 제공한다.
- 객체지향 모델링에서 중요한 것은 어떤 클래스가 필요한가가 아니라 어떤 객체들이 어떤 메시지를 주고 받으며 협력하는가다.
- 객체지향의 핵심은 적절한 책임을 수행하는 역할 간의 유연하고 견고한 협력 관계를 구축하는 것이다.
- 관계형 데이터베이스는 데이터 중심으로 구조화되어 있고, 집합적인 사고를 요구한다.
- 객체와 관계형 데이터베이스는 지향하는 목적이 다르므로 둘의 기능과 구현 방법도 다르다.
- 객체 구조를 테이블 구조에 저장하는 데는 한계가 있다.


왜 객체를 RDBMS 에 직접 저장하거나 가져올 수 없을까?

> - 테이블 모듈 아키텍처는 복잡한 도메인과 지속 가능한 애플리케이션에 적합하지 않다.
- 좋은 소프트웨어는 눈 앞의 문제를 해결할 뿐 아니라, 고객이 장차 요구할 피할 수 없는 변경에 대응하도록 유지보수와 수정이 쉬워야 한다.
- 최종 코드는 사용자가 도메인을 바라보는 관점을 반영해야 한다.
- 이것은 곧 애플리케이션이 도메인 모델을 기반으로 설계되어야 한다는 것을 의미한다.
- 객체지향을 사용하면 사용자들이 이해하고 있는 도메인 구조와 최대한 유사하게 코드를 구조화할 수 있다.
- 애플리케이션 개발에서 객체지향 프로그래밍을 어렵게 만드는 영속성에 대한 RDBMS 매핑 문제를 해결해야 한다.

이렇게 소프트웨어 개발의 복잡성을 해결하기 위해 나온것이 ORM이다. 패러다임 불일치 문제를 개발자 대신 프레임워크가 대신 해준다. 복잡한 매핑은 프레임워크에 맡기고 객체지향과 RDBMS의 역할을 분리하여 각자의 역할에 충실할 수 있다. 개발자는 영속성 문제를 ORM 프레임워크에 맡기고 객체가 메모리 컬렉션에 저장되어 있는 것과 같이 개발하면 된다. 이를 통해 개발자는 도메인 모델과 비지니스 문제에 집중할 수 있게 된다. ORM 프레임워크의 목적은 객체지향 프로그래밍 개발자가 객체지향을 사용하여 도메인에 관련된 문제를 해결할 수 있도록 하는데 있다.



적용
-----

pom.xml에 추가

```xml
<!-- my sql은 추가되있음 -->
	<!-- JPA -->
	<dependency>
		<groupId>org.springframework.data</groupId>
		<artifactId>spring-data-jpa</artifactId>
		<version>1.11.14.RELEASE</version>
	</dependency>
	<!-- hibernate -->
	<dependency>
		<groupId>org.hibernate</groupId>
		<artifactId>hibernate-entitymanager</artifactId>
		<version>5.3.5.Final</version>
	</dependency>
```

Entity 클래스 작성

- 모든 테이블에 해당하는 클래스를 작성해야하는 번거로움이 있다....
- Join하는 것을 잘 만들 수 있따.

```java

```

Repository

- SQL을 일일히 작성하지 않아도 된다는 것이 정말 좋았따.

```java

```

Service

- 이전의 내용들과 크게 달라지는 것은 없을것이다. 다만 JpaRepository에서 제공되는 메서드의 이름은 정해져 있기 때문에 이를 고쳐주어야 할 것이다.

```java

```

Java Config

- 작성된 파일을 WebMvcConfig에 import해야 한다.
- 아마 이것도 DBConfig 안에 포함시켜도 될 것이다. 하지만 지금은 MyBatis까지 있으니 일단 분리시켜놨다.


```java
package com.nts.connect.pjt3.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.nts.connect.pjt3.jpa") //Repository Package
public class JpaConfig {
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setDatabase(Database.MYSQL);

		Properties props = new Properties();
		props.setProperty("hibernate.ejb.naming.strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setJpaVendorAdapter(adapter);
		emfb.setJpaProperties(props);
		emfb.setDataSource(dataSource);
		emfb.setPersistenceUnitName("notDefault");
		emfb.setPackagesToScan("com.nts.connect.pjt3.jpa"); //Entity Package
		return emfb;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}

```


```
JPA container EntityManagerFactory for persistence unit 'default'
```






















[JPA 개발 참고](https://medium.com/@geminikim/%EA%B0%9C%EC%9D%B8%EC%B7%A8%ED%96%A5-jpa-%EC%82%AC%EC%9A%A9%EA%B8%B0-2%ED%8E%B8-entity-with-getter-setter-and-test-a0305af69090)
