package marioGame;

import javax.swing.*;

public class Player extends JLabel implements Moveable {
	//위치
	private int x;
	private int y;
	
	//움직임 판별
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	//마지막으로 움직인 좌우 판별
	private boolean lastLR;
	
	//좌우 이전 동작 판별
	private boolean lastL;
	private boolean lastR;	
	
	//캐릭터 속도
	private final int SPEED = 6;
	private final int JUMPSPEED = 2;
	
	//캐릭터 상태
	private int status;
	
	//캐릭터 이미지
	private ImageIcon playerR, playerL, playerJR, playerJL, playerRR, playerRL;

	//움직임 쓰레드
	private Thread leftThread;
	private Thread rightThread;
	private Thread upThread;
	private Thread downThread;
	
	public Player() {
		initNormal();
		initSetting();
	}
	
	//캐릭터 리셋
	public void resetPlayer() {
		initNormal();
		initSetting();
	}

	//일반 마리오
	private void initNormal() {
		playerR = new ImageIcon("images/mario_R.png");
		playerL = new ImageIcon("images/mario_L.png");
		playerJR = new ImageIcon("images/mario_J_R.png");
		playerJL = new ImageIcon("images/mario_J_L.png");
		playerRR = new ImageIcon("images/mario_R_R.png");
		playerRL = new ImageIcon("images/mario_R_L.png");
	}
	
	//화이트 마리오
	private void initWhite() {
		playerR = new ImageIcon("images/mario_R_W.png");
		playerL = new ImageIcon("images/mario_L_W.png");
		playerJR = new ImageIcon("images/mario_J_R_W.png");
		playerJL = new ImageIcon("images/mario_J_L_W.png");
		playerRR = new ImageIcon("images/mario_R_R_W.png");
		playerRL = new ImageIcon("images/mario_R_L_W.png");
	}

	private void initSetting() {
		x = 50;
		y = 670;
		
		left = false;
		right = false;
		up = false;
		down = false;	
		lastLR = false;
		lastL = false;
		lastR = false;
		status = 1;

		setIcon(playerR);
		setSize(40, 40);
		setLocation(x, y);
	}
	
	//status에 따라 캐릭터의 이미지 변화
	private void transPlayer() {
		if(status == 0) {
			System.out.println("게임종료");
		}
		else if(status == 1) {
			initNormal();
		}
		else if(status == 2) {
			initWhite();
		}
	}
	
	
	// 현재 위치 return
	public int isLocationX() {
		return x;
	}
	
	public int isLocationY() {
		return y;
	}
	
	// 플레이어 위치 set
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
		
	// 현재 상태 return
	public boolean isLeft() {
		return left;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public boolean isUp() {
		return up;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public int isStatus() {
		return status;
	}
	
	// 현재 상태 set
	public void setLeft(boolean b) {
		left = b;
	}
	
	public void setRight(boolean b) {
		right = b;
	}
	
	public void setUp(boolean b) {
		up = b;
	}
	
	public void setDown(boolean b) {
		down = b;
	}
	
	public void setStatus(int status) {
		this.status += status;
		if(this.status > 2)
			this.status = 2;
		transPlayer();
	}

	@Override
	public void left() {
		setIcon(playerL);
		lastLR = true;
		left = true;
		leftThread = new Thread(()-> {
			while(left && status>0) {
				this.x -= SPEED; 
				if(x<=0) {
					left = false;
					this.x += SPEED;
				}
				setLocation(x, y);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					//인터루프트 발생시 쓰레드 중지
					return;
				}
				//왼쪽으로 달리는 모션
				if(lastL) {
					setIcon(playerL);
					lastL = false;
				}
				else {
					setIcon(playerRL);
					lastL = true;
				}
				//뛰는 상태면 뛰는 모션
				if(up) {
					setIcon(playerJL);
				}
				else if(down) {
					setIcon(playerJL);
				}
			}
			setIcon(playerL);
		});
		leftThread.start();
	}
	
	public void leftInterrupt() {
		if(leftThread != null && leftThread.isAlive()) {
			leftThread.interrupt();
			left = false;
		}
	}

	@Override
	public void right() {
		setIcon(playerR);
		lastLR = false;
		right = true;
		rightThread = new Thread(()-> {
			while(right && status>0) {
				this.x += SPEED;
				if(x>=1500-60) {
					right = false;
					this.x -= SPEED;
				}
				setLocation(x, y);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					//인터루프트 발생시 쓰레드 중지
					return;
				}
				//오른쪽으로 달리는 모션
				if(lastR) {
					setIcon(playerR);
					lastR = false;
				}
				else {
					setIcon(playerRR);
					lastR = true;
				}
				//뛰는 상태면 뛰는 모션
				if(up) {
					setIcon(playerJR);
				}
				else if(down) {
					setIcon(playerJR);
				}
			}
		});
		rightThread.start();
	}
	public void rightInterrupt() {
		if(rightThread != null && rightThread.isAlive()) {
			rightThread.interrupt();
			right = false;
		}
	}

	@Override
	public void up() {
		up = true; 
		upThread = new Thread(()->{
			setIcon(playerJR);
			if(lastLR) {
				setIcon(playerJL);
			}
			else {
				setIcon(playerJR);
			}
			//120px까지 점프 가능
			for(int i=0; i<60; i++) {
				y -= JUMPSPEED;
				setLocation(x, y);
				try {
					//점프하는 속도를 감소시킴
					Thread.sleep(3+i/80);
				} catch (InterruptedException e) {
					//인터루프트 발생시 쓰레드 중지
					return;
				}
			}
			up = false;
			down();		
		});
		upThread.start();
	}
	
	public void upInterrupt() {
		if(upThread != null && upThread.isAlive()) {
			upThread.interrupt();
			up = false;
		}
	}

	@Override
	public void down() {
		if (!down) {
			down = true;
			downThread = new Thread(()->{
				System.out.println("다운 실행");
				for(int i=0; i<300; i++) {
					y += JUMPSPEED;
					//y좌표가 바닥보다 커질 경우 플레이어의 y좌표를 670으로 고정
					if(y >= 670) {
						y = 670;
						setLocation(x, y);
						break;
					}
					setLocation(x, y);
					try {
						//내려오는 속도를 증가시킴
						Thread.sleep(4-i/100);
					} catch (InterruptedException e) {
						//인터루프트 발생시 쓰레드 중지
						return;
					}
				}
				//이전의 방향에 따라 캐릭터 이미지 변경
				if(lastLR) {
					setIcon(playerL);
				}
				else {
					setIcon(playerR);
				}
				down = false;
			});
			downThread.start();
		}
	}
	
	public void downInterrupt() {
		if(downThread != null && downThread.isAlive()) {
			downThread.interrupt();
			down = false;
			//down중간에 인터루프트를 할 경우 착지 상태 이미지를 넣어준다.
			if(lastLR) {
				setIcon(playerL);
			}
			else {
				setIcon(playerR);
			}
		}
	}
}
