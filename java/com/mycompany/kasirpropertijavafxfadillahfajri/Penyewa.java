/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

/**
 *
 * @author Fadil
 */
public class Penyewa {
    private int id;
    private String nama;
    private String noHp;
    private String alamat;
    private int userId;

    public Penyewa(int id, String nama, String noHp, String alamat, int userId) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
        this.userId = userId;
    }

    public Penyewa(String nama, String noHp, String alamat, int userId) {
        this(0, nama, noHp, alamat, userId);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
