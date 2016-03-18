package com.example.a_starroutefinding;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Global {
    static MainActivity mainActivity = null;
    static int locationStage = 0;
    static String startLocation,targetLocation = "";
    static ArrayList<Node> network = new ArrayList<>();

    public static class Node {
        public String identifier = "";
        public int nodeID,x,y,z = 0;
        ArrayList<Integer> adjacentNodes = new ArrayList<>();
    }

    public static void InitialiseNetwork(){
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

    public ArrayList<String> RouteFind(String startLocation, String targetLocation){
        ArrayList<String> route = new ArrayList<>();
        ArrayList<Integer> startNodes = new ArrayList<>();
        ArrayList<Integer> targetNodes = new ArrayList<>();
        for (int i = 0; i < network.size(); i++){
            if (network.get(i).identifier == startLocation){
                startNodes.add(i);
            }
            if (network.get(i).identifier == targetLocation){
                targetNodes.add(i);
            }
        }
        if (startNodes.size() == 0 | targetNodes.size()== 0){
            return route;
        }

        double bestLength = Double.POSITIVE_INFINITY;
        ArrayList<Integer> bestRoute = new ArrayList<>();
        ArrayList<Integer> potentialRoute = new ArrayList<>();
        for (int i = 0; i < startNodes.size(); i++){
            for (int j = 0; i < targetNodes.size(); j++){
                potentialRoute = AStar(startNodes.get(i),targetNodes.get(j));
                if (potentialRoute.size() < bestLength){
                    bestRoute = potentialRoute;
                    bestLength = potentialRoute.size();
                }
            }
        }

        route = GetDirections(bestRoute);
        return route;
    }

    public ArrayList<Integer> AStar(int startNode, int targetNode){
        int currentNode = startNode;
        ArrayList<Integer> potentialNodes = new ArrayList<>();
        potentialNodes.add(startNode);
        ArrayList<Integer> visitedNodes = new ArrayList<>();
        Map<Integer,Integer> path = new HashMap<>();
        Map<Integer,Double> fScore = new HashMap<>();
        Map<Integer,Double> gScore = new HashMap<>();

        for (int i = 0; i < network.size(); i++){
            if (network.get(i).nodeID == startNode){
                fScore.put(startNode, StraightDistance(startNode, targetNode));
                gScore.put(startNode, (double)0);
                continue;
            }
            fScore.put(i, Double.POSITIVE_INFINITY);
            gScore.put(i, Double.POSITIVE_INFINITY);
        }

        while (potentialNodes.size() > 0){
            double bestF = Double.POSITIVE_INFINITY;
            int bestNode = 0;
            for (int i = 0; i < potentialNodes.size(); i++){
                if (fScore.get(i) < bestF){
                    bestF = fScore.get(i);
                    bestNode = i;
                }
            }
            currentNode = bestNode;
        }



        return null;

        //http://pastebin.com/upA1b6zx
    }

    public double StraightDistance(int start, int end){
        int deltaX = network.get(start).x - network.get(end).x;
        int deltaY = network.get(start).y - network.get(end).y;
        int deltaZ = network.get(start).z - network.get(end).z;
        double distance = Math.pow((Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2)), 0.5);
        return distance;
    }

    public ArrayList<String> GetDirections(ArrayList<Integer> route){
        return null;

        //http://pastebin.com/1k51W1fg
    }
}
