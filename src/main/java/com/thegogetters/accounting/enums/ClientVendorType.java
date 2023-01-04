package com.thegogetters.accounting.enums;

import javax.validation.constraints.NotNull;

public enum ClientVendorType {

   CLIENT("Client") , VENDOR("Vendor");

   @NotNull
   private String value;

    ClientVendorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
