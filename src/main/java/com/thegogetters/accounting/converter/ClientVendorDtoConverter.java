package com.thegogetters.accounting.converter;

import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.service.ClientVendorService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClientVendorDtoConverter implements Converter<String, ClientVendorDto> {

    private final ClientVendorService clientVendorService;

    public ClientVendorDtoConverter(@Lazy ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @Override
    public ClientVendorDto convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return clientVendorService.findById(Long.parseLong(source));
    }


}
