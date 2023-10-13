package com.sg.cachesystem.bdd;

import com.sg.cachesystem.cache.Cache;
import com.sg.cachesystem.cache.CacheImpl;
import com.sg.cachesystem.eviction.EvictionPolicy;
import com.sg.cachesystem.eviction.LRUEvictionPolicy;
import com.sg.cachesystem.storage.CacheStorage;
import com.sg.cachesystem.storage.InMemoryCacheStorage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.junit.Assert;

import java.util.List;

public class lru_cache implements En {
  public lru_cache(){
    this.steps();
  }

  private EvictionPolicy<Integer> evictionPolicy;
  private CacheStorage<Integer, Integer> storage;
  private Cache<Integer, Integer> cache;

  public void steps(){

    Given("^an LRU cache with size (\\d+)$", (Integer size) -> {
      this.evictionPolicy = new LRUEvictionPolicy<>();
      this.storage = new InMemoryCacheStorage<>(size);
      this.cache = new CacheImpl<>(this.evictionPolicy, this.storage);
    });

    When("^an item is added to the cache with$", (DataTable keyValuePairs) -> {
      for (List<String> kv : keyValuePairs.asLists()){
        this.cache.put(Integer.parseInt(kv.get(0)), Integer.parseInt(kv.get(1)));
      }
    });

    Then("^the cache should contain the item$", (DataTable expectedKVs) -> {
      for (List<String> kv : expectedKVs.asLists()){
        Integer value = this.cache.get(Integer.parseInt(kv.get(0)));
        Assert.assertEquals("", Integer.parseInt(kv.get(1)), (int)value);
      }
    });

    And("^get an from cache with key (\\d+)$", (Integer key) -> {
      this.cache.get(key);
    });

    And("^update the cache value to$", (DataTable keyValuePairs) -> {
      for (List<String> kv : keyValuePairs.asLists()){
        this.cache.put(Integer.parseInt(kv.get(0)), Integer.parseInt(kv.get(1)));
      }
    });

    And("^the cache is busted$", () -> {
      this.cache.bust();
    });

    Then("^the cache should not contain any items$", (DataTable expectedKVs) -> {
      for (List<String> kv : expectedKVs.asLists()){
        Integer value = this.cache.get(Integer.parseInt(kv.get(0)));
        Assert.assertNull("", value);
      }
    });
  }
}
