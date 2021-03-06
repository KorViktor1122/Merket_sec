package com.v.korneev.controllers;


import com.v.korneev.entities.Order;
import com.v.korneev.entities.User;
import com.v.korneev.services.OrderService;
import com.v.korneev.services.UserService;
import com.v.korneev.utils.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private UserService userService;
    private OrderService orderService;
    private Cart cart;

    @GetMapping
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "orders";
    }

    @GetMapping("/create")
    public String showOrderPage(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "create_order";
    }

    @PostMapping("/confirm")
    @ResponseBody
    public String confirmOrder(Principal principal,
                              @RequestParam(name = "address") String address,
                              @RequestParam(name = "receiver_name") String receiverName,
                              @RequestParam(name = "phone_number") String phone
                              ) {
        User user = userService.findByUsername(principal.getName());
        Order order = new Order(user, cart, address);
        order = orderService.save(order);
        return "Ваш заказ #" + order.getId();
    }
}
