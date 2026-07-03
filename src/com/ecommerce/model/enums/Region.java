package com.ecommerce.model.enums;

public enum Region {
    DOMESTIC(1.0),
    INTERNATIONAL(2.5);

    private final double baseShippingMultiplier;

    Region(double baseShippingMultiplier) {
        this.baseShippingMultiplier = baseShippingMultiplier;
    }

    public double getBaseShippingMultiplier() {
        return baseShippingMultiplier;
    }
}
