package controle.io;
import java.util.Scanner;

public class Leitor implements LeitorInterface {
    private final Scanner scan;

    public Leitor(Scanner scan) {
        this.scan = scan;
    }

    public String linha(String m) {
        System.out.println(m);
        return scan.nextLine();
    }

    public int inteiro(String m) {
        try {
            return Integer.parseInt(linha(m).trim());
        } catch(NumberFormatException e){
            throw new IllegalArgumentException("Input invalido: " + m);
        }
    }

    public double real(String m) {
        try {
            return Double.parseDouble(linha(m).trim());
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("Input invalido: " + m);
        }
    }

    public void escrever(String m) {
        System.out.println(m);
    }

}
