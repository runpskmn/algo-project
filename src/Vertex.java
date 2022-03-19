
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
/**
 *
 * @author vento
 */
public class Vertex {

    int x;
    int y;
    String sum;
    int r;
    int shift ;
    boolean isAnswer;
    Vertex parent;
    int[] xs;
    
    
    Vertex(int x, int y, String sum, Vertex parent, boolean isAnswer, int xs[]) {
        this.sum = sum;
        this.isAnswer = isAnswer;
        this.r = 35;
        this.shift = 29;
        this.x = x;
        this.y = y;
        this.xs = xs;
    }
    

    void draw(Graphics2D g) {
        g.setColor(isAnswer ? Color.BLUE : Color.BLACK);
        g.setStroke(new BasicStroke(2));

        g.fillRect(x - r, y - r, r+20 , r );

        g.setColor(Color.WHITE);
        g.fillRect(x - r + (r - shift) / 2, y - r + (r - shift) / 2, r+20  - (r - shift), r  - (r - shift));
            
        g.setColor(isAnswer ? Color.BLUE : Color.BLACK);
        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.drawString("-999".equals(sum) ? "-∞" : sum , x - 15, y - 13);
    }

}

