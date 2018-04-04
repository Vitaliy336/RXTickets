package com.example.v_shevchyk.rxtickets;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.v_shevchyk.rxtickets.data.model.Ticket;
import com.example.v_shevchyk.rxtickets.ui.adapter.TicketsAdapter;
import com.example.v_shevchyk.rxtickets.ui.planeTickets.PlaneContract;
import com.example.v_shevchyk.rxtickets.ui.planeTickets.PlanePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PlaneContract.IPlaneView, TicketsAdapter.TicketsAdapterListener{

    private PlanePresenter presenter;
    private View rootView;
    @BindView(R.id.ticketList_rv)
    protected RecyclerView tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_layout);
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        initPresenter();
    }

    private void init() {
        LinearLayoutManager ticketManager = new LinearLayoutManager(this);
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
        TicketsAdapter adapter = new TicketsAdapter(this, list, this);
        tickets.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTicketSelected(Ticket contact) {

    }
}
