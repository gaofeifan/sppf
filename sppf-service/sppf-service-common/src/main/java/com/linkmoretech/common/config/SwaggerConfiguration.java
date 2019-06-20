package com.linkmoretech.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "swagger")
@Data
@Slf4j
public class SwaggerConfiguration {
	
	private String title = "应用接口";
	private String description = "本文档 完全基于RESTful接口说明";
	private String version = "1.0";
	private String contact = "凌猫停车"; 
	private String basePackage = "";
	
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                //.apis(basePackage("com.linkmoretech.user.controller,com.linkmoretech.order.controller"))
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any()).build();
    }
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(this.title)
                .description(this.description)
                .contact(new Contact(this.contact,null,null))
                .version(this.version)
                .build();
    }
    
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
		log.info("basePackage ======================= {} " + basePackage);
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
            }
        };
    }
    
    /**
     * 处理包路径配置规则,支持多路径扫描匹配以逗号隔开
     * 
     * @param basePackage 扫描包路径
     * @return Function
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return new Function<Class<?>, Boolean>() {
            
            @Override
            public Boolean apply(Class<?> input) {
                for (String strPackage : basePackage.split(",")) {
                    boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                    if (isMatch) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
    
    /**
     * @param input RequestHandler
     * @return Optional
     */
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.getClass());
    }
}
