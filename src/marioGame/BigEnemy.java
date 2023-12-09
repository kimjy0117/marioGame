package marioGame;

import javax.swing.*;
import java.awt.*;

public class BigEnemy extends JLabel {
	//위치
	private int x;
	private int y;

	//몬스터 이동 속도
	private final int UPDOWNSPEED = 2;
	private int speed;
	
	//몬스터 이미지
	private ImageIcon BigEnemyR, BigEnemyL;
	
	//플레이어
	private Player player;
	
	//플레이어 위치
	private int player_x;
	private int player_y;
	
	//몬스터위치 - 플레이어 위치 (거리)
	private double distance_x;
	
	public BigEnemy(int x, int y, int speed ,Player player) {
		initSetting(x, y, speed, player);
	}

	private void initSetting(int x, int y, int speed, Player player) {
		BigEnemyR = new ImageIcon("images/mario_big_enemy_R.png");
		BigEnemyL = new ImageIcon("images/mario_big_enemy_L.png");
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.player = player;
		this.player_x = player.isLocationX();
		this.player_y = player.isLocationY();
		this.distance_x = x - player_x + 20;

		setIcon(BigEnemyL);
		setSize(120, 120);
		setLocation(x, y);
		
		checkCollision();
		upDown();
	}
	
	public void upDown() { 
	new Thread(()->{
		while(player.isStatus()>0) {		
			for(int i=0; i<70; i++) {
				y -= UPDOWNSPEED;
				moving();
				setLocation(x, y);
				try {
					//올라가는 속도를 감소시킴
					Thread.sleep(4+i/100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for(int i=0; i<70; i++) {
				y += UPDOWNSPEED;
				moving();
				setLocation(x, y);
				try {
					//내려오는 속도를 감소시킴
					Thread.sleep(3-i/100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}).start();
	}
	
	private void moving() {
		this.distance_x = this.x - player.isLocationX() + 20;
		
		//x 좌표 수정
		if(distance_x>0) {
			x -= speed;
			setIcon(BigEnemyL);
		}	
		else if(distance_x<0) {
			x += speed;
			setIcon(BigEnemyR);
		}
	}
	
	private void checkCollision() {
		new Thread(()->{
			while(player.isStatus()>0) {
				//player의 위치 값이 변수에 저장되도록 함
				synchronized(player) {
					player_x = player.isLocationX();
					player_y = player.isLocationY();
				}
				//충돌할 경우 플레이어의 status -1을 해줌
				if(collision()) {
					player.setStatus(-1);
					try {
						//출돌 후 잠시 무적
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private boolean collision() {
		Rectangle playerRec = new Rectangle(player_x+5, player_y+5, 30, 30);
		Rectangle bigEnemyRec = new Rectangle(this.x+20, this.y+40, 80, 80);
		
		if (playerRec.intersects(bigEnemyRec)) {
			return true;
		}
		return false;
	}
}
