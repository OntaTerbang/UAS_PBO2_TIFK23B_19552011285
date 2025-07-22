/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import java.time.LocalDate;

public class Transaksi {
    private int id;
    private String properti;
    private String penyewa;
    private LocalDate tanggal;
    private int lamaTinggal;
    private double total;
    private int userId;

    public Transaksi(int id, String properti, String penyewa, LocalDate tanggal, int lamaTinggal, double total, int userId) {
        this.id = id;
        this.properti = properti;
        this.penyewa = penyewa;
        this.tanggal = tanggal;
        this.lamaTinggal = lamaTinggal;
        this.total = total;
        this.userId = userId;
    }
    public Transaksi(String properti, String penyewa, LocalDate tanggal, int lamaTinggal, double total, int userId) {
        this(0, properti, penyewa, tanggal, lamaTinggal, total, userId);
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProperti() { return properti; }
    public void setProperti(String properti) { this.properti = properti; }
    public String getPenyewa() { return penyewa; }
    public void setPenyewa(String penyewa) { this.penyewa = penyewa; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public int getLamaTinggal() { return lamaTinggal; }
    public void setLamaTinggal(int lamaTinggal) { this.lamaTinggal = lamaTinggal; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}