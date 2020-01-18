//Run with: javac Canvas.java; java Canvas
// Program: Canvas
// Written by: Steven Oslan a00233771
// Description: Provides the canvas for creating and bouncing balls off the edges.
// Challenges: Static variables, threads, anywhere the word "canvas" appears 3 times over 2 lines
// Time Spent: 4 hours

//                   Revision History
// Date:                   By:               Action:
// ---------------------------------------------------
// 04/27/2018             sho                 Created
// 04/27/2018             sho                 Submitted
// 01/03/2020             sho                 Adding gravity
/*
Game: Gravity Golf
Example: Start with a red, a green, and a blue dot moving up and down horizontally. Get them into holes (through or into appropriate areas)
by clicking anywhere to create a gravity pulse at that location for the duration of the click. Optionally, creates a "gravity ball" with some properties.
Lasts X seconds, pulses in power, moves away from other balls, pulls the hole areas towards it, pushes the hole areas away...
Program your balls? (build an arsenal; each upgrade costs so many points)
TODO: Push-a-key functionality. Possibly buttons that trigger what happens when you click.
TODO: "time deltas" loop; have the program refresh as quickly as it can, and move based on how long it's been since the last update.
*/
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Canvas extends JFrame{ //extends JFrame
    private static Canvas canvas;
    private ArrayList<BallData> balls = new ArrayList<BallData>();
    private BallData bd = new BallData(1000, 1000);
    private JPanel p1 = new JPanel();
    private int x1, y1, x2, y2, mouseX, mouseY;
    private long dt = 1;

    final float MS_BETWEEN_UPDATE = 25f;
    final int GRAVITY_FORCE = 3;
    


    public Canvas(){
        
        p1.addMouseListener(new ClickListener());
        p1.addMouseMotionListener(new MouseMotionListener());
        p1.addKeyListener(new KeyboardListener());
        add(p1);
        balls.add(bd);
        //canvas = BallData.getCanvas();
        //canvas.addMouseListener(new ClickListener());
        Thread th = new Thread(bd);
        setForeground(bd.getColor());
        th.start();
        repaint();
    }

    public long getDt(){
        return dt;
    }

    public void gravity()
    {
        for(BallData a : balls)
        {
            for(BallData b : balls)
            {
                if(a != b)
                {
                    double dx = b.getX() - a.getX();
                    double dy = b.getY() - a.getY();
                    double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); //hopefully not 0 :)
                    double dxStep = dt/MS_BETWEEN_UPDATE * GRAVITY_FORCE*dx/distance;
                    double dyStep = dt/MS_BETWEEN_UPDATE * GRAVITY_FORCE*dy/distance;
                    a.setXStep(a.getXStep() + dxStep);
                    a.setYStep(a.getYStep() + dyStep);
                }
            }
        }
    }

    public void paint(Graphics g){
        long time = System.currentTimeMillis();
        //try {
            //Thread.sleep(25);
            super.paint(g);
            dt = System.currentTimeMillis() - time;
        //} catch (InterruptedException ex) {
        //    Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
        //}
        gravity();
        for(BallData b : balls){
            g.setColor(b.getColor());
            g.fillOval((int)b.getX(), (int)b.getY(), BallData.getDiameter(), BallData.getDiameter());
            b.run();
        }
    }
    class ClickListener extends MouseAdapter{ //Only defines mouseClicked method; others are initialized to empty method by MouseAdapter.
        public void mouseClicked(MouseEvent e){
            balls.add(new BallData(1000, 1000));
            //Creates another bouncy ball at random position of random color.
        }
        public void mousePressed(MouseEvent e){
            x1 = e.getX();
            y1 = e.getY();
        }
        public void mouseReleased(MouseEvent e){
            x2 = e.getX();
            y2 = e.getY();
            balls.add(new BallData(x1, y1, x2, y2));
        }
    }
    //below was added for gravity
    class MouseMotionListener extends MouseMotionAdapter{
        public void mouseMoved(MouseEvent e){ //this definitely works
            //if(mouseX - e.getX() < 100 && mouseY - e.getY() < 100)
            //{
            //    System.out.println("GRAVITY EVENT HAPPENING!");
                for(BallData a : balls){
                    double dx = e.getX() - a.getX();//mouseX;
                    double dy = e.getY() - a.getY();//mouseY;
                    double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    double dxStep = 10*GRAVITY_FORCE*dx/distance;
                    double dyStep = 10*GRAVITY_FORCE*dy/distance;
                    a.setXStep(a.getXStep() + dxStep);
                    a.setYStep(a.getYStep() + dyStep);
                }
            //}
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    class KeyboardListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                System.out.println("WORKING!");
            }
            //if(e.getKeyChar() == 'g')
                System.out.println("in KeyboardListener");
                for(BallData a : balls){
                    double dx = a.getX() - mouseX;
                    double dy = a.getY() - mouseY;
                    double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    double dxStep = GRAVITY_FORCE*dx/distance;
                    double dyStep = GRAVITY_FORCE*dy/distance;
                    a.setXStep(a.getXStep() + dxStep);
                    a.setYStep(a.getYStep() + dyStep);
                }
        }
        @Override
        public void keyReleased(KeyEvent e){
            //if(e.getKeyChar() == 'g')
                System.out.println("in KeyboardListener");
                for(BallData a : balls){
                    double dx = a.getX() - mouseX;
                    double dy = a.getY() - mouseY;
                    double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    double dxStep = GRAVITY_FORCE*dx/distance;
                    double dyStep = GRAVITY_FORCE*dy/distance;
                    a.setXStep(a.getXStep() + dxStep);
                    a.setYStep(a.getYStep() + dyStep);
                }
        }
        @Override
        public void keyTyped(KeyEvent e){
            //if(e.getKeyChar() == 'g')
                System.out.println("in KeyboardListener");
                for(BallData a : balls){
                    double dx = a.getX() - mouseX;
                    double dy = a.getY() - mouseY;
                    double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    double dxStep = GRAVITY_FORCE*dx/distance;
                    double dyStep = GRAVITY_FORCE*dy/distance;
                    a.setXStep(a.getXStep() + dxStep);
                    a.setYStep(a.getYStep() + dyStep);
                }
        }
        //above was added for gravity

        //below is an attempt at something new
        /*public Test(){
            this.get
        }*/

        //above is an attempt at something new
    }

    //below copy pasted
    private void display() {
        JFrame f = new JFrame("LinePanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        //f.add(new ControlPanel(), BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    //above copy pasted
    public static void main(String[] args){
        Canvas canvas = BallData.getCanvas();
        //canvas.display(); //unsure
        canvas.setVisible(true);
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //bindings
        canvas.setSize(1000, 1000);
        while(true){
            canvas.repaint();
            //catch (InterruptedException ex) {
            //    Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            //}
        }
    }
}