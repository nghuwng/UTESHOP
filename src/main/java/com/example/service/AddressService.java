package com.example.service;

import com.example.dao.AddressDAO;
import com.example.model.Address;

import java.sql.SQLException;
import java.util.List;

public class AddressService {

    private AddressDAO addressDAO;

    public AddressService() {
        addressDAO = new AddressDAO();
    }

    // Lấy tất cả địa chỉ của user
    public List<Address> getAddressesByUserId(int userId) throws SQLException {
        return addressDAO.getAddressesByUserId(userId);
    }

    // Thêm địa chỉ
    public boolean addAddress(Address address) throws SQLException {
        return addressDAO.addAddress(address);
    }

    // Sửa địa chỉ
    public boolean updateAddress(Address address) throws SQLException {
        return addressDAO.updateAddress(address);
    }

    // Xóa địa chỉ
    public boolean deleteAddress(int addressId) throws SQLException {
        return addressDAO.deleteAddress(addressId);
    }
    
    public Address getAddressById(int addressId) throws SQLException {
        return addressDAO.getAddressById(addressId);
    }
}
