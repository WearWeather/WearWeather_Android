package com.wearweather.mainTabFragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import com.wearweather.R;

import java.util.ArrayList;
import java.util.List;


public class hourly_tab extends Fragment {

    View v;
    private RecyclerView tab_recyclerview;
    private List<hourly_connecting_items> lstDay;

    public hourly_tab(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_hourly_tab, container, false);
        tab_recyclerview = (RecyclerView) v.findViewById(R.id.hourly_recyclerview);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(),lstDay);
        tab_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        tab_recyclerview.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstDay = new ArrayList<>();
        //일단 리사이클러가 나오나 보려고 테스트용 하드코딩 했음.
        lstDay.add(new hourly_connecting_items("월요일",R.drawable.icon_sunny,21,26));
        lstDay.add(new hourly_connecting_items("화요일",R.drawable.icon_sunny,20,30));
        lstDay.add(new hourly_connecting_items("수요일",R.drawable.icon_sunny,19,23));
        lstDay.add(new hourly_connecting_items("목요일",R.drawable.icon_sunny,26,28));
        lstDay.add(new hourly_connecting_items("금요일",R.drawable.icon_sunny,23,32));
        lstDay.add(new hourly_connecting_items("토요일",R.drawable.icon_sunny,25,29));

    }
}