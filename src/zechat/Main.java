/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zechat;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author vi
 */
public class Main {

          /**
           * @param args the command line arguments
           * @throws java.net.UnknownHostException
           * @throws java.net.SocketException
           */
          public static void main(String[] args) throws UnknownHostException, SocketException {
                    // TODO code application logic here
                    new Chat()
                        .startChat();
                    
          }
          
}
