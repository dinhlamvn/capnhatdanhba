package com.adomino.ddsdb.recyclerview;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class XViewHolder<T extends XModel> extends RecyclerView.ViewHolder {

  public interface Factory {
    XViewHolder create(ViewGroup viewGroup, int viewType);
  }

  public XViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  public abstract void bind(@NonNull T model);
}
