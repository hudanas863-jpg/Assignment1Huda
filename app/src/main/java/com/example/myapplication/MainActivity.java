package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityMainBinding;

// Host Activity: بتعمل implement لل Interfaces تبعت Fragment2 وFragment3 وهي وسيط بينهم
public class MainActivity extends AppCompatActivity
        implements Fragment2.OnFragment2Listener,
        Fragment3.OnFragment3Listener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // بنضيف Fragment1 أول مرة بس عشان ما يتكرر لما الشاشة تنقلب (rotation)
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, Fragment1.newInstance(null, null))
                    .commit();
        }

        binding.buttonContinue.setOnClickListener(view ->
                showFragment(Fragment2.newInstance(null, null)));

        // بنراقب ال back stack: لو فاضي يعني إحنا في Fragment1 فبنظهر الزر اوبنخفيه
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            boolean onFirst = getSupportFragmentManager().getBackStackEntryCount() == 0;
            binding.buttonContinue.setVisibility(onFirst ? View.VISIBLE : View.GONE);
        });
    }

    // استبدال ال Fragment الحالي باخر
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // من Fragment2: استلمنا الاسم نفتح Fragment3 ونمرره اله
    @Override
    public void onContinueClicked(String name) {
        showFragment(Fragment3.newInstance(name));
    }

    // من Fragment3: ال CheckBox تغير نحدث الزر داخل Fragment3
    @Override
    public void onConfirmationChanged(boolean isChecked) {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (fragment instanceof Fragment3) {
            ((Fragment3) fragment).updateButton(isChecked);
        }
    }
}