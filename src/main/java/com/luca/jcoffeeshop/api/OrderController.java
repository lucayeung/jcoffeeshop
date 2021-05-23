package com.luca.jcoffeeshop.api;

import com.luca.jcoffeeshop.biz.OrderService;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import com.luca.jcoffeeshop.query.UserExpress;
import com.luca.jcoffeeshop.vo.OrderStatusVo;
import com.luca.jcoffeeshop.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    @Qualifier("standardOrderService")
    private OrderService orderService;

    /**
     * 我的订单
     */
    @GetMapping("my-orders")
    public ResponseResult myOrders(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        List<OrderVo> userOrders = orderService.queryUserOrders(user.getUserId());
        return new ResponseResult("操作成功", 200, userOrders);
    }

    /**
     * 创建订单
     */
    @PostMapping("confirm-order")
    public ResponseResult createOrder(HttpServletRequest request, @RequestBody @Validated UserExpress userExpress) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        orderService.createOrder(user.getUserId(), userExpress);
        return new ResponseResult("操作成功", 200);
    }

    /**
     * 查询订单状态
     */
    @GetMapping("status/{orderId}")
    public ResponseResult queryOrderStatus(HttpServletRequest request, @PathVariable String orderId) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        OrderStatusVo orderStatusVo = orderService.queryOrderStatus(user.getUserId(), orderId);
        return new ResponseResult("查询成功", 200, orderStatusVo);
    }

    /**
     * 支付订单
     */
    @PostMapping("pay/{orderId}")
    public ResponseResult pay(HttpServletRequest request, @PathVariable String orderId) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        orderService.pay(user.getUserId(), orderId);
        return new ResponseResult("操作成功", 200);
    }

    /**
     * 确认收货
     */
    @PostMapping("receipt/{orderId}")
    public ResponseResult receipt(HttpServletRequest request, @PathVariable String orderId) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        orderService.receipt(user.getUserId(), orderId);
        return new ResponseResult("操作成功", 200);
    }

}
