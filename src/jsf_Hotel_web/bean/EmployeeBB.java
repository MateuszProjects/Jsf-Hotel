package jsf_Hotel_web.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.EmployeeDAO;
import projectotel.entities.Employee;

@ManagedBean
public class EmployeeBB {

	private static final String PAGE_EMPLOYEE_EDIT = "employeeedit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	String name;
	String surname;



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@EJB
	EmployeeDAO employeeDAO;

	/**
	 * Funkcja w warstwie web, do przekazywania 
	 * parametrów do zapytania do bazy danych.
	 * @return
	 */
	public List<Employee> getListf() {
		List<Employee> list = null;
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if(name != null){
			searchParams.put("name", name);
		}
		if(surname != null){
			searchParams.put("surname", surname);
		}
		
		list = employeeDAO.getSearchList(searchParams);
		
		return list;
	}

	/**
	 * Funkcja w warstwie web, do tworzenia nowego 
	 * obiektu employee.
	 * @return
	 */
	public String newEmployee() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Employee employee = new Employee();
		session.setAttribute("employee", employee);
		return PAGE_EMPLOYEE_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do edycji 
	 * obiektu employee.
	 * @param employee
	 * @return
	 */
	public String editEmployee(Employee employee) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("employee", employee);
		return PAGE_EMPLOYEE_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do usuwania obiektu 
	 * employee.
	 * @param employee
	 * @return
	 */
	public String delete(Employee employee) {
		employeeDAO.remove(employee);
		return PAGE_STAY_AT_THE_SAME;
	}

}
