package com.pe.proyecto.java.Proyecto11.dao;

import com.pe.proyecto.java.Proyecto11.model.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;


@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    EntityManager entityManager;  //Conexion a la BD



    @Override
    public List<Usuario> getUsuarios() {
                                               // No colocamos el nombre de la tabla porque trabajamos con los objetos de nuestra clase
        String query = "FROM Usuario"; //Esta consulta lo hace sobre Hibarnate
        return  entityManager.createQuery(query).getResultList();

    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);

    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email= :email ";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        }


        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        if (argon2.verify(passwordHashed, usuario.getPassword())){
            return   lista.get(0);

     }
        return null;
    }














/* @Override
    public boolean validarEmailPassword(Usuario usuario) {
        String query = "FROM Usuario WHERE email= :email ";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()){
            return false;
        }



        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String Passhash = lista.get(0).getPassword();

        return argon2.verify(Passhash, usuario.getPassword());
*/

        //No valido

        //boolean passigual = argon2.verify(Passhash, usuario.getPassword());

        /* if (lista.isEmpty()){
            return false;
        }else {
            return true;
        }
        */

        //return !lista.isEmpty();

}
