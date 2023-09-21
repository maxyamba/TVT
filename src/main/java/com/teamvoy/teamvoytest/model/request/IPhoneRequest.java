package com.teamvoy.teamvoytest.model.request;

import com.teamvoy.teamvoytest.model.IPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPhoneRequest {
    private Integer quantity;
    private BigDecimal price;
    private String name;

    public IPhone toIPhone(){
        IPhone iPhone = new IPhone();
        iPhone.setName(name);
        iPhone.setPrice(price);
        iPhone.setQuantity(quantity);

        return iPhone;
    }
}
