package com.usertest;

import com.usercontroller.FlightScheduleService;
import com.model.FlightSchedule;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class FlightScheduleServiceTest {

    private FlightScheduleService flightScheduleService = new FlightScheduleService();
//    private FlightSchedule testSchedule;

//    @Before
//    public void setUp() {
//        flightScheduleService = new FlightScheduleService();
//        testSchedule = new FlightSchedule(101, "AI123", "New York", "Los Angeles", "2025-04-15 10:00:00");
//    }
//
//    @After
//    public void tearDown() {
//        testSchedule = null;
//    }

   

    @Test
    public void testGetScheduledFlight() {
        FlightSchedule retrieved = flightScheduleService.getScheduledFlight(5001);
        assertNotNull(retrieved); 
    }


}
