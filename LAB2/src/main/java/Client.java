import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            boolean running = true;

            while (running) {
                System.out.println(in.readLine()); // Read menu from server
                userInput = stdIn.readLine();
                out.println(userInput);

                switch (userInput) {
                    case "1":
                        System.out.println(in.readLine()); // prompt
                        String numberInput = stdIn.readLine();
                        out.println(numberInput);
                        System.out.println("Server response: " + in.readLine());
                        break;
                    case "2":
                        System.out.println(in.readLine()); // prompt for file name
                        String fileName = stdIn.readLine();
                        out.println(fileName);
                        File file = new File(fileName);
                        if (file.exists()) {
                            try (FileInputStream fis = new FileInputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = fis.read(buffer)) > 0) {
                                    bos.write(buffer, 0, bytesRead);
                                }
                                bos.flush();
                            } catch (IOException e) {
                                System.out.println("Error reading file: " + e.getMessage());
                            }
                        } else {
                            System.out.println("File not found.");
                        }
                        System.out.println("Server response: " + in.readLine());
                        break;
                    case "3":
                        System.out.println(in.readLine());
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please select 1, 2, or 3.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
