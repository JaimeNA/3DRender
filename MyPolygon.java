
import java.awt.*;

public class MyPolygon {

    //PRIVATE

    private MyPoint[] points;

    
    //PUBLIC

    public MyPolygon(MyPoint... points){

        this.points = new MyPoint[points.length];

        for(int i = 0; i < points.length; i++){ // copies the point array recieved into this point array 

            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);

        }

    }
    
    public void render(Graphics g){

        Polygon poly = new Polygon();

        for(int i = 0; i < points.length; i++){

            poly.addPoint(PointConverter.convertPoint(points[i]).x, PointConverter.convertPoint(points[i]).y); // passing all the points to the polygon

        }

        g.setColor(new Color(100, 100, 100));
        g.drawPolygon(poly);

    }

    public void rotateX(double d){

        for(MyPoint p : this.points){
        
            PointConverter.rotateX(p, d);

        }

    }

    public void rotateZ(double d){

        for(MyPoint p : this.points){
        
            PointConverter.rotateZ(p, d);

        }

    }

}
