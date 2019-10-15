package com.karthik.taxi.ui.taxilist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.karthik.taxi.R;

import androidx.annotation.Nullable;

public class BottomFragment  extends BottomSheetDialogFragment {

    public static BottomFragment getInstance() {
        return new BottomFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_item_taxi, container, false);


        return view;
    }

}