package ds8987_8832;



public class Player {
int playerId ;
String name ;
Board board ;
int score ;
int x ;
int y ;

//Basic Constructor
public Player( ) {
playerId = 0 ;
name = " " ;
board =  null ;
score = 0 ;
x = 0 ;
y = 0 ;
}
	
//Constructor with arguments
public Player( int id, String name, Board board, int score, int x, int y) {
this.playerId = id ;
this.name = name ;
this.board = new Board(board) ;
this.score = score ;
this.x = x ;
this.y = y ;
}

//Constructor with argument of type Player
public Player(Player p) {
this.playerId = p.playerId ;
this.name = p.name ;
this.board = p.board ;
this.x = p.x ;
this.y = p.y ;	
}

//Getters-Setters
public int getPlayerId() {
	return playerId;
}

public void setPlayerId(int playerId) {
	this.playerId = playerId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Board getBoard() {
	return board;
}

public void setBoard(Board board) {
	this.board = board;
}

public int getScore() {
	return score;
}

public void setScore(int score) {
	this.score = score;
}

public int getX() {
	return x;
}

public void setX(int x) {
	this.x = x;
}

public int getY() {
	return y;
}

public void setY(int y) {
	this.y = y;
}
	
public int[] move( int dice) {
	//αρχικοποίηση του πίνακα με τιμές μη αποδεκτές ώστε να αποδειχθεί οτι η ανάθεση γίνεται για κάθε περίπτωση
	int[] play = new int[4];
	for(int  m = 0; m < 4; m++ ) {
		play[m] = -1;
	}
	int next_tile = 0;
	
	
	
	
    int tile =0;
    //βρισκεί το  τρέχον πλακάκι του παίκτη 
	for(int t=0; t <board.getN()*board.getN(); t++) {
		 if( board.tiles[t].getX()==getX() && board.tiles[t].getY()== getY()) {
		tile = board.tiles[t].getTileId() ;
		 next_tile = tile;
		 }
	    }
	//για  κίνηση προς τα πάνω
	if(dice==1 ){
	setY(getY()+1);
	next_tile =next_tile+board.getN();//ανεβαίνει στο πάνω πλακάκι
	//για κίνηση προς τα δεξια
	//όμοια λογική με την κίνηση προς τα πάνω
	}else if(dice==3 ){
	 setX(getX()+1);
	 next_tile++;//διπλανο απο δεξιά πλακάκι
    // για κίνηση προς τα κάτω
    //όμοια λογική με την κίνηση προς τα πάνω
	}else if(dice==5 ){
	setY(getY()-1);
	next_tile=next_tile-board.getN();// πάει στο  απο κάτω πλακάκι
    //όμοια λογική με την κίνηση προς τα πάνω
    //κίνηση προς τα αριστερά
    }else if(dice==7 ){
    setX(getX()-1);
   next_tile--;//πηγαίνει στο διπλανό απο τα αριστερά
    }
    
    if(this.playerId==1) {
		for(int s=0; s < board.getS(); s++) {
			  if(board.supplies[s].getSupplyTileId() == next_tile) {
			     board.supplies[s].setX(0);
			     board.supplies[s].setY(0);
			     board.supplies[s].setSupplyTileId(-1);
			     System.out.println(name+" took a prize"+s);
			     play[3] = board.supplies[s].getSupplyId() ;
			     this.score++;
			  }
		     }
    }
	play[0]=next_tile;// νέο-τελικό πλακάκι κίνησης 
	play[1]= getX();// νέα- τελική συντεραγμένη x κίνησης .
	play[2] = getY();// νέα- τελική συντεραγμένη y κίνησης .
	play[3] = dice;
	return play ;
}
	




}

