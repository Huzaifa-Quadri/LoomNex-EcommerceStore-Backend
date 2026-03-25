package com.huzaifaq.LoomNex_EcommerceStore.Model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    @Converter(autoApply = true)
    public static class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
        @Override
        public String convertToDatabaseColumn(OrderStatus status) {
            if (status == null) return null;
            return status.name();
        }

        @Override
        public OrderStatus convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Stream.of(OrderStatus.values())
                .filter(c -> c.name().equalsIgnoreCase(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dbData));
        }
    }
}
