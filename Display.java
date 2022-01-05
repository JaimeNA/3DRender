import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

// subclass of Canvas and using elements of Runnable interface(similar to thread)
public class Display extends Canvas implements Runnable{

    //PRIVATE

    private static final long serialVersionUID = 1L;

    private Thread thread;
    private JFrame frame;

    private Tetrahedron tetra;

    private static boolean running = false;

    private void render(){

        BufferStrategy bs = this.getBufferStrategy(); // the mechanism with which to organize complex memory on a particular Canvas or Window
        // application draws to a single back buffer and then moves the contents to the front 

        if(bs == null){ // if is not initialized

            this.createBufferStrategy(3);
            return;

        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, WIDTH);
        
        tetra.render(g);
        
        g.dispose( );
        bs.show(); // display the drawn buffer

    }

    private void update(){

        this.tetra.rotateZ(1);
        this.tetra.rotateX(1);

    }

    //PUBLIC

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public Display(){ // contructor

        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);

        this.setPreferredSize(size);

        this.tetra = new Tetrahedron(
        new MyPolygon(new MyPoint(0, 0, 0), new MyPoint(0, 100, 0), new MyPoint(100, 100, 0), new MyPoint(100, 0, 0)),
        new MyPolygon(new MyPoint(0, 0, 0), new MyPoint(0, 0, -100), new MyPoint(0, 100, -100), new MyPoint(0, 100, 0)),
        new MyPolygon(new MyPoint(100, 0, 0), new MyPoint(100, 0, -100), new MyPoint(100, 100, -100), new MyPoint(100, 100, 0)),
        new MyPolygon(new MyPoint(0, 0, -100), new MyPoint(0, 100, -100), new MyPoint(100, 100, -100), new MyPoint(100, 0, -100)));
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

                render();
                FPS++; // frame per seconds
    
            }

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