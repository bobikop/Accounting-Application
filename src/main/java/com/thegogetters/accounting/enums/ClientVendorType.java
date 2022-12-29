package com.thegogetters.accounting.enums;

public enum ClientVendorType {

   CLIENT("Vendor") , VENDOR("Vendor");

    private String value;

    ClientVendorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
