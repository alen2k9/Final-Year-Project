-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema user
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema user
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `user` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `user` ;

-- -----------------------------------------------------
-- Table `user`.`hosts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user`.`hosts` (
  `hostid` INT NOT NULL AUTO_INCREMENT,
  `floorid` INT NOT NULL,
  `rackid` INT NOT NULL,
  `datacenterid` INT NOT NULL,
  `host` INT NOT NULL,
  PRIMARY KEY (`hostid`),
  UNIQUE INDEX `host_UNIQUE` (`host` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `user`.`servers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user`.`servers` (
  `serverid` INT NOT NULL AUTO_INCREMENT,
  `school` VARCHAR(45) NULL DEFAULT NULL,
  `researchgroup` VARCHAR(45) NULL DEFAULT NULL,
  `project` VARCHAR(45) NULL DEFAULT NULL,
  `server` VARCHAR(45) NULL DEFAULT NULL,
  `annualBudget` INT(10) UNSIGNED ZEROFILL NULL DEFAULT '0000000000',
  `carbonBudget` INT(10) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `hostid` INT NOT NULL,
  PRIMARY KEY (`serverid`),
  UNIQUE INDEX `hostid_UNIQUE` (`hostid` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `user`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user`.`users` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `user`.`userserver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user`.`userserver` (
  `userid` INT NOT NULL,
  `serverid` INT NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
