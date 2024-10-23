package com.senne.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senne.modal.Order;
import com.senne.modal.PaymentOrder;
import com.senne.modal.Seller;
import com.senne.modal.SellerReport;
import com.senne.response.ApiResponse;
import com.senne.service.PaymentService;
import com.senne.service.SellerReportService;
import com.senne.service.SellerService;
import com.senne.service.TransactionService;
import com.senne.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(
        @PathVariable String paymentId,
        @RequestParam String paymentLinkId,
        @RequestHeader("Authorization") String jwt
    ) throws Exception {
        userService.findUserByJwtToken(jwt);

        // PaymentLinkResponse paymentResponse;

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.ProceedPaymentOrder(paymentOrder, paymentId, paymentLinkId);

        if(paymentSuccess) {
            for(Order order : paymentOrder.getOrders()) {
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarnings(report.getTotalEarnings() + order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales() + order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);
            }
        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Payment Success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
