package com.u44437.initial_dump.config;

import com.u44437.initial_dump.repository.UsersDao;
import com.u44437.initial_dump.repository.UsersRepository;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersConfiguration {
  @Bean
  public UsersDao usersDao(DataSource dataSource) {
    return new UsersRepository(dataSource);
  }

  @Bean
  public EnvConfiguration envConfiguration() {
    return new EnvConfiguration();
  }

  @Bean
  public EnvSystem envSystem() {
    return new EnvSystem();
  }

  @Bean
  public DataSource dataSource() {
    System.out.println(envSystem().getAppName());

    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(envConfiguration().getUrl());
    dataSource.setUsername(envConfiguration().getUsername());
    dataSource.setPassword(envConfiguration().getPassword());
    dataSource.setMinimumIdle(envConfiguration().getMinIdle());
    dataSource.setMaximumPoolSize(envConfiguration().getMaxPoolSize());

    return dataSource;
  }
}
