package ds8987_8832;
import java.util.ArrayList;
import java.lang.Math;
public class HeristicPlayer extends Player {
 ArrayList<Integer> path = new ArrayList<Integer>();
	
//Basic Constructor
	public HeristicPlayer() {
		path.add(0,-1);
	}
	public HeristicPlayer(int p) {
		path.add(0,p);
	}
	//Constructor with argument of type Game
	public HeristicPlayer(HeristicPlayer hp) {
     this.path = hp.path;
		}
	public ArrayList<Integer> getPath() {
		return path;
	}
	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}
	
	double[] evaluate(int id,int currentPos, int dice, int  opponetPos) {
		double[] d = {0.0,0.0,0.0};
		double d0=0;
		double NearSupplies = 0.0;
		double OpponetDist = 0.0;
		int border = 0;
		double distS = 0;
		double distM = 0;
		boolean wall = false;
		boolean supply = false;
		boolean opponet= false;
		if(dice == 1 ) {
		int i = currentPos+board.getN();
		if(currentPos+3*board.getN()< board.getN()*board.getN()  ) border = currentPos+3*board.getN();
		else if(currentPos+2*board.getN() <board.getN()*board.getN()  ) border = currentPos+2*board.getN() ;
		else if(currentPos+board.getN() <board.getN()*board.getN() ) border = currentPos+board.getN() ;
        while(i<=border && wall != true )  {
	     distS++;
	     distM++;
	    for(int s=0; s<board.getS(); s++) {
	    if(supply != true && board.supplies[s].getSupplyTileId() == i ) {
	    NearSupplies= 1.0/distS;
	    supply = true;
		}
	    }
	    if(  opponet!= true && board.tiles[i].getTileId() == opponetPos) {
	     OpponetDist = 1.0/distM;
	     
	    }
	    if(board.tiles[i].isUp() == true) wall = true;
		i=i+board.getN();
		}
		}else if(dice == 5 ) {
		int i = currentPos-board.getN();
		if(currentPos-3*board.getN() >= 0   ) border = currentPos-3*board.getN();
		else if(currentPos-2*board.getN() >= 0 ) border = currentPos-2*board.getN()  ;
		else if(currentPos-board.getN() >= 0  ) border = currentPos-board.getN() ;
	    while(i>=border && wall != true )  {
        distS++;
        distM++;
        for(int s=0; s<board.getS(); s++) {
        	if(wall == false && supply != true && board.supplies[s].getSupplyTileId() == i ) {
        	    NearSupplies= 1.0/distS;
        	    supply = true;
        		}
        	    }
        	    if( wall == false &&  opponet!= true && board.tiles[i].getTileId() == opponetPos) {
        	     OpponetDist = 1.0/distM;
        	     
        	    }
         if(board.tiles[i].isDown() == true) wall = true;
		i=i-board.getN();	
	    }
		}else if(dice == 3) {
		int i = currentPos+1;
		if(currentPos+3 <= ((currentPos/board.getN() +1)*board.getN() - 1) ) border = currentPos+3;
		else if(currentPos+2 <= ((currentPos/board.getN() +1)*board.getN() - 1)  ) border = currentPos+2 ;
		else if(currentPos+1<= ((currentPos/board.getN() +1)*board.getN() - 1) ) border = currentPos+1 ;
	    while(i<=border && wall != true )  {
         distS++;
	     distM++;
	     for(int s=0; s<board.getS(); s++) {
	    	 if(distS == 1 && supply != true && board.supplies[s].getSupplyTileId() == i ) {
	    	     supply = true;
	    	     NearSupplies= 1;
	    	    }else if(distS == 2 && supply != true && board.supplies[s].getSupplyTileId() == i  ) {
	    	     supply = true;
	    		 NearSupplies= 0.5;
	    	    }else if(distS == 3 && supply != true && board.supplies[s].getSupplyTileId() == i ) {
	    		 supply = true;
	    		 NearSupplies= 0.3;
	    		}
	    	    }
	    	    if(board.tiles[i].getTileId() == opponetPos) {
	    	    if(distM == 1 &&  opponet!= true) {
	    	     opponet= true;
	    	     OpponetDist = 1;
	    	    }else if(distM == 2 &&  opponet!= true ) {
	    	     opponet= true;
	    	     OpponetDist = 0.5;
	            }else if(distM == 3 &&  opponet!= true ) {
	             opponet= true;
	             OpponetDist = 0.3;
	    	     }
	    	    }
	    if(board.tiles[i].isRight() == true) wall = true;
	   	i++;
		}		
			
		}else if(dice == 7) {
		int i = currentPos-1;
		if(currentPos-3 >= 0 ) border = currentPos-3;
		else if(currentPos-2 >= 0 ) border = currentPos-2 ;
		else if(currentPos-1 >= 0 ) border = currentPos-1 ;
	    while(i>=border && wall != true )  {
	    distS++;
        distM++;
        for(int s=0; s<board.getS(); s++) {
        	if(wall == false && supply != true && board.supplies[s].getSupplyTileId() == i ) {
        	    NearSupplies= 1.0/distS;
        	    supply = true;
        		}
        	    }
        	    if( wall == false &&  opponet!= true && board.tiles[i].getTileId() == opponetPos) {
        	     OpponetDist = 1.0/distM;
        	     
        	    }
        	if(board.tiles[i].isLeft() == true) wall = true;
		   	i--;
	}		
   }	
		
		
		if(id==1) {
		d0=0.4*NearSupplies+0.9*OpponetDist;
		}else if ( id == 2) {
		d0=0.4*NearSupplies - 0.9*OpponetDist;//γιατι αν ειναι Τ S M θα κινηθει δεξια και μετα ο μινωταυρος θα τον φαει σιγουρα αν ειναι εξυπνος 
		}
		
		int start = start();
		if( path.size() >=8 && Math.abs(path.get(start+1)-dice) == 4){//epistrefei sto idio
		d0=-5;
		}
		int[] lastDices = lastDices();
		int sameDices=0;
		int repeatDice = 0;
		for(int k =0; k <4; k++) {
		 for(int j =0; j <4; j++) {
			if(lastDices[k] == lastDices[j] && k!=j) {
			sameDices++;
			repeatDice = lastDices[k];
			}
		 }
		}
		sameDices = sameDices/2;
		if(sameDices>=3 && dice == repeatDice && d0 <=0) {
			d0 = d0-0.1;//αν επαναλαμβάνει μία κατέυθυνση και δεν βλέπει εφόδιο να μην την ξαναπροτιμήσει	
			}
		
		d[0] = d0;
		d[1] = NearSupplies;
		d[2] = OpponetDist;
		
		return d;
	}
	int getNextMove(int id,int currentPos,int opponetPos) {
	     int newTile= currentPos;
	     int dice = 0;
	     double[] eval = {0,0,0,0};
	     double[] eval1 = evaluate(id,currentPos,1,opponetPos);
	     double[] eval3 = evaluate(id,currentPos,3,opponetPos);
	     double[] eval5 = evaluate(id,currentPos,5,opponetPos);
	     double[] eval7 = evaluate(id,currentPos,7,opponetPos);
	     if(board.tiles[currentPos].isUp() == true ) eval1[0] = -10;
	     if(board.tiles[currentPos].isRight() == true ) eval3[0] = -10;
	     if(board.tiles[currentPos].isDown() == true ) eval5[0] = -10;
	     if(board.tiles[currentPos].isLeft() == true ) eval7[0] = -10;
	     if(id == 1) {//o Θησεας κινειται προς τα πανω και δεξια ώστε να βρει τα εφοδια και ας ειναι προς τα παν ο Μινωταυρος γιατι αν δεν βρει εφόδια δεν θα νικησει
	      if(eval1[0]>=eval3[0] && eval1[0] >= eval5[0] && eval1[0] >=eval7[0]) {
		     eval=eval1;
		   	 dice=1;
		   }else  if(eval3[0]>=eval1[0] && eval3[0] >= eval5[0] && eval3[0] >=eval7[0]) {
		     eval=eval3;
		   	 dice =3;
	       }else if(eval7[0] >=eval1[0] && eval7[0] >=eval3[0] && eval7[0] >=eval5[0]) {
	    	 eval= eval7;
	    	 dice=7;
	       }else if(eval5[0]>=eval1[0] && eval5[0]>=eval3[0] && eval5[0]>=eval7[0]) {
 			
	    	 eval=eval5;
	    	 dice=5;
	       }
	     }else if (id == 2) {//o Μινωταθρος προτιμα κατω αριστερα γιατι εκει βρισκεται αρχικα  ο Θησεας
	    	 if(eval5[0]>=eval1[0] && eval5[0]>=eval3[0] && eval5[0]>=eval7[0]) {
	    			
		    	 eval=eval5;
		    	 dice=5;
		      }else if(eval7[0] >=eval1[0] && eval7[0] >=eval3[0] && eval7[0] >=eval5[0]) {
		    	 eval= eval7;
		    	 dice=7;
		     }  else if(eval1[0]>=eval3[0] && eval1[0] >= eval5[0] && eval1[0] >=eval7[0]) {
		    	 eval=eval1;
		    	 dice=1;
		     }else if(eval3[0]>=eval1[0] && eval3[0] >= eval5[0] && eval3[0] >=eval7[0]) {
		    	 eval=eval3;
		    	 dice =3;
		     }
	     }
	        
	     int point = 0;
	     int[] move = move(id,dice);
	     newTile = move[0];
	     path.add(dice);//1 ζαρι
	     point = 0;
	     path.add(point);//2ποντοι
	     path.add(newTile);//3 θησεας
	     path.add(opponetPos);//4 μινωταυρος
	     if(eval[1] == 1 && eval[0] >0 ) path.add(1);//5 πηρε ή οχι εφοδιο 
	     else  path.add(0);
	     if(eval[1] ==0) path.add(0); //6 αν εβλεπε εφοδιο
	     else path.add(1);
	     if(eval[2] ==0) path.add(0); //7 αν εβλεπε μονωταυρο 
	     else path.add(1);
	     if(eval[1] == 0.3) path.add(3);//8 αποσταση απο εφοδιο αν εβλεπε
	     else if(eval[1] == 0.5) path.add(2);
	     else if(eval[1] == 1) path.add(1);
	     if(eval[2] == -0.3) path.add(3);//9o αποσταση απο μινωταυρο αν εβλεπε-αν δεν εβλεπε εφοδιο και εβλεπε μινωταυρο θα ταν 8ο
	     else if(eval[2] == -0.5) path.add(2);
	     else if(eval[2] == -1) path.add(1);
	   
	     path.add(-1);//10ο για να ξεχωριζω οτι εκλεισε
	    // System.out.println("1= "+eval1[0]+" 3= "+eval3[0]+" 5= "+eval5[0]+" 7= "+eval7[0]);
	     return newTile;
		}
	public int start() {
		int start=-2;
		int t=path.size()-11;
		if (path.size() <12) t=0;
	for(int  i =path.size()-8;  i >=t; i--) {
		if(path.get(i) == -1) {
			start = i;
		}
	}
	return start;
	}
	public int[] lastDices() {
		int[] lastDices = {0,0,0,0,0};
		int start=-2;
		int j=0;
		if(j<5) {
		for(int  i =path.size()-8;  i >=0; i--) {
			if(path.get(i) == -1) {
				start = i;
			}
			if(start != -2) {
			lastDices[j] = path.get(start+1);
			start=-2;
			}
			
		}
		}
		return lastDices;
		
	}
	public void statistics() {
		boolean game = true;
		if(this.x == -1) {
			game = false;
		}
		int dice1=0;
		int dice3=0;
		int dice5=0;
		int dice7=0;
		int start = -2;
		if(game == true) {
		
          start = start();	
			//if(path.get(start+5 ) == 1) System.out.println("Tooks prizes!!!!");
			if(path.get(start+6) == 1) {//αν ρβλεπε εφοδιο εκτυπωνει αποσταση
			System.out.println("Distance from prize is "+path.get(start+8));
			if(path.get(start+7) == 1) System.out.println("Distance from minotaur is "+path.get(start + 9));//εβλεπε και εφοδιο και μινωταυρο
			}else{//δεν εβλεπε εφοδιο
			if(path.get(start+7) == 1) System.out.println("Distance from minotaur is "+path.get(start+ 8)); 
			}

		}else {
			for(int  i =path.size()-8;  i >=0; i--) {
				if(path.get(i) == -1) {
					start = i;
				}
				if(start!= -2) {
				if(path.get(start+1) == 1)  dice1++;
				if(path.get(start+1) == 3)  dice3++;
				if(path.get(start+1) == 5)  dice5++;
				if(path.get(start+1) == 7)  dice7++;
				start=-2;
				}
				
			}
			System.out.println( "1 = "+dice1+" 3 = "+dice3+" 5 = "+dice5+" 7 = "+dice7);
			
       }
		
	}		
		
}

