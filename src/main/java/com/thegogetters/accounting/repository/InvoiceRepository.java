package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
