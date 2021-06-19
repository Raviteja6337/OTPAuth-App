package com.example.otpauth.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.otpauth.MainActivity;
import com.example.otpauth.OtpDetailScreen;
import com.example.otpauth.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    private List<ContactInfo> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context mContext;

    public ContactsRecyclerViewAdapter(Context context, List<ContactInfo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cntct_info_rw_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        String strName = "";
        if (mData != null && mData.size()>0 && mData.get(position)!=null && mData.get(position).getContactName()!=null)
        {
            strName = mData.get(position).getContactName();
            holder.cntctNameTxtView.setTag(mData.get(position).getPhoneNo());
        }
        holder.cntctNameTxtView.setText(strName);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cntctNameTxtView;

        ViewHolder(View itemView) {
            super(itemView);
            cntctNameTxtView = itemView.findViewById(R.id.contactName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView txtVwName = (TextView) view.findViewById(R.id.contactName);

            Intent otpDetailIntnt = new Intent(mContext, OtpDetailScreen.class);

            Bundle bundle = new Bundle();
            ContactInfo cntObj = new ContactInfo();
            cntObj.setContactName(txtVwName.getText().toString());
            cntObj.setPhoneNo((String)txtVwName.getTag());
            bundle.putSerializable("ContactInfoObj",cntObj);

            otpDetailIntnt.putExtras(bundle);
            mContext.startActivity(otpDetailIntnt);
        }
    }

//    String getItem(int id) {
//        return mData.get(id);
//    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}