package server;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.Date;

public class ClientHandler extends Thread {

    private Socket socket;
    private PrintWriter remetente;
    private Set<PrintWriter> remetentes;
    private BufferedWriter fileWriter;
    private final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String nomeArquivo = "";

    public ClientHandler(Socket socket, Set<PrintWriter> remetentes) {
        this.socket = socket;
        this.remetentes = remetentes;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            remetente = new PrintWriter(socket.getOutputStream(), true);

            synchronized (remetentes) {
                remetentes.add(remetente);
            }

            String usuario = reader.readLine();
            String welcomeMessage = usuario + " entrou no chat.";
            transmitir(welcomeMessage);
            salvarMensagem(welcomeMessage);
            System.out.println(usuario + " conectou-se ao servidor.");

            String msg;
            while ((msg = reader.readLine()) != null) {
                if (msg.equals("exit")) {
                    break;
                }

                String message = usuario + ": " + msg;
                transmitir(message);
                salvarMensagem(message);
            }

            synchronized (remetentes) {
                remetentes.remove(remetente);
            }

            String exitMessage = usuario + " saiu do chat.";
            transmitir(exitMessage);
            salvarMensagem(exitMessage);
            System.out.println(usuario + " desconectou.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void transmitir(String msg) {
        synchronized (remetentes) {
            remetentes.forEach(remetente -> remetente.println(msg));
        }
    }

    public void criarArquivo(String ip, int port){
        this.nomeArquivo = "chat_log_" + ip + "_" + port + ".txt";
        try {
            File logFile = new File(nomeArquivo);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fileWriter = new BufferedWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvarMensagem(String msg) {
        try {
            if (fileWriter != null) {
                String timestamp = timestampFormat.format(new Date());
                fileWriter.write("[" + timestamp + "] " + msg);
                fileWriter.newLine();
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
