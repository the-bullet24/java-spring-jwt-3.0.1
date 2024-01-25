package com.pe.proyecto.java.Proyecto11.controllers;

import com.pe.proyecto.java.Proyecto11.dao.UsuarioDao;
import com.pe.proyecto.java.Proyecto11.model.Usuario;
import com.pe.proyecto.java.Proyecto11.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthSession {

    @Autowired
    UsuarioDao usuarioDao;

    @Autowired
    JWTUtils jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String IniciarSesion(@RequestBody Usuario usuario) {

        Usuario usuarioLogueado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);

        if (usuarioLogueado != null) {

            String tokenjwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());

            return tokenjwt;
        }
        return "FAIL";
    }

}
