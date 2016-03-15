package com.example.a_starroutefinding;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Global {
    static MainActivity mainActivity = null;
    static int locationStage = 0;
    static String startLocation,targetLocation = "";
    static Node[] network;

    public class Node {
        public String identifier = "";
        public int nodeID,x,y,z = 0;
        public int[] adjecentNodes;
        public Node(int nodeID, String identifier,int x, int y, int z, int[] adjecentNodes){
            this.nodeID = nodeID;
            this.identifier = identifier;
            this.x = x;
            this.y = y;
            this.z = z;
            this.adjecentNodes = adjecentNodes;
        }
    }

    public void InitialiseNetwork(){
        int fileLength = 0;
        try {
             fileLength = countLines("network.txt");
        } catch (IOException e){

        }
        if (fileLength > 0){

        }
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
