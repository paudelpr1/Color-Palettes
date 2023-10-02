/**
Date: 04/10/2022

Course: CSCI 3005 60360

Description: This program uses backtracking algorithm approach to display the patterns of colors while avaoiding
              color collisions. This program displays the color on the basis of their popularity with less popular
              color toward the right side. It also displays the number of color patterns that can be generated if there was
              no any color preference. 
             
On my honor, I have neither given nor received unauthorized help while
completing this assignment.

Name: Prasansha Paudel
CWID: 30120811

*/

/*imports required java classes */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * PaintShop class uses a text file to obtain colors based on their popularity and generates the color pattern while avoiding 
 * color collision. 
 * It uses backtracking algorithm to generate the color pattern and possible number of color arrangements. 
 */
public class PaintShop{

    //declares and initializes arrylist to store the contents of text file. 
    ArrayList<String> colorPattern = new ArrayList<>(); //This arraylist stores the color ordered in decreasing order of popularity.
    ArrayList<String> answerSet = new ArrayList<>();    // This arraylist stores the color ordered in decresing order of popularity while avaoiding collision.

    //global variables
    int Count = 0;          //this variable stores number of possible color arrangements
    String solution = "";   //this varibale stores the possible color pattern.
    boolean[][] matrix;     //this variable stores the adjacency matrix boolean values

    /**
     * Reads the text file called filename and stores the given color arrangement in an arraylist
     * It creates adjacency matrix to help avoid collision.
     * @param filename a text file containing color arrangement and possible color collisions. 
     */
    public PaintShop(String filename)
    {
        /*try block reads filename and adds the colors inside the arraylist */
        try{
            Scanner in = new Scanner(new File(filename));
            in.nextLine();
            String[] arrofColor = in.nextLine().split(" "); 
            for(int i = 0; i < arrofColor.length; i++)
            {
                colorPattern.add(arrofColor[i]);
            }

            //initializes the matrix to the size of the arraylist
            matrix = new boolean[colorPattern.size()][colorPattern.size()];
            
            //number of collisions
            int similar = Integer.parseInt(in.nextLine());

            //finds the number of collision in the color patterns and gives the boolean value for arraylist. 
            for(int i = 0; i < similar; i++)
            {
                String[] collision = in.nextLine().split(" ");
                matrix[colorPattern.indexOf(collision[0])][colorPattern.indexOf(collision[1])] = true; //assigns ture matrix value for the colors with collision
                matrix[colorPattern.indexOf(collision[1])][colorPattern.indexOf(collision[0])] = true; //assigns ture matrix value for the colors with collision
            }

            //goes through each row of matrix 
            for(int i = 0; i < colorPattern.size(); i++){ 
                for(int j = 0; j < colorPattern.size(); j++) // goes through each column of matrix 
                {
                    if (i == j) matrix[i][j] = true; // assigns true to values having same row and column.
                }
            }
            
            in.close();
        }

        //catch block to prevent exception
        catch(FileNotFoundException e){
            System.out.print("no file");
        }
    }

    /**
     * This is a boolean method that checks the matrix and gives the boolean value if the given condition is true 
     * @param row row of the matrix
     * @param column column of the matrix 
     * @return check returns true if the given condition matches else returns false. 
     */
    public boolean promising(int row, int column){
        boolean check = false;
        if(matrix[row][column] == true){
            check = true;
        }
        return check; 
    }

    /**
     * This method uses promising method to find the color colision and finds the pattern of the colrs while 
     * avoiding collision. THis is a recursive method. 
     * @param row original index of colors that is stored in the colorPattern arraylist
     */
    public void checkNode(int row){
        //local variable to store the color from the original arraylist. 
        String color; 

        //condition to check if the answerSet arraylist is empty. 
        if(answerSet.size() == 0)
        {
            color = colorPattern.get(row); //gets the first color from the original arraylist
            answerSet.add(color);          // adds the color to the arraylist that stores fianl color pattern
            row ++;                        //increases row value 
            checkNode(row);                //recursively calls the same checkNode method to repeat the process
            
        }

        //condition if the answerSet is not empty
        else{
            //goes through each element in the original arraylist
            for(int column = 0; column < colorPattern.size(); column++)
            {
                //condition to check if there is any collision in the matrix at given row and column 
                //condition to check if the obtained color is already added to the final arrayList
                if(promising(row, column) == false && (!answerSet.contains(colorPattern.get(column))))
                {
                    color = colorPattern.get(column);  //obtains the color at given index
                    answerSet.add(color);              //adds the color to the final arraylist
                    checkNode(column);                 //initializes recursive method from the given index.
                }
            
            }
        }

        // once the recursion is completed, check if the size of original array is equal to the final array. 
        if(answerSet.size() == colorPattern.size())
        {
            Count++;   //increases count if the solution is found.
            solution = answerSet.toString();  //stores the value of arraylist in string variable using toString method. 
            
        }

        //condition if the required solution is not found
        else
        {
            //removes all the values of final arraylist to initialize new count.
            for(int k = answerSet.size()-1; k >= 0; k--)
            {
                answerSet.remove(answerSet.get(k));
            }
        }

    }

    /**
     * This method displays the final pattern of colors without collision.
     * If no solution is found, THe method returns NONE. 
     * @return solution string varibale containing the output of colors
     */
    public String getSolution()
    {
        //initializes for loop to find the promising color patterns from every element of arraylist.
        for(int i = 0; i < colorPattern.size(); i++)
        {
            checkNode(i);  //calls recursive method checknode at every index.
        }

        //condition if no color pattern is found
        if(solution.equals(""))
        {
            solution = "NONE";   //assigns none to output.
        }

        //returns the string variable consisting of color patterns without collision.
        return solution;
    }

    /**
     * This method gives the number of possible color arrangements that can be formed if there is
     *  no color preference based on their popularity.
     * @return Count the number of color arrangement patterns that can be formed. 
     */
    public int howManySolutions()
    {
        //returns the int value that represents the number of color arrangements that can be formed. 
        return Count;
    }
}