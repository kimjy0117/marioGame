package marioGame;

import javax.swing.*;
import java.awt.*;

public class Item extends JLabel{
	//위치
	private int x;
	private int y;
	
	//아이템 이미지
	private ImageIcon item;
	
	//아이템 상태
	private boolean status;
	
	//플레이어
	private Player player;
	
	//플레이어 위치
	private int player_x;
	private int player_y;
	
	public Item(int x, int y, Player player) {
		initSetting(x, y, player);
	}

	private void initSetting(int x, int y, Player player) {
		item = new ImageIcon("images/mario_item.png");	
		status = true;
		this.x = x;
		this.y = y;
		this.player = player;
		this.player_x = player.isLocationX();
		this.player_y = player.isLocationY();

		setIcon(item);
		setSize(30, 30);
		setLocation(x, y);
		
		checkCollision();
	}
	private void checkCollision() {
		new Thread(()->{
			while(status && player.isStatus()>0) {
				//player의 위치 값이 변수에 저장되도록 함
				synchronized(player) {
					player_x = player.isLocationX();
					player_y = player.isLocationY();
				}
				//충돌할 경우 플레이어의 status +1을 해줌
				if(collision()) {
					status = false;
					setSize(0, 0);
					player.setStatus(+1);
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private boolean collision() {
		Rectangle playerRec = new Rectangle(player_x+5, player_y+5, 30, 30);
		Rectangle itemRec = new Rectangle(this.x, this.y, 30, 30);
		
		if (playerRec.intersects(itemRec)) {
			return true;
		}
		return false;
	}
}
