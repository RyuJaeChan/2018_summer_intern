package com.nts.connect.pjt3.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {"com.nts.connect.pjt3"}, excludeFilters = @Filter({Controller.class}))
@MapperScan("com.nts.connect.pjt3.mapper")
@Import({DBConfig.class, JpaConfig.class})
public class ApplicationConfig {

}
