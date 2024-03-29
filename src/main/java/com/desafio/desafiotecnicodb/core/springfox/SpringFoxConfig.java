package com.desafio.desafiotecnicodb.core.springfox;

import com.desafio.desafiotecnicodb.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.desafio.desafiotecnicodb"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(Resource.class, InputStream.class, URL.class, URI.class, File.class)
                .apiInfo(apiInfo())
                .tags(new Tag("Receitas","Gerencia as receitas"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Desafio Tecnico DB")
                .description("API para documentação do desafio proposto")
                .version("1")
                .contact(new Contact("Rafael Guido","https://rafaeltoccolini.com","rafatocco7@gmail.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
