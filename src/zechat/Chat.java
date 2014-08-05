/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zechat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author vi
 */
public class Chat {
    private String nick;
    private final Comunica c;
    private final ArrayList<Contato> ctts;
    private final FormPrincipal fp;
    private final Timer timer;
    private boolean isLogged;
    private final FormChat fc;
    private Thread thread;

    /**
     * @return the nick
     */
    public String getNick() {
        return nick;
    }          

    public Chat() throws UnknownHostException, SocketException{
        fp = new FormPrincipal(this);
        fc = new FormChat(this);
        c = new Comunica(this);
        ctts = new ArrayList<>();
        isLogged = false;
        
        timer = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                        Comunica.enviar("L"+nick, "broadcast");
                    } catch (IOException ex) {
                        Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Mensagem enviada"+ex.getMessage());
                    }
                for(Contato ctt : ctts){
                    ctt.sumIgnored();
                    if(ctt.getKeepAlive() == 5){
                        cttDisconected(ctt);
                        break;
                    }
                }
            }
        });
    }
          
    public void startChat() throws SocketException{
        thread = new Thread(c);
        thread.start();
        fp.setVisible(true);
        
        timer.start();
    }
    
    public void fazerLogin(String nick) throws IOException{
        this.nick = nick;
        isLogged = true;
        Comunica.enviar("L"+nick, "broadcast");
    }
          
    public boolean contatoRegistrado(String ip){
        for(Contato cs : ctts){
            if(cs.getIp().equals(ip))
                return true;
        }
        return false;
    }   
          
    public void trataRecebimento(Mensagem m, String ip) throws IOException{
        Contato atual = null;
        if(!isLogged) return;
        
        for(Contato cs : ctts){
            if(!contatoRegistrado(ip)) break;
            if(cs.getIp().equals(ip)){
                atual = cs;
            }
        }
        switch(m.getFlag()){
            case 'L':
                if(atual != null){
                    atual.resetKeepAlive();
                }else{
                    ctts.add(new Contato(m.getMsg(),ip,this));
                    fp.updateList();
                    new FrmNotification(m.getMsg()," está online").setVisible(true);
                    Comunica.enviar("L"+nick,ip);
                }
                break;
            case 'F':
                if(atual == null) break;
                cttDisconected(atual);
                fc.checkAbas();
                break;
            case 'M':
                if(atual == null) break;
                if(!fc.isVisible()){
                    fc.setVisible(true);
                }
                if(!fc.hasTab(atual.getNick())){
                    fc.addTab(atual.getScroll(), atual.getNick());
                   
                }else{
                    fc.setTabVisible(nick);
                }
                fc.getTab().setSelectedIndex(
                    fc.getTab().indexOfTab(atual.getNick()));
                atual.addMesg(m);
                break;
        }        
    }

    public void cttDisconected(Contato atual){
        if(ctts.isEmpty()) return;
        ctts.remove(atual);
        fc.getTab().remove(atual.getScroll());
        new FrmNotification(atual.getNick()," se deslogou do chat").setVisible(true);
        fp.updateList();
    }

    public void logout() throws IOException {
        Comunica.enviar("F", "broadcast");
    }

    public ArrayList<Contato> getCtts() {
        return ctts;
    }

    void iniciarChat(int selectedIndex) {
        fc.setVisible(true);
        Contato co = ctts.get(selectedIndex);
        fc.getTab().add(co.getNick(),co.getScroll());
        fc.getTab().setSelectedIndex(fc.getTab().getTabCount()-1);
    }
    
    public String getIpByNick(String nnick, String msg){
        for(Contato co: ctts){
            if(co.getNick().equals(nnick)){
                co.msgSent(msg, nick);
                return co.getIp();
            }
        }
        return null;
    }
    
    void sendMessage(String text, String ip) throws IOException {
        Comunica.enviar("M"+text, ip);
    }
}