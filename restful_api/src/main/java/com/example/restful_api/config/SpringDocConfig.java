package com.example.restful_api.config;

import com.example.restful_api.security.login.filter.CustomUsernamePasswordAuthenticationFilter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                title = "롤링페이퍼 API Document",
                description = "RESTful API를 전반",
                license = @License(),
                contact = @Contact(
                        name = "Jung_Dayoung",
                        email = "mugglecoding@gmail.com"
                )
        ),
        tags = {
                @Tag(name = "01.User", description = "사용자 "),
                @Tag(name = "02.Paper", description = "롤링페이퍼"),
                @Tag(name = "03.Comment", description = "게시글 기능"),
        },
        security = {
                @SecurityRequirement(name = "Authorization"),
        }
)
@SecuritySchemes({
        @SecurityScheme(name = "Authorization",
                type = SecuritySchemeType.APIKEY,
                description = "JSON Web Token",
                in = SecuritySchemeIn.HEADER,
                paramName = "Authorization"),
})
@RequiredArgsConstructor
@Configuration
public class SpringDocConfig {

    private final ApplicationContext applicationContext;

    @Bean
    public OpenApiCustomiser springSecurityLoginEndpointCustomizer() {
        FilterChainProxy filterChainProxy = applicationContext.getBean(
                AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, FilterChainProxy.class);
        return openAPI -> {
            for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                Optional<CustomUsernamePasswordAuthenticationFilter> optionalFilter =
                        filterChain.getFilters().stream()
                                .filter(CustomUsernamePasswordAuthenticationFilter.class::isInstance)
                                .map(CustomUsernamePasswordAuthenticationFilter.class::cast)
                                .findAny();
                if (optionalFilter.isPresent()) {
                    CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = optionalFilter.get();
                    Operation operation = new Operation();
                    Schema<?> schema = new ObjectSchema()
                            .addProperty(customUsernamePasswordAuthenticationFilter.getUsernameParameter(), new StringSchema().example("user"))
                            .addProperty(customUsernamePasswordAuthenticationFilter.getPasswordParameter(), new StringSchema().example("123!@#"));

                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                    new MediaType().schema(schema)));
                    operation.requestBody(requestBody);
                    operation.setSummary("로그인");
                    operation.setDescription("로그인 성공 후 JWT 인증을 통해 토큰을 발급받는다 ");

                    ApiResponses apiResponses = new ApiResponses();
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));
                    operation.responses(apiResponses);
                    operation.addTagsItem("01.User");
                    PathItem pathItem = new PathItem().post(operation);
                    openAPI.getPaths().addPathItem("/api/users/signin", pathItem);
                }
            }
        };
    }


}