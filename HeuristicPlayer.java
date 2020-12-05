package ds8987_8832;
import java.util.ArrayList;
public class HeuristicPlayer extends Player {
 ArrayList<Integer> path = new ArrayList<Integer>();//���� ��� ���������� ����������� ��� ����� ���� ������ ��� ����������
	
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
	
	double[] evaluate(int currentPos, int dice, int  opponentPos) {//��������� ����������� ��� �������� ������� 
		double[] d = {0.0,0.0,0.0};//������������ ������ ��� ���������� ��� ����������� ��� ������� �����
		double d0=0; //��������� ��� ���� ������� ��� ��������
		double NearSupplies = 0.0; //������ ������� �� �� �� ������ ������ � ���
		double opponentDist = 0.0; //������ ������� �� �� �� ������ ��� �������� � ��� � ���
		int border = 0; // ������- �� �������� � ��� 3 �������� �� ��� 
		double distS = 0;//�������� ������ ��� ������
		double distO = 0;//�������� ������ ��� ������
		boolean wall = false;//�� ������ ������ � ���
		boolean supply = false;//�� ������ ������ � ���
		boolean opponent= false;//�� ������ �������� � ���
		int newTile = 0;
		if(dice == 1 ) {//�� �� ���� ����� 1-������
		int i = currentPos+board.getN();//�� ����� ������� ��� ������-�� ������� ��� ����
		newTile = i;//�� ������� ������ �� ����� �� ��� �������
		//�� �������� 3 �������� ���� �� ���� �� ���� ����� �� tileId ��� 3�� ���� �� ���� ������ �� ������ 2 ����� ��� 2�� ���
		if(currentPos+3*board.getN()< board.getN()*board.getN()  ) border = currentPos+3*board.getN();
		else if(currentPos+2*board.getN() <board.getN()*board.getN()  ) border = currentPos+2*board.getN() ;
		else if(currentPos+board.getN() <board.getN()*board.getN() ) border = currentPos+board.getN() ;
        while(i<=border && wall != true )  {//��� ��������� ����� ��� ���������� ��� ��� ��� ������� ������ ��� ��� �������
	    //������� ��� ���������� �������-���������
         distS++;
	     distO++;
	    for(int s=0; s<board.getS(); s++) {
	    if(supply != true && board.supplies[s].getSupplyTileId() == i ) {//������ �� ���� ������ ������ ��� ���� ���� ������ �� ��� ������� �������
	    NearSupplies= 1.0/distS;//������ ��� ������- ��� ��� ����� ���� ��� ������ ������
	    supply = true;//����� ������
		}
	    }
	    if(  opponent!= true && board.tiles[i].getTileId() == opponentPos) {//������ �� ���� ��� �������� ������ ��� ���� ���� ������ �� ��� ������� �������
	     opponentDist = 1.0/distO;//������ ���  ������ ���������- ��� ��� ����� ���� ��� ������ ������
	     opponent = true;//����� ��� ��������-���������� ��� �� ��� ������ ��� �� ������ �������� ����� ���� ��������� ������� ���� ��� �� ��� ������ ����� �� ���� ������
	    }
	    if(board.tiles[i].isUp() == true) wall = true;//�� �� ������ ������ ���� ���� ������ ��� ���� ������ � while ����� � ������� ����� �� ��� ���������
		i=i+board.getN();//���� ��� �������� �������
		}
		}else if(dice == 5 ) {//�� �� ���� ����� 5- �����
		int i = currentPos-board.getN();//�� ����� ������� ��� ������-�� ������� ��� ����
		newTile = i;
		//�� �������� 3 �������� ���� �� ���� �� ���� ����� �� tileId ��� 3�� ���� �� ���� ������ �� ������ 2 ����� ��� 2�� ���
		if(currentPos-3*board.getN() >= 0   ) border = currentPos-3*board.getN();
		else if(currentPos-2*board.getN() >= 0 ) border = currentPos-2*board.getN()  ;
		else if(currentPos-board.getN() >= 0  ) border = currentPos-board.getN() ;
	   //����� ������ �� �������� ���� ���� �� i-- ��� � while ���������� ��� �� i ����� ���������� ��� �� border
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
		}else if(dice == 3) {//�� �� ���� ����� 3-���������
		int i = currentPos+1;//�� ����� ������� ��� ������-�� ������� ��� �� �����
		newTile = i;
		//����� �� ���� �� border ����� 3 �� �������� 3 ��������  ���������  ������ 2,1 ������� ��� �� ���� ��������.
		if(currentPos+3 <= ((currentPos/board.getN() +1)*board.getN() - 1) ) border = currentPos+3;
		else if(currentPos+2 <= ((currentPos/board.getN() +1)*board.getN() - 1)  ) border = currentPos+2 ;
		else if(currentPos+1<= ((currentPos/board.getN() +1)*board.getN() - 1) ) border = currentPos+1 ;
	   //����� ������ -�� i ��� ��������� ���� 1 ���� �� ������ ���� �� ����� 
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
			
		}else if(dice == 7) {//�� �� ���� ����� 7-����������
		int i = currentPos-1;//�� ����� ������� ��� ������ - �� ������� ��� �� ��������
		newTile = i;
		//����� �� ���� �� border ����� 3 �� �������� 3 ��������  ��������  ������ 2,1 ������� ��� �� ���� ��������.
		if(currentPos-3 >= 0 ) border = currentPos-3;
		else if(currentPos-2 >= 0 ) border = currentPos-2 ;
		else if(currentPos-1 >= 0 ) border = currentPos-1 ;
	//����� ������ -�� i ��� ��������� ���� 1 ���� �� ������ ���� �� �������� 
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
		
		
		if(this.playerId ==1) {//� ������ ���� (-) ��� opponentDist ����� ����� �� �������� ��� ��������� 
		d0=0.4*NearSupplies-0.9*opponentDist;
		}else if (this.playerId == 2) {//� ���������� ���� (+) ��� opponentDist ����� ����� �� ���������  ��� ����� 
		d0=0.4*NearSupplies + 0.9*opponentDist;
		}
		
		int[] lastDices = lastDices();//���������� ��� ���������� 5 ����� ��� ������ ��� �����������-5 ���������� ������������ 
		int sameDices=0;//����� ������������
		for(int k =0; k <5; k++) {
			if(lastDices[k] == dice) {//�� ��� ���� 5 ���������� ������� ��� �� ���� ����� ��� ������� ���� -1
			sameDices++;
			}
		 
		}
		if(sameDices>=3 && d0 <=0) {
			d0 = d0-0.1;//�� ������������� ��� ���������� ��� ��� ������ ������ �� ��� ��� ��������������	
			}
		int[] lastTiles = lastTiles();//�� �������� ��� �������� ��� ���������� ������ -�� ��� ���� ������� 10 ����� ��� �� ���� ���� ����� -1 ��� �������
		for(int i=0; i<10; i++) {
		if(lastTiles[i] == newTile) {//���� �� ����������� � "������� ������"
			d0= - 0.25;
		}
		}
		
		d[0] = d0;//������ ������ �������
		d[1] = NearSupplies;//������ ������� �� ��������� �������
		d[2] = opponentDist;//������ ������� �� ��������� ���������
		
		return d;
	}
	int getNextMove(int currentPos,int opponentPos) {
	     int newTile= currentPos;//��� �������
	     int dice = 0;//� ���� ��� ������-����������
	     double[] eval = {0,0,0,0};//������� �������
	     double[] eval1 = {-100,-100,-100,-100};
	     double[] eval3 = {-100,-100,-100,-100};
	     double[] eval5 = {-100,-100,-100,-100};
	     double[] eval7 =  {-100,-100,-100,-100};
	     if(board.tiles[currentPos].isUp() == false )  eval1 = evaluate(currentPos,1,opponentPos);
	     if(board.tiles[currentPos].isRight() == false ) eval3 = evaluate(currentPos,3,opponentPos);
	     if(board.tiles[currentPos].isDown() ==false )  eval5 = evaluate(currentPos,5,opponentPos);
	     if(board.tiles[currentPos].isLeft() == false )  eval7 = evaluate(currentPos,7,opponentPos);
	     if(this.playerId == 1) {//o ������ �������� �� ������������� ���� �� ���� ��� ����� ���� �� ���� �� ������ ��� �� ����� ���� �� ��� � ���������� ����� �� ��� ���� ������ ��� �� �������
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
	     }else if (this.playerId == 2) {//o ���������� ������� ���� �������� ����� ���� ��������� ������  � ������
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
	     int[] move = move(dice);//����� ��� ������
	     newTile = move[0];//��� ��������
	     path.add(dice);//1� ����-����������
	     if(eval[0]> 0) point = 10;//�� � ������ ���� ����������
	     if(eval[0]< 0) point = -10;//�� � ������ ���� ��������� � ��� ��������� ���� ���� �������� ������� � ����������������� �����������
	     path.add(point);//2� ������-0 �� � ������ ���� ��������
	     path.add(newTile);//3� ������(����������)
	     path.add(opponentPos);//4� ����������((������)
	     if(eval[1] == 1  && this.playerId == 1) path.add(1);//5 ���� � ��� ������ -��� id 2 ����� 0
	     else  path.add(0);
	     if(eval[1] ==0) path.add(0); //6 �� ������ ������
	     else path.add(1);
	     if(eval[2] ==0) path.add(0); //7 �� ������ ��������� 
	     else path.add(1);
	     if(eval[1] == 0.3) path.add(3);//8 �������� ��� ������ �� ������
	     else if(eval[1] == 0.5) path.add(2);
	     else if(eval[1] == 1) path.add(1);
	     if(eval[2] == -0.3) path.add(3);//9o �������� ��� ��������� �� ������-�� ��� ������ ������ ��� ������ ��������� �� ��� 8�
	     else if(eval[2] == -0.5) path.add(2);
	     else if(eval[2] == -1) path.add(1);
	   
	     path.add(-1);//10�(� 9� � 8� ������� �� �� �� ������ ��� ������ ��� ���������,��� ��� �� ��� � ������) ��� �� �������� ��� �������
	     
	     return newTile;//���������� ��� ��������
		}
	
	public int[] lastDices() {
		int[] lastDices = {-1,-1,-1,-1,-1};
		int start=-2;
		int j=0;
		
		for(int  i =path.size()-8;  i >=0; i--) { //��� 8 ��� ���� ����� ����� �� �������� ������� �������� ������-���� ��� ������� ���������� �� ��� -1
			if(path.get(i) == -1) {
				start = i;//������ �� ���� ����������� 
			}
			if(start != -2 && j<5) {//�� ���� ���� ���� ����������� - ��� 5 ��������
			lastDices[j] = path.get(start+1);//���������� ���� ������ ��� ���� ��� ������ ��� ����������
			start=-2;//������� ��� ���� ��� start ���� �� ����� ��� �������� ������������ �������
			j++;//������� �� j- ����� ����� �������� ���� ������� ����� ����
			}
			
		}
		
		return lastDices;//���������� ��� 5 ���������� ����� ��� ������-������������
		
	}
	public int[] lastTiles() {
		int[] lastTiles = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int start=-2;
		int j=0;
		
		for(int  i =path.size()-8;  i >=0; i--) { //��� 8 ��� ���� ����� ����� �� �������� ������� �������� ������-���� ��� ������� ���������� �� ��� -1
			if(path.get(i) == -1) {
				start = i;//������ �� ���� ����������� 
			}
			if(start != -2 && j<10) {//�� ���� ���� ���� �����������-��� 10 ��������
			lastTiles[j] = path.get(start+3);//���������� ���� ������ ��� ���� ��� ��������� ��� ����������
			start=-2;//������� ��� ���� ��� start ���� �� ����� ��� �������� ������������ �������
			j++;//������� �� j- ����� ����� �������� ���� ������� ����� ����
			}
			
		}
		
		return lastTiles;//���������� �� 10 ��������� �������� ��� ����� ������� � �������
		
	}
	public void statistics() {
		ArrayList<String> statiInf = new ArrayList<String>(); 
		//���������� ��� ������� ����� ����� �������� � ������� ���� ���� ����������
		int dice1=0;
		int dice3=0;
		int dice5=0;
		int dice7=0;
		int start = -2;
		int round=0;//���� �� �� ��� ����� ������ �� ������� 1 
	    if(this.playerId ==2) round =1;//���� �� �� ��� ����� ������ �� ������� 2 
	
		for(int  i =0;  i <path.size()-8; i++) {//��� ���� �������� ����� ����� ������ �� ������ ��������� -1 ���� �� ������� �� round 
			//-���������� ����� -8 ���� �� ��� �������� �� ��� -1 �� ������ ��� ���������� �������
				if(path.get(i) == -1) {
					start = i;//���� ���� �� ������ ��������� �������� ���� �������
					round++;
				}
				if(start!= -2) {//��� ���� ������ ��� ���� ������
				statiInf.add("Round is "+round);	//� ����� ����
				if(path.get(start+5) == 1) 	statiInf.add("Tooks prize!");// ������  ��� �� ��  ���� ���� ������ 
				if(path.get(start+6) == 1) {//�� ������ ������ 
				statiInf.add("Distance from prize is"+path.get(start+8));//������ ��� ��� �������� ��� ������ ��� ������ ������
				if(path.get(start+7) == 1) statiInf.add("Distance from opponent is"+path.get(start+9));//������ ��� ��� �������� ��� ��� ����� �� ������ ��� �����
				}else if(path.get(start+7) == 1) statiInf.add("Distance from opponent is"+path.get(start+8));//�� ������ ����� �� ��� ������ ������ ��� ��� �������� ��� �����
				//������� �� �� ���� -������� ��� ��������� ��������� ���� �� ��������� �� ����� ��� �������� ���� ���� ����������
				if(path.get(start+1) == 1)  dice1++;
				if(path.get(start+1) == 3)  dice3++;
				if(path.get(start+1) == 5)  dice5++;
				if(path.get(start+1) == 7)  dice7++;
				start=-2;
				}
				
			}
		   for(int j =0; j<statiInf.size(); j++) {
			   System.out.println(statiInf.get(j));//��������� ��� �� �������� ��� �������������� �������� ��� ���� ���� -��� �����!
		   }
			System.out.println(this.name+"  moved upwards "+dice1+"times");
			System.out.println(this.name+"  moved to the right "+dice3+"times");
			System.out.println(this.name+"  moved downwaeds "+dice5+"times");
			System.out.println(this.name+"  moved to the left "+dice7+"times");
       
		
	}		
		
}

