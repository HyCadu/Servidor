
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente {

    private String servidorEdrss = "127.0.0.1"; // IP da máquina
    private Socket clienteSocket;
    private BufferedReader in;
    private PrintWriter out;
    Scanner scanner = new Scanner(System.in);

    public void start() throws IOException {
        clienteSocket = new Socket(servidorEdrss, ChatServer.portaServidor);
        System.out.println("Cliente conectado ao servidor em " + servidorEdrss);

        //Recebimento da mensagem
        in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
        //Envio da mensagem
        out = new PrintWriter(clienteSocket.getOutputStream(), true);

        // Inicia a thread para ler mensagens do servidor
        Thread thread = new Thread(new ServerListener());
        thread.start();

        // Entra no loop principal para enviar mensagens para o servidor
        messageLoop();
    }

    // Método que mantém o código do Cliente rodando até ser digitado a palavra sair
    private void messageLoop() throws IOException {
        String msg;

        do {
            msg = scanner.nextLine();
            out.println(msg);
            out.flush();
        } while (!msg.equalsIgnoreCase("sair"));
        
    }

    // Thread para ouvir mensagens do servidor
    class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String resposta;
                while ((resposta = in.readLine()) != null) {
                    System.out.println(resposta);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ChatCliente chatCliente = new ChatCliente();
            chatCliente.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar cliente " + ex.getMessage());
        }
    }
}
