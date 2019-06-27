package com.linkmoretech.swagger.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: alec
 * Description:
 * @date: 16:55 2019-06-27
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig  {

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.desc}")
    private String desc;

    @Value("${swagger.email}")
    private String email;

    @Value("${swagger.author}")
    private String author;

    @Value("${swagger.version}")
    private String version;

    private String url = "http://linkmoretech.cn";

    private String baseController = "com.linkmoretech.**.controller";

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(baseController))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(desc)
                .contact(new Contact(author, url, email))
                .version(version)
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, true, true, 60000L);
    }
}
