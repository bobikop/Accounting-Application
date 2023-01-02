package com.thegogetters.accounting.entity;

import com.thegogetters.accounting.enums.ClientVendorType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients_vendors")
@Where(clause = "is_deleted = false")
public class ClientVendor extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientVendorName;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne
    private Company company;







}
