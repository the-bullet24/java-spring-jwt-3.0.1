package com.pe.proyecto.java.Proyecto11.dao;

import com.pe.proyecto.java.Proyecto11.model.Usuario;

import java.util.List;

public interface UsuarioDao {


    List<Usuario> getUsuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);

    //boolean validarEmailPassword(Usuario usuario);


}
