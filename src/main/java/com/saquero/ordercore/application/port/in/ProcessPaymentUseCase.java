package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.command.ProcessPaymentCommand;
import com.saquero.ordercore.application.dto.PaymentResponse;

public interface ProcessPaymentUseCase {
    PaymentResponse execute(ProcessPaymentCommand command);
}