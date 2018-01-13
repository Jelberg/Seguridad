package com.seguridad1718.serverbankclient;

import com.google.gson.Gson;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//imports para poder hacer el recuperar password
//FIN de imports para poder hacer el recuperar password
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.sql.*;
//imports para hacer el encrypt y decrypt
import java.io.IOException;
import java.io.UnsupportedEncodingException;



@Path("/ServiciosCliente")
public class ServiciosCliente {

    private Connection conn = Sql.getConInstance();
    private int RESULT_CODE_OK=200;
    private int RESULT_CODE_FAIL=300;
    private int RESULT_CLINTE_FAIL =400;
    private static String DEFAULT_ENCODING1="UTF-8";
    Gson gson = new Gson();

    public ServiciosCliente() {}

    /**
     * Funcion encargada de realizar la encriptación de un password
     * @param password El password a ser encriptado
     * @return String encriptado con BASE64
     */
    private static String encryptPassword(@QueryParam("password") String password) {
        //Instanciamos un encriptador BASE64
        BASE64Encoder enc = new BASE64Encoder();
        try {
            //Utilizando la codificacion por defecto (UTF-8) encriptamos el string
            return enc.encode(password.getBytes(DEFAULT_ENCODING1));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * Funcion encargada de realizar la desencriptacion de un password
     * @param password El password a desencriptar
     * @return String con el contenido original antes de ser encriptado
     */
    private static String decryptPassword(String password) {
        //Instanciamos un decodificador de BASE64
        BASE64Decoder dec = new BASE64Decoder();
        try {
            //Realizamos la decodificacion mediante el proceso inverso de la encriptacion
            return new String(dec.decodeBuffer(password), DEFAULT_ENCODING1);
        } catch (IOException e) {
            return null;
        }
    }

    @GET
    @Path("/informacionCliente")
    @Produces("application/json")
    public String informationUser(@QueryParam("username") String userparam) {
        String query = "SELECT * FROM INFORMACIONCLIENTE('" + userparam + "')";
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Cliente user = null;

            while (rs.next()) {
                String username = rs.getString("USERNAME");
                String ci = rs.getString("CI");
                String password = rs.getString("PASS");
                float saldo = rs.getFloat("SALDO");

                user = new Cliente(ci, username, password, saldo);

            }

            return gson.toJson(user);

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    /**
     * Metodo que es llamado a traves del web service para agregar a la base de datos los parametros recibidos
     *
     * @param username
     * @param password
     * @param ci
     * @param saldo
     * @return
     */
    @GET
    @Path("/insertarCliente")
    @Produces("application/json")
    public String insertarCliente(@QueryParam("username") String username,
                                  @QueryParam("password") String password,
                                  @QueryParam("ci") String ci,
                                  @QueryParam("saldo") Float saldo) {


        password=encryptPassword(password);
        String insertUserQuery = " SELECT * FROM REGISTRAR('" + username + "','" + password + "','" + ci + "','" + saldo  + "')";

        Cliente clienteFail= new Cliente();

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(insertUserQuery);
            Cliente cliente = null;
            Boolean valida = false;

            int id = 0;

            while (rs.next()) {

                valida=true;
                id = rs.getInt("registrar");

            }

            if(valida == true){
                cliente = new Cliente(ci, username, password, saldo);
                cliente.set_status(Integer.toString(RESULT_CODE_OK));
            return gson.toJson(cliente);
            }
            else{
                clienteFail.set_status(Integer.toString(RESULT_CODE_FAIL));
                return gson.toJson(clienteFail);
            }
        }
        catch (SQLException e) {
            clienteFail.set_status(e.getSQLState());
            return gson.toJson(clienteFail);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    /***
     * Metodo que elimina a un usuario
     * @param ci
     * @return
     */
    @GET
    @Path("/eliminarCliente")
    @Produces("application/json")
    public String eliminarCliente(@QueryParam("ci") String ci){

        String query="SELECT ELIMINARCLIENTE('"+ ci +"')";

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            int delete =0;

            while(rs.next()){

                delete = rs.getRow();

            }
            return gson.toJson(delete);
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Metodo que realiza cambios en el usuario
     * @param userparam
     * @param password
     * @param saldo
     * @return
     */
    @GET
    @Path("/modificarSaldo")
    @Produces("application/json")
    public String updateUser(@QueryParam("username") String userparam, @QueryParam("password") String password,
                             @QueryParam("saldo") float saldo){

        String query="SELECT MODIFICARSALDO('"+userparam+"','"+password+"','"+saldo+"')";

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            int update =0;

            while(rs.next()){

                update = rs.getRow();

            }
            return gson.toJson(update);
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }



    /**
     * Metodo que es llamado a traves del web service para consultar un usuario existente en la base de datos
     * @param username
     * @param password
     * @return el usuario con los datos que trae la consulta
     */
    @GET
    @Path("/iniciarSesion")
    @Produces("application/json")
    public String getUser(@QueryParam("username") String username, @QueryParam("password") String password)
    {
        password= encryptPassword(password);

        String query="SELECT * FROM INICIARSESION('"+username+"','"+password+"')";

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            int idUser =0;
            String ci="";

            while(rs.next()){

                 idUser = rs.getInt("iniciarsesion");
                 ci = rs.getString("CI");

            }
            if (idUser !=0){

                Cliente clienteResult=new Cliente();
                clienteResult.set_status(Integer.toString(RESULT_CODE_OK));
                clienteResult.setCi(ci);
                return gson.toJson(clienteResult);
            }
            else {
                Cliente userFail = new Cliente();
                userFail.set_status(Integer.toString(RESULT_CLINTE_FAIL));
                return gson.toJson(userFail);
            }
        }
        catch (NullPointerException e){
            return e.getMessage();
        }
        catch (SQLException e){
            String error= e.getSQLState();
            return e.getSQLState();
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }

    @GET
    @Path("/iniciar")
    @Produces("text/plain")
    public String getUser()
    {
        return "prueba";
    }

}
