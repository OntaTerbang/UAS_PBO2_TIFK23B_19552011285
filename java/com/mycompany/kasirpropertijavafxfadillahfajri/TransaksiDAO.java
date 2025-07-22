/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public class TransaksiDAO {
    public static List<Transaksi> getAllTransaksi() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi";
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Transaksi t = new Transaksi(
                    rs.getInt("id"),
                    rs.getString("properti"),
                    rs.getString("penyewa"),
                    rs.getDate("tanggal").toLocalDate(),
                    rs.getInt("lama_tinggal"),
                    rs.getDouble("total"),
                    rs.getInt("user_id")
                );
                list.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static boolean insertTransaksi(Transaksi t) {
        String sql = "INSERT INTO transaksi (properti, penyewa, tanggal, lama_tinggal, total, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, t.getProperti());
            stmt.setString(2, t.getPenyewa());
            stmt.setDate(3, Date.valueOf(t.getTanggal()));
            stmt.setInt(4, t.getLamaTinggal());
            stmt.setDouble(5, t.getTotal());
            stmt.setInt(6, t.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean deleteTransaksi(int id) {
        String sql = "DELETE FROM transaksi WHERE id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}