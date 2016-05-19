CREATE DATABASE `belugaAppstore` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `belugaAppstore`;

CREATE TABLE `organization` (
  `id` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `joinDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `apps` (
  `id` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `orgId` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(10000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appContext` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appFile` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `appFilePath` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `appFileLength` int(11) NOT NULL,
  `appFileDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `appFileChecksum` char(32) COLLATE utf8_unicode_ci NOT NULL,
  `appContext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appFile2` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appFilePath2` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appFileLength2` int(11) DEFAULT NULL,
  `appFileDate2` datetime DEFAULT NULL,
  `appFileChecksum2` char(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appFileUpdated` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `appFileRevision` int(11) NOT NULL DEFAULT '1',
  `environment` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `cpus` float(2,1) NOT NULL,
  `memory` int(11) NOT NULL,
  `scale` int(11) NOT NULL,
  `resources` mediumtext COLLATE utf8_unicode_ci,
  `envs` mediumtext COLLATE utf8_unicode_ci,
  `autoScaleConf` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updateDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_apps_org_id_idx` (`orgId`),
  CONSTRAINT `fk_apps_org_id` FOREIGN KEY (`orgId`) REFERENCES `organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `apps_grant` (
  `appId` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `orgId` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  KEY `fk_grant_app_id_idx` (`appId`),
  KEY `fk_grant_org_id_idx` (`orgId`),
  CONSTRAINT `fk_grant_app_id` FOREIGN KEY (`appId`) REFERENCES `apps` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_grant_org_id` FOREIGN KEY (`orgId`) REFERENCES `organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `user` (
  `id` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `orgId` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `type` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'U',
  `joinDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_org_id_idx` (`orgId`),
  CONSTRAINT `fk_org_id` FOREIGN KEY (`orgId`) REFERENCES `organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `resources` (
  `id` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `orgId` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `resourceName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `port` int(11) NOT NULL,
  `env` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cpus` float(2,1) NOT NULL,
  `memory` int(11) NOT NULL,
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_resources_org_id_idx` (`orgId`),
  CONSTRAINT `fk_resources_org_id` FOREIGN KEY (`orgId`) REFERENCES `organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `resource_types` (
  `id` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `catalog` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `port` int(11) NOT NULL,
  `env` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `desc` longtext COLLATE utf8_unicode_ci DEFAULT NULL,
  `file` longblob DEFAULT NULL,
  `filetype` varchar(100) DEFAULT NULL,
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('mysql5', 'database', 'MySQL 5.7.9', 'mysql:5.7.9', 3306, '{"MYSQL_ROOT_PASSWORD":"1234"}', 'MySQL is written in C and C++. Its SQL parser is written in yacc, but it uses a home-brewed lexical analyzer. MySQL works on many system platforms, including AIX, BSDi, FreeBSD, HP-UX, eComStation, i5/OS, IRIX, Linux, OS X, Microsoft Windows, NetBSD, Novell NetWare, OpenBSD, OpenSolaris, OS/2 Warp, QNX, Oracle Solaris, Symbian, SunOS, SCO OpenServer, SCO UnixWare, Sanos and Tru64. A port of MySQL to OpenVMS also exists.');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('mysql5Lower', 'database', 'MySQL 5.7.9 Lowercase', 'fastcat/mysql:5.7.9lower', 3306, '{"MYSQL_ROOT_PASSWORD":"1234"}', 'MySQL is written in C and C++. Its SQL parser is written in yacc, but it uses a home-brewed lexical analyzer. MySQL works on many system platforms, including AIX, BSDi, FreeBSD, HP-UX, eComStation, i5/OS, IRIX, Linux, OS X, Microsoft Windows, NetBSD, Novell NetWare, OpenBSD, OpenSolaris, OS/2 Warp, QNX, Oracle Solaris, Symbian, SunOS, SCO OpenServer, SCO UnixWare, Sanos and Tru64. A port of MySQL to OpenVMS also exists');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('postgresql9', 'database', 'PostgreSQL 9.4.5', 'postgres:9.4.5', 5432, '{"POSTGRES_PASSWORD":"1234"}', 'PostgreSQL, often simply Postgres, is an object-relational database management system (ORDBMS) with an emphasis on extensibility and standards-compliance. As a database server, its primary function is to store data securely, supporting best practices, and to allow for retrieval at the request of other software applications. It can handle workloads ranging from small single-machine applications to large Internet-facing applications with many concurrent users.');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('oraclexe11g', 'database', 'Oracle XE 11g', 'wnameless/oracle-xe-11g', 1521, '{}', 'Oracle Database (commonly referred to as Oracle RDBMS or simply as Oracle) is an object-relational database management system[3] produced and marketed by Oracle Corporation. Larry Ellison and his two friends and former co-workers, Bob Miner and Ed Oates, started a consultancy called Software Development Laboratories (SDL) in 1977. SDL developed the original version of the Oracle software. The name Oracle comes from the code-name of a CIA-funded project Ellison had worked on while previously employed by Ampex.');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('mongodb3', 'database', 'MongoDB 3.0', 'mongo:3.0', 27017, '{}', '');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('redis3', 'database', 'Redis 3.0.5', 'redis:3.0.5', 6379, '{}', '');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('memcached1', 'database', 'Memcached 1.4.25', 'memcached:1.4.25', 11211, '{}', '');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('elasticsearch2', 'database', 'Elasticsearch 2.1', 'elasticsearch:2.1.0', 9200, '{"ES_HEAP_SIZE":"500m"}', '');

INSERT INTO resource_types (id, catalog, name, image, port, env, `desc`)
VALUES ('ftp', 'database', 'FTP', 'mcreations/ftp', 21, '{"FTP_USER":"beluga","FTP_PASS":"1234","HOST":"localhost"}', '');


