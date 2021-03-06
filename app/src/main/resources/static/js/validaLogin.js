function validaLogin(event) {
    if (validarCampoSenha()){
        event.currentTarget.submit()
        return true
    }
    return false
}

function alerta(){
    alert('Usuário ou senha incorretos!')
    return false;
}

function validarCampoSenha() {
    let user = document.getElementById('first_name')
    let senha = document.getElementById('password')

    if (user.value === '') {
        alert('Informe um nome para autenticar')
        user.classList.remove('input-fancy')
        user.classList.add('is-danger')
        return false
    }

    if (senha.value === '') {
        alert('Informe uma senha para autenticar')
        senha.classList.remove('input-fancy')
        senha.classList.add('is-danger')
        return false
    }
    return true
}