package ds8987_8832;

public class Play {
	int playerId;
	String name;
	Board board;
	int score;
	int x;
	int y;

	
	public Play() {
		playerId = 0;
		name = "";
		score = 0;
		x = 0;
		y = 0;
	}
	
	public Play(int id, String n, Board b, int s, int px, int py) {
		playerId = id;
		name = n;
		board = b;
		score = s;
		x = px;
		y = py;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public int whereTo(int dice, int id) {
		int res = id;
		if (dice == 1 && board.getTiles()[id].up == false) {
			res = id + board.getN();
		}else if (dice == 3 && board.getTiles()[id].right == false) {
			res = id + 1;
		}else if (dice == 5 && board.getTiles()[id].down == false) {
			res = id - board.getN();
		}else if (dice == 7 && board.getTiles()[id].left == false) {
			res = id - 1;
		}else {
			System.out.println("The number of Dice is: " + dice + ". I can't move there is a wall blocking me");
		}
		return res;
	}
	
	int[] move(int id,int pid, int dice) {
		int[] result = new int[4];
		for (int i = 0; i < result.length; i++) {
			result[i] = 0;
		}
		int rand=0;
		while(rand%2 == 0) {//όσο το rand είναι ζυγός αριθμός υπολόγιζε μέχρι να βρεί αποδεκτό περιττό στο [1,7]
			rand= (int) (Math.random() *7)+1 ; //1-7
			}
	  if(id == 2) dice=rand;
		result[0] = whereTo(dice,id);
		for(int i = 0; i<board.getSupplies().length;i++) {
			if(board.getSupplies()[i].getSupplyTileId() == result[0] && pid == 0) {
				System.out.println("I found the supply number " + result[3] + " in the tile " + result[0]);
				board.supplies[i].setSupplyTileId(-1);
				board.getSupplies()[i].setX(-1);
				board.getSupplies()[i].setY(-1);
				score = score + 1;
				result[3] = board.getSupplies()[i].getSupplyId();
			}
		}
		
		result[1] = result[0] / board.getN();
		result[2] = result[0] % board.getN();
		return result;
	}
	
		
}
	


