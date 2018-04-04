package com.example.v_shevchyk.rxtickets.ui.planeTickets;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.v_shevchyk.rxtickets.R;
import com.example.v_shevchyk.rxtickets.data.model.Ticket;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketFragment extends Fragment implements PlaneContract.IPlaneView {

    private PlanePresenter presenter;
    private View rootView;
    @BindView(R.id.ticketList_rv)
    protected RecyclerView tickets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ticket_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        initPresenter();
    }

    private void init() {
        LinearLayoutManager ticketManager = new LinearLayoutManager(getActivity());
        ticketManager.setOrientation(LinearLayoutManager.VERTICAL);
        tickets.setLayoutManager(ticketManager);
    }

    private void initListener() {

    }

    private void initPresenter() {
        presenter = new PlanePresenter();
        presenter.attachView(this);
        presenter.getTickets();
    }

    @Override
    public void displayTickets(List<Ticket> list) {
        Log.e("LIST", list.toString());
    }
}
