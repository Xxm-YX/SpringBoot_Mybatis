package com.example.config;



import com.example.handlers.MyEnumConverterFactory;
import com.example.handlers.MyEnumConverterFactory1;
import com.example.handlers.MyEnumConverterFactory2;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 自定义GET/POST表单提交方式的入参反序列化规则
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 把我们自定义的枚举转换器添加到Spring容器，Spring容器会把它加入到SpringMVC的拦截链路中
        registry.addConverterFactory(new MyEnumConverterFactory2());
    }

    /**
     * 自定义JSON响应时枚举字段的序列化行为：调用toString()
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }
}
