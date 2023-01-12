package com.thegogetters.accounting.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.thegogetters.accounting.dto.PaymentDto;
import com.thegogetters.accounting.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @Value("${stripe.apikey}")
    private String stripeKey;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping({"/list", "/list/{year}"})
    public String list(@RequestParam(value = "year", required = false) String selectedYear, Model model) {

        int selectedYear1 = (selectedYear == null || selectedYear.isEmpty()) ? LocalDate.now().getYear() : Integer.parseInt(selectedYear);
        paymentService.createPaymentsIfNotExist(selectedYear1);
        model.addAttribute("payments",paymentService.getAllPaymentsByYear(selectedYear1));
        model.addAttribute("year", selectedYear1);
        return "payment/payment-list";
    }


    @GetMapping("/newpayment/{id}")
    public String pay(@PathVariable("id") Long id) throws StripeException {

        paymentService.getPaymentById(id);

        PaymentDto paymentDto = paymentService.updatePayment(id);


        return "redirect:/payments/charge/" + id;

    }


    @GetMapping("/charge/{id}")
    public String createCharge(@PathVariable("id") Long id,Model model) throws StripeException {

        Stripe.apiKey = stripeKey;

        String publishableKey = "pk_test_51MNXSGEYi8IZXYI6hysv6u3foVX5tlKl97s6IttUM8bmUtldxWjX2m5DzGmE8mzDUxWTMx3ibys1BZsMBWk3zKi700VpdRo3wO";
        model.addAttribute("stripePublicKey", publishableKey);
        model.addAttribute("modelId", id);

        Map<String, Object> params = new HashMap<>();
        params.put("name","Mary Grant");
        params.put("email","admin@greentech.com");
        Customer customer = Customer.create(params);

        Customer customer1 = Customer.retrieve("cus_N7ndBAc2cFCkHE");

        Map<String, Object> cardParams = new HashMap<>();

        cardParams.put("number","4242424242424242");
        cardParams.put("exp_month","11");
        cardParams.put("exp_year","2025");
        cardParams.put("cvc","123");


        Map<String, Object> tokenParam = new HashMap<>();
        tokenParam.put("card",cardParams);

        Token token = Token.create(tokenParam);



        /*
        Map<String, Object> sourceParam = new HashMap<>();
        sourceParam.put("source",token.getId());

        customer1.getSources().create(sourceParam);


         */



        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(customer1));

        return "payment/payment-method" ;
    }


    @PostMapping("/charge/{id}")
    public String payCharge(@PathVariable("id") Long id) throws StripeException {


        Stripe.apiKey = stripeKey;

        Map<String, Object> chargeParams = new HashMap<>();
        PaymentDto paymentDto = paymentService.getPaymentById(id);
        chargeParams.put("amount",paymentDto.getAmount() * 100);
        chargeParams.put("currency","usd");
        chargeParams.put("customer","cus_N7ndBAc2cFCkHE");

        Charge charge = Charge.create(chargeParams);



        return "redirect:/payments/list";

    }


}