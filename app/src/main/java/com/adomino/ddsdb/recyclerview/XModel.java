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
    return XIds.id(this.id) + hashCode();
  }
}
