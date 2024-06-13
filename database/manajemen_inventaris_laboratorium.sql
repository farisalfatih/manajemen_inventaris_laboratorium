-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 13, 2024 at 05:28 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `manajemen_inventaris_laboratorium`
--

-- --------------------------------------------------------

--
-- Table structure for table `alat_laboratorium`
--

CREATE TABLE `alat_laboratorium` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `deskripsi` varchar(255) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `alat_laboratorium`
--

INSERT INTO `alat_laboratorium` (`id`, `nama`, `deskripsi`, `stok`) VALUES
(1, 'Alat 11', 'Deskripsi alat 11', 20),
(2, 'Alat 2', 'Deskripsi Alat 2', 15),
(3, 'Alat 3', 'Deskripsi Alat 3', 20),
(4, 'Alat 4', 'Deskripsi Alat 4', 12),
(5, 'Alat 5', 'Deskripsi Alat 5', 8),
(6, 'Alat 6', 'Deskripsi Alat 6', 25),
(7, 'alat 7', 'Deskripsi Alat 7', 40),
(8, 'Alat 8', 'Deskripsi Alat 8', 30),
(9, 'Alat 9', 'Deskripsi Alat 9', 22),
(10, 'Alat 10', 'Deskripsi Alat 10', 7),
(11, 'Alat 11', 'Deskripsi Alat 11', 14),
(12, 'Alat 12', 'Deskripsi Alat 12', 19),
(13, 'Alat 13', 'Deskripsi Alat 13', 23),
(14, 'Alat 14', 'Deskripsi Alat 14', 17),
(15, 'Alat 15', 'Deskripsi Alat 15', 11),
(16, 'Alat 16', 'Deskripsi Alat 16', 28),
(17, 'Alat 17', 'Deskripsi Alat 17', 9),
(18, 'Alat 18', 'Deskripsi Alat 18', 16),
(19, 'Alat 19', 'Deskripsi Alat 19', 21),
(20, 'Alat 20', 'Deskripsi Alat 20', 13),
(21, 'Alat 21', 'Deskripsi Alat 21', 27),
(22, 'Alat 22', 'Deskripsi Alat 22', 6),
(23, 'Alat 23', 'Deskripsi Alat 23', 24),
(24, 'Alat 24', 'Deskripsi Alat 24', 31),
(25, 'Alat 25', 'Deskripsi Alat 25', 26),
(26, 'Alat 26', 'Deskripsi Alat 26', 5),
(27, 'Alat 27', 'Deskripsi Alat 27', 32),
(28, 'Alat 28', 'Deskripsi Alat 28', 29),
(29, 'Alat 29', 'Deskripsi Alat 29', 34),
(30, 'Alat 30', 'Deskripsi Alat 30', 37),
(33, 'Botol Kimia', 'Botol kaca untuk cairan asam', 50),
(34, 'mikroskop', 'Tersedia', 5),
(35, 'suntik', 'tersedia', 50),
(36, 'Alat 7', 'deskripsi 7', 30);

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_peminjaman` int(11) NOT NULL,
  `id_alat` int(11) DEFAULT NULL,
  `nama_peminjam` varchar(255) DEFAULT NULL,
  `npm_peminjam` varchar(255) DEFAULT NULL,
  `tanggal_pinjam` date DEFAULT NULL,
  `tanggal_kembali` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `peminjaman`
--

INSERT INTO `peminjaman` (`id_peminjaman`, `id_alat`, `nama_peminjam`, `npm_peminjam`, `tanggal_pinjam`, `tanggal_kembali`) VALUES
(2, 2, 'Peminjam 2', '22081010002', '2024-06-13', '2024-06-16'),
(3, 3, 'Peminjam 3', '22081010003', '2024-06-14', '2024-06-17'),
(4, 4, 'Peminjam 4', '22081010004', '2024-06-15', '2024-06-18'),
(5, 5, 'Peminjam 5', '22081010005', '2024-06-16', '2024-06-19'),
(6, 6, 'Peminjam 6', '22081010006', '2024-06-17', '2024-06-20'),
(7, 7, 'Peminjam 7', '22081010007', '2024-06-18', '2024-06-21'),
(8, 8, 'Peminjam 8', '22081010008', '2024-06-19', '2024-06-22'),
(9, 9, 'Peminjam 9', '22081010009', '2024-06-20', '2024-06-23'),
(10, 10, 'Peminjam 10', '22081010010', '2024-06-21', '2024-06-24'),
(11, 11, 'Peminjam 11', '22081010011', '2024-06-22', '2024-06-25'),
(12, 12, 'Peminjam 12', '22081010012', '2024-06-23', '2024-06-26'),
(13, 13, 'Peminjam 13', '22081010013', '2024-06-24', '2024-06-27'),
(14, 14, 'Peminjam 14', '22081010014', '2024-06-25', '2024-06-28'),
(15, 15, 'Peminjam 15', '22081010015', '2024-06-26', '2024-06-29'),
(16, 16, 'Peminjam 16', '22081010016', '2024-06-27', '2024-06-30'),
(17, 17, 'Peminjam 17', '22081010017', '2024-06-28', '2024-07-01'),
(18, 18, 'Peminjam 18', '22081010018', '2024-06-29', '2024-07-02'),
(19, 19, 'Peminjam 19', '22081010019', '2024-06-30', '2024-07-03'),
(20, 20, 'Peminjam 20', '22081010020', '2024-07-01', '2024-07-04'),
(21, 21, 'Peminjam 21', '22081010021', '2024-07-02', '2024-07-05'),
(22, 22, 'Peminjam 22', '22081010022', '2024-07-03', '2024-07-06'),
(23, 23, 'Peminjam 23', '22081010023', '2024-07-04', '2024-07-07'),
(24, 24, 'Peminjam 24', '22081010024', '2024-07-05', '2024-07-08'),
(25, 25, 'Peminjam 25', '22081010025', '2024-07-06', '2024-07-09'),
(26, 26, 'Peminjam 26', '22081010026', '2024-07-07', '2024-07-10'),
(27, 27, 'Peminjam 27', '22081010027', '2024-07-08', '2024-07-11'),
(28, 28, 'Peminjam 28', '22081010028', '2024-07-09', '2024-07-12'),
(29, 29, 'Peminjam 29', '22081010029', '2024-07-10', '2024-07-13'),
(30, 33, 'Mohammad Faris Al Fatih', '22081010277', '2024-06-01', '2024-06-13'),
(31, 34, 'mikroskop', '100', '2024-06-20', '2024-07-01');

-- --------------------------------------------------------

--
-- Table structure for table `penjaga_laboratorium`
--

CREATE TABLE `penjaga_laboratorium` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penjaga_laboratorium`
--

INSERT INTO `penjaga_laboratorium` (`id`, `nama`, `username`, `password`) VALUES
(1, 'Mohammad Faris Al Fatih', '22081010277', '20062004');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alat_laboratorium`
--
ALTER TABLE `alat_laboratorium`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`),
  ADD KEY `id_alat` (`id_alat`);

--
-- Indexes for table `penjaga_laboratorium`
--
ALTER TABLE `penjaga_laboratorium`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alat_laboratorium`
--
ALTER TABLE `alat_laboratorium`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `peminjaman`
--
ALTER TABLE `peminjaman`
  MODIFY `id_peminjaman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `penjaga_laboratorium`
--
ALTER TABLE `penjaga_laboratorium`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `peminjaman_ibfk_1` FOREIGN KEY (`id_alat`) REFERENCES `alat_laboratorium` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
