package com.example.victorjuez.mywaiter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CartaFragment extends Fragment {

    private TextView textView;
    private int position;

    public CartaFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carta, container, false);

        textView = rootView.findViewById(R.id.section_label);
        textView.setText("putu lmao "+ position);

        return rootView;
    }
}
