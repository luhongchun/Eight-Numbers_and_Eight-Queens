
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Astar extends JFrame{
    JPanel contentPane;
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JPanel jPanel1 = new JPanel();
    TitledBorder titledBorder1 = new TitledBorder("");
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JPanel jPanel2 = new JPanel();
    JButton jButton3 = new JButton();
    JCheckBox jCheckBox1 = new JCheckBox();
    JCheckBox jCheckBox2 = new JCheckBox();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    TitledBorder titledBorder2 = new TitledBorder("");
    JButton jButton4 = new JButton();
    JButton jButton5 = new JButton();
    JButton jButton6 = new JButton();
    JButton jButton7 = new JButton();
    JLabel jLabel1 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea jTextArea1 = new JTextArea();
    JTextField jTextField5 = new JTextField();
    JTextField jTextField10 = new JTextField();
    JTextField jTextField11 = new JTextField();
    JTextField jTextField12 = new JTextField();
    JRadioButton jRadioButton3 = new JRadioButton();
    JRadioButton jRadioButton4 = new JRadioButton();
    JButton jButton8 = new JButton();
    JButton jButton10 = new JButton();
    GridLayout gridLayout1 = new GridLayout(3,3);
    JButton [][]jButtonGroup_start = new JButton[3][3];
    JButton [][]jButtonGroup_end = new JButton[3][3];
    java.awt.GridLayout gridLayout2 = new GridLayout(3,1);
    JCheckBox jCheckBox3 = new JCheckBox();
    JLabel jLabel6 = new JLabel();
    JTextField jTextField2 = new JTextField();
    JLabel jLabel7 = new JLabel();
    JTextField jTextField4 = new JTextField();
    JButton jButton9 = new JButton();
    JButton jButton11 = new JButton();
    JButton jButton12 = new JButton();
    JButton jButton13 = new JButton();
    
    String Tips = new String("");
    
    
    
    javax.swing.Timer time;
    
    int Count1 = 0;
    int Count2 = 0;
    int i= 0;
    int j = 0;
    int current_step = 0;
    int current_result = 0;
    int dos_step = 0;
    int window_step = 0;
    String change_img = "Nomal";
    boolean window_flag = false;
    
    Node mynode,tonode,auto_node,return_node,next_node;
    Vector vc = new Vector(1,1);
    
    

    int[][] end = {{1,2,3},{4,5,6},{7,8,0}};
    int[][] start = {{1,2,3},{4,5,6},{7,8,0}};
    int[][] min = {{1,2,3},{4,5,6},{7,8,0}};

    int local_x = 0;
    int local_y = 0; 
    
    Vector open=new Vector(1,1);
	Vector close=new Vector(1,1);
	int pnodeId=0;
	Vector Result=new Vector(1,1);
    
    
    
    
    
    public Astar() {
       
 	    setDefaultCloseOperation(EXIT_ON_CLOSE);
 	    setResizable(false);
 	    titledBorder2 = new TitledBorder("个性化");
        titledBorder1 = new TitledBorder("状态设置");
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(850, 500));
        
        setTitle("启发式求解八数码程序演示");
        
        

        jPanel1.setBorder(titledBorder1);
        jPanel1.setBounds(new Rectangle(8, 16, 135, 284));
        jPanel1.setLayout(null);

        jPanel2.setBorder(BorderFactory.createEtchedBorder());
        jPanel2.setBounds(new Rectangle(18, 123, 95, 147));
        jPanel2.setLayout(null);
        jButton3.setBounds(new Rectangle(10, 15, 95, 26));
        jButton3.setText("随机生成");
        jCheckBox1.setText("起始状态");
        jCheckBox1.setBounds(new Rectangle(10, 61, 95, 23));
        jCheckBox2.setText("目标状态");
        jCheckBox2.setBounds(new Rectangle(10, 88, 95, 23));
        jCheckBox3.setText("避免无解");
        jCheckBox3.setBounds(new Rectangle(10, 118, 95, 23));
        jPanel3.setBorder(BorderFactory.createEtchedBorder());
        jPanel3.setBounds(new Rectangle(153, 52, 266, 238));
        jPanel3.setLayout(gridLayout1);
        jLabel6.setText("初始状态");
        jLabel6.setBounds(new Rectangle(154, 15, 64, 25));
        jTextField2.setEditable(false);
        jTextField2.setBounds(new Rectangle(241, 16, 174, 24));
        jLabel7.setText("目标状态");
        jLabel7.setBounds(new Rectangle(575, 14, 70, 26));
        jTextField4.setEditable(false);
        jTextField4.setBounds(new Rectangle(665, 16, 166, 23));
//        jButton9.setBounds(new Rectangle(28, 89, 95, 26));
//        jButton9.setText("导入布局");
        
        
        jTextField5.setEditable(false);
        jTextField10.setEditable(false);
        jTextField11.setEditable(false);
        jTextField12.setEditable(false);
        jButton6.setEnabled(false);
     	jButton7.setEnabled(false);
     	jTextArea1.setEnabled(false);
     	
     	 

            
            
            
        for(i=0;i<3;i++)
            for(j=0;j<3;j++)
            {
            	if(start[i][j]==0)
            	{
            		local_x = i;
            		local_y = j;
            	}
                Icon img = new ImageIcon("digits\\"+change_img+start[i][j]+".png");
                jButtonGroup_start[i][j] = new JButton(img);
                
                jPanel3.add(jButtonGroup_start[i][j]);
            }
        
        for(i=0;i<3;i++)
            for(j=0;j<3;j++)
            {
                Icon image = new ImageIcon("digits\\"+change_img+end[i][j]+".png");
                jButtonGroup_end[i][j] = new JButton(image);
                jPanel5.add(jButtonGroup_end[i][j]);
                //jButtonGroup_end[i][j].setBorder(BorderFactory.createEtchedBorder());  
            	jButtonGroup_end[i][j].setBackground(Color.magenta); 
            }
            
            
            
         	
        time=new javax.swing.Timer(700,new ActionListener()
    	      	{
    	      		public void actionPerformed(ActionEvent event)
    	      		{
    	      			   
    	      			
    	      				if(event.getSource()==time)
    		              	{	
    		              	  continue_1();	
    		              	  jTextField1.setText(" "+(window_step++));       	
    				          for(int i=0;i<3;i++)
                               for(int j=0;j<3;j++)
    					        {
    					          Icon im =new ImageIcon("digits\\"+change_img+start[i][j]+".png");
    					          jButtonGroup_start[i][j].setIcon(im);    					           					          
    					        }
    					   	    if(window_flag)
								{
									JOptionPane.showMessageDialog(null,"演示完毕");	
									window_flag =false;
									window_step = 0;
								}
    					        
 			             
    		           		}
    	      		}
    	      	});   
   
        
        
        jPanel4.setBorder(titledBorder2);
        jPanel4.setBounds(new Rectangle(433, 16, 130, 282));
        jPanel4.setLayout(null);
        
        jButton6.setBounds(new Rectangle(500, 80, 92, 32));
        jButton6.setText("恢复初始");
        
        jButton4.setBounds(new Rectangle(22, 126, 83, 26));
        jButton4.setText("格式1");
        jButton5.setBounds(new Rectangle(22, 165, 84, 25));
        jButton5.setText("格式2");
//        jButton11.setBounds(new Rectangle(22, 105, 83, 26));
//        jButton11.setText("ƴͼ1");
//        jButton12.setBounds(new Rectangle(22, 145, 83, 26));
//        jButton12.setText("ƴͼ2");
//        jButton13.setBounds(new Rectangle(22, 185, 83, 26));
//        jButton13.setText("ƴͼ3");
        
        
        
        
        jButton7.setBounds(new Rectangle(100, 80, 92, 32));
        jButton7.setText("自动演示");
        jLabel1.setText("当前步数");
        jLabel1.setBounds(new Rectangle(22, 211, 88, 24));
        jTextField1.setEditable(false);
        jTextField1.setText("0");
        jTextField1.setBounds(new Rectangle(23, 244, 81, 20));
        jPanel5.setBorder(BorderFactory.createEtchedBorder());
        jPanel5.setBounds(new Rectangle(573, 52, 258, 237));
        jPanel5.setLayout(gridLayout2);
        jPanel6.setBorder(BorderFactory.createEtchedBorder());
        jPanel6.setBounds(new Rectangle(8, 302, 822, 125));
        jPanel6.setLayout(null);
        jLabel2.setText("ִ执行步数");
        jLabel2.setBounds(new Rectangle(500, 33, 69, 26));

        jLabel5.setText("执行时间");
        jLabel5.setBounds(new Rectangle(200, 33, 66, 21));

        jTextField5.setBounds(new Rectangle(300, 33, 116, 22));
        jTextField10.setBounds(new Rectangle(600, 33, 116, 21));

        jRadioButton3.setText("uu1");
        jRadioButton3.setBounds(new Rectangle(602, 23, 103, 23));
        jRadioButton4.setText("ii2");
        jRadioButton4.setBounds(new Rectangle(602, 74, 103, 23));
        jButton8.setBounds(new Rectangle(300, 80, 92, 32));
        jButton8.setText("开始搜索");
        jButton10.setBounds(new Rectangle(700, 80, 92, 32));
        jButton10.setText("退出");
        jMenuBar1.add(jMenuFile);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        contentPane.add(jPanel1);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel1.add(jPanel2);
        jPanel2.add(jButton3);
        jPanel2.add(jCheckBox1);
        jPanel2.add(jCheckBox2);
        jPanel2.add(jCheckBox3);
        jPanel1.add(jButton9);
        contentPane.add(jPanel3);
        contentPane.add(jPanel4);
        contentPane.add(jPanel5);
        contentPane.add(jPanel6);
        jPanel6.add(jLabel5);
        jPanel6.add(jLabel2);
        jPanel6.add(jLabel4);
        jPanel6.add(jLabel3);
        jPanel6.add(jScrollPane1);
        jPanel6.add(jTextField5);
        jPanel6.add(jTextField10);
        jPanel6.add(jTextField12);
        jPanel6.add(jTextField11);
        jPanel6.add(jButton6);
        jPanel6.add(jButton7);
        jPanel6.add(jButton8);
        jPanel6.add(jButton10);
        jScrollPane1.getViewport().add(jTextArea1);
        jPanel4.add(jButton4);
        jPanel4.add(jButton5);
        jPanel4.add(jButton11);
        jPanel4.add(jButton12);
        jPanel4.add(jButton13);

        jPanel4.add(jLabel1);
        jPanel4.add(jTextField1);

        contentPane.add(jLabel6);
        contentPane.add(jLabel7);
        contentPane.add(jTextField2);
        contentPane.add(jTextField4);
        jButton8.setEnabled(false);
        jButton9.setEnabled(false);
        setJMenuBar(jMenuBar1);
        
        
        moveListener listener=new moveListener();
        jButton1.addActionListener(listener);
        jButton2.addActionListener(listener);
        jButton3.addActionListener(listener);
        jButton4.addActionListener(listener);
        jButton5.addActionListener(listener);
        jButton6.addActionListener(listener);
        jButton7.addActionListener(listener);
        jButton8.addActionListener(listener);
        jButton9.addActionListener(listener);
        jButton10.addActionListener(listener);
        jButton11.addActionListener(listener);
        jButton12.addActionListener(listener);
        jButton13.addActionListener(listener);
        jCheckBox1.addActionListener(listener);
        jCheckBox2.addActionListener(listener);
        jCheckBox3.addActionListener(listener);
        jMenuHelpAbout.addActionListener(listener);

        
        
        MyGroup button_listener = new MyGroup();
        for(int i=0;i<3;i++)
        {
        	for(int j=0;j<3;j++)
        	{
        	jButtonGroup_start[i][j].addActionListener(button_listener); 
        	//jButtonGroup_start[i][j].setBorder(BorderFactory.createEtchedBorder());  
        	jButtonGroup_start[i][j].setBackground(Color.cyan);   
        	}	
   		}
        
        
        
        setVisible(true);
    }
    
    
    
private  class  moveListener implements ActionListener
{
    public void actionPerformed(ActionEvent event)
    {
    	boolean flag =true;
    	Node return_mynode;
    	
    	
    	if(event.getSource()==jButton1)
    	{
    		int[][] end_hip  = {{1,2,3},{4,5,6},{7,8,0}};
    		end = end_hip;
    		for(i=0;i<3;i++)
            for(j=0;j<3;j++)
            {
                Icon imge =new ImageIcon("digits\\"+change_img+end[i][j]+".png");
    		    jButtonGroup_end[i][j].setIcon(imge);  		    
            }
    	}
    	
    	if(event.getSource()==jButton3&&jCheckBox1.isSelected())
    	{
    		jButton6.setEnabled(false);
     		jButton7.setEnabled(true);
    		current_step = 0;
    		jTextField1.setText("0");
	    	start_random();          		
    	}
    	
    	
    	if(event.getSource()==jButton3&&jCheckBox2.isSelected())
    	{  
    		jButton6.setEnabled(false);
     		jButton7.setEnabled(true); 	
    		current_step = 0;
    		jTextField1.setText("0");	
            end_random();
    	}
    	
    	if(jCheckBox3.isSelected()&&event.getSource()==jButton3)
    	{
    		current_step = 0;
    		jTextField1.setText("0");
	   	
    	   	 	if(jCheckBox1.isSelected())
    	        {
    	   	        while(!judge_arr(start,end))
    	        	{
    	   	          start_random(); 
    	   	        }
    	   	    	   	   	    	       
    	   	    }
    	   	    
    	   	   	if(jCheckBox2.isSelected())
    	        {
    	        	while(!judge_arr(start,end))
    	        	{
    	   	        	end_random(); 
    	   	        }	        
    	   	    }	     
    	   
    	}
    	
    	if(event.getSource()==jButton6)
    	{   		
    	  return_mynode = (Node)vc.get(vc.size()-1);
    	  min = return_mynode.arr;
    	  
    	  for(int i=0;i<3;i++)
    	  {
          	for(int j=0;j<3;j++)
		    {
		      Icon im =new ImageIcon("digits\\"+change_img+min[i][j]+".png");
		      jButtonGroup_start[i][j].setIcon(im); 
		        					           					          
		    }
		  }
		  start = return_mynode.arr; 
    	  
   		}
    	
    	if(event.getSource()==jButton7)
    	{
    		if(!Solve())
    		{
    			return;	
    		}
    		
    		System.out.println("d");
    		time.start();
    	
    	}
    	
    	if(event.getSource()==jButton8)
    	{
    		
    		Solve();
    		 
    		
    	}
    	
    	if(event.getSource()==jButton2)
    	{
    		jTextField2.setEditable(true);
    		jTextField4.setEditable(true);
    		jButton9.setEnabled(true);
    		JOptionPane.showMessageDialog(null,"232");
    	}
    	
    	if(event.getSource()==jButton9)
    	{
    		String start_string = jTextField2.getText();
    		String end_string = jTextField4.getText();
    		
    		if(start_string.equals("")||end_string.equals(""))
    		{
    			JOptionPane.showMessageDialog(null,"33");
    			return;
    		}
    		else if(start_string.length()!=9||end_string.length()!=9)
    		{
    			JOptionPane.showMessageDialog(null,"44");
    			return;
    		}
    		
    		Pattern pattern = Pattern.compile("[0-8]{9}",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(start_string);
            System.out.println(matcher.matches());
        
    		
    	    if(matcher.matches()== true)
    	    {
    	    	enter_number();
    	    	jButton9.setEnabled(false);
    	    	jTextField2.setEditable(false);
    			jTextField4.setEditable(false);
    	    }
    	    
    	    else
    	    {
    	    	JOptionPane.showConfirmDialog(null,"55");
    	    }
    			
    		
    		
    	}
    	
    	if(event.getSource()==jButton4)
    	{
    		change_img = "Nomal";
			repaint_face();
    		
    	}
    	
    	if(event.getSource()==jButton5)
    	{
    		change_img = "red";
			repaint_face();   		
    	}
    	
    	if(event.getSource()==jButton10)
    	{
    		System.exit(0);
    	}
    	
    	
    	if(event.getSource()==jButton11)
    	{
    		change_img = "pic_1_";
			repaint_face();
    	}
    	
    	if(event.getSource()==jButton12)
    	{
    		change_img = "pic_4_";
			repaint_face();
    	}
    	
    	if(event.getSource()==jButton13)
    	{
    		change_img = "pic_2_";
			repaint_face();
    	}
    	

    	
    	
    	if(jMenuHelpAbout.isArmed())
    	{
    		new About();
    		}
    	
    
    	
    	
    }
    
    
}




private  class  MyGroup implements ActionListener
{
    public void actionPerformed(ActionEvent event)
    {
    	int n;   	
    	    
        for(i=0;i<3;i++)
            for(j=0;j<3;j++)
            {
		    	if(start[i][j]==0)
		        {
		           local_x = i;
		           local_y = j;
		        }
		        
            }
         
        
    	if(local_x-1>=0)
        {
    	  	 if(event.getSource()==jButtonGroup_start[local_x-1][local_y])
    		  {
    			Icon ima = jButtonGroup_start[local_x][local_y].getIcon();
    			jButtonGroup_start[local_x][local_y].setIcon(jButtonGroup_start[local_x-1][local_y].getIcon());
    			jButtonGroup_start[local_x-1][local_y].setIcon(ima);
    			n=start[local_x][local_y];
    			start[local_x][local_y]=start[local_x-1][local_y];
    			start[local_x-1][local_y]=n;
    			local_x=local_x-1;
    			current_step++;
    	   	  }
    	   	  jTextField1.setText(""+current_step);
        	
        }
        
        if(local_x+1<=2)
        {
    	  	 if(event.getSource()==jButtonGroup_start[local_x+1][local_y])
    		  {

    			Icon ima = jButtonGroup_start[local_x][local_y].getIcon();
    			jButtonGroup_start[local_x][local_y].setIcon(jButtonGroup_start[local_x+1][local_y].getIcon());
    			jButtonGroup_start[local_x+1][local_y].setIcon(ima);
    			n=start[local_x][local_y];
    			start[local_x][local_y]=start[local_x+1][local_y];
    			start[local_x+1][local_y]=n;
    			local_x=local_x+1;
    			current_step++;
    	   	  }
        	jTextField1.setText(""+current_step);
        }
        
        if(local_y-1>=0)
        {
    	  	 if(event.getSource()==jButtonGroup_start[local_x][local_y-1])
    		  {
    			Icon ima = jButtonGroup_start[local_x][local_y].getIcon();
    			jButtonGroup_start[local_x][local_y].setIcon(jButtonGroup_start[local_x][local_y-1].getIcon());
    			jButtonGroup_start[local_x][local_y-1].setIcon(ima);
    			n=start[local_x][local_y];
    			start[local_x][local_y]=start[local_x][local_y-1];
    			start[local_x][local_y-1]=n;
    			local_y=local_y-1;
    			current_step++;
    	   	  }
        	jTextField1.setText(""+current_step);
        }
        
        if(local_y+1<=2)
        {
    	  	 if(event.getSource()==jButtonGroup_start[local_x][local_y+1])
    		  {
    		  	
    			Icon ima = jButtonGroup_start[local_x][local_y].getIcon();
    			jButtonGroup_start[local_x][local_y].setIcon(jButtonGroup_start[local_x][local_y+1].getIcon());
    			jButtonGroup_start[local_x][local_y+1].setIcon(ima);
    			n=start[local_x][local_y];
    			start[local_x][local_y]=start[local_x][local_y+1];
    			start[local_x][local_y+1]=n;
    			local_y=local_y+1;
    			current_step++;
    	   	  }
        	jTextField1.setText(""+current_step);
        	
        }
        judge_success();
    }
}


public void repaint_face()
{
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			Icon imge =new ImageIcon("digits\\"+change_img+start[i][j]+".png");
			jButtonGroup_start[i][j].setIcon(imge);  
			
			Icon img =new ImageIcon("digits\\"+change_img+end[i][j]+".png");
			jButtonGroup_end[i][j].setIcon(img); 		    
		}
	}
}



public void enter_number()
{
	String string = jTextField2.getText().trim();
	String end_string = jTextField4.getText().trim();
	int k = 0;
	
	
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				
				int new_num = Integer.parseInt(String.valueOf(string.charAt(k))); 
				int end_num = Integer.parseInt(String.valueOf(end_string.charAt(k))); 
				start[i][j] = (new_num);
				end[i][j] = end_num;
				k++;
			}
		}
	

    repaint_face();
	jButton8.setEnabled(true);
}



public void continue_1()
{
	String space = " ";
	
		
    	auto_node = (Node)vc.get((vc.size()-1)-current_result);
    	start = auto_node.arr;
    	current_result+=1;
	System.out.println("=================");
	System.out.println(dos_step);
	dos_step++;
	
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			if(start[i][j]!=0)
			{
				System.out.print(""+start[i][j]+" ");
			}
	        else
	     	{	     	
	     	    System.out.print(""+space+" ");
	     	} 
	     }
	     System.out.println();
	}
	
	if(current_result==vc.size())
	{
			time.stop();
			current_result = 0;
			dos_step = 0;
			window_flag =true;				
	}
}


public void judge_success()
{
	int flag = 0;
	for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
		    	if(start[i][j]==end[i][j])
		        {
		           flag+=1;
		        }
		        
            }
            if(flag==9)
            {
            	JOptionPane.showMessageDialog(null,"qqqq");
            }
	}


public void start_random()
{
			jButton8.setEnabled(true);	
	    	Random_Status rs = new Random_Status();
	    	start = rs.starts_status;

    		for(i=0;i<3;i++)
            for(j=0;j<3;j++)
            {
                Icon imge =new ImageIcon("digits\\"+change_img+start[i][j]+".png");
    		    jButtonGroup_start[i][j].setIcon(imge);  		    
            }
}

public void end_random()
{
			jButton8.setEnabled(true);
    		Random_Status rs = new Random_Status();
	    	end = rs.starts_status;
	    	
	    	for(i=0;i<3;i++)
            for(j=0;j<3;j++)
            {
                Icon imge =new ImageIcon("digits\\"+change_img+end[i][j]+".png");
    		    jButtonGroup_end[i][j].setIcon(imge);  		    
            }
}




public boolean Solve()
{

     	int step=0;

     	int source[][] = new int[3][3];
     	int dest[][] = new int [3][3]; 
     	
     	int i=0;
     	int j=0;
     	String space = " ";
     	
     	
	
	if(!judge_arr(start,end))
	{
		JOptionPane.showMessageDialog(null,"����ʼ״̬�޷�����Ŀ��״̬");
		jButton8.setEnabled(true);
		return false;
	}
	
	jButton8.setEnabled(false);
		
     	
     	

     	
     	long   start1   =   System.currentTimeMillis(); 
     	 
     	EightNumber en = new EightNumber();
     	
     	
	   
     	     	
     	vc = en.process(start,end);
     	
     	
     	long   end1   =   System.currentTimeMillis();  
     	
     
     	
     	for(int k=0;k<vc.size();k++)
     	{
     		System.out.println("=============");
     		System.out.println(step);
     		step++;
     	    mynode = (Node)vc.get((vc.size()-1)-k);

     		for(i=0;i<3;i++)
         	{
	       	
	     		for(j=0;j<3;j++)
	     		{
	     		  if(mynode.arr[i][j]!=0)
	     		  {
	     		  	System.out.print(""+mynode.arr[i][j]+" ");	     		  	
	     		  }
	     		  else
	     		  {	     	
	     	     	 System.out.print(""+space+" ");
	     	      } 
	     		}
	     		System.out.println();
            }
            
     		
     	}	
     		
     	
     	

     	
     	jButton6.setEnabled(true);
     	jButton7.setEnabled(true);


     	jTextField5.setText(" "+ (end1-start1)+ "");
     	jTextField10.setText(""+ (step-1)+ "");
     	jTextField11.setText(" "+en.close.size()+"");
     	jTextField12.setText(" "+en.open.size()+"");
	return true;
	
}
  
  public static boolean judge_arr(int start[][],int end[][])
	{
		int count1 = 0;
		int count2 = 0;
		int m = 0;
		int starts[] = new int[9];
		int ends[] = new int[9];
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				starts[m] = start[i][j];
				ends[m] = end[i][j];
				m++;
			}
			
		}
		
		
		for(int i = 0;i<9;i++)
		{
			for(int j = 0;j<i;j++)
 			    if(starts[i]<starts[j]&&starts[i]!=0) 			    
   				count1++;
		}
		
		
		for(int i = 0;i<9;i++)
		{
			for(int j = 0;j<i;j++)
 			    if(ends[i]<ends[j]&&ends[i]!=0)
   				count2++;
   		}
   		
		System.out.println("START="+count1);
		System.out.println("END="+count2);
		
		if(count1%2==count2%2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	   
	   
	   

    
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }


   
    
    public static void main(String[] args)
    {
    	try{ 
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
			}
			catch(Exception e){} 
    	new Astar();
    	}
}



