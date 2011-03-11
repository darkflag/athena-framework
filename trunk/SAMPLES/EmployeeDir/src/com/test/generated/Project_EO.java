/*************************** DO NOT MODIFY THIS FILE **************************
 *
 * Generated by Athena Console. All Rights Reserved by Athena Source (c) 2007 - 2010.
 * www.athenaframework.org
 *
 * Generated on Nov 08, 2010 10:47:19 by Jack (Windows 7 6.1 [x86])
 * DB: MySQL 5.0[connection pool - driver class: com.mysql.jdbc.Driver, url: jdbc:mysql://localhost/employeedir?useUnicode=yes&characterEncoding=UTF-8, max active: 10, max idle: 5, max wait time: 5000, connection timeout: 300]
 *
 ******************************************************************************/

package com.test.generated;

import java.util.List;

import org.athenasource.framework.eo.core.EOObject;

/**
 * Generated EO class for Project
 *
 * @version 1
 */
public class Project_EO extends EOObject {

	public static final String META_XSD_VERSION = "2.0"; // META VERSION
	public static final int COLUMN_COUNT = 5; // total number of columns, i.e., attributes
	public static final int ENTITY_ID = 104; // the entity type id. 
	public static final String SYSTEM_NAME = "Project"; // the entity system name. 
	public static final String TABLE_NAME = "Project"; // the table name. 

	// Property names 
	public static final String ATTR_project_ID = "project_ID"; // Primary key
	public static final String ATTR_version = "version"; // Version
	public static final String ATTR_status = "status"; // EO status
	public static final String ATTR_ORG_ID = "ORG_ID"; // Organization
	public static final String ATTR_title = "title";

	public static final String REL_empProjs = "empProjs";

	/**
	 * Gets Project ID (Primary key).
	 * @return Project ID
	 */
	public int getProject_ID() {
		return getObject(0) == null ?  -1 : ((Number)getObject(0)).intValue(); 
	}

	/**
	 * Sets Project ID (Primary key).
	 * @param project_ID Project ID
	 */
	public void setProject_ID(int project_ID) {
		setObject(0, Integer.valueOf(project_ID)); 
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
	 * Gets Org ID (Organization).
	 * @return Org ID
	 */
	public int getORG_ID() {
		return getObject(3) == null ?  -1 : ((Number)getObject(3)).intValue(); 
	}

	/**
	 * Sets Org ID (Organization).
	 * @param ORG_ID Org ID
	 */
	public void setORG_ID(int ORG_ID) {
		setObject(3, Integer.valueOf(ORG_ID)); 
	}

	/**
	 * Gets Title (Title).
	 * @return Title
	 */
	public String getTitle() {
		String v = getObject(4, String.class); 
		return v;
	}

	/**
	 * Sets Title (Title).
	 * @param title Title
	 */
	public void setTitle(String title) {
		setObject(4, title); 
	}

	// -------------------------------------- Relationships --------------------------------------

	/**
	 * [INVERSE] (Complement rel: EmpProj.project) Gets the collection of EmpProj in empProjs
	 * null - null
	 * 
	 * @return the collection of EmpProj in empProjs
	 */
	public List<EOObject> getEmpProjs() { 
		return getRelationshipTargetObjectsList("empProjs"); 
	}

	/**
	 * [INVERSE] (Complement rel: EmpProj.project) Adds the specified EmpProj.
	 * null - null
	 * 
	 * @param empProj The EmpProj to be added.
	 * @return <code>true</code> if added successfully; <code>false</code> if the specified item already in the collection.
	 */
	public boolean addToEmpProjs(EOObject empProj) {
		return addRelationshipTargetObject("empProjs", empProj);
	}

	/**
	 * [INVERSE] (Complement rel: EmpProj.project) Adds the specified EmpProj and optionally updates the complement relationship object. 
	 * null - null
	 * 
	 * @param empProj The EmpProj to be added.
	 * @param updateComplementRelationship Whether to update the target object of the complement to-one relationship object to this object.
	 * @return <code>true</code> if added successfully; <code>false</code> if the specified item already in the collection.
	 */
	public boolean addToEmpProjs(EOObject empProj, boolean updateComplementRelationship) {
		return addRelationshipTargetObject("empProjs", empProj, updateComplementRelationship);
	}

	/**
	 * [INVERSE] (Complement rel: EmpProj.project) Removes the specified EmpProj.
	 * null - null
	 * 
	 * @param empProj The EmpProj to be removed.
	 * @return <code>true</code> if removed successfully; <code>false</code> if the specified item was not in the collection.
	 */
	public boolean removeFromEmpProjs(EOObject empProj) {
		return removeRelationshipTargetObject("empProjs", empProj);
	}

	/**
	 * [INVERSE] (Complement rel: EmpProj.project) Removes the specified EmpProj and optionally updates the complement relationship object. 
	 * null - null
	 * 
	 * @param empProj The EmpProj to be removed.
	 * @param updateComplementRelationship Whether to update the target object of the complement to-one relationship object to null.
	 * @return <code>true</code> if removed successfully; <code>false</code> if the specified item was not in the collection.
	 */
	public boolean removeFromEmpProjs(EOObject empProj, boolean updateComplementRelationship) {
		return removeRelationshipTargetObject("empProjs", empProj, updateComplementRelationship);
	}

}

/********************* DO NOT MODIFY - ATHENA CODE GENERATION LOG *******************
 * @file_type EO CLASS for Java - Java 5
 * @entity_id 104
 * @entity_classname com.test.Project
 * @entity_fingerprint edd34c331cf7093da6bc884fc72bff93
 * @entity_version 1
 * @generated_on 1289184439455
 * @generated_by Jack
 * @generated_platform Windows 7 6.1 [x86]
 ******************************************************************************/