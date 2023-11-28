package marioGame;

import javax.swing.*;

public class Pipe extends JLabel{
	//파이프 위치
	private int x;
	private int y;
	
	//파이프 크기
	private int wSize;
	private int hSize;

	//파이프 이미지
	private ImageIcon pipe = new ImageIcon("images/mario_pipe.png");
	
	//플레이어
	private Player player;
	
	//플레이어 위치
	private int player_x;
	private int player_y;
	
	public Pipe(int x, int y, Player player) {
		this.x = x;
		this.y = y;
		this.player = player;
		init();
		collision();
	}
	
	private void collision() {
		new Thread(()->{
			while(player.isStatus()>0) {
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
	
	private void init() {
		wSize = 55;
		hSize = 70;
		player_x = 50;
		player_y = 670;
		setIcon(pipe);
		setSize(wSize, hSize);
		setLocation(x, y);
	}
	
	public boolean check(){
		if (player_x+40 > x && player_x < x + wSize && player_y < y + hSize && player_y > y + 35) {
			player.upInterrupt();
			player.down();
		}
		else if(player_x+40 >= x && player_x+40<x+wSize/2 && player_y < y + hSize && player_y+40 > y) {
			player.rightInterrupt();
			return true;
		}
		else if(player_x <= x+wSize && player_x >= x+wSize/2 && player_y < y + hSize && player_y+40 > y) {
			player.leftInterrupt();
			return true;
		}
		else if(player_x+40 > x+3 && player_x < x + wSize-3 && player_y+40 == y) {
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
}
