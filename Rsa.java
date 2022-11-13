import java.math.BigInteger;
import java.security.SecureRandom;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Esta classe foi criada para a avaliação de Sistemas de Informacao do
 * Professor
 * Carlos Euzebio
 */
public class Rsa_criptografia {

    public static void main(String[] args) {
        /**
         * JOptionPane e uma biblioteca nativa do Java para mostrar uma combo box, um
         * dialog, uma modal (ha varios nomes),
         * e usaremos muito ela para ficar intuitivo e mais bonito.
         * Optamos por utilizar a JOptionPane ao inves do JAVAFX por ser mais facil
         */

        JOptionPane.showMessageDialog(null, "Bem vindo ao trabalho de criptografia RSA", "SISTEMAS DA INFORMAÇÃO", -1);

        /**
         * Aqui inicializamos as variaveis necessarias.
         */
        String msgcifrada; // Aqui guardaremos a mensagem ja criptografada
        String msgdecifrada; // Aqui guardaremos a mensagem descriptografada
        BigInteger n, d, e;
        int bitlen = 2048;
        /**
         * JTextArea e uma biblioteca nativa do Java para criar um component de texto.
         * Aqui criamos um com 10 linhas e 20 colunas que ira automaticamente quebrar a
         * palavra
         * em novas linhas caso passem de 20 colunas. E nos deixamos como read only
         * ou seja so pode ler e nao editar a mensagem
         */
        JTextArea component = new JTextArea(10, 20);
        component.setWrapStyleWord(true);
        component.setLineWrap(true);
        component.setCaretPosition(0);
        component.setEditable(false);

        /**
         * SecureRandom e uma classe que gera um numero aleatorio para nos,
         * Usamos entao a classe BigInteger para criar um numero inteiro positivo e
         * provavelmente primo
         * Seu tamanho deve ser de 1024 bits
         * E temos uma certeza de 100 que sera um primo utilizando o r que e nosso
         * numero random
         * E assim temos dois numeros randomicos primos de 1024 bits de tamanho.
         */
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);

        /**
         * Aqui utilizamos o metodo multiply do BigInteger
         * Que gera o numero da classe * o parametro passado
         * ou seja n = p * q
         */
        n = p.multiply(q);

        // Compute a função totiente phi(n) = (p -1) (q -1)
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Escolha um inteiro "e" , 1 < "e" < phi(n) , "e" e phi(n) sejam primos entre
        // si.
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }

        // d seja inverso multiplicativo de "e"
        d = e.modInverse(m);

        /**
         * Aqui chamamos a funcao ObterOpcaoUsuario que e nossa funcao para mostrar e
         * capturar uma string contendo
         * a opcao desejada pelo usuario, ja transformada em um inteiro
         */
        int opcaoUsuario = ObterOpcaoUsuario();

        /**
         * Aqui utilizamos o loop while para manter o usuario no nosso menu.
         */
        while (opcaoUsuario != 0) {
            switch (opcaoUsuario) {

                case 1:
                    String msg = JOptionPane.showInputDialog("Escreva a mensagem a ser criptografada");

                    msgcifrada = new BigInteger(msg.getBytes()).modPow(e, n).toString();

                    component.setText(msgcifrada);

                    JOptionPane.showMessageDialog(null, new JScrollPane(component), "MENSAGEM CIFRADA",
                            JOptionPane.INFORMATION_MESSAGE);
                    opcaoUsuario = ObterOpcaoUsuario();

                    break;

                case 2:
                    String decript = JOptionPane.showInputDialog("Escreva a mensagem a ser descriptografada");

                    msgdecifrada = new String(new BigInteger(decript).modPow(d, n).toByteArray());
                    JOptionPane.showMessageDialog(null, "Mensagem decifrada: " + msgdecifrada);
                    opcaoUsuario = ObterOpcaoUsuario();

                    break;

                case 3:
                    String variaveis = "p: " + p + "\n\n q: " + q + "\n\n n: " + n + "\n\n e: " + e;
                    component.setText(variaveis);

                    JOptionPane.showMessageDialog(null, new JScrollPane(component), "VARIAVEIS",
                            JOptionPane.INFORMATION_MESSAGE);

                    opcaoUsuario = ObterOpcaoUsuario();

                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Insira uma opcao valida por favor!", "OPCAO INVALIDA",
                            JOptionPane.ERROR_MESSAGE);
                    opcaoUsuario = ObterOpcaoUsuario();

            }

        }

    }

    static Integer ObterOpcaoUsuario() {
        String opcaoUsuario = JOptionPane.showInputDialog(
                "Escolha a opcao:\n 1 - Criptografar \n 2 - Descriptografar \n 3 - Mostrar variáveis \n 0 - Sair ");
        return Integer.parseInt(opcaoUsuario);
    }
}