import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PaintShop {
    ArrayList<String> colorPattern = new ArrayList<>();
    ArrayList<String> answerSet = new ArrayList<>();
    int Count = 0;
    String solution = "";
    boolean[][] matrix;

    public PaintShop(String filename)
    {
        try{
            Scanner in = new Scanner(new File(filename));
            in.nextLine();
            String[] arrofColor = in.nextLine().split(" "); 
            for(int i = 0; i < arrofColor.length; i++)
            {
                colorPattern.add(arrofColor[i]);
            }
            matrix = new boolean[colorPattern.size()][colorPattern.size()];
            
            int similar = Integer.parseInt(in.nextLine());
            for(int i = 0; i < similar; i++)
            {
                String[] collision = in.nextLine().split(" ");
                matrix[colorPattern.indexOf(collision[0])][colorPattern.indexOf(collision[1])] = true;
                matrix[colorPattern.indexOf(collision[1])][colorPattern.indexOf(collision[0])] = true;
            }
            for(int i = 0; i < colorPattern.size(); i++){
                for(int j = 0; j < colorPattern.size(); j++)
                {
                    if (i == j) matrix[i][j] = true;
                }
            }
            
            in.close();
        }
        catch(FileNotFoundException e){
            System.out.print("no file");
        }
    }

    public boolean promising(int row, int column){
        boolean check = false;
        if(matrix[row][column] == true){
            check = true;
        }
        return check; 
    }
    
    public void checkNode(int row){
        String color; 
        if(answerSet.size() == 0)
        {
            color = colorPattern.get(row);
            answerSet.add(color);
            row ++;
            checkNode(row);
        }
        else{
            for(int column = 0; column < colorPattern.size(); column++)
            {
                if(promising(row, column) == false && (!answerSet.contains(colorPattern.get(column))))
                {
                    color = colorPattern.get(column);
                    answerSet.add(color);
                    checkNode(column);
                }
            
            }

            if(answerSet.size() == colorPattern.size())
            {
                Count++;
                System.out.println(Count);
                solution = answerSet.toString();
                
            }
            else
            {
                for(int k = answerSet.size()-1; k >= 0; k--)
                {
                    answerSet.remove(answerSet.get(k));
                }
            }
            

        }

    }

    public String getSolution()
    {
        for(int i = 0; i < colorPattern.size(); i++)
        {
            checkNode(i);
        }

        if(solution.equals(""))
        {
            solution = "NONE";
        }
        System.out.println(Count);
        return solution;
    }

    public int howManySolutions()
    {
        return Count;
    }
}