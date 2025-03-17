package com.usertest;

import com.usercontroller.*;
import com.model.CarrierDetails;
import com.userdao.CarrierDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class CarrierServiceTest {
    private CarrierService carrierService;
    private CarrierDetails testCarrier;
    private int testCarrierId;

    @Before
    public void setUp() {
        carrierService = new CarrierService();
        testCarrier = new CarrierDetails(0, "TestCarrier", 10, 15, 20, 5, 50, 60, 70, 5, 10, 15);
        boolean added = carrierService.addCarrier(testCarrier);
        assertTrue("Carrier should be added successfully", added);
        List<CarrierDetails> carriers = carrierService.getCarriers();
        testCarrierId = carriers.get(carriers.size() - 1).getCarrierId();
    }

    @Test
    public void testGetCarrier() {
        CarrierDetails carrier = carrierService.getCarrier(testCarrierId);
        assertNotNull("Carrier should not be null", carrier);
        assertEquals("Carrier name should match", "TestCarrier", carrier.getCarrierName());
    }

    @After
    public void tearDown() {
        carrierService.deleteCarrier(testCarrierId);
    }
}
