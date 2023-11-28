package marioGame;

import javax.swing.*;
import java.awt.*;

public class FlyEnemy extends JLabel {
	//위치
	private int x;
	private int y;

	//캐릭터 이동 속도
	private final int UPDOWNSPEED = 1;
	private int speed;
	
	//캐릭터 이미지
	private ImageIcon flyEnemyR, flyEnemyL;
	
	//플레이어
	private Player player;
	
	//플레이어 위치
	private int player_x;
	private int player_y;
	
	//캐릭터위치 - 플레이어 위치 (거리)
	private double distance_x;
	private double distance_y;
	
	//distance x 비율
	private double distance_x_ratio;
	
	public FlyEnemy(int x, int y, int speed ,Player player) {
		initSetting(x, y, speed, player);
	}

	private void initSetting(int x, int y, int speed, Player player) {
		flyEnemyR = new ImageIcon("images/mario_fly_enemy_R.png");
		flyEnemyL = new ImageIcon("images/mario_fly_enemy_L.png");
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.player = player;
		this.player_x = player.isLocationX();
		this.player_y = player.isLocationY();
		this.distance_x = x - player_x + 20;
		this.distance_y = y - player_y + 20;
		this.distance_x_ratio = Math.abs(distance_x) / (Math.abs(distance_x) + Math.abs(distance_y));

		setIcon(flyEnemyL);
		setSize(40, 40);
		setLocation(x, y);
		
		checkCollision();
		upDown();
		moving();
	}
	
	public void upDown() { 
	new Thread(()->{
		while(player.isStatus()>0) {
			for(int i=0; i<20; i++) {
				y += UPDOWNSPEED;
				setLocation(x, y);
				try {
					//내려오는 속도를 감소시킴
					Thread.sleep(15+i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for(int i=0; i<20; i++) {
				y -= UPDOWNSPEED;
				setLocation(x, y);
				try {
					//올라가는 속도를 감소시킴
					Thread.sleep(15+i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}).start();
	}
	
	private void moving() {
		new Thread(()->{
			while(player.isStatus()>0) {
				this.distance_x = this.x - player.isLocationX() + 20;
				this.distance_y = this.y - player.isLocationY() + 20;
				this.distance_x_ratio = Math.abs(distance_x) / (Math.abs(distance_x) + Math.abs(distance_y));
	
				//x 좌표 수정
				if(distance_x>0) {
					x -= speed * distance_x_ratio;
					setIcon(flyEnemyL);
				}
				else if(distance_x<0) {
					x += speed * distance_x_ratio;
					setIcon(flyEnemyR);
				}

				//y 좌표 수정
				if(distance_y>0) 
					y -= speed * (1-distance_x_ratio);
				else if(distance_y<0)
					y += speed * (1-distance_x_ratio);
				
				setLocation(x, y);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
					System.out.println("충돌");
					try {
						//출돌 후 1초 무적
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
		Rectangle flyEnemyRec = new Rectangle(this.x+10, this.y+10, 20, 20);
		
		if (playerRec.intersects(flyEnemyRec)) {
			return true;
		}
		return false;
	}
}
