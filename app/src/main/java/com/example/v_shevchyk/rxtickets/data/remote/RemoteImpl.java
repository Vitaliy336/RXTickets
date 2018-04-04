package com.example.v_shevchyk.rxtickets.data.remote;

import com.example.v_shevchyk.rxtickets.data.model.Price;
import com.example.v_shevchyk.rxtickets.data.model.Ticket;
import com.example.v_shevchyk.rxtickets.data.rest.ApiClient;
import com.example.v_shevchyk.rxtickets.data.rest.ApiInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RemoteImpl implements Remote{
    ApiInterface apiInterface;

    public RemoteImpl() {
        this.apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public Observable<List<Ticket>> getTickets(String from, String to) {
        return apiInterface.searchTickets(from, to)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<Ticket> getPriceObservable(final Ticket ticket) {
        return apiInterface.getPrice(ticket.getFlightNumber(), ticket.getFrom(), ticket.getTo())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Price, Ticket>() {
                    @Override
                    public Ticket apply(Price price) throws Exception {
                        ticket.setPrice(price);
                        return ticket;
                    }
                });
    }
}
