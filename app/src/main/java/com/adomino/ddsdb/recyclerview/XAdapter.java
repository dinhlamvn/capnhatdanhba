package com.adomino.ddsdb.recyclerview;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import java.util.Collections;
import java.util.List;

public class XAdapter extends ListAdapter<XModel, XViewHolder> {

  private XViewHolder.Factory viewHolderFactory;

  private XAdapter() {
    super(new XDiffUtil());
    setHasStableIds(true);
  }

  public static XAdapter create(@NonNull XViewHolder.Factory viewHolderFactory) {
    XAdapter instance = new XAdapter();
    instance.setViewHolderFactory(viewHolderFactory);
    return instance;
  }

  private void setViewHolderFactory(@NonNull XViewHolder.Factory viewHolderFactory) {
    this.viewHolderFactory = viewHolderFactory;
  }

  @Override
  public int getItemViewType(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).id();
  }

  public void submitChange(XModel model) {
    submitChange(Collections.singletonList(model));
  }

  public void submitChange(List<XModel> models) {
    submitList(models);
  }

  @NonNull
  @Override
  public XViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return viewHolderFactory.create(parent, getItem(viewType));
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onBindViewHolder(@NonNull XViewHolder holder, int position) {
    holder.bind(getItem(position));
  }
}
