package com.sg.cachesystem.eviction;

import com.sg.cachesystem.entities.CacheNode;
import com.sg.cachesystem.exception.CacheEvictionException;

import java.util.HashMap;

public class LRUEvictionPolicy<Key> implements EvictionPolicy<Key> {

  private final HashMap<Key, CacheNode<Key>> keys;
  private CacheNode<Key> head;
  private CacheNode<Key> tail;

  public LRUEvictionPolicy(){
    this.keys = new HashMap<>();
    this.head = this.tail = null;
  }

  @Override
  public Key evict() throws CacheEvictionException {
    CacheNode<Key> node = this.detachFromHead();
    if(node == null){
      throw new CacheEvictionException("unable to evict keys from cache storage");
    }
    this.keys.remove(node.key());
    return node.key();
  }

  private CacheNode<Key> detachFromHead(){
    if(this.head == null){
      return null;
    }

    CacheNode<Key> node = head;
    this.head = this.head.next();
    this.keys.remove(node.key());

    if(this.head == null){
      this.tail = null;
    }

    return node;
  }

  private void attacheToTail(final CacheNode<Key> node){

    if(this.head == null){
      this.head = node;
    } else{
      this.tail.next(node);
      node.prev(this.tail);
    }

    this.tail = node;
    node.next(null);
  }

  @Override
  public void accessed(final Key key) {
      CacheNode<Key> node = this.keys.getOrDefault(key, null);

      if(node == null){
        node = new CacheNode<>(key);
      } else if(node.key() == head.key()){
        this.detachFromHead();
      } else if(node.key() == tail.key()){
        //nothing to do
      } else {
        this.adjustNode(node);
      }

    this.attacheToTail(node);
  }

  public void bust(){
    this.keys.clear();
    this.head = this.tail = null;
  }

  @Override
  public void remove(Key key) {

    CacheNode<Key> node = this.keys.getOrDefault(key,null);

    if(node == null){
      return;
    }

    if(node.key() == head.key()){
      this.detachFromHead();
    } else if(node.key() == tail.key()){
      this.detachFromTail();
    } else {
      this.adjustNode(node);
    }
  }

  private void adjustNode(CacheNode<Key> node){
    node.prev().next(node.next());
    node.next().prev(node.prev());
  }

  private void detachFromTail(){

    if(tail == null){
      return;
    }

    tail = tail.prev();
    tail.next(null);
  }
}
