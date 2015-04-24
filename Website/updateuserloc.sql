-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 24, 2015 at 01:12 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `updateuserloc`
--

-- --------------------------------------------------------

--
-- Table structure for table `userloc`
--

CREATE TABLE IF NOT EXISTS `userloc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_lat` double DEFAULT NULL,
  `u_lng` double DEFAULT NULL,
  `u_lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `userloc`
--

INSERT INTO `userloc` (`id`, `u_lat`, `u_lng`, `u_lastUpdated`) VALUES
(1, -37.887276, 145.17194, '2015-04-24 10:59:26'),
(2, -37.887276, 145.1719, '2015-04-24 11:11:17'),
(3, -37.887276, 145.17189, '2015-04-24 11:11:27');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
