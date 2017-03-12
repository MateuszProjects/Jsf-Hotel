package projectotel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRoom;

	@Column(name = "description_room")
	private String descriptionRoom;

	private double pricePerDay;

	private int roomFloor;

	// bi-directional many-to-one association to Boocking
	@OneToMany(mappedBy = "room")
	private List<Boocking> boockings;

	// bi-directional many-to-one association to Typeroom
	@ManyToOne
	@JoinColumn(name = "idTypeRoom")
	private Typeroom typeroom;

	public Room() {
	}

	public Integer getIdRoom() {
		return this.idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public String getDescriptionRoom() {
		return this.descriptionRoom;
	}

	public void setDescriptionRoom(String descriptionRoom) {
		this.descriptionRoom = descriptionRoom;
	}

	public double getPricePerDay() {
		return this.pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getRoomFloor() {
		return this.roomFloor;
	}

	public void setRoomFloor(int roomFloor) {
		this.roomFloor = roomFloor;
	}

	public List<Boocking> getBoockings() {
		return this.boockings;
	}

	public void setBoockings(List<Boocking> boockings) {
		this.boockings = boockings;
	}

	public Boocking addBoocking(Boocking boocking) {
		getBoockings().add(boocking);
		boocking.setRoom(this);

		return boocking;
	}

	public Boocking removeBoocking(Boocking boocking) {
		getBoockings().remove(boocking);
		boocking.setRoom(null);

		return boocking;
	}

	public Typeroom getTyperoom() {
		return this.typeroom;
	}

	public void setTyperoom(Typeroom typeroom) {
		this.typeroom = typeroom;
	}

}