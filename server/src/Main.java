import server.ClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static Set<PrintWriter> remetentes = new HashSet<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o IP que o servidor deve usar (ex: 127.0.0.1):");
        String ip = scanner.nextLine();
        InetAddress endereco;
        try {
            endereco = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
        System.out.println("Erro: Endereço IP inválido. Verifique o valor inserido.");
        return;
    }

        System.out.println("Digite a porta que o servidor irá rodar:");
        int port = Integer.parseInt(scanner.nextLine());

        try(ServerSocket server = new ServerSocket(port, 50, endereco)) {
            System.out.println("Servidor rodando no endereço " + endereco + " e na porta " + port);
            while(true){
                Socket cliente = server.accept();
                ClientHandler handler = new ClientHandler(cliente, remetentes);
                handler.criarArquivo(ip, port);
                handler.start();
            }
        }catch (IOException e){

        }
    }
}