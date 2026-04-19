import controle.Dados;
import controle.SistemaControle;

public class App {
    public static void main(String[] args) {
        System.out.println("TDE1 - Java");

        Dados infos = new Dados();
        infos.carregarDados();

        SistemaControle gerenciador = new SistemaControle(infos);

        gerenciador.mostrarMenu();


    }
}
