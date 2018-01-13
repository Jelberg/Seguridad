package com.seguridad1718.serverbankclient;


import com.seguridad1718.serverbankclient.ServiciosCliente;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/* Se define la raiz de todo el proyecto*/
@ApplicationPath("/")
public class BancoCliente extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( ServiciosCliente.class );
        return h;
    }
}
