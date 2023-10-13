package com.sg.cachesystem.cache;

import com.sg.cachesystem.eviction.EvictionPolicy;
import com.sg.cachesystem.storage.CacheStorage;
import com.sg.cachesystem.exception.CacheEvictionException;
import com.sg.cachesystem.exception.CacheFullException;
import com.sg.cachesystem.exception.KeyNotFoundException;

/**
* &#064;Author:  Shanmukhi Goli
*
* this class provides cache functionality for storing and retrieving values
* the eviction policy can be any supported policy like LRU which can be injected during startup
* the storage can be any supported storage policy like in-memory can be injected during startup
*  */
public class CacheImpl<Key, Value> implements Cache<Key, Value> {

  private final EvictionPolicy<Key> evictionPolicy;
  private final CacheStorage<Key, Value> storage;

  public CacheImpl(final EvictionPolicy<Key> evictionPolicy, final CacheStorage<Key, Value> storage){
    this.evictionPolicy = evictionPolicy;
    this.storage = storage;
  }

  /**
   * stores the key value pair in the cache
   *
   * @param key the key to access the value from cache
   * @param value the value to store
   *
   */
  @Override
  public void put(Key key, Value value) throws CacheFullException, CacheEvictionException {
    try{
      this.tryPut(key, value);
    } catch (CacheFullException exception){
      this.tryEvictKey();
      this.tryPut(key, value);
    }
  }

  private void tryEvictKey() throws CacheEvictionException {
    Key evictedKey = this.evictionPolicy.evict();

    if(evictedKey == null){
      throw new RuntimeException("cache is full, unable to evict any keys");
    }

    this.storage.remove(evictedKey);
  }

  private void tryPut(final Key key, final Value value) throws CacheFullException {
//    Assert.notNull(key, "key should not be empty or null");
//    Assert.notNull(value, "value should not be empty or null");

    this.storage.put(key, value);
    this.evictionPolicy.accessed(key);
  }

  @Override
  public Value get(Key key) {
    try {
      Value value = this.storage.get(key);
      this.evictionPolicy.accessed(key);
      return value;
    }catch (KeyNotFoundException exception){
      System.out.println(exception.getMessage());
    }
    return null;
  }

  @Override
  public void bust() {
    this.storage.bust();
    this.evictionPolicy.bust();
  }

  @Override
  public void remove(Key key) {
    this.storage.remove(key);
    this.evictionPolicy.remove(key);
  }
}
