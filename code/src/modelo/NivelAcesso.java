package modelo;

import java.util.EnumSet;
import java.util.Set;

/**
 * enum é melhor para controlar o nivel de acesso do usuario
 * e mais flexivel para caso surgir novos niveis
 */
public enum NivelAcesso {
    CLIENTE(EnumSet.of(Permissao.CONSULTAR)),
    ADMIN(EnumSet.of(Permissao.CONSULTAR, Permissao.CADASTRAR, Permissao.EXCLUIR));

    private final Set<Permissao> permissoes;

    NivelAcesso(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public boolean temPermissao(Permissao p) {
        return permissoes.contains(p);
    }
}
