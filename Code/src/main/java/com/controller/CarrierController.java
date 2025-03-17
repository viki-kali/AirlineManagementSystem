package com.controller;

import com.dao.CarrierDAO;
import com.model.CarrierDetails;
import java.sql.SQLException;
import java.util.List;

public class CarrierController {

    private CarrierDAO carrierDAO;

    public CarrierController() {
        this.carrierDAO = new CarrierDAO();
    }

    // Add a new carrier.
    public void addCarrier(CarrierDetails carrier) {
        try {
            carrierDAO.createCarrier(carrier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a carrier by its ID.
    public CarrierDetails getCarrier(int carrierId) {
        try {
            return carrierDAO.getCarrierById(carrierId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all carriers.
    public List<CarrierDetails> getAllCarriers() {
        try {
            return carrierDAO.getAllCarriers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update carrier details.
    public boolean updateCarrier(CarrierDetails carrier) {
        try {
            return carrierDAO.updateCarrier(carrier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a carrier by its ID.
    public boolean deleteCarrier(int carrierId) {
        try {
            return carrierDAO.deleteCarrier(carrierId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
