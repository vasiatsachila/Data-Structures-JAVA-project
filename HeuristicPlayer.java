package ds8987_8832;
import java.util.ArrayList;
public class HeuristicPlayer extends Player {
 ArrayList<Integer> path = new ArrayList<Integer>();//δομη που αποθηκεύει πληροφορίες απο όλους τους γύρους του παιχνιδιού
	
//Basic Constructor
	public HeuristicPlayer() {
		path.add(0,-1);
	}
	public HeuristicPlayer(int id, String name, Board b, int score,int x,int y,int p) {
		super(id,name,b,score,x,y);
		path.add(0,p);
	}
	//Constructor with argument of type Game
	public HeuristicPlayer(HeuristicPlayer hp) {
	 super(hp.playerId,hp.name,hp.board,hp.score,hp.x,hp.y);
     this.path = hp.path;
		}
	public ArrayList<Integer> getPath() {
		return path;
	}
	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}
	
	double[] evaluate(int currentPos, int dice, int  opponentPos) {//συναρτηση αξιολογησης της εκάστοτε κίνησης 
		double[] d = {0.0,0.0,0.0};//αρχικοποίηση πίνακα που επιστρέφει τις πληροφορίες της κίνησης αυτής
		double d0=0; //μεταβλητή για τους πόντους της κίνσησης
		double NearSupplies = 0.0; //πόντοι σχετικά με το αν βλέπει εφόδιο ή οχι
		double opponentDist = 0.0; //πόντοι σχετικά με το αν βλέπει τον αντίπαλο ή οχι ή οχι
		int border = 0; // σύνορο- αν υπάρχουν ή όχι 3 πλακάκια να δει 
		double distS = 0;//αποστασή παίκτη απο εφόδιο
		double distO = 0;//αποστασή παίκτη απο εφόδιο
		boolean wall = false;//αν βλέπει τείχος ή οχι
		boolean supply = false;//αν βλέπει εφόδιο ή όχι
		boolean opponent= false;//αν βλέπει αντίπαλο ή οχι
		int newTile = 0;
		if(dice == 1 ) {//αν το ζάρι δώσει 1-βόρεια
		int i = currentPos+board.getN();//το πρώτο πλακάκι που ψάχνει-το διπλανό απο πάνω
		newTile = i;//αν κινηθει βορεια θα ειναι το νέο πλακάκι
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
	    if(  opponent!= true && board.tiles[i].getTileId() == opponentPos) {//ψάχνει να βρεί τον αντίπαλο εφοσόν δεν έχει βρεί εφόδιο σε πιο κοντινό πλακάκι
	     opponentDist = 1.0/distO;//πόντοι για  εύρεση αντιπάλου- όσο πιο κοντά τόσο πίο πολλοί πόντοι
	     opponent = true;//βρήκε τον αντίπαλο-ουσιαστικα και να μην υπήρχε δεν θα ειχαμε προβλημά γιατι ενας αντίπαλος υπάρχει απλα για να μην ψάχνει άδικα αν έχει βρεθεί
	    }
	    if(board.tiles[i].isUp() == true) wall = true;//αν το τρέχον τείχος έχει πάνω τείχος δεν ξάνα τρέχει η while γιατι ο παίκτης παύει να χει ορατότητα
		i=i+board.getN();//πάει στο παραπάνω πλακάκι
		}
		}else if(dice == 5 ) {//αν το ζάρι δώσει 5- νότια
		int i = currentPos-board.getN();//το πρώτο πλακάκι που ψάχνει-το διπλανό απο κάτω
		newTile = i;
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
        	    if( wall == false &&  opponent!= true && board.tiles[i].getTileId() == opponentPos) {
        	     opponentDist = 1.0/distO;
        	     opponent= true;
        	    }
         if(board.tiles[i].isDown() == true) wall = true;
		i=i-board.getN();	
	    }
		}else if(dice == 3) {//αν το ζάρι δώσεο 3-ανατολικά
		int i = currentPos+1;//το πρώτο πλακάκι που ψάχνει-το διπλανό απο τα δεξιά
		newTile = i;
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
	        	    if( wall == false &&  opponent!= true && board.tiles[i].getTileId() == opponentPos) {
	        	     opponentDist = 1.0/distO;
	        	     opponent= true;
	        	    }
	    if(board.tiles[i].isRight() == true) wall = true;
	   	i++;
		}		
			
		}else if(dice == 7) {//αν το ζάρι δώσεο 7-δυτικοτερα
		int i = currentPos-1;//το πρώτο πλακάκι που ψάχνει - το διπλανο απο τα αριστερα
		newTile = i;
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
        	    if( wall == false &&  opponent!= true && board.tiles[i].getTileId() == opponentPos) {
        	     opponentDist = 1.0/distO;
        	     opponent=true;
        	    }
        	if(board.tiles[i].isLeft() == true) wall = true;
		   	i--;
	}		
   }	
		
		
		if(this.playerId ==1) {//Ο Θησέας έχει (-) στο opponentDist γιατί θέλει να αποφύγει τον Μινώταυρο 
		d0=0.4*NearSupplies-0.9*opponentDist;
		}else if (this.playerId == 2) {//Ο Μινώταυρος έχει (+) στο opponentDist γιατί θέλει να πλησιάσει  τον Θησέα 
		d0=0.4*NearSupplies + 0.9*opponentDist;
		}
		
		int[] lastDices = lastDices();//επιστρέγει τις τελευταίες 5 τιμές του ζαριού που επιλέχθηκαν-5 τελευταίες κατευθύνσεις 
		int sameDices=0;//ίδιες κατευθύνσεις
		for(int k =0; k <5; k++) {
			if(lastDices[k] == dice) {//αν δεν εχει 5 τελευταιες κινησει δεν θα μπει γιατι δεν υπαρχει ζαρι -1
			sameDices++;
			}
		 
		}
		if(sameDices>=3 && d0 <=0) {
			d0 = d0-0.1;//αν επαναλαμβάνει μία κατέυθυνση και δεν βλέπει εφόδιο να μην την ξαναπροτιμήσει	
			}
		int[] lastTiles = lastTiles();//τα πλακιδια που κινηθηκε τις τελευταίες θέσεις -αν δεν εχει κινηθει 10 φορές δεν θα μπει ποτε γιατι -1 δεν υπαρχει
		for(int i=0; i<10; i++) {
		if(lastTiles[i] == newTile) {//ωστε να αποφεύγεται η "κυκλική πορεία"
			d0= - 0.25;
		}
		}
		
		d[0] = d0;//τελική πόντοι κίνησης
		d[1] = NearSupplies;//πόντοι σχετικά με ορατότητα εφοδιών
		d[2] = opponentDist;//πόντοι σχετικά με ορατότητα αντιπάλου
		
		return d;
	}
	int getNextMove(int currentPos,int opponentPos) {
	     int newTile= currentPos;//νέο πλακάκι
	     int dice = 0;//η τιμη του ζαριου-κατεύθυνση
	     double[] eval = {0,0,0,0};//τελικός πίνακας
	     double[] eval1 = {-100,-100,-100,-100};
	     double[] eval3 = {-100,-100,-100,-100};
	     double[] eval5 = {-100,-100,-100,-100};
	     double[] eval7 =  {-100,-100,-100,-100};
	     if(board.tiles[currentPos].isUp() == false )  eval1 = evaluate(currentPos,1,opponentPos);
	     if(board.tiles[currentPos].isRight() == false ) eval3 = evaluate(currentPos,3,opponentPos);
	     if(board.tiles[currentPos].isDown() ==false )  eval5 = evaluate(currentPos,5,opponentPos);
	     if(board.tiles[currentPos].isLeft() == false )  eval7 = evaluate(currentPos,7,opponentPos);
	     if(this.playerId == 1) {//o Θησεας κινειται με προτεραιοτητα προς τα πανω και δεξια ώστε να βρει τα εφοδια και ας ειναι προς τα παν ο Μινωταυρος γιατι αν δεν βρει εφόδια δεν θα νικησει
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
	     }else if (this.playerId == 2) {//o Μινωταθρος προτιμα κατω αριστερα γιατι εκει βρισκεται αρχικα  ο Θησεας
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
	     int[] move = move(dice);//κινεί τον παίκτη
	     newTile = move[0];//νέο πλακίδιο
	     path.add(dice);//1ο ζαρι-κατεύθυνση
	     if(eval[0]> 0) point = 10;//αν η κινηση ήταν συμφέρουσα
	     if(eval[0]< 0) point = -10;//αν η κινηση είχε κινδύνους ή δεν θεωρήθηκε κάλη λόγω κυκλικής κίνησης ή επαναλαμβανόμενης κατεύθυνσης
	     path.add(point);//2ο ποντοι-0 αν η κίνηση ήταν αδιαφορη
	     path.add(newTile);//3ο θησεας(μινώταυρος)
	     path.add(opponentPos);//4ο μινωταυρος((θησέας)
	     if(eval[1] == 1  && this.playerId == 1) path.add(1);//5 πηρε ή οχι εφοδιο -για id 2 πάντα 0
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
	   
	     path.add(-1);//10ο(ή 9ο ή 8ο αναλόγα με το αν έβλέπε και εφοδιο και μινωταυρο,ενα απο τα δύο ή τιποτα) για να ξεχωριζω οτι εκλεισε
	     
	     return newTile;//επιστρέφει νέο πλακίδιο
		}
	
	public int[] lastDices() {
		int[] lastDices = {-1,-1,-1,-1,-1};
		int start=-2;
		int j=0;
		
		for(int  i =path.size()-8;  i >=0; i--) { //απο 8 και κάτω γιατι είναι το ελάχιστο μέγεθός εγγραφών κίνησς-πριν δεν υπάρχει πιθανοτητα να βρω -1
			if(path.get(i) == -1) {
				start = i;//βρησκω τη θέση διαχωρισμού 
			}
			if(start != -2 && j<5) {//αν έχει βρεί θέση διαχωρισμού - για 5 κινήσεις
			lastDices[j] = path.get(start+1);//αποθηκεύει στον πίνακα την τιμή του ζαριού που επιλέχθηκε
			start=-2;//αλλαζει την τιμή του start ώστε να ψάξει για εγγραφές προηγούμενης κίνησης
			j++;//αυξάνει το j- μετρά πόσες κινήσεις έχει ελέγξει μέχρι τώρα
			}
			
		}
		
		return lastDices;//επιστρέφει τις 5 τελευταίες τιμές του ζαριού-κατευθύνσεις
		
	}
	public int[] lastTiles() {
		int[] lastTiles = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int start=-2;
		int j=0;
		
		for(int  i =path.size()-8;  i >=0; i--) { //απο 8 και κάτω γιατι είναι το ελάχιστο μέγεθός εγγραφών κίνησς-πριν δεν υπάρχει πιθανοτητα να βρω -1
			if(path.get(i) == -1) {
				start = i;//βρησκω τη θέση διαχωρισμού 
			}
			if(start != -2 && j<10) {//αν έχει βρεί θέση διαχωρισμού-για 10 κινήσεις
			lastTiles[j] = path.get(start+3);//αποθηκεύει στον πίνακα την τιμή του πλακιδιου που επιλέχθηκε
			start=-2;//αλλαζει την τιμή του start ώστε να ψάξει για εγγραφές προηγούμενης κίνησης
			j++;//αυξάνει το j- μετρά πόσες κινήσεις έχει ελέγξει μέχρι τώρα
			}
			
		}
		
		return lastTiles;//επιστρέφει τα 10 τελευταία πλακάκια στα οποία βρέθηκε ο παίκτης
		
	}
	public void statistics() {
		ArrayList<String> statiInf = new ArrayList<String>(); 
		//μεταβλητές που μετράνε πόσες φορές κινήθηκε ο παίκτης προς κάθε κατεύθυνση
		int dice1=0;
		int dice3=0;
		int dice5=0;
		int dice7=0;
		int start = -2;
		int round=0;//ώστε με το την πρώτη κίνηση να αυξήθει 1 
	    if(this.playerId ==2) round =1;//ώστε με το την πρώτη κίνηση να αυξήθει 2 
	
		for(int  i =0;  i <path.size()-8; i++) {//για όσες κινήσεις έχουν γίνει ξάχνει το σήμειο εκκίνησης -1 ώστε να αυξήσει το round 
			//-εκτελείται μέχρι -8 ώστε να μην μετρήσει κα ιτο -1 το τέλους της τελευταίας κίνησης
				if(path.get(i) == -1) {
					start = i;//εχει βρεί το σημειο εκκινησης εγγραφων μιας κινησης
					round++;
				}
				if(start!= -2) {//για κάθε κίνηση που εχει βρεθει
				statiInf.add("Round is "+round);	//ο γυρος γυρο
				if(path.get(start+5) == 1) 	statiInf.add("Tooks prize!");// μηνυμα  για το αν  έχει βρεί εφόδιο 
				if(path.get(start+6) == 1) {//αν έβλεπε εφόδιο 
				statiInf.add("Distance from prize is"+path.get(start+8));//μηνυμα για την αποσταση απο εφόδιο εαν εβλέπε καποιο
				if(path.get(start+7) == 1) statiInf.add("Distance from opponent is"+path.get(start+9));//μηνυμα για την αποσταση απο τον εχθρο αν εβλεπε και εχθρο
				}else if(path.get(start+7) == 1) statiInf.add("Distance from opponent is"+path.get(start+8));//αν εβλεπε εχθρο αι οχι εφοδιο μηνυμα για την αποσταση απο αυτον
				//αναλογα με το ζαρι -αυξάνει την καταλληλη μεταβλητή ώστε να μετρήθουν οι φορες που κινηθηκε προς καθε κατεύθυνση
				if(path.get(start+1) == 1)  dice1++;
				if(path.get(start+1) == 3)  dice3++;
				if(path.get(start+1) == 5)  dice5++;
				if(path.get(start+1) == 7)  dice7++;
				start=-2;
				}
				
			}
		   for(int j =0; j<statiInf.size(); j++) {
			   System.out.println(statiInf.get(j));//εκτυπώνει ολα τα μηνύματα που αποθηκεύθτηκαν παραπανω για κάθε γύρο -στο τελος!
		   }
			System.out.println(this.name+"  moved upwards "+dice1+"times");
			System.out.println(this.name+"  moved to the right "+dice3+"times");
			System.out.println(this.name+"  moved downwaeds "+dice5+"times");
			System.out.println(this.name+"  moved to the left "+dice7+"times");
       
		
	}		
		
}

