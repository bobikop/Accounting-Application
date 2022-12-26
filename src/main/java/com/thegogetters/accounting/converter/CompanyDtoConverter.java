package com.thegogetters.accounting.converter;

import com.thegogetters.accounting.dto.CompanyDto;
import org.springframework.core.convert.converter.Converter;

public class CompanyDtoConverter implements Converter<String, CompanyDto> {
    @Override
    public CompanyDto convert(String source) {
        return null;
    }
}
