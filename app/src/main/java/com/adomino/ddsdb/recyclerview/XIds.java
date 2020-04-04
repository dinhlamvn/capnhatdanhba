package com.adomino.ddsdb.recyclerview;

public final class XIds {
  public static int id(String s) {
    int hash = 7;
    int len = Math.min(32, s.length());
    for (int i = 0; i < len; ++i) {
      hash = hash * 31 + s.charAt(i);
    }
    return hash;
  }
}
