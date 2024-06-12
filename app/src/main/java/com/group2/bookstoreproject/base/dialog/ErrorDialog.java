package com.group2.bookstoreproject.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.group2.bookstoreproject.R;

public class ErrorDialog extends Dialog {

    private String errorContent;
    private String textButton;

    public ErrorDialog(Context context, String errorContent, String textButton) {
        super(context);
        this.errorContent = errorContent;
        this.textButton = textButton;
    }

    public ErrorDialog(Context context, String errorContent) {
        this(context, errorContent, "Đóng");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.dialog_error, null, false);

        TextView tvContentError = rootView.findViewById(R.id.tvContentError);
        tvContentError.setText(errorContent);

        Button btnOK = rootView.findViewById(R.id.btnOK);
        if (textButton != null) {
            btnOK.setText(textButton);
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setContentView(rootView);
    }
}
