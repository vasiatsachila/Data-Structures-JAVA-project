package ds8987_8832;



public class Tile {
int TileId ; //id πλακιδίου
int x ; // η συντεταγμένη x του πλακιδίου
int y ; //  η συντεταγμένη y του πλακιδίου
boolean up ; // δείχνει αν υπάρχει  άνω τείχος για το πλακίδιο
boolean down ;	//δείχνει αν υπάρχει  κάτω τείχος για το πλακίδιο
boolean left ; // δείχνει αν υπάρχει  αριστερό τείχος για το πλακίδιο
boolean right ;	// δείχνει αν υπάρχει  δεξίο τείχος για το πλακίδιο

//Basic Constructor
public Tile() {
this.TileId = 0 ;
this.x = 0 ; 
this.y = 0 ; 
this.up = false ; 
this.down = false ;
this.left = false ;
this.right = false ;
}

//Constructor with arguments
public Tile(int id, int x, int y ,boolean up ,boolean down, boolean left, boolean right) {
this.TileId = id;
this.x = x ;
this.y = y ; 
this.up = up ;  
this.down = down ;
this.left = left ;
this.right = right ;
}

//Constructor with argument of type Tile
public Tile(Tile t) {
this.TileId = t.TileId ;
this.x = t.x ;
this.y = t.y ;
this.up = t.up ;
this.down = t.down ;
this.left = t.left ;
this.right = t.right ;

}
//Getters-Setters
public int getTileId() {
	return TileId;
}

public void setTileId(int tileId) {
	TileId = tileId;
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

public boolean isUp() {
	return up;
}

public void setUp(boolean up) {
	this.up = up;
}

public boolean isDown() {
	return down;
}

public void setDown(boolean down) {
	this.down = down;
}

public boolean isLeft() {
	return left;
}

public void setLeft(boolean left) {
	this.left = left;
}

public boolean isRight() {
	return right;
}

public void setRight(boolean right) {
	this.right = right;
}



}


