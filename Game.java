package ds8987_8832;

public class Game {
	int round;//γύρος παιχνιδιού

	//Basic Constructor
	public Game() {
		round = 0;
	}
	
	//Constructor with argument
	public Game(int round) {
		this.round = round;
	}
	//Constructor with argument of type Game
		public Game(Game g) {
			this.round = g.round;
		}
	//Getter-Setter
	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	} 


   public static void main(String[] args) {
	Game game = new Game();//δημιουργώ ένα αντικείμενο παιχνίδι
	Board b = new Board(15,4,338);//δημιουργώ ενα αντικείμενο ταμπλό δίνοντας τα κατάλληλα ορίσματα βάσει της εκφώνησης
	b.createBoard();// δημιουργεί το ταμπλό καλώντας τις συναρτήσσεις createTile και createSupply
	//Player p1 =  new Player(1,"thiseus",b,0,0,0);//Θησέας -(0,0)
	HeristicPlayer p1 = new  HeristicPlayer ();
	HeristicPlayer p2 = new  HeristicPlayer ();
	p1.name="theseus";
	p1.board=b;
	p1.playerId=1;
	p1.x=0;
	p1.y=0;
	p1.score=0;
	p1.name="theseus";
	p1.board=b;
	p1.playerId=1;
	p1.x=0;
	p1.y=0;
	p1.score=0;
	int theseusTile =0;
	p2.name="minotaur";
	p2.board=b;
	p2.playerId=2;
	p2.x=b.getN()/2;
	p2.y=b.getN()/2;
	p2.score=0;
	int minotaurTile= (b.getN()*b.getN()-1)/2;
	String[][] x = b.getStringRepresentation(0, 112);//σχηματοποιει το αρχικό ταμπλό
	int[] moveM = new int[4];
		
	boolean  g= true;
	
	int lastTileM=(b.getN()*b.getN()-1)/2;
	int lastTileT= 0;
	// HeuristicPlayer p3 = new HeuristicPlayer(0);
	while(game.round <200 && theseusTile!=minotaurTile && p1.score<4  ) {
		game.round++;//πρώτος γύρος
		if(game.round%2==0 ) {//για εναλλαξ παιχνιδι-ζυγα=Μινώταυρος
		minotaurTile = p2.getNextMove(2,minotaurTile,theseusTile);//κινεί τον Μινώταυρο;
		  //εκτυπωνω κατάλληλο μήνυμα ανάλογα την κίνηση 
		  if(minotaurTile  == (lastTileM-1) ){
          System.out.println(p2.name+ "moves to the left");
		  }else if(minotaurTile  == (lastTileM+1)) {
		  System.out.println(p2.name+" moves to the right");	
		  }else if(minotaurTile  ==(lastTileM-b.N)) {
		  System.out.println(p2.name+" moves downwards");
		  }else  if(minotaurTile  == (lastTileM+b.N)) {
		  System.out.println(p2.name+" moves upwards");
		  }
		lastTileM = minotaurTile;
		}else if(game.round%2 !=0){//μονα=Θησέας
		  theseusTile = p1.getNextMove(1,theseusTile,minotaurTile);
		   //εκτυπωνω κατάλληλο μήνυμα ανάλογα την κίνηση
		  if(theseusTile  == (lastTileT-1) ){
	          System.out.println(p1.name+ "moves to the left");
			  }else if(theseusTile  == (lastTileT+1)) {
			  System.out.println(p1.name+" moves to the right");	
			  }else if(theseusTile  ==(lastTileT-b.N)) {
			  System.out.println(p1.name+" moves downwards");
			  }else  if(theseusTile  == (lastTileT+b.N)) {
			  System.out.println(p1.name+" moves upwards");
			  }
		   lastTileT= theseusTile;
		   b=p1.board;//αναννεώνει τον ταμπλό(αν έχε πιάσει εφόδιο τότε αυτό διαγράφεται απο το ταμπλό)
		   p1.statistics();
		   }
	 x = b.getStringRepresentation(theseusTile, minotaurTile);	//δημιουργεί το τρέχον ταμπλό προς εκτύπωση
	 System.out.println("ROUND IS "+game.round);//εκτυπώνει το ταμπλό
	 
	 String[] y = new String[2*b.getN()+1];//δημιουργεί εναν πινάκα 2*15+1 ώστε να εμφανιστούν όλες οι γραμμές του ταμπλό
	 for(int w=0; w <2*b.getN()+1; w++) {
			y[w]="";
		}
	for(int i= 2*b.getN(); i>-1; i--) {
		for(int j =0; j<b.getN(); j++) {
		y[i]=y[i]+x[i][j];
		}
		System.out.println(y[i]);	
	}
   }
	 // ο παίκτης κέρδισε μαζεύοντας όλα τα εφόδια
	 // στην ειδική περίπτωση που  ο Μινωταυρος είναι σε πλακάκι με εφόδιο και ο Θησέας έχει μαζέψει 3 εφόδια
	 //και πάει στο πλακάκι του Μινωταυρου με το τελευταίο εφόδιο το μαζεύει και νικάει 
	//άρα για αυτό μπαίνει ο έλεγχός το σκορ πρώτος!
     if(p1.score==4){
	 System.out.println("Theseus is winner!!!");
     }else if( minotaurTile == theseusTile) {
     
     System.out.println("Minotaur is winner!!!"); 
	}else if(game.round == 200) {//το round=200 στο τέλος επειδή μπορεί στον 200οστο γύρο να έχει καλυφθεί 
	 System.out.println("The game was a draw"+p1.score);  // μια απο τις παραπάνω συνθήκες και να υπάρχει νικητής.
	
	}	
     
     p1.x = -1;
     p1.statistics();
     
     
  
  
   }

}