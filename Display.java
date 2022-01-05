import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

// subclass of Canvas and using elements of Runnable interface(similar to thread)
public class Display extends Canvas implements Runnable{

    //PRIVATE

    private static final long serialVersionUID = 1L;

    private Thread thread;
    private JFrame frame;
    private BufferStrategy bs;
    private Graphics g;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static boolean running = false;

    private void render(){

        this.bs = this.getBufferStrategy(); // the mechanism with which to organize complex memory on a particular Canvas or Window
        // application draws to a single back buffer and then moves the contents to the front 

        if(this.bs == null){ // if is not initialized

            this.createBufferStrategy(3);
            return;

        }

        this.g = this.bs.getDrawGraphics();

        g.setColor(new Color(0, 100, 0));
        g.fillRect(100, 100, 400, 400);        

        g.dispose();
        bs.show(); // display the drawn buffer

    }

    private void update(){



    }

    //PUBLIC

    public Display(){ // contructor

        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);

        this.setPreferredSize(size);

    }

    public static void main(String[] args){

        Display display = new Display();//creating display object

        // frame settings
        display.frame.setTitle("3D Renderer");
        display.frame.add(display);
        display.frame.pack(); // sizes the frame so that all its contents are at or above their preferred sizes
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setVisible(true);

        display.start();

    }

    /* When one thread is executing a synchronized method for an object,
    all other threads that invoke synchronized methods for the same object
    block (suspend execution) until the first thread is done with the object */

    public synchronized void start(){

        running = true;
        this.thread = new Thread(this, "Display");

        this.thread.start(); // start the thread

    }

    public synchronized void stop(){

        running = false;

        try{ // try and catch block

            this.thread.join();

        } catch(InterruptedException e){

            e.printStackTrace();

        }

    }

    @Override // overrides the method of the parent class
    public void run(){

        // clock parameters
        long lastTime = System.nanoTime();
        long now = 0;
        long timer = System.currentTimeMillis();
        final double nanoSecs = 1000000000.0 / 60.0; // 60 updates per second - 1B nanoseconds = 1 second
        double deltaTime = 0;
        int FPS = 0;

        //MAIN LOOP
        while(running){

            // clock
            now = System.nanoTime();
            deltaTime += (now - lastTime) / nanoSecs;
            lastTime = now;

            while(deltaTime >=1){

                update();

                deltaTime--;

            }

            render();
            FPS++; // frame per seconds

            // display FPS
            if(System.currentTimeMillis() - timer > 1000){

                timer += 1000;
                this.frame.setTitle("3D Renderer || FPS: " + FPS); // displaying fps on window title

                FPS = 0; // reseting FPS
            }

        }

        this.stop();

    }

}