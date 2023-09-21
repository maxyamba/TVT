package com.teamvoy.teamvoytest.service.impl;

import com.teamvoy.teamvoytest.model.IPhone;
import com.teamvoy.teamvoytest.model.dto.IPhoneDto;
import com.teamvoy.teamvoytest.model.request.IPhoneRequest;
import com.teamvoy.teamvoytest.repository.IPhoneRepository;
import com.teamvoy.teamvoytest.service.IPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IPhoneServiceImpl implements IPhoneService {

    private final IPhoneRepository iPhoneRepository;

    @Override
    public IPhoneDto createAndAddToStore(IPhoneRequest request) {
        IPhone iPhone = request.toIPhone();
        return new IPhoneDto(iPhoneRepository.save(iPhone));
    }

    @Override
    public List<IPhoneDto> showAllIPhones() {
        return iPhoneRepository.findAll().stream().map(IPhoneDto::new).collect(Collectors.toList());
    }
}
