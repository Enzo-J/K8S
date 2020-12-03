package com.wenge.tbase.harbor.config;
import com.wenge.tbase.harbor.interceptor.HarborServiceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebServerMvcConfigurerAdapter implements WebMvcConfigurer {
	 @Bean
	    public HarborServiceInterceptor storeBucketInterceptor() {
	        return new HarborServiceInterceptor();
	    }

//	    @Override
//	    public void addInterceptors(InterceptorRegistry registry) {
//	        registry.addInterceptor(storeBucketInterceptor()).addPathPatterns("/wos/**");
//	    }
    @Override
	public  void addResourceHandlers(ResourceHandlerRegistry registry) {
		    registry.addResourceHandler("/").addResourceLocations("classpath:/static/");

	        registry.addResourceHandler("swagger-ui.html")
	                .addResourceLocations("classpath:/META-INF/resources/");
//
	        registry.addResourceHandler("/webjars/**")
	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
}
