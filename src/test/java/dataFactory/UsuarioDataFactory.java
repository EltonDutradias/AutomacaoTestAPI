package dataFactory;

import pojo.UsuarioPojo;

public class UsuarioDataFactory {
    public static UsuarioPojo criaUsuarioComun( String login, String senha){

 UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("eltondutradias1");
        usuario.setUsuarioSenha("123456");
        return usuario;
    }
}
