package com.company;

import java.util.Scanner;
import java.util.Random;

public class Main {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand= new Random();
        boolean winConditionMet=false;
        System.out.println("**** Battle Ship game ****");
        char[][] playerOneMap= initializeCleanMap();
        char[][] computerMap= initializeCleanMap();
        displayMap(playerOneMap);
        playerOneMap= setPlayerShips(input,playerOneMap);
        computerMap= setComputerShips(rand,computerMap);
        while (!winConditionMet){
            computerMap=playerOneMove(input,computerMap);
            displayMap(playerOneMap);
            winConditionMet=checkForBoats(computerMap);
            if(winConditionMet){
                System.out.println("!Congratulations! You have Won!");
                System.out.println("Computer Map:");
                displayMap(computerMap);
                System.out.println("Player Map:");
                displayMap(playerOneMap);
            }
            playerOneMap = computerMove(rand, playerOneMap);
            winConditionMet=checkForBoats(playerOneMap);
            if(winConditionMet){
                System.out.println("!Massive Loss of Fleet!");
                System.out.println("The Computer has won!");
                System.out.println("Computer Map:");
                displayMap(computerMap);
                System.out.println("Player Map:");
                displayMap(playerOneMap);

            }

        }

    }

    private static char[][] computerMove(Random rand, char[][] map) {
        boolean newShot=false;

        while(!newShot){
            System.out.println("Computer is determining coordinates for torpedo deployment");
            int x = rand.nextInt(9);
            int y = rand.nextInt(9);
            if((map[x][y] == '·')){
                newShot = true;
                map[x][y]='-';
                System.out.println("Computer has made an attack.");
                System.out.println("Wooosh!");
            } else if(map[x][y]=='@'){
             newShot= true;
             map[x][y]='X';
             System.out.println("Computer has made an attack.");
             System.out.println("!It was a HIT!");
            }
        }
        return map;
    }

    private static boolean checkForBoats(char[][] map) {
        System.out.println("Checking for hidden boats");
        int hiddenBoats=5;
        for (int y=map.length-1;y>=0;y--){

            for (int x=0;x<map[0].length;x++){
                if(map[x][y]=='X'){
                    hiddenBoats--;
                }
            }
        }

        System.out.println("there are " +hiddenBoats +" hidden boats left.");
        if (hiddenBoats==0){
            return true;
        }else return false;
    }

    private static char[][] playerOneMove(Scanner input, char[][] computerMap) {
        boolean hit = false;
        int x=100;
        int y=100;
        /*TODO Test for condition of placing a torpedo where there has already been a hit or a miss.
           Currently, placing  a torpedo on a location where there has already been a miss makes the game unwinnable*/
        System.out.println("Computer Map:");
        displayMapHideBoats(computerMap);
        System.out.println("Player One Move");
        while ((x>9)||(x<0)){
            System.out.print("Enter X coordinate for torpedo between 0 and 9: ");
            x= input.nextInt();
            if (((x>9)||(x<0))){
                System.out.print("!Error! Coordinate out of bounds. ");
            }
        }
        while ((y>9)||(y<0)){
            System.out.print("Enter Y coordinate for your ship between 0 and 9: ");
            y= input.nextInt();
            if (((y>9)||(y<0))){
                System.out.print("!Error! Coordinate out of bounds. ");
            }
        }
        if (computerMap[x][y]=='@'){
            System.out.println("That was a hit!");
            computerMap[x][y]='X';
            displayMapHideBoats(computerMap);
        }else{
            System.out.println("'woosh'");
            computerMap[x][y]='-';
            displayMapHideBoats(computerMap);
        }
        return computerMap;
    }

    private static char[][] setComputerShips(Random rand,char[][] map) {

        System.out.println("The Computer is setting it's ships");
        for(int i=0;i<5;){
            int x= rand.nextInt(9);
            int y= rand.nextInt(9);
            if (map[x][y]!='@'){
                map[x][y]= '@';
                i++;
            }
        }
        return map;
    }

    private static char[][] setPlayerShips(Scanner input, char[][] map) {

        for (int i=0; i<5;i++){
            map=placeOneBoat(input,map);
        }
        return map;
    }

    private static char[][] initializeCleanMap() {
        System.out.println("Initializing maps");
        char[][] map= new char[10][10];
        for(int i=0;i<map.length;i++){
            for (int j=0;j<map[0].length;j++) {
                map[i][j] = '·';
            }
        }
        return map;
    }

    public static void displayMap(char[][] map){
        System.out.println("@|0123456789|@");
        for (int y=map.length-1;y>=0;y--){
            System.out.print(y+"|");
            for (int x=0;x<map[0].length;x++){
                 System.out.print(map[x][y]);
            }
            System.out.println("|"+y);
        }
        System.out.println("@|0123456789|@");
    }

    public static void displayMapHideBoats(char[][] map){
        System.out.println("@|0123456789|@");
        for (int y=map.length-1;y>=0;y--){
            System.out.print(y+"|");
            for (int x=0;x<map[0].length;x++){
                if (map[x][y]=='@')
                    System.out.print('·');
                else System.out.print(map[x][y]);
            }
            System.out.println("|"+y);
        }
        System.out.println("@|0123456789|@");

    }

    public static char[][] placeOneBoat(Scanner input,char[][] map){
        int x = 10;
        int y = 10;
        boolean hit=false;
        while(hit==false){
            while ((x>9)||(x<0)){
                System.out.print("Enter X coordinate for a ship between 0 and 9: ");
                x= input.nextInt();
                if (((x>9)||(x<0))){
                    System.out.print("!Error! Coordinate out of bounds. ");
                }
            }

            while ((y>9)||(y<0)){
                System.out.print("Enter Y coordinate for a ship between 0 and 9: ");
                y= input.nextInt();
                if (((y>9)||(y<0))){
                        System.out.print("!Error! Coordinate out of bounds. ");
                    }
            }
            if (map[x][y]!='@') {
                map[x][y]='@';
                hit=true;
                displayMap(map);
            }else{
                System.out.println("There is already a ship in this location");
            }
        }
        return map;
    }
}
