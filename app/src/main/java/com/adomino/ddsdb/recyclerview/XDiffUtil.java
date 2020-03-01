package com.adomino.ddsdb.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class XDiffUtil extends DiffUtil.ItemCallback<XModel> {
  @Override public boolean areItemsTheSame(@NonNull XModel oldItem, @NonNull XModel newItem) {
    return oldItem.isEquals(newItem);
  }

  @Override public boolean areContentsTheSame(@NonNull XModel oldItem, @NonNull XModel newItem) {
    return oldItem.isEqualsContents(newItem);
  }
}
