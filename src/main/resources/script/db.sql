CREATE SCHEMA `db_awslearningapp` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

CREATE TABLE `app_client` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `isAdmin` bit(1) NOT NULL,
                              `name` varchar(255) DEFAULT NULL,
                              `token` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `UK_4kmcrk9p3nbwh2b8dygsl7uup` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `db_awslearningapp`.`app_client`
(`id`,
 `isAdmin`,
 `name`,
 `token`)
VALUES
    (1,
     true,
     'SuperAdmin',
     'narayan');
SELECT * FROM db_awslearningapp.app_client;