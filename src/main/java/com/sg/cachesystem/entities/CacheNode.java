package com.sg.cachesystem.entities;

public class CacheNode<Key> {
  private final Key key;
  private CacheNode<Key> prev;
  private CacheNode<Key> next;

  public CacheNode(final Key key) {
    this.key = key;
    this.next = this.prev = null;
  }

  public Key key(){
    return this.key;
  }

  public CacheNode<Key> prev(){
    return this.prev;
  }

  public void prev(final CacheNode<Key> prev){
    this.prev = prev;
  }

  public CacheNode<Key> next(){
    return this.next;
  }

  public void next(final CacheNode<Key> next){
    this.next = next;
  }
}