package com.example.otpauth.FragmentViews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpauth.MainActivity;
import com.example.otpauth.R;
import com.example.otpauth.Util.ContactInfo;
import com.example.otpauth.Util.ContactsRecyclerViewAdapter;

import java.util.List;

public class ContactsFragment extends Fragment {

    private static final String TAG = "ContactsFragment";

    int position;
    private TextView textView;
    private ContactsRecyclerViewAdapter mAdapter;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        ContactsFragment tabFragment = new ContactsFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_lay_frag, container, false);
        try {
            //Checking instanceOf Activity
            if (getActivity() instanceof MainActivity){
                List<ContactInfo> contactInfos = ((MainActivity) getActivity()).dbHandler.getAllContacts();
                RecyclerView recyclerView = view.findViewById(R.id.contactFragRecycler);
                mAdapter = new ContactsRecyclerViewAdapter(getActivity(),contactInfos);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        textView = (TextView) view.findViewById(R.id.section_label); //Fragment test
        textView.setText("Fragment Contacts");

    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }
}