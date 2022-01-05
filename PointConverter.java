
import java.awt.Point;

// static class - no instance of this class will be created
public class PointConverter {
    
    private static double scale = 1;

    // convert 3D coordinates to be used in 2D coordinates to make the 3D illusion
    public static Point convertPoint(MyPoint point3D){

        // scaling of necessary
        double x3D = point3D.x * scale; 
        double y3D = point3D.y * scale;
        double depth = point3D.z * scale;

        double[] newVal = scale(x3D, y3D, depth);

        // the point3D coordinates, but starting from the middle of the frame
        int x2D = (int)(Display.WIDTH / 2 + newVal[0]);
        int y2D = (int)(Display.HEIGHT / 2 - newVal[1]);// inverts the axis so up = y > 1

        Point point2D = new Point(x2D, y2D);

        return point2D;
    }

    private static double[] scale(double x3d, double y3d, double depth){

        double dist = Math.sqrt(x3d*x3d + y3d*y3d); // getting the hypotenuse(depth)
        double theta = Math.atan2(x3d, y3d);// angle of the z axis - to get a perspective illusion
        double depth2 = 15 - depth; // distance of the camera from the origin
        double localScale = Math.abs(1400/(depth2+1400));// how much we are truncating the vector - have a value between 0 and 1

        dist *= localScale;

        double[] newVal = new double[2];

        newVal[0] = dist * Math.cos(theta);
        newVal[1] = dist * Math.sin(theta);

        return newVal;
    }

    public static void rotateX(MyPoint p, double d){

        double radius = Math.sqrt(p.x*p.x + p.y*p.y);
        double theta = Math.atan2(p.x, p.y);
        theta += 2*Math.PI/360*d;// converting to radiants
        p.x = radius * Math.cos(theta);
        p.y = radius * Math.sin(theta);


    }

    public static void rotateZ(MyPoint p, double d){

        double radius = Math.sqrt(p.z*p.z + p.y*p.y);
        double theta = Math.atan2(p.z, p.y);
        theta += 2*Math.PI/360*d;// converting to radiants
        p.z = radius * Math.sin(theta);
        p.y = radius * Math.cos(theta);


    }

}
