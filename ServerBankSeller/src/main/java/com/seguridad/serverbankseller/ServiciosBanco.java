/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.serverbankseller;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Elberg
 */
@Path("/BankSeller")
public class ServiciosBanco {
    private Connection conn = Sql.getConInstance();
    private int RESULT_CODE_OK=200;
    private int RESULT_CODE_FAIL=300;
    private int RESULT_CLINTE_FAIL =400;
    private static String DEFAULT_ENCODING1="UTF-8";
    Gson gson = new Gson();
    
    /**
     * Trae toda la informacion del banco
     * @return 
     */
    @GET
    @Path("/getInfoSeller")
    @Produces("application/json")
    public String informacion (){
    String query = "Select * from bankseller";
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Banco bank = null;
            while (rs.next()) {
                String cliente = rs.getString("clientebs");
                int saldo = rs.getInt("saldobs");
                int deposito = rs.getInt("depositobs");

                bank = new Banco(cliente, saldo, deposito);

            }

            return gson.toJson(bank);

        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @GET
    @Path("/setPago")
    @Produces("application/json")
    public String set(@QueryParam("pago") int pago,
                      @QueryParam("usuario") String userparam){
    String query = "Select * from bankseller";
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Banco bank = null;
            while (rs.next()) {
                String cliente = rs.getString("clientebs");
                int saldo = rs.getInt("saldobs");
                int deposito = rs.getInt("depositobs");

                bank = new Banco(cliente, saldo, deposito);

            }

            return gson.toJson(bank);

        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
