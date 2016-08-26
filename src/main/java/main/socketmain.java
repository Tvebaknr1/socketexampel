/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emil
 */
public class socketmain {

    static String ip = "localhost";
    static int portNum = 8080;
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 2) {
            ip = args[0];
            portNum = Integer.parseInt(args[1]);

        }
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, portNum));
        while (true) {

            Socket link = ss.accept();

            System.out.println("new client connection");
            handleclient(link);
        }

    }
    private static void handleclient(Socket s){
        try {
            Scanner scr = new Scanner(s.getInputStream());
            PrintWriter prnt = new PrintWriter(s.getOutputStream(),true);
            String msg = "";
            prnt.println("wellcome");
            
            while (!msg.equals("STOP")) {
                msg = scr.nextLine();
                if(msg.length()>=6&&msg.substring(0, 6).equals("UPPER#"))
                {
                    prnt.println(msg.substring(6, msg.length()).toUpperCase());
                }
                else if(msg.length()>=6&&msg.substring(0, 6).equals("LOWER#")){
                    prnt.println(msg.substring(6,msg.length()).toLowerCase());
                }
                else if (msg.length()>=8&&msg.substring(0, 8).equals("REVERSE#")){
                    String str = new StringBuilder(msg.substring(8, msg.length())).reverse().toString();
                    prnt.println(str.substring(0,1).toUpperCase()+str.substring(1, str.length()));
                }
                else if(msg.length()>=10&&msg.substring(0, 10).equals("TRANSLATE#")){
                    switch(msg.substring(10, msg.length()).toLowerCase()){
                        case"hund":{
                            prnt.println("dog");
                        }
                        default:{
                            prnt.println("#NOT_FOUND");
                        }
                    }
                }
                
            }
            scr.close();
            prnt.close();
            s.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
