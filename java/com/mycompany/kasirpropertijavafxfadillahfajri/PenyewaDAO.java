/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import java.sql.*;
import java.util.*;

public class PenyewaDAO {
    public static List<Penyewa> getAllPenyewa() {
        List<Penyewa> list = new ArrayList<>();
        String sql = "SELECT * FROM penyewa";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Penyewa p = new Penyewa(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getInt("user_id")
                );
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static Penyewa getByUserId(int userId) {
        String sql = "SELECT * FROM penyewa WHERE user_id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Penyewa(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getInt("user_id")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static boolean insertPenyewa(Penyewa p) {
        String sql = "INSERT INTO penyewa (nama, no_hp, alamat, user_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, p.getNama());
            stmt.setString(2, p.getNoHp());
            stmt.setString(3, p.getAlamat());
            stmt.setInt(4, p.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean updatePenyewa(Penyewa p) {
        String sql = "UPDATE penyewa SET nama=?, no_hp=?, alamat=? WHERE user_id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, p.getNama());
            stmt.setString(2, p.getNoHp());
            stmt.setString(3, p.getAlamat());
            stmt.setInt(4, p.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean deletePenyewa(int id) {
        String sql = "DELETE FROM penyewa WHERE id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
