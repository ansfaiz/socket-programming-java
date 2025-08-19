
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
 
 

public class Client {
    public Runnable getRunnable(int id){
        return new Runnable() {
            @Override
            public  void run(){
               int port=8080;
               try {
                   InetAddress address= InetAddress.getByName("localhost");
                   Socket socket=new Socket(address,port);
                   
                       PrintWriter tosocket= new PrintWriter(socket.getOutputStream(),true);
                       BufferedReader fromsocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                       tosocket.println("hello from client" +id);
                       String line=fromsocket.readLine();
                       System.out.println(line);
                       tosocket.close();
                       fromsocket.close();
                       socket.close();
                } catch (Exception e) {
                e.printStackTrace();
                }
            }
        };
    }
   
          
           
    
    public static void main(String[] args){
       Client client=new Client();
       for(int i=0;i<=100;i++){
        try {
            Thread th= new Thread(client.getRunnable(i));
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

       }
    }
}
