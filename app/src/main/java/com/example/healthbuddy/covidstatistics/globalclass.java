package com.example.healthbuddy.covidstatistics;

import com.example.healthbuddy.covidstatistics.CountryInfo;

public class globalclass {
    public globalclass(){

    }



    public globalclass(String country, long cases) {
        this.country = country;
        this.cases = cases;

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getUpdated() {
        return cases;
    }

    public void setUpdated(long cases) {
        this.cases = cases;
    }

    public String country;
    public long cases;


    public CountryInfo countryInfo;

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }
}

