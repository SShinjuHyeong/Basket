package com.deep.basket.config;

import lombok.var;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger =  LogManager.getLogger(WebConfig.class);

    private static final String[] SECURE_PATTERNS = { "/users", "/users/**/*", "/authorities", "/authorities/**/*" };
    private static final String[] WILDCARD_PATTERN = { "/**" };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } //db에 저장된 암호 해싱하는 암호 인코더 정의

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info(">>> >>> webconfig - http - 401 CORS 세팅");
        http
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                ;
                //주소순서 바꿨더니 되버림;; 주소는 맨위에 표기
        http.cors();
    }

//    @Bean
//    public void addCorsMappings(CorsRegistry registry) {
//        logger.info(">>> >>> webconfig - 401 CORS 세팅");
    //Access-Control-Allow-Origin 헤더 세팅
    // `/**` : 모든 요청
//        registry.addMapping("/**")
//                .allowedOrigins("http://localHost:8081")    // 허용할 출처
//                .allowedMethods(ALLOWED_METHOD_NAMES.split(",")) // 허용할 HTTP method 모든 method
//                .allowCredentials(true) // 쿠키 인증 요청 허용
//                .exposedHeaders(HttpHeaders.LOCATION);
//                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱

//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8081","http://localhost:8099")
//                .allowedHeaders("*")
//                .exposedHeaders(HttpHeaders.LOCATION)
//                .allowCredentials(true)
//                .allowedMethods(HttpMethod.GET.name()
//                        ,HttpMethod.HEAD.name()
//                        ,HttpMethod.POST.name()
//                        ,HttpMethod.PATCH.name()
//                        ,HttpMethod.PUT.name()
//                        ,HttpMethod.DELETE.name()
//                        ,HttpMethod.OPTIONS.name())
//                .maxAge(3000)
//
//        ;
//}
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        logger.info(">>> >>> webconfig - registry - 401 CORS 세팅");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localHost:8081"); // 모든 오리진 허용
        configuration.addAllowedOrigin("http://localHost:8099"); // 모든 오리진 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.addExposedHeader("Authorization");
        source.registerCorsConfiguration("WILDCARD_PATTERN", configuration);
        return source;
    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//        registry.addResourceHandler("/image/**")
//                .addResourceLocations("file:///" + filePath)
//                .resourceChain(true)
////				addResolver : 한글을 decoding 해주는 영역
//                .addResolver(new PathResourceResolver(){
//                    @Override
//                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
//                        resourcePath = URLDecoder.decode(resourcePath,StandardCharsets.UTF_8);
//                        return super.getResource(resourcePath, location);
//                    }
//                });
//    }
}
