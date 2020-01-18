// Program: BallData
// Written by: Steven Oslan a00233771
// Description: Provides information for bounces balls around a canvas using threads.
// Challenges: Getting it to run to the point that I can debug it.
// Time Spent: 4 hours

//                   Revision History
// Date:                   By:               Action:
// ---------------------------------------------------
// 04/27/2018             sho                 Created
// 04/27/2018             sho                 Submitted

import java.awt.Color;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallData implements Runnable{
    private static final int DIAMETER = 20, BASESTEP = 5;
    private double x, y, xStep, yStep;
    private int startAngle;
    private Random rand = new Random();
    private Color randColor;
    protected static Canvas canvas = new Canvas();
    
    
    public BallData(int width, int height){
        x = rand.nextInt(width);
        y = rand.nextInt(height);
        randColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        startAngle = 3 + rand.nextInt(85) + 90*rand.nextInt(4);
        xStep = BASESTEP;
        yStep = BASESTEP*Math.tan(startAngle);
    }
    
    public BallData(int x1, int y1, int x2, int y2){ //Allows the user to click-and-drag to "fire" a ball, controlling initial position, direction, and magnitude
        randColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        x = x1;
        y = y1;
        xStep = x2 - x1;
        yStep = y2 - y1;
    }
    
    public static int getDiameter(){
        return DIAMETER;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getXStep()
    {
        return xStep;
    }
    public double getYStep()
    {
        return yStep;
    }
    public void setXStep(double i)
    {
        xStep = i;
    }
    public void setYStep(double j)
    {
        yStep = j;
    }
    public Color getColor(){
        return randColor;
    }
    public static Canvas getCanvas(){
        return canvas;
    }
    
    @Override
    public void run(){
        if(x + xStep + DIAMETER > canvas.getWidth()){ //Checks whether the leftmost part of the ball is too far to the left
            x = 2*canvas.getWidth()-x-xStep; //Example: If the max width is 100, and we would be at 103, we want to be at 97, or 200-103.
            xStep *= -1; //Reverses x-direction (bounces)
        }
        else if(x + xStep < 0){ //Checks whether the rightmost part of the ball is too far to the right
            x += xStep; //We're now below zero by some amount...
            x *= -1;    //We want to be above zero by that same amount!
            xStep *= -1; //Reverses x-direction (bounces)
        }
        else{
            x += xStep; //Behaves normally
        }
        
        if(y + yStep + DIAMETER > canvas.getHeight()){ //Checks whether the bottommost part of the ball is too far down
            yStep *= -1; //Reverses y-direction (bounces)
        }
        else if(y + yStep < 0){ //Checks whether the topmost part of the ball is too far up
            y += yStep; //We're now below zero by some amount...
            y *= -1;    //We want to be above zero by that same amount!
            yStep *= -1; //Reverses y-direction (bounces)
        }
        else{
            y += yStep; //Behaves normally
        }
        
    }
}