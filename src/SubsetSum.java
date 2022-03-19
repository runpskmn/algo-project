import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringJoiner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.Arrays;
import javax.imageio.ImageIO;


public class SubsetSum extends JFrame implements MouseListener, MouseMotionListener{

    ArrayList<Vertex> vs = new ArrayList<>();
    ArrayList<Edge_> es = new ArrayList<>();
    
    JPanel c;
    JScrollPane panelPane;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 14);
    Font font = new Font("SanSerif", Font.PLAIN, 28);
    
    
    int[] set;
    int target;
    int n;
    int[] x;   
    int algoIndex;
    String algoName;
    int step = 0;
    
    int xSize;
    int ySize;
    int height;
    boolean saveImage;
    String filePath;
    
    public SubsetSum(String[] strSet, int  target, int algoIndex, boolean saveImage, String filePath, String algoName){
        super("Subset Sum - " + algoName);
        this.n = strSet.length;
        this.set = new int[n];;
        this.x = new int[n];
        this.saveImage = saveImage;
        this.filePath = filePath;
        this.algoName = algoName;
        this.target = target;
        this.algoIndex = algoIndex;
        for(int i=0;i<strSet.length;++i){
            set[i] = Integer.parseInt(strSet[i]);
        }
        // create a empty canvas 
        c = new JPanel(){
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
                draw();
            }
        };

        // add mouse listener 
        c.addMouseListener(this);
        c.addMouseMotionListener(this);
        xSize = 30*(int)Math.pow(2, n);
        ySize = (set.length+1)*120;
        height = (screenSize.height - getHeight())-50;
       
        c.setPreferredSize(new Dimension((xSize+30)*2, ySize));
        panelPane = new JScrollPane(c);
        panelPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   
        //c.setSize(5000,5000);
       
        panelPane.setBounds(0, 0, (screenSize.width - getWidth())-100, ySize < height ? ySize+100 : height);

        setBounds(0, 0, (screenSize.width - getWidth())-100, ySize < height ? ySize+100 : height);
   
        //setUndecorated(true);
        //setVisible(true);
        add(panelPane);
        show();
    }
    
     void branchAndBound(int set[], int target){
        PriorityQueue<pqSetCompare> pQueue = new PriorityQueue<>();
        int a[] = new int[0];
        //System.out.println(a.length);
        ArrayList<Vertex> parent = new ArrayList<>();
        pQueue.add(new pqSetCompare(0,a));
        while(!pQueue.isEmpty()){ 
            draw();
            step++;
            pqSetCompare t = pQueue.remove();
            int[] x = t.x;
           int sum = sum(set,x,x.length);
            if(x.length == 0)
                parent.add(calculatePos(x.length, true, sum > target ? -999 : sum, null, (sum == target), x));
            
            if(sum == target){
                try{
                    FileWriter fw = new FileWriter(filePath);
                    BufferedWriter bfw = new BufferedWriter(fw);
                    for(int i : set)
                        bfw.write(i + " ");
                    bfw.newLine();
                    bfw.write(target+"");
                    bfw.newLine();
                    for(int i : x)
                        bfw.write(i + " ");
                    
                    for(int i=0;i<set.length-x.length;++i)
                        bfw.write(0 + " ");
                    bfw.close();
                    
                    if(sum == target){
                        for(int i=0;i<x.length;++i)
                            System.out.print(x[i] + " ");
                        
                        for(int i=0;i<set.length-x.length;++i)
                            System.out.print(0 + " ");
                        
                        System.out.println("");
                        break;
                    }
                }catch (IOException e) {
                   System.out.println("An error occurred.");
                   e.printStackTrace();
                }
                break;
            }
            if(x.length == set.length){
                 if(sum == target){
                     for(int i=0;i<x.length;++i)
                         System.out.print(x[i] + " ");
                     System.out.println("");
                     break;
                 }
            }else{
                int[] x0 = Arrays.copyOf(x, x.length+1);
                x0[x.length] = 0;
                if(x0.length != 0){
                    Vertex p = parent.get(0);
                    for(Vertex v : parent){
                        boolean samething = false;
                         if(v.xs.length == x0.length-1){
                             for(int i=0;i<x0.length-1;++i){
                                 //System.out.print(v.xs[i] + " ");
                                 if(v.xs[i] == x0[i])
                                     samething = true;
                                 else{
                                     samething = false;
                                     break;
                                 }
                             }
                             //System.out.println("");
                             if(samething){
                                 p = v;
                                 break;
                             }
                         }
                    }
                    parent.add(calculatePos(x0.length, (x0[x0.length-1] == 1), sum(set, x0, x0.length) > target ? -999 : sum(set, x0, x0.length), p, (sum(set, x0, x0.length) == target), x0));
                }
                int[] x1 = Arrays.copyOf(x, x.length+1);
                x1[x.length] = 1;
                if(x1.length != 0){
                    Vertex p = parent.get(0);
                    for(Vertex v : parent){
                        boolean samething = false;
                         if(v.xs.length == x1.length-1){
                             for(int i=0;i<x1.length-1;++i){
                                 //System.out.print(v.xs[i] + " ");
                                 if(v.xs[i] == x1[i])
                                     samething = true;
                                 else{
                                     samething = false;
                                     break;
                                 }
                             }
                             //System.out.println("");
                             if(samething){
                                 p = v;
                                 break;
                             }
                         }
                    }
                    parent.add(calculatePos(x1.length, (x1[x1.length-1] == 1), sum(set, x1, x1.length) > target ? -999 : sum(set, x1, x1.length), p, (sum(set, x1, x1.length) == target), x1));
                }
                pQueue.add(new pqSetCompare(sum(set, x0, x0.length) > target ? -999 : sum(set, x0, x0.length) , x0));
                pQueue.add(new pqSetCompare(sum(set, x1, x1.length) > target ? -999 : sum(set, x1, x1.length), x1));
            }
        }
        
    }
    
    void backtracking(int[] set, int target, int[] x, int index, boolean include, Vertex parent){
        int sum = sum(set,x,index);
        if(sum == target)
            parent = calculatePos(index, include, sum, parent, true, x);
        else
            parent = calculatePos(index, include, sum, parent, false, x);
        draw();
        step++;
        
        if(index==set.length || sum == target){
            if(sum == target){
                try{
                    FileWriter fw = new FileWriter(filePath);
                    BufferedWriter bfw = new BufferedWriter(fw);
                    for(int i : set)
                    bfw.write(i + " ");
                    bfw.newLine();
                    bfw.write(target+"");
                    bfw.newLine();
                    for(int i : x)
                        bfw.write(i + " ");
                    bfw.close();
                    
                }catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                
                if(sum == target){
                     for(int i=0;i<x.length;++i)
                         System.out.print(x[i] + " ");
                     
                     for(int i=0;i<set.length-x.length;++i)
                            System.out.print(0 + " ");
                     System.out.println("");
                 }
            }
        }else{
            if(sum+set[index] <= target){
                x[index] = 1; 
                backtracking(set, target, x, index+1, true, parent);
            }
            x[index] = 0;
            backtracking(set, target, x, index+1, false, parent);
            
        }
    }
    
    int sum(int[] d, int[] x,int m){
        int sum =0;
        for(int i=0;i<m;++i){
            if(x[i] == 1)
                sum+= d[i];
        }
        return sum;
    }
    
    Vertex calculatePos(int index, boolean include,int sum , Vertex parent, boolean isAnswer, int xs[]){
        int x= 0,y=0;
        if(index == 0){
            x = xSize;
            y = 50;
        }else{
            if(include){
                x = parent.x - (xSize/(int)Math.pow(2, index));
                y = parent.y + 120;
            }else{
                 x = parent.x + ((xSize/(int)Math.pow(2, index)));
                y = parent.y + 120;
            }
        }
        
        vs.add(new Vertex(x, y, sum+"" , parent, isAnswer, xs));
        if(index != 0)
            es.add(new Edge_(vs.get(vs.size()-1), parent, include));
        return vs.get(vs.size()-1);
    }
    
    BufferedImage grid = null;
    
    public void draw() {
        

        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setFont(sanSerifFont);
        
        if(grid==null){
            grid = (BufferedImage)createImage(c.getWidth(),c.getHeight());
        }
        
        Graphics2D g2 = grid.createGraphics();
        
        g2.setColor(Color.white);
        g2.fillRect(0, 0, (xSize+30)*2, ySize+100);
        
        for (Edge_ t : es) {
            t.draw(g2);
        }
        
        /*if (TempEdge != null) {
            TempEdge.line(g2);
        }*/
        for (Vertex s : vs) {
            s.draw(g2);
        }


       // clear();
        
        g.drawImage(grid, null, 0, 0);
        //drawadrawImagell
        if(saveImage){
            try{

                File outputfile = new File(algoName+"_step_"+step+".png");
                ImageIO.write(grid, "png", outputfile);
            }catch(IOException e){
                 e.printStackTrace();
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(algoIndex == 0)
            backtracking(set, target, x, 0, true, null);
        else
            branchAndBound(set, target);
        
        if(step > 0)
            saveImage = false;
    }
     @Override
    public void mouseDragged(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
     @Override
    public void mousePressed(MouseEvent e) {}
    
     @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    private SubsetSum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
