package com.website.loveconnect.controller.user;

import com.website.loveconnect.config.VnpayConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.website.loveconnect.dto.response.ApiResponse;
import com.website.loveconnect.dto.response.PaymentResponse;
import com.website.loveconnect.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${spring.application.api-prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final VnpayConfig vnpayConfig;

    private final PaymentService paymentService;

    @GetMapping(value = "/vn-pay")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPay(HttpServletRequest request) {
        PaymentResponse response = paymentService.createVnPayPayment(request);
        return ResponseEntity.ok(new ApiResponse<>(true,"Create payment successful",response));
    }

    @GetMapping(value = "/vn-pay-callback")
    public ResponseEntity<ApiResponse<PaymentResponse>>  payCallback(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if ("00".equals(status)) {
            return ResponseEntity.ok(new ApiResponse<>(true,"Callback successful",
                    PaymentResponse.builder()
                    .code("00")
                    .message("Success")
                    .paymentUrl("")
                    .build()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(true,"Callback successful",
                    PaymentResponse.builder()
                            .code(status)
                            .message("Failed")
                            .paymentUrl("")
                            .build()));

        }
    }


}