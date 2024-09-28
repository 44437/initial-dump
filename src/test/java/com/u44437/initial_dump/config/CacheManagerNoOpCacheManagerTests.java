package com.u44437.initial_dump.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"cache.enabled=false"})
class CacheManagerNoOpCacheManagerTests {
  @Autowired private CacheManager cacheManager;

  @Test
  void Should_ReturnNoOpCacheManager() {
    assertThat(cacheManager).isInstanceOf(NoOpCacheManager.class);
  }
}
