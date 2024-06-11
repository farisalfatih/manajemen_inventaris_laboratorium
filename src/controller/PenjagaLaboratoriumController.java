package controller;

import model.AlatLaboratorium;
import model.DatabaseConnection;
import model.Peminjaman;
import model.PenjagaLaboratorium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PenjagaLaboratoriumController {

    // Metode untuk mendapatkan ID terbaru dari entitas AlatLaboratorium
    public int getLatestAlatID() {
      List<AlatLaboratorium> alatList = getAllAlat();
      if (alatList.isEmpty()) {
        return 1; // Jika tidak ada data, kembalikan ID awal
      } else {
        // Dapatkan ID terbaru dari daftar alat
        return alatList.get(alatList.size() - 1).getId();
      }
    }
    
        // Method to get the name of the borrower based on their ID
    public String getNamaPeminjam(int idPeminjam) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT nama_peminjam FROM peminjaman WHERE id_peminjaman = ?");
            stmt.setInt(1, idPeminjam);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nama_peminjam");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get the NPM of the borrower based on their ID
    public String getNpmPeminjam(int idPeminjam) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT npm_peminjam FROM peminjaman WHERE id_peminjaman = ?");
            stmt.setInt(1, idPeminjam);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("npm_peminjam");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void tambahAlat(AlatLaboratorium alat) {
        try {
            alat.simpanKeDatabase(); // Simpan alat ke database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editAlat(AlatLaboratorium alat) {
        try {
            alat.updateDiDatabase(); // Update alat di database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusAlat(int id) {
        try {
            AlatLaboratorium.hapusDariDatabase(id); // Hapus alat dari database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AlatLaboratorium> getAllAlat() {
        try {
            return AlatLaboratorium.getAllFromDatabase(); // Ambil semua alat dari database
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Peminjaman> getAllPeminjaman() {
        try {
            return Peminjaman.getAllFromDatabase(); // Ambil semua peminjaman dari database
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void tambahPenjaga(PenjagaLaboratorium penjaga) {
      try {
        penjaga.simpanKeDatabase(); // Simpan penjaga laboratorium ke database
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

}
