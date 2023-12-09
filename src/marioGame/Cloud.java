package marioGame;

import javax.swing.*;
import java.awt.*;

public class Cloud extends JLabel {
	//위치
	private int x;
	private int y;

	//구름 이동 속도
	private int speed;
	
	//구름 이미지
	private ImageIcon cloud;
	
	//플레이어 위치
	private Player player;
	
	public Cloud(int x, int y, int speed, Player player) {
		initSetting(x, y, speed, player);
	}

	private void initSetting(int x, int y, int speed, Player player) {
		cloud = new ImageIcon("images/mario_cloud.png");
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.player = player;
		
		setIcon(cloud);
		setSize(140, 83);
		setLocation(x, y);
		
		moving();
	}

	private void moving() {
		new Thread(()->{
			while(player.isStatus()>0) {
				if(this.x > 1500)
					this.x = -140;
				this.x += speed;
				setLocation(x, y);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
}
