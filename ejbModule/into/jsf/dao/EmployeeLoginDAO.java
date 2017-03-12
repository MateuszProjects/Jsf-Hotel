package into.jsf.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projectotel.entities.Employee;

@Stateless
public class EmployeeLoginDAO {

	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja do logowania do systemu
	 * do funkcji przekazywane s¹ parametry login i password
	 * jeœli odpowiedŸ po zapytaniu s¹ ró¿ne od null, zaloguj siê.
	 * @param login
	 * @param pass
	 * @return
	 */
	public Employee getEmployee(String login, String pass) {
		Employee employee = null;

		Query query = em.createQuery("SELECT e FROM Employee e  where e.login=:login and e.pass=:pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);

		try {
			employee = (Employee) query.getSingleResult();

			if (employee != null) {
				employee.getRights().size();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return employee;
	}

}
