package com.sg.cachesystem.exception;

public class CacheFullException extends Exception {
  public CacheFullException(final String message){
      super(message);
  }
}
