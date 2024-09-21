package com.u44437.initial_dump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// I am unsure which one is better
@Component
public class EnvSystem {
  @Autowired
  private Environment env;

  public String getAppName() {
    return env.getProperty("APP_NAME");
  }
}
