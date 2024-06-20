package com.group2.bookstoreproject.base.customView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.group2.bookstoreproject.databinding.CustomChatMessageBinding;
import com.group2.bookstoreproject.R;


public class CustomChatMessage extends ConstraintLayout {

    public CustomChatMessageBinding binding;
    private boolean startDirection;
    private final ColorStateList leftBackGround;
    private final ColorStateList rightBackGround;
    private final LinearLayout.LayoutParams layoutParams;

    public CustomChatMessage(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.CustomChatMessage);
        String content = attr.getString(R.styleable.CustomChatMessage_messageContent);
        int avatar = attr.getResourceId(R.styleable.CustomChatMessage_android_src, R.drawable.default_user_avt);
        leftBackGround = attr.getColorStateList(R.styleable.CustomChatMessage_leftBackGroundColor);
        rightBackGround = attr.getColorStateList(R.styleable.CustomChatMessage_rightBackGroundColor);
        int textLeftColor = attr.getColor(R.styleable.CustomChatMessage_textLeftColor, ContextCompat.getColor(context,R.color.white));
        int textRightColor = attr.getColor(R.styleable.CustomChatMessage_textRightColor, ContextCompat.getColor(context,R.color.black));
        startDirection = attr.getBoolean(R.styleable.CustomChatMessage_startDirection, true);

        attr.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        binding = CustomChatMessageBinding.inflate(inflater, this, true);

        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        setStartDirection(startDirection);
        setContent(content);
        setAvatar(avatar);
        setLeftMessageBackground(leftBackGround);
        setRightMessageBackground(rightBackGround);
        setTextLeftColor(textLeftColor);
        setTextRightColor(textRightColor);
    }

    public void refreshContent() {
        binding.tvMessageLeft.setLayoutParams(layoutParams);
        binding.tvMessageRight.setLayoutParams(layoutParams);
    }

    public void setStartDirection(boolean isStartDirection) {
        startDirection = isStartDirection;
        if (startDirection) {
            binding.llMessageLeft.setVisibility(View.VISIBLE);
            binding.llMessageRight.setVisibility(View.GONE);
        } else {
            binding.llMessageRight.setVisibility(View.VISIBLE);
            binding.llMessageLeft.setVisibility(View.GONE);
        }
    }

    public void setMessageSeen(boolean isSeen) {
        if (startDirection && !isSeen) {
            binding.ivSendStatusLeft.setVisibility(View.VISIBLE);
        } else {
            binding.ivSendStatusLeft.setVisibility(View.GONE);
        }
    }

    public void setContent(String content) {
        if (startDirection) {
            binding.tvMessageLeft.setText(content);
        } else {
            binding.tvMessageRight.setText(content);
        }
    }

    public void setAvatar(int resource) {
        if (startDirection) {
            binding.ivMessageLeft.setImageResource(resource);
        } else {
            binding.ivMessageRight.setImageResource(resource);
            binding.ivMessageLeft.setVisibility(View.GONE);
        }
    }

    public void setLeftMessageBackground(ColorStateList colorStateList) {
        ViewCompat.setBackgroundTintList(binding.llContentMessageLeft, colorStateList);
    }

    public void setRightMessageBackground(ColorStateList colorStateList) {
        ViewCompat.setBackgroundTintList(binding.llContentMessageRight, colorStateList);
    }

    public void setTextLeftColor(int color) {
        binding.tvMessageLeft.setTextColor(color);
    }

    public void setTextRightColor(int color) {
        binding.tvMessageRight.setTextColor(color);
    }

    public void setContentVisible(boolean isVisible) {
        if (startDirection) {
            binding.llContentMessageLeft.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        } else {
            binding.llContentMessageRight.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

}


