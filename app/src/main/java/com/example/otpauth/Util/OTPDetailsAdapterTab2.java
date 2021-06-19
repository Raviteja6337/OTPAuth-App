package com.example.otpauth.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.otpauth.OtpDetailScreen;
import com.example.otpauth.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OTPDetailsAdapterTab2 extends RecyclerView.Adapter<OTPDetailsAdapterTab2.ViewHolder>{

    private static final String TAG = "OTPDetailsAdapterTab2";

    private List<OTPDetailsDAO> mData;
    private LayoutInflater mInflater;
    private ContactsRecyclerViewAdapter.ItemClickListener mClickListener;
    Context mContext;

    public OTPDetailsAdapterTab2(Context context, List<OTPDetailsDAO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    @NotNull
    @Override
    public OTPDetailsAdapterTab2.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.otp_detail_rw_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull OTPDetailsAdapterTab2.ViewHolder holder, int position) {
        if (mData != null && mData.size()>0 && mData.get(position)!=null)
        {
            try {

                String strOTP = holder.otpSentTxtView.getText() + " " + mData.get(position).getOtpSent();
                holder.otpSentTxtView.setText(strOTP);

                String strMob = holder.otpSentMob.getText() + " " + mData.get(position).getOtpSentMobNo();
                holder.otpSentMob.setText(strMob);

                String strOtpTime = holder.otpSentTime.getText() + " " + mData.get(position).getOtpSentTime();
                holder.otpSentTime.setText(strOtpTime);
            }
            catch (Exception e)
            {
                Log.e(TAG,"error in onBind setting text values"+e.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView otpSentTxtView;
        TextView otpSentMob;
        TextView otpSentTime;

        ViewHolder(View itemView) {
            super(itemView);
            otpSentTxtView = itemView.findViewById(R.id.otpSentTxtView);
            otpSentMob = itemView.findViewById(R.id.otpSentPhoneNo);
            otpSentTime = itemView.findViewById(R.id.otpSentTimeTxtVw);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
