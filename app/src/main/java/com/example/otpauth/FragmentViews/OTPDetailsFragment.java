package com.example.otpauth.FragmentViews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpauth.MainActivity;
import com.example.otpauth.R;
import com.example.otpauth.Util.ContactInfo;
import com.example.otpauth.Util.ContactsRecyclerViewAdapter;
import com.example.otpauth.Util.OTPDetailsAdapterTab2;
import com.example.otpauth.Util.OTPDetailsDAO;

import java.util.List;

public class OTPDetailsFragment extends  Fragment{

    private static final String TAG = "OTPDetailFragment";

    private OTPDetailsAdapterTab2 mAdapter;
    TextView txtVwNoData;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_otp_detail, container, false);
        txtVwNoData = view.findViewById(R.id.section_label);
        recyclerView = view.findViewById(R.id.otpDetailRecycler);


        try {
            //Checking instanceOf Activity
            if (getActivity() instanceof MainActivity){
                List<OTPDetailsDAO> arrOtpSentDetails = ((MainActivity) getActivity()).dbHandler.getAllOtpDetailsSent();

                mAdapter = new OTPDetailsAdapterTab2(getActivity(),arrOtpSentDetails);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                if (arrOtpSentDetails!=null && arrOtpSentDetails.size()>0)
                {
                    //Do Nothing
                    Log.e(TAG, "OTP SENT listData :"+arrOtpSentDetails.size());
                }
                else
                {
                    recyclerView.setVisibility(View.GONE);

                    txtVwNoData.setVisibility(View.VISIBLE);
                    txtVwNoData.setText(R.string.no_data);
                }
            }
            else
            {
                recyclerView.setVisibility(View.GONE);

                txtVwNoData.setVisibility(View.VISIBLE);
                txtVwNoData.setText(R.string.no_data);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error in setting Contacts Frag listData"+e.getMessage());
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            List<OTPDetailsDAO> arrOtpSentDetails = ((MainActivity)getActivity()).dbHandler.getAllOtpDetailsSent();
            if (arrOtpSentDetails!=null && arrOtpSentDetails.size()>0)
            {
                txtVwNoData.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                mAdapter = new OTPDetailsAdapterTab2(getActivity(),arrOtpSentDetails);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                Log.e(TAG, "OTP SENT listData :"+arrOtpSentDetails.size());
            }
            else
            {
                recyclerView.setVisibility(View.GONE);

                txtVwNoData.setVisibility(View.VISIBLE);
                txtVwNoData.setText(R.string.no_data);
            }
        }
    }
}
