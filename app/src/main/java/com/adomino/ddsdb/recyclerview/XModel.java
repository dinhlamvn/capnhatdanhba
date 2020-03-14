package com.adomino.ddsdb.recyclerview;

public abstract class XModel {

  private int id = 0;

  public XModel(int id) {
    this.id = id;
  }

  boolean isEquals(XModel other) {
    return id() == other.id();
  }

  boolean isEqualsContents(XModel other) {
    return id() == other.id() && this.equals(other);
  }

  protected int id() {
    return (id * hashId() + 32 * id);
  }

  public abstract int viewType();

  public abstract int hashId();
}
