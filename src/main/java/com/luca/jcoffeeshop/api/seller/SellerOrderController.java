package com.luca.jcoffeeshop.api.seller;

import com.luca.jcoffeeshop.biz.OrderService;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("order/seller")
public class SellerOrderController {

    @Autowired
    @Qualifier("standardOrderService")
    private OrderService orderService;

    /**
     * 发货
     */
    @PostMapping("ship/{orderId}")
    public ResponseResult ship(HttpServletRequest request, @PathVariable String orderId) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        orderService.ship(user.getUserId(), orderId);
        return new ResponseResult("操作成功", 200);
    }
}
