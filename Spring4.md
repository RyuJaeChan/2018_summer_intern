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

```

설치된 경로 ```C:\Users\USER\.m2\repository\org\projectlombok\lombok\1.18.2```에 가서 lombok.jar 파일을 실행시켜 설치한다.

![](https://i.imgur.com/2YrPruJ.png)