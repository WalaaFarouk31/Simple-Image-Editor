package D_Task;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class ImagePanel extends JPanel{
	
	BufferedImage original;
	JLabel label=new JLabel();
    ArrayList<BufferedImage> AllImages=new ArrayList<BufferedImage>(10);;
    private App_Frame myframe;
    Point startDrag, endDrag;
    final static float dash1[] = { 10.0f };
    final static BasicStroke dashed = new BasicStroke(1.0f,
        BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
    
    int recW=0;
    int recH=0;
    int recX=0;
    int recY=0;
    int current=-1;
    
    int shape=0;
    int cW=0;
    int cH=0;
    int cX=0;
    int cY=0;
    
    int imgh=0,imgw=0;
    boolean selected=false;
    
    public ImagePanel(App_Frame myframe){ 
    	 this.myframe=myframe;
    	startDrag=new Point(0,0);
    	endDrag=new Point(0,0);
      	 
    	//JPanel specifications! 
    	 setLocation(myframe.swidth/4,myframe.sheight/3);
    	 setBackground(Color.DARK_GRAY);
    	 setVisible(true); 
         setSize(600,300);
         //************************The label for showing the image*******************  
             
         label.setIcon(new ImageIcon(myframe.Resources_Path+"Welcome.png"));
         add(label);	
         myframe.add(this);
    }
    
    
    public void draw(Graphics g){
    	
    	  
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setColor(new Color(212, 212, 212));
    	 g2d.setStroke(dashed);
    	 
    	 if(shape==0){
    		 g2d.setColor(Color.DARK_GRAY);
    		 g2d.drawRect(100, 500, 0, 0);
         }
         else if(shape==1){
        	 
        	 recW=Math.abs(startDrag.x-endDrag.x);
        	 recH=Math.abs(startDrag.y-endDrag.y);
        	 recX=Math.min(startDrag.x, endDrag.x);
        	 recY=Math.min(startDrag.y,endDrag.y);   	 
        	 
         	
         	//g2d.drawRect(recX,recY,recW, recH);
         	
         // draw the rows
            int rowHt = recH / 3;
            for (int i = 0; i <= 3; i++)
              g2d.drawLine(recX,recY+(i * rowHt),recX+recW, recY+(i * rowHt));
           
            // draw the columns
            int rowWid = recW / 3;
            for (int i = 0; i <= 3; i++)
              g2d.drawLine(recX+(i * rowWid), recY, recX+(i * rowWid),recY+recH);     
         }
         else if(shape ==2){
        	 cW=Math.abs(startDrag.x-endDrag.x);
        	 cH=Math.abs(startDrag.y-endDrag.y);
        	 cX=Math.min(startDrag.x, endDrag.x);
        	 cY=Math.min(startDrag.y,endDrag.y);   
        	 
        	 g2d.drawOval(cX,cY,cW,cH);
        	 
         }
    	
    }
    
    // *********************Method to resize imageIcon with the same size of a Jlabel
    public BufferedImage ResizeImage(String ImagePath) throws IOException
    {   
    	BufferedImage im=ImageIO.read(new File(ImagePath));
    	imgh=im.getHeight();
    	imgw=im.getWidth();
    	Image resized =  im.getScaledInstance( this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
    	original = new BufferedImage(this.getWidth(), this.getHeight(), Image.SCALE_REPLICATE);
    	original.getGraphics().drawImage(resized, 0, 0 , null);
        
    	AllImages.add(original);
    	current++;
        return AllImages.get(current);
    }
    
    protected void SelectsubImage(){
 	   
    	MouseMotionListener mml= new MouseAdapter(){
 			 public void mouseDragged(MouseEvent e){
 				endDrag.x =Math.min(e.getX(),598);
 	         	 endDrag.y =Math.min(e.getY(),298);
 	        	   removeAll();
 	        	   add(label);
 	        	   draw(getGraphics());
 	        	   repaint();
 	        
 	           }
 			
 		};
 	   
 	   MouseListener ml = new MouseAdapter() {
 		   public void mousePressed(MouseEvent e) {
 			    
         	  startDrag.x =Math.min(e.getX(),598);
         	  startDrag.y =Math.min(e.getY(),298);
         	  endDrag.x =Math.min(e.getX(),598);
         	  endDrag.y =Math.min(e.getY(),298);
         	  removeAll();
         	  add(label);
         	  
                draw(getGraphics());
                repaint();
             
            }
 		  
            
            public void mouseReleased(MouseEvent e) {
              endDrag.x =Math.min(e.getX(),598);
           	  endDrag.y =Math.min(e.getY(),298);
         	   if(endDrag.x==startDrag.x && endDrag.x==startDrag.x){
         		removeAll();
   				add(label);
   				selected=false;
   				shape=0;		
   				repaint();
   				
         	   }
         	   else
                draw(getGraphics());
                
                
            }

 		};
 		
 		addMouseMotionListener(mml);
 		addMouseListener(ml);
 		
 		    
 	   	
    }
    
    protected void removeLastImages(){
    	for(int i=current+1;i<AllImages.size();i++){
    		AllImages.remove(i);
    		i--;
    	}
    }
}
