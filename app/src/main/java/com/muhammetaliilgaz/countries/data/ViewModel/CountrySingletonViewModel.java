package com.muhammetaliilgaz.countries.data.ViewModel;

import com.muhammetaliilgaz.countries.data.model.country.Data;

import org.jetbrains.annotations.NotNull;

public class CountrySingletonViewModel {

    private Data country;

    private static final CountrySingletonViewModel countrySingletonViewModel = new CountrySingletonViewModel();

    public static CountrySingletonViewModel getInstance(){
        return countrySingletonViewModel;
    }

    public Data getCountry() {


        return country;
    }



    public void setCountry(@NotNull Data country) {
     this.country = country;
    }
}
