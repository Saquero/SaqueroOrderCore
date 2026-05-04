package com.saquero.ordercore.application.dto;

import java.util.Map;

public class OrderSummaryResponse {

    private final long total;
    private final Map<String, Long> byStatus;

    public OrderSummaryResponse(long total, Map<String, Long> byStatus) {
        this.total = total;
        this.byStatus = byStatus;
    }

    public long getTotal() { return total; }
    public Map<String, Long> getByStatus() { return byStatus; }
}