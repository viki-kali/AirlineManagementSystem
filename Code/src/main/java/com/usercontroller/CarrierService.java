package com.usercontroller;

import java.util.List;

import com.userdao.CarrierDao;
import com.model.CarrierDetails;

public class CarrierService {
	CarrierDao carrierDao = new CarrierDao();
	public List<CarrierDetails> getCarriers(){
		return carrierDao.getCarriers();
	}
	public CarrierDetails getCarrier(int carrierId) {
		return carrierDao.getCarrier(carrierId);
	}
	public boolean addCarrier(CarrierDetails cr) {
		return carrierDao.addCarrier(cr);
	}
	public boolean updateCarrier(CarrierDetails cr) {
		return carrierDao.updateCarrier(cr);
	}
	public boolean deleteCarrier(int carrierId) {
		return carrierDao.deleteCarrier(carrierId);
	}
}
