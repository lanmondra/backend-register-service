--liquibase formatted sql

-- changeset angel:20220917-160100-1

-- -----------------------------------------------------
-- Table base_role
-- -----------------------------------------------------
DROP TABLE IF EXISTS `base_role` ;

CREATE TABLE IF NOT EXISTS `base_role`  (
   `id` INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(45) NOT NULL
  )
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table base_user
-- -----------------------------------------------------
DROP TABLE IF EXISTS `base_user` ;

CREATE TABLE IF NOT EXISTS `base_user`(
   `id` INT NOT NULL AUTO_INCREMENT,
   `uuid` VARCHAR(36) NOT NULL,
   `name` VARCHAR(45) NOT NULL,
   `first_last_name` VARCHAR(45) NOT NULL,
   `second_last_name` VARCHAR(45) NOT NULL,
   `email` VARCHAR(45) NOT NULL,
   `phone` VARCHAR(15) NULL,
   `password` VARCHAR(100) NOT NULL,
   `recover_pw` VARCHAR(40) NULL,
   `default_lang` VARCHAR(2) NOT NULL,
   `register_status` INT UNSIGNED NOT NULL DEFAULT 0,
   `town` VARCHAR(40) NULL,
   `post_code` VARCHAR(8) NULL,
   `created` TIMESTAMP NOT NULL,
   `updated` TIMESTAMP NULL,
   `deleted` TIMESTAMP NULL,
	PRIMARY KEY (`id`)
  
 )
   

ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_roles` ;

CREATE TABLE IF NOT EXISTS `user_roles` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT  NOT NULL ,
  `role_id` INT  NOT NULL 

   )
ENGINE = InnoDB ;
