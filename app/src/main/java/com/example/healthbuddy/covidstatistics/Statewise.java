package com.example.healthbuddy.covidstatistics;

public class Statewise {
    public String state;
    public long confirmed;
    public long recovered;
    public long deaths;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long active;

}
