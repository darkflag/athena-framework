/*
 * $Id$
 * Copyright (c) 2008-2011 Insprise Software (Shanghai) Co. Ltd.
 * All Rights Reserved
 * Changelog:
 *   Jack - 30-Jan-2011: Initial version
 *
 */

package standalone;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.athenasource.framework.eo.core.EOConfiguration;
import org.athenasource.framework.eo.core.EOContext;
import org.athenasource.framework.eo.core.EOObject;
import org.athenasource.framework.eo.core.EOService;
import org.athenasource.framework.eo.core.IEntity;
import org.athenasource.framework.eo.core.MetaDomain;
import org.athenasource.framework.eo.core.UnitOfWork;
import org.athenasource.framework.eo.core.baseclass.Attribute;
import org.athenasource.framework.eo.core.baseclass.Entity;
import org.athenasource.framework.eo.core.baseclass.Relationship;
import org.athenasource.framework.eo.core.context.EOThreadLocal;
import org.athenasource.framework.eo.query.EJBQLSelect;

import com.test.Address;
import com.test.Employee;
import com.test.generated.Address_EO;
import com.test.generated.Employee_EO;

public class QueryUpdateSample {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        EOService eoService = constructEOService();
        System.out.println("Entity count: " + eoService.getMetaDomain().getAllEntities(true).size());

        MetaDomain metaDomain = eoService.getMetaDomain();
        //testQueryEjbql(eoService);
        //testQueryFindById(eoService);
        //testQueryRelationship(eoService);
        //testCreateEmployee(eoService);
        //testUpdateEmployee(eoService);
        testDeleteEmployee(eoService);
    }

    protected static void testQueryEjbql(EOService eoService) {
    	System.out.println("*** Query EJBQL ***");
    	EOContext context = eoService.createEOContext();
    	EJBQLSelect select = context.createSelectQuery("SELECT e FROM Employee e WHERE e.nameFull LIKE 'Jack%'");
    	List<Object> emps = select.getResultList();
    	System.out.println("Total number of employees found: " + emps.size());
    	for (Iterator iterator = emps.iterator(); iterator.hasNext();) {
			Employee emp = (Employee) iterator.next();
			System.out.println("Emp: " + emp.getNameFull());
		}
    }

    protected static void testQueryFindById(EOService eoService) {
    	System.out.println("*** Find by ID ***");
    	EOContext context = eoService.createEOContext();
    	Employee emp = (Employee)context.find(Employee_EO.SYSTEM_NAME, 100, null, null);
    }

    protected static void testQueryRelationship(EOService eoService) {
    	System.out.println("*** Query via relationship ***");
    	EOContext context = eoService.createEOContext();
    	EJBQLSelect select = context.createSelectQuery("SELECT e FROM Employee e {result_first='1', result_max='1'}");
    	Employee emp = (Employee) select.getSingleResult();
    	if(emp != null) {
    		List<EOObject> addrs = emp.getAddresses();
    		for(int i = 0; i < addrs.size(); i++) {
    			System.out.println("Address " + i + ": " + ((Address)addrs.get(i)).getAddress());
    		}
    	}
    }

    protected static void testCreateEmployee(EOService eoService) {
    	System.out.println("*** Creating EO ***");
    	UnitOfWork uow = eoService.createUnitOfWork();
    	Employee emp = (Employee) uow.createNewInstance(Employee_EO.SYSTEM_NAME);
    	emp.setNameFull("Jane Smith");
    	emp.setBornYear(1970);

    	Address addr = (Address) uow.createNewInstance(Address_EO.SYSTEM_NAME);
    	addr.setAddress("No. 1 New City Street");
    	emp.addToAddresses(addr, true); // wire up the relationship.

    	uow.flush();

    	System.out.println("New employee created. ID: " + emp.getEmployee_ID());
    }

    protected static void testUpdateEmployee(EOService eoService) {
    	System.out.println("*** Updating EO ***");
    	UnitOfWork uow = eoService.createUnitOfWork();
    	EJBQLSelect select = uow.createSelectQuery("SELECT e FROM Employee e {result_first='1', result_max='1'}");
    	Employee emp = (Employee) select.getSingleResult();
    	if(emp != null) {
    		System.out.println("Updating name for Employee with id: " + emp.getEmployee_ID());
    		emp.setNameFull(emp.getNameFull() + " New");
    	}
    	uow.flush();
    }

    protected static void testDeleteEmployee(EOService eoService) {
    	System.out.println("*** Deleting EO ***");
    	UnitOfWork uow = eoService.createUnitOfWork();
    	EJBQLSelect select = uow.createSelectQuery("SELECT e FROM Employee e {result_first='1', result_max='1'}");
    	Employee emp = (Employee) select.getSingleResult();
    	if(emp != null) {
    		System.out.println("Deleting Employee with id: " + emp.getEmployee_ID());
    		uow.remove(emp);
    	}
    	uow.flush();
    }


    public static EOService constructEOService() throws IOException {
    	EOConfiguration eoConfig = new EOConfiguration(new File("WebContent/WEB-INF/eo-config.xml").toURI().toString());
        EOService eoService = new EOService(eoConfig);
        eoService.loadMetadomain(); // only need to load once.
        EOThreadLocal.setEOService(eoService); // Bind to thread local
        return eoService;
    }



}
