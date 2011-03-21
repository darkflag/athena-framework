/*
* $Id$
* Copyright (c) 2008-2011 Insprise Software (Shanghai) Co. Ltd.
* All Rights Reserved
* Changelog:
*   Jack - Feb 22, 2011: Initial version
*/

package
{
	import com.test.Department;
	import com.test.Employee;

	import mx.collections.ArrayCollection;

/**
 * DOC ME!
 */
public class Test
{
	// Constructor
	public function Test()
	{
		var dept:Department = Department.createNewInstance();
		dept.nameFull = "R & D";
		var emp:Employee = Employee.createNewInstance();
		emp.firstName = "Alan";
		emp.lastName = "Turing";
		emp.department = dept;

		eoService.invokeService("empService", "save", [emp], onSaveSuccess, onSaveError, null);
		trace

	}

	function executeEjbql():void {
		var ejbql:String = "SELECT d FROM Department d ORDER BY d.nameFull";
		eoService.invokeService("empService", "executeQuery", [ejbql], onQuerySuccess, onQueryError, null);
	}

	protected function onSavedSuccess(e:EventRemoteOperationSuccess):void {
		var depts:ArrayCollection = e.data as ArrayCollection;
		trace("Departments loaded, total: " + depts.length);
	}

} // end class
} // end package