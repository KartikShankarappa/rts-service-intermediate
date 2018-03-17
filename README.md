# Access Swagger URTL
http://localhost:8087/rts-service/swagger-ui.html


# Database Tables

CREATE TABLE `candidate` (
  `idcandidate` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `skills` varchar(2000) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `status` varchar(100) NOT NULL,
  `bill_rate` decimal(8,2) DEFAULT '0.00',
  `source` varchar(1000) NOT NULL,
  `date_available` datetime DEFAULT NULL,
  `last_job_title` varchar(500) DEFAULT NULL,
  `current_job_title` varchar(500) DEFAULT NULL,
  `client_name` varchar(200) DEFAULT NULL,
  `client_city` varchar(100) DEFAULT NULL,
  `client_state` varchar(100) DEFAULT NULL,
  `client_zip` varchar(15) DEFAULT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_on` datetime NOT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  PRIMARY KEY (`idcandidate`),
  UNIQUE KEY `NAMEEMAIL` (`first_name`,`last_name`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;



CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(200) NOT NULL,
  `role` varchar(15) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_on` datetime NOT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='This holds user login information';



