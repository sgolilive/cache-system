package com.sg.cachesystem.storage;

import com.sg.cachesystem.exception.CacheFullException;
import com.sg.cachesystem.exception.KeyNotFoundException;

public interface CacheStorage<Key, Value> {
  void put(final Key key, final Value value) throws CacheFullException;
  Value get(final Key key) throws KeyNotFoundException;
  void remove(final Key key);
  void bust();
}