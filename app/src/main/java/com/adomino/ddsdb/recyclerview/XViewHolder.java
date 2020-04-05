package com.adomino.ddsdb.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class XViewHolder<T extends XModel> extends RecyclerView.ViewHolder {

  public interface Factory {
    XViewHolder create(ViewGroup viewGroup, XModel model);
  }

  @SuppressWarnings("unchecked")
  public static <T extends XViewHolder> T create(Class<T> clazz, ViewGroup parent,
      @LayoutRes int layout) {
    try {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(layout, parent, false);
      Constructor constructor = clazz.getConstructor(View.class);
      return (T) constructor.newInstance(view);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  public XViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  public abstract void bind(@NonNull T model);
}
