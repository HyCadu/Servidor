
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatCliente {
    
    private String servidorEdrss = "127.0.0.1"; //IP da máquina
    private Socket clienteSocket;
    private Scanner scan = new Scanner(System.in);
    private PrintWriter out;
    
    public void start() throws IOException{
        clienteSocket = new Socket(servidorEdrss,ChatServer.portaServidor);
        System.out.println("Cliente conectado ao servidor em "+servidorEdrss);
        this.out = new PrintWriter(clienteSocket.getOutputStream(), true);
        messageLoop();
    }
    
    //Método que mantém o código do Cliente rodando até ser digitado a palavra sair
    private void messageLoop(){ 
        String msg;
        
        do {
            System.out.println("Digite sua mensagem (Para encerrar digite 'sair')");
            msg = scan.nextLine();
            out.println(msg);
            out.flush();
        } while (!msg.equalsIgnoreCase("sair"));
        
    }
    
    public static void main(String[] args) {
        
        try {
            ChatCliente chatCliente = new ChatCliente();
            chatCliente.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar cliente "+ex.getMessage());
        }
        
        
    }
}
