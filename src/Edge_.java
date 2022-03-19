
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

/**
 *
 * @author vento
 */
public class Edge_ {

    Vertex vertexA;
    Vertex vertexB;
    String weight;


    int shift;
    int x_center;
    int y_center;
    int r_center;
    
    Edge_(Vertex a, Vertex b, boolean include) {
        this.vertexA = a;
        this.vertexB = b;

        this.r_center = 50;
        
        this.weight = include ? "1" : "0";
        this.shift = include ? -10 : 10;

    }


    void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));

        g.drawLine(vertexA.x, vertexA.y, vertexB.x, vertexB.y);

        g.drawString(weight, (vertexA.x+vertexB.x)/2+shift, (vertexA.y+vertexB.y)/2);
    }
    
}