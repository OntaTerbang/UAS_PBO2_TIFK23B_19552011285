/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

/**
 *
 * @author Fadil
 */
public class Properti {
    private int id;
    private String jenis;
    private String alamat;
    private int jumlahKamar;
    private double hargaSewa;
    private double pajak;
    private String status;

    public Properti(int id, String jenis, String alamat, int jumlahKamar, double hargaSewa, double pajak, String status) {
        this.id = id;
        this.jenis = jenis;
        this.alamat = alamat;
        this.jumlahKamar = jumlahKamar;
        this.hargaSewa = hargaSewa;
        this.pajak = pajak;
        this.status = status;
    }

    public Properti(String jenis, String alamat, int jumlahKamar, double hargaSewa, double pajak, String status) {
        this(0, jenis, alamat, jumlahKamar, hargaSewa, pajak, status);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public int getJumlahKamar() { return jumlahKamar; }
    public void setJumlahKamar(int jumlahKamar) { this.jumlahKamar = jumlahKamar; }
    public double getHargaSewa() { return hargaSewa; }
    public void setHargaSewa(double hargaSewa) { this.hargaSewa = hargaSewa; }
    public double getPajak() { return pajak; }
    public void setPajak(double pajak) { this.pajak = pajak; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}