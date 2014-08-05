/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zechat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vi
 */
public class Comunica  implements Runnable{
    Initiater ini;
    Responder resp;
    public static final int porta = 7690;
    byte msg_rec[];
    ArrayList<String> myIps;
    
    public Comunica(Chat c) throws SocketException{
              ini  = new Initiater();
              resp = new Responder(c);
              ini.addListener(resp);
              myIps = new ArrayList<>();
              getMyIP();
    }
    
    @Override
    public void run() {
        try {
                Contato atual=null;
                DatagramSocket conexao = new DatagramSocket(porta); //9874
            do{
                Mensagem m;
                String ip;
                msg_rec= new byte[1000];
                DatagramPacket pacotinho = new DatagramPacket(msg_rec, msg_rec.length);

                try {
                    conexao.receive(pacotinho);
                } catch (IOException ex) {}
                m = new Mensagem(new String(pacotinho.getData()).trim());
                ip = pacotinho.getAddress().toString().split("/")[1];
                System.out.println(m.getMsg());

                if(!myIps.contains(ip))
                    ini.gotMessage(m, ip);

            }while(true);
        } catch (SocketException ex) { System.out.println("Mensagem enviada"+ex.getMessage());}
    }
    
    public static void enviar(String msg, String ipAddr) throws UnknownHostException, IOException{
        if(ipAddr.isEmpty())return;
        try {
            byte msg_envio[] = msg.getBytes();
            DatagramPacket pacotinho;
            DatagramSocket conexao = new DatagramSocket();
            
            if(ipAddr.equals("broadcast")){
                ArrayList<String> ipsBC = getBroadCast();
                for(String ss : ipsBC){
                    pacotinho = new DatagramPacket(msg_envio, msg_envio.length, 
                        InetAddress.getByName(ss), porta);
                    conexao.send(pacotinho);
                }
                System.out.println("Broadcast Enviado");
            }else{
                pacotinho = new DatagramPacket(msg_envio, msg_envio.length, 
                        InetAddress.getByName(ipAddr), porta);
                conexao.send(pacotinho);
                System.out.println("Mensagem enviada"+msg);
            }
        } catch (SocketException ex) {System.out.println("Mensagem enviada"+ex.getMessage());}
    }

    private void getMyIP() throws SocketException{
          Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
          for (; n.hasMoreElements();)
          {
              NetworkInterface e = n.nextElement();

              Enumeration<InetAddress> a = e.getInetAddresses();
              for (; a.hasMoreElements();)
              {
                  InetAddress addr = a.nextElement();
                  if (addr instanceof Inet4Address)
                              myIps.add(addr.getHostAddress());
              }
          }
    }
    private static ArrayList<String> getBroadCast() throws SocketException{
        ArrayList<String> lista = null;
              Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        lista = (lista != null) ? lista : new ArrayList<String>(); 
                              NetworkInterface networkInterface = interfaces.nextElement();
                              if (networkInterface.isLoopback())
                                        continue;    // Don't want to broadcast to the loopback interface
                              for (InterfaceAddress interfaceAddress :
                                        networkInterface.getInterfaceAddresses()) {
                                        InetAddress broadcast = interfaceAddress.getBroadcast();
                                        if(broadcast == (null)) continue;
                                        lista.add(broadcast.toString().split("/")[1]);
                              }
                    }
                    return lista;
    }
    
}



interface HelloListener {
    public void avisaMsgRecebida(Mensagem msg, String ip);
}


class Initiater {
    List<HelloListener> listeners = new ArrayList<>();

    public void addListener(HelloListener toAdd) {
        listeners.add(toAdd);
    }

    
    public void gotMessage(Mensagem msg,String ip) {
        
        // Notify everybody that may be interested.
        for (HelloListener hl : listeners){
                  hl.avisaMsgRecebida(msg, ip);         
        }
    }
}


class Responder implements HelloListener {
          private final Chat c;
          public Responder(Chat c){
                    this.c = c;
          }
    @Override
    public void avisaMsgRecebida(Mensagem msg, String ip) {
              try {
                  c.trataRecebimento(msg, ip);
              } catch (IOException ex) {
                  Logger.getLogger(Responder.class.getName()).log(Level.SEVERE, null, ex);
              }
    }
}
