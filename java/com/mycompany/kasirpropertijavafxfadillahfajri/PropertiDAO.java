/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import java.sql.*;
import java.util.*;

public class PropertiDAO {
    public static List<Properti> getAllProperti() {
        List<Properti> list = new ArrayList<>();
        String sql = "SELECT * FROM properti";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Properti p = new Properti(
                    rs.getInt("id"),
                    rs.getString("jenis"),
                    rs.getString("alamat"),
                    rs.getInt("jumlah_kamar"),
                    rs.getDouble("harga_sewa"),
                    rs.getDouble("pajak"),
                    rs.getString("status")
                );
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static boolean insertProperti(Properti p) {
        String sql = "INSERT INTO properti (jenis, alamat, jumlah_kamar, harga_sewa, pajak, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, p.getJenis());
            stmt.setString(2, p.getAlamat());
            stmt.setInt(3, p.getJumlahKamar());
            stmt.setDouble(4, p.getHargaSewa());
            stmt.setDouble(5, p.getPajak());
            stmt.setString(6, p.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean updateProperti(Properti p) {
        String sql = "UPDATE properti SET jenis=?, alamat=?, jumlah_kamar=?, harga_sewa=?, pajak=?, status=? WHERE id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, p.getJenis());
            stmt.setString(2, p.getAlamat());
            stmt.setInt(3, p.getJumlahKamar());
            stmt.setDouble(4, p.getHargaSewa());
            stmt.setDouble(5, p.getPajak());
            stmt.setString(6, p.getStatus());
            stmt.setInt(7, p.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean deleteProperti(int id) {
        String sql = "DELETE FROM properti WHERE id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}