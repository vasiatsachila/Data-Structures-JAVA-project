package ds8987_8832;

public class Game {
	int round;//����� ����������

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
	Game game = new Game();//��������� ��� ����������� ��������
	Board b = new Board(15,4,338);//��������� ��� ����������� ������ �������� �� ��������� �������� ����� ��� ���������
	b.createBoard();// ���������� �� ������ �������� ��� ������������ createTile ��� createSupply
	//Player p1 =  new Player(1,"thiseus",b,0,0,0);//������ -(0,0)
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
	String[][] x = b.getStringRepresentation(0, 112);//������������ �� ������ ������
	int[] moveM = new int[4];
		
	boolean  g= true;
	
	int lastTileM=(b.getN()*b.getN()-1)/2;
	int lastTileT= 0;
	// HeuristicPlayer p3 = new HeuristicPlayer(0);
	while(game.round <200 && theseusTile!=minotaurTile && p1.score<4  ) {
		game.round++;//������ �����
		if(game.round%2==0 ) {//��� ������� ��������-����=����������
		minotaurTile = p2.getNextMove(2,minotaurTile,theseusTile);//����� ��� ���������;
		  //�������� ��������� ������ ������� ��� ������ 
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
		}else if(game.round%2 !=0){//����=������
		  theseusTile = p1.getNextMove(1,theseusTile,minotaurTile);
		   //�������� ��������� ������ ������� ��� ������
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
		   b=p1.board;//���������� ��� ������(�� ��� ������ ������ ���� ���� ����������� ��� �� ������)
		   p1.statistics();
		   }
	 x = b.getStringRepresentation(theseusTile, minotaurTile);	//���������� �� ������ ������ ���� ��������
	 System.out.println("ROUND IS "+game.round);//��������� �� ������
	 
	 String[] y = new String[2*b.getN()+1];//���������� ���� ������ 2*15+1 ���� �� ����������� ���� �� ������� ��� ������
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
	 // � ������� ������� ���������� ��� �� ������
	 // ���� ������ ��������� ���  � ���������� ����� �� ������� �� ������ ��� � ������ ���� ������� 3 ������
	 //��� ���� ��� ������� ��� ���������� �� �� ��������� ������ �� ������� ��� ������ 
	//��� ��� ���� ������� � ������� �� ���� ������!
     if(p1.score==4){
	 System.out.println("Theseus is winner!!!");
     }else if( minotaurTile == theseusTile) {
     
     System.out.println("Minotaur is winner!!!"); 
	}else if(game.round == 200) {//�� round=200 ��� ����� ������ ������ ���� 200���� ���� �� ���� �������� 
	 System.out.println("The game was a draw"+p1.score);  // ��� ��� ��� �������� �������� ��� �� ������� �������.
	
	}	
     
     p1.x = -1;
     p1.statistics();
     
     
  
  
   }

}