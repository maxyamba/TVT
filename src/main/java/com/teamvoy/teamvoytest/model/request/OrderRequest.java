package com.teamvoy.teamvoytest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Map<String, String> iPhoneIdQuantityMap;
    private String name;
}
