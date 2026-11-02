package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

// Fragment3: بنعرض فيه ال name وفيه CheckBox وButton، وال Button disabled لحد ما المستخدم يعمل check
public class Fragment3 extends Fragment {

    // ال key اللي بنخزن فيه ال name جوا ال Bundle
    private static final String ARG_NAME = "name";

    // Interface: بخبر ال Host Activity لما حالة ال CheckBox تتغير
    public interface OnFragment3Listener {
        void onConfirmationChanged(boolean isChecked);
    }

    private OnFragment3Listener mListener;
    private Fragment3Binding binding;
    private String mName;

    public Fragment3() {
        // Required empty public constructor
    }

    // Factory method: بنحط ال name في Bundle وبنستخدم setArguments عشان نمرره لل Fragment
    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    // نتأكد إن ال Activity منفذة OnFragment3Listener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment3Listener) {
            mListener = (OnFragment3Listener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragment3Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);

        // عرض الاسم
        binding.textViewName.setText("Welcome, " + mName + "!");

        // الحالة الابتدائية: الزر معطل ونصه Continue
        binding.buttonAction.setEnabled(false);
        binding.buttonAction.setText("Continue");

        // عند تغير ال CheckBox نخبر ال Activity
        binding.checkBoxConfirm.setOnCheckedChangeListener((buttonView, isChecked) ->
                mListener.onConfirmationChanged(isChecked));

        return binding.getRoot();
    }

    // ال Activity تستدعيها لتغيير الزر
    public void updateButton(boolean isChecked) {
        if (binding == null) return;
        binding.buttonAction.setEnabled(isChecked);
        binding.buttonAction.setText(isChecked ? "Finish" : "Continue");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}