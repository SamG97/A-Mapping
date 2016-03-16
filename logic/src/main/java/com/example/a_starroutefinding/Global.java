package com.example.a_starroutefinding;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Global {
    static MainActivity mainActivity = null;
    static int locationStage = 0;
    static String startLocation,targetLocation = "";
    static ArrayList<Node> network = new ArrayList<>();

    public class Node {
        public String identifier = "";
        public int nodeID,x,y,z = 0;
        ArrayList<Integer> adjacentNodes = new ArrayList<>();
    }

    public void InitialiseNetwork(){
        String filename = "network.txt";
        try{
            FileReader fileReader = new FileReader(filename);
            try{
                BufferedReader buffer = new BufferedReader(fileReader);
                String l;
                while ((l = buffer.readLine()) != null){
                    String elements[] = l.split(",");
                    Node currentNode = new Node();
                    currentNode.nodeID = Integer.parseInt(elements[0]);
                    currentNode.identifier = elements[1];
                    currentNode.x = Integer.parseInt(elements[2]);
                    currentNode.y = Integer.parseInt(elements[3]);
                    currentNode.z = Integer.parseInt(elements[4]);
                    ArrayList<Integer> adjacentNodes = new ArrayList<>();
                    for (int i = 5; i < elements.length; i++) {
                        adjacentNodes.add(Integer.parseInt(elements[i]));
                    }
                    currentNode.adjacentNodes = adjacentNodes;
                    network.add(currentNode);
                }
            } finally {
                fileReader.close();
            }
        } catch (IOException e){}
    }

    public static int countLines(String filename) throws IOException {
        InputStream fileReader = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = fileReader.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            fileReader.close();
        }
    }
}
