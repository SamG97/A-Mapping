package com.example.a_starroutefinding;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Global {
    static MainActivity mainActivity = null;
    static int locationStage = 0;
    static String startLocation, targetLocation = "";
    static ArrayList<Node> network = new ArrayList<>();

    public static class Node {
        public String identifier = "";
        public int nodeID, x, y, z = 0;
        ArrayList<Integer> adjacentNodes = new ArrayList<>();
    }

    public static boolean ReadStairOption(AppCompatActivity activity) {
        boolean stairOption = false;
        try (FileInputStream fileInputStream = activity.openFileInput("settings.txt")){
            String ReadMessage;
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            ReadMessage = bufferedReader.readLine();
            stairOption = Boolean.valueOf(ReadMessage);
        } catch (IOException e){
            e.printStackTrace();
        }
        return stairOption;
    }

    public static void InitialiseNetwork(AppCompatActivity activity){
        String filename = "network.txt";
        try(FileInputStream fileInputStream = activity.openFileInput(filename)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String elements[] = line.split(",");
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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<String> RouteFind(AppCompatActivity activity){
        ArrayList<String> route = new ArrayList<>();
        ArrayList<Integer> startNodes = new ArrayList<>();
        ArrayList<Integer> targetNodes = new ArrayList<>();
        for (int i = 0; i < network.size(); i++){
            if (network.get(i).identifier.equals(startLocation) | network.get(i).identifier.contains(startLocation + "/") | network.get(i).identifier.contains("/" + startLocation)){
                startNodes.add(i);
                Toast.makeText(activity.getApplicationContext(),network.get(i).identifier,Toast.LENGTH_SHORT).show();
            }
            if (network.get(i).identifier.equals(targetLocation) | network.get(i).identifier.contains(targetLocation + "/") | network.get(i).identifier.contains("/" + targetLocation)){
                targetNodes.add(i);
            }
        }
        return route;/*
        if (startNodes.size() == 0 | targetNodes.size()== 0){
            return route;
        }

        double bestLength = Double.POSITIVE_INFINITY;
        ArrayList<Integer> bestRoute = new ArrayList<>();
        ArrayList<Integer> potentialRoute;
        for (int i = 0; i < startNodes.size(); i++){
            for (int j = 0; i < targetNodes.size(); j++){
                potentialRoute = AStar(activity, startNodes.get(i),targetNodes.get(j));
                if (RouteLength(potentialRoute) < bestLength){
                    bestRoute = potentialRoute;
                    bestLength = RouteLength(potentialRoute);
                }
            }
        }

        if (bestRoute.size() == 0){
            return null;
        }
        route = GetDirections(bestRoute);
        return route;*/
    }

    public static int RouteLength(ArrayList<Integer> route){
        int length = 0;
        for (int i = 1; i < route.size(); i++){
            length += StraightDistance(i - 1, i);
        }
        return length;
    }

    public static ArrayList<Integer> AStar(AppCompatActivity activity, int startNode, int targetNode){
        Boolean stairOption = ReadStairOption(activity);
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

                if (stairOption & network.get(currentNode).z != network.get(testNode).z & !(currentNode == 179 | currentNode == 180)){
                    continue;
                }

                double trialGScore = gScore.get(currentNode) + StraightDistance(currentNode, testNode);
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

    public static ArrayList<Integer> CreatePath(Map<Integer, Integer> path, int currentNode){
        ArrayList<Integer> completePath = new ArrayList<>();
        completePath.add(currentNode);
        while (path.containsKey(currentNode)){
            currentNode = path.get(currentNode);
            completePath.add(currentNode);
        }
        Collections.reverse(completePath);
        return completePath;
    }

    public static double StraightDistance(int start, int end){
        int deltaX = network.get(end).x - network.get(start).x;
        int deltaY = network.get(end).y - network.get(start).y;
        int deltaZ = network.get(end).z - network.get(start).z;
        return Math.pow((Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2)), 0.5);
    }

    public static ArrayList<String> GetDirections(ArrayList<Integer> path){
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
                if (network.get(path.get(i)).z != network.get(path.get(i + 1)).z){
                    directionList.add("Go forward " + Integer.toString(straightDistance) + " metres");
                    straightDistance = 1;
                    if (network.get(path.get(i)).z < network.get(path.get(i + 1)).z){
                        directionList.add("Go up the stairs " + Double.toString(StraightDistance(path.get(i),path.get(i + 1))) + " metres");
                    } else {
                        directionList.add("Go down the stairs " + Double.toString(StraightDistance(path.get(i),path.get(i + 1))) + " metres");
                    }
                }

                if (previousDirection == newDirection){
                    straightDistance += StraightDistance(path.get(i),path.get(i + 1));
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

    public static int CardinalDirection(int startNode, int endNode){
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
