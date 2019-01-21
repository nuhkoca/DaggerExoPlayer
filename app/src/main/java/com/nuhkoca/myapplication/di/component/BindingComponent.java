package com.nuhkoca.myapplication.di.component;

import com.nuhkoca.myapplication.di.module.BindingModule;
import com.nuhkoca.myapplication.di.qualifier.DataBinding;

import androidx.databinding.DataBindingComponent;
import dagger.Component;

@DataBinding
@Component(dependencies = AppComponent.class, modules = BindingModule.class)
public interface BindingComponent extends DataBindingComponent {
}
