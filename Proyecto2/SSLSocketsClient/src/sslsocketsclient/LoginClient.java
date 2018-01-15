/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsclient;

/**
 *
 * @author karol
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

class LoginClient {
  public LoginClient(String nombre, String pass, String respuesta) {
    
      try {
      SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
      PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      
      output.println(nombre);      
      output.println(pass);
      output.println("2");
      output.println("null");
      output.flush();
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String response = input.readLine();
      System.out.println(response);
      respuesta=response;
      output.close();
      input.close();
      socket.close();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }/* finally {
      System.exit(0);
    }*/
  }
}