package com.linkmoretech.common.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 统一配置swagger
 * @Author: alec
 * @Description:
 * @date: 下午5:09 2019/4/10
 */
@Configuration
@EnableSwagger2
@Data
@Slf4j
public class SwaggerConfiguration {

    @Autowired
    SwaggerConfig swaggerConfig;
	
    @Bean
    public Docket createRestApi() {
    	log.info("package = {} contact = {}", swaggerConfig.getBasePackage(), swaggerConfig.getContact());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerConfig.getBasePackage()))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swaggerConfig.getTitle())
                .description(swaggerConfig.getDescription())
                .contact(new Contact(swaggerConfig.getContact(), null , null))
                .version(swaggerConfig.getVersion())
                .build();
    }


}
