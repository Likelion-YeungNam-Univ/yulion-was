package org.example.yulion.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.yulion.global.auth.AuthConstants;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.config.Elements.JWT;

@OpenAPIDefinition(
        servers = {
                @Server(url = "{${app.backend-api}}", description = "Production Server"),
                @Server(url = "http://localhost:8080", description = "Local Server")
        },
        tags = {
                @Tag(name = "Auth", description = "인증"),
        }
)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Configuration
public class SwaggerConfig {

    private final Environment environment;


    @Bean
    public OpenAPI openAPI() {
        final SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT);

        return new OpenAPI()
                .info(info())
                .addServersItem(new io.swagger.v3.oas.models.servers.Server().url(""))
                .addSecurityItem(securityRequirement)
                .components(securitySchemes());
    }

    @Bean
    public OperationCustomizer disableSwaggerSecurity() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethodAnnotation(DisableSwaggerSecurity.class) != null) {
                operation.setSecurity(Collections.emptyList());
            }
            return operation;
        };
    }


    private Info info() {
        final String activeProfile = getActiveProfile();
        return new Info()
                .title("YuLion API (" + activeProfile + ")")
                .description("API 명세 문서")
                .version("1.0.0");
    }


    private Components securitySchemes() {
        final SecurityScheme securityScheme = new SecurityScheme()
                .name(JWT)
                .type(SecurityScheme.Type.HTTP)
                .scheme(AuthConstants.TOKEN_TYPE.trim())
                .bearerFormat(JWT)
                .in(SecurityScheme.In.HEADER)
                .name(AUTHORIZATION);

        return new Components()
                .addSecuritySchemes(JWT, securityScheme);
    }

    private String getActiveProfile() {
        if (ObjectUtils.isEmpty(environment.getActiveProfiles())) {
            return environment.getDefaultProfiles()[0];
        }
        return environment.getActiveProfiles()[0];
    }

}
