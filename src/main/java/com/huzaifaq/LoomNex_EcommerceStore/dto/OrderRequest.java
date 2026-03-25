package com.huzaifaq.LoomNex_EcommerceStore.dto;

import java.util.Map;

public class OrderRequest {
    private Map<Long,Integer> productQuantities;

    public OrderRequest() {}

    public OrderRequest(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public Map<Long, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }
}
