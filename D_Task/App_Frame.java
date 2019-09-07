package D_Task;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class App_Frame extends JFrame {
	
	String Resources_Path="F:\\Resources\\";  //Edit
	
	public static void main(String[] args){
        new App_Frame();
    }
	
	 
	private Buttons FrameButtons;
    ImagePanel myImage;
    int sheight;
    int swidth;
     
    public App_Frame(){
    	
    super("Simple Image Editor");  
   // get the screen size as a java dimension
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    sheight = screenSize.height;
    swidth = screenSize.width;
    
    Initialize();
    
    }
    
    private void Initialize(){
        
        myImage=new ImagePanel(this);
    	FrameButtons=new Buttons(this,myImage);  //Add the buttons 
    	
    	
    	 //*****************Specifications for the frame******************************
        setLayout(null);
        getContentPane().setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1200,700);
        setLocationRelativeTo(null);
        setVisible(true);
     
    }


	

}
