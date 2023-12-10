package marioGame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameIntro extends JFrame{
	private IntroPanel panel = new IntroPanel();
	private Image introImg = new ImageIcon("images/mario_background.png").getImage();
	private Image startUserImg = new ImageIcon("images/mario_start_user.png").getImage();
	private Image startComImg = new ImageIcon("images/mario_start_com.png").getImage();
	private Image startLearningImg = new ImageIcon("images/mario_start_learning.png").getImage();
	private int btnState = 0;
	
	public GameIntro() {			
		//마우스 리스너
		mouseListener();
		
		//프레임 설정
		setTitle("MARIO GAME");
		setContentPane(panel);
		setSize(1000, 800);
		setResizable(false);// 프레임의 크기를 조절하지 못하게 설정
		setVisible(true);
		setLocationRelativeTo(null); // JFrame 가운데 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class IntroPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
				
			//배경화면 그리기
			g.drawImage(introImg, 0, 0, 1000, 800, this);
			
			//타이틀
			g.setColor(Color.decode("#DAA520"));
			g.setFont(new Font("Arial", Font.BOLD, 100));
			g.drawString("MARIO GAME", 194, 254);
			g.setColor(Color.WHITE);
			g.drawString("MARIO GAME", 190, 250);
			
			//버튼 그리기
			if(btnState == 0) {
				//버튼 1
				g.setColor(Color.GRAY);
		        g.fillRect(363, 373, 250, 80);
				g.drawImage(startUserImg, 360, 370, 250, 80, this);
				//버튼 2
				g.setColor(Color.GRAY);
		        g.fillRect(363, 483, 250, 80);
				g.drawImage(startComImg, 360, 480, 250, 80, this);
				//버튼 3
				g.setColor(Color.GRAY);
		        g.fillRect(363, 593, 250, 80);
				g.drawImage(startLearningImg, 360, 590, 250, 80, this);
			}
			else if(btnState == 1){
				//버튼 1
				g.drawImage(startUserImg, 363, 373, 250, 80, this);
				//버튼 2
				g.setColor(Color.GRAY);
		        g.fillRect(363, 483, 250, 80);
				g.drawImage(startComImg, 360, 480, 250, 80, this);
				//버튼 3
				g.setColor(Color.GRAY);
		        g.fillRect(363, 593, 250, 80);
				g.drawImage(startLearningImg, 360, 590, 250, 80, this);
			}
			else if(btnState == 2) {
				//버튼 1
				g.setColor(Color.GRAY);
		        g.fillRect(363, 373, 250, 80);
				g.drawImage(startUserImg, 360, 370, 250, 80, this);
				//버튼 2
				g.drawImage(startComImg, 363, 483, 250, 80, this);
				//버튼 3
				g.setColor(Color.GRAY);
		        g.fillRect(363, 593, 250, 80);
				g.drawImage(startLearningImg, 360, 590, 250, 80, this);
			}
			else if(btnState == 3) {
				//버튼 1
				g.setColor(Color.GRAY);
		        g.fillRect(363, 373, 250, 80);
				g.drawImage(startUserImg, 360, 370, 250, 80, this);
				//버튼 2
				g.setColor(Color.GRAY);
		        g.fillRect(363, 483, 250, 80);
				g.drawImage(startComImg, 360, 480, 250, 80, this);
				//버튼 3
				g.drawImage(startLearningImg, 363, 593, 250, 80, this);
			}
		}
	}
	
	private void mouseListener() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				if(x>=360 && x<=610) {
					if(y>=370 && y<=450) {
						btnState = 1;
						repaint();
					}
					else if(y>=480 && y<=560) {
						btnState = 2;
						repaint();
					}
					else if(y>=590 && y<=670) {
						btnState = 3;
						repaint();
					}
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				btnState = 0;
				repaint();				
				if(x>=360 && x<=610) {
					if(y>=370 && y<=450) {
						repaint();
						new Main();
						dispose();
					}
					else if(y>=480 && y<=560) {
						new MainCom();
						dispose();
					}
					else if(y>=590 && y<=670) {
						new MainLearning();
						dispose();
					}
				}
			}
		});
	}
}
