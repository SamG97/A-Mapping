package com.example.a_starroutefinding;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Global {

    //Declare global variables
    static int locationStage = 0;
    static String startLocation, targetLocation = "";
    static ArrayList<Node> network = new ArrayList<>();

    //Declare node class
    protected static class Node {
        protected String identifier = "";
        protected int nodeID, x, y, z = 0;
        ArrayList<Integer> adjacentNodes = new ArrayList<>();
    }

    public static boolean ReadStairOption(AppCompatActivity activity) {
        //Reads in the stair option saved to settings.txt and returns the value in the file
        //Gives stairOption a default value if the value cannot be loaded
        boolean stairOption = false;
        try (FileInputStream fileInputStream = activity.openFileInput("settings.txt")){
            //Opens the settings.txt, reads the value stored and converts to a boolean data type
            String ReadMessage;
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            ReadMessage = bufferedReader.readLine();
            stairOption = Boolean.valueOf(ReadMessage);
        } catch (IOException e){
            //The first time the app is run, no settings.txt file will exist so this branch will run; hence why a default value is set
            e.printStackTrace();
        }
        //Returns the stair option value either read in or the default false value
        return stairOption;
    }

    public static void InitialiseNetwork(AppCompatActivity activity){
        //Imports network and parses it into an ArrayList
        try(FileInputStream fileInputStream = activity.openFileInput("network.txt")) {
            //Opens the network.txt file created by CreateMapFile
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            //Reads in one line at a time, corresponding to one location, until the end of the file is reached
            while ((line = bufferedReader.readLine()) != null) {
                //Separates the line into an array on commas which separate data
                String elements[] = line.split(",");
                //Places each item in the array into its appropriate element of a node, converting the data type where necessary
                Node currentNode = new Node();
                currentNode.nodeID = Integer.parseInt(elements[0]);
                currentNode.identifier = elements[1];
                currentNode.x = Integer.parseInt(elements[2]);
                currentNode.y = Integer.parseInt(elements[3]);
                currentNode.z = Integer.parseInt(elements[4]);
                //After the first 5 elements, all remaining items are the adjacent nodes
                ArrayList<Integer> adjacentNodes = new ArrayList<>();
                //Adds all remaining elements to an adjacency ArrayList and also adds this to the node
                for (int i = 5; i < elements.length; i++) {
                    adjacentNodes.add(Integer.parseInt(elements[i]));
                }
                currentNode.adjacentNodes = adjacentNodes;
                //Adds the constructed node to the network of all nodes
                network.add(currentNode);
            }
        } catch (IOException e){
            //This branch should never run but is included in case of an unexpected error so that the app does not crash
            e.printStackTrace();
        }
    }

    public static ArrayList<String> RouteFind(AppCompatActivity activity){
        //Manages route finding and direction making
        ArrayList<String> route = new ArrayList<>();
        ArrayList<Integer> startNodes = new ArrayList<>();
        ArrayList<Integer> targetNodes = new ArrayList<>();
        //Some locations have multiple nodes (e.g. classrooms with two exits, such as C7, and toilets) so it may be necessary to check multiple possible start / end nodes
        //Checks every node in the network to see if it references the start or end location, if it does, add it to the appropriate list of possible nodes
        for (int i = 0; i < network.size(); i++){
            if (network.get(i).identifier.equals(startLocation) || network.get(i).identifier.contains(startLocation + "/") || network.get(i).identifier.contains("/" + startLocation)){
                startNodes.add(i);
            }
            if (network.get(i).identifier.equals(targetLocation) || network.get(i).identifier.contains(targetLocation + "/") || network.get(i).identifier.contains("/" + targetLocation)){
                targetNodes.add(i);
            }
        }

        //If no nodes are found to start or end at, return that no route was found
        //This is mostly unnecessary and is used if extending the app to allow user inputs and to catch errors if a location's identifier was entered in the adjacency list incorrectly
        if (startNodes.size() == 0 || targetNodes.size() == 0){
            route.add("No route found");
            return route;
        }

        double bestLength = Double.POSITIVE_INFINITY;
        ArrayList<Integer> bestRoute = new ArrayList<>();
        ArrayList<Integer> potentialRoute;
        //Go through every possible combination of start and end nodes and find the shortest route between them
        for (int i = 0; i < startNodes.size(); i++){
            for (int j = 0; j < targetNodes.size(); j++){
                potentialRoute = AStar(activity, startNodes.get(i),targetNodes.get(j));
                //If the route is better than the currently stored best route, replace it
                if (RouteLength(potentialRoute) < bestLength){
                    bestRoute = potentialRoute;
                    bestLength = RouteLength(potentialRoute);
                }
            }
        }

        //If the best route is of zero length (i.e. no route was found), return that no route was found
        if (bestRoute.size() == 0){
            route.add("No route found");
            return route;
        }

        //Generate directions for the best route and return them
        route = GetDirections(bestRoute);
        return route;
    }

    protected static Double RouteLength(ArrayList<Integer> route){
        //Calculates the total length of a route
        Double length = (double) 0;
        if (route == null || route.size() == 0){ //Both forms seemed necessary as the app would sometimes crash if only one was used
            //If the route has no nodes (i.e. a route does not exist), return a length of infinity for the route length
            return Double.POSITIVE_INFINITY;
        }
        //Adds the distance between each node to get the total length and return it
        for (int i = 1; i < route.size(); i++){
            length += StraightLineDistance(i - 1, i);
        }
        return length;
    }

    protected static ArrayList<Integer> AStar(AppCompatActivity activity, int startNode, int targetNode){
        //Finds the shortest route between two nodes using the a* algorithm
        Boolean stairOption = ReadStairOption(activity);
        int currentNode;
        ArrayList<Integer> potentialNodes = new ArrayList<>();  //Nodes to be considered next; adjacent to the network of visited nodes without being part of it
        potentialNodes.add(startNode);
        ArrayList<Integer> visitedNodes = new ArrayList<>();    //Nodes already visited by the algorithm
        Map<Integer, Integer> path = new HashMap<>();   //The node leading to the node stored as the key in the shortest path there
        Map<Integer, Double> fScore = new HashMap<>();  //The estimated distance from the start to end node via the node stored as the key
        Map<Integer, Double> gScore = new HashMap<>();  //The shortest distance from the start node to this node found so far

        //Initialises the various dictionaries with infinity for all nodes but the start node
        for (int i = 0; i < network.size(); i++){
            if (network.get(i).nodeID == startNode){
                //Sets different values for the start node
                fScore.put(startNode, StraightLineDistance(startNode, targetNode));
                gScore.put(startNode, (double) 0);
                continue;
            }
            fScore.put(i, Double.POSITIVE_INFINITY);
            gScore.put(i, Double.POSITIVE_INFINITY);
        }

        //Loops while there are still nodes to be considered
        while (potentialNodes.size() > 0){
            //Finds the most promising node based on fScore as the lower the fScore, the shorter the final route is likely to be
            double bestF = Double.POSITIVE_INFINITY;
            int bestNode = 0;
            //Loops through each potential node to find the node with lowest fScore
            for (int i = 0; i < potentialNodes.size(); i++){
                if (fScore.get(potentialNodes.get(i)) < bestF){
                    bestF = fScore.get(potentialNodes.get(i));
                    bestNode = potentialNodes.get(i);
                }
            }
            //Moves to the most promising node
            currentNode = bestNode;

            //If the current node is the target node, the route has been found so return it, after it has been reconstructed
            if (currentNode == targetNode){
                return CreatePath(path, currentNode);
            }

            //Removes the current node from potential nodes so that it is not considered again
            for (int i = 0; i < potentialNodes.size(); i++){
                if (potentialNodes.get(i) == currentNode){
                    potentialNodes.remove(i);
                    break;
                }
            }
            //Adds the current node to the network of visited nodes
            visitedNodes.add(currentNode);

            //Loops through all adjacent nodes
            for (int i = 0; i < network.get(currentNode).adjacentNodes.size(); i++){
                int testNode = network.get(currentNode).adjacentNodes.get(i);   //The current node being considered, as the expression to access it is long, a shorter name is given to it for convenience
                //If the node has been visited before, skip it
                if (visitedNodes.contains(testNode)){
                    continue;
                }

                if ((currentNode == 119 && testNode == 183) || (currentNode == 183 && testNode == 119)){
                    //Special case for the lift in the sports hall; can only be used by those who cannot go up stairs so if stairOption is false (can use stairs), skip this node
                    //Separate from below if statement as it was already quite long and seemed not to work
                    if (!stairOption){
                        continue;
                    }
                } else {
                    //If the user cannot use stairs, skip any nodes where the z co-ordinate of the node (height) is different to the previous one
                    //Some special cases where there are stairs but no height change which are also skipped and a ramp which should not be skipped
                    if (stairOption && (network.get(currentNode).z != network.get(testNode).z && !(currentNode == 179 || currentNode == 180) || (currentNode == 7 && testNode == 8) || (currentNode == 8 && testNode == 7) || (currentNode == 16 && testNode == 17) || (currentNode == 17 && testNode == 16))) {
                        continue;
                    }
                }

                //Calculates the gScore of the node
                double trialGScore = gScore.get(currentNode) + StraightLineDistance(currentNode, testNode);
                //If the currently stored gScore is better, then a better route to the node already exists so skip this node
                if (trialGScore >= gScore.get(testNode)){
                    continue;
                }

                //If the node has not yet been added to potential nodes, it has been discovered and should be added
                if (!potentialNodes.contains(testNode)){
                    potentialNodes.add(testNode);
                }

                //Stores which node was used to get to this one on this new shortest route to it
                path.put(testNode, currentNode);
                //Stores the new, better gScore
                gScore.put(testNode, trialGScore);
                //Calculates and stores the new, better fScore of the node
                //fScore is given by fScore = gScore + hScore where hScore is an estimate of the distance remaining in the route - the straight line distance between the current and target node
                //As the hScore for a given node and the target node is, in this implementation of the a* algorithm, constant, if the gScore is better, the fScore will also be better
                fScore.put(testNode, (trialGScore) + StraightLineDistance(testNode, targetNode));
            }
        }
        //At this point, there are no adjacent nodes to those visited so no route is possible; returns this result
        return null;
    }

    protected static ArrayList<Integer> CreatePath(Map<Integer, Integer> path, int currentNode){
        //Creates the route from the list of previous nodes
        ArrayList<Integer> completePath = new ArrayList<>();
        //Starts with the target node
        completePath.add(currentNode);
        //While the current node was accessed from another node, trace back along this network, adding the source for reaching each node in the shortest way to the route
        while (path.containsKey(currentNode)){
            currentNode = path.get(currentNode);
            completePath.add(currentNode);
        }
        //The start node was never accessed from anywhere so the loop will break at this point when the start node is reached
        //completePath is now the shortest route from the target node to the start node so this route should be reversed
        Collections.reverse(completePath);
        //Returns the correctly ordered route
        return completePath;
    }

    protected static double StraightLineDistance(int start, int end){
        //Calculates the straight line difference between two points ("as the crow flies")
        //Finds the change in x, y and z co-ordinates between the two nodes
        int deltaX = network.get(end).x - network.get(start).x;
        int deltaY = network.get(end).y - network.get(start).y;
        int deltaZ = network.get(end).z - network.get(start).z;
        //Returns the distance between the two nodes using Pythagoras's Theorem in 3 dimensions
        //a^2 = b^2 + c^2 + d^2
        return Math.pow((Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2)), 0.5);
    }

    protected static ArrayList<String> GetDirections(ArrayList<Integer> path){
        //Makes an ArrayList of human readable instructions from a route
        //Keeps track of the total distance of the route
        //The RouteLength function is not used so that the sum of the rounded values presented to the user sum to the total value which is likely to be different to the actual total length
        int totalDistance = 0; //Has a default value of 0 as the special cases for initial nodes do not change this so a default value is needed
        ArrayList<String> directionList = new ArrayList<>();
        Double straightDistance = (double) 0;
        if (path.size() == 2) {
            //If there is only one node, the start and target node were the same so no directions are needed
            directionList.add("You are at your destination already");
        } else {
            int previousDirection;
            //Special cases are needed for this stage as well as the main part of the algorithm in the loop for when a special case occurs for the first two nodes
            //The special cases are simpler than their full versions as much of the resetting is unneeded
            //Handles the lift special case if the nodes are the specific lift nodes
            //This should never be chosen by the user as neither node is accessible as a start or end node but this allows for extension if any location could be chosen by the user
            if ((path.get(0) == 119 && path.get(1) == 183) || (path.get(0) == 183 && path.get(1) == 119)){
                //Adds the appropriate instruction based on which way around the two lift nodes are
                if (path.get(0) == 119 && path.get(1) == 183){
                    directionList.add("Go up in the lift");
                } else {
                    directionList.add("Go down in the lift");
                }
                //Sets the direction to be coming out of the lift
                previousDirection = 1;
            } else {
                //Sets initial direction travelled
                previousDirection = CardinalDirection(path.get(0), path.get(1));
                //Handles the stairs special case
                if ((network.get(path.get(0)).z != network.get(path.get(1)).z) && !(path.get(0) == 179 || path.get(0) == 180)){
                    //Calculates the length of the stairs and adds this to the total distance
                    Long stairLength = Math.round(StraightLineDistance(path.get(0), path.get(1)));
                    totalDistance += stairLength;

                    //Adds a stair instruction to the directions
                    if (network.get(path.get(0)).z < network.get(path.get(1)).z){
                        directionList.add("Go up the stairs " + PluralChecker(stairLength));
                    } else {
                        directionList.add("Go down the stairs " + PluralChecker(stairLength));
                    }

                    previousDirection = CardinalDirection(path.get(0), path.get(1));
                } else {
                    //Sets the distance between the first two nodes
                    straightDistance = StraightLineDistance(path.get(0), path.get(1));
                }
            }
            for (int i = 1; i < (path.size() - 1); i++){    //Loops for all nodes in the route except for the first (already processed) and the last as it does not have a node after it
                //Find the direction between the next two nodes
                int newDirection = CardinalDirection(path.get(i), path.get(i + 1));

                //Handles the lift special case if the nodes are the specific lift nodes
                if ((path.get(i) == 119 && path.get(i + 1) == 183) || (path.get(i) == 183 && path.get(i + 1) == 119)){
                    //Adds the current distance traveled in a straight line to the directions and resets the counter
                    if (straightDistance > 0) {
                        directionList.add("Go forward " + PluralChecker(Math.round(straightDistance)));
                        totalDistance += straightDistance;
                        straightDistance = (double) 0;
                    }

                    //Adds the appropriate instruction based on which way around the two lift nodes are
                    if (path.get(i) == 119 && path.get(i + 1) == 183){
                        directionList.add("Go up in the lift");
                    } else {
                        directionList.add("Go down in the lift");
                    }

                    //Sets the direction to be coming out of the lift
                    previousDirection = 1;
                    //Move on to the next node
                    continue;
                }

                //Handles stairs special case if the z co-ordinates are different (exception for the ramp)
                //The small stairs are not counted here as they are not significant for users that can use stairs and may be more confusing, especially in the case where the small stairs are next to a full flight of stairs
                if ((network.get(path.get(i)).z != network.get(path.get(i + 1)).z) && !(path.get(i) == 179 || path.get(i) == 180)){
                    //Adds the current distance traveled in a straight line to the directions and resets the counter
                    if (straightDistance > 0) {
                        directionList.add("Go forward " + PluralChecker(Math.round(straightDistance)));
                        totalDistance += straightDistance;
                        straightDistance = (double) 0;
                    }

                    //Calculates the length of the stairs and adds this to the total distance
                    Long stairLength = Math.round(StraightLineDistance(path.get(i), path.get(i + 1)));
                    totalDistance += stairLength;

                    //Adds a stair instruction to the directions
                    if (network.get(path.get(i)).z < network.get(path.get(i + 1)).z){
                        directionList.add("Go up the stairs " + PluralChecker(stairLength));
                    } else {
                        directionList.add("Go down the stairs " + PluralChecker(stairLength));
                    }
                    //Updates direction travelled
                    previousDirection = newDirection;
                    //Move on to the next node
                    continue;
                    //The turning part of the algorithm is deliberately skipped for stairs at the directions are clear enough already and having too many turning instructions adds unnecessary complexity to the directions for the user
                }

                //If the route is moving in a straight line, add to the straight line counter and move on to the next node
                if (previousDirection == newDirection){
                    straightDistance += StraightLineDistance(path.get(i), path.get(i + 1));
                    continue;
                }

                //At this point, the user must have gone round a corner
                //Adds the current distance traveled in a straight line to the directions
                if (straightDistance > 0) {
                    directionList.add("Go forward " + PluralChecker(Math.round(straightDistance)));
                    totalDistance += straightDistance;
                }
                //Sets the straight line counter to the distance travelled since the corner
                straightDistance = StraightLineDistance(path.get(i), path.get(i + 1));

                //Makes an appropriate instruction to turn right or left
                String instruction = "Turn ";
                if ((newDirection > previousDirection && !(newDirection == 4 && previousDirection == 1)) || (newDirection == 1 && previousDirection == 4)){
                    //Turns right if the direction is "more right" from north unless moving from north to west; moving west to north is also accepted as a special case
                    instruction = instruction + "right";
                } else {
                    //If not turning right, the user must be turning left
                    instruction = instruction + "left";
                }

                //If possible, adds a label to the turn to make it easier to identify where to turn
                String label = network.get(path.get(i)).identifier;
                if (label != null){
                    instruction = instruction + " by " + label;
                }

                //Adds the turning instruction to the directions
                directionList.add(instruction);

                //Updates direction travelled
                previousDirection = newDirection;
            }
            //Finally, adds the remaining distance travelled to the destination and indicate that the route is finished
            directionList.add("Go forward " + PluralChecker(Math.round(straightDistance)) + " to your destination");
            totalDistance += straightDistance;
        }
        //Adds the total route distance to the list of directions
        //Although this is not a direction, it is easier to add it here for when it is displayed in DisplayRoute
        directionList.add("Total route distance: " + PluralChecker(totalDistance));
        //Returns the directions
        return directionList;
    }

    protected static int CardinalDirection(int startNode, int endNode){
        //Determines the direction (north, east, south or west) from one node to another
        //Key: North = 1, East = 2, South = 3, West = 4; North is taken to be in the direction of positive y
        if (network.get(startNode).x == network.get(endNode).x){
            //x co-ordinate is the same for both nodes so the movement must be vertical
            if (network.get(startNode).y > network.get(endNode).y){
                //y decreases so moving south
                return 3;
            } else {
                //y increases so moving north
                return 1;
            }
        } else {
            //x co-ordinate changed so must be moving horizontally
            if (network.get(startNode).x > network.get(endNode).x){
                //x decreasing so moving west
                return 4;
            } else {
                //x increasing so moving east
                return 2;
            }
        }
    }

    protected static String PluralChecker(long value){
        //Checks if a value is singular and returns the correct form of metre
        if (value == 1){
            //Returns "1 metre" ("rather than 1 metres")
            return Long.toString(value) + " metre";
        } else {
            //Returns "X metres" where X is an integer: the normal form or representing a distance
            return Long.toString(value) + " metres";
        }
    }

    public static void CreateMapFile(Activity activity){
        //Creates a file for the adjacency list
        //As non-code files cannot easily be saved with initial values in Android, the file containing the network data must be written from within the code
        //If an updated version of the app is created and pushed, the device will already have a copy of this file but it may be outdated
        //The file is therefore remade every time the app is started to make sure the correct network is used
        try {
            String WriteMessage = "0,Reception,0,0,0,1,139\n" +
                    "1,Branch 9,0,2,0,0,2,18\n" +
                    "2,Branch 7,0,5,0,1,3,17\n" +
                    "3,Branch 6,0,8,0,2,4,20\n" +
                    "4,Men's Toilets,0,10,0,3,5\n" +
                    "5,Women's Toilets,0,14,0,4,6\n" +
                    "6,Disabled Toilets,0,15,0,5,7\n" +
                    "7,E1,0,19,0,6,8,37\n" +
                    "8,Stairs,-3,19,0,7,9,47\n" +
                    "9,C9,-11,19,0,8,10\n" +
                    "10,C8,-20,19,0,9,11\n" +
                    "11,Branch,-28,19,0,10,12,21\n" +
                    "12,C7,-29,19,0,11,13\n" +
                    "13,Branch 2,-32,19,0,12,14,23\n" +
                    "14,Branch 3,-32,11,0,13,15,30\n" +
                    "15,Branch 4,-32,3,0,14,16,31\n" +
                    "16,Branch 5,-32,0,0,15,17,33\n" +
                    "17,Hall,-16,0,0,2,16,34\n" +
                    "18,Staff Corridor,2,2,0,1,19\n" +
                    "19,Door,2,8,0,18,20,137\n" +
                    "20,Stairs,1,8,0,3,19,41\n" +
                    "21,C7/C8,-28,28,0,11,22\n" +
                    "22,Door 2,-28,29,0,21,77,100,181\n" +
                    "23,C6,-37,19,0,13,24\n" +
                    "24,C2,-38,19,0,23,25\n" +
                    "25,C4,-46,19,0,24,26,27\n" +
                    "26,C3,-46,16,0,25\n" +
                    "27,C6,-46,29,0,25,28\n" +
                    "28,Door 4,-46,31,0,27,29,175\n" +
                    "29,C5,-46,32,0,28\n" +
                    "30,Men's Toilets,-35,11,0,14\n" +
                    "31,Changing Room,-37,3,0,15,32\n" +
                    "32,Door 3,-41,3,0,31,162\n" +
                    "33,Gym,-35,0,0,16\n" +
                    "34,Canteen,-16,-10,0,17,35\n" +
                    "35,Dummy,-16,-20,0,34,36\n" +
                    "36,Door 5,-20,-20,0,35,145\n" +
                    "37,D2/D6,13,19,0,7,38\n" +
                    "38,Branch,17,19,0,37,39,40\n" +
                    "39,D4,17,16,0,38\n" +
                    "40,Workshop,22,19,0,38\n" +
                    "41,Dummy,1,3,3,20,42\n" +
                    "42,Dummy 2,2,3,3,41,43\n" +
                    "43,ASD Department,2,4,3,42,44\n" +
                    "44,Dummy 3,2,8,4,43,45\n" +
                    "45,Library,1,8,4,44,46\n" +
                    "46,Stairs,1,7,4,45,53\n" +
                    "47,Dummy,-3,16,2,8,48\n" +
                    "48,Dummy 2,-4,16,2,47,49\n" +
                    "49,Dummy 3,-4,19,4,48,50\n" +
                    "50,Stairs,-3,19,4,49,51,63\n" +
                    "51,E12,-1,19,4,50,52\n" +
                    "52,E13,0,19,4,51\n" +
                    "53,Dummy,1,4,6,46,54\n" +
                    "54,Dummy 2,2,4,6,53,55\n" +
                    "55,Dummy 3,2,7,8,54,56\n" +
                    "56,Stairs,1,7,8,55,57,66\n" +
                    "57,E20,0,7,8,56,58\n" +
                    "58,E21,0,10,8,57,59\n" +
                    "59,E22,0,16,8,58,60\n" +
                    "60,E24,0,19,8,59,61\n" +
                    "61,E23,-1,19,8,60,62\n" +
                    "62,Stairs,-3,19,8,61,65,71\n" +
                    "63,Dummy,-3,16,6,50,64\n" +
                    "64,Dummy 2,-4,16,6,63,65\n" +
                    "65,Dummy 3,-4,19,8,62,64\n" +
                    "66,Dummy,1,4,10,56,67\n" +
                    "67,Dummy 2,2,4,10,66,68\n" +
                    "68,Stairs,2,7,12,67,69\n" +
                    "69,E30,0,7,12,68,70\n" +
                    "70,E31,0,10,69\n" +
                    "71,Dummy,-3,16,10,62,72\n" +
                    "72,Dummy 2,-4,16,10,71,73\n" +
                    "73,Stairs,-4,19,12,72,74\n" +
                    "74,E33,-1,19,12,73,75\n" +
                    "75,E34,0,19,12,74,76\n" +
                    "76,E32,0,15,12,75\n" +
                    "77,Door,-28,41,0,22,78\n" +
                    "78,Entrance,-28,43,0,77,79,87\n" +
                    "79,B1,-28,47,0,78,80,81\n" +
                    "80,B5,-31,47,0,79\n" +
                    "81,B2,-27,47,0,79,82\n" +
                    "82,Women's Toilets,-26,47,0,81,83\n" +
                    "83,Men's Toilets,-25,47,0,82,84\n" +
                    "84,B3,-18,47,0,83,85\n" +
                    "85,B4,-15,47,0,84\n" +
                    "86,B6,-10,47,0,85\n" +
                    "87,Stairs,-31,43,0,78,88\n" +
                    "88,Dummy,-31,41,2,89\n" +
                    "89,Dummy 2,-30,41,2,90\n" +
                    "90,Stairs,-30,43,4,91\n" +
                    "91,B10,-30,46,4,90,92\n" +
                    "92,B11,-30,47,4,91,93\n" +
                    "93,B12,-27,47,4,92,94\n" +
                    "94,B16,-26,47,4,93,95\n" +
                    "95,B13,-24,47,4,94,96\n" +
                    "96,B14,-17,47,4,95,97\n" +
                    "97,B15,-14,47,4,96,98\n" +
                    "98,Stairs,-12,47,4,99\n" +
                    "99,Dummy,-12,40,0,98,100\n" +
                    "100,Dummy 2,-12,29,0,22,101\n" +
                    "101,Playground,8,29,0,100,102,110\n" +
                    "102,Door,8,56,0,101,103\n" +
                    "103,Entrance,6,56,0,102,104,106\n" +
                    "104,B7,6,63,0,103,105\n" +
                    "105,B8,6,64,0,104,106\n" +
                    "106,Branch,6,51,0,105,107,109\n" +
                    "107,Men's Toilets/Women's Toilets,5,51,0,106,108\n" +
                    "108,Disabled Toilets,3,51,0,107\n" +
                    "109,B6,6,48,0,106\n" +
                    "110,Playground,48,29,0,101,111\n" +
                    "111,Dummy,48,24,0,110,112\n" +
                    "112,Door,63,24,0,111,113\n" +
                    "113,Branch,66,24,0,112,114,119\n" +
                    "114,Branch 2,71,24,0,113,115,116,117\n" +
                    "115,Sports Hall,71,27,0,114\n" +
                    "116,Changing Room,71,23,0,114\n" +
                    "117,S2,78,24,0,114,118\n" +
                    "118,Fitness Room,83,24,0,117\n" +
                    "119,Stairs,66,22,0,113,120,183\n" +
                    "120,Dummy,66,20,1,119,121\n" +
                    "121,Dummy 2,64,20,2,120,122\n" +
                    "122,Stairs,64,22,4,121,183\n" +
                    "123,Disabled Toilets,68,22,4,124,183\n" +
                    "124,Men's Toilets/S11,69,22,4,123,125\n" +
                    "125,Women's Toilets,71,22,4,124,126\n" +
                    "126,Common Room,72,22,4,125\n" +
                    "127,Dummy,48,21,0,111,128,135\n" +
                    "128,Dummy 2,98,21,0,127,129\n" +
                    "129,L1,98,26,0,128,130\n" +
                    "130,Branch,98,31,0,129,131,132\n" +
                    "131,L2/L3,98,41,0,130\n" +
                    "132,Dummy,113,31,0,130,133\n" +
                    "133,Dummy,113,41,0,132,134\n" +
                    "134,L4/L5,135,41,0,133\n" +
                    "135,Branch,48,11,0,127,136,138\n" +
                    "136,Dummy,35,11,0,135,137\n" +
                    "137,Dummy 2,35,8,0,136,137\n" +
                    "138,Dummy,48,-9,0,135,139\n" +
                    "139,Branch,0,-9,0,0,138\n" +
                    "140,Branch,0,-29,0,139,141,142,144\n" +
                    "141,Cafe 42,23,-29,0,140\n" +
                    "142,Dummy,0,-44,0,140,143\n" +
                    "143,Swimming Pool,-35,-44,0,142\n" +
                    "144,M6,-15,-29,0,140,145\n" +
                    "145,Branch,-20,-29,0,36,144,146\n" +
                    "146,M5,-35,-29,0,145,147,148\n" +
                    "147,M1,-35,-19,0,146\n" +
                    "148,M4,-47,-29,0,146,149\n" +
                    "149,Branch,-57,-29,0,148,150,160\n" +
                    "150,J1,-67,-29,0,149,151,158\n" +
                    "151,Dummy,-70,-29,2,150,152\n" +
                    "152,Dummy 2,-70,-28,2,151,153\n" +
                    "153,Dummy 3,-67,-28,4,152,154\n" +
                    "154,Stairs,-65,-28,4,153,155\n" +
                    "155,J11,-65,-27,4,154,156,157\n" +
                    "156,J10,-67,-27,4,155\n" +
                    "157,J12,-64,-27,4,155\n" +
                    "158,Dummy,-87,-29,0,150,159\n" +
                    "159,Pavilion,-87,-39,0,158\n" +
                    "160,M3,-57,-19,0,149,161\n" +
                    "161,Dummy,-62,-19,0,160,162\n" +
                    "162,Branch,-62,3,0,32,161,184\n" +
                    "163,Door,-62,43,1,166,174,184\n" +
                    "164,A1,-63,43,1,165\n" +
                    "165,A2,-63,44,1,164,166\n" +
                    "166,Branch,-62,44,1,163,165,167,168\n" +
                    "167,Men's Toilets,-61,44,1,166\n" +
                    "168,Branch 2,-62,48,1,166,169,172\n" +
                    "169,Dummy,-62,52,1,168,170\n" +
                    "170,A2,-63,52,1,169,171\n" +
                    "171,A3,-62,53,1,170\n" +
                    "172,Women's Toilets,-60,52,1,168,173\n" +
                    "173,A4,-58,52,1,172\n" +
                    "174,Branch,-45,43,1,163,175,176\n" +
                    "175,Branch,-45,31,0,28,174,182\n" +
                    "176,Dummy,-37,43,1,174,177\n" +
                    "177,Dummy 2,-37,39,1,176,178\n" +
                    "178,Dummy 3,-44,39,1,177,179\n" +
                    "179,Dummy 4,-44,35,1,178,180\n" +
                    "180,Dummy 5,-37,35,0,179,181\n" +
                    "181,Branch,-37,29,0,22,180,182\n" +
                    "182,Dummy,-45,29,0,175,181\n" +
                    "183,Lift,66,22,4,119,122,123\n" +
                    "184,Dummy,-62,39,0,162,163";
            FileOutputStream fileOutputStream = activity.openFileOutput("network.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(WriteMessage.getBytes());
            fileOutputStream.close();
        } catch (IOException e){
            e.printStackTrace();
            //This branch should never run but is included in case of an unexpected error so that the app does not crash
        }
    }
}