package ds8987_8832;



import java.lang.Math;
import java.lang.String;

public class Board {

	int N ; //  οι διαστάσεις του τετραγωνικού ταμπλό
	int S ; // ο αριθμός των εφοδίων του ταμπλό
	int W ; // το όριο των τειχών που μπορούν να τοποθετηθούν στο ταμπλό
	Tile[] tiles ; // πίνακας με αντικείμενα τα πλακάκια του ταμπλό
	Supply[] supplies ; // πίνακας με αντικείμενα τα εφόδια του ταμπλό.
	
	
	//Basic Constructor
	public Board() {
	this.N = 0;
	this.S = 0;
	this.W = 0;
	this.tiles = new Tile[0];
	this.supplies = new Supply[0];
	}
	
	//Constructor with arguments
	public Board(int N, int S, int W ) {
	this.N = N;
	this.S= S;
   this.W = W;
   this.tiles = new Tile[W] ;
   this.supplies = new Supply[S] ;
	}
	
	//Constructor with argument of type Board
	public Board(Board b) {
	this(b.getN(), b.getS(), b.getW());
	    for (int i = 0; i < N*N; i++) {
			this.tiles[i] = new Tile(b.getTiles()[i]);
		}
		
		for(int i = 0; i < S; i++) {
			this.supplies[i] = new Supply(b.getSupplies()[i]);
		}		
	}

//Getters-Setters
	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getS() {
		return S;
	}

	public void setS(int s) {
		S = s;
	}

	public int getW() {
		return W;
	}

	public void setW(int w) {
		W = w;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}

	public Supply[] getSupplies() {
		return supplies;
	}

	public void setSupplies(Supply[] supplies) {
		this.supplies = supplies;
	}

	
public boolean rand() {
double r = Math.random();
boolean b = false;
if(r >0.5) {
	b = true;
}
return b;
}
	        
public void createSupply() { // η συνάρτηση που κατανέμει τυχαία  τα εφόδια στο ταμπλό
//δημιουργώ και αρχικοποιώ τα αντικείμενα των εφοδίων του ταμπλό 
	for( int k = 0 ; k <getS(); k++ ) {
		supplies[k] = new Supply( 0,0,0,0);
	}
 //δίνω στο x-y  αρχικά μη αποδεκτές τίμες ώστε να αποδειχθεί οτί η ανάθεση έγινε σωστά για όλα τα εφόδια
	int x =-2;
	int y = -2;
	// για κάθε εφόδιο το id είναι ο αριθμός της σειράς με την οποία εμφανίζεται
	//τα x-y δίνονται τυχαία
	for(int f = 0; f < getS(); f++) {
	 supplies[f].setSupplyId(f);
	 x = (int) (Math.random() * getN());
	 supplies[f].setX(x);
	 y = (int) (Math.random() *getN());
	 supplies[f].setY(y);
    // αν εφόδιο  τοποθετηθεί τυχαία στο κελί (0,0) από όπου ξεκινά ο Θησέας γίνεται νέα ανάθεση x-y
	//ή αν εφόδιο  τοποθετηθεί τυχαία στο κελί (Ndiv2,Ndiv2) από όπου ξεκινά ο Μινώταυρος γίνεται νέα ανάθεση x-y
	 while((x == 0 && y==0) || (x == getN()/2 && y== getN()/2)) {
	 x = (int) (Math.random() *getN());
    supplies[f].setX(x);
    y = (int) (Math.random() *getN());
	 supplies[f].setY(y);
	 }
	 //για κάθε εφόδιο αντιστοιχίζεται το σωστό Id πλακιδίου σύμφωνα με το x-y
	  for(int t = 0; t < getN()*getN(); t++) {
			 if(supplies[f].getX() == tiles[t].getX() && supplies[f].getY() == tiles[t].getY()) {
				supplies[f].setSupplyTileId(t)	 ;
			 }
	  }
	}//αφού έχει τελείωσει η τυχαία ανάθεση των x-y-id για όλα τα εφόδια
	//γίνεται έλεγχος αν έχουν τοποθετηθεί παραπάνω από ένα εφόδια σε ένα πλακάκι
	//και γίνεται τυχαία επιλογή πλακιδίου  μέχρι το πλακάκι αυτό να είναι αποδεκτό
	int id =0;
	for(int f = 0 ; f < getS(); f++) {
		for (int k = 0; k< getS(); k++) {
		// αν βρεί 2 διαφορετικά εφόδια στο ίδιο πλακάκι αναθέτει τυχαία νέο id πλακιδίου που να είναι αποδεκτό
		// υπάρχει και έλεγχος για τα πλακίδια  του Θησέα και του Μινώταυρου
		while(supplies[f].getSupplyTileId() == supplies[k].getSupplyTileId()  && f != k && id !=0 && id !=(getN()*getN()/2)) {
			  id= (int) (Math.random() * (getN()*getN())) ;
			 supplies[f].setSupplyTileId(id);	
		}
		}//για τα τελικά μοναδικά πλακάκια των εφοδίων  αντιστοιχίζονται τα τελικά x-y
		if(supplies[f].getSupplyTileId() == tiles[id].getTileId() ) {
				supplies[f].setX(tiles[id].getX());
				supplies[f].setY(tiles[id].getY());
			 }
		
	}
	
	//supplies[1].setSupplyTileId(15) ;
	//supplies[1].setX(0);
	//supplies[1].setY(1);
	//supplies[2].setSupplyTileId(30) ;
	//supplies[2].setX(0);
	//supplies[2].setY(2);
	
} 

public void createTile() {

int t = 0 ;
int inwalls = 0;// το πλήθος των εσωτερικών τειχών του ταμπλό 
int T = getW() -4*getN();//όριο εσωτερικών τειχών-4*Ν =πλήθος εξωτερικών πλευρών
int[] walls = new int[getN()*getN()] ;//πίνακας με το πλήθος των τειχών κάθε πλακιδίου
//αρχικοποιηση Tile
for( int k = 0 ; k <getN()*getN(); k++ ) {
	tiles[k] = new Tile( 0,0,0,false ,false,false, false);
	walls[k] = 0;
}	
//για κάθε γραμμή και στήλη αντιστοιχίζει τα x-y-id
//η αριθμιση των id γίνεται απο τα αριστερα προς τα δεξια και απο κάτων προς τα πάνω 
//δηλαδη το πιο νοτιοδυτικο κελι είναι το  0-(0,0) και το πιο βορειοανατολικό είναι το (Ν*Ν-1)-(Ν-1,Ν-1)
for(int i = 0; i < getN(); i++) {
 for(int j = 0; j <getN() ; j++ ) {
	  tiles[t].setY(i);
	  tiles[t].setX(j);
	  tiles[t].setTileId(t);
	  
	  
	//εξωτερικά τείχη δυτικά και ανατολικά 
	  if(tiles[t].getX() == 0) {
		tiles[t].setLeft(true);  
		walls[t]++;
	  }else if(tiles[t].getX() == (getN()-1)) {
		tiles[t].setRight(true);
		walls[t]++;
	  }
	//εξωτερικά τείχη νότια και βόρεια
	  if(tiles[t].getY() == 0) {
		tiles[t].setDown(true);  
		walls[t]++;
	  }else if(tiles[t].getY() == (getN()-1)) {
		tiles[t].setUp(true);
		walls[t]++;
	  }
	  t++;
 }
}
//για κάθε ζυγό πλακάκι  γίνεται η τυχαια  κατανομη τειχών και ταυτοχρονα η αναγκαία λόγω γειτνίασης
 for(int  k= (getN()*getN()-1); k >-1; k--) {
	boolean r  ;
	 r= rand();//παίρνει τιμες 0 η 1
	 //αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
	 //(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
	 //εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
	 //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
	 //για κάθε πλακάκι που στα αριστερά του έχει πλακάκι(x>0)
	if(tiles[k].getX() > 0  && walls[k] < 2 && walls[k-1] <2 && r == true && inwalls < (T-1)) {
	  tiles[k].setLeft(true);
	  tiles[k-1].setRight(true);
     walls[k]++;
     walls[k-1]++;
     inwalls = inwalls + 2 ;
	}
	r= rand();
	//αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
	//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
	//εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
   //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
   //για κάθε πλακάκι που στα δεξιά του έχει πλακάκι(x<Ν-1)
	if( tiles[k].getX() < (getN()-1) && walls[k] < 2 && walls[k+1] <2 && r == true  && inwalls < (T-1)) {
	   tiles[k].setRight(true);
	   tiles[k+1].setLeft(true);
	   walls[k]++;
	   walls[k+1]++;
	   inwalls = inwalls + 2 ;
		}
	r = rand();
	//αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
	//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
	//εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
   //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
   //για κάθε πλακάκι που στα νότια του έχει πλακάκι(y>0)
	if( tiles[k].getY() > 0 && walls[k] < 2 && walls[k-getN()] <2 &&r == true  && inwalls < (T-1)) {
	   tiles[k].setDown(true);
	   tiles[k-getN()].setUp(true);
	   walls[k]++;
	   walls[k-getN()]++;
	   inwalls = inwalls + 2 ;
		}
	r = rand();
	//αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
		//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		//εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
	    //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
	    //για κάθε πλακάκι που στα βόρεια του έχει πλακάκι(y<Ν-1)
	if( tiles[k].getY() < (getN()-1) && walls[k] < 2 && walls[k+getN()] <2 && r == true  && inwalls < (T-1)) {
	   tiles[k].setUp(true);
	   tiles[k+getN()].setDown(true);
	   walls[k]++;
	   walls[k+getN()]++;
	   inwalls = inwalls + 2 ;
		}
	k--; //ώστε να εκτελεστεί μονο για ζυγα-με το k-- θα πάει απο τ 24->23 και μετά στην for -> 22 
} 
//για κάθε μονό πλακάκι  γίνεται η τυχαια  κατανoμή τειχών και ταυτοχρονα η αναγκαία λόγω γειτνίασης
 for(int  k= (getN()*getN()-2); k >-1; k--) {
		boolean r ;
		 r= rand();//παίρνει τιμες 0 η 1
		 //αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
		 //(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		 //εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
		 //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
		 //για κάθε πλακάκι που στα αριστερά του έχει πλακάκι(x>0)
		if(tiles[k].getX() > 0  && walls[k] < 2 && walls[k-1] <2 && r == true && inwalls < (T-1)) {
			  tiles[k].setLeft(true);
			  tiles[k-1].setRight(true);
			  walls[k]++;
	 	      walls[k-1]++;
			  inwalls = inwalls + 2 ;
		}
		r= rand();
		//αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
		//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		//εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
	    //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
	    //για κάθε πλακάκι που στα δεξιά του έχει πλακάκι(x<Ν-1)
		if( tiles[k].getX() < (getN()-1) && walls[k] < 2 && walls[k+1] <2 && r == true && inwalls < (T-1)){
		   tiles[k].setRight(true);
		   tiles[k+1].setLeft(true);
		   walls[k]++;
		   walls[k+1]++;
		   inwalls = inwalls + 2 ;
			}
		r = rand();
		//αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
		//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		//εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
	    //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
	    //για κάθε πλακάκι που στα νότια του έχει πλακάκι(y>0)
		if( tiles[k].getY() > 0 && walls[k] < 2 && walls[k-getN()] <2 && r == true && inwalls < (T-1)) {
		   tiles[k].setDown(true);
		   tiles[k-getN()].setUp(true);
		   walls[k]++;
		   walls[k-getN()]++;
		   inwalls = inwalls + 2 ;
			}
		r = rand();
		//αν το συνολικο πληθος επιτρέπει την προσθήκη ακόμα 2 πλακιδίων
			//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
			//εαν το πλακάκι έχει πλήθος τειχών μικρότερο απο 2 και το r =1
		    //αλλα και το γειτονικό εχει λιγότερα απο 2 τείχη άρα επιτρέπεται ακομα ένα
		    //για κάθε πλακάκι που στα βόρεια του έχει πλακάκι(y<Ν-1)
		if( tiles[k].getY() < (getN()-1) && walls[k] < 2 && walls[k+getN()] <2 && r == true && inwalls < (T-1)) {
		   tiles[k].setUp(true);
		   tiles[k+getN()].setDown(true);
		   walls[k]++;
		   walls[k+getN()]++;
		   inwalls = inwalls + 2 ;
			}
		k--; //ώστε να εκτελεστεί μονο για μονά-με το k-- θα πάει απο τ 23->22 και μετά στην for -> 21 
	  }
 
	      
 		
}



//δημιουργεί το ταμπλό καλώντας πρώτα την createTile που είναι προαπαιτούμενο για την createSupply
public void createBoard() {
	createTile();
	createSupply();
}
//δίνει συμβολό σε κάθε πλακάκι
public  String[][] getStringRepresentation(int theseusTile, int minotaurTile){
	String[][] rep = new String[2*getN()+1][getN()] ;
	      //αρχικοποίηση ωστέ να αποδειχθεί ότι η τελική ανάθεση έγινε σωστά
          for( int n = 0; n< (2*getN()+1); n++) {
    		for(int m =0; m<getN(); m++) {  
    			rep[n][m]= "0000";
}
}        
        int i = 0 ;//i= ζυγό για οριζόντια κάτω διάσταση και i=μονό για κάθετο σώμα πλακιδίου
        int j = 0;//στήλες ταμπλό
     
        for(int k =0; k< getN()*getN(); k++) {//για κάθε πλακάκι
        if(tiles[k].isDown()==true ) {//αν έχει κάτω οριζόντιο τείχος
       	 if(tiles[k].getX()== (getN()-1)) {//αν είναι απο τα ανατολικότερα πλακάκια
            rep[i][j] = "+---+"; 
       	 }else {//για ολα τα υπόλοιπα-αν είχε 2 + δεν θα εμφανιζόνταν όπως το ταμπλό της εκφωνησης 
       	 rep[i][j] = "+---";
       	 }
        }else {//αν δεν έχει οριζόντιο τείχος
       	 if(tiles[k].getX()== (getN()-1)) {//αν είναι απο τα ανατολικότερα πλακάκια
             rep[i][j] = "+   +"; 
             }else {
       	 rep[i][j] = "+   "; 
             }
        }
   if(tiles[k].getX()== (getN()-1)) {//ανατολικότερα πλακάκια με σιγουρο εξωτερικό δεξί τείχος
       //αν υπάρχει εφόδιο διορθώνεται  ησυμβολοσειρά στον παρακάτω έλεγχο για τα εφόδια 
	   if(tiles[k].isLeft()==true) {//αν έχει και δεξι και αριστερό τείχος
           rep[i+1][j] = "|   |";	 
          }else {
           rep[i+1][j] = "    |";	 
            }
	    
          for(int f = 0 ; f<getS(); f++) {//ελεγχω αν υπαρχει και εφόδιο
       	if(tiles[k].getTileId() == minotaurTile ) {//αν είναι ο μινοταυρος στο πλακάκι
       	if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
              rep[i+1][j] = "|S"+f+"M"+"|"; 	
           }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
              rep[i+1][j] = " S"+f+"M"+"|";
           } 
       	}else {//αν δεν είναι ο Μινώταυρος στο πλακάκι	
       		if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId() ) {
            rep[i+1][j] = "|S"+f+" |"; 	
           }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
             rep[i+1][j] = " S"+f+" |";
                }
          }
          }
          //αν υπήρχε εφόδιο πιάνεται απο τον Θησέα και για αυτό δεν εμφανίζεται
          if(theseusTile == minotaurTile && theseusTile == tiles[k].getTileId()) {//κερδίζει ο μινώταυρος
           	 if(tiles[k].isLeft()==true ) {
                    rep[i+1][j] = "| "+"M "; 	
                 }else if(tiles[k].isLeft()== false) {
                    rep[i+1][j] = "  "+"M ";
                 } 
             } else if(tiles[k].getTileId() == theseusTile) {//ελέγχω αν είναι ο Θησέας στο πλακάκι
              if(tiles[k].isLeft()==true ) {
               rep[i+1][j] = "| "+"T "; 	
              }else if(tiles[k].isLeft()== false) {
                rep[i+1][j] = "  "+"T ";
              }
          }else if(tiles[k].getTileId() == minotaurTile) {//ελέγχω αν είναι ο Μινώταυρος στο πλακάκι
           	if(tiles[k].isLeft()==true ) {
                  rep[i+1][j] = "| "+"M "; 	
               }else if(tiles[k].isLeft()== false) {
                  rep[i+1][j] = "  "+"M ";
               }  
             }
    }else {//αν δεν ειναί ανατολικότερα τοτε δεν κοιτάμε για δεξί τείχος γιατί αναπαρισταταί απο το δεξιό γειτονικό
         if(tiles[k].isLeft()==true) {//αν έχει αριστερό τείχος
         rep[i+1][j] = "|   ";	 
         }else {
         rep[i+1][j] = "    ";	 
         }
         for(int f = 0 ; f<getS(); f++) {//ελέγχω αν έχει εφόδιο
         if(tiles[k].getTileId() == minotaurTile ) { //αν είναι ο μινοταυρος στο πλακάκι
         if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
           rep[i+1][j] = "|S"+f+"Μ"; 	
          }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
           rep[i+1][j] = " S"+f+"M";
         }
         }else {//αν δεν είναι ο Μινώταυρος στο πλακάκι
         if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
         rep[i+1][j] = "| S"+f; 	
         }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
         rep[i+1][j] = "  S"+f;
          }
        }
        }
         if(theseusTile == minotaurTile && theseusTile == tiles[k].getTileId()) {//κερδίζει ο μινώταυρος
          	 if(tiles[k].isLeft()==true ) {
                   rep[i+1][j] = "| "+"M "; 	
                }else if(tiles[k].isLeft()== false) {
                   rep[i+1][j] = "  "+"M ";
                } 
            } else if(tiles[k].getTileId() == theseusTile) {//ελέγχω αν είναι ο Θησέας στο πλακάκι
             if(tiles[k].isLeft()==true ) {
              rep[i+1][j] = "| "+"T "; 	
             }else if(tiles[k].isLeft()== false) {
               rep[i+1][j] = "  "+"T ";
             }
         }else if(tiles[k].getTileId() == minotaurTile) {//ελέγχω αν είναι ο Μινώταυρος στο πλακάκι
          	if(tiles[k].isLeft()==true ) {
                 rep[i+1][j] = "| "+"M "; 	
              }else if(tiles[k].isLeft()== false) {
                 rep[i+1][j] = "  "+"M ";
              }  
            }
       }
       	 
         j++;//κινείται προς το δεξιότερο πλακάκι
       ////αν το j έχει φτάσει στο Ν τοτε έχει φτάσει στο πιό ανατολικό πλακάκι της γραμμής 
       if(j == getN() ) {
      // το i=i + 2 γίατι ήδη χρησιμοποιήθηκε το i και i+1  για την κατω οριζοντια και κάθετη διασταση των πλακιδίων της γραμμής
       i = i + 2 ; 
       j=0;// μηδενίζει για να αρχισεί η διαδικασία και για την παραπάνω γραμμή
       }
       }
        
       //αναπαριστά την άνω οριζόντια διάσταση των πλακιδίων την τελευταίας γραμμής του ταμπλό
        // γινέται ξεχωριστά γιατί δεν έχει κελί i+1
       for( int w=0; w< (getN()-1); w++) {
       	rep[2*getN()][w]= "+---";
       }
       //το βορειοανατολικότερο κελί
       rep[2*getN()][getN()-1]= "+---+";
       	
        return rep;
}
}

