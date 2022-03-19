import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame{
    public static void main(String[] args) {
        MainFrame main = new MainFrame();
    }
    
    JPanel panel = new JPanel();
    JButton importBtn = new JButton();
    JTextField pathTXT = new JTextField();
    JButton startBtn = new JButton();
    JFileChooser chooser = new JFileChooser();
    JComboBox chooseAlgo = new JComboBox();
    JCheckBox saveImage = new JCheckBox();
    
    String[] set;
    int target;
    
    MainFrame(){
        super("Subset Sum Main");
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        

        
        pathTXT.setBounds(20, 20, 280, 25);
        pathTXT.setEditable(false);
        getContentPane().add(pathTXT);
        
        importBtn.setText("Import file...");
        importBtn.setBounds(310, 20, 100, 25);
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFile();
            }
        });
        getContentPane().add(importBtn);
        
        chooseAlgo.addItem("Backtracking");
        chooseAlgo.addItem("Branch and Bound");
        chooseAlgo.setBounds(20, 55, 390, 25);
        getContentPane().add(chooseAlgo);
        
        saveImage.setText("Save image for each step");
        saveImage.setBounds(20, 90, 390, 25);
        getContentPane().add(saveImage);
        
        startBtn.setText("Find sum of subset");
        startBtn.setBounds(20, 120, 390, 25);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFile();
            }
        });
        getContentPane().add(startBtn);
        
        setBounds(0, 0, 450, 205);
        panel.setBounds(0, 0, 450, 205);
        
        add(panel);
        show();
    }
    
    public void importFile(){
       chooser.setSelectedFile(new File(""));
       chooser.setAcceptAllFileFilterUsed(false); 
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File (.txt)", "txt");
       chooser.addChoosableFileFilter(filter);
       chooser.showOpenDialog(null);
       String f = null;
       if(chooser.getSelectedFile() != null){
           f = chooser.getSelectedFile().toString();
       }
        pathTXT.setText(f);
    }
    
    public void readFile(){
        try{
            if(pathTXT.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Plase select text file.", "Error!", JOptionPane.ERROR_MESSAGE);
            }else{
                File file = new File(pathTXT.getText());
                Scanner reader = new Scanner(file);
                int line = 1;
                while(reader.hasNextLine()){
                    String str = reader.nextLine();
                    if(line == 1)
                        set = str.split(" ");
                    else if(line == 2)
                        target = Integer.parseInt(str);
                    line++;
                }
                reader.close();
            }
        }catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        if(!pathTXT.getText().isEmpty()){
            for(int i=0;i<set.length;++i)
                System.out.print(set[i] + " ");
            System.out.println("");
            System.out.println(target);
            SubsetSum gui = new SubsetSum(set, target, chooseAlgo.getSelectedIndex(), saveImage.isSelected(), pathTXT.getText(), chooseAlgo.getSelectedItem().toString());
        }
    }
}
