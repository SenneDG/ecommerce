package com.senne.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.senne.domain.PaymentOrderStatus;
import com.senne.modal.Order;
import com.senne.modal.PaymentOrder;
import com.senne.modal.User;
import com.senne.repository.OrderRepository;
import com.senne.repository.PaymentOrderRepository;
import com.senne.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    
    private final PaymentOrderRepository paymentOrderRepository;
    private final OrderRepository orderRepository;

    private String stripeSecretKey="stripeSecretKey";

    @Override
    public PaymentOrder createOrder(User user, Set<Order> orders) {
        Long amount = orders.stream().mapToLong(Order::getTotalSellingPrice).sum();

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setOrders(orders);

        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long orderId) throws Exception {
        return paymentOrderRepository.findById(orderId).orElseThrow(() -> 
            new Exception("Payment order not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(orderId);

        if (paymentOrder == null) {
            throw new Exception("Payment order not found with provided payment link id");
        }
        return paymentOrder;
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
            // I DIDNT IMPLEMENT THIS METHOD BECAUSE ONLY RAZORPAY WAS INTEGRATED
        }

        return false;
    }

    @Override
    public String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
            .setCancelUrl("http://localhost:3000/payment-cancel")
            .addLineItem(SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("usd")
                    .setUnitAmount(amount * 100)
                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Senne De Greef Payment")
                        .build())
                    .build())
                .build())
            .build();

        Session session = Session.create(params);
        return session.getUrl();
    }

}
