package sg.edu.nus.iss.examsys.team3ft.instantmessaging;

import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

/**
 *
 * @author Lei
 */
@PushEndpoint("/chat")
public class ChatEndpoint {
	
	@OnOpen
	public void open(RemoteEndpoint remote) {
		System.out.println(">> open: " + remote.address());
	}
	
	@OnMessage(encoders = JSONEncoder.class)
	public String message(String msg) {
		return ("chat: " + msg);
	}
        
        @OnClose
	public void close(RemoteEndpoint remote) {
		System.out.println(">> close: " + remote.address());
	}
        
}
