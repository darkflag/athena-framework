import java.io.File;
import java.io.IOException;
import java.util.List;

import org.athenasource.framework.eo.core.EOConfiguration;
import org.athenasource.framework.eo.core.EOObject;
import org.athenasource.framework.eo.core.EOService;
import org.athenasource.framework.eo.core.UnitOfWork;
import org.athenasource.framework.eo.query.EJBQLSelect;

import com.test.Address;
import com.test.EmailAccount;
import com.test.EmpProj;
import com.test.Employee;
import com.test.Project;
import com.test.generated.Address_EO;
import com.test.generated.EmailAccount_EO;
import com.test.generated.EmpProj_EO;
import com.test.generated.Employee_EO;
import com.test.generated.Project_EO;

/*
 * $Id$
 * Copyright (c) 2008-2010 Insprise Software (Shanghai) Co. Ltd.
 * All Rights Reserved
 * Changelog:
 *   Jack - Nov 8, 2010: Initial version
 *
 */

public class TestRelationships {

	EOService eoService;

	protected void init() throws IOException {
		EOConfiguration eoConfiguration = new EOConfiguration(new File("WebContent/WEB-INF/eo-config.xml").toURI().toString());
		eoService = new EOService(eoConfiguration);
		eoService.loadMetadomain();
	}

	// ------------------ one to one ----------------
	public void test1to1Create() {
		Employee emp = (Employee) eoService.getMetaDomain().getEntity(Employee_EO.SYSTEM_NAME).newInstance();
		EmailAccount account = (EmailAccount) eoService.getMetaDomain().getEntity(EmailAccount_EO.SYSTEM_NAME).newInstance();

		emp.setNameFull("John Smith");

		account.setAddress("johh.smith@athenaframework.org");
		account.setEmployee(emp);
		emp.setEmailAcct(account);

		UnitOfWork uow = eoService.createUnitOfWork();
		uow.persist(account);
		uow.flush();

		System.out.println("Employee created: id = " + emp.getEmployee_ID() + "; its email account id: " + emp.getEmailAcct().getEmailAccount_ID());
	}

	public void test1to1Load() {
		EJBQLSelect select = eoService.createEOContext().createSelectQuery("SELECT e FROM Employee e [e.emailAcct:J]");
		List<Object> employees = select.getResultList();
		for(int i=0; i < employees.size(); i++) {
			Employee emp = (Employee) employees.get(i);
			System.out.println(emp.getNameFull() + ", email address: " + (emp.getEmailAcct() == null ? "(null)" : emp.getEmailAcct().getAddress()));
		}
	}

	public void test1to1Delete() {
		UnitOfWork uow = eoService.createUnitOfWork();
		EJBQLSelect select = uow.createSelectQuery("SELECT e FROM Employee e [e.emailAcct:J]");
		List<Object> employees = select.getResultList();
		for(int i=0; i < employees.size(); i++) {
			Employee emp = (Employee) employees.get(i);
			if(emp.getEmailAcct() != null && emp.getEmailAcct().getAddress().endsWith("athenaframework.org")) {
				uow.remove(emp.getEmailAcct());
			}
		}

		uow.flush();
	}

	// ------------------ one to many ----------------
	public void test1toMCreate() {
		Employee emp = (Employee) eoService.getMetaDomain().getEntity(Employee_EO.SYSTEM_NAME).newInstance();
		Address addr = (Address) eoService.getMetaDomain().getEntity(Address_EO.SYSTEM_NAME).newInstance();

		emp.setNameFull("John Smith");

		addr.setAddress("No. 1, Athena Street");

		emp.addToAddresses(addr, true);
		// addr.setEmployee(emp);

		UnitOfWork uow = eoService.createUnitOfWork();
		uow.persist(emp);
		uow.flush();
	}

	public void test1toMLoad() {
		EJBQLSelect select = eoService.createEOContext().createSelectQuery("SELECT e FROM Employee e [e.addresses:J]");
		List<Object> employees = select.getResultList();
		for(int i=0; i < employees.size(); i++) {
			Employee emp = (Employee) employees.get(i);
			System.out.println(emp.getNameFull() + ", addresses: " + emp.getAddresses());
		}
	}

	public void test1toMDelete() {
		UnitOfWork uow = eoService.createUnitOfWork();
		EJBQLSelect select = uow.createSelectQuery("SELECT e FROM Employee e WHERE e.nameFull LIKE 'John Smith'");
		Employee emp = (Employee) select.getSingleResult();

		List<EOObject> addresses = emp.getAddresses();
		if(addresses.size() > 0) {
			emp.removeFromAddresses(addresses.get(0), true);
			uow.remove(addresses.get(0));
		}

		uow.flush();
	}

	// ------------------ many to many ----------------
	public void testM2MCreate() {
		Employee emp = (Employee) eoService.getMetaDomain().getEntity(Employee_EO.SYSTEM_NAME).newInstance();
		Project proj = (Project) eoService.getMetaDomain().getEntity(Project_EO.SYSTEM_NAME).newInstance();
		EmpProj empProj = (EmpProj) eoService.getMetaDomain().getEntity(EmpProj_EO.SYSTEM_NAME).newInstance();

		emp.setNameFull("John Smith");
		proj.setTitle("Project A");

		empProj.setEmployee(emp);
		empProj.setRole_("Leader");
		empProj.setProject(proj);

		UnitOfWork uow = eoService.createUnitOfWork();
		uow.persist(empProj);
		uow.flush();
	}

	public void testM2MLoad() {
		EJBQLSelect select = eoService.createEOContext().createSelectQuery("SELECT e FROM Employee e [e.empProjs.project:J]");
		List<Object> employees = select.getResultList();
		for(int i=0; i < employees.size(); i++) {
			Employee emp = (Employee) employees.get(i);
			System.out.println(emp.getNameFull() + ", projects: ");
			for(int j=0; j < emp.getEmpProjs().size(); j++) {
				System.out.println("\t" + ((EmpProj)emp.getEmpProjs().get(j)).getProject().getTitle());
			}
		}
	}

	public void testMtoMDelete() { // remove project leaders in Project A
		UnitOfWork uow = eoService.createUnitOfWork();
		EJBQLSelect select = uow.createSelectQuery("SELECT e FROM Employee e WHERE e.empProjs.project.title = 'Project A' [e.empProjs.project:J]");
		Employee emp = (Employee) select.getSingleResult();

		for(int i = 0; i < emp.getEmpProjs().size(); i++) {
			if(((EmpProj)emp.getEmpProjs().get(i)).getRole_().equals("Leader")) {
				uow.remove(emp.getEmpProjs().get(i));
			}
		}
		uow.flush();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
		TestRelationships standalone = new TestRelationships();
		standalone.init();
		standalone.test1to1Create();
	}

}
