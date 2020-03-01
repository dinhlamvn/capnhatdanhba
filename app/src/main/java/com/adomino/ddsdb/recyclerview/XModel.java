package com.adomino.ddsdb.recyclerview;

public interface XModel {
  boolean isEquals(XModel other);

  boolean isEqualsContents(XModel other);

  int viewType();
}
