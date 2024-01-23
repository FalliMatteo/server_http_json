package com.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void sendFile(PrintWriter out, String name){
        try{
            File file = new File("src/files" + name);
            System.out.println("-----" + name);
            String type[] = name.split("\\.");
            Scanner scanner = new Scanner(file);
            out.println("HTTP/1.1 200 OK");
            System.out.println("LENGTH: " + file.length());
            out.println("Content-Length: " + file.length());
            out.println("Server: Falli's Java HTTP Server");
            out.println("Date: " + new Date());
            switch(type[1]){
                case "html":
                    out.println("Content-Type: text/html; charset=utf-8");
                    break;
                case "css":
                    out.println("Content-Type: text/css; charset=utf-8");
                    break;
                case "js":
                    out.println("Content-Type: text/javascript; charset=utf-8");
                    break;
                case "png":
                    out.println("Content-Type: image/png; charset=utf-8");
                    break;
                case "jpg":
                    out.println("Content-Type: image/jpeg; charset=utf-8");
                    break;
                default:
                    out.println("Content-Type: text; charset=utf-8");
            }
            out.println();
            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                out.println(data);
            }
            out.close();
            scanner.close();
        }catch(FileNotFoundException e){
            out.println("HTTP/1.1 404 NOT FOUND");
            System.out.println("File not found");
        }
    }

    public static void sendClassroom(PrintWriter out, Classroom c){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String classroom = mapper.writeValueAsString(c);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Length: " + classroom.length());
            out.println("Server: Falli's Java HTTP Server");
            out.println("Date: " + new Date());
            out.println("Content-Type: application/json; charset=utf-8");
            out.println();
            out.println(classroom);
            out.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main( String[] args )
    {
        try {
            ServerSocket server= new ServerSocket(3000);
            boolean loop = true;
            Classroom c1 = new Classroom(5, "DIA", "Itis Meucci");
            Student s1 = new Student("Alessio", "Didilescu", 19);
            Student s2 = new Student("Alessandro","Gonzalez", 20);
            Student s3 = new Student("Matteo", "Falli", 19);
            Student s4 = new Student("Anatolie", "Pavlov", 21);
            Student s5 = new Student("Singh", "Swarang", 20);
            Student s6 = new Student("Ryan", "Mohd", 19);
            c1.addStudent(s1);
            c1.addStudent(s2);
            c1.addStudent(s3);
            c1.addStudent(s4);
            c1.addStudent(s5);
            c1.addStudent(s6);
            while(loop){
                Socket socket = server.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                String message = input.readLine();
                String init[] = message.split(" ");
                do{
                    message = input.readLine();
                    System.out.println(message);
                }while(!message.equals("") || !message.isEmpty());
                if(init[1].equals("/classroom.json")){
                    sendClassroom(output, c1);
                }else{
                    sendFile(output, init[1]);
                }
                output.flush();
                socket.close();
            }
            server.close();
            System.out.println("Server shutdown");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}