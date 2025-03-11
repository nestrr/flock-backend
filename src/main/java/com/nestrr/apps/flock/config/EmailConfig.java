package com.nestrr.apps.flock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class EmailConfig {
  @Bean
  public ISpringTemplateEngine emailTemplateEngine() {
    final SpringTemplateEngine result = new SpringTemplateEngine();
    // Html email resolver
    result.addTemplateResolver(this.htmlTemplateResolver());
    return result;
  }

  @Bean
  public ITemplateResolver htmlTemplateResolver() {
    ClassLoaderTemplateResolver result = new ClassLoaderTemplateResolver();
    result.setPrefix("email-templates/");
    result.setSuffix(".html");
    result.setTemplateMode(TemplateMode.HTML);
    result.setCharacterEncoding("UTF-8");
    return result;
  }
}
