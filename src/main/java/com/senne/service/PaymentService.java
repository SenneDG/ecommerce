package com.senne.service;

import java.util.Set;

import com.senne.modal.Order;
import com.senne.modal.PaymentOrder;
import com.senne.modal.User;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;
    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId);
    String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}
