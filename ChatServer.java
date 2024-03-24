
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;




public class ChatServer {
    public static int portaServidor = 8080;
    private ServerSocket serverSocket;
    Scanner scanner = new Scanner (System.in);
    private PrintWriter out;
    
    
    //Metodo para iniciar o servidor
    public void start() throws IOException{
        
        serverSocket = new ServerSocket(portaServidor);
        System.out.println("Servidor iniciou na porta "+portaServidor);
        clienteConectionLoop();   
    }
    
    //Metodo que mantém uma conexão constate com o cliente e recebe as mensagens
    private void clienteConectionLoop() throws IOException{
    
      while(true){
           Socket clienteSocket = serverSocket.accept(); //metodo 'accept' pertence a biblioteca Socket
           System.out.println("Cliente "+clienteSocket.getRemoteSocketAddress()+" conectou");
           
           //recebimento da mensagem
           BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
           //envio da mensagem
           out = new PrintWriter(clienteSocket.getOutputStream(), true);
           
           String msg;
           while ((msg = in.readLine()) != "") {
           System.out.println(clienteSocket.getRemoteSocketAddress() + ": " + msg);
           String resposta;
            
           //Resposta do servidor
            resposta = scanner.nextLine();
            out.println("Servidor: "+resposta);
            out.flush();
   
        }
       }
    
    }
    
    public static void main(String[] args) {
     
        try {
            ChatServer server = new ChatServer();
            server.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor: "+ex.getMessage());
        }
    }
}
