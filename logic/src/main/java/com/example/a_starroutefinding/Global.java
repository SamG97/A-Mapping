package com.example.a_starroutefinding;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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
        InitialiseNetwork();

        ArrayList<String> route = new ArrayList<>();
        ArrayList<Integer> startNodes = new ArrayList<>();
        ArrayList<Integer> targetNodes = new ArrayList<>();
        for (int i = 0; i < network.size(); i++){
            if (network.get(i).identifier.equals(startLocation)){
                startNodes.add(i);
            }
            if (network.get(i).identifier.equals(targetLocation)){
                targetNodes.add(i);
            }
        }
        if (startNodes.size() == 0 | targetNodes.size()== 0){
            return route;
        }

        double bestLength = Double.POSITIVE_INFINITY;
        ArrayList<Integer> bestRoute = new ArrayList<>();
        ArrayList<Integer> potentialRoute;
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
        int currentNode;
        ArrayList<Integer> potentialNodes = new ArrayList<>();
        potentialNodes.add(startNode);
        ArrayList<Integer> visitedNodes = new ArrayList<>();
        Map<Integer, Integer> path = new HashMap<>();
        Map<Integer, Double> fScore = new HashMap<>();
        Map<Integer, Double> gScore = new HashMap<>();

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

            if (currentNode == targetNode){
                return CreatePath(path, currentNode);
            }

            for (int i = 0; i < potentialNodes.size(); i++){
                if (potentialNodes.get(i) == currentNode){
                    potentialNodes.remove(i);
                    break;
                }
            }
            visitedNodes.add(currentNode);

            for (int i = 0; i < network.get(currentNode).adjacentNodes.size(); i++){
                int testNode = network.get(currentNode).adjacentNodes.get(i);
                if (visitedNodes.contains(testNode)){
                    continue;
                }

                double trialGScore = gScore.get(currentNode) + 1;
                if (trialGScore >= gScore.get(testNode)){
                    continue;
                }

                if (!potentialNodes.contains(testNode)){
                    potentialNodes.add(testNode);
                }
                path.put(testNode, currentNode);
                gScore.put(testNode, trialGScore);
                fScore.put(testNode, (trialGScore) + StraightDistance(testNode, targetNode));
            }
        }
        return null;
    }

    public ArrayList<Integer> CreatePath(Map<Integer, Integer> path, int currentNode){
        ArrayList<Integer> completePath = new ArrayList<>();
        completePath.add(currentNode);
        while (path.containsKey(currentNode)){
            currentNode = path.get(currentNode);
            completePath.add(currentNode);
        }
        Collections.reverse(completePath);
        return completePath;
    }

    public double StraightDistance(int start, int end){
        int deltaX = network.get(end).x - network.get(start).x;
        int deltaY = network.get(end).y - network.get(start).y;
        int deltaZ = network.get(end).z - network.get(start).z;
        return Math.pow((Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2)), 0.5);
    }

    public ArrayList<String> GetDirections(ArrayList<Integer> path){
        ArrayList<String> directionList = new ArrayList<>();
        int straightDistance = 1;
        if (path.size() == 2){
            directionList.add("Go forward 1 metres to your destination");
        } else if (path.size() < 2) {
            directionList.add("You are at your destination already");
        } else {
            int previousDirection = CardinalDirection(path.get(0), path.get(1));
            for (int i = 0; i < (path.size() - 2); i++){
                int newDirection = CardinalDirection(path.get(i), path.get(i + 1));
                if (previousDirection == newDirection){
                    straightDistance++;
                    continue;
                }

                directionList.add("Go forward " + Integer.toString(straightDistance) + " metres");
                straightDistance = 1;
                String instruction = "Turn ";
                if (newDirection > previousDirection | (newDirection == 1 & previousDirection == 4)){
                    instruction = instruction + "right";
                } else {
                    instruction = instruction + "left";
                }

                String label = network.get(path.get(i)).identifier;
                if (label != null){
                    instruction = instruction + " by " + label;
                }
                directionList.add(instruction);
            }
        }
        directionList.add("Go forward " + Integer.toString(straightDistance) + " metres to your destination");
        return directionList;
    }

    public int CardinalDirection(int startNode, int endNode){
        if (network.get(startNode).x == network.get(endNode).x){
            if (network.get(startNode).y > network.get(endNode).y){
                return 3;
            } else {
                return 1;
            }
        } else {
            if (network.get(startNode).x > network.get(endNode).x){
                return 4;
            } else {
                return 2;
            }
        }
    }
}
