# Final Proyek Pemrograman Berorientasi Obyek 2 & Sistem Basis Data

- **Mata Kuliah:** Sistem Basis Data
- **Dosen Pengampu:** Muhammad Reksa Ariansyah, M.Kom.

- **Mata Kuliah:** Pemrograman Berorientasi Obyek 2  
- **Dosen Pengampu:** [Muhammad Ikhwan Fathulloh](https://github.com/)


## Profil

- **Nama:** Fadillah Fajri
- **NIM:** 19552011285
- **Studi Kasus:** 5

## Judul Studi Kasus

Kasir Properti

## Penjelasan Studi Kasus

Aplikasi ini digunakan untuk mengelola data properti, penyewa, dan transaksi sewa properti. Fitur utama meliputi:
- Manajemen data properti (rumah, apartemen)
- Manajemen data penyewa
- Pencatatan transaksi sewa properti
- Cetak bukti transaksi
- Hak akses berdasarkan role (admin/member)

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance (Pewarisan)
Inheritance terjadi saat sebuah class mewarisi atribut dan method dari class lain. Pada proyek ini, meskipun tidak ada pewarisan eksplisit antar entitas utama, namun konsep inheritance dapat dijelaskan melalui penggunaan class turunan dari JavaFX dan struktur DAO yang bisa dikembangkan lebih lanjut.

```java
public class KelolaTransaksiController {
   @FXML private ComboBox<String> jenisCombo;
    @FXML private TableView<Properti> propertiTable;
    @FXML private TableColumn<Properti, Integer> colId, colJumlahKamar;
    @FXML private TableColumn<Properti, String> colJenis, colAlamat, colStatus;
    @FXML private TableColumn<Properti, Double> colHargaSewa, colPajak;
    @FXML private Label penyewaLabel, totalLabel;
    @FXML private TextField lamaTinggalField;
    @FXML private Button tambahButton, hapusButton, resetButton, kembaliButton;
    @FXML private TableView<Transaksi> transaksiTable;
    @FXML private TableColumn<Transaksi, Integer> colTransId, colTransLama;
    @FXML private TableColumn<Transaksi, String> colTransProperti, colTransAlamat, colTransPenyewa;
    @FXML private TableColumn<Transaksi, LocalDate> colTransTanggal;
    @FXML private TableColumn<Transaksi, Double> colTransTotal;
    @FXML private TextField penyewaField, totalField;

    private Properti selectedProperti;
    private Transaksi selectedTransaksi;

    // Mewarisi method-method dari JavaFX Controller
}
```

### 2. Encapsulation (Enkapsulasi)
Encapsulation adalah membungkus data (atribut) dan method dalam satu class, serta membatasi akses langsung ke data dengan modifier seperti `private`, dan menyediakan akses melalui getter/setter.

```java
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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    // ... getter dan setter lainnya
}
```

### 3. Polymorphism (Polimorfisme)
Polymorphism adalah kemampuan objek untuk memiliki banyak bentuk, misal method override. Pada proyek ini, polimorfisme terlihat pada penggunaan custom cell factory di TableView JavaFX.

```java
colTransTotal.setCellFactory(tc -> new TableCell<Transaksi, Double>() {
    @Override
    protected void updateItem(Double value, boolean empty) {
        super.updateItem(value, empty);
        if (empty || value == null) {
            setText(null);
        } else {
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            setText(nf.format(value));
        }
    }
});
```

### 4. Abstraction (Abstraksi)
Abstraction adalah menyembunyikan detail implementasi dan hanya menampilkan fungsionalitas penting. Pada proyek ini, class DAO mengabstraksi akses database dari logika aplikasi.

```java
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
    // ... method insert, delete, dll
}
```

---

## Penjelasan CRUD dalam Studi Kasus

CRUD adalah singkatan dari Create, Read, Update, Delete, yaitu operasi dasar dalam pengelolaan data.

- **Create**: Menambah data baru ke database.
    ```java
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
    ```

- **Read**: Mengambil data dari database.
    ```java
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
    ```

- **Update**: Mengubah data yang sudah ada.
    ```java
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
    ```

- **Delete**: Menghapus data dari database.
    ```java
    public static boolean deletePenyewa(int id) {
        String sql = "DELETE FROM penyewa WHERE id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    ```
    
    ## Demo Proyek
<ul>
  <li>Youtube: <a href="">Youtube</a></li>
</ul>
