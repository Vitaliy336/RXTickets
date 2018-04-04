package com.example.v_shevchyk.rxtickets.data.remote;

import com.example.v_shevchyk.rxtickets.data.model.Ticket;
import java.util.List;

import io.reactivex.Observable;

public interface Remote {
    Observable<List<Ticket>> getTickets(String from, String to);

    Observable<Ticket> getPriceObservable(Ticket ticket);

}
