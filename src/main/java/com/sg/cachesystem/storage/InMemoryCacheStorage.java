package com.sg.cachesystem.storage;

import com.sg.cachesystem.exception.CacheFullException;
import com.sg.cachesystem.exception.KeyNotFoundException;

import java.util.HashMap;

/**
 * &#064;Author  Shanmukhi Goli
 *
 * this class is an implementation of in-memory cache storage
 *
 * */
public class InMemoryCacheStorage<Key, Value> implements CacheStorage<Key, Value> {

  private final HashMap<Key, Value> storage;
  private final int maxKeys;

  public InMemoryCacheStorage(final int maxKeys){
    this.maxKeys = maxKeys;
    this.storage = new HashMap<>();
  }

  @Override
  public void put(Key key, Value value) throws CacheFullException {
    if(this.storage.size() >= this.maxKeys){
      throw new CacheFullException("the cache storage is full, and can't store any more values");
    }

    this.storage.put(key, value);
  }

  /**
   * try to get value associated with the key from cache
   *
   * @param key for which the value to returned
   * @throws KeyNotFoundException is thrown when the given is not found in the cache
   *
   * */

  @Override
  public Value get(Key key) throws KeyNotFoundException {

    Value value = this.storage.getOrDefault(key, null);

    if(value == null){
      throw new KeyNotFoundException(String.format("the given key %s not found in the cache storage", key));
    }

    return value;
  }

  /**
  * removes the given key from the storage
  *
  * @param key the key to remove from storage
  *
  * */
  @Override
  public void remove(Key key) {
    this.storage.remove(key);
  }

  /**
   *
   * clears the entire cache storage
   *
   * */

  @Override
  public void bust() {
    this.storage.clear();
  }
}
