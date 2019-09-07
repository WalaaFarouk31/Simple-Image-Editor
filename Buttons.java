package D_Task;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Buttons {

	private App_Frame myframe;
	private ImagePanel Image_Panel;
	private JButton UploadImage;
	private JButton DownloadImage;
	private JButton crclSelect;
	private JButton rectSelect;
	private JButton lighten;
	private JButton darken;
	private JButton invert;
	private JButton undo;
	private JButton redo;
	private JButton blur;
	
	
	final static float dash1[] = { 10.0f };
	final static BasicStroke dashed = new BasicStroke(1.0f,
		      BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	
	public Buttons(App_Frame frame,ImagePanel Image_Panel){
		this.myframe=frame;
		this.Image_Panel=Image_Panel;
		addButtons();
	}
	
   private void addButtons(){
	   Upbutton();
	   redoButton();
	   download();
	   Rselect();
	   Cselect();
	   Lighten();
	   Darker();
	   Blur();
	   Invert();
	   Undo(); 
	}
   
	
   private void Upbutton(){
	   UploadImage = new JButton(new ImageIcon(myframe.Resources_Path+"Up_Image.png"));
	    UploadImage.setBounds(myframe.swidth/4,myframe.sheight/10,48,48);
	    //UploadImage.setBorder(new RoundedBorder(10));
	    UploadImage.setBackground(Color.DARK_GRAY); 
	    UploadImage.setBorderPainted(false);
	    UploadImage.setToolTipText("Upload image");
	    myframe.add(UploadImage);
	    
	    
	    UploadImage.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        
	        	
	        
	    	    
	          JFileChooser file = new JFileChooser();
	          file.setDialogTitle("Choose an image");
	          file.setCurrentDirectory(new File(System.getProperty("user.home")));
	          //filter the files
	          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
	          file.addChoosableFileFilter(filter);
	          int result = file.showSaveDialog(null);
	           //if the user click on save in Jfilechooser
	          if(result == JFileChooser.APPROVE_OPTION){
	              File selectedFile = file.getSelectedFile();
	              String path = selectedFile.getAbsolutePath();
	              
	              try {
	            	  if(Image_Panel.current!=-1){ 
	      	        	Image_Panel.AllImages.removeAll(Image_Panel.AllImages);
	      	        	Image_Panel.current=-1;
	      	        	}
	            	  Image_Panel.label.setIcon(new ImageIcon(Image_Panel.ResizeImage(path)));
				} catch (IOException e1) {
				
					e1.printStackTrace();
				}
	          }
	           //if the user click on save in Jfilechooser

	          else if(result == JFileChooser.CANCEL_OPTION){
	              System.out.println("No File Select");
	          }
	        }
	        
	    });
   }
   
   private void download(){
	   DownloadImage = new JButton(new ImageIcon(myframe.Resources_Path+"Down_Image.png"));
	    DownloadImage.setBounds((myframe.swidth/4)+80,myframe.sheight/10,48,48);
	    DownloadImage.setBackground(Color.DARK_GRAY); 
	    DownloadImage.setBorderPainted(false);
	    DownloadImage.setToolTipText("Download image");
	    myframe.add(DownloadImage);
	    
	    
	    DownloadImage.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        
	        	JFileChooser fileChooser = new JFileChooser();
	        	fileChooser.setDialogTitle("Specify a file to save"); 
	        	int userSelection = fileChooser.showSaveDialog(null);
	        	
	        	if (userSelection == JFileChooser.APPROVE_OPTION) {
	        	    File fileToSave = fileChooser.getSelectedFile();
	        	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
	        	    
	        	    //resize the image back to it's original size
	        	    Image resized =  Image_Panel.AllImages.get(Image_Panel.current)
	        	    .getScaledInstance(Image_Panel.imgw,Image_Panel.imgh, Image.SCALE_SMOOTH);
	            	BufferedImage edited = new BufferedImage(Image_Panel.imgw,Image_Panel.imgh, Image.SCALE_REPLICATE);
	            	edited.getGraphics().drawImage(resized, 0, 0 , null);
	        	    
	        	    File outputfile = new File(fileToSave.getAbsolutePath()+".jpg");
	        	    try {
						ImageIO.write(edited,"jpg", outputfile);
					} catch (IOException e1) {
					
						e1.printStackTrace();
					}
	        	}
	         }
	        }
	    });
	    
   }
   
   private void Rselect(){
	    rectSelect = new JButton(new ImageIcon(myframe.Resources_Path+"Rectangle.png"));
	    rectSelect.setBounds((myframe.swidth/4)+160,myframe.sheight/10,48,48);
	    //rectSelect.setBorder(new RoundedBorder(1));
	    rectSelect.setBackground(Color.DARK_GRAY);  
	    rectSelect.setBorderPainted(false);
	    rectSelect.setToolTipText("Select");
	    myframe.add(rectSelect);
	    
	    
	    rectSelect.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {  
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        	Image_Panel.shape=1;
	        	Image_Panel.selected=true;
	            Image_Panel.SelectsubImage();
	        	}
	            
	            }
	      });
   }
   
   private void Cselect(){
	   
	   crclSelect = new JButton(new ImageIcon(myframe.Resources_Path+"Circle.png"));
	    crclSelect.setBounds((myframe.swidth/4)+240,myframe.sheight/10,48,48);
	    //crclSelect.setBorder(new RoundedBorder(1));
	    crclSelect.setBackground(Color.DARK_GRAY);  
	    crclSelect.setBorderPainted(false);
	    crclSelect.setToolTipText("Select");
	    myframe.add(crclSelect);
	    
	    
	    crclSelect.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { 
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        	Image_Panel.shape=2;
	        	Image_Panel.selected=true;
	        	Image_Panel.SelectsubImage();
	        	}
	        	
	            }
	      });
   }
   
   private void redoButton(){
		
		redo = new JButton(new ImageIcon(myframe.Resources_Path+"redo.png"));
	    redo.setBounds((myframe.swidth/4)+720,myframe.sheight/10,48,48);
	    //crclSelect.setBorder(new RoundedBorder(1));
	    redo.setBackground(Color.DARK_GRAY);
	    //redo.setContentAreaFilled(false);
	    redo.setBorderPainted(false);
	    redo.setToolTipText("Redo");
	    myframe.add(redo);  
		
	    
	    redo.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        	int size=Image_Panel.AllImages.size()-1;
	        	if(Image_Panel.current<size){
	        		Image_Panel.current++;
	        		Image_Panel.label.setIcon(new ImageIcon(Image_Panel.AllImages.get(Image_Panel.current)));
	        		myframe.repaint();
	              }
	        	}
	        	}
	      });
	}
   
   private void Lighten(){
	   
	   lighten = new JButton(new ImageIcon(myframe.Resources_Path+"Lighten.png"));
	    lighten.setBounds((myframe.swidth/4)+320,myframe.sheight/10,48,48);
	    lighten.setBackground(Color.DARK_GRAY);  
	    lighten.setBorderPainted(false);
	    lighten.setToolTipText("Shine");
	    myframe.add(lighten);
	    
	    
	    
	    lighten.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { 
	        	
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        	float scaleFactor = 1.3f;
	        	RescaleOp op = new RescaleOp(scaleFactor, 0, null);
	        	BufferedImage  BImage;
	        	int w = Image_Panel.AllImages.get(Image_Panel.current).getWidth();    
		        int h = Image_Panel.original.getHeight();
		        
		        if(Image_Panel.current+1!=Image_Panel.AllImages.size()) Image_Panel.removeLastImages();
	        	
	        	if(Image_Panel.selected==false){
	            
	            BImage = op.filter(Image_Panel.AllImages.get(Image_Panel.current), null);
	            Image_Panel.AllImages.add(BImage); 
	        	}
	        	else if(Image_Panel.shape==1){
	        	
                   BufferedImage subImage=new BufferedImage(Image_Panel.recW, Image_Panel.recH,Image_Panel.AllImages.get(Image_Panel.current).getType());
	    	    	
                   BImage= new BufferedImage(w, h, Image_Panel.AllImages.get(Image_Panel.current).getType());
	    	    	Graphics2D graphic = BImage.createGraphics();
	    	    	op.filter(Image_Panel.AllImages.get(Image_Panel.current).
	    	    			getSubimage(Image_Panel.recX,Image_Panel.recY,
		            				Image_Panel.recW,Image_Panel.recH), subImage);
	    	    	
	    	    	graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
	    	    	graphic.drawImage(subImage,null,Image_Panel.recX,Image_Panel.recY);	
	    	    	Image_Panel.AllImages.add(BImage);
	    	    	Image_Panel.selected=false;
	    	    	Image_Panel.shape=0;
	        	}
	        	else if(Image_Panel.shape==2){
	        	
	        		BufferedImage subImage=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	        		BufferedImage CicularImage=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	        		
	        		BImage= new BufferedImage(w, h, Image_Panel.AllImages.get(Image_Panel.current).getType());
		    	    Graphics2D graphic = BImage.createGraphics();
		    	    Graphics2D g2=CicularImage.createGraphics();
		    	
		    	    	op.filter(Image_Panel.AllImages.get(Image_Panel.current).
		    	    			getSubimage(Image_Panel.cX,Image_Panel.cY,
			            				Image_Panel.cW,Image_Panel.cH), subImage);
		    	    	
		    	    	g2.drawImage(Image_Panel.AllImages.get(Image_Panel.current).
		    	    			getSubimage(Image_Panel.cX,Image_Panel.cY,
			            				Image_Panel.cW,Image_Panel.cH), null, 0, 0);
		    	    	g2.setClip(new Ellipse2D.Float(0,0,Image_Panel.cW,Image_Panel.cH));
		    	    	g2.drawImage(subImage,0,0, Image_Panel.cW, Image_Panel.cH, null);
		    	    	graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
		    	    	graphic.drawImage(CicularImage,null,Image_Panel.cX,Image_Panel.cY);
		    	    	
		    	    	Image_Panel.AllImages.add(BImage);
		    	    	Image_Panel.selected=false;
		    	    	Image_Panel.shape=0;
	        		
	        	}
	          
	            Image_Panel.current++;
	            Image_Panel.label.setIcon(new ImageIcon(Image_Panel.AllImages.get(Image_Panel.current)));
		         myframe.repaint();
	        	}
	        }
	      });
	    
	   
   }
   
   private void Darker(){
	   darken = new JButton(new ImageIcon(myframe.Resources_Path+"Darken.png"));
	    darken.setBounds((myframe.swidth/4)+400,myframe.sheight/10,48,48);
	    //darken.setBorder(new RoundedBorder(1));
	    darken.setBackground(Color.DARK_GRAY);  
	    darken.setBorderPainted(false);
	    darken.setToolTipText("Darker");
	    myframe.add(darken);  
	    
	    
	    
	    darken.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { 
	        	
	        	
	        	if(Image_Panel.current==-1)
	        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        	 RescaleOp op = new RescaleOp(.9f, 0, null);
	        	 BufferedImage Darker; 
	        	 int w = Image_Panel.AllImages.get(Image_Panel.current).getWidth();    
			     int h = Image_Panel.original.getHeight();
			     
			    
			     if(Image_Panel.current+1!=Image_Panel.AllImages.size()) Image_Panel.removeLastImages();
			     
	        	 if(Image_Panel.selected==false){   
	        		 Darker = op.filter(Image_Panel.AllImages.get(Image_Panel.current), null);
	        		 Image_Panel.AllImages.add(Darker);
	 	        	}
	 	        	else if(Image_Panel.shape==1){
	                    BufferedImage subImage=new BufferedImage(Image_Panel.recW, Image_Panel.recH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	 	    	    	
	                    Darker= new BufferedImage(w, h, Image_Panel.AllImages.get(Image_Panel.current).getType());
	 	    	    	Graphics2D graphic = Darker.createGraphics();
	 	    	    	op.filter(Image_Panel.AllImages.get(Image_Panel.current).
	 	    	    			getSubimage(Image_Panel.recX,Image_Panel.recY,
	 		            				Image_Panel.recW,Image_Panel.recH), subImage);
	 	    	    	
	 	    	    	graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
	 	    	    	graphic.drawImage(subImage,null,Image_Panel.recX,Image_Panel.recY);
	 	    	    	
	 	    	    	Image_Panel.AllImages.add(Darker);
	 	    	    	Image_Panel.selected=false;
	 	    	    	Image_Panel.shape=0;
	 	        	}
	 	        	else if(Image_Panel.shape==2){
	 	        		
	 	        		BufferedImage subImage=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
		        		BufferedImage CicularImage=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
		        		
		        		Darker= new BufferedImage(w, h, Image_Panel.AllImages.get(Image_Panel.current).getType());
			    	    Graphics2D graphic = Darker.createGraphics();
			    	    Graphics2D g2=CicularImage.createGraphics();
			    	
			    	    	op.filter(Image_Panel.AllImages.get(Image_Panel.current).
			    	    			getSubimage(Image_Panel.cX,Image_Panel.cY,
				            				Image_Panel.cW,Image_Panel.cH), subImage);
			    	    	
			    	    	g2.drawImage(Image_Panel.AllImages.get(Image_Panel.current).
			    	    			getSubimage(Image_Panel.cX,Image_Panel.cY,
				            				Image_Panel.cW,Image_Panel.cH), null, 0, 0);
			    	    	g2.setClip(new Ellipse2D.Float(0,0,Image_Panel.cW,Image_Panel.cH));
			    	    	g2.drawImage(subImage,0,0, Image_Panel.cW, Image_Panel.cH, null);
			    	    	graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
			    	    	graphic.drawImage(CicularImage,null,Image_Panel.cX,Image_Panel.cY);
			    	    	
			    	    	Image_Panel.AllImages.add(Darker);
			    	    	Image_Panel.selected=false;
			    	    	Image_Panel.shape=0;
	 	        		
	 	        	}
	        	 Image_Panel.current++;
	        	 Image_Panel.label.setIcon(new ImageIcon(Image_Panel.AllImages.get(Image_Panel.current)));
	        	 Image_Panel.repaint();
	        	
	        	}
	        }
	      });	
   }
   
   private void Invert(){
	   
	   invert = new JButton(new ImageIcon(myframe.Resources_Path+"Invert.png"));
	    invert.setBounds((myframe.swidth/4)+480,myframe.sheight/10,48,48);
	    invert.setBackground(Color.DARK_GRAY);  
	    invert.setBorderPainted(false);
	    invert.setToolTipText("Invert");
	    myframe.add(invert);
	    
	    
    	
	    
	    invert.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { 
	        	
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        	int w = Image_Panel.AllImages.get(Image_Panel.current).getWidth();    
	            int h = Image_Panel.original.getHeight();
	        
	            BufferedImage rotated = new BufferedImage(w, h, Image_Panel.AllImages.get(Image_Panel.current).getType());  
	            Graphics2D graphic = rotated.createGraphics();
	            
	            if(Image_Panel.current+1!=Image_Panel.AllImages.size()) Image_Panel.removeLastImages();
	            
	            if(Image_Panel.selected==false){
	            	 graphic.rotate(Math.toRadians(180), w/2,h/2);
	            	 graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
	            }
	            else if(Image_Panel.shape==1){
	            graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
	            graphic.rotate(Math.toRadians(180), Image_Panel.recW/2,Image_Panel.recH/2);
	            graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current).
	            		getSubimage(Image_Panel.recX,Image_Panel.recY,
	            				Image_Panel.recW,Image_Panel.recH),
	            		        null,-Image_Panel.recX,-Image_Panel.recY);
	            Image_Panel.selected=false;
	            Image_Panel.shape=0;
	            }
	            else if(Image_Panel.shape==2){
	            	
	            	BufferedImage subImage=Image_Panel.AllImages.get(Image_Panel.current).getSubimage(Image_Panel.cX,Image_Panel.cY,
            				Image_Panel.cW,Image_Panel.cH);
	            	BufferedImage temp=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	        		BufferedImage CicularImage=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	        		
	        		
		    	    Graphics2D g2=CicularImage.createGraphics(); 
		    	    Graphics2D g=temp.createGraphics();
		    	    	
		    	    	
		    	        g2.drawImage(subImage, null, 0, 0);  //the original subimage
		    	        g.rotate(Math.toRadians(180), Image_Panel.cW/2,Image_Panel.cH/2);
		    	    	g.drawImage(subImage,null,0,0);   //rotated subimage
		    	    	
		    	    	
		    	    	g2.setClip(new Ellipse2D.Float(0,0,Image_Panel.cW,Image_Panel.cH));
		    	    	g2.drawImage(temp,0,0, Image_Panel.cW, Image_Panel.cH, null);  //clipped rotated subimage
		    	    	
		    	    	graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
		    	    	graphic.drawImage(CicularImage,null,Image_Panel.cX,Image_Panel.cY);
		    	    	Image_Panel.selected=false;
		    	    	Image_Panel.shape=0;
		    	   
	            }
	         
	            
	           Image_Panel.AllImages.add(rotated);
	            Image_Panel.current++;
	            Image_Panel.label.setIcon(new ImageIcon(Image_Panel.AllImages.get(Image_Panel.current)));
	         
	        	}
	        }
	      });	
   }
   
   private void Blur(){
	   blur = new JButton(new ImageIcon(myframe.Resources_Path+"Blur.png"));
	    blur.setBounds((myframe.swidth/4)+560,myframe.sheight/10,48,48);
	    //crclSelect.setBorder(new RoundedBorder(1));
	    blur.setBackground(Color.DARK_GRAY);
	    //redo.setContentAreaFilled(false);
	    blur.setBorderPainted(false);
	    blur.setToolTipText("blur");
	    myframe.add(blur);
	   
	    
	    blur.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { 
	        	 // A 3x3 kernel that blurs an image
	        	
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        	if(Image_Panel.current+1!=Image_Panel.AllImages.size()) Image_Panel.removeLastImages();
	    	    
	    	    Kernel kernel = new Kernel(3, 3,  
	    	    new float[] { 1f/9f, 1f/9f, 1f/9f,1f/9f, 1f/9f, 1f/9f,1f/9f, 1f/9f, 1f/9f});
	    	    int w = Image_Panel.AllImages.get(Image_Panel.current).getWidth();    
	            int h = Image_Panel.original.getHeight();
	    	   
	    	    BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,null); 
	    	    BufferedImage blurred;  
	    	    
	    	    if(Image_Panel.selected==false){
	    	    blurred = op.filter(Image_Panel.AllImages.get(Image_Panel.current), null); 
	    	    Image_Panel.AllImages.add(blurred);
	    	    }
	    	    else if(Image_Panel.shape==1){
	    	    	
	    	    	BufferedImage subImage=new BufferedImage(Image_Panel.recW, Image_Panel.recH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	    	    	
	    	    	
	    	    	blurred= new BufferedImage(w, h, Image_Panel.AllImages.get(Image_Panel.current).getType());
	    	    	Graphics2D graphic = blurred.createGraphics();
	    	    	
	    	    	
	    	    	op.filter(Image_Panel.AllImages.get(Image_Panel.current).
	    	    			getSubimage(Image_Panel.recX,Image_Panel.recY,
		            				Image_Panel.recW,Image_Panel.recH), subImage);
	    	    	graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
	    	    	graphic.drawImage(subImage,null,Image_Panel.recX,Image_Panel.recY);
	    	    	
	    	    	Image_Panel.AllImages.add(blurred);  
	    	    	Image_Panel.selected=false;
	    	    }
	    	    else if(Image_Panel.shape==2){
	    	    	BufferedImage subImage=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	        		BufferedImage CicularImage=new BufferedImage(Image_Panel.cW, Image_Panel.cH, Image_Panel.AllImages.get(Image_Panel.current).getType());
	        		
	        		blurred= new BufferedImage(w, h, Image_Panel.AllImages.get(Image_Panel.current).getType());
		    	    Graphics2D graphic = blurred.createGraphics();
		    	    Graphics2D g2=CicularImage.createGraphics();
		    	
		    	    	op.filter(Image_Panel.AllImages.get(Image_Panel.current).
		    	    			getSubimage(Image_Panel.cX,Image_Panel.cY,
			            				Image_Panel.cW,Image_Panel.cH), subImage);
		    	    	
		    	    	g2.drawImage(Image_Panel.AllImages.get(Image_Panel.current).
		    	    			getSubimage(Image_Panel.cX,Image_Panel.cY,
			            				Image_Panel.cW,Image_Panel.cH), null, 0, 0);
		    	    	g2.setClip(new Ellipse2D.Float(0,0,Image_Panel.cW,Image_Panel.cH));
		    	    	g2.drawImage(subImage,0,0, Image_Panel.cW, Image_Panel.cH, null);
		    	    	graphic.drawImage(Image_Panel.AllImages.get(Image_Panel.current), null, 0, 0);
		    	    	graphic.drawImage(CicularImage,null,Image_Panel.cX,Image_Panel.cY);
		    	    	
		    	    	Image_Panel.AllImages.add(blurred);
		    	    	Image_Panel.selected=false;
		    	    	Image_Panel.shape=0;

	    	    }
	    	   
	    	    Image_Panel.current++;
	    	    Image_Panel.label.setIcon(new ImageIcon(Image_Panel.AllImages.get(Image_Panel.current)));
	    	    
	        
	              }    
	        }
	      });
   }
   
   private void Undo(){
	   undo = new JButton(new ImageIcon(myframe.Resources_Path+"Undo.png"));
	    undo.setBounds((myframe.swidth/4)+640,myframe.sheight/10,48,48);
	    undo.setBackground(Color.DARK_GRAY);  
	    undo.setBorderPainted(false);
	    undo.setToolTipText("Undo");
	    myframe.add(undo);
	    
	    undo.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if(Image_Panel.current==-1)
        			JOptionPane.showMessageDialog(null, "Upload an Image please!");
	        	else{
	        
	        	if(Image_Panel.current>0){
	        		Image_Panel.current--;
	        		Image_Panel.label.setIcon(new ImageIcon(Image_Panel.AllImages.get(Image_Panel.current)));
	        		Image_Panel.repaint();
	              }
	        	}
	        }
	      });
	    
   }
}