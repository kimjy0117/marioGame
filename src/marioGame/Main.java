package marioGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame{
	private JLabel backgroundImg;
	private Map2 map2 = new Map2();
	
	public Main() {
		frameSetting();
		new Map2();
	}

	private void frameSetting() {
	    //프레임 설정
		setTitle("MARIO GAME");
		setSize(1500, 800);
        setResizable(false);// 프레임의 크기를 조절하지 못하게 설정
		setLayout(null); //자유롭게 그림을 그릴 수 있게 해준다.
		backgroundImg = new JLabel(new ImageIcon("images/mario_background.png"));
	    setContentPane(backgroundImg);//배경 이미지 삽입
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // JFrame 가운데 배치
	}

	
    public static void main(String[] args) {
    	new Main();
    }
    
}
