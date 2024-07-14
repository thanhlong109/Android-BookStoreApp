    package com.group2.bookstoreproject.ui.adapter;

    import android.graphics.Color;
    import android.graphics.Typeface;
    import android.text.SpannableString;
    import android.text.style.StyleSpan;
    import android.text.style.UnderlineSpan;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.appcompat.content.res.AppCompatResources;
    import androidx.core.content.ContextCompat;
    import androidx.recyclerview.widget.DiffUtil;
    import androidx.recyclerview.widget.RecyclerView;

    import com.group2.bookstoreproject.R;
    import com.group2.bookstoreproject.data.model.Order;
    import com.group2.bookstoreproject.util.FormatterUtils;

    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;

    public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

        private List<Order> orders = new ArrayList<>();
        private OnOrderItemClickListener listener;
        private OnOrderDetailClickListener detailListener;
        private int role;

        public OrderListAdapter(int role) {
            this.role = role;
        }

        public interface OnOrderItemClickListener {
            void onOptionsClicked(int position, int newStatus);
        }
        public interface OnOrderDetailClickListener {
            void onDetailClicked(int position);
        }
        public void setOnOrderItemClickListener(OnOrderItemClickListener listener) {
            this.listener = listener;
        }
        public void setOnOrderDetailClickListener(OnOrderDetailClickListener detailListener) {
            this.detailListener = detailListener;
        }
        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> newOrders) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new OrdersDiffCallback(orders, newOrders));
            orders.clear();
            orders.addAll(newOrders);
            diffResult.dispatchUpdatesTo(this);
        }

        @NonNull
        @Override
        public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_orders, parent, false);
            return new OrderListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
            Order order = orders.get(position);
            holder.bind(order);
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public void hideOptions() {
            notifyDataSetChanged();
        }

        class OrderListViewHolder extends RecyclerView.ViewHolder {

            private TextView tvStatus;
            private TextView tvFullname;
            private TextView tvAddress;
            private TextView tvOrderAt;
            private TextView tvOrderId;
            private TextView tvTotalAmount;
            private TextView tvDetailOrder;
            private LinearLayout linearLayout;

            private LinearLayout lnOptions;
            private LinearLayout lnOptionDelivering;
            private LinearLayout lnOptionDelivered;
            private LinearLayout lnOptionCancelled;

            OrderListViewHolder(@NonNull View itemView) {
                super(itemView);
                tvStatus = itemView.findViewById(R.id.tvStatus);
                tvAddress = itemView.findViewById(R.id.tvDiaChi);
                tvOrderId = itemView.findViewById(R.id.tvMaDonHang);
                tvTotalAmount = itemView.findViewById(R.id.tcTongGiaTien);
                linearLayout = itemView.findViewById(R.id.lnBackGround);
                tvOrderAt = itemView.findViewById(R.id.tvNgayDat);
                tvFullname = itemView.findViewById(R.id.tvType);

                tvDetailOrder = itemView.findViewById(R.id.tvXemChiTietDonHang);
                String text = "Xem chi tiết đơn hàng";
                SpannableString content = new SpannableString(text);
                content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
                content.setSpan(new StyleSpan(Typeface.ITALIC), 0, text.length(), 0);
                tvDetailOrder.setText(content);

                lnOptions = itemView.findViewById(R.id.lnOptions);
                lnOptionDelivering = itemView.findViewById(R.id.lnOptionDelivering);
                lnOptionDelivered = itemView.findViewById(R.id.lnOptionDelivered);
                lnOptionCancelled = itemView.findViewById(R.id.lnOptionCancelled);

                linearLayout.setOnLongClickListener(v -> {
                    if (role == 1 || role == 3) {
                        lnOptions.setVisibility(View.VISIBLE);
                    }
                    return true;
                });

                lnOptionDelivering.setOnClickListener(v -> {
                    handleOptionClicked(1);
                });

                lnOptionDelivered.setOnClickListener(v -> {
                    handleOptionClicked(2);
                });

                lnOptionCancelled.setOnClickListener(v -> {
                    handleOptionClicked(3);
                });

                itemView.setOnClickListener(v -> {
                    if (role == 1 || role == 3) {
                        lnOptions.setVisibility(View.GONE);
                    }
                });
                tvDetailOrder.setOnClickListener(v -> {
                    if (detailListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            detailListener.onDetailClicked(position);
                        }
                    }
                });
            }

            private void handleOptionClicked(int newStatus) {
                if (listener != null && (role == 1 || role == 3)) {
                    listener.onOptionsClicked(getAdapterPosition(), newStatus);
                }
            }

            void bind(Order order) {
                tvAddress.setText(order.getAddress());
                tvOrderId.setText(order.getOrderId());
                tvTotalAmount.setText(FormatterUtils.ToMoneyText(order.getTotalAmount()));
                int orderStatus = order.getOrderStatus();
                if (orderStatus == 1) {
                    tvStatus.setText("Đang Giao Hàng");
                    linearLayout.setBackgroundColor(Color.GRAY);
                } else if (orderStatus == 2) {
                    tvStatus.setText("Đã Giao Hàng");
                    linearLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.green_3));
                } else if (orderStatus == 3) {
                    tvStatus.setText("Đã Hủy Đơn Hàng");
                    linearLayout.setBackgroundColor(Color.RED);
                }
                long orderAt = order.getOrderAt();
                String formattedDate = formatDate(orderAt);
                tvOrderAt.setText(formattedDate);
                // Lấy userId và truy vấn fullname từ AuthRepository
                tvFullname.setText(order.getUserId());

                lnOptions.setVisibility(View.GONE);
                tvDetailOrder.setVisibility(View.VISIBLE);
            }

            private String formatDate(long dateLong) {
                String dateString = String.valueOf(dateLong);
                try {
                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    Date date = originalFormat.parse(dateString);
                    SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    return newFormat.format(date);
                } catch (Exception e) {
                    e.printStackTrace();
                    return dateString;
                }
            }
        }

        static class OrdersDiffCallback extends DiffUtil.Callback {

            private List<Order> oldList;
            private List<Order> newList;

            OrdersDiffCallback(List<Order> oldList, List<Order> newList) {
                this.oldList = oldList;
                this.newList = newList;
            }

            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).getOrderId().equals(newList.get(newItemPosition).getOrderId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Order oldOrder = oldList.get(oldItemPosition);
                Order newOrder = newList.get(newItemPosition);
                return oldOrder.getAddress().equals(newOrder.getAddress()) &&
                        oldOrder.getOrderStatus() == newOrder.getOrderStatus() &&
                        oldOrder.getTotalAmount() == newOrder.getTotalAmount() &&
                        oldOrder.getOrderAt() == newOrder.getOrderAt() &&
                        oldOrder.getUserId().equals(newOrder.getUserId());
            }
        }
    }

