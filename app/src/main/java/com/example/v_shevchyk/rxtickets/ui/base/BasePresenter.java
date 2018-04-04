package com.example.v_shevchyk.rxtickets.ui.base;

public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
