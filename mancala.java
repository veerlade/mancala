package ai;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
class config
{
    int fin[];
    int value;
    config(int a[],int val)
    {
        fin=new int[a.length];
       System.arraycopy(a, 0, fin, 0, a.length);
       value=val;
    }
    config(config a)
    {
        fin=new int[a.fin.length];
        System.arraycopy(a.fin, 0, fin, 0, a.fin.length);
        value=a.value;
    }
}
class board 
{
    config c;
    static int depth;
    static int player;
    int task;
    int position[];
    int counter=0;
    int pits;
    static int infinity=Integer.MAX_VALUE;
    List<String> tlog = new ArrayList<>();
    board(List<String> A)
    {
        
        task=Integer.parseInt(A.get(0));
	player=Integer.parseInt(A.get(1));
	depth=Integer.parseInt(A.get(2));
        int p=A.get(3).split(" ").length;
        String temp[]= A.get(4).split(" ");
        position=new int[2*p+2];
        pits=p;
        for(int i=0;i<temp.length;i++)
        {
            position[i]=Integer.parseInt(temp[i]);
            counter++;
        }
        position[counter++]=Integer.parseInt(A.get(6));
        temp= A.get(3).split(" ");
        int scounter=counter;
        for(int i=scounter+temp.length-1;i>=scounter;i--)
        {
            position[counter]=Integer.parseInt(temp[i-scounter]);
            counter++;
        }
        position[counter]=Integer.parseInt(A.get(5));
        c=new config(position,-infinity);
        if(task==1)
        {
            position = greedy(position,player);
        }
        else if(task==2)
        {
            tlog.add("Node,Depth,Value_");
            if(player==1)
        {
            tlog.add("root,0,-Infinity"+"_");
            c=minmax(c,player,"root",0,-infinity);
        }
        else
        {
            tlog.add("root,0,-Infinity"+"_");
            c=minmax(c,player,"root",0,-infinity);
        }
        }
        else
        {
            tlog.add("Node,Depth,Value,Alpha,Beta_");
            tlog.add("root,0,-Infinity,-Infinity,Infinity_");
            c=alphabeta(c,player,"root",0,-infinity,-infinity,infinity);
        }
        
        
    }
    config alphabeta(config pos1,int playernew,String current, int dpth, int value, int alpha1,int beta1)
    {
        int finalans=0;
    	config pos=new config(pos1);
    	int value1=value;
    		config ans=new config(pos1);
                config fans=new config(pos1);
    		config temp=new config(pos1);
            
    		int key=(pos.fin.length/2)-1;
    		if(player==1)
    		{
                    for(int i=0;i<key;i++)
    			{
    				if(pos.fin[i]!=0)
    				{
                                    System.arraycopy(pos.fin,0,temp.fin,0,pos.fin.length);
                                    temp.value=pos.value;
    					int j=i;
    					int cntr=pos.fin[j];
    		            j=performchange(j,temp.fin);
    		            if((j-1)%temp.fin.length==(temp.fin.length/2)-1)
    					ans=calalphabeta(pos,(playernew)%2,getcurrent(i),dpth+1,value,alpha1,beta1);
    		            else
    		            ans=calalphabeta(pos,(playernew+1)%2,getcurrent(i),dpth+1,-value,alpha1,beta1);
                            if(ans.value>value1)
                            {
                                alpha1=ans.value;
                                value1=ans.value;
                                fans=new config(ans);
                                finalans=i;
                            }
                            String finalalpha="";
                            String finalbeta="";
                            if(alpha1==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha1==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha1;
                            if(beta1==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta1==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta1;
                            
                         if(value1==Integer.MAX_VALUE)
                            {   
                                
                                tlog.add(current+","+dpth+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            }
                            else if(value1==-Integer.MAX_VALUE)
                            tlog.add(current+","+dpth+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(current+","+dpth+","+value1+","+finalalpha+","+finalbeta+"_");
                            
    		            
    				}
    			}
                    
                    return fans;
    		}
    		else
    		{
    			for(int i=pos.fin.length-2;i>=(pos.fin.length/2);i--)
    			{
    				if(pos.fin[i]!=0)
    				{
    					int j=i;
    					int cntr=pos.fin[j];
    		            j=performchange(j,temp.fin);
    		            if((j-1)%temp.fin.length==temp.fin.length-1)
    		            	ans=calalphabeta(pos,(playernew)%2,getcurrent(i),dpth+1,value,alpha1,beta1);
    		            else
    		            ans=calalphabeta(pos,(playernew+1)%2,getcurrent(i),dpth+1,-value,alpha1,beta1);
    		            if(ans.value>value1)
                            {
                                alpha1=ans.value;
                                value1=ans.value;
                                fans=new config(ans);
                                finalans=i;
                                
                            }
                            String finalalpha="";
                            String finalbeta="";
                            if(alpha1==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha1==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha1;
                            if(beta1==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta1==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta1;
    		            if(value1==Integer.MAX_VALUE)
                            tlog.add(current+","+dpth+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value1==-Integer.MAX_VALUE)
                            tlog.add(current+","+dpth+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(current+","+dpth+","+value1+","+finalalpha+","+finalbeta+"_");
                            }
                                System.arraycopy(pos.fin,0,temp.fin,0,pos.fin.length);
                                temp.value=pos.value;
    			}
                        
                        return fans;
    		}
    }
    config calalphabeta(config posa, int playera,String currenta,int deptha, int valuea, int alpha2,int beta2)
    {
      int value2=valuea;
      config pos1=new config(posa);
        config pos2=new config(posa);
        config pos4=new config(posa);
        int cntr=posa.fin[getlocation(currenta)];
        int j=getlocation(currenta);
        if(depth!=deptha)
        {
                            String finalalpha="";
                            String finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                            if(valuea==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(valuea==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+valuea+","+finalalpha+","+finalbeta+"_");
                          if(playera==1)
        {
        	int pj=performchange(j,pos1.fin);
                if(checkempty(pos1.fin))
                {
                    finalalpha="";
                     finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+","+finalalpha+","+finalbeta+"_");
                
                pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+","+finalalpha+","+finalbeta+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
                }
        	if((pj-1)%pos1.fin.length==(pos1.fin.length/2)-1)
                {
                    config tempo=new config(pos1);
        		for(int i=0;i<(pos1.fin.length/2)-1;i++)
                        {
        			if(pos1.fin[i]!=0)
        			{
                                   
                                    
                                    System.arraycopy(pos1.fin,0,tempo.fin,0,pos1.fin.length);
                                tempo.value=pos1.value;
                                    int tempi=performchange(i,tempo.fin);
                                    
                                    if(((tempi-1)%tempo.fin.length)==((pos1.fin.length/2)-1))
                                    pos2 = calalphabeta(pos1,(playera)%2,getcurrent(i),deptha,valuea,alpha2,beta2);
                                    else
                                    pos2 = calalphabeta(pos1,(playera+1)%2,getcurrent(i),deptha,-valuea,alpha2,beta2);
                                    if(player==1)
                                    {
                                    if(playera==1)
                                    {
                                        if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            if(value2>=beta2)
                                            {
                                               
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2>alpha2)
                                            alpha2=value2;
                                            pos4=new config(pos2);
                                        }
                                    }
                                    else
                                    {
                                      if(pos2.value<value2)
                                        {
                                           value2=pos2.value;
                                            if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             //beta2=value2;
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2<beta2)
                                            beta2=value2;
                                            pos4=new config(pos2);
                                        }  
                                    }
                                    }
                                    else
                                    {
                                        if(playera==1)
                                        {
                                            if(pos2.value<value2)
                                            {
                                                
                                                value2=pos2.value;
                                                if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                if(value2<beta2)
                                                beta2=value2;
                                                pos4=new config(pos2);
                                            } 
                                        }
                                      else
                                        {
                                            if(pos2.value>value2)
                                            {
                                                
                                                value2=pos2.value;
                                                if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                if(value2>alpha2)
                                                alpha2=value2;
                                                pos4=new config(pos2);
                                            }         
                                        }
                                    }
                                    finalalpha="";
                             finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                    if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                            
                            	}
                        }
                        return pos4;
                        
                        
                }
                else
                {
                  
                  for(int i=0;i<(pos1.fin.length/2)-1;i++)
                        {
        			if(pos1.fin[i]!=0)
        			{
                                    config tempo=new config(pos1);
                                    int tempi=performchange(i,tempo.fin);
                                    if((tempi-1)%pos1.fin.length==((pos1.fin.length/2)-1))
                                    pos2= calalphabeta(pos1,playera%2,getcurrent(i),deptha+1,valuea,alpha2,beta2);
                                    else
                                    pos2 = calalphabeta(pos1,(playera+1)%2,getcurrent(i),deptha+1,-valuea,alpha2,beta2);
                                    if(player==1)
                                    {
                                    if(playera==1)
                                    {
                                        if(pos2.value>value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2>alpha2)
                                            alpha2=value2;
                                            pos4=new config(pos2);
                                        }
                                    }
                                    else
                                    {
                                      if(pos2.value<value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2<beta2)
                                            beta2=value2;
                                            pos4=new config(pos2);
                                        }  
                                    }
                                    }
                                    else
                                    {
                                        if(playera==1)
                                        {
                                            if(pos2.value<value2)
                                            {
                                                
                                                value2=pos2.value;
                                                if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                if(value2<beta2)
                                                beta2=value2;
                                                pos4=new config(pos2);
                                            } 
                                        }
                                      else
                                        {
                                            if(pos2.value>value2)
                                            {
                                                
                                                value2=pos2.value;
                                                if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                if(value2>alpha2)
                                                alpha2=value2;
                                                pos4=new config(pos2);
                                            }         
                                        }
                                    }
                                    finalalpha="";
                                    finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                    if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                            	}
                        }
                  int superb=pos4.value;
                  pos4=new config(pos1);        
                  pos4.value=superb;
                 return pos4;       
                }
        }
        else    
        {
            int j1=performchange(j,pos1.fin);
            if(checkempty(pos1.fin))
            {
                finalalpha="";
                finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+finalalpha+","+finalbeta+"_");
                    pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+","+finalalpha+","+finalbeta+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
            }
            config pos3=new config(pos1);
            if((j1-1)%pos1.fin.length==pos1.fin.length-1)
                {
        		for(int i=pos1.fin.length-2;i>=(pos1.fin.length/2);i--)
                        {
        			if(pos1.fin[i]!=0)
        			{
                                    config tempo=new config(pos1);
                                    int tempi=performchange(i,tempo.fin);
                                    if(((tempi-1)%tempo.fin.length)==pos1.fin.length-1)
                                    pos2 = calalphabeta(pos1,(playera)%2,getcurrent(i),deptha,valuea,alpha2,beta2);
                                    else
                                    pos2 = calalphabeta(pos1,(playera+1)%2,getcurrent(i),deptha,-valuea,alpha2,beta2);
                                    if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2>alpha2)
                                            alpha2=value2;
                                            pos4=new config(pos2);
                                        }
                                            }
                                            else
                                            {
                                              if(pos2.value<value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2<beta2)
                                            beta2=value2;
                                            pos4=new config(pos2);
                                        }  
                                            }
                                        }
                                        else
                                        {
                                           if(playera==1)
                                           {
                                               if(pos2.value<value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2<beta2)
                                            beta2=value2;
                                            pos4=new config(pos2);
                                        }
                                           }
                                           else
                                           {
                                               
                                             if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2>alpha2)
                                            alpha2=value2;
                                            pos4=new config(pos2);
                                        }  
                                           }
                                        }
                                    finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                    if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                            	}
                        }
                        return pos4;
                }
            else{
                    for(int p=pos1.fin.length-2;p>=(pos1.fin.length/2);p--)
                        {
                            if(pos1.fin[p]!=0)
        			{
                                    pos3=new config(pos1);
                            		j1=performchange(p,pos3.fin);
                            	if((j1-1)%pos3.fin.length==pos3.fin.length-1)
        				pos2=calalphabeta(pos1,(playera)%2,getcurrent(p),deptha+1,valuea,alpha2,beta2);
        				else
        				pos2=calalphabeta(pos1,(playera+1)%2,getcurrent(p),deptha+1,-valuea,alpha2,beta2);
        				if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2>alpha2)
                                            alpha2=value2;
                                            pos4=new config(pos2);
                                        }
                                            }
                                            else
                                            {
                                              if(pos2.value<value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2<beta2)
                                            beta2=value2;
                                            pos4=new config(pos2);
                                        }  
                                            }
                                        }
                                        else
                                        {
                                           if(playera==1)
                                           {
                                               if(pos2.value<value2)
                                        {
                                            
                                            value2=pos2.value;
                                            if(alpha2>=value2)
                                            {
                                                 finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2<beta2)
                                            beta2=value2;
                                            pos4=new config(pos2);
                                        }
                                           }
                                           else
                                           {
                                               
                                             if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                            if(value2>alpha2)
                                            alpha2=value2;
                                            pos4=new config(pos2);
                                        }  
                                           }
                                        }
                                        finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                            if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
        			}
                                
                            
            	}
                
                int supera=pos4.value;
                pos4=new config(pos1);
                pos4.value=supera;
                return pos4;
            }
                
        }
        }
        else
        {
            if(playera==1)
            {
                int tp=performchange(j,pos1.fin);
                if(checkempty(pos1.fin))
                {
                    String finalalpha="";
                            String finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+","+finalalpha+","+finalbeta+"_");
                    pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+","+finalalpha+","+finalbeta+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
                }
                if((tp-1)%pos1.fin.length==((pos1.fin.length/2)-1))
    			{
                            String finalalpha="";
                            String finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                            if(valuea==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+","+finalalpha+","+finalbeta+"_");
                            else if(valuea==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+","+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+valuea+","+finalalpha+","+finalbeta+"_");
            		config pos3=new config(posa);
                        
            		for(int p=0;p<((posa.fin.length/2)-1);p++)
                	{
                          	if(pos1.fin[p]!=0)
            			{
            				pos3=new config(pos1);
            				tp=performchange(p,pos3.fin);
                          		if((tp-1)%pos2.fin.length==((pos2.fin.length/2)-1))
            				pos2=calalphabeta(pos1,(playera)%2,getcurrent(p),deptha,valuea,alpha2,beta2);
            				else
            				pos2=calalphabeta(pos1,(playera+1)%2,getcurrent(p),deptha,-valuea,alpha2,beta2);
            				if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                                {
                                                    
                                                    value2=pos2.value;
                                                    if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                    if(value2>alpha2)
                                                    alpha2=value2;
                                                    pos4=new config(pos2);
                                                }
                                            }
                                            else
                                            {
                                               if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                     if(alpha2>=value2)
                                                    {
                                                       finalalpha="";
                             finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                                       if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                                    pos4=new config(pos2);
                                                    return pos4;
                                                    }
                                                     if(value2<beta2)
                                                    beta2=value2;
                                                    pos4=new config(pos2);
                                                } 
                                            }
                                        }
                                        else
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                     if(alpha2>=value2)
                                                    {
                                                       finalalpha="";
                             finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                                       if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                                    pos4=new config(pos2);
                                                    return pos4;
                                                    }
                                                     if(value2<beta2)
                                                    beta2=value2;
                                                    pos4=new config(pos2);
                                                }  
                                            }
                                            else
                                            {
                                             if(pos2.value>value2)
                                                {
                                                    value2=pos2.value;
                                                    if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                    if(value2>alpha2)
                                                    alpha2=value2;
                                                    pos4=new config(pos2);
                                                }    
                                            }
                                        }
                                        finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                        if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                          	}
                          }
                        return pos4;
    			}
                else            
                {
                    String finalalpha="";
                            String finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                    if(player==1)
                    {
                        tlog.add(currenta+","+deptha+","+(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1])+","+finalalpha+","+finalbeta+"_");
                        pos1.value=(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1]);
                    }
                else
                {
                  tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)])+","+finalalpha+","+finalbeta+"_");
                pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)]);
                }
                    return pos1;
                }
            }
            else
            {
            	int tp=performchange(j,pos1.fin);
                if(checkempty(pos1.fin))
            {
                String finalalpha="";
                            String finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+","+finalalpha+","+finalbeta+"_");
                    pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+","+finalalpha+","+finalbeta+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
            }          
            	if((tp-1)%pos1.fin.length==pos1.fin.length-1)
    			{
                            String finalalpha="";
                            String finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                            if(valuea==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(valuea==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+valuea+","+finalalpha+","+finalbeta+"_");
            		config pos3=new config(posa);
            		for(int p=pos1.fin.length-2;p>=(pos1.fin.length/2);p--)
                	{
            			if(pos1.fin[p]!=0)
            			{
                                        pos3=new config(pos1);
            				tp=performchange(p,pos3.fin);
                			if((tp-1)%pos2.fin.length==pos2.fin.length-1)
            				pos2=calalphabeta(pos1,(playera)%2,getcurrent(p),deptha,valuea,alpha2,beta2);
            				else
            				pos2=calalphabeta(pos1,(playera+1)%2,getcurrent(p),deptha,-valuea,alpha2,beta2);
            				if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                                {
                                                    value2=pos2.value;
                                                    if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                    if(value2>alpha2)
                                                    alpha2=value2;
                                                    pos4=new config(pos2);
                                                }
                                            }
                                            else
                                            {
                                               if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                    if(alpha2>=value2)
                                                    {
                                                       finalalpha="";
                             finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                                       if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                                    pos4=new config(pos2);
                                                    return pos4;
                                                    }
                                                    if(value2<beta2)
                                                    beta2=value2;
                                                    pos4=new config(pos2);
                                                    
                                                } 
                                            }
                                        }
                                        else
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                     if(alpha2>=value2)
                                                    {
                                                       finalalpha="";
                             finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                                       if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                                    pos4=new config(pos2);
                                                    return pos4;
                                                    }
                                                     if(value2<beta2)
                                                    beta2=value2;
                                                    pos4=new config(pos2);
                                                }  
                                            }
                                            else
                                            {
                                             if(pos2.value>value2)
                                                {
                                                    value2=pos2.value;
                                                    if(value2>=beta2)
                                            {
                                                finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                             if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                                             pos4=new config(pos2);
                                             return pos4;
                                            }
                                                    if(value2>alpha2)
                                                    alpha2=value2;
                                                    pos4=new config(pos2);
                                                }    
                                            }
                                        }
                                        finalalpha="";
                            finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                                        if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity,"+finalalpha+","+finalbeta+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity,"+finalalpha+","+finalbeta+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+","+finalalpha+","+finalbeta+"_");
                            
            			}
                                
                	}
                        return pos4;
    			}
    			else
                    {
                        String finalalpha="";
                            String finalbeta="";
                            if(alpha2==Integer.MAX_VALUE)
                                finalalpha="Infinity";
                            else if(alpha2==-Integer.MAX_VALUE)
                                finalalpha="-Infinity";
                            else
                                finalalpha=""+alpha2;
                            if(beta2==Integer.MAX_VALUE)
                                finalbeta="Infinity";
                            else if(beta2==-Integer.MAX_VALUE)
                                finalbeta="-Infinity";
                            else
                                finalbeta=""+beta2;
                    if(player==1)
                    {
                        tlog.add(currenta+","+deptha+","+(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1])+","+finalalpha+","+finalbeta+"_");
                        pos1.value=(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1]);
                    }
                        else
                    {
                        tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)])+","+finalalpha+","+finalbeta+"_");
                        pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)]);
                    }   
                return pos1;
                }
                
            	
            }
        }  
    }        
    String getcurrent(int i)
    {
    	int key=(position.length/2)-1;
    	if(i<key)
    	return("B"+(i+2));
    	else
    	return("A"+(position.length-i));
    }
    config minmax(config posint,int playernew,String current, int dpth, int value)
    {
        int finalans=0;
    	config pos=new config(posint);
    	int value1=value;
    		config ans=new config(posint);
                config fans=new config(posint);
    		config temp=new config(posint);
            
    		int key=(pos.fin.length/2)-1;
    		if(player==1)
    		{
    	            for(int i=0;i<key;i++)
    			{
    				if(pos.fin[i]!=0)
    				{
                                    System.arraycopy(pos.fin,0,temp.fin,0,pos.fin.length);
        				int j=i;
    					int cntr=pos.fin[j];
    		            j=performchange(j,temp.fin);
                            if((j-1)%temp.fin.length==(temp.fin.length/2)-1)
    					ans=calminmax(pos,(playernew)%2,getcurrent(i),dpth+1,value);
    		            else
    		            ans=calminmax(pos,(playernew+1)%2,getcurrent(i),dpth+1,-value);
                            if(ans.value>value1)
                            {
                                value1=ans.value;
                                fans=new config(ans);
                                finalans=i;
                            }
                            
    		            if(value1==Integer.MAX_VALUE)
                            tlog.add(current+","+dpth+","+"Infinity"+"_");
                            else if(value1==-Integer.MAX_VALUE)
                            tlog.add(current+","+dpth+","+"-Infinity"+"_");
                            else
                            tlog.add(current+","+dpth+","+value1+"_");
                            }
    			}
                    return fans;
    		}
    		else
    		{
    			for(int i=pos.fin.length-2;i>=(pos.fin.length/2);i--)
    			{
    				if(pos.fin[i]!=0)
    				{
                                 	int j=i;
    					int cntr=pos.fin[j];
                            j=performchange(j,temp.fin);
                            if((j-1)%temp.fin.length==temp.fin.length-1)
    		            	ans=calminmax(pos,(playernew)%2,getcurrent(i),dpth+1,value);
    		            else
    		            ans=calminmax(pos,(playernew+1)%2,getcurrent(i),dpth+1,-value);
    		            if(ans.value>value1)
                            {   
                                value1=ans.value;
                                fans=new config(ans);
                                finalans=i;
                            }
    		            if(value1==Integer.MAX_VALUE)
                            tlog.add(current+","+dpth+","+"Infinity"+"_");
                            else if(value1==-Integer.MAX_VALUE)
                            tlog.add(current+","+dpth+","+"-Infinity"+"_");
                            else
                            tlog.add(current+","+dpth+","+value1+"_");
                            	}
                                System.arraycopy(posint.fin,0,temp.fin,0,posint.fin.length);
    			}
                        return fans;
    		}
    }
    int getlocation(String a)
    {
    	if(a.startsWith("A"))
    	{
            int p=Integer.parseInt(a.substring(1,a.length()));
    		return (position.length-p);
    	}
    	else
    	{
    		int p=Integer.parseInt(a.substring(1,a.length()));
                return (p-2);
    	}
    }
    int performchange(int k, int hope[])
    {
    	int cntr=hope[k];
    	int j=k;
        if(k%hope.length>((hope.length/2)-1))
    	{
    	        hope[j++]=0;
            for(int i=0;i<cntr;i++)
            {
                if(j%hope.length==((hope.length/2)-1))
                {
                    j=j+1;
                    hope[(j)%(hope.length)]+=1;
                    j++;
                }    
                else
                hope[(j++)%(hope.length)]+=1;
           }
            if(hope[((j-1)%hope.length)]==1 && ((j-1)%hope.length)!=hope.length-1 && (((j-1)%hope.length)>((hope.length/2)-1)))
            {
                int mirror=findmirror((j-1)%hope.length,hope);
                int cpy=hope[mirror%hope.length];
                hope[mirror%hope.length]=0;
                hope[hope.length-1]+=cpy;
                hope[((j-1)%hope.length)]=0;
                hope[hope.length-1]+=1;
            }
            boolean b=true;
            boolean x=true;
            for(int i=hope.length/2;i<hope.length-1;i++)
            {
                if(hope[i]!=0)
                    b=false;
            }
            for(int i=0;i<(hope.length/2)-1;i++)
            {
                if(hope[i]!=0)
                    x=false;
            }
            if(b || x)
            {
                int yp=0;
                for(int i=0;i<(hope.length/2)-1;i++)
                {
                   yp+=hope[i];
                   hope[i]=0;
                }
                
                hope[(hope.length/2)-1]+=yp;
                yp=0;
                for(int i=hope.length-2;i>((hope.length/2)-1);i--)
                {
                   yp+=hope[i];
                   hope[i]=0;
                }
                hope[hope.length-1]+=yp;
            }
    	}
    	else
    	{
        	hope[j++%hope.length]=0;
    		for(int i=0;i<cntr;i++)
        	{
        		if(j%hope.length==hope.length-1)
        		{
                            j=j+1;
        			hope[(j)%(hope.length)]+=1;
        			j++;
        		}
        		else
        		hope[(j++)%(hope.length)]+=1;
        	}
                if(hope[(j-1)%hope.length]==1 && ((j-1)%hope.length)!=((hope.length/2)-1) && (((j-1)%hope.length)<((hope.length/2)-1)))
            {
                int mirror=findmirror((j-1)%hope.length,hope);
                int cpy=hope[mirror%hope.length];
                hope[mirror%hope.length]=0;
                hope[(hope.length/2)-1]+=cpy;
                hope[((j-1)%hope.length)]=0;
                hope[(hope.length/2)-1]+=1;
            }
        	boolean b=true;
                boolean x=true;
            for(int i=0;i<(hope.length/2)-1;i++)
            {
                if(hope[i]!=0)
                    b=false;
            }
            for(int i=hope.length/2;i<hope.length-1;i++)
            {
                if(hope[i]!=0)
                    x=false;
            }
            if(b || x)
            {
                int yp=0;
                for(int i=hope.length-2;i>((hope.length/2)-1);i--)
                {
                   yp+=hope[i];
                   hope[i]=0;
                }
                hope[hope.length-1]+=yp;
                yp=0;
                for(int i=0;i<(hope.length/2)-1;i++)
                {
                   yp+=hope[i];
                   hope[i]=0;
                }
                hope[(hope.length/2)-1]+=yp;
                
            }
        	
         }
        return j;
    }
    boolean checkempty(int hope1[])
    {
        boolean check=true;
        int key1=(hope1.length/2)-1;
        for(int i=0;i<key1;i++)
        {
            if(hope1[i]!=0)
            {
                check=false;
                return check;
            }
        }
        for(int i=hope1.length-2;i>=(hope1.length/2);i--)
        {
            if(hope1[i]!=0)
            {check=false;
            return check;}
        }
        return check;
    }
    config calminmax(config posa, int playera,String currenta,int deptha, int valuea)
    {
    	int value2=valuea;
        config pos1=new config(posa);
        config pos2=new config(posa);
        config pos4=new config(posa);
        int cntr=posa.fin[getlocation(currenta)];
        int j=getlocation(currenta);
        if(depth!=deptha)
        {
        	if(valuea==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(valuea==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+valuea+"_");
        if(playera==1)
        {
        	int pj=performchange(j,pos1.fin);
                if(checkempty(pos1.fin))
                {
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+"_");
                
                pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
                }
        	if((pj-1)%pos1.fin.length==(pos1.fin.length/2)-1)
                {
        		for(int i=0;i<(pos1.fin.length/2)-1;i++)
                        {
        			if(pos1.fin[i]!=0)
        			{
                                    config tempo=new config(pos1);
                                    int tempi=performchange(i,tempo.fin);
                                    if(((tempi-1)%tempo.fin.length)==((pos1.fin.length/2)-1))
                                    pos2 = calminmax(pos1,(playera)%2,getcurrent(i),deptha,valuea);
                                    else
                                    pos2 = calminmax(pos1,(playera+1)%2,getcurrent(i),deptha,-valuea);
                                    if(player==1)
                                    {
                                    if(playera==1)
                                    {
                                        if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }
                                    }
                                    else
                                    {
                                      if(pos2.value<value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }  
                                    }
                                    }
                                    else
                                    {
                                        if(playera==1)
                                        {
                                            if(pos2.value<value2)
                                            {
                                                value2=pos2.value;
                                                pos4=new config(pos2);
                                            } 
                                        }
                                      else
                                        {
                                            if(pos2.value>value2)
                                            {
                                                value2=pos2.value;
                                                pos4=new config(pos2);
                                            }         
                                        }
                                    }
                                    if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+"_");
                            	}
                        }
                        return pos4;
                        }
                else
                {
                  for(int i=0;i<(pos1.fin.length/2)-1;i++)
                        {
        			if(pos1.fin[i]!=0)
        			{
                                    config tempo=new config(pos1);
                                    int tempi=performchange(i,tempo.fin);
                                    if((tempi-1)%pos1.fin.length==((pos1.fin.length/2)-1))
                                    pos2= calminmax(pos1,playera%2,getcurrent(i),deptha+1,valuea);
                                    else
                                    pos2 = calminmax(pos1,(playera+1)%2,getcurrent(i),deptha+1,-valuea);
                                    if(player==1)
                                    {
                                    if(playera==1)
                                    {
                                        if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }
                                    }
                                    else
                                    {
                                      if(pos2.value<value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }  
                                    }
                                    }
                                    else
                                    {
                                        if(playera==1)
                                        {
                                            if(pos2.value<value2)
                                            {
                                                value2=pos2.value;
                                                pos4=new config(pos2);
                                            } 
                                        }
                                      else
                                        {
                                            if(pos2.value>value2)
                                            {
                                                value2=pos2.value;
                                                pos4=new config(pos2);
                                            }         
                                        }
                                    }
                                    if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+"_");
                            }
                        }
                  int superb=pos4.value;
                  pos4=new config(pos1);        
                  pos4.value=superb;
                  return pos4;       
                }
        }
        else    
        {
            int j1=performchange(j,pos1.fin);
            if(checkempty(pos1.fin))
            {
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+"_");
                    pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
            }
            config pos3=new config(pos1);
            if((j1-1)%pos1.fin.length==pos1.fin.length-1)
                {
        		for(int i=pos1.fin.length-2;i>=(pos1.fin.length/2);i--)
                        {
        			if(pos1.fin[i]!=0)
        			{
                                    pos3=new config(pos1);
                                 	j1=performchange(i,pos3.fin);
                                   if((j1-1)%pos3.fin.length==pos3.fin.length-1)
                                       pos2 = calminmax(pos1,(playera)%2,getcurrent(i),deptha,valuea);
                                   else
                                        pos2 = calminmax(pos1,(playera+1)%2,getcurrent(i),deptha,-valuea);
                                    if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }
                                            }
                                            else
                                            {
                                              if(pos2.value<value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }  
                                            }
                                        }
                                        else
                                        {
                                           if(playera==1)
                                           {
                                               if(pos2.value<value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }
                                           }
                                           else
                                           {
                                             if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }  
                                           }
                                        }
                                    if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+"_");
                            }
                        }
                        return pos4;
                }
            else{
                    for(int p=pos1.fin.length-2;p>=(pos1.fin.length/2);p--)
                        {
        			if(pos1.fin[p]!=0)
        			{
                                    pos3=new config(pos1);
                         		j1=performchange(p,pos3.fin);
                         	if((j1-1)%pos3.fin.length==pos3.fin.length-1)
        				pos2=calminmax(pos1,(playera)%2,getcurrent(p),deptha+1,valuea);
        				else
        				pos2=calminmax(pos1,(playera+1)%2,getcurrent(p),deptha+1,-valuea);
        				if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }
                                            }
                                            else
                                            {
                                              if(pos2.value<value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }  
                                            }
                                        }
                                        else
                                        {
                                           if(playera==1)
                                           {
                                               if(pos2.value<value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }
                                           }
                                           else
                                           {
                                             if(pos2.value>value2)
                                        {
                                            value2=pos2.value;
                                            pos4=new config(pos2);
                                        }  
                                           }
                                        }
                                        if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+"_");
        			}
                }
                int supera=pos4.value;
                pos4=new config(pos1);
                pos4.value=supera;
                return pos4;
            }
                }
        }
        else
        {
            if(playera==1)
            {
            	int tp=performchange(j,pos1.fin);
                if(checkempty(pos1.fin))
                {
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+"_");
                    pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
                }
                if((tp-1)%pos1.fin.length==((pos1.fin.length/2)-1))
    			{
                            if(valuea==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(valuea==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+valuea+"_");
            		config pos3=new config(posa);
                        for(int p=0;p<((posa.fin.length/2)-1);p++)
                	{
                		if(pos1.fin[p]!=0)
            			{
            				pos3=new config(pos1);
            				tp=performchange(p,pos3.fin);
                			if((tp-1)%pos2.fin.length==((pos2.fin.length/2)-1))
            				pos2=calminmax(pos1,(playera)%2,getcurrent(p),deptha,valuea);
            				else
            				pos2=calminmax(pos1,(playera+1)%2,getcurrent(p),deptha,-valuea);
            				if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                }
                                            }
                                            else
                                            {
                                               if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                } 
                                            }
                                        }
                                        else
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                }  
                                            }
                                            else
                                            {
                                             if(pos2.value>value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                }    
                                            }
                                        }
                                        if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+"_");
                            
            			}
                	}
                        return pos4;
    			}
                else            
                {
                    if(player==1)
                    {
                        tlog.add(currenta+","+deptha+","+(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1])+"_");
                        pos1.value=(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1]);
                    }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)])+"_");
                pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)]);
                }return pos1;
                }
            }
            else
            {
                int tp=performchange(j,pos1.fin);
                if(checkempty(pos1.fin))
            {
                if(player==1)
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1])+"_");
                    pos1.value=(pos1.fin[(pos1.fin.length/2)-1]-pos1.fin[pos1.fin.length-1]);
                }
                else
                {
                    tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1])+"_");
                    pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[(pos1.fin.length/2)-1]);
                }
                   
                    return pos1;
            }
                if((tp-1)%pos1.fin.length==pos1.fin.length-1)
    			{
                            if(valuea==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(valuea==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+valuea+"_");
                        config pos3=new config(posa);
            		for(int p=pos1.fin.length-2;p>=(pos1.fin.length/2);p--)
                	{
            			if(pos1.fin[p]!=0)
            			{
                                        pos3=new config(pos1);
            				tp=performchange(p,pos3.fin);
                        		if((tp-1)%pos2.fin.length==pos2.fin.length-1)
            				pos2=calminmax(pos1,(playera)%2,getcurrent(p),deptha,valuea);
            				else
            				pos2=calminmax(pos1,(playera+1)%2,getcurrent(p),deptha,-valuea);
            				if(player==1)
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value>value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                }
                                            }
                                            else
                                            {
                                               if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                } 
                                            }
                                        }
                                        else
                                        {
                                            if(playera==1)
                                            {
                                                if(pos2.value<value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                }  
                                            }
                                            else
                                            {
                                             if(pos2.value>value2)
                                                {
                                                    value2=pos2.value;
                                                    pos4=new config(pos2);
                                                }    
                                            }
                                        }
                                        if(value2==Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"Infinity"+"_");
                            else if(value2==-Integer.MAX_VALUE)
                            tlog.add(currenta+","+deptha+","+"-Infinity"+"_");
                            else
                            tlog.add(currenta+","+deptha+","+value2+"_");
                            
            			}
                                
                	}
                        return pos4;
    			}
    			else
                    {
                    if(player==1)
                    {
                        tlog.add(currenta+","+deptha+","+(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1])+"_");
                        pos1.value=(pos1.fin[((pos1.fin.length/2)-1)]-pos1.fin[pos1.fin.length-1]);
                    }
                        else
                    {
                        tlog.add(currenta+","+deptha+","+(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)])+"_");
                        pos1.value=(pos1.fin[pos1.fin.length-1]-pos1.fin[((pos1.fin.length/2)-1)]);
                    }   
                return pos1;
                }
                }
        }
      }
    
    int[] greedy(int pos[],int plyr)
    {
      int temp[]=new int[pos.length];
      int temp2[]=new int[pos.length];
      int h=-Integer.MAX_VALUE;
      int key=pos.length/2-1;
      if(plyr==1)
      {
          for(int i=0;i<key;i++)
      {
          if(pos[i]!=0)
          {
              temp=greedycal(pos,i,plyr);
              if((temp[key]-temp[temp.length-1])>h)
              {
                 h=temp[key]-temp[temp.length-1];
                 for(int l=0;l<temp.length;l++)
                 {
                     temp2[l]=temp[l];
                 }
              }
          }
      }
      for(int o=0;o<temp2.length;o++)    
      {
          pos[o]=temp2[o];
      }
      }
      else
      {
        for(int i=pos.length-2;i>((pos.length/2)-1);i--)
        {
          if(pos[i]!=0)
          {
              temp=greedycal(pos,i,plyr);
              if((temp[temp.length-1]-temp[(temp.length/2)-1])>h)
              {
                 h=temp[temp.length-1]-temp[(temp.length/2)-1];
                 for(int k=0;k<temp.length;k++)
                 {
                     temp2[k]=temp[k];
                 }
                 }
          }
        }
      for(int o=0;o<temp2.length;o++)    
      {
          pos[o]=temp2[o];
      } 
      }
      return pos;
    }
    int[] greedycal(int pos[],int j,int plyr)
    {
        int pos1[]=new int[pos.length];
        for(int i=0;i<pos.length;i++)
        {
            pos1[i]=pos[i];
        }
        if(plyr==1)
        {
            int cntr=pos1[j];
            j=performchange(j,pos1);
            if(checkempty(pos1))
            {
                return pos1;
            }
            if((j-1)%pos1.length==(pos1.length/2)-1)
            {
                pos1 = greedy(pos1,plyr);
            }
        }
        else
        {
            int cntr=pos1[j];
            j=performchange(j,pos1);
            if(checkempty(pos1))
            {
                return pos1;
            }
            if((j-1)%pos1.length==pos1.length-1)
            {
                pos1 = greedy(pos1,plyr);
            }
            }
        return pos1;
    }
    int findmirror(int i,int arr[])
    {
        int dis=Math.abs(i-((arr.length/2)-1));
        if(i-(2*dis)>=0 && i>((arr.length/2)-1))
            return (i-(2*dis));
        else
            return (i+(2*dis));
    }
    void print() throws FileNotFoundException, IOException
    {
        File f = new File("next_state.txt");
        FileOutputStream fos = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(fos);
        if(task==1)
        {
            for(int i=position.length-2;i>((position.length/2)-1);i--)
            {
                if(i!=((position.length/2)-1))
                pw.write(position[i]+" ");
                else
                pw.write(position[i]+"");
            }
            pw.write(System.getProperty("line.separator"));
            for(int i=0;i<((position.length/2)-1);i++)
            {
                if(i!=((position.length/2)-1))
                pw.write(position[i]+" ");
                else
                pw.write(position[i]+"");
            }
            pw.write(System.getProperty("line.separator"));
            pw.write(position[position.length-1]+"");
            pw.write(System.getProperty("line.separator"));
            pw.write(position[((position.length/2)-1)]+"");
            pw.flush();
        //}
        fos.close();
        pw.close();
        return;
        }
        
            for(int i=c.fin.length-2;i>(c.fin.length/2)-1;i--)
            {
                if(i!=(pits+1))
                pw.write(c.fin[i]+" ");
                else
                pw.write(c.fin[i]+"");
            }
            pw.write(System.getProperty("line.separator"));
            for(int i=0;i<pits;i++)
            {
                if(i!=(pits-1))
                pw.write(c.fin[i]+" ");
                else
                pw.write(c.fin[i]+"");
            }
            pw.write(System.getProperty("line.separator"));
            pw.write(c.fin[position.length-1]+"");
            pw.write(System.getProperty("line.separator"));
            pw.write(c.fin[((position.length/2)-1)]+"");
            pw.flush();
        
        fos.close();
        pw.close();
        File f1 = new File("traverse_log.txt");
        fos = new FileOutputStream(f1);
        pw = new PrintWriter(fos);
        for(int hp=0;hp<tlog.size();hp++)
        {
            pw.write(tlog.get(hp).substring(0,tlog.get(hp).length()-1));
            pw.write(System.getProperty("line.separator"));
        }
        pw.flush();
        fos.close();
        pw.close();
        }
}
class mancala
{
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        File f = new File("output.txt");
        FileOutputStream fos = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(fos);
        File inFile=new File(args[1]);
        BufferedReader br = null;
        List<String> input = new ArrayList<String>();
        try 
        {
            br = new BufferedReader(new FileReader(inFile));
            	while(true)
            	{
            		String s=br.readLine();
            		if(s!=null)
            		input.add(s);
            		else 
            		break;
            	}
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
            if (br != null)br.close();
            } 
            catch (IOException ex) 
            {
            ex.printStackTrace();
            }
        }
        long startTime = System.currentTimeMillis();
        //board b1=new board(input);
        //b1.print();
        for(int i=0;i<10000;i++)
        {
            System.out.println(i);
        }
        fos.close();
        pw.close();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
}
