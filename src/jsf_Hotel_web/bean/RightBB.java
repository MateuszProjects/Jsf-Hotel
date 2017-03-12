package jsf_Hotel_web.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import into.jsf.dao.RightDAO;
import projectotel.entities.Right;

@ManagedBean
public class RightBB {

	private static final String PAGE_RIGHT_EDIT = "rightsedit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	RightDAO rightDAO;

	/**
	 * Funkcja w warstwie web, pobieraj¹ca dane 
	 * pobrane w warstwie dao.
	 * @return
	 */
	public List<Right> getList() {
		return rightDAO.getFullList();
	}

	/**
	 * Funkcja w warstwie web do tworzenia 
	 * nowego obiekt right.
	 * @return
	 */
	public String newRight() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Right right = new Right();
		session.setAttribute("right", right);
		return PAGE_RIGHT_EDIT;
	}

	/**
	 * Funkcja w warstwie web, do edycji
	 * obiektu right.
	 * @param right
	 * @return
	 */
	public String editRight(Right right) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("right", right);
		return PAGE_RIGHT_EDIT;
	}

	/**
	 * Funkcja w warstwie, web do usuwania 
	 * obiektów right.
	 * @param right
	 * @return
	 */
	public String delete(Right right) {
		rightDAO.remove(right);
		return PAGE_STAY_AT_THE_SAME;
	}

}
