package com.adomino.ddsdb.recyclerview;

public abstract class XModel {
  protected boolean isEquals(XModel other) {
    return hashId() == other.hashId();
  }

  protected boolean isEqualsContents(XModel other) {
    return hashId() == other.hashId();
  }

  public abstract int viewType();

  public abstract int hashId();
}
