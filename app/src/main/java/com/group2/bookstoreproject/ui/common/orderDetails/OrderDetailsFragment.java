package com.group2.bookstoreproject.ui.common.orderDetails;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Order;
import com.group2.bookstoreproject.data.model.OrderItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentOrderDetailsBinding;
import com.group2.bookstoreproject.ui.adapter.OrderDetailAdapter;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.List;


public class OrderDetailsFragment extends BaseFragment<FragmentOrderDetailsBinding, OrderDetailsViewModel> {

    private OrderDetailAdapter orderDetailAdapter;
    private String accountId ;
    private int role;

    @NonNull
    @Override
    protected FragmentOrderDetailsBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentOrderDetailsBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<OrderDetailsViewModel> getViewModelClass() {
        return OrderDetailsViewModel.class;
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

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role ==3 ){
                    Navigation.findNavController(view).navigate(R.id.action_orderDetailsFragment_to_orderListFragment);
                } else if (role ==2){
                    Navigation.findNavController(view).navigate(R.id.action_orderDetailsFragment2_to_orderListFragment2);
                } else if (role==1) {
                    Navigation.findNavController(view).navigate(R.id.action_orderDetailsFragment3_to_orderListFragment3);
                }
            }
        });

        if (getArguments() != null) {
            String orderId = getArguments().getString("orderId", "");
            if (!orderId.isEmpty()) {
                viewModel.getOrderDetails(orderId);
                viewModel.getOrderDetailByOrderId(orderId);
            }
            String userId = getArguments().getString("userId","");
            if (!userId.isEmpty()){
                viewModel.getUserbyUserId(userId);
            }

        }

        viewModel.getOrder().observe(getViewLifecycleOwner(), this::displayOrderDetails);
        viewModel.getUser().observe(getViewLifecycleOwner(),this::displayUserInfor);

        binding.tvCopyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = binding.tvAddress.getText().toString();

                // Sao chép nội dung vào clipboard
                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Địa chỉ nhận hàng", address);
                clipboard.setPrimaryClip(clip);

                // Hiển thị thông báo sao chép thành công
                Toast.makeText(requireContext(), "Đã sao chép địa chỉ", Toast.LENGTH_SHORT).show();
            }
        });
        binding.headerTitle.setOnClickListener(v -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId("6");
            orderItem.setOrderId("3");
            orderItem.setPrice(60000);
            orderItem.setQuantity(1);
            orderItem.setBookId("4");
            viewModel.addOrder(orderItem);
        });
        viewModel.getOrderItems().observe(getViewLifecycleOwner(), orderItem -> {
            if(orderItem!=null){
                orderDetailAdapter.setOrderItems((List<OrderItem>) orderItem);
            }
        });
        viewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            if (books != null) {
                orderDetailAdapter.setBooks(books);
            }
        });
        orderDetailAdapter = new OrderDetailAdapter();
        binding.recyclerViewOrderItems.setAdapter(orderDetailAdapter);
        binding.recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

     private void displayOrderDetails(Order order) {
        // Hiển thị chi tiết đơn hàng trong giao diện người dùng
        binding.tvAddress.setText(order.getAddress());
        int status = order.getOrderStatus();
        if(status ==1){
            binding.tvTransport.setText("Đang vận chuyển");
            binding.tvTransport.setTextColor(getResources().getColor(R.color.gray_600));

            binding.orderStatusTitle.setText("Đơn hàng chưa hoàn thành");
            binding.orderStatusSubtitle.setText("Quý khách vui lòng chờ đợi");
            binding.lnBackGroundStatus.setBackgroundColor(getResources().getColor(R.color.gray_600));
        } else if (status==3) {
            binding.tvTransport.setText("Hủy đơn hàng ");
            binding.tvTransport.setTextColor(getResources().getColor(R.color.red_light));

            binding.orderStatusTitle.setText("Đơn hàng đã bị hủy ");
            binding.orderStatusSubtitle.setText("Mong quý khách ủng hộ shop");
            binding.lnBackGroundStatus.setBackgroundColor(getResources().getColor(R.color.red_light));
        } else if (status==2) {
            binding.tvTransport.setText("Giao hàng thành công");
            binding.tvTransport.setTextColor(getResources().getColor(R.color.green_3));

            binding.orderStatusTitle.setText("Đơn hàng đã hoàn thành");
            binding.orderStatusSubtitle.setText("Cảm ơn bạn đã đặt hàng");
            binding.lnBackGroundStatus.setBackgroundColor(getResources().getColor(R.color.green_3));
        }
    }

    private void displayUserInfor(User user){
        binding.tvPhone.setText(user.getPhone());
        binding.tvFullname.setText(user.getFullName());
    }
}