package com.teamvoy.teamvoytest;

import com.teamvoy.teamvoytest.model.IPhone;
import com.teamvoy.teamvoytest.model.dto.IPhoneDto;
import com.teamvoy.teamvoytest.model.request.IPhoneRequest;
import com.teamvoy.teamvoytest.repository.IPhoneRepository;
import com.teamvoy.teamvoytest.service.impl.IPhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IPhoneTest {

    @InjectMocks
    private IPhoneServiceImpl iPhoneService;

    @Mock
    private IPhoneRepository iPhoneRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAndAddToStore() {
        IPhoneRequest request = new IPhoneRequest();
        request.setName("iPhone 12");
        request.setPrice(new BigDecimal("999.99"));
        request.setQuantity(10);

        IPhone iPhone = new IPhone();
        iPhone.setId(1);
        iPhone.setName(request.getName());
        iPhone.setPrice(request.getPrice());
        iPhone.setQuantity(request.getQuantity());

        when(iPhoneRepository.save(any(IPhone.class))).thenReturn(iPhone);

        IPhoneDto result = iPhoneService.createAndAddToStore(request);

        verify(iPhoneRepository, times(1)).save(any(IPhone.class));

        assertEquals(iPhone.getId(), result.getId());
        assertEquals(iPhone.getName(), result.getName());
        assertEquals(iPhone.getPrice(), result.getPrice());
        assertEquals(iPhone.getQuantity(), result.getQuantity());
    }

    @Test
    public void testShowAllIPhones() {
        List<IPhone> iPhoneList = Arrays.asList(
                createSampleIPhone(1, "iPhone 11", new BigDecimal("799.99"), 5),
                createSampleIPhone(2, "iPhone SE", new BigDecimal("399.99"), 10),
                createSampleIPhone(3, "iPhone XR", new BigDecimal("599.99"), 3)
        );

        when(iPhoneRepository.findAll()).thenReturn(iPhoneList);

        List<IPhoneDto> resultList = iPhoneService.showAllIPhones();

        verify(iPhoneRepository, times(1)).findAll();

        assertEquals(iPhoneList.size(), resultList.size());

        for (int i = 0; i < iPhoneList.size(); i++) {
            IPhoneDto result = resultList.get(i);
            IPhone iPhone = iPhoneList.get(i);

            assertEquals(iPhone.getId(), result.getId());
            assertEquals(iPhone.getName(), result.getName());
            assertEquals(iPhone.getPrice(), result.getPrice());
            assertEquals(iPhone.getQuantity(), result.getQuantity());
        }
    }

    private IPhone createSampleIPhone(int id, String name, BigDecimal price, int quantity) {
        IPhone iPhone = new IPhone();
        iPhone.setId(id);
        iPhone.setName(name);
        iPhone.setPrice(price);
        iPhone.setQuantity(quantity);
        return iPhone;
    }
}