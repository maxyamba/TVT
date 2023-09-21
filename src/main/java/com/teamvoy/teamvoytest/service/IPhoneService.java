package com.teamvoy.teamvoytest.service;

import com.teamvoy.teamvoytest.model.dto.IPhoneDto;
import com.teamvoy.teamvoytest.model.request.IPhoneRequest;

import java.util.List;

public interface IPhoneService {
    IPhoneDto createAndAddToStore(IPhoneRequest request);
    List<IPhoneDto> showAllIPhones();
}
