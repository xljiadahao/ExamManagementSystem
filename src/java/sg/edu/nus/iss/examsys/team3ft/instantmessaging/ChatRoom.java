/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.examsys.team3ft.instantmessaging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Lei
 */
@ApplicationScoped
@Named
public class ChatRoom implements Serializable {

    private Lock lock = new ReentrantLock();
    private Map<String, StringBuilder> chatMap = new HashMap<String, StringBuilder>();

    public void addMessage(String channel, String msg) {
        StringBuilder sb = getChatMap().get(channel);
        if (sb == null) {
            sb = new StringBuilder();
            getChatMap().put(channel, sb);
        }
        sb.append("\n").append(msg);
    }
    
    public void guard(Runnable r) {
        lock.lock();
        try {
            r.run();
        } finally {
            lock.unlock();
        }
    }

    public String guard(Callable<String> c) {
        lock.lock();
        try {
            return (c.call());
        } catch (Exception ex) {
        } finally {
            lock.unlock();
        }
        return ("*** chatroom error ***");
    }

    public String allChats(String channel) {
        //return (sb.toString());
        if (getChatMap().get(channel) != null) {
            return (getChatMap().get(channel).toString());
        } else {
            return "";
        }

    }

    public String tsAllChats(final String channel) {
        //return (guard(() -> allChats()));
        return guard(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return allChats(channel);
            }
        });
    }

    /**
     * @return the chatMap
     */
    public Map<String, StringBuilder> getChatMap() {
        return chatMap;
    }

    /**
     * @param chatMap the chatMap to set
     */
    public void setChatMap(Map<String, StringBuilder> chatMap) {
        this.chatMap = chatMap;
    }

}
