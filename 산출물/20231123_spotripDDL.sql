-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema spotrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema spotrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `spotrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `spotrip` ;

-- -----------------------------------------------------
-- Table `spotrip`.`attraction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`attraction` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`attraction` (
  `id` INT NOT NULL,
  `content_type_id` INT NOT NULL,
  `content_sub_type` VARCHAR(45) NULL DEFAULT NULL,
  `title` VARCHAR(100) NOT NULL,
  `addr1` VARCHAR(500) NULL DEFAULT NULL,
  `addr2` VARCHAR(500) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `image` VARCHAR(200) NULL DEFAULT NULL,
  `sido_code` VARCHAR(20) NOT NULL,
  `gugun_code` VARCHAR(20) NOT NULL,
  `latitude` VARCHAR(20) NOT NULL,
  `longitude` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `spotrip`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`member` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`member` (
  `id` VARCHAR(20) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `image` VARCHAR(200) NULL DEFAULT NULL,
  `content` VARCHAR(200) NULL DEFAULT NULL,
  `token` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `spotrip`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`board` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `content` VARCHAR(10000) NULL DEFAULT NULL,
  `recruits` INT NULL DEFAULT '0',
  `category` VARCHAR(45) NULL DEFAULT NULL,
  `image` VARCHAR(200) NULL DEFAULT NULL,
  `departure_date` VARCHAR(45) NULL DEFAULT NULL,
  `arrival_date` VARCHAR(45) NULL DEFAULT NULL,
  `views` INT NOT NULL DEFAULT '0',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sido_code` VARCHAR(20) NULL DEFAULT NULL,
  `member_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`, `member_id`),
  INDEX `fk_board_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `board_ibfk_1`
    FOREIGN KEY (`member_id`)
    REFERENCES `spotrip`.`member` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_board_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `spotrip`.`member` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `spotrip`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`comment` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `parents` INT NULL DEFAULT NULL,
  `board_id` INT NOT NULL,
  `member_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_board1_idx` (`board_id` ASC) VISIBLE,
  INDEX `fk_comment_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `comment_ibfk_1`
    FOREIGN KEY (`board_id`)
    REFERENCES `spotrip`.`board` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `comment_ibfk_2`
    FOREIGN KEY (`member_id`)
    REFERENCES `spotrip`.`member` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_comment_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `spotrip`.`board` (`id`),
  CONSTRAINT `fk_comment_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `spotrip`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `spotrip`.`sidocode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`sidocode` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`sidocode` (
  `sidoCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sidoCode`),
  UNIQUE INDEX `sidoName` (`sidoName` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spotrip`.`dongcode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`dongcode` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`dongcode` (
  `dongCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`dongCode`),
  INDEX `sidoName` (`sidoName` ASC) VISIBLE,
  CONSTRAINT `dongcode_ibfk_1`
    FOREIGN KEY (`sidoName`)
    REFERENCES `spotrip`.`sidocode` (`sidoName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spotrip`.`guguncode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`guguncode` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`guguncode` (
  `gugunCode` VARCHAR(10) NOT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`gugunCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spotrip`.`mode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`mode` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`mode` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `spotrip`.`plan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`plan` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`plan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_number` INT NULL DEFAULT '0',
  `board_id` INT NOT NULL,
  `attraction_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_plan_board_idx` (`board_id` ASC) VISIBLE,
  INDEX `fk_plan_attraction1_idx` (`attraction_id` ASC) VISIBLE,
  CONSTRAINT `fk_plan_attraction1`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `spotrip`.`attraction` (`id`),
  CONSTRAINT `fk_plan_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `spotrip`.`board` (`id`),
  CONSTRAINT `plan_ibfk_1`
    FOREIGN KEY (`board_id`)
    REFERENCES `spotrip`.`board` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `plan_ibfk_2`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `spotrip`.`attraction` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `spotrip`.`style`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `spotrip`.`style` ;

CREATE TABLE IF NOT EXISTS `spotrip`.`style` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `mode_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_style_board1_idx` (`board_id` ASC) VISIBLE,
  INDEX `fk_style_mode1_idx` (`mode_id` ASC) VISIBLE,
  CONSTRAINT `fk_style_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `spotrip`.`board` (`id`),
  CONSTRAINT `fk_style_mode1`
    FOREIGN KEY (`mode_id`)
    REFERENCES `spotrip`.`mode` (`id`),
  CONSTRAINT `style_ibfk_1`
    FOREIGN KEY (`board_id`)
    REFERENCES `spotrip`.`board` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `style_ibfk_2`
    FOREIGN KEY (`mode_id`)
    REFERENCES `spotrip`.`mode` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
