package com.example.mydatamanagement.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mydatamanagement.R;

public class InputDialog  extends AlertDialog implements View.OnClickListener {
    private EditText etID,etCount;  //编辑框
    private Button btnConfrim, btnCancel;  //确定取消按钮
    private OnEditInputFinishedListener mListener; //接口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        initID();
    }
    private void initID(){
        etCount=(EditText)findViewById(R.id.serch_count);
        etID=(EditText)findViewById(R.id.serch_id);
        btnConfrim = (Button)findViewById(R.id.btn_confirm);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnConfrim.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }
    public interface OnEditInputFinishedListener{
        void editInputFinished(String id,String count);
    }
    public InputDialog(Context context, OnEditInputFinishedListener mListener) {
        super(context);
        this.mListener = mListener;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm:
                if (mListener != null) {
                    String id = etID.getText().toString().trim();
                    String cnt = etCount.getText().toString().trim();
                    mListener.editInputFinished(id,cnt);
                }
                dismiss();
                break;

                default:
                    dismiss();
                    break;
        }
    }
}
