package marioGame;

import javax.swing.*;

public class Brick extends JLabel{
	//벽돌 위치
	private int x;
	private int y;
	
	//벽돌 크기
	private int size;
	
	//벽돌 상태
	private boolean status;
	
	//벽돌 이미지
	private ImageIcon brick = new ImageIcon("images/mario_brick.png");
	
	//플레이어
	private Player player;
	
	//플레이어 위치
	private int player_x;
	private int player_y;
	
	public Brick(int x, int y, Player player) {
		init(x, y, player);
//		collision();
	}
	
	private void init(int x, int y, Player player) {
		status = true;
		size = 40;
		this.x = x;
		this.y = y;
		this.player = player;
		player_x = player.isLocationX();
		player_y = player.isLocationX();
		setIcon(brick);
		setSize(size, size);
		setLocation(x, y);
		collision();
	}
	
	private void collision() {
		new Thread(()->{
			while(status && player.isStatus()>0){
				//player의 위치 값이 변수에 저장되도록 함
				synchronized(player) {
					player_x = player.isLocationX();
					player_y = player.isLocationY();
				}
				check();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public boolean check(){
		if (status) {
			//player의 위치가 블럭 하단보다 초과할 경우 블럭 제거
			if (player_x+40 > x + 5 && player_x < x + size - 5 && player_y < y + size && player_y > y + 35) {
				player.upInterrupt();
				player.down();
				status = false;
				setSize(0, 0);
			}
			//오른쪽에서 충돌시 right인터루프트 실행
			else if(player_x+40 >= x && player_x+40<x+20 && player_y < y + size - 5 && player_y+40 > y) {
				player.rightInterrupt();
				return true;
			}
			//왼쪽에서 충돌시 left인터루프트 실행
			else if(player_x <= x+size && player_x >= x+20 && player_y < y + size - 5 && player_y+40 > y) {
				player.leftInterrupt();
				return true;
			}
			//윗쪽에서 충돌시 down인터루프트 실행
			else if(player_x+40 > x && player_x < x + size && player_y+40 == y) {
				//down상태일 때만 down을 인터루프트 해준다
				if(player.isDown()) {
					player.downInterrupt();
				}
				return false;
			}
			//플레이어가 발판높이 보다 높으며 발판에서 벗어날 경우 true리턴
			if(player_x+40 < x && player_y+40 <= y || player_x > x && player_y+40 <= y) {
				return true;
			}
			else {
				return true;
			}
		}
		//벽돌의 status가 false인 경우 true리턴
		return true;
	}
}
