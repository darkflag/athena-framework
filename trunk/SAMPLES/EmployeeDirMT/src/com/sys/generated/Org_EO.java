/*************************** DO NOT MODIFY THIS FILE **************************
 *
 * Generated by Athena Console. All Rights Reserved by Athena Source (c) 2007 - 2011.
 * www.athenaframework.org
 *
 * Generated on Feb 17, 2011 15:09:49 by Jack (Windows 7 6.1 [x86])
 * DB: MySQL 5.0[connection pool - driver class: com.mysql.jdbc.Driver, url: jdbc:mysql://localhost/employeedirMT?useUnicode=yes&characterEncoding=UTF-8, max active: 10, max idle: 0, max wait time: 5000, connection timeout: 300]
 *
 ******************************************************************************/

package com.sys.generated;

import org.athenasource.framework.eo.core.EOObject;

/**
 * Generated EO class for Org
 *
 * @version 1
 */
public class Org_EO extends EOObject {

	public static final String META_XSD_VERSION = "2.0"; // META VERSION
	public static final int COLUMN_COUNT = 4; // total number of columns, i.e., attributes
	public static final int ENTITY_ID = 201; // the entity type id. 
	public static final String SYSTEM_NAME = "Org"; // the entity system name. 
	public static final String TABLE_NAME = "Sys_Org"; // the table name. 

	// Property names 
	public static final String ATTR_org_ID = "org_ID"; // Primary key
	public static final String ATTR_version = "version"; // Version
	public static final String ATTR_status = "status"; // EO status
	public static final String ATTR_nameFull = "nameFull"; // Full name


	/**
	 * Gets Org ID (Primary key).
	 * @return Org ID
	 */
	public int getOrg_ID() {
		return getObject(0) == null ?  -1 : ((Number)getObject(0)).intValue(); 
	}

	/**
	 * Sets Org ID (Primary key).
	 * @param org_ID Org ID
	 */
	public void setOrg_ID(int org_ID) {
		setObject(0, Integer.valueOf(org_ID)); 
	}

	/**
	 * Gets Version (Version).
	 * @return Version
	 */
	public int getVersion() {
		return getObject(1) == null ?  -1 : ((Number)getObject(1)).intValue(); 
	}

	/**
	 * Sets Version (Version).
	 * @param version Version
	 */
	public void setVersion(int version) {
		setObject(1, Integer.valueOf(version)); 
	}

	/**
	 * Gets Status (EO status).
	 * @return Status
	 */
	public int getStatus() {
		return getObject(2) == null ? (byte)-1 : ((Number)getObject(2)).intValue(); 
	}

	/**
	 * Sets Status (EO status).
	 * @param status Status
	 */
	public void setStatus(int status) {
		setObject(2, Integer.valueOf(status)); 
	}

	/**
	 * Gets Full name (Full name).
	 * @return Full name
	 */
	public String getNameFull() {
		String v = getObject(3, String.class); 
		return v;
	}

	/**
	 * Sets Full name (Full name).
	 * @param nameFull Full name
	 */
	public void setNameFull(String nameFull) {
		setObject(3, nameFull); 
	}

	// -------------------------------------- Relationships --------------------------------------

}

/********************* DO NOT MODIFY - ATHENA CODE GENERATION LOG *******************
 * @file_type EO CLASS for Java - Java 5
 * @entity_id 201
 * @entity_classname com.sys.Org
 * @entity_fingerprint 9a38f873fa72fc076514d5640ceb5105
 * @entity_version 1
 * @generated_on 1297926589771
 * @generated_by Jack
 * @generated_platform Windows 7 6.1 [x86]
 ******************************************************************************/