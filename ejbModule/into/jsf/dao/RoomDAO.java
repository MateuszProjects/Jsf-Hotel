package into.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projectotel.entities.Room;

@Stateless
public class RoomDAO {
	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja szukajaca obiektu po 
	 * przekazanym id.
	 * @param id
	 * @return
	 */
	public Room find(Integer id) {
		return em.find(Room.class, id);
	}

	/**
	 * Funkcja tworz¹ca obiekt typu room
	 * @param room
	 */
	public void createRoom(Room room) {
		em.persist(room);
	}

	/**
	 * Funkcja usuwaj¹ca obietk typu room
	 * @param room
	 */
	public void remove(Room room) {
		em.remove(em.merge(room));
	}

	/**
	 * Funkcja uaktualniaj¹ca obiekt typu room
	 * @param room
	 * @return
	 */
	public Room merge(Room room) {
		return em.merge(room);
	}

	/**
	 * Funkcja pobieraj¹ca dane z bazy dnaych
	 * wraz z parametrami do zapytania o dane.
	 * @param searchParams
	 * @return
	 */
	public List<Room> getSearchList(Map<String, Object> searchParams) {
		List<Room> list = null;

		String select = "select p ";
		String from = "from Room p ";
		String where = "";
		String join = "";

		Double price = (Double) searchParams.get("price");
		Integer floor = (Integer) searchParams.get("floor");

		if (price != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and  ";
			}
			where += " p.pricePerDay like :price ";
		}

		if (floor != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and  ";
			}
			where += " p.roomFloor like :floor ";
		}

		Query query = em.createQuery(select + from + join + where);

		if (price != null) {
			query.setParameter("price", price);
		}
		if (floor != null) {
			query.setParameter("floor", floor);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
}
