package com.usertest;

import com.usercontroller.FlightService;
import com.model.FlightDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FlightServiceTest {

    private FlightService flightService;
    private FlightDetails testFlight;

    @Before
    public void setUp() {
        flightService = new FlightService();
        testFlight = new FlightDetails(0, 3001, "New York", "Los Angeles",3000,120,30,20);
    }

    @After
    public void tearDown() {
        testFlight = null;
    }

    @Test
    public void testGetFlights() {
        List<FlightDetails> flights = flightService.getFlights();
//        System.out.println(flights);
        assertNotNull(flights);
//        assertTrue(flights instanceof ArrayList);
    }

    @Test
    public void testGetFlight() {
        FlightDetails retrieved = flightService.getFlight(4001);
//        System.out.println(retrieved);
        assertNotNull(retrieved); // Since no actual DB connection exists
    }

}
