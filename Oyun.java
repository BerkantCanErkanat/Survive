
package hayattakal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


class Meteor{
    private int x;
    private int y;

    public Meteor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
//alttan da meteor uret
public class Oyun extends JPanel implements KeyListener,ActionListener{
    private int sure=0;
    private int bizX=250;
    private int bizY=250;
    ArrayList<Meteor> meteorlar = new ArrayList<>();
    Timer timer = new Timer(10,this);
    Random rnd = new Random();
    Thread tr;
    private Object lock1 = new Object();
    Thread tr2;
    //kasma sorunu nasıl cozulcek
    public Oyun() {
        setBackground(Color.BLACK);
        //add(label);
        tr = new Thread(new Runnable() {

            @Override
            public void run() {
                uretust();
            }
        });
        tr2=new Thread(new Runnable() {

            @Override
            public void run() {
                sil();
            }
        });
        //tr2.start();
        tr.start();
        timer.start();
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c =e.getKeyCode();
        
        if(c==KeyEvent.VK_LEFT){
            if(bizX>10)
            bizX-=5;
            else
            bizX=10;
            }
       else  if(c==KeyEvent.VK_RIGHT){
            if(bizX<440)
            bizX+=5;
            else
            bizX=440;
        }
      else   if(c==KeyEvent.VK_UP){
            if(bizY>10)
            bizY-=5;
            else
            bizY=10;
        }
       else if(c==KeyEvent.VK_DOWN){
            if(bizY<440)
            bizY+=5;
            else
            bizY=440;     
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void uretust(){
         while(true){
             synchronized(lock1){
             try {
                 Thread.sleep(200);
                 int y =rnd.nextInt(10);
                 y = -y;
                 meteorlar.add(new Meteor(rnd.nextInt(600)-200,y)); 
             } catch (InterruptedException ex) {
                 System.out.println("sleep");
             }
         }
        }
    }
    public void sil(){
        while(true){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        synchronized(lock1){
            for(Meteor met:meteorlar){
                if(met.getX()>460)meteorlar.remove(met);
            }
        }
        }
    }
    
 
    @Override
    public void actionPerformed(ActionEvent e) {
        sure+=10;
       // System.out.println(meteorlar.size());
        for(Meteor met:meteorlar){
            met.setX(met.getX()+1);
            met.setY(met.getY()+1);
        }
       
         //label.setText("Gecen Sure: "+sure/1000);
        repaint();
        
    }
   public void kontrolet(){
       for(Meteor met:meteorlar){
           Rectangle rect = new Rectangle(met.getX(),met.getY(),8,8);
          if(rect.intersects(new Rectangle(bizX,bizY,10,10))){
              timer.stop();
              JOptionPane.showMessageDialog(this,"hayatta kalınan sure :"+sure/1000.0+" sn\nGAME OVER");
              System.exit(0);
          }
       }
   }
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.WHITE);
           kontrolet();
            for(Meteor met : meteorlar){
            if(met.getX()<490 || met.getY()<490){
                g.fillOval(met.getX(),met.getY(),10,10);
            }
        }
         
       g.setColor(Color.GREEN);
       g.fillOval(bizX,bizY,20,20);
            
    }
    @Override
    public void repaint(){
        super.repaint();
    }
    
    
    
    
    
}
