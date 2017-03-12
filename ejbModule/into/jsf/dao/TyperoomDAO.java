package into.jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projectotel.entities.Typeroom;

@Stateless
public class TyperoomDAO {
	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja szukaj¹ca obiektu po  przekazanym id
	 * @param id
	 * @return
	 */
	public Typeroom find(Integer id) {
		return em.find(Typeroom.class, id);
	}

	/**
	 * Funkcja zapisuj¹ca przekazny obiekt do bazy danych
	 * @param typeroom
	 */
	public void createTyperoom(Typeroom typeroom) {
		em.persist(typeroom);
	}

	/**
	 * Funkcja do usuwania obiektu Typeroom
	 * @param typeroom
	 */
	public void remove(Typeroom typeroom) {
		em.remove(em.merge(typeroom));
	}

	/**
	 * Funkcja do uaktualnieniu obiektu typeroom
	 * @param typeroom
	 * @return
	 */
	public Typeroom merge(Typeroom typeroom) {
		return em.merge(typeroom);
	}

	/**
	 * Funkcja pobieraj¹ca wszystkie dane z Encji Typeroom
	 * @return
	 */
	public List<Typeroom> getFullList() {
		List<Typeroom> list = null;

		Query query = em.createQuery("SELECT t FROM Typeroom t");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
