package com.example.semiprojectsample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.activity.NewMemoActivity;
import com.google.android.material.tabs.TabLayout;

public class FragmentModifyWrite extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NewMemoActivity.ViewPagerAdapter mViewPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_write, container, false);

        return view;
    }
}
