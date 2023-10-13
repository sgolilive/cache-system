package com.sg.cachesystem.cache;

import com.sg.cachesystem.exception.CacheEvictionException;
import com.sg.cachesystem.exception.CacheFullException;
import com.sg.cachesystem.exception.KeyNotFoundException;

public interface Cache<Key, Value>{
  void put(final Key key, final Value value) throws CacheFullException, CacheEvictionException;
  Value get(final Key key) throws KeyNotFoundException;
  void bust();
  void remove(final Key key);
}

