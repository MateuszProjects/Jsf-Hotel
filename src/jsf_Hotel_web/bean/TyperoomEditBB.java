package jsf_Hotel_web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.TyperoomDAO;
import projectotel.entities.Room;
import projectotel.entities.Typeroom;

@ManagedBean
@ViewScoped
public class TyperoomEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_TYPE_ROOM = "typeroom?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	TyperoomDAO typeroomDAO;

	private Integer idTypeRoom;
	private int roomStandartStar;
	private String roomTypeDescription;

	public Integer getIdTypeRoom() {
		return idTypeRoom;
	}

	public void setIdTypeRoom(Integer idTypeRoom) {
		this.idTypeRoom = idTypeRoom;
	}

	public int getRoomStandartStar() {
		return roomStandartStar;
	}

	public void setRoomStandartStar(int roomStandartStar) {
		this.roomStandartStar = roomStandartStar;
	}

	public String getRoomTypeDescription() {
		return roomTypeDescription;
	}

	public void setRoomTypeDescription(String roomTypeDescription) {
		this.roomTypeDescription = roomTypeDescription;
	}

	private Typeroom typeroom = null;
	private Room room = null;
	
	@PostConstruct
	public void postConstruct() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		typeroom = (Typeroom) session.getAttribute("typeroom");

		if (typeroom != null) {
			session.removeAttribute("typeroom");
		}
		if (typeroom != null && typeroom.getIdTypeRoom() != null) {
			setIdTypeRoom(typeroom.getIdTypeRoom());
			setRoomStandartStar(typeroom.getRoomStandartStar());
			setRoomTypeDescription(typeroom.getRoomTypeDescription());
		}	

	}

	/**
	 * Funkcja w warstwie web, do sprawdzania poprawnoœci
	 * danych  przekazywanych przez u¿ywkownika.
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (roomTypeDescription == null || roomTypeDescription.length() == 0) {
			ctx.addMessage(null, new FacesMessage("idBooking Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			typeroom.setRoomStandartStar(roomStandartStar);
			typeroom.setRoomTypeDescription(roomTypeDescription);
			result = true;
		}

		return result;
	}

	/**
	 * Funkcja w warstwie web, do zapisaywania 
	 * danych do bazy danych.
	 * @return
	 */
	public String saveData() {

		if (typeroom == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("B³êdne uzycie systemu"));
			return PAGE_STAY_AT_THE_SAME;
		}

		if (!validate()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (typeroom.getIdTypeRoom() == null) {
				typeroomDAO.createTyperoom(typeroom);
			} else {
				typeroomDAO.merge(typeroom);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_TYPE_ROOM;
	}

}
