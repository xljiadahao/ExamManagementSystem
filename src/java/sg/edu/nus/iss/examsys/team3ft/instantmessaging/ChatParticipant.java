package sg.edu.nus.iss.examsys.team3ft.instantmessaging;

import java.io.Serializable;
import java.util.concurrent.Callable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;

/**
 *
 * @author Lei
 */
@ViewScoped
@Named
public class ChatParticipant implements Serializable {
	
	private static final long serialVersionUID = 1L;
        
        @EJB UserBean userBean;
	
	private String name;
	private String message;
	
	@Inject ChatRoom chatRoom;

        @PostConstruct
        public void init() {
            String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
            name = userBean.findUserByID(loginID).getUserName();
        }
        
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void sendMessage(final String channel) {
		System.out.println(channel + " >> message: " + message);
		chatRoom.guard(new Callable<String>(){
                    @Override
                    public String call() throws Exception {
                        chatRoom.addMessage(channel, name + ": " + message);
                        return "";
                    }
		});
		message = "";
		EventBus bus = EventBusFactory.getDefault().eventBus();
//                if ("chatc".equals(channel)) {
//                    bus.publish("/chat/c", "xljiadahao");
//                } else if ("chatc1".equals(channel)) {
//                    bus.publish("/chat/c1", "xljiadahao");
//                }
                bus.publish("/chat", "team3ft");
	}
	
}
