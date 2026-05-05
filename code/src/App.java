import controle.Dados;
import controle.SistemaControle;

public class App {
    public static void main() {
        System.out.println("TDE1 - Java");

        Dados infos = new Dados();
        infos.carregarDados();

        SistemaControle gerenciador = new SistemaControle(infos);

        if(gerenciador.validarLogin()) {
            gerenciador.menu();
        } 


    }
}
