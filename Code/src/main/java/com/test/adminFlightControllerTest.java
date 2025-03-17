package com.test;
import com.controller.FlightController;
import com.model.FlightDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class adminFlightControllerTest {
    private FlightController flightController;
    private FlightDetails testFlight;

    @Before
    public void setUp() {
        flightController = new FlightController();
        testFlight = new FlightDetails(
            0, // FlightID (auto-generated)
            3008, // CarrierID
            "New York", // Origin
            "Los Angeles", // Destination
            350.0, // Airfare
            150, // SeatCapacityEconomyClass
            50, // SeatCapacityBusinessClass
            20 // SeatCapacityExecutiveClass
        );
        flightController.addFlight(testFlight);
    }

    @Test
    public void testGetFlight() {
        FlightDetails flight = flightController.getFlight(testFlight.getFlightID());
        assertNotNull("Flight should not be null", flight);
        assertEquals("Flight ID should match", testFlight.getFlightID(), flight.getFlightID());
        assertEquals("Origin should match", testFlight.getOrigin(), flight.getOrigin());
    }

    @Test
    public void testUpdateFlight() {
        testFlight.setAirfare(400.0);
        boolean isUpdated = flightController.updateFlight(testFlight);
        assertEquals("Flight should be updated successfully", true, isUpdated);

        FlightDetails updatedFlight = flightController.getFlight(testFlight.getFlightID());
        assertNotNull("Updated flight should not be null", updatedFlight);
        assertEquals("Airfare should be updated", 400.0, updatedFlight.getAirfare(), 0.001);
    }

    @Test
    public void testGetAllFlights() {
        List<FlightDetails> flights = flightController.getAllFlights();
        assertNotNull("Flights list should not be null", flights);
        assertFalse("Flights list should not be empty", flights.isEmpty());
    }

    @Test
    public void testDeleteFlight() {
        boolean isDeleted = flightController.deleteFlight(testFlight.getFlightID());
        assertEquals("Flight should be deleted successfully", true, isDeleted);

        FlightDetails deletedFlight = flightController.getFlight(testFlight.getFlightID());
        assertNull("Deleted flight should be null", deletedFlight);
    }

    @After
    public void tearDown() {
        flightController.deleteFlight(testFlight.getFlightID());
    }
}
