package com.pe.proyecto.java.Proyecto11.controllers;

import com.pe.proyecto.java.Proyecto11.dao.UsuarioDao;
import com.pe.proyecto.java.Proyecto11.model.Usuario;
import com.pe.proyecto.java.Proyecto11.utils.JWTUtils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtils jwtUtils;

    /* @RequestMapping(value = "api/usuarios/{id}")
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Alonso");
        usuario.setApellido("Garcia");
        usuario.setEmail("123@gmail.com");
        usuario.setTelefono("922389405");
        usuario.setPassword("123456");

        return usuario;
    }
*/
    @RequestMapping(value = "api/usuarios",  method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader (value = "Authorization") String token ){

        if (!validarToken(token)){
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioId=jwtUtils.getKey(token);

        return usuarioId != null;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrar(@RequestBody Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String hash = argon2.hash(1,1024,1, usuario.getPassword());

        usuario.setPassword(hash);

         usuarioDao.registrar(usuario);
    }






    @RequestMapping(value = "editar")
    public Usuario editar(){
        Usuario usuarios = new Usuario();
        usuarios.setNombre("Alonso");
        usuarios.setApellido("Garcia");
        usuarios.setEmail("123@gmail.com");
        usuarios.setTelefono("922389405");
        usuarios.setPassword("123456");

        return usuarios;
    }

    @RequestMapping( value = "api/usuarios/{id}" , method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader (value = "Authorization") String token ,@PathVariable Long id){
        if (!validarToken(token)){
            return ;
        }
        usuarioDao.eliminar(id);
    }




}
