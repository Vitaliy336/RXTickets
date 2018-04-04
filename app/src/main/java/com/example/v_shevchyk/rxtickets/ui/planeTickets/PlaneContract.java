package com.example.v_shevchyk.rxtickets.ui.planeTickets;

import com.example.v_shevchyk.rxtickets.data.model.Ticket;
import com.example.v_shevchyk.rxtickets.ui.base.BasePresenter;
import com.example.v_shevchyk.rxtickets.ui.base.BaseView;

import java.util.List;

public interface PlaneContract {

    interface IPlaneView extends BaseView{
        void displayTickets(List<Ticket> list);
    }

    interface IPlanePresenter extends BasePresenter<IPlaneView>{
        void getTickets();
    }
}
