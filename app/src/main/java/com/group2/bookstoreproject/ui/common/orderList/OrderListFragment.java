package com.group2.bookstoreproject.ui.common.orderList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.Order;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentOrderListBinding;
import com.group2.bookstoreproject.ui.adapter.OrderListAdapter;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.List;


public class OrderListFragment extends BaseFragment<FragmentOrderListBinding, OrderListViewModel> {
    private String accountId ;
    private int role;
    private OrderListAdapter orderListAdapter ;

    @NonNull
    @Override
    protected FragmentOrderListBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentOrderListBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<OrderListViewModel> getViewModelClass() {
        return OrderListViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager sessionManager = SessionManager.getInstance();
        User loggedInUser = sessionManager.getLoggedInUser();
        if (loggedInUser!=null){
           accountId = loggedInUser.getUserId();
           role = loggedInUser.getRole();
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//         binding.btnAdd.setOnClickListener(v -> {
//            Order order = new Order();
//            order.setOrderId("3");
//            order.setUserId("thang@gmail.com");
//            order.setTotalAmount(5000);
//            order.setOrderStatus(1);
//            order.setOrderAt(20241111);
//            order.setTransactionNo("khai");
//            order.setAddress("Quan 9 ");
//            viewModel.addOrder(order);
//        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        orderListAdapter = new OrderListAdapter(role);
        orderListAdapter.setOnOrderDetailClickListener(position -> {
            Order order = orderListAdapter.getOrders().get(position);
            if (order != null && order.getOrderId() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", order.getUserId());
                bundle.putString("orderId", order.getOrderId());
                try {
                    if(role==2){
                        Navigation.findNavController(view).navigate(R.id.action_orderListFragment2_to_orderDetailsFragment2, bundle);
                    } else if (role==1) {
                        Navigation.findNavController(view).navigate(R.id.action_orderListFragment3_to_orderDetailsFragment3, bundle);
                    } else if (role==3) {
                        Navigation.findNavController(view).navigate(R.id.action_orderListFragment_to_orderDetailsFragment, bundle);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("OrderListFragment", "Order or Order ID is null");
            }
        });
        binding.recyclerViewOrder.setLayoutManager(new GridLayoutManager(getContext(),1));
        binding.recyclerViewOrder.setAdapter(orderListAdapter);

     //   viewModel.getOrders().observe(getViewLifecycleOwner(),orderListAdapter::setOrders);
        viewModel.getOrders().observe(getViewLifecycleOwner(), orders -> {
            orderListAdapter.setOrders(orders);
            updateEmptyView(orders);
        });

        if(role==1 || role==3){
            viewModel.fetchOrders();
            binding.backButton.setVisibility(View.GONE);
        } else if (role==2) {
            viewModel.fetchOrdersByUserId(accountId);
        }

         binding.tabAllDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabIndicatorAndTextColor(false, false, false, true);
                if(role==1 || role==3){
                    viewModel.fetchOrders();
                } else if (role==2) {
                    viewModel.fetchOrdersByUserId(accountId);
                }
            }
        });
        binding.tabDangDoiGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabIndicatorAndTextColor(true, false, false, false);
                if(role==1 || role==3){
                    viewModel.filterOrdersByStatus(1);
                } else if (role==2) {
                    viewModel.filterOrdersByStatus(1);
                }

            }
        });
        binding.tabDaHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabIndicatorAndTextColor(false, true, false, false);
                if(role==1 || role==3){
                    viewModel.filterOrdersByStatus(2);
                } else if (role==2) {
                    viewModel.filterOrdersByStatus(2);
                }
            }
        });
        binding.tabHuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabIndicatorAndTextColor(false, false, true, false);
                if(role==1 || role==3){
                    viewModel.filterOrdersByStatus(3);
                } else if (role==2) {
                    viewModel.filterOrdersByStatus(3);
                }
            }
        });
        if (role == 1 || role == 3) {
            orderListAdapter.setOnOrderItemClickListener(new OrderListAdapter.OnOrderItemClickListener() {
                @Override
                public void onOptionsClicked(int position, int newStatus) {
                    List<Order> orders = orderListAdapter.getOrders();
                    if (position >= 0 && position < orders.size()) {
                        Order order = orders.get(position);
                        viewModel.updateOrderStatus(order.getOrderId(), newStatus);
                        orderListAdapter.hideOptions();
                        binding.tabAllDonHang.performClick();
                    }
                }
            });
        }


//        orderListAdapter.setOnOrderItemClickListener(((position, newStatus) -> {
//            Order order = orderListAdapter.getOrders().get(position);
//            navigateToOrderDetails(order);
//        }));
    }
    private void setTabIndicatorAndTextColor(boolean dangDoiGiao, boolean daHoanThanh, boolean huyDonHang, boolean danhSachDonHang) {
        binding.indicatorDangDoiGiao.setVisibility(dangDoiGiao ? View.VISIBLE : View.GONE);
        binding.indicatorDaHoanThanh.setVisibility(daHoanThanh ? View.VISIBLE : View.GONE);
        binding.indicatorHuyDonHang.setVisibility(huyDonHang ? View.VISIBLE : View.GONE);
        binding.indicatorDanhSachDonHang.setVisibility(danhSachDonHang ? View.VISIBLE : View.GONE);

        binding.tabDangDoiGiao.setTextColor(dangDoiGiao ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.black));
        binding.tabDaHoanThanh.setTextColor(daHoanThanh ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.black));
        binding.tabHuyDonHang.setTextColor(huyDonHang ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.black));
        binding.tabAllDonHang.setTextColor(danhSachDonHang ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.black));
    }

//    private void navigateToOrderDetails(Order order) {
//        Bundle bundle = new Bundle();
//        bundle.putString("orderId", order.getOrderId());
//
//        Navigation.findNavController(requireView()).navigate(
//                R.id.action_orderListFragment_to_orderDetailsFragment,
//                bundle
//        );
//    }

    private void updateEmptyView(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            binding.recyclerViewOrder.setVisibility(View.GONE);
            binding.emptyOrderLayout.emptyOrderListLayout.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewOrder.setVisibility(View.VISIBLE);
            binding.emptyOrderLayout.emptyOrderListLayout.setVisibility(View.GONE);
        }
    }
}