
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientsocket) -> {
                try (
                    PrintWriter toclient = new PrintWriter(clientsocket.getOutputStream(), true); 
                    BufferedReader fromclient = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()))) 
                    {
                    String msg = fromclient.readLine();
                    System.out.println("Received: " + msg);
                    toclient.println("Server reply: " + msg.toUpperCase());
                }

                catch (IOException e) {
                e.printStackTrace();
                } finally {
                     try {

                     clientsocket.close();
                   } catch (Exception e) {
                 }
            }
        };
    }

    public static void main(String[] args) {
        int port = 8080;
        Server server = new Server();
        ExecutorService eService = Executors.newFixedThreadPool(10);// for 10000 u need 100 thread in pool 
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(10000);
            System.out.println("server listening on port " + port);
            while (true) {
                Socket acceptedConnection = socket.accept();
                eService.submit(() -> server.getConsumer().accept(acceptedConnection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
