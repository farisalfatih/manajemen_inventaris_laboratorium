package view;

import controller.PenjagaLaboratoriumController;
import model.AlatLaboratorium;
import model.Peminjaman;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {
    private JTextArea searchResultsArea;
    private PenjagaLaboratoriumController penjagaController;
    private JTable alatTable, peminjamanTable;
    private DefaultTableModel alatTableModel, peminjamanTableModel;
    private JButton tambahAlatButton, editAlatButton, hapusAlatButton;
    private JButton tambahPeminjamanButton, editPeminjamanButton, hapusPeminjamanButton;
    private JButton searchButton, logoutButton; // Tombol pencarian dan logout
    private JTextField searchField;
    private JLabel loggedInUserLabel;
    private JPanel searchPanel, alatButtonPanel, peminjamanButtonPanel;
    private JScrollPane alatScrollPane, peminjamanScrollPane;

    public MainFrame() {
        penjagaController = new PenjagaLaboratoriumController();

        // Set up the JFrame
        setTitle("Manajemen Inventaris Laboratorium");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        alatTableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Deskripsi", "Stok"}, 0);
        alatTable = new JTable(alatTableModel);
        alatScrollPane = new JScrollPane(alatTable);

        peminjamanTableModel = new DefaultTableModel(new Object[]{"ID Peminjaman", "ID Alat", "Nama Peminjam",
                "NPM Peminjam", "Tanggal Pinjam", "Tanggal Kembali"}, 0);
        peminjamanTable = new JTable(peminjamanTableModel);
        peminjamanScrollPane = new JScrollPane(peminjamanTable);

        tambahAlatButton = new JButton("Tambah Alat");
        editAlatButton = new JButton("Edit Alat");
        hapusAlatButton = new JButton("Hapus Alat");

        tambahPeminjamanButton = new JButton("Tambah Peminjaman");
        editPeminjamanButton = new JButton("Edit Peminjaman");
        hapusPeminjamanButton = new JButton("Hapus Peminjaman");

        searchField = new JTextField(20);
        searchButton = new JButton("Cari"); // Tombol pencarian
        logoutButton = new JButton("Logout"); // Tombol logout
        searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Cari:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(logoutButton);

        loggedInUserLabel = new JLabel("Belum Login");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add search panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(searchPanel, gbc);

        // Add alat scroll pane
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(alatScrollPane, gbc);

        // Add peminjaman scroll pane
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(peminjamanScrollPane, gbc);

        // Add button panel for Alat
        alatButtonPanel = new JPanel(new FlowLayout());
        alatButtonPanel.add(tambahAlatButton);
        alatButtonPanel.add(editAlatButton);
        alatButtonPanel.add(hapusAlatButton);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(alatButtonPanel, gbc);

        // Add button panel for Peminjaman
        peminjamanButtonPanel = new JPanel(new FlowLayout());
        peminjamanButtonPanel.add(tambahPeminjamanButton);
        peminjamanButtonPanel.add(editPeminjamanButton);
        peminjamanButtonPanel.add(hapusPeminjamanButton);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(peminjamanButtonPanel, gbc);

        // Add logged-in user label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loggedInUserLabel, gbc);

        // Load data
        loadData();

        // Add action listeners
        tambahAlatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = JOptionPane.showInputDialog("Masukkan nama alat:");
                String deskripsi = JOptionPane.showInputDialog("Masukkan deskripsi alat:");
                String stokStr = JOptionPane.showInputDialog("Masukkan stok alat:");

                try {
                    int stok = Integer.parseInt(stokStr);
                    AlatLaboratorium alatBaru = new AlatLaboratorium(0, nama, deskripsi, stok);
                    penjagaController.tambahAlat(alatBaru);
                    int newID = penjagaController.getLatestAlatID();
                    alatTableModel.addRow(new Object[]{newID, alatBaru.getNama(), alatBaru.getDeskripsi(), alatBaru.getStok()});
                    JOptionPane.showMessageDialog(MainFrame.this, "Alat berhasil ditambahkan.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Masukkan stok dalam format angka.");
                }
            }
        });

        editAlatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = alatTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Pilih baris untuk diedit.");
                    return;
                }

                int id = (int) alatTableModel.getValueAt(row, 0);
                String nama = JOptionPane.showInputDialog("Masukkan nama alat baru:");
                String deskripsi = JOptionPane.showInputDialog("Masukkan deskripsi alat baru:");
                String stokStr = JOptionPane.showInputDialog("Masukkan stok alat baru:");

                try {
                    int stok = Integer.parseInt(stokStr);
                    AlatLaboratorium alat = new AlatLaboratorium(id, nama, deskripsi, stok);
                    penjagaController.editAlat(alat);
                    alatTableModel.setValueAt(alat.getNama(), row, 1);
                    alatTableModel.setValueAt(alat.getDeskripsi(), row, 2);
                    alatTableModel.setValueAt(alat.getStok(), row, 3);
                    JOptionPane.showMessageDialog(MainFrame.this, "Alat berhasil diubah.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Masukkan stok dalam format angka.");
                }
            }
        });

        hapusAlatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = alatTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Pilih baris untuk dihapus.");
                    return;
                }

                int id = (int) alatTableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(MainFrame.this, "Anda yakin ingin menghapus alat ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    penjagaController.hapusAlat(id);
                    alatTableModel.removeRow(row);
                    JOptionPane.showMessageDialog(MainFrame.this, "Alat berhasil dihapus.");
                }
            }
        });


        // Tambah peminjaman
        tambahPeminjamanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ambil input dari pengguna
                int idAlat = Integer.parseInt(JOptionPane.showInputDialog("Masukkan ID Alat:"));
                String namaPeminjam = JOptionPane.showInputDialog("Masukkan Nama Peminjam:");
                String npmPeminjam = JOptionPane.showInputDialog("Masukkan NPM Peminjam:");
                String tanggalPinjamStr = JOptionPane.showInputDialog("Masukkan Tanggal Pinjam (YYYY-MM-DD):");
                String tanggalKembaliStr = JOptionPane.showInputDialog("Masukkan Tanggal Kembali (YYYY-MM-DD):");
                
                // Konversi tanggal dari String ke Date
                Date tanggalPinjam = Date.valueOf(tanggalPinjamStr);
                Date tanggalKembali = Date.valueOf(tanggalKembaliStr);
                
                // Buat objek peminjaman
                Peminjaman peminjaman = new Peminjaman(0, idAlat, namaPeminjam, npmPeminjam, tanggalPinjam, tanggalKembali);
                
                // Simpan ke database
                try {
                    peminjaman.simpanKeDatabase();
                    JOptionPane.showMessageDialog(MainFrame.this, "Peminjaman berhasil ditambahkan.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Gagal menambahkan peminjaman.");
                    ex.printStackTrace();
                }
            }
        });

        // Edit peminjaman
        editPeminjamanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ambil ID peminjaman yang akan diedit
                int idPeminjaman = Integer.parseInt(JOptionPane.showInputDialog("Masukkan ID Peminjaman yang akan diedit:"));
                
                // Ambil input baru dari pengguna
                int idAlat = Integer.parseInt(JOptionPane.showInputDialog("Masukkan ID Alat Baru:"));
                String namaPeminjam = JOptionPane.showInputDialog("Masukkan Nama Peminjam Baru:");
                String npmPeminjam = JOptionPane.showInputDialog("Masukkan NPM Peminjam Baru:");
                String tanggalPinjamStr = JOptionPane.showInputDialog("Masukkan Tanggal Pinjam Baru (YYYY-MM-DD):");
                String tanggalKembaliStr = JOptionPane.showInputDialog("Masukkan Tanggal Kembali Baru (YYYY-MM-DD):");
                
                // Konversi tanggal dari String ke Date
                Date tanggalPinjam = Date.valueOf(tanggalPinjamStr);
                Date tanggalKembali = Date.valueOf(tanggalKembaliStr);
                
                // Buat objek peminjaman dengan data baru
                Peminjaman peminjaman = new Peminjaman(idPeminjaman, idAlat, namaPeminjam, npmPeminjam, tanggalPinjam, tanggalKembali);
                
                // Update di database
                try {
                    peminjaman.updateDiDatabase();
                    JOptionPane.showMessageDialog(MainFrame.this, "Peminjaman berhasil diubah.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Gagal mengubah peminjaman.");
                    ex.printStackTrace();
                }
            }
        });

        // Hapus peminjaman
        hapusPeminjamanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ambil ID peminjaman yang akan dihapus
                int idPeminjaman = Integer.parseInt(JOptionPane.showInputDialog("Masukkan ID Peminjaman yang akan dihapus:"));
                
                // Hapus dari database
                try {
                    Peminjaman.hapusDariDatabase(idPeminjaman);
                    JOptionPane.showMessageDialog(MainFrame.this, "Peminjaman berhasil dihapus.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Gagal menghapus peminjaman.");
                    ex.printStackTrace();
                }
            }
        });

        // Initialize search components
        searchResultsArea = new JTextArea(10, 30);
        searchResultsArea.setEditable(false);
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                List<AlatLaboratorium> alatResults;
                List<Peminjaman> peminjamanResults;

                // Call the search methods without catching SQLException
                alatResults = PenjagaLaboratoriumController.searchAlat(keyword);
                peminjamanResults = PenjagaLaboratoriumController.searchPeminjaman(keyword);

                // Display search results
                searchResultsArea.setText("");
                searchResultsArea.append("Alat:\n");
                for (AlatLaboratorium alat : alatResults) {
                    searchResultsArea.append(alat.getNama() + " - " + alat.getDeskripsi() + "\n");
                }

                searchResultsArea.append("\nPeminjaman:\n");
                for (Peminjaman peminjaman : peminjamanResults) {
                    searchResultsArea.append(peminjaman.getNamaPeminjam() + " - " + peminjaman.getNpmPeminjam() + "\n");
                }
            }
        });


        // Call login method
        try {
            login();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, "An error occurred while attempting to log in.");
            ex.printStackTrace();
        }
    }

        private void loadData() {
        // Load data alat laboratorium
        List<AlatLaboratorium> alatList = penjagaController.getAllAlat();
        for (AlatLaboratorium alat : alatList) {
            alatTableModel.addRow(new Object[]{alat.getId(), alat.getNama(), alat.getDeskripsi(), alat.getStok()});
        }

        // Load data peminjaman
        List<Peminjaman> peminjamanList = penjagaController.getAllPeminjaman();
        for (Peminjaman peminjaman : peminjamanList) {
            // Dapatkan nama dan NPM peminjam berdasarkan ID peminjam
            String namaPeminjam = penjagaController.getNamaPeminjam(peminjaman.getIdPeminjaman());
            String npmPeminjam = penjagaController.getNpmPeminjam(peminjaman.getIdPeminjaman());

            // Tambahkan data peminjaman beserta nama dan NPM peminjam ke tabel
            peminjamanTableModel.addRow(new Object[]{peminjaman.getIdPeminjaman(), peminjaman.getIdAlat(), namaPeminjam, npmPeminjam, peminjaman.getTanggalPinjam(), peminjaman.getTanggalKembali()});
        }
    }


        private void login() throws SQLException {
        String username = JOptionPane.showInputDialog("Masukkan Username:");
        String password = JOptionPane.showInputDialog("Masukkan Password:");
        try {
            // Check if login is successful
            boolean loggedIn = penjagaController.login(username, password);
            if (loggedIn) {
                // Hide login components
                getContentPane().removeAll();

                // Set logged-in user label
                loggedInUserLabel.setText("Logged In as: " + username);

                // Add search panel
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10, 10, 10, 10);
                add(searchPanel, gbc);

                // Add alat scroll pane
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;
                add(alatScrollPane, gbc);

                // Add peminjaman scroll pane
                gbc.gridx = 1;
                gbc.gridy = 1;
                add(peminjamanScrollPane, gbc);

                // Add button panel for Alat
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.weightx = 0.5;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                add(alatButtonPanel, gbc);

                // Add button panel for Peminjaman
                gbc.gridx = 1;
                gbc.gridy = 2;
                add(peminjamanButtonPanel, gbc);

                // Add logged-in user label
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                add(loggedInUserLabel, gbc);

                // Load data
                loadData();

                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(MainFrame.this, "Login failed. Invalid username or password.");
                // Call login method again recursively
                login();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, "An error occurred while attempting to log in.");
            ex.printStackTrace();
            // Call login method again recursively
            login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}