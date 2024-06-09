package com.example.vitorya_app05;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlanetaFragment extends Fragment {

    private static final String ARG_PLANETA = "arg_planeta";

    private String planeta;

    public static PlanetaFragment newInstance(String planeta) {
        PlanetaFragment fragment = new PlanetaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLANETA, planeta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            planeta = getArguments().getString(ARG_PLANETA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planeta, container, false);

        Log.d("PlanetaFragment", "onCreateView: Planet name is " + planeta);
        TextView titleText = view.findViewById(R.id.titleText);
        ImageView backgroundImage = view.findViewById(R.id.backgroundImage);

        int resId = getResources().getIdentifier(planeta.toLowerCase(), "drawable", getContext().getPackageName());
        backgroundImage.setImageResource(resId);

        titleText.setText(planeta);

        return view;
    }
}