package com.wenge.tbase.sysadmin.config;


import org.springframework.context.annotation.Bean;
import com.wenge.tbase.sysadmin.interceptor.SysadminServiceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebServerMvcConfigurerAdapter implements WebMvcConfigurer {
	 @Bean
	 public SysadminServiceInterceptor storeBucketInterceptor() {
	        return new SysadminServiceInterceptor();
	    }

//	    @Override
//	    public void addInterceptors(InterceptorRegistry registry) {
//	        registry.addInterceptor(storeBucketInterceptor()).addPathPatterns("/wos/**");
//	    }
    @Override
	public  void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("swagger-ui.html")
//				.addResourceLocations("classpath:/META-INF/resources/");
//		registry.addResourceHandler("/").addResourceLocations("classpath:/static/");
//
////
//	        registry.addResourceHandler("/webjars/**")
//	                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
}
