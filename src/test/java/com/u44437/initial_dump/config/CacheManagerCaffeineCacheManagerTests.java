package com.u44437.initial_dump.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"cache.enabled=true"})
class CacheManagerCaffeineCacheManagerTests {
  @Autowired private CacheManager cacheManager;

  @Test
  void Should_ReturnCaffeineCacheManager() {
    assertThat(cacheManager).isInstanceOf(CaffeineCacheManager.class);
  }
}
