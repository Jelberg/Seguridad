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
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author karol
 */
  
public class RunServer {
    
    private SSLServerSocket serverSocket;     
    
    public RunServer() throws Exception {
    SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
        .getDefault();
    serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
  }
    
    
    void runServer() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, CertificateException, SignatureException, KeyStoreException, Exception {
    while (true) {
      try {
        System.err.println("Waiting for connection...");
        SSLSocket socket = (SSLSocket) serverSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        System.out.println("ENTRAMOS AL SERVER");
        String userName = input.readLine();
         System.out.println(userName);
        String password = input.readLine();
        System.out.println(password);
        Double saldo = Double.valueOf(input.readLine());
        System.out.println(saldo.toString());
        String opc = input.readLine();
         System.out.println(opc);
        Usuario u = new Usuario(userName, password, saldo);

        
        
        if (opc.compareTo("1")==0) {
          System.out.println("Inscripción");
          Inscripcion i = new Inscripcion();
          String respuesta = i.registro(u);
          System.out.println("Respuesta Servidor: " + respuesta);
          output.println("Respuesta Servidor: " + respuesta);
        } 
          if (opc.compareTo("2")==0) {
             System.out.println("Login en Server");
             LoginServer i = new LoginServer();
             String respuesta = i.login(userName,password);
             System.out.println("Respuesta Servidor: " + respuesta);
             output.println("Respuesta Servidor: " + respuesta);

        }           
        if (opc.compareTo("3")==0) {
            System.out.println("Sumar Saldo");
            SumarSaldoVendedor i = new SumarSaldoVendedor();
            String respuesta = i.sumarSaldo(u, saldo);
            System.out.println("Respuesta Servidor: " + respuesta);
            output.println("Respuesta Servidor: " + respuesta);
        }
                 
          if (opc.compareTo("4")==0) {
              System.out.println("Certificado");
              CertIssuer i = new CertIssuer();
              i.generar();
              GenCert j = new GenCert();
              //j.Generar();
              
              String respuesta ="Generó";
              System.out.println("Respuesta Servidor: " + respuesta);
              output.println("Respuesta Servidor: " + respuesta);
        }        
      
        output.close();
        input.close();
        socket.close();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }
}
