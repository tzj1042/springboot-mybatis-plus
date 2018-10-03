package com.shuma.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 *
 * @author KingKong
 * @create 2018-08-31 0:25
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private ApplicationArguments applicationArguments;

    @Bean
    public Docket api(){

        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("登录返回的授权token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
        boolean debug = applicationArguments.containsOption("dev");
        if (debug) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .pathMapping("/")
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.shuma.web.controller"))
                    //错误路径不监控
                    //.paths(Predicates.not(PathSelectors.regex("/error.*")))
                    .paths(PathSelectors.regex("/.*"))
                    .build().globalOperationParameters(pars);
        } else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfoOnline())
                    .select()
                    //如果是线上环境，添加路径过滤，设置为全部都不符合
                    .paths(PathSelectors.none())
                    .build();
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口文档")
                .description("这是swaggerUI生成的：RESTful风格的 API接口文档")
                .termsOfServiceUrl("http://www.ruanyifeng.com/blog/2014/05/restful_api.html")
                .version("1.0.0")
                .build();
    }

    private ApiInfo apiInfoOnline() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("")
                .build();
    }
}
