package modelo.enums;

import java.util.EnumSet;
import java.util.Set;

public enum NivelAcesso {
    CLIENTE(EnumSet.of(Permissao.CONSULTAR, Permissao.FAZER_PEDIDO)),
    ADMIN(EnumSet.of(Permissao.CONSULTAR, Permissao.CADASTRAR, Permissao.EXCLUIR,Permissao.ALTERAR));

    private final Set<Permissao> permissoes;

    NivelAcesso(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public boolean temPermissao(Permissao p) {
        return permissoes.contains(p);
    }
}
