package into.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projectotel.entities.Payment;

@Stateless
public class PaymentDAO {

	private final static String UNIT_NAME = "jsfcourse-hotelPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	/**
	 * Funkcja do tworzeia obiektu payment
	 * @param payment
	 */
	public void createPayment(Payment payment) {
		em.persist(payment);
	}

	/**
	 * Funkcja do usuwania obiektu payment
	 * @param payment
	 */
	public void remove(Payment payment) {
		em.remove(em.merge(payment));
	}

	/**
	 * Funkcja do uaktualniania  obiektu payment
	 * @param payment
	 * @return
	 */
	public Payment merge(Payment payment) {
		return em.merge(payment);
	}

	/**
	 * Funkcja do wyszukiwania danych z parametrami
	 * przekazywanymi przez u¿tkownika
	 * @param searchParams
	 * @return
	 */
	public List<Payment> getSearchList(Map<String, Object> searchParams) {
		List<Payment> list = null;

		String select = "select p ";
		String from = "from Payment p ";
		String where = "";
		String join = "";

		Double amount = (Double) searchParams.get("amount");

		if (amount != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += " and  ";
			}
			where += " p.amount = :amount ";
		}

		Query query = em.createQuery(select + from + join + where);

		if (amount != null) {
			query.setParameter("amount", amount);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
}