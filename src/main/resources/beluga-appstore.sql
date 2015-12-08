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
  `autoScaleOutUse` char(1) COLLATE utf8_unicode_ci DEFAULT 'N',
  `autoScaleInUse` char(1) COLLATE utf8_unicode_ci DEFAULT 'N',
  `autoScaleOutConf` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `autoScaleInConf` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
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
  `description` varchar(10000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cpus` float(2,1) NOT NULL,
  `memory` int(11) NOT NULL,
  `updateDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;