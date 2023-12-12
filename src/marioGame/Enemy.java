package marioGame;

import javax.swing.*;
import java.awt.*;

public class Enemy extends JLabel {
	//위치
	private int x;
	private int y;
	
	//가동 시간
	private int time;
	
	//움직임 판별
	private boolean left;
	private boolean right;
	
	//마지막으로 움직인 좌우 판별
	private boolean lastLR;
	
	//몬스터 속도
	private final int SPEED = 2;
	
	//몬스터 이미지
	private ImageIcon enemyR, enemyL;
	
	//플레이어
	private Player player;
	
	//플레이어 위치
	private int player_x;
	private int player_y;

	//움직임 쓰레드
	private Thread leftThread;
	private Thread rightThread;
	
	public Enemy(int x, int y, int time ,Player player) {
		initSetting(x, y, time, player);
	}

	private void initSetting(int x, int y, int time, Player player) {
		enemyR = new ImageIcon("images/mario_enemy_R.png");
		enemyL = new ImageIcon("images/mario_enemy_L.png");
		this.x = x;
		this.y = y;
		this.time = time;
		this.player = player;
		this.player_x = player.isLocationX();
		this.player_y = player.isLocationY();
		left = false;
		right = false;	

		setIcon(enemyR);
		setSize(30, 30);
		setLocation(x, y);
		
		checkCollision();
		moving();
	}
	
	public void left() {
		setIcon(enemyL);
		lastLR = true;
		left = true;
		leftThread = new Thread(()-> {
			while(left) {
				this.x -= SPEED; 
				if(x<=0) {
					left = false;
					this.x += SPEED;
				}
				setLocation(x, y);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					//인터루프트 발생시 쓰레드 중지
					return;
				}
				if(lastLR) {
					setIcon(enemyR);
					lastLR = false;
				}
				else {
					setIcon(enemyL);
					lastLR = true;
				}
			}
		});
		leftThread.start();
	}

	public void right() {
		setIcon(enemyR);
		lastLR = false;
		right = true;
		rightThread = new Thread(()-> {
			while(right) {
				this.x += SPEED;
				if(x>=1500-60) {
					right = false;
					this.x -= SPEED;
				}
				setLocation(x, y);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					//인터루프트 발생시 쓰레드 중지
					return;
				}
				//오른쪽으로 달리는 모션
				if(lastLR) {
					setIcon(enemyL);
					lastLR = false;
				}
				else {
					setIcon(enemyR);
					lastLR = true;
				}
			}
		});
		rightThread.start();
	}	

	private void moving() {
		new Thread(()->{
			while(player.isStatus()>0) {
				//오른쪽으로 이동
				right();
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				right = false;
				
				//왼쪽으로 이동
				left();
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				left = false;
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
					try {
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
		Rectangle enemyRec = new Rectangle(this.x+2, this.y+3, 26, 27);
		
		if (playerRec.intersects(enemyRec)) {
			return true;
		}
		return false;
	}
}
