package com.sg.cachesystem.eviction;

import com.sg.cachesystem.exception.CacheEvictionException;

public interface EvictionPolicy<Key> {
  Key evict() throws CacheEvictionException;
  void accessed(final Key key);
  void bust();
  void remove(final Key key);
}
