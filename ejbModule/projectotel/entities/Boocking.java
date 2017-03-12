package projectotel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Date;

/**
 * The persistent class for the boocking database table.
 * 
 */
@Entity
@NamedQuery(name = "Boocking.findAll", query = "SELECT b FROM Boocking b")
public class Boocking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idBoocking;

	private byte active;

	@Temporal(TemporalType.DATE)
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	private Date datoTo;

	// bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name = "idCustomer")
	private Customer customer;

	// bi-directional many-to-one association to Room
	@ManyToOne
	@JoinColumn(name = "idRoom")
	private Room room;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "idEmployee")
	private Employee employee;

	// bi-directional many-to-one association to Payment
	@OneToMany(mappedBy = "boocking")
	private List<Payment> payments;

	public Boocking() {
	}

	public Integer getIdBoocking() {
		return this.idBoocking;
	}

	public void setIdBoocking(int idBoocking) {
		this.idBoocking = idBoocking;
	}

	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDatoTo() {
		return this.datoTo;
	}

	public void setDatoTo(Date datoTo) {
		this.datoTo = datoTo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setBoocking(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setBoocking(null);

		return payment;
	}

}