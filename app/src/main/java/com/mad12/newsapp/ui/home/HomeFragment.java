package com.mad12.newsapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mad12.newsapp.databinding.FragmentHomeBinding;
import com.mad12.newsapp.model.Category;

import java.util.ListIterator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TabLayout categoriesBar = binding.categoriesBar;
        ViewPager articlesViewPager = binding.articlesViewPager;

        categoriesBar.addTab(categoriesBar.newTab().setText("Tất cả"));

        //get category list
        homeViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                ListIterator<Category> categoriesDataIterator = categories.listIterator();
                while(categoriesDataIterator.hasNext()) {
                    String tabname = categoriesDataIterator.next().getName();
                    categoriesBar.addTab(categoriesBar.newTab().setText(tabname));
                }
                ArticleViewPagerAdapter adapter = new ArticleViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, categories);
                articlesViewPager.setAdapter(adapter);
            }
        });


        categoriesBar.setupWithViewPager(articlesViewPager);

        return root;
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}