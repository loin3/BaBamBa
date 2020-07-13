package com.example.enclosedmusicshareapp;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBarDisplayer {
    private ProgressDialog progressDialog;

    ProgressBarDisplayer(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("로딩중");
        progressDialog.setCancelable(false);
    }

    public void showDialog(){
        progressDialog.show();
    }

    public void hideDialog(){
        progressDialog.hide();
    }

}
