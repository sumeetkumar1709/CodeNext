package com.segmnf.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.segmnf.myapplication.Fragments.EditorFragment;
import com.segmnf.myapplication.Fragments.OuputFragment;
import com.segmnf.myapplication.Fragments.QuestionFragment;


public class QuestionPagerAdapter extends FragmentStateAdapter {
    public QuestionPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public QuestionPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public QuestionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new QuestionFragment();
            case 1:
                return new EditorFragment();
            case 2:
                return new OuputFragment();

            default:
                return new QuestionFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
