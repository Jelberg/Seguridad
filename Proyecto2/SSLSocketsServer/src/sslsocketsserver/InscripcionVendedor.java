/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author karol
 */
public class InscripcionVendedor {
    public InscripcionVendedor(Usuario u) {
    
      try {
      SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
      PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      output.println(u.getNombre());
      output.println(u.getPass());      
      output.println(u.getSaldo().toString());
      output.println("1");
      System.out.println(u.getSaldo().toString());
      output.flush();
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String response = input.readLine();
      System.out.println(response);

      output.close();
      input.close();
      socket.close();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } finally {
      System.exit(0);
    }
  }
}
