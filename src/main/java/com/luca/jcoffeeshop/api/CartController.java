package com.luca.jcoffeeshop.api;

import com.luca.jcoffeeshop.biz.CartService;
import com.luca.jcoffeeshop.dto.LoginUserDTO;
import com.luca.jcoffeeshop.dto.ResponseResult;
import com.luca.jcoffeeshop.query.AddToCartQuery;
import com.luca.jcoffeeshop.query.RemoveFromCardQuery;
import com.luca.jcoffeeshop.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    @Qualifier("standardCartService")
    private CartService cartService;

    @GetMapping("my-cart")
    public ResponseResult myCart(HttpServletRequest request) {
        // TODO 抽取登录认证逻辑
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        CartVO response = cartService.myCart(user.getUserId());
        return new ResponseResult("查询成功", 200, response);
    }

    @PostMapping("add")
    public ResponseResult addProduct(HttpServletRequest request,
            @RequestBody @Validated AddToCartQuery addToCartQuery) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        cartService.addProduct(user.getUserId(), addToCartQuery);
        return new ResponseResult("操作成功", 200);
    }

    @DeleteMapping("remove")
    public ResponseResult removeProduct(HttpServletRequest request,
            @RequestBody @Validated RemoveFromCardQuery removeToCartQuery) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        cartService.removeProduct(user.getUserId(), removeToCartQuery);
        return new ResponseResult("操作成功", 200);
    }

    @DeleteMapping("clear")
    public ResponseResult clear(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("user");
        cartService.clear(user.getUserId());
        return new ResponseResult("操作成功", 200);
    }
}
