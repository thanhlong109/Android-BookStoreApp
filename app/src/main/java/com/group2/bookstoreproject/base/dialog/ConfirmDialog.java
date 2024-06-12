package com.group2.bookstoreproject.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.group2.bookstoreproject.R;

public class ConfirmDialog extends Dialog {

    private ConfirmCallback callback;
    private String title;
    private String message;
    private String positiveButtonTitle;
    private String negativeButtonTitle;

    public ConfirmDialog(Context context, ConfirmCallback callback, String title, String message, String positiveButtonTitle, String negativeButtonTitle) {
        super(context);
        this.callback = callback;
        this.title = title;
        this.message = message;
        this.positiveButtonTitle = positiveButtonTitle;
        this.negativeButtonTitle = negativeButtonTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        if (message != null) {
            TextView tvContent = findViewById(R.id.tvContent);
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(message);
        }

        Button btnNegative = findViewById(R.id.btnNegative);
        btnNegative.setText(negativeButtonTitle);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.negativeAction();
                }
                dismiss();
            }
        });

        Button btnPositive = findViewById(R.id.btnPositive);
        btnPositive.setText(positiveButtonTitle);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.positiveAction();
                }
                dismiss();
            }
        });
    }

    public interface ConfirmCallback {
        void negativeAction();
        void positiveAction();
    }
}
