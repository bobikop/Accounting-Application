package com.thegogetters.accounting.entity;

import com.thegogetters.accounting.enums.ClientVendorType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients_vendors")
public class ClientVendor extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientVendorName;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    @OneToOne
    private Address address;

    @ManyToOne
    private Company company;







}
