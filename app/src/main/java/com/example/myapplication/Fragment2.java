package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

// Fragment2: هنا نجمع فيه معلومات المستخدم، لما نضغط ع Continue بتبعت ال name لل Host Activity

public class Fragment2 extends Fragment {

    // Interface: ال Activity لازم تعمل implement إله، وهيك ال Fragment بتبعت البيانات لل Activity
    public interface OnFragment2Listener {
        void onContinueClicked(String name);
    }


    // بنخزن فيه ال Activity اللي منفذة ال Interface
    private OnFragment2Listener mListener;

    // ViewBinding: بنوصل لل Views بدون findViewById
    private Fragment2Binding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // onAttach: بنعمله استدعاء لما ال Fragment يرتبط بActivity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2Listener) {
            mListener = (OnFragment2Listener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragment2Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment2Binding.inflate(inflater, container, false);

        binding.buttonContinue2.setOnClickListener(view -> {
            String name = binding.editText.getText().toString().trim();
            String email = binding.editTextEmail.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.editText.setError("Enter name!");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextEmail.setError("Enter a valid email!");
                return;
            }
            // بنبعت الاسم لل Activity عن طريق ال interface
            mListener.onContinueClicked(name);
        });

        return binding.getRoot();
    }
    // بنفضي ال binding عشان نتجنب Memory Leak
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}