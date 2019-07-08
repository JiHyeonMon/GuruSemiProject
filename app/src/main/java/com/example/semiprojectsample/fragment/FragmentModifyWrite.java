package com.example.semiprojectsample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.activity.NewMemoActivity;

public class FragmentModifyWrite extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_write, container, false);

        view.findViewById(R.id.edtModifyMemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메모 수정

            }
        });

        return view;
    }
}
