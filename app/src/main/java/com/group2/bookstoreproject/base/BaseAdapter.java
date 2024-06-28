package com.group2.bookstoreproject.base;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class BaseAdapter<T, VH extends BaseItemViewHolder<T, ?>> extends RecyclerView.Adapter<VH> {

    private OnItemClickListener<T> onItemClickListener;
    private SetItemOrderBy<T> setItemOrderBy;
    private RecyclerView recyclerView;

    protected abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType);

    protected abstract DiffUtil.ItemCallback<T> differCallBack();

    private final AsyncListDiffer<T> differ = new AsyncListDiffer<>(this, differCallBack());

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VH viewHolder = onCreateViewHolder(inflater, parent, viewType);
        setupClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = differ.getCurrentList().get(position);
        holder.bind(item);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public void submitList(List<T> list) {
        if (setItemOrderBy != null) {
            setItemOrderBy.setItemOrder(list);
        }
//        differ.submitList(list);
        notifyDataSetChanged();
    }

    public void submitList(List<T> list, boolean autoScrollToNewElement) {
        if (setItemOrderBy != null) {
            setItemOrderBy.setItemOrder(list);
        }
        if(autoScrollToNewElement){
            differ.submitList(list, () -> {
                if (recyclerView != null && getItemCount()>0) {
                    recyclerView.smoothScrollToPosition(getItemCount() - 1);
                }
            });
        }else{
            differ.submitList(list);
        }
        notifyDataSetChanged();
    }

    public void setItemOrderBy(SetItemOrderBy<T> listener) {
        setItemOrderBy = listener;
    }

    public void setItemOnClickListener(OnItemClickListener<T> listener) {
        onItemClickListener = listener;
    }

    private void setupClickListener(@NonNull VH viewHolder) {
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        T item = differ.getCurrentList().get(position);
                        onItemClickListener.onItemClick(item);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }

    public interface SetItemOrderBy<T> {
        void setItemOrder(List<T> list);
    }
}
