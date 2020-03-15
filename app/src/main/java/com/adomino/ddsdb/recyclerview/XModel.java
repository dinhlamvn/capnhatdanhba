package com.adomino.ddsdb.recyclerview;

public abstract class XModel {

  private String id = "";

  public XModel(String id) {
    this.id = id;
  }

  boolean isEquals(XModel other) {
    return id() == other.id();
  }

  boolean isEqualsContents(XModel other) {
    return id() == other.id() && this.equals(other);
  }

  protected int id() {
    int hash = 7;
    int len = Math.min(32, this.id.length());
    for (int i = 0; i < len; ++i) {
      hash = hash * 31 + this.id.charAt(i);
    }
    return hash + hashId();
  }

  public abstract int viewType();

  public abstract int hashId();
}
