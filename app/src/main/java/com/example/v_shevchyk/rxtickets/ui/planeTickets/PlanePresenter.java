package com.example.v_shevchyk.rxtickets.ui.planeTickets;

import android.util.Log;

import com.example.v_shevchyk.rxtickets.data.remote.RemoteImpl;
import com.example.v_shevchyk.rxtickets.data.model.Ticket;
import com.example.v_shevchyk.rxtickets.ui.adapter.TicketsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlanePresenter implements PlaneContract.IPlanePresenter, TicketsAdapter.TicketsAdapterListener{

    private PlaneContract.IPlaneView view;
    private RemoteImpl remote = new RemoteImpl();
    private List<Ticket> ticketList = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private static final String from = "DEL";
    private static final String to = "HYD";

    @Override
    public void attachView(PlaneContract.IPlaneView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getTickets() {
        ConnectableObservable<List<Ticket>> ticketsObservable = remote.getTickets(from, to).replay();
        disposable.add(
                ticketsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Ticket>>() {
                    @Override
                    public void onNext(List<Ticket> tickets) {
                        ticketList.clear();
                        ticketList.addAll(tickets);
                        view.displayTickets(ticketList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("Compleate", "s");
                    }
                })
        );

        disposable.add(
                ticketsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<List<Ticket>, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(List<Ticket> tickets) throws Exception {
                                return Observable.fromIterable(tickets);

                            }
                        })
                        .flatMap(new Function<Ticket, ObservableSource<Ticket>>() {
                            @Override
                            public ObservableSource<Ticket> apply(Ticket ticket) throws Exception {

                                return remote.getPriceObservable(ticket);
                            }
                        })
                        .subscribeWith(new DisposableObserver<Ticket>() {
                            @Override
                            public void onNext(Ticket ticket) {
                                int position = ticketList.indexOf(ticket);
                                ticketList.set(position, ticket);
                                view.displayTickets(ticketList);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        })

        );
        ticketsObservable.connect();

    }

    @Override
    public void onTicketSelected(Ticket contact) {

    }
}
