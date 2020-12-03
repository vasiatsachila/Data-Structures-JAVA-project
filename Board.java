package ds8987_8832;



import java.lang.Math;
import java.lang.String;

public class Board {

	int N ; //  �� ���������� ��� ������������ ������
	int S ; // � ������� ��� ������� ��� ������
	int W ; // �� ���� ��� ������ ��� ������� �� ������������ ��� ������
	Tile[] tiles ; // ������� �� ����������� �� �������� ��� ������
	Supply[] supplies ; // ������� �� ����������� �� ������ ��� ������.
	
	
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
	        
public void createSupply() { // � ��������� ��� ��������� ������  �� ������ ��� ������
//��������� ��� ���������� �� ����������� ��� ������� ��� ������ 
	for( int k = 0 ; k <getS(); k++ ) {
		supplies[k] = new Supply( 0,0,0,0);
	}
 //���� ��� x-y  ������ �� ��������� ����� ���� �� ���������� ��� � ������� ����� ����� ��� ��� �� ������
	int x =-2;
	int y = -2;
	// ��� ���� ������ �� id ����� � ������� ��� ������ �� ��� ����� �����������
	//�� x-y �������� ������
	for(int f = 0; f < getS(); f++) {
	 supplies[f].setSupplyId(f);
	 x = (int) (Math.random() * getN());
	 supplies[f].setX(x);
	 y = (int) (Math.random() *getN());
	 supplies[f].setY(y);
    // �� ������  ����������� ������ ��� ���� (0,0) ��� ���� ������ � ������ ������� ��� ������� x-y
	//� �� ������  ����������� ������ ��� ���� (Ndiv2,Ndiv2) ��� ���� ������ � ���������� ������� ��� ������� x-y
	 while((x == 0 && y==0) || (x == getN()/2 && y== getN()/2)) {
	 x = (int) (Math.random() *getN());
    supplies[f].setX(x);
    y = (int) (Math.random() *getN());
	 supplies[f].setY(y);
	 }
	 //��� ���� ������ ��������������� �� ����� Id ��������� ������� �� �� x-y
	  for(int t = 0; t < getN()*getN(); t++) {
			 if(supplies[f].getX() == tiles[t].getX() && supplies[f].getY() == tiles[t].getY()) {
				supplies[f].setSupplyTileId(t)	 ;
			 }
	  }
	}//���� ���� ��������� � ������ ������� ��� x-y-id ��� ��� �� ������
	//������� ������� �� ����� ����������� �������� ��� ��� ������ �� ��� �������
	//��� ������� ������ ������� ���������  ����� �� ������� ���� �� ����� ��������
	int id =0;
	for(int f = 0 ; f < getS(); f++) {
		for (int k = 0; k< getS(); k++) {
		// �� ���� 2 ����������� ������ ��� ���� ������� �������� ������ ��� id ��������� ��� �� ����� ��������
		// ������� ��� ������� ��� �� ��������  ��� ����� ��� ��� ����������
		while(supplies[f].getSupplyTileId() == supplies[k].getSupplyTileId()  && f != k && id !=0 && id !=(getN()*getN()/2)) {
			  id= (int) (Math.random() * (getN()*getN())) ;
			 supplies[f].setSupplyTileId(id);	
		}
		}//��� �� ������ �������� �������� ��� �������  ���������������� �� ������ x-y
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
int inwalls = 0;// �� ������ ��� ���������� ������ ��� ������ 
int T = getW() -4*getN();//���� ���������� ������-4*� =������ ���������� �������
int[] walls = new int[getN()*getN()] ;//������� �� �� ������ ��� ������ ���� ���������
//������������ Tile
for( int k = 0 ; k <getN()*getN(); k++ ) {
	tiles[k] = new Tile( 0,0,0,false ,false,false, false);
	walls[k] = 0;
}	
//��� ���� ������ ��� ����� ������������� �� x-y-id
//� �������� ��� id ������� ��� �� �������� ���� �� ����� ��� ��� ����� ���� �� ���� 
//������ �� ��� ����������� ���� ����� ��  0-(0,0) ��� �� ��� ��������������� ����� �� (�*�-1)-(�-1,�-1)
for(int i = 0; i < getN(); i++) {
 for(int j = 0; j <getN() ; j++ ) {
	  tiles[t].setY(i);
	  tiles[t].setX(j);
	  tiles[t].setTileId(t);
	  
	  
	//��������� ����� ������ ��� ��������� 
	  if(tiles[t].getX() == 0) {
		tiles[t].setLeft(true);  
		walls[t]++;
	  }else if(tiles[t].getX() == (getN()-1)) {
		tiles[t].setRight(true);
		walls[t]++;
	  }
	//��������� ����� ����� ��� ������
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
//��� ���� ���� �������  ������� � ������  �������� ������ ��� ���������� � �������� ���� ����������
 for(int  k= (getN()*getN()-1); k >-1; k--) {
	boolean r  ;
	 r= rand();//������� ����� 0 � 1
	 //�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
	 //(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
	 //��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
	 //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
	 //��� ���� ������� ��� ��� �������� ��� ���� �������(x>0)
	if(tiles[k].getX() > 0  && walls[k] < 2 && walls[k-1] <2 && r == true && inwalls < (T-1)) {
	  tiles[k].setLeft(true);
	  tiles[k-1].setRight(true);
     walls[k]++;
     walls[k-1]++;
     inwalls = inwalls + 2 ;
	}
	r= rand();
	//�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
	//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
	//��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
   //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
   //��� ���� ������� ��� ��� ����� ��� ���� �������(x<�-1)
	if( tiles[k].getX() < (getN()-1) && walls[k] < 2 && walls[k+1] <2 && r == true  && inwalls < (T-1)) {
	   tiles[k].setRight(true);
	   tiles[k+1].setLeft(true);
	   walls[k]++;
	   walls[k+1]++;
	   inwalls = inwalls + 2 ;
		}
	r = rand();
	//�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
	//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
	//��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
   //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
   //��� ���� ������� ��� ��� ����� ��� ���� �������(y>0)
	if( tiles[k].getY() > 0 && walls[k] < 2 && walls[k-getN()] <2 &&r == true  && inwalls < (T-1)) {
	   tiles[k].setDown(true);
	   tiles[k-getN()].setUp(true);
	   walls[k]++;
	   walls[k-getN()]++;
	   inwalls = inwalls + 2 ;
		}
	r = rand();
	//�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
		//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		//��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
	    //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
	    //��� ���� ������� ��� ��� ������ ��� ���� �������(y<�-1)
	if( tiles[k].getY() < (getN()-1) && walls[k] < 2 && walls[k+getN()] <2 && r == true  && inwalls < (T-1)) {
	   tiles[k].setUp(true);
	   tiles[k+getN()].setDown(true);
	   walls[k]++;
	   walls[k+getN()]++;
	   inwalls = inwalls + 2 ;
		}
	k--; //���� �� ���������� ���� ��� ����-�� �� k-- �� ���� ��� � 24->23 ��� ���� ���� for -> 22 
} 
//��� ���� ���� �������  ������� � ������  �����o�� ������ ��� ���������� � �������� ���� ����������
 for(int  k= (getN()*getN()-2); k >-1; k--) {
		boolean r ;
		 r= rand();//������� ����� 0 � 1
		 //�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
		 //(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		 //��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
		 //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
		 //��� ���� ������� ��� ��� �������� ��� ���� �������(x>0)
		if(tiles[k].getX() > 0  && walls[k] < 2 && walls[k-1] <2 && r == true && inwalls < (T-1)) {
			  tiles[k].setLeft(true);
			  tiles[k-1].setRight(true);
			  walls[k]++;
	 	      walls[k-1]++;
			  inwalls = inwalls + 2 ;
		}
		r= rand();
		//�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
		//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		//��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
	    //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
	    //��� ���� ������� ��� ��� ����� ��� ���� �������(x<�-1)
		if( tiles[k].getX() < (getN()-1) && walls[k] < 2 && walls[k+1] <2 && r == true && inwalls < (T-1)){
		   tiles[k].setRight(true);
		   tiles[k+1].setLeft(true);
		   walls[k]++;
		   walls[k+1]++;
		   inwalls = inwalls + 2 ;
			}
		r = rand();
		//�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
		//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
		//��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
	    //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
	    //��� ���� ������� ��� ��� ����� ��� ���� �������(y>0)
		if( tiles[k].getY() > 0 && walls[k] < 2 && walls[k-getN()] <2 && r == true && inwalls < (T-1)) {
		   tiles[k].setDown(true);
		   tiles[k-getN()].setUp(true);
		   walls[k]++;
		   walls[k-getN()]++;
		   inwalls = inwalls + 2 ;
			}
		r = rand();
		//�� �� �������� ������ ��������� ��� �������� ����� 2 ���������
			//(inwalls < T-1->T-1+2 inwalls<T+1 ->inwalls<=T )
			//��� �� ������� ���� ������ ������ ��������� ��� 2 ��� �� r =1
		    //���� ��� �� ��������� ���� �������� ��� 2 ����� ��� ����������� ����� ���
		    //��� ���� ������� ��� ��� ������ ��� ���� �������(y<�-1)
		if( tiles[k].getY() < (getN()-1) && walls[k] < 2 && walls[k+getN()] <2 && r == true && inwalls < (T-1)) {
		   tiles[k].setUp(true);
		   tiles[k+getN()].setDown(true);
		   walls[k]++;
		   walls[k+getN()]++;
		   inwalls = inwalls + 2 ;
			}
		k--; //���� �� ���������� ���� ��� ����-�� �� k-- �� ���� ��� � 23->22 ��� ���� ���� for -> 21 
	  }
 
	      
 		
}



//���������� �� ������ �������� ����� ��� createTile ��� ����� �������������� ��� ��� createSupply
public void createBoard() {
	createTile();
	createSupply();
}
//����� ������� �� ���� �������
public  String[][] getStringRepresentation(int theseusTile, int minotaurTile){
	String[][] rep = new String[2*getN()+1][getN()] ;
	      //������������ ���� �� ���������� ��� � ������ ������� ����� �����
          for( int n = 0; n< (2*getN()+1); n++) {
    		for(int m =0; m<getN(); m++) {  
    			rep[n][m]= "0000";
}
}        
        int i = 0 ;//i= ���� ��� ��������� ���� �������� ��� i=���� ��� ������ ���� ���������
        int j = 0;//������ ������
     
        for(int k =0; k< getN()*getN(); k++) {//��� ���� �������
        if(tiles[k].isDown()==true ) {//�� ���� ���� ��������� ������
       	 if(tiles[k].getX()== (getN()-1)) {//�� ����� ��� �� ������������� ��������
            rep[i][j] = "+---+"; 
       	 }else {//��� ��� �� ��������-�� ���� 2 + ��� �� ������������ ���� �� ������ ��� ��������� 
       	 rep[i][j] = "+---";
       	 }
        }else {//�� ��� ���� ��������� ������
       	 if(tiles[k].getX()== (getN()-1)) {//�� ����� ��� �� ������������� ��������
             rep[i][j] = "+   +"; 
             }else {
       	 rep[i][j] = "+   "; 
             }
        }
   if(tiles[k].getX()== (getN()-1)) {//������������� �������� �� ������� ��������� ���� ������
       //�� ������� ������ �����������  ������������� ���� �������� ������ ��� �� ������ 
	   if(tiles[k].isLeft()==true) {//�� ���� ��� ���� ��� �������� ������
           rep[i+1][j] = "|   |";	 
          }else {
           rep[i+1][j] = "    |";	 
            }
	    
          for(int f = 0 ; f<getS(); f++) {//������ �� ������� ��� ������
       	if(tiles[k].getTileId() == minotaurTile ) {//�� ����� � ���������� ��� �������
       	if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
              rep[i+1][j] = "|S"+f+"M"+"|"; 	
           }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
              rep[i+1][j] = " S"+f+"M"+"|";
           } 
       	}else {//�� ��� ����� � ���������� ��� �������	
       		if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId() ) {
            rep[i+1][j] = "|S"+f+" |"; 	
           }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
             rep[i+1][j] = " S"+f+" |";
                }
          }
          }
          //�� ������ ������ �������� ��� ��� ����� ��� ��� ���� ��� �����������
          if(theseusTile == minotaurTile && theseusTile == tiles[k].getTileId()) {//�������� � ����������
           	 if(tiles[k].isLeft()==true ) {
                    rep[i+1][j] = "| "+"M "; 	
                 }else if(tiles[k].isLeft()== false) {
                    rep[i+1][j] = "  "+"M ";
                 } 
             } else if(tiles[k].getTileId() == theseusTile) {//������ �� ����� � ������ ��� �������
              if(tiles[k].isLeft()==true ) {
               rep[i+1][j] = "| "+"T "; 	
              }else if(tiles[k].isLeft()== false) {
                rep[i+1][j] = "  "+"T ";
              }
          }else if(tiles[k].getTileId() == minotaurTile) {//������ �� ����� � ���������� ��� �������
           	if(tiles[k].isLeft()==true ) {
                  rep[i+1][j] = "| "+"M "; 	
               }else if(tiles[k].isLeft()== false) {
                  rep[i+1][j] = "  "+"M ";
               }  
             }
    }else {//�� ��� ����� ������������� ���� ��� ������� ��� ���� ������ ����� ������������� ��� �� ����� ���������
         if(tiles[k].isLeft()==true) {//�� ���� �������� ������
         rep[i+1][j] = "|   ";	 
         }else {
         rep[i+1][j] = "    ";	 
         }
         for(int f = 0 ; f<getS(); f++) {//������ �� ���� ������
         if(tiles[k].getTileId() == minotaurTile ) { //�� ����� � ���������� ��� �������
         if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
           rep[i+1][j] = "|S"+f+"�"; 	
          }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
           rep[i+1][j] = " S"+f+"M";
         }
         }else {//�� ��� ����� � ���������� ��� �������
         if(tiles[k].isLeft()==true && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
         rep[i+1][j] = "| S"+f; 	
         }else if(tiles[k].isLeft()== false && supplies[f].getSupplyTileId() == tiles[k].getTileId()) {
         rep[i+1][j] = "  S"+f;
          }
        }
        }
         if(theseusTile == minotaurTile && theseusTile == tiles[k].getTileId()) {//�������� � ����������
          	 if(tiles[k].isLeft()==true ) {
                   rep[i+1][j] = "| "+"M "; 	
                }else if(tiles[k].isLeft()== false) {
                   rep[i+1][j] = "  "+"M ";
                } 
            } else if(tiles[k].getTileId() == theseusTile) {//������ �� ����� � ������ ��� �������
             if(tiles[k].isLeft()==true ) {
              rep[i+1][j] = "| "+"T "; 	
             }else if(tiles[k].isLeft()== false) {
               rep[i+1][j] = "  "+"T ";
             }
         }else if(tiles[k].getTileId() == minotaurTile) {//������ �� ����� � ���������� ��� �������
          	if(tiles[k].isLeft()==true ) {
                 rep[i+1][j] = "| "+"M "; 	
              }else if(tiles[k].isLeft()== false) {
                 rep[i+1][j] = "  "+"M ";
              }  
            }
       }
       	 
         j++;//�������� ���� �� ��������� �������
       ////�� �� j ���� ������ ��� � ���� ���� ������ ��� ��� ��������� ������� ��� ������� 
       if(j == getN() ) {
      // �� i=i + 2 ����� ��� ��������������� �� i ��� i+1  ��� ��� ���� ��������� ��� ������ �������� ��� ��������� ��� �������
       i = i + 2 ; 
       j=0;// ��������� ��� �� ������� � ���������� ��� ��� ��� �������� ������
       }
       }
        
       //���������� ��� ��� ��������� �������� ��� ��������� ��� ���������� ������� ��� ������
        // ������� ��������� ����� ��� ���� ���� i+1
       for( int w=0; w< (getN()-1); w++) {
       	rep[2*getN()][w]= "+---";
       }
       //�� ������������������� ����
       rep[2*getN()][getN()-1]= "+---+";
       	
        return rep;
}
}

