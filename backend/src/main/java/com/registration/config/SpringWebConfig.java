package com.registration.config;

import com.registration.util.Constants;
import com.registration.localization.I18n;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        return new CustomLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setDefaultEncoding(Constants.UTF8);
        source.setUseCodeAsDefaultMessage(true);
        source.setFallbackToSystemLocale(false);
        return source;
    }

    @Bean
    public I18n i18n() {
        I18n i18n = new I18n();
        i18n.setMessageSource(messageSource());
        return i18n;
    }
}