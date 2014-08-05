/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zechat;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author vi
 */
public final class Contato {
    private String nick;
    private String ip;
    private final ArrayList<Mensagem> msgs;
    private final JScrollPane scroll;
    private final StringBuilder buffer;
    private final JTextPane textArea;
    private int keepAlive;

    public Contato(String nick, String ip, Chat c){
        setIp(ip);
        setNick(nick);
        msgs = new ArrayList<>();
        scroll = new JScrollPane();
        textArea = new JTextPane();
        buffer = new StringBuilder();
                    this.buffer.append("<html><head>"
                            +"<meta charset=\"UTF-8\">" 
                            +"</head><body>");
        scroll.setAutoscrolls(true);
        ((DefaultCaret)textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scroll.setEnabled(true);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBar(null);
        textArea.setEditable(false);
        textArea.setContentType("text/html");
        

        scroll.setViewportView(textArea);
    }
          
    public String getNick() {
        return nick;
    }
    
    public JScrollPane getScroll(){
        return scroll;
    }
    /**
     *
     * @return nick
     */
    @Override
    public String toString(){
        return nick;
    }
    /**
     * @param nick the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the msgs
     */
    public ArrayList<Mensagem> getMsgs() {
        return msgs;
    }
    
    public void addMesg(Mensagem m){
        this.msgs.add(m);
        buffer.append("<p><b>")
                .append(nick)
                .append("</b> diz: ")
                .append(replaceString(breakMsg(m.getMsg())))
                .append("</p>");
        writeText();
    }
    
    public void msgSent(String msg, String myNick){
        buffer.append("<p><b>")
                .append(myNick)
                .append("</b> diz: ")
                .append(replaceString(breakMsg(msg)))
                .append("</p>");
        writeText();
    }
    
    private String breakMsg(String m){
        if(m.length()>60){
            return m.substring(0, 60)+"<br>"+breakMsg(m.substring(61));
        }else return m;
    }
    
    private void writeText() {
        textArea.setText(buffer.toString()
                +"</body></html>");
    }

    private String replaceString(String m){
        return
        m.replace(":)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/feliz.png")+"\">")
                .replace("oO", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/10.png")+"\">")
                .replace("]:D", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/11.png")+"\">")
                .replace("(good)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/12.png")+"\">")
                .replace("=D", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/13.png")+"\">")
                .replace(":|", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/14.png")+"\">")
                .replace("\\o/", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/15.png")+"\">")
                .replace("8)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/16.png")+"\">")
                .replace("zzz", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/17.png")+"\">")
                .replace(":o", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/18.png")+"\">")
                .replace(":p", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/19.png")+"\">")
                .replace(":'(", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/2.png")+"\">")
                .replace("*_*", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/20.png")+"\">")
                .replace(":s", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/21.png")+"\">")
                .replace("*_-", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/22.png")+"\">")
                .replace("(Y)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/23.png")+"\">")
                .replace("(N)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/24.png")+"\">")
                .replace("kkk", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/25.png")+"\">")
                .replace("ecaaa", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/26.png")+"\">")
                .replace("=)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/3.png")+"\">")
                .replace(";)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/4.png")+"\">")
                .replace("-.-", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/5.png")+"\">")
                .replace("x)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/6.png")+"\">")
                .replace("(bad)", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/7.png")+"\">")
                .replace("Oo", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/8.png")+"\">")
                .replace("=(", "<img src=\""+ 
                    getClass().getResource("/zechat/emoticons/9.png")+"\">");
    }
    
    public void resetKeepAlive() {
        keepAlive = 0;
    }
        
    public void sumIgnored(){
        keepAlive++;
    }
    
    public int getKeepAlive(){
        return keepAlive;
    }
}
