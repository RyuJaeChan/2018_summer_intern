Spring4
=====

Interceptor
-----

- interceptor를 통해 뷰의 공통적인 부분을 묶을 수 있다. 이를 통해 사용자 세션, 쿠키를 확인하거나 로그를 남기는 등 여러 작업을 할 수 있다.

```java
interface CookieValidator {
	void checkUser(email);
}
```

컨트롤러에서 이 인터페이스를 상속받아 사용하면 된다.


#### 인터셉터 작성

```java
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		String email = getEmailInCookie(request.getCookies());

		HandlerMethod method = (HandlerMethod)handler;
		if (method.getBean() instanceof ViewController) {
			ViewController vc = (ViewController)method.getBean();
			vc.setUserEmail(email);
		}

		return true;
	}

	private String getEmailInCookie(Cookie[] cookies) {
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if ("email".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}
}
```

```preHandle```메소드를 통해 컨트롤러 처리 이전의 작업을 수행한다. 여기서 이전에 작성한 인터페이스를 수행시킨다.

#### 인터셉터 등록

인터셉터를 config에 등록해야한다.

```java
package com.nts.connect.pjt3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.nts.connect.pjt3.interceptor.AuthorizationInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.nts.connect.pjt3.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/font/**").addResourceLocations("/resources/font/")
			.setCachePeriod(31556926);
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/")
			.setCachePeriod(31556926);
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/")
			.setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/")
			.setCachePeriod(31556926);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("mainpage");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthorizationInterceptor());
	}
}
```


lombok
---

dependency 추가
```xml
	<!-- Lombok -->
	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<version>1.18.2</version>
		<scope>provided</scope>
	</dependency>
```

설치된 경로 ```C:\Users\USER\.m2\repository\org\projectlombok\lombok\1.18.2```에 가서 lombok.jar 파일을 실행시켜 설치한다. STS는 경로를 직접 찾아서 지정해줘야한다고 한다.

![](https://i.imgur.com/2YrPruJ.png)


Data Binding
-----
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