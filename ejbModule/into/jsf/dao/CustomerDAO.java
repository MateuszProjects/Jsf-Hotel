package into.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projectotel.entities.Customer;

@Stateless
public class CustomerDAO {

	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja do znajdowania obiektów po 
	 * przekazanym w parametrze id obiektu
	 * @param id
	 * @return
	 */
	public Customer find(Integer id) {
		return em.find(Customer.class, id);
	}

	/**
	 * Funkcja do tworzenia obiektu customer
	 * @param customer
	 */
	public void createCustomer(Customer customer) {
		em.persist(customer);
	}

	/**
	 * Funkcja do usuwania obiektu customer
	 * @param customer
	 */
	public void remove(Customer customer) {
		em.remove(em.merge(customer));
	}

	/**
	 * Funkcja do uaktualniania obiektu customer
	 * @param customer
	 * @return
	 */
	public Customer merge(Customer customer) {
		return em.merge(customer);
	}

	/**
	 * Funkcja do pobierania i wyszukiwania obiektów 
	 * przez podane parametry.
	 * @param searchParams
	 * @return
	 */
	public List<Customer> getSearchList(Map<String, Object> searchParams) {
		List<Customer> list = null;

		String select = "select c ";
		String from = "from Customer c ";
		String where = "";
		String orderby = "";

		Integer idCustomer = (Integer) searchParams.get("idCustomer");
		String name = (String) searchParams.get("name");
		String surname = (String) searchParams.get("surname");

		if (idCustomer != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.idCustomer like :idCustomer ";
		}

		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.name like :name ";
		}

		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.surname like :surname ";
		}

		Query query = em.createQuery(select + from + where);

		if (idCustomer != null) {
			query.setParameter("idCustomer", idCustomer);
		}

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
	
	
	/**
	 * 
	 * @param searchParams
	 * @param info
	 * @return
	 */
	public List<Customer> lazyFunction(Map<String, Object> searchParams, PaginationInfo info){
		List<Customer> list = null;
		
		String select = "select c ";
		String from = "from Customer c ";
		String where = "";

		Integer idCustomer = (Integer) searchParams.get("idCustomer");
		String name = (String) searchParams.get("name");
		String surname = (String) searchParams.get("surname");
		
		if (idCustomer != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.idCustomer like :idCustomer ";
		}

		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.name like :name ";
		}

		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			where += " c.surname like :surname ";
		}

		Query querycount = em.createQuery("SELECT COUNT(c.idCustomer) " + from + where);
		
		
		if (idCustomer != null) {
			querycount.setParameter("idCustomer", idCustomer);
		}

		if (name != null) {
			querycount.setParameter("name", name);
		}

		if (surname != null) {
			querycount.setParameter("surname", surname);
		}

		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Query query = em.createQuery(select + from  + where);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());
		

		if (idCustomer != null) {
			query.setParameter("idCustomer", idCustomer);
		}

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
