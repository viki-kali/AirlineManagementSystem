package com.test;

import com.controller.*;
//import com.dao.*;
import com.model.CarrierDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class adminCarrierControllerTest {

    private CarrierController carrierController;

    @Before
    public void setUp() {
        carrierController = new CarrierController();
    }

    @After
    public void tearDown() {
        carrierController = null;
    }

    @Test
    public void testAddAndGetCarrier() {
        CarrierDetails carrier = new CarrierDetails(0, "Test Carrier", 10, 15, 20, 5, 50, 60, 70, 5, 10, 15);
        carrierController.addCarrier(carrier);
        
        CarrierDetails retrievedCarrier = carrierController.getCarrier(carrier.getCarrierId());
        assertNotNull("Carrier should not be null", retrievedCarrier);
        assertEquals("Carrier name should match", "Test Carrier", retrievedCarrier.getCarrierName());
    }

    @Test
    public void testGetAllCarriers() {
        List<CarrierDetails> carriers = carrierController.getAllCarriers();
        assertNotNull("Carrier list should not be null", carriers);
        assertTrue("Carrier list size should be at least 1", carriers.size() > 0);
    }

    @Test
    public void testUpdateCarrier() {
        CarrierDetails carrier = new CarrierDetails(3002, "Updated Carrier", 12, 18, 25, 8, 55, 65, 75, 6, 12, 18);
        boolean updated = carrierController.updateCarrier(carrier);
        assertTrue("Carrier should be updated successfully", updated);

        CarrierDetails updatedCarrier = carrierController.getCarrier(3114);
        assertNotNull(updatedCarrier);
    }

    @Test
    public void testDeleteCarrier() {
        boolean deleted = carrierController.deleteCarrier(3003);
        assertTrue("Carrier should be deleted successfully", deleted);

        CarrierDetails carrier = carrierController.getCarrier(3003);
        assertNull(carrier);
    }
}
