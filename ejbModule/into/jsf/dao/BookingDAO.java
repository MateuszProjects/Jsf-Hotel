package into.jsf.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import projectotel.entities.Boocking;

@Stateless
public class BookingDAO {

	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja do szukania obiektu booking po
	 * przekaznym w parametrze id obiektu
	 * @param id
	 * @return Object booking
	 */
	public Boocking find(int id) {
		return em.find(Boocking.class, id);
	}

	/**
	 * Funkcja do tworzenia obiektu booking
	 * @param booking
	 */
	public void createBooking(Boocking booking) {
		em.persist(booking);
	}

	
	/**
	 * Funkcja do usuwania obiektu boocking
	 * @param booking
	 */
	public void remove(Boocking booking) {
	 
		em.remove(em.merge(booking));
	}

	/**
	 * Funkcja do uakualniania obiektu booking
	 * @param booking
	 * @return
	 */
	public Boocking merge(Boocking booking) {
		return em.merge(booking);
	}


	/**
	 * Funkcja pobieraj¹ca dane z bazy danych
	 * z przekazywanymi parametrami do wyszukiwania obiektów z bazy dnaych
	 * @param searchParams
	 * @return
	 */
	public List<Boocking> getListSearch( Map<String, Object> searchParams) {
		List<Boocking> list = null;

	
		Query maxResult = em.createQuery("select COUNT (e.idBoocking) FROM Boocking e ");
		
		String select = "select p ";
		String from = "from Boocking p ";
		String where = "";
		String join = "";

		Integer idCustomer = (Integer) searchParams.get("idCustomer");
		
	
		
		if (idCustomer != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " or ";
			}
			if (join.isEmpty()) {
				join = " join p.customer c  ";
			}
			where += " c.idCustomer like :idCustomer ";
		}

		Query query = em.createQuery(select + from + join + where);

		if (idCustomer != null) {
			query.setParameter("idCustomer", idCustomer);
		}
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}


	/**
	 * Funkcja pobieraj¹ca dane z bazy dnaych, z zastosowaniem
	 * technologii lazy, pobiera tylko frangment danych zgodnie z przekazanymi 
	 * parametrami.  Funkcja posiada zapytanie o liczbê obiektów w bazie danych
	 * offset oraz limit. Funikcja z parametrami do zapytania.
	 * @param limit
	 * @param offset
	 * @param searchParams
	 * @return
	 */
	public List<Boocking> getAdminList(Map<String, Object> searchParams,PaginationInfo info) {
		List<Boocking> list = null;

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		String select = "select p ";
		String from = "from Boocking p ";
		String where = "";
		String join = "";

		String datefrom = (String) searchParams.get("datafrom");
		String dateto = (String) searchParams.get("datato");

		Date datestart = null;
		Date dateend = null;

		if (datefrom != null && dateto != null) {
			try {
				datestart = format.parse(datefrom);
				dateend = format.parse(dateto);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (datefrom != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and  ";
			}
			where += " p.dateFrom > :datefrom ";
		}

		if (dateend != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and  ";
			}
			where += " p.datoTo < :dateend ";
		}

		Query querycount = em.createQuery("SELECT COUNT(p.idBoocking) " + from + join + where);
		
		if (datefrom != null) {
			querycount.setParameter("datefrom", datestart);
		}

		if (dateto != null) {
			querycount.setParameter("dateend", dateend);
		}
	
		try {
			Number n = (Number) querycount.getSingleResult();
			info.setCount(n.intValue());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createQuery(select + from + join + where);
		query.setFirstResult(info.getOffset());
		query.setMaxResults(info.getLimit());

		if (datefrom != null) {
			query.setParameter("datefrom", datestart);
		}

		if (dateto != null) {
			query.setParameter("dateend", dateend);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
