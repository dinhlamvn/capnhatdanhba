package com.adomino.ddsdb.recyclerview;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import java.util.ArrayList;
import java.util.List;

public class XAdapter extends ListAdapter<XModel, XViewHolder> {

  private XViewHolder.Factory viewHolderFactory;
  private XClickListener listener;

  private XAdapter() {
    super(new XDiffUtil());
  }

  public static XAdapter create() {
    return new XAdapter();
  }

  public XAdapter setViewHolderFactory(XViewHolder.Factory viewHolderFactory) {
    this.viewHolderFactory = viewHolderFactory;
    return this;
  }

  @Override public int getItemViewType(int position) {
    return getItem(position).viewType();
  }

  public XAdapter setItemClickListener(XClickListener listener) {
    this.listener = listener;
    return this;
  }

  public void submitChange(List<XModel> models) {
    submitList(models);
  }

  @NonNull
  @Override
  public XViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return viewHolderFactory.create(parent, viewType);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onBindViewHolder(@NonNull XViewHolder holder, int position) {
    holder.bind(getItem(position));
    if (listener != null) {
      listener.onSepUpItemClick(holder.itemView, getItem(position), position);
    }
  }
}
