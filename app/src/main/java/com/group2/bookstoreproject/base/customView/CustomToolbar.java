package com.group2.bookstoreproject.base.customView;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.databinding.CustomToolbarBinding;

public class CustomToolbar extends ConstraintLayout {

    private CustomToolbarBinding binding;
    private Runnable onStartIconClick;
    private Runnable onEndIconClick;

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar);
        String title = attr.getString(R.styleable.CustomToolbar_android_title);
        int startIcon = attr.getResourceId(R.styleable.CustomToolbar_startIcon, R.drawable.ic_arrow_back_ios_new_24px);
        int endIcon = attr.getResourceId(R.styleable.CustomToolbar_endIcon, R.drawable.ic_more_vert_24px);
        boolean showStartIcon = attr.getBoolean(R.styleable.CustomToolbar_showStartIcon, true);
        boolean showEndIcon = attr.getBoolean(R.styleable.CustomToolbar_showEndIcon, false);
        boolean showTextStart = attr.getBoolean(R.styleable.CustomToolbar_showStartText, true);
        boolean titleStart = attr.getBoolean(R.styleable.CustomToolbar_titleStart, false);
        String textStart = attr.getString(R.styleable.CustomToolbar_textStart);
        boolean showShadow = attr.getBoolean(R.styleable.CustomToolbar_showShadow, false);

        float textStartSize = attr.getFloat(R.styleable.CustomToolbar_textStartSize, 25);

        attr.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_toolbar, this, true);

        setTitle(title != null ? title : "");
        setStartIconResource(startIcon);
        setEndIconResource(endIcon);
        setTextStart(textStart != null ? textStart : "Back");
        isShowEndIcon(showEndIcon);
        isShowStartIcon(showStartIcon);
        isShowStartText(showTextStart);
        isShowShadow(showShadow);
        setTitleStart(titleStart);
        setTextStartSize(textStartSize);
        binding.llStartIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStartIconClick != null) {
                    onStartIconClick.run();
                }
            }
        });

        binding.llEndIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEndIconClick != null) {
                    onEndIconClick.run();
                }
            }
        });
    }

    public void setOnStartIconClick(Runnable onStartIconClick) {
        this.onStartIconClick = onStartIconClick;
    }

    public void setOnEndIconClick(Runnable onEndIconClick) {
        this.onEndIconClick = onEndIconClick;
    }


    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public void setTextStart(String text) {
        binding.tvStartText.setText(text);
    }

    public void setTitleStart(boolean isStart){
        if(isStart){
            binding.tvTitle.setGravity(Gravity.CENTER_VERTICAL);
            binding.tvTitle.setPadding(50, 0,0,0);
        }
    }

    public void setStartIconResource(int icon) {
        binding.ivStartIcon.setImageResource(icon);
    }

    public void setEndIconResource(int icon) {
        binding.ivEndIcon.setImageResource(icon);
    }

    public void isShowStartIcon(boolean show) {
        binding.ivStartIcon.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void isShowEndIcon(boolean show) {
        binding.ivEndIcon.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void isShowStartText(boolean show) {
        binding.tvStartText.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void isShowShadow(boolean show) {
        binding.toolBarShadow.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setTextStartSize(float size) {
        binding.tvStartText.setTextSize(size);
    }
}
