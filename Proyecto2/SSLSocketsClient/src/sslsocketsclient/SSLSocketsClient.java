/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author karol
 */
public class SSLSocketsClient {
    private static String nombre;
    private static String pass;
    private static Double saldo;
    private static Usuario u;
    private static Security sc = Security.getInstance();
    private static String text;
    private static CaptchaGenerator cap = new CaptchaGenerator();
    private static String respuesta="";
    private static String saldoIn;
    private static String saldoTransf;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
       //Indico donde se encuntran los certificados antes de iniciar el socket
        System.setProperty("javax.net.ssl.keyStore", "src//sslsocketsclient//certs//client//bancovendedor.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","fresa*1984");
        System.setProperty("javax.net.ssl.trustStore", "src//sslsocketsclient//certs//client//vendedorTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "fresa*1984");
        
        
      /*  SSLSocketFactory clientFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket client = clientFactory.createSocket("127.0.0.1", 8888);*/
        String opc="0";        
        
        while (opc.compareTo("5")!=0){
           
            System.out.println("1 REGISTRARSE");
            System.out.println("2 INICIAR SESIÓN");
            System.out.println("3 SOLICITAR DESBLOQUEO");
            System.out.println("4 SOLICITAR CERTIFICADO");
            System.out.println("5 SALIR");
            
            BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));     
        
            opc = brRequest.readLine();
            
            switch (opc){
                case "1":
                    System.out.println("Nombre");
                    nombre = brRequest.readLine();
                    System.out.println("PASS");
                    pass = brRequest.readLine();
                    System.out.println("SALDO INICIAL");
                    saldoIn = brRequest.readLine();
                    
                    while (!sc.validarPassSize(pass) 
                        || (sc.validarMay(pass)[0]==0)                                                   
                        || (sc.validarMay(pass)[1]==0)){
                    
                        if (!sc.validarPassSize(pass))
                            System.out.println("PASS DEBE SER MAYOR A 8 CARACTERES");
                        if (sc.validarMay(pass)[0]==0)
                            System.out.println("PASS DE TENER UNA MAYÚSCULA AL MENOS");
                        if (sc.validarMay(pass)[1]==0)
                            System.out.println("PASS DE TENER UN NÚMERO AL MENOS");
                    System.out.println("ESCRIBA NUEVAMENTE PASS");    
                    pass = brRequest.readLine();                   
                }
                    
                    u = new Usuario(nombre, pass, Double.valueOf(saldoIn));
                    
                    text = cap.generateCaptchaText();
                    cap.renderImage(text);
                   
                    String captcha = brRequest.readLine();
            
                    int cont=0;
                    while(captcha.compareTo(text)!=0){
                        cont++;
                        text = cap.generateCaptchaText();
                        cap.renderImage(text);
                        captcha = brRequest.readLine();
                        if (cont==3){
                            System.err.println("LO HA INTENTADO DEMASIADAS VECES");
                            System.err.println("SALIENDO DE LA APLICACIÓN");
                            System.exit(0);
                        }
                    }
                    
                    new InscripcionClient(u);
                break;
                    
                    case "2":
                    System.out.println("Nombre");
                    nombre = brRequest.readLine();
                    System.out.println("PASS");
                    pass = brRequest.readLine();                                
                    new LoginClient(nombre,pass,respuesta);
                    
                    System.out.println("RRR "+ respuesta);
                    //if (respuesta.compareTo("ACCEDIÓ")==0)
                        u = new Usuario(nombre, pass, null);  
                break;
                        
                case "3":
                   // if (respuesta.compareTo("ACCEDIÓ")==0){
                       System.out.println("SALDO A TRANSFRIR");
                        saldoTransf = brRequest.readLine();
                        new TransferirDineroAVendedor(u,saldoTransf);
                    //}
                    //else
                      //  System.out.println("Debe estar iniciada la sesión");                   
                    
                break;
                
                case "4":
                    new CrearCert(u.getNombre(),u._pass);
                break;
                
                case "5":
                    System.exit(0);
                break;
            } 
        }        
        
  }
}
