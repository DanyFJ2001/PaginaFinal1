package com.itsqmet.app_hotel.Servicio;

import com.itsqmet.app_hotel.Entidad.Admin;
import com.itsqmet.app_hotel.Entidad.Cliente;
import com.itsqmet.app_hotel.Entidad.Proveedor;
import com.itsqmet.app_hotel.Repositorio.AdminRepositorio;
import com.itsqmet.app_hotel.Repositorio.ClienteRepositorio;
import com.itsqmet.app_hotel.Repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private AdminRepositorio adminRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.findByUsername(username);
        if (cliente != null) {
            return new User(cliente.getUsername(), cliente.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + cliente.getRol().toString())));
        }

        Proveedor proveedor = proveedorRepositorio.findByUsername(username);
        if (proveedor != null) {
            return new User(proveedor.getUsername(), proveedor.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString())));
        }


        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}

