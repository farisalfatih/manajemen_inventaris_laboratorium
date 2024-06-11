package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlatLaboratorium {
    private int id;
    private String nama;
    private String deskripsi;
    private int stok;

    public AlatLaboratorium(int id, String nama, String deskripsi, int stok) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.stok = stok;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getStok() {
        return stok;
    }

    // Fungsi untuk menyimpan alat ke database
    public void simpanKeDatabase() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO alat_laboratorium (nama, deskripsi, stok) VALUES (?, ?, ?)");
        stmt.setString(1, nama);
        stmt.setString(2, deskripsi);
        stmt.setInt(3, stok);
        stmt.executeUpdate();
        conn.close();
    }

    // Fungsi untuk mengupdate alat di database
    public void updateDiDatabase() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE alat_laboratorium SET nama=?, deskripsi=?, stok=? WHERE id=?");
        stmt.setString(1, nama);
        stmt.setString(2, deskripsi);
        stmt.setInt(3, stok);
        stmt.setInt(4, id);
        stmt.executeUpdate();
        conn.close();
    }

    // Fungsi untuk menghapus alat dari database
    public static void hapusDariDatabase(int id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM alat_laboratorium WHERE id=?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        conn.close();
    }

    // Fungsi untuk mengambil semua alat dari database
    public static List<AlatLaboratorium> getAllFromDatabase() throws SQLException {
      List<AlatLaboratorium> alatList = new ArrayList<>();
      Connection conn = DatabaseConnection.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM alat_laboratorium");
      while (rs.next()) {
        AlatLaboratorium alat = new AlatLaboratorium(
            rs.getInt("id"),
            rs.getString("nama"),
            rs.getString("deskripsi"),
            rs.getInt("stok"));
        alatList.add(alat);
      }
      conn.close();
      return alatList;
    }
    
    // Fungsi untuk mencari alat dari database
    public static List<AlatLaboratorium> searchFromDatabase(String keyword) throws SQLException {
      List<AlatLaboratorium> alatList = new ArrayList<>();
      Connection conn = DatabaseConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM alat_laboratorium WHERE nama LIKE ? OR deskripsi LIKE ?");
      stmt.setString(1, "%" + keyword + "%");
      stmt.setString(2, "%" + keyword + "%");
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
          AlatLaboratorium alat = new AlatLaboratorium(
              rs.getInt("id"),
              rs.getString("nama"),
              rs.getString("deskripsi"),
              rs.getInt("stok")
          );
          alatList.add(alat);
      }
      conn.close();
      return alatList;
  }
}
