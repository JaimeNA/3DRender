import java.awt.*;

public class Tetrahedron {
    
    private MyPolygon[] polygons;

    public Tetrahedron(MyPolygon... polygons){

        this.polygons = polygons;

    }

    public void render(Graphics g){

        for(MyPolygon poly : this.polygons){

            poly.render(g);

        }
        
    }

    public void rotateX(double d){

        for(MyPolygon p : this.polygons){
        
            p.rotateX(d);

        }

    }

    public void rotateZ(double d){

        for(MyPolygon p : this.polygons){
        
            p.rotateZ(d);

        }

    }

}
