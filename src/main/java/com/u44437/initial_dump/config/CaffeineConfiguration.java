package com.u44437.initial_dump.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@EnableCaching
@Configuration
public class CaffeineConfiguration {
  @Bean
  public Caffeine caffeineConfig() {
    return Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(5));
  }

  @Bean
  public CacheManager cacheManager(
      Caffeine caffeine, @Value("${cache.enabled}") boolean isCacheEnabled) {
    if (!isCacheEnabled) {
      return new NoOpCacheManager();
    }
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    caffeineCacheManager.setCaffeine(caffeine);

    return caffeineCacheManager;
  }
}
