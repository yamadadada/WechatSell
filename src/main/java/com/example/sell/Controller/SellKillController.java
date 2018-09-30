package com.example.sell.Controller;

import com.example.sell.service.SellKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/skill")
@Slf4j
public class SellKillController {

    @Autowired
    private SellKillService sellKillService;

    /**
     * 查询特价活动特价商品的信息
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception{
        return sellKillService.querySellKillProductInfo(productId);
    }

    /**
     * 秒杀，没有抢到获得提示，抢到会返回剩余的库存量
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception {
        log.info("@skill request, productId:" + productId);
        sellKillService.orderProductMockDiffUser(productId);
        return sellKillService.querySellKillProductInfo(productId);
    }
}
