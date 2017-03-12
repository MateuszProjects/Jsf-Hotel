package projectotel.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the typeroom database table.
 * 
 */
@Entity
@NamedQuery(name = "Typeroom.findAll", query = "SELECT t FROM Typeroom t")
public class Typeroom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTypeRoom;

	private int roomStandartStar;

	private String roomTypeDescription;

	// bi-directional many-to-one association to Room
	@OneToMany(mappedBy = "typeroom")
	private List<Room> rooms;

	public Typeroom() {
	}

	public Integer getIdTypeRoom() {
		return this.idTypeRoom;
	}

	public void setIdTypeRoom(int idTypeRoom) {
		this.idTypeRoom = idTypeRoom;
	}

	public int getRoomStandartStar() {
		return this.roomStandartStar;
	}

	public void setRoomStandartStar(int roomStandartStar) {
		this.roomStandartStar = roomStandartStar;
	}

	public String getRoomTypeDescription() {
		return this.roomTypeDescription;
	}

	public void setRoomTypeDescription(String roomTypeDescription) {
		this.roomTypeDescription = roomTypeDescription;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Room addRoom(Room room) {
		getRooms().add(room);
		room.setTyperoom(this);

		return room;
	}

	public Room removeRoom(Room room) {
		getRooms().remove(room);
		room.setTyperoom(null);

		return room;
	}

}