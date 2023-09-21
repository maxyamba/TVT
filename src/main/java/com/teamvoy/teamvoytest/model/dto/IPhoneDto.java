package com.teamvoy.teamvoytest.model.dto;

import com.teamvoy.teamvoytest.model.IPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPhoneDto implements Serializable {
    private Integer id;
    private Integer quantity;
    private BigDecimal price;
    private String name;

    public IPhoneDto(IPhone iPhone) {
        id = iPhone.getId();
        quantity = iPhone.getQuantity();
        price = iPhone.getPrice();
        name = iPhone.getName();
    }
}
