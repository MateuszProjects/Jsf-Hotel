package projectotel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPayment;

	private double amount;

	private byte paid;

	@Temporal(TemporalType.DATE)
	private Date paymentData;

	// bi-directional many-to-one association to Boocking
	@ManyToOne
	@JoinColumn(name = "idBoocking")
	private Boocking boocking;

	public Payment() {
	}

	public Integer getIdPayment() {
		return this.idPayment;
	}

	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public byte getPaid() {
		return this.paid;
	}

	public void setPaid(byte paid) {
		this.paid = paid;
	}

	public Date getPaymentData() {
		return this.paymentData;
	}

	public void setPaymentData(Date paymentData) {
		this.paymentData = paymentData;
	}

	public Boocking getBoocking() {
		return this.boocking;
	}

	public void setBoocking(Boocking boocking) {
		this.boocking = boocking;
	}

}