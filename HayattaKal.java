
package hayattakal;


import java.awt.HeadlessException;
import javax.swing.JFrame;



public class HayattaKal extends JFrame {

    public HayattaKal(String title) throws HeadlessException {
        super(title);
    }
 
    
    public static void main(String[] args) {
       
        HayattaKal hayattaKal=new HayattaKal("Hayatta Kal");
        Oyun oyun  = new Oyun();
        hayattaKal.setFocusable(false);
        oyun.requestFocus();
        oyun.addKeyListener(oyun);
        oyun.setFocusable(true);
        oyun.setFocusTraversalKeysEnabled(false);
        hayattaKal.add(oyun);
        hayattaKal.setSize(500,500);
        hayattaKal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hayattaKal.setVisible(true);
        
    }
    
}
