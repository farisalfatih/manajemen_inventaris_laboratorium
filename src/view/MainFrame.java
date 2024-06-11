package view;

import controller.PenjagaLaboratoriumController;
import model.AlatLaboratorium;
import model.Peminjaman;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {
    private PenjagaLaboratoriumController penjagaController;
    private JTable alatTable, peminjamanTable;
    private DefaultTableModel alatTableModel, peminjamanTableModel;
    private JButton tambahButton, editButton, hapusButton;
    private JTextField searchField;

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
        JScrollPane alatScrollPane = new JScrollPane(alatTable);

        peminjamanTableModel = new DefaultTableModel(new Object[]{"ID Peminjaman", "ID Alat", "Nama Peminjam", "NPM Peminjam", "Tanggal Pinjam", "Tanggal Kembali"}, 0);
        peminjamanTable = new JTable(peminjamanTableModel);
        JScrollPane peminjamanScrollPane = new JScrollPane(peminjamanTable);

        tambahButton = new JButton("Tambah");
        editButton = new JButton("Edit");
        hapusButton = new JButton("Hapus");

        // Initialize search field
        searchField = new JTextField(20);
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Cari:"));
        searchPanel.add(searchField);

        // Set layout
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

        // Add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(tambahButton);
        buttonPanel.add(editButton);
        buttonPanel.add(hapusButton);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        // Load data
        loadData();

        // Add action listeners
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = JOptionPane.showInputDialog("Masukkan nama alat:");
                String deskripsi = JOptionPane.showInputDialog("Masukkan deskripsi alat:");
                String stokStr = JOptionPane.showInputDialog("Masukkan stok alat:");

                try {
                    int stok = Integer.parseInt(stokStr);
                    AlatLaboratorium alatBaru = new AlatLaboratorium(0, nama, deskripsi, stok);
                    penjagaController.tambahAlat(alatBaru);
                    // Perbarui ID berdasarkan urutan dari database
                    int newID = penjagaController.getLatestAlatID();
                    alatTableModel.addRow(new Object[]{newID, alatBaru.getNama(), alatBaru.getDeskripsi(), alatBaru.getStok()});
                    JOptionPane.showMessageDialog(MainFrame.this, "Alat berhasil ditambahkan.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Masukkan stok dalam format angka.");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
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

        hapusButton.addActionListener(new ActionListener() {
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

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                if (!keyword.isEmpty()) {
                    // Perform search in alatTableModel
                    for (int row = 0; row < alatTableModel.getRowCount(); row++) {
                        for (int col = 0; col < alatTableModel.getColumnCount(); col++) {
                            Object value = alatTableModel.getValueAt(row, col);
                            if (value != null && value.toString().toLowerCase().contains(keyword.toLowerCase())) {
                                // Select row and scroll to it
                                alatTable.setRowSelectionInterval(row, row);
                                alatTable.scrollRectToVisible(alatTable.getCellRect(row, 0, true));
                                return;
                            }
                        }
                    }
                    // If not found, show message
                    JOptionPane.showMessageDialog(MainFrame.this, "Data tidak ditemukan.");
                }
            }
        });
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
            String npmPeminjam = penjagaController.getNpmPeminjam(peminjaman.getIdPeminjam());

            // Tambahkan data peminjaman beserta nama dan NPM peminjam ke tabel
            peminjamanTableModel.addRow(new Object[]{peminjaman.getIdPeminjaman(), peminjaman.getIdAlat(), namaPeminjam, npmPeminjam, peminjaman.getTanggalPinjam(), peminjaman.getTanggalKembali()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
