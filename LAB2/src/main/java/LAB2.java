import java.io.*;
import java.net.*;

public class LAB2 {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server is listening on port 12345");

            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());

            String inputLine;
            boolean running = true;

            while (running) {
                out.println("Select an option: 1.Check if a number is prime 2.Send a file to the server 3.Exit");
                inputLine = in.readLine();
                System.out.println("Received from client: " + inputLine);

                switch (inputLine) {
                    case "1":
                        out.println("Enter a number to check if it's prime:");
                        int number = Integer.parseInt(in.readLine());
                        out.println(isPrime(number) ? number + " is a prime number." : number + " is not a prime number.");
                        break;
                    case "2":
                        out.println("Send the file name:");
                        String fileName = in.readLine();
                        String filePath = "C:/Users/teo/Documents/NetBeansProjects/LAB2/src/main/java/New folder/" + fileName; // Specify your desired path here

                        try {
                            File file = new File(filePath);
                            File parentDir = file.getParentFile();
                            if (!parentDir.exists()) {
                                parentDir.mkdirs(); // Create parent directories if they don't exist
                            }

                            try (FileOutputStream fos = new FileOutputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = bis.read(buffer)) > 0) {
                                    fos.write(buffer, 0, bytesRead);
                                }
                            }
                            out.println("File received and saved as " + fileName);
                        } catch (IOException e) {
                            out.println("Error saving file: " + e.getMessage());
                        }
                        break;
                    case "3":
                        out.println("Goodbye!");
                        running = false;
                        break;
                    default:
                        out.println("Invalid option. Please select 1, 2, or 3.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
