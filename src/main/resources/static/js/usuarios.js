// Call the dataTables jQuery plugin
$(document).ready(function() {

    cargarusuarios();
    actualizarEmailUSuario();

    $('#usuarios').DataTable();
});


function actualizarEmailUSuario() {
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarusuarios(){

    const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        }
    });

    const usuarios = await request.json();


    let listadoHtml = '';

    for (let usuario of usuarios){

        let  telefono= usuario.telefono == null ? 'No telefono' : usuario.telefono;

        let botonEliminar = '<a href="#" onclick = "eliminarUsuario(' + usuario.id +')"  class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a>';

        let usuarioHtml  =  '<tr><td>'+ usuario.id +'</td><td>'+usuario.nombre+' '+usuario.apellido+' </td><td>'
            +usuario.email+'</td><td>'
            +telefono+'</td><td>' + botonEliminar + '</td></tr>';

        listadoHtml += usuarioHtml;

    }

    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}


async function eliminarUsuario(id){



    if(  !confirm( '¿Desea eliminar este usuario?')  ){
        return;
    }


    const request = await fetch('api/usuarios/' + id , {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        }
    });

    location.reload();

}