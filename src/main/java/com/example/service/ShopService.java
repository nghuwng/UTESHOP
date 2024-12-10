package com.example.service;

import com.example.dao.ShopDAO;
import com.example.model.Shop;

import java.sql.SQLException;
import java.util.List;

public class ShopService {

    private ShopDAO shopDAO;

    public ShopService() {
        shopDAO = new ShopDAO();
    }

    // Lấy tất cả các shop
    public List<Shop> getAllShops() throws SQLException {
        return shopDAO.getAllShops();
    }

    // Thêm shop
    public boolean addShop(Shop newShop) throws SQLException {
        return shopDAO.addShop(newShop);
    }

    // Cập nhật shop
    public boolean updateShop(Shop shop) throws SQLException {
        return shopDAO.updateShop(shop);
    }

    // Xóa shop
    public boolean deleteShop(int shopId) throws SQLException {
        return shopDAO.deleteShop(shopId);
    }

    // Lấy shop theo ID
    public Shop getShopById(int id) throws SQLException {
        return shopDAO.getShopById(id);
    }
    
    public Shop getShopByUserId(int userId) throws SQLException {
        return shopDAO.getShopByUserId(userId);
    }
}
