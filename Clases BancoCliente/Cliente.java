import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

/**
 * Clase Usuario del Modulo 1
 */

@XmlRootElement
public class Cliente {

    private String _username;
    private String  _password;
    private String _ci;
    private float _saldo;
    private String _status;



    /**
     * Constructor vacio
     */
    public Cliente(){};

    /**
     * Constructor para crear el usuario solo con el id
     * @param ci userId
     */
    public Cliente(String ci){
        _ci=ci;
    }

    /**
     * Constructor con todos los atributos
     * @param ci
     * @param user
     * @param password
     * @param saldo
     */
    public Cliente(String ci,String user,String password, float saldo)
    {
        _ci = ci;
        _username = user;
        _password = password;
        _saldo = saldo;
    }

    /***
     *
     * @param ci
     * @param user
     * @param password
     */
    public Cliente(String ci,String user,String password)
    {
        _ci = ci;
        _username = user;
        _password = password;
    };



    /**
     * Constructor para solo el username y el password
     * @param user
     * @param password
     */
    public Cliente(String user,String password)
    {
        _username = user;
        _password = password;
    }





    public String getCi()
    {
        return _ci;
    }

    public void setCi(String ci)
    {
        this._ci = ci;
    }

    public String getUser()
    {
        return _username;
    }

    public void setUser(String user)
    {
        this._username = user;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(String password)
    {
        this._password = password;
    }

    public float getSaldo()
    {
        return _saldo;
    }

    public void setSaldo(float saldo)
    {
        this._saldo = saldo;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }
}
