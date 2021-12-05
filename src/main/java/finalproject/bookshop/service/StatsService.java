package finalproject.bookshop.service;


import finalproject.bookshop.model.view.StatsView;

public interface StatsService {


    void onRequest();
    StatsView getStats();
}
