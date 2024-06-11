package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Peminjaman {
    private int idPeminjaman;
    private int idAlat;
    private String namaPeminjam;
    private String npmPeminjam;
    private Date tanggalPinjam;
    private Date tanggalKembali;

    public Peminjaman(int idPeminjaman, int idAlat, String namaPeminjam, String npmPeminjam, Date tanggalPinjam,
        Date tanggalKembali) {
      this.idPeminjaman = idPeminjaman;
      this.idAlat = idAlat;
      this.namaPeminjam = namaPeminjam;
      this.npmPeminjam = npmPeminjam;
      this.tanggalPinjam = tanggalPinjam;
      this.tanggalKembali = tanggalKembali;
    }
    

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public int getIdAlat() {
        return idAlat;
    }

    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public String getNpmPeminjam() {
        return npmPeminjam;
    }

    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    // Fungsi untuk menyimpan peminjaman ke database
    public void simpanKeDatabase() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO peminjaman (id_alat, nama_peminjam, npm_peminjam, tanggal_pinjam, tanggal_kembali) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, idAlat);
        stmt.setString(2, namaPeminjam);
        stmt.setString(3, npmPeminjam);
        stmt.setDate(4, tanggalPinjam);
        stmt.setDate(5, tanggalKembali);
        stmt.executeUpdate();
        conn.close();
    }

    // Fungsi untuk mengupdate peminjaman di database
    public void updateDiDatabase() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE peminjaman SET id_alat=?, nama_peminjam=?, npm_peminjam=?, tanggal_pinjam=?, tanggal_kembali=? WHERE id_peminjaman=?");
        stmt.setInt(1, idAlat);
        stmt.setString(2, namaPeminjam);
        stmt.setString(3, npmPeminjam);
        stmt.setDate(4, tanggalPinjam);
        stmt.setDate(5, tanggalKembali);
        stmt.setInt(6, idPeminjaman);
        stmt.executeUpdate();
        conn.close();
    }

    // Fungsi untuk menghapus peminjaman dari database
    public static void hapusDariDatabase(int idPeminjaman) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM peminjaman WHERE id_peminjaman=?");
        stmt.setInt(1, idPeminjaman);
        stmt.executeUpdate();
        conn.close();
    }

    // Fungsi untuk mengambil semua peminjaman dari database
    public static List<Peminjaman> getAllFromDatabase() throws SQLException {
      List<Peminjaman> peminjamanList = new ArrayList<>();
      Connection conn = DatabaseConnection.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM peminjaman");
      while (rs.next()) {
        Peminjaman peminjaman = new Peminjaman(
            rs.getInt("id_peminjaman"),
            rs.getInt("id_alat"),
            rs.getString("nama_peminjam"),
            rs.getString("npm_peminjam"),
            rs.getDate("tanggal_pinjam"),
            rs.getDate("tanggal_kembali"));
        peminjamanList.add(peminjaman);
      }
      conn.close();
      return peminjamanList;
    }

    public static List<Peminjaman> searchFromDatabase(String keyword) throws SQLException {
        List<Peminjaman> peminjamanList = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM peminjaman WHERE nama_peminjam LIKE ? OR npm_peminjam LIKE ?");
        stmt.setString(1, "%" + keyword + "%");
        stmt.setString(2, "%" + keyword + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Peminjaman peminjaman = new Peminjaman(
                rs.getInt("id_peminjaman"),
                rs.getInt("id_alat"),
                rs.getString("nama_peminjam"),
                rs.getString("npm_peminjam"),
                rs.getDate("tanggal_pinjam"),
                rs.getDate("tanggal_kembali")
            );
            peminjamanList.add(peminjaman);
        }
        conn.close();
        return peminjamanList;
    }
}
