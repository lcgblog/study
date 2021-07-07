package com.lcgblog.study.springboot;

import com.lcgblog.study.springboot.plugins.CustomTcpDiscoverySpi;
import com.lcgblog.study.springboot.plugins.SecurityPluginConfiguration;
import com.lcgblog.study.springboot.plugins.SecurityPluginProvider;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class IgniteConfig {

  public static final String IGNITE_PROPS_PREFIX = "ignite";

  public IgniteConfig() {
  }

  @Bean
  public IgniteConfiguration igniteConfiguration() {
    IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
    SecurityCredentials securityCredentials = new SecurityCredentials("testUser", "testPassword");
    SecurityPluginConfiguration securityPluginConfiguration = new SecurityPluginConfiguration(securityCredentials);
    igniteConfiguration.setPluginConfigurations(securityPluginConfiguration);
    CustomTcpDiscoverySpi customTcpDiscoverySpi = new CustomTcpDiscoverySpi();
    customTcpDiscoverySpi.setSecurityCredentials(securityCredentials);
    igniteConfiguration.setPluginProviders(new SecurityPluginProvider());
    igniteConfiguration.setDiscoverySpi(customTcpDiscoverySpi);
    return igniteConfiguration;
  }

  @ConditionalOnBean({IgniteConfiguration.class})
  @Bean
  public Ignite ignite(IgniteConfiguration cfg) {
    return Ignition.start(cfg);
  }

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("ignite")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/ignite/**"))
        .build();
  }

}
