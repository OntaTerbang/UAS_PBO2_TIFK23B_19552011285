CREATE DATABASE IF NOT EXISTS kasir_properti;
USE kasir_properti;


CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'member') NOT NULL
);
CREATE TABLE IF NOT EXISTS penyewa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    no_hp VARCHAR(20) NOT NULL,
    alamat VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS properti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jenis VARCHAR(50) NOT NULL, 
    alamat VARCHAR(255) NOT NULL,
    jumlah_kamar INT NOT NULL,
    harga_sewa DOUBLE NOT NULL,
    pajak DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL 
);
CREATE TABLE IF NOT EXISTS transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    properti VARCHAR(50) NOT NULL, 
    penyewa VARCHAR(100) NOT NULL, 
    tanggal DATE NOT NULL,
    lama_tinggal INT NOT NULL,
    total DOUBLE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
