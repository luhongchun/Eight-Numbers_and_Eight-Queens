import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.lang.Math;
import java.util.Random;
class Node 
{  
	int Id;
	int[][] arr=new int[3][3];
	int FatherId;      
	int g;
	int f;
	int h;
	long hash;

	public  Node(int Id,int FatherId,int[][] arr,int f,int g,int h,long hash) 
	{
	 	 this.Id=Id;
		 this.FatherId=FatherId;
		 
		 for(int i=0;i<=2;i++)
		 {
		 	for(int j=0;j<=2;j++)
		    this.arr[i][j]=arr[i][j];
		 }
		 
		 this.f=f;
		 this.g=g;
		 this.h=h;
		 this.hash = hash;
	}
}
 
 
 
 
 
 

public class EightNumber 
{
	public  Vector open=new Vector(1,1);
	public  Vector close=new Vector(1,1);
	public  int current_deepth=0;
	Vector Result=new Vector(1,1);



public  Vector process(int[][] Source,int[][] Dest)
{
      boolean flag=true;
             
      Node Father;               
      
      int count=0;
      
      long start_hash =hashValue(Source);
      long end_hash =hashValue(Dest);

      Node source=new Node(0,0,Source,0,0,0,0); 
      Node dest=new Node(0,0,Dest,0,0,0,0);
      
      source.h=caculatemove(source,dest);
      source.f=source.g+source.h;
      
    
      push(source,open);
      Node current_node=source;
      
   	  while(flag)
      {   
                     
	      if(open.isEmpty())
	      {
	           flag=false;
	           break;
	      }
	      
	      
	      current_node=pop(open);

	      push(current_node,close);
	    
	      
	      if (judgesame(current_node, dest)) 
	      {   	      
               System.out.println("恭喜，找到目标结点路径了");
	           break;
	      }
      	
	      
	      	
	       
	      extendnode(current_node,dest);

	      QueueOpen();
      }
   
   
      if(flag)
      {
        Result.add(current_node);        
        int m=current_node.FatherId;
        while(m>0)
        {
          Father=Found_list(m,close,source);
          m=Father.FatherId;
          Result.add(Father);
          count++;
        }
      }
      
      Result.add(source);
      
      return Result;              
      

}


public   Node Found_list(int k,Vector vec,Node Source)
{ 
   Node result=Source;
   int i;
   
   for(i=0;i<vec.size();i++)
   {
     if(k==((Node)vec.get(i)).Id)
     {
     	result=(Node)vec.get(i);
     }
  }
  return result;
}

public  boolean judgesame(Node s1,Node s2)
{       
    int[][] arr=new int[3][3];
    for(int i=0;i<=2;i++)
    {
      for(int j=0;j<=2;j++)
      {
          if(!(s1.arr[i][j]==s2.arr[i][j]))
          return false;
      }
    }
    return true;
}


  public  void push(Node node,Vector open)
  { 
    
    open.add(node);
  }

  public  Node pop(Vector open)
  {  
    Node node=(Node)open.get(0);
    open.removeElementAt(0);
    return node;
  }

public  void extendnode(Node current_node,Node des)
{  	 	

 
	  int m=0;
	  int n=0;
	  int i=0;
	  int j=0;
	  int zeropos=0;

   for(i=0;i<=2;i++)
   { 
    for(j=0;j<=2;j++)
    {
      if(current_node.arr[i][j]==0)
      {
	      m=i;
	      n=j;
      }
    }
   }
   zeropos=3*m+n;
  


 if(zeropos/3-1>=0)
 { 
 	      
     extend_node(current_node,des,m,n,m-1,n);
   	    	           
 }


 if(zeropos/3+1<3)
 { 
	extend_node(current_node,des,m,n,m+1,n);
 }


 if(zeropos%3-1>=0)
{ 
		extend_node(current_node,des,m,n,m,n-1);
}




 if(zeropos%3+1<3)
{ 
	extend_node(current_node,des,m,n,m,n+1);
}


}





public void extend_node(Node current_node,Node des,int m,int n,int change_row,int change_col)
{
		int i=0;
		int j=0;
		
		int[][] arr=new int[3][3];
		boolean flag=true;
		Node pnewnode=new Node(0,0,arr,0,0,0,0);
		
       	
		for(i=0;i<=2;i++)
		{
			for(j=0;j<=2;j++)
			{
				pnewnode.arr[i][j]=current_node.arr[i][j];
				pnewnode.arr[m][n]=current_node.arr[change_row][change_col];  
				pnewnode.arr[change_row][change_col]=0;
			}		
		}	 
		
			long pnewnode_hash =hashValue(pnewnode.arr);
        	pnewnode.hash = pnewnode_hash;   
 		    
 		  
 		    
 		     int  k=0;

             while(k<close.size())
             {
                  
                  flag=false;           		
                if(pnewnode.hash==((Node)close.get(k)).hash)
                {
                	flag=true;
                	break;
                }
                
                
             k++;
             }
             
         

	          if(!flag)  
	           {
	               pnewnode.FatherId=current_node.Id;	               
	               current_deepth=current_deepth+1;               
	               pnewnode.Id=current_deepth;
	               pnewnode.g=current_node.g+1;
	               pnewnode.h=caculatemove(pnewnode,des);
	               pnewnode.f=pnewnode.g+pnewnode.h;
	               pnewnode.hash = pnewnode_hash;
	               open.add(pnewnode);
	           }
}


public  void QueueOpen()
{       
     
      
     int min_flag=0;
     int[][] arr=new int[3][3];
     Node temp=new Node(0,0,arr,0,0,0,0);
     Node node_flag=(Node)open.get(0);	
     int minvalue=node_flag.f;
     
   
    for(int i=0;i<open.size();i++)
    { 
       Node opei=(Node)open.get(i);       
       if(opei.f<minvalue)    
       {      
         minvalue=opei.f;
         min_flag=i;
       }
    }


      temp=(Node)open.get(0);
      open.setElementAt((Node)open.elementAt(min_flag),0);  
      open.setElementAt(temp,min_flag);
      
  }

  
  public  int caculatemove(Node nod,Node destin)
 {
 	 
   
	int count=0;
	for(int i=0;i<=2;i++)
	for(int j=0;j<=2;j++)
	for(int g=0;g<=2;g++)
	for(int k=0;k<=2;k++)
	{   
		if(nod.arr[i][j]==destin.arr[g][k])       
		{
			count+=Math.abs(i-g)+Math.abs(j-k);
		}
		
	        	
	} 
 	return count;
 }
    
	
	public static int[] randnumber()
	{
		int[] rand = new int[9];
		int[] num = {0,1,2,3,4,5,6,7,8};
		int local = 0;
		int length = 9;
		
		Random random = new Random();
		for(int i = 0;i<9;i++)
		{
			local = random.nextInt(length);			
			rand[i] = num[local];
			num[local] = num[length-1];
			length--;						
		}
		return  rand;
	}
       
public long hashValue(int [][]arr)
{
	int one[]=new int[9];
	int opp[]=new int[9];
	int n=0;
	int k=0;
	int pos=0;
	int count=0;
	long hashvalue=0;
	long jiechen[] ={1,1,2,6,24,120,720,5040,40320,362880};
	
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			one[n]=arr[i][j];
			if(one[n]==0)
				pos=n;
			n++;
			
		}
	}
	
	for(int i=0;i<9;i++)
	{
		for(int j=0;j<i;j++)
		{
			if(one[i]<one[j])
			{
				count++;
			
			}
		
		}
		opp[k]=count;
		k++;
		count=0;
	}
	
	for(int i=0;i<9;i++)
	{
		if(i==pos)
		hashvalue+=(9-pos)*jiechen[pos];
		else
		hashvalue+=opp[i]*jiechen[i];
	}
	return hashvalue;
		
}
    
    
     

}







