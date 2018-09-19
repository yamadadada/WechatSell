package com.example.sell.service;

import com.example.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    void notify(String notifyData);

}
