package marioGame;

import javax.swing.*;

public class Peach extends JLabel{
	//이미지 위치
	private int x;
	private int y;

	//이미지
	private ImageIcon peach = new ImageIcon("images/mario_peach.png");
	
	public Peach(int x, int y) {
		this.x = x;
		this.y = y;
		init();
	}
	
	private void init() {
		setIcon(peach);
		setSize(40, 70);
		setLocation(x, y);
	}
}
