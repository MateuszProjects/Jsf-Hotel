package into.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projectotel.entities.Employee;

@Stateless
public class EmployeeDAO {
	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja odpowiada za wyszukiwanie 
	 * Pracownika po id pracownika.
	 * @param id
	 * @return
	 */
	public Employee find(Integer id) {
		return em.find(Employee.class, id);
	}

	/**
	 * Funkcja tworz¹ca nowy obiekt Employee
	 * @param employee
	 */
	public void createEmployee(Employee employee) {
		em.persist(employee);
	}

	/**
	 * Funkcja do usuwania obiektu Employee
	 * @param employee
	 */
	public void remove(Employee employee) {
		em.remove(em.merge(employee));
	}

	/**
	 * Funkcja wprwadzaj¹ca modfikacje
	 * obiektu klasy Employee
	 * @param employee
	 * @return
	 */
	public Employee merge(Employee employee) {
		return em.merge(employee);
	}

	

	/**
	 * Funkcja do pobierania danych z bazy danych
	 * z parametrami dotycz¹cymi zapytania.
	 * @param searchParams
	 * @return
	 */
	public List<Employee> getSearchList(Map<String, Object> searchParams) {
		List<Employee> list = null;

		String select = "select e ";
		String from = "from Employee e ";
		String where = "";
		String join = "";

		String name = (String) searchParams.get("name");
		String surname = (String) searchParams.get("surname");

		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and  ";
			}
			where += " e.name like :name ";
		}

		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and  ";
			}
			where += " e.surname like :surname ";
		}

		Query query = em.createQuery(select + from + join + where);

		if (name != null) {
			query.setParameter("name", name);
		}

		if (surname != null) {
			query.setParameter("surname", surname);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
}
