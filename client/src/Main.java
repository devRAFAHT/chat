import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insira o seu nome: ");
        System.out.flush();
        String usuario = scanner.nextLine();

        System.out.print("Insira o IP do servidor: ");
        String ipServidor = scanner.nextLine();

        System.out.print("Insira a porta do servidor: ");
        int portaServidor = Integer.parseInt(scanner.nextLine());

        try (Socket socket = new Socket(ipServidor, portaServidor);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter remetente = new PrintWriter(socket.getOutputStream(), true)
        ) {
            remetente.println(usuario);

            Thread receptor = new Thread(() -> {
                try {
                    String msgRecebida;
                    while ((msgRecebida = reader.readLine()) != null) {
                        synchronized (System.out) {
                            System.out.println("\n" + msgRecebida);
                            System.out.print("Escreva uma mensagem: ");
                            System.out.flush();
                        }
                    }
                } catch (IOException e) {
                }
            });
            receptor.start();

            while (true) {
                synchronized (System.out) {
                    System.out.print("Escreva uma mensagem: ");
                    System.out.flush();
                }
                String msg = scanner.nextLine();
                remetente.println(msg);

                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            receptor.interrupt();
            System.out.println("Saindo do chat...");

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }
    }
}