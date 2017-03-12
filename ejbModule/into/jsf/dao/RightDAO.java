package into.jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projectotel.entities.Right;

@Stateless
public class RightDAO {
	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja do tworzenia obiektu right
	 * @param right
	 */
	public void createRight(Right right) {
		em.persist(right);
	}
	
	/**
	 * Funkcja do uaktualnianiu obiektu right
	 * @param right
	 * @return
	 */
	public Right merge(Right right) {
		return em.merge(right);
	}
	
	/**
	 * Funkcja do usuwania obiektu right
	 * @param right
	 */
	public void remove(Right right){
		em.remove(em.merge(right));
	}

	/**
	 * Funkcja do szukania obiektu right 
	 * po przekazywanym id
	 * @param id
	 * @return
	 */
	public Right find(Object id) {
		return em.find(Right.class, id);
	}

	/**
	 * Funkcja do pobierania wszystkich obiektów z 
	 * bazy  danych.
	 * @return
	 */
	public List<Right> getFullList() {
		List<Right> list = null;

		Query query = em.createQuery("SELECT r FROM Right r");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
