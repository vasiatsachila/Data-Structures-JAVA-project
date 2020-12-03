package ds8987_8832;
import java.util.ArrayList;
import java.lang.Math;
public class HeristicPlayer extends Player {
 ArrayList<Integer> path = new ArrayList<Integer>();//δομη που αποθηκεύει πληροφορίες απο όλους τους γύρους του παιχνιδιού
	
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
	
	double[] evaluate(int id,int currentPos, int dice, int  opponetPos) {//συναρτηση αξιολογησης της εκάστοτε κίνησης 
		double[] d = {0.0,0.0,0.0};//αρχικοποίηση πίνακα που επιστρέφει τις πληροφορίες της κίνησης αυτής
		double d0=0; //μεταβλητή για τους πόντους της κίνσησης
		double NearSupplies = 0.0; //πόντοι σχετικά με το αν βλέπει εφόδιο ή οχι
		double OpponetDist = 0.0; //πόντοι σχετικά με το αν βλέπει τον αντίπαλο ή οχι ή οχι
		int border = 0; // σύνορο- αν υπάρχουν ή όχι 3 πλακάκια να δει 
		double distS = 0;//αποστασή παίκτη απο εφόδιο
		double distO = 0;//αποστασή παίκτη απο εφόδιο
		boolean wall = false;//αν βλέπει τείχος ή οχι
		boolean supply = false;//αν βλέπει εφόδιο ή όχι
		boolean opponet= false;//αν βλέπει αντίπαλο ή οχι
		if(dice == 1 ) {//αν το ζάρι δώσει 1-βόρεια
		int i = currentPos+board.getN();//το πρώτο πλακάκι που ψάχνει-το διπλανό απο πάνω
		//αν υπάρχουν 3 πλακίδια προς τα πάνω το όριο είναι το tileId του 3ου προς τα πανώ αλλίως αν βλέπει 2 είναι του 2ου κτλ
		if(currentPos+3*board.getN()< board.getN()*board.getN()  ) border = currentPos+3*board.getN();
		else if(currentPos+2*board.getN() <board.getN()*board.getN()  ) border = currentPos+2*board.getN() ;
		else if(currentPos+board.getN() <board.getN()*board.getN() ) border = currentPos+board.getN() ;
        while(i<=border && wall != true )  {//όσο βρίσκεται όντως της ορατότητας του και δεν υπάρχει τείχος που τον ενοχλεί
	    //αυξάνει τις αποστάσεις εφοδίου-αντιπάλου
         distS++;
	     distO++;
	    for(int s=0; s<board.getS(); s++) {
	    if(supply != true && board.supplies[s].getSupplyTileId() == i ) {//ψάχνει να βρεί εφόδιο εφοσόν δεν έχει βρεί εφόδιο σε πιο κοντινό πλακάκι
	    NearSupplies= 1.0/distS;//πόντοι για εφόδιο- όσο πιο κοντά τόσο πίο πολλοί πόντοι
	    supply = true;//βρήκε εφόδιο
		}
	    }
	    if(  opponet!= true && board.tiles[i].getTileId() == opponetPos) {//ψάχνει να βρεί τον αντίπαλο εφοσόν δεν έχει βρεί εφόδιο σε πιο κοντινό πλακάκι
	     OpponetDist = 1.0/distO;//πόντοι για  εύρεση αντιπάλου- όσο πιο κοντά τόσο πίο πολλοί πόντοι
	     opponet = true;//βρήκε τον αντίπαλο-ουσιαστικα και να μην υπήρχε δεν θα ειχαμε προβλημά γιατι ενας αντίπαλος υπάρχει απλα για να μην ψάχνει άδικα αν έχει βρεθεί
	    }
	    if(board.tiles[i].isUp() == true) wall = true;//αν το τρέχον τείχος έχει πάνω τείχος δεν ξάνα τρέχει η while γιατι ο παίκτης παύει να χει ορατότητα
		i=i+board.getN();//πάει στο παραπάνω πλακάκι
		}
		}else if(dice == 5 ) {//αν το ζάρι δώσει 5- νότια
		int i = currentPos-board.getN();//το πρώτο πλακάκι που ψάχνει-το διπλανό απο κάτω
		//αν υπάρχουν 3 πλακίδια προς τα κάτω το όριο είναι το tileId του 3ου προς τα κάτω αλλίως αν βλέπει 2 είναι του 2ου κτλ
		if(currentPos-3*board.getN() >= 0   ) border = currentPos-3*board.getN();
		else if(currentPos-2*board.getN() >= 0 ) border = currentPos-2*board.getN()  ;
		else if(currentPos-board.getN() >= 0  ) border = currentPos-board.getN() ;
	   //όμοια λογική με παράπάνω απλα τώρα το i-- και η while εκτελείται όσο το i είναι μεγαλύτερο απο το border
		while(i>=border && wall != true )  {
        distS++;
        distO++;
        for(int s=0; s<board.getS(); s++) {
        	if(wall == false && supply != true && board.supplies[s].getSupplyTileId() == i ) {
        	    NearSupplies= 1.0/distS;
        	    supply = true;
        		}
        	    }
        	    if( wall == false &&  opponet!= true && board.tiles[i].getTileId() == opponetPos) {
        	     OpponetDist = 1.0/distO;
        	     opponet= true;
        	    }
         if(board.tiles[i].isDown() == true) wall = true;
		i=i-board.getN();	
	    }
		}else if(dice == 3) {//αν το ζάρι δώσεο 3-ανατολικά
		int i = currentPos+1;//το πρώτο πλακάκι που ψάχνει-το διπλανό απο τα δεξιά
		//όμοια με πρίν το border ειναι 3 αν υπάρχουν 3 πλακάκια  δεξιότερα  αλλίως 2,1 ανάλογα που το πόσα υπάρχουν.
		if(currentPos+3 <= ((currentPos/board.getN() +1)*board.getN() - 1) ) border = currentPos+3;
		else if(currentPos+2 <= ((currentPos/board.getN() +1)*board.getN() - 1)  ) border = currentPos+2 ;
		else if(currentPos+1<= ((currentPos/board.getN() +1)*board.getN() - 1) ) border = currentPos+1 ;
	   //όμοια λογική -το i εδώ αυξάνεται κατά 1 ώστε να ψάχνει προς τα δεξιά 
		while(i<=border && wall != true )  {
         distS++;
	     distO++;
	     for(int s=0; s<board.getS(); s++) {
	        	if(wall == false && supply != true && board.supplies[s].getSupplyTileId() == i ) {
	        	    NearSupplies= 1.0/distS;
	        	    supply = true;
	        		}
	        	    }
	        	    if( wall == false &&  opponet!= true && board.tiles[i].getTileId() == opponetPos) {
	        	     OpponetDist = 1.0/distO;
	        	     opponet= true;
	        	    }
	    if(board.tiles[i].isRight() == true) wall = true;
	   	i++;
		}		
			
		}else if(dice == 7) {//αν το ζάρι δώσεο 7-δυτικοτερα
		int i = currentPos-1;//το πρώτο πλακάκι που ψάχνει - το διπλανο απο τα αριστερα
		//όμοια με πρίν το border ειναι 3 αν υπάρχουν 3 πλακάκια  αριστερα  αλλίως 2,1 ανάλογα που το πόσα υπάρχουν.
		if(currentPos-3 >= 0 ) border = currentPos-3;
		else if(currentPos-2 >= 0 ) border = currentPos-2 ;
		else if(currentPos-1 >= 0 ) border = currentPos-1 ;
	//όμοια λογική -το i εδώ μειώνεται κατά 1 ώστε να ψάχνει προς τα αριστερα 
	    while(i>=border && wall != true )  {
	    distS++;
        distO++;
        for(int s=0; s<board.getS(); s++) {
        	if(wall == false && supply != true && board.supplies[s].getSupplyTileId() == i ) {
        	    NearSupplies= 1.0/distS;
        	    supply = true;
        		}
        	    }
        	    if( wall == false &&  opponet!= true && board.tiles[i].getTileId() == opponetPos) {
        	     OpponetDist = 1.0/distO;
        	     opponet=true;
        	    }
        	if(board.tiles[i].isLeft() == true) wall = true;
		   	i--;
	}		
   }	
		
		
		if(id==1) {//Ο Θησέας έχει (-) στο OpponetDist γιατί θέλει να αποφύγει τον Μινώταυρο 
		d0=0.4*NearSupplies-0.9*OpponetDist;
		}else if ( id == 2) {//Ο Μινώταυρος έχει (+) στο OpponetDist γιατί θέλει να πλησιάσει  τον Θησέα 
		d0=0.4*NearSupplies + 0.9*OpponetDist;
		}
		
		int start = start();//παίρνει την αρχική θέση (με το -1) της τελευταίας κίνησης
		if( path.size() >1 && Math.abs(path.get(start+1)-dice) == 4){//για κάθε γύρο έκτος απο τον πρώτο ελέγχει αν ο παίκτης πάει να επιστρέψει στην προηγ.θέση
		d0=-0.1;//δίνει -0.1 ώστε αν υπάρχει δυνάτη καλύτερη επιλογή να προτιμήσει εκείνη 
		}
		int[] lastDices = lastDices();//επιστρέγει τις τελευταίες 5 τιμές του ζαριού που επιλέχθηκαν-5 τελευταίες κατευθύνσεις 
		int sameDices=0;//ίδιες κατευθύνσεις
		for(int k =0; k <5; k++) {
			if(lastDices[k] == 1) {
			sameDices++;
			}
		 
		}
		if(sameDices>=3 && d0 <=0) {
			d0 = d0-0.1;//αν επαναλαμβάνει μία κατέυθυνση και δεν βλέπει εφόδιο να μην την ξαναπροτιμήσει	
			}
		
		d[0] = d0;//τελική πόντοι κίνησης
		d[1] = NearSupplies;//πόντοι σχετικά με ορατότητα εφοδιών
		d[2] = OpponetDist;//πόντοι σχετικά με ορατότητα αντιπάλου
		
		return d;
	}
	int getNextMove(int id,int currentPos,int opponetPos) {
	     int newTile= currentPos;//νέο πλακάκι
	     int dice = 0;//η τιμη του ζαριου-κατεύθυνση
	     double[] eval = {0,0,0,0};//τελικός πίνακας
	     double[] eval1 = {-100,-100,-100,-100};
	     double[] eval3 = {-100,-100,-100,-100};
	     double[] eval5 = {-100,-100,-100,-100};
	     double[] eval7 =  {-100,-100,-100,-100};
	     if(board.tiles[currentPos].isUp() == false )  eval1 = evaluate(id,currentPos,1,opponetPos);
	     if(board.tiles[currentPos].isRight() == false ) eval3 = evaluate(id,currentPos,3,opponetPos);
	     if(board.tiles[currentPos].isDown() ==false )  eval5 = evaluate(id,currentPos,5,opponetPos);
	     if(board.tiles[currentPos].isLeft() == false )  eval7 = evaluate(id,currentPos,7,opponetPos);
	     if(id == 1) {//o Θησεας κινειται με προτεραιοτητα προς τα πανω και δεξια ώστε να βρει τα εφοδια και ας ειναι προς τα παν ο Μινωταυρος γιατι αν δεν βρει εφόδια δεν θα νικησει
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
	     int[] move = move(id,dice);//κινεί τον παίκτη
	     newTile = move[0];//νέο πλακίδιο
	     path.add(dice);//1ο ζαρι-κατεύθυνση
	     point = 0;
	     path.add(point);//2ο ποντοι
	     path.add(newTile);//3ο θησεας(μινώταυρος)
	     path.add(opponetPos);//4ο μινωταυρος((θησέας)
	     if(eval[1] == 1 && eval[0] >0 ) path.add(1);//5 πηρε ή οχι εφοδιο -για id 2 πάντα 0
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
	     return newTile;//επιστρέφει νέο πλακίδιο
		}
	public int start() {//επιστρέφει τη θέση που  θα διαχωρίζει τις εγγραφές δύο διαδοχικών κινήσεων-σημείο εκκίνησης νέας εγγραφής.
		int start=-2;//αρχικοποίηση
		int t=path.size()-11;//max μέγεθος κινησής 10+1(path(0,-1))
		if (path.size() <12) t=0;//αν πρόκειται για την πρώτη κίνηση κάθε παίκτη 
	for(int  i =path.size()-8;  i >=t; i--) {//min μέγεθος 7+1 άρα ψάχνει απο 8 και κάτω ώστε να βρεί -1 (σημείο τέλους-αρχής)
		if(path.get(i) == -1) {
			start = i;//παίρνει την θέση όπου ξεκίνάνε οι εγγραφές της κίνησης
		}
	}
	return start;
	}
	public int[] lastDices() {
		int[] lastDices = {0,0,0,0,0};
		int start=-2;
		int j=0;
		if(j<5) {//για 5 κινήσεις
		for(int  i =path.size()-8;  i >=0; i--) { //απο 8 και κάτω γιατι είναι το ελάχιστο μέγεθός εγγραφών κίνησς-πριν δεν υπάρχει πιθανοτητα να βρω -1
			if(path.get(i) == -1) {
				start = i;//βρησκω τη θέση διαχωρισμού 
			}
			if(start != -2) {//αν έχει βρεί θέση διαχωρισμού
			lastDices[j] = path.get(start+1);//αποθηκεύει στον πίνακα την τιμή του ζαριού που επιλέχθηκε
			start=-2;//αλλαζει την τιμή του start ώστε να ψάξει για εγγραφές προηγούμενης κίνησης
			}
			
		}
		j++;//αυξάνει το j- μετρά πόσες κινήσεις έχει ελέγξει μέχρι τώρα
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

