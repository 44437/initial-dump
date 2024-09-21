package com.u44437.initial_dump.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="db")
public class EnvConfiguration {
  private String url;
  private String username;
  private String password;
  private int minIdle;
  private int maxPoolSize;
}
