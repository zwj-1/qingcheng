package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.seckill.SeckillGoods;
import com.qingcheng.pojo.seckill.SeckillOrder;
import com.qingcheng.service.seckill.SeckillOrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/****
 * @Author:itheima
 * @Date:2019/5/27 17:40
 * @Description:
 *****/
@RestController
@RequestMapping(value = "/seckill/order")
public class SeckillOrderController {


    @Reference
    private SeckillOrderService seckillOrderService;


    /***
     * URL:/seckill/order/add
     * 用户下单操作
     * @param time:商品所在的时间区间
     * @param id:商品iD
     */
    @RequestMapping(value = "/add")
    public Result add(Long id,String time){
        //获取用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //如果用户没登录，则提醒用户登录
        if(username.equals("anonymousUser")){
            return new Result(403,"未登录，请先登录！");
        }

        try {
            //调用Service下单操作
            Boolean bo = seckillOrderService.add(id, time, username);

            if(bo){
                return new Result(0,"抢单成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(1,"抢单失败！");
    }

}
