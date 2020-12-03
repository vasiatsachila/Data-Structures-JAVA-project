package ds8987_8832;


public class Supply {
int supplyId ; //id εφοδίου
int x ; //η συντεταγμένη x του εφοδίου
int y ; //η συντεταγμένη y του εφοδίου
int supplyTileId ; // το id του πλακιδίου στο οποίο είναι το εφόδιο

//Basic Constructor
public Supply() {
this.supplyId = 0 ;
this.x = 0 ;
this.y = 0 ;
this.supplyTileId = 0 ;	
}

//Constructor with arguments
public Supply(int supplyId, int x, int y,int supplyTileId) {
	this.supplyId = supplyId;
	this.x = x;
	this.y = y;
	this.supplyTileId = supplyTileId;
}	

//Constructor with argument of type Supply
public Supply(Supply s) {
	this.supplyId = s.supplyId ;
	this.x = s.x;
	this.y = s.y;
	this.supplyTileId = s.supplyTileId;
}

//Getters-Setters
public int getSupplyId() {
	return supplyId;
}

public void setSupplyId(int supplyId) {
	this.supplyId = supplyId;
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

public int getSupplyTileId() {
	return supplyTileId;
}

public void setSupplyTileId(int supplyTileId) {
	this.supplyTileId = supplyTileId;
}		


}



