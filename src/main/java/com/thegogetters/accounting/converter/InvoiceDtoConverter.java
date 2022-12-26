package com.thegogetters.accounting.converter;

import com.thegogetters.accounting.dto.InvoiceDTO;
import org.springframework.core.convert.converter.Converter;

public class InvoiceDtoConverter implements Converter<String, InvoiceDTO> {

    @Override
    public InvoiceDTO convert(String source) {
        return null;
    }
}
