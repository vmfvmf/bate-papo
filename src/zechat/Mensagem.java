/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zechat;

/**
 *
 * @author vi
 */
public class Mensagem {
    private String msg;
    //private Date dtEnvio, dtRecebimento;
    private char flag;
    
    public Mensagem(String msg){
        this.msg = msg.substring(1);
        this.flag = msg.charAt(0);
        //this.dt = DateFormat.g();
    }
    
    @Override
    public String toString(){
        return "";
    }
    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the flag
     */
    public char getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(char flag) {
        this.flag = flag;
    }
}
