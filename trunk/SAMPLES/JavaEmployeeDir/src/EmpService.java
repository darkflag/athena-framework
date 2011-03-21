import java.util.List;

import org.apache.log4j.Logger;
import org.athenasource.framework.eo.core.EOContext;
import org.athenasource.framework.eo.core.UnitOfWork;
import org.athenasource.framework.eo.query.EJBQLSelect;
import org.athenasource.framework.eo.web.service.AbstractService;

import com.test.Department;
import com.test.Employee;
import com.test.generated.Department_EO;
import com.test.generated.Employee_EO;

public class EmpService extends AbstractService {
	private static final Logger log = Logger.getLogger(EmpService.class);

	/**
	 * Load all employees and departments(All employee object is artial).
	 * @return
	 */
	public Object[] loadData() {
		EOContext eoContext = createEOContext();

		EJBQLSelect select = eoContext.createSelectQuery("SELECT e FROM Employee e [e.department:S]{po_e='employee_ID, firstName, lastName, email, phone, department_ID'}");
		List<Object> listOfEmployees = select.getResultList();

		select = eoContext.createSelectQuery("SELECT d FROM Department d ORDER BY d.nameFull");
		List<Object> listOfDepts = select.getResultList();

		if(listOfDepts.size() == 0) { // Nothing in db yet, fill sample data.
			UnitOfWork uow = createUow();
			Department dept = (Department) uow.createNewInstance(Department_EO.SYSTEM_NAME);
			dept.setNameFull("Finance");
			dept = (Department) uow.createNewInstance(Department_EO.SYSTEM_NAME);
			dept.setNameFull("Support");
			dept = (Department) uow.createNewInstance(Department_EO.SYSTEM_NAME);
			dept.setNameFull("HR");
			dept = (Department) uow.createNewInstance(Department_EO.SYSTEM_NAME);
			dept.setNameFull("R & D");

			Employee emp = (Employee) uow.createNewInstance(Employee_EO.SYSTEM_NAME);
			emp.setFirstName("Alan");
			emp.setLastName("Turing");
			emp.setPhone("111 11111");
			emp.setResume("1936: The Turing machine, computability, universal machine\n" +
				"1936-38: Princeton University. Ph.D. Logic, algebra, number theory ...");
			emp.setDepartment(dept);

			uow.flush();
			uow.close();
			// reload now
			listOfDepts = eoContext.createSelectQuery("SELECT d FROM Department d ORDER BY d.nameFull").getResultList();
			listOfEmployees = eoContext.createSelectQuery("SELECT e FROM Employee e [e.department:S]{po_e='employee_ID, firstName, lastName, email, phone, department_ID'}").getResultList();
		}

		return new Object[] {listOfEmployees, listOfDepts};
	}

	/** Get employee full object. */
	public Object loadFullEmpObject(int empID) {
		String sql = "SELECT e FROM Employee e WHERE e.employee_ID = " + empID;

		EJBQLSelect select = new EJBQLSelect(createEOContext(), sql);
		return select.getSingleResult();
	}

	/**
	 * Save Employee.
	 * @param employee
	 * @return
	 */
	public Object saveEmployee(Employee employee) {
		Object saveEmployee = doPerisist(employee, false);
		log.info("Employee saved: " + employee);
		return saveEmployee;
	}

	/**
	 * Remove Emoployee
	 * @param employee
	 * @return
	 */
	public Object removeEmployee(Employee employee) {
		Object removedEmployee = doPerisist(employee, true);
		log.info("Employee removed: " + employee);
		return removedEmployee;
	}
}
