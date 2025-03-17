package com.test;

import com.controller.*;
import com.model.FlightSchedule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

public class adminFlightScheduleControllerTest {

    private FlightScheduleController controller;
    private FlightSchedule testSchedule;

    @Before
    public void setUp() {
        controller = new FlightScheduleController();
        testSchedule = new FlightSchedule(0, 4006, Date.valueOf("2025-03-15"), 10, 20, 5);
        controller.addFlightSchedule(testSchedule);
    }

    @After
    public void tearDown() {
        controller.deleteFlightSchedule(testSchedule.getFlightScheduleID());
    }

    @Test
    public void testGetFlightSchedule() {
        FlightSchedule retrieved = controller.getFlightSchedule(testSchedule.getFlightScheduleID());
        assertNotNull(retrieved);
        assertEquals(testSchedule.getFlightID(), retrieved.getFlightID());
    }

    @Test
    public void testGetAllFlightSchedules() {
        List<FlightSchedule> schedules = controller.getAllFlightSchedules();
        assertNotNull(schedules);
        assertFalse(schedules.isEmpty());
    }

    @Test
    public void testUpdateFlightSchedule() {
        testSchedule.setBusinessClassBookedCount(15);
        boolean updated = controller.updateFlightSchedule(testSchedule);
        assertTrue(updated);
        FlightSchedule updatedSchedule = controller.getFlightSchedule(testSchedule.getFlightScheduleID());
        assertEquals(15, updatedSchedule.getBusinessClassBookedCount());
    }

    @Test
    public void testDeleteFlightSchedule() {
        boolean deleted = controller.deleteFlightSchedule(testSchedule.getFlightScheduleID());
        assertTrue(deleted);
        assertNull(controller.getFlightSchedule(testSchedule.getFlightScheduleID()));
    }
}
