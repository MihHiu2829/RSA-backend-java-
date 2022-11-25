package com.example.api_ver5;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

public abstract class base_ACt <T extends ViewBinding, M extends ViewModel> extends AppCompatActivity
{
    T binding ;
    M viewModel ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding() ;
        viewModel = new ViewModelProvider(this).get(ClassVM());
        initViews();
        setContentView(binding.getRoot());
    }

    protected abstract void initViews();

    protected abstract Class<M> ClassVM();

    protected abstract T initViewBinding();
}
