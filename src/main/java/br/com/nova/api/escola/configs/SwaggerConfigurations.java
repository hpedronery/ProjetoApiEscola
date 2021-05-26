package br.com.nova.api.escola.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
public class SwaggerConfigurations {

    @Value("${swagger.info.title}")
    private String title;
    @Value("${swagger.info.description}")
    private String description;
    @Value("${swagger.info.version}")
    private String version;
    @Value("${swagger.info.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${swagger.info.contactName}")
    private String contactName;
    @Value("${swagger.info.contactUrl}")
    private String contactUrl;
    @Value("${swagger.info.contactEmail}")
    private String contactEmail;
    @Value("${swagger.info.license}")
    private String license;
    @Value("${swagger.info.licenseUrl}")
    private String licenseUrl;

    private final Contact contact = new Contact(contactName, contactUrl, contactEmail);
    private final ApiInfo apiInfo = new ApiInfo(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, Collections.emptyList());
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Collections.singletonList("application/json"));

    public Docket api() {
        String packageName = this.getClass().getPackage().getName();
        String rootPackage = packageName.substring(0, packageName.lastIndexOf('.'));

        return new Docket(DocumentationType.SWAGGER_2)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage(rootPackage))
                .build();
    }
}
