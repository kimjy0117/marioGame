package marioGame;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class Ending extends JFrame{
	private class UserInfo{
		private long time = 0;
		private int death = 0;
		private String name = "Guest"; 
	}
	private UserInfo info = new UserInfo();
	private myPanel backPanel = new myPanel();
	private String nickName = "Guest";
	private long playTime = 0;
	private int deathCount = 0;
	private int minute = 0;
	private int second = 0;
	private int mSecond = 0;
	private String strMinute;
	private String strSecond;
	private String strMSecond;
	private File file = new File("marioFile.txt");
	private List<UserInfo> listInfo = new ArrayList<UserInfo>();
	private int listLength;
	
	public Ending(long playTime, int deathCount) {
		this.playTime = playTime;
		this.deathCount = deathCount;
		init();
		System.out.println(playTime);
	}
	
	private void init() {
		//프레임 설정
		setTitle("GAME ENDING (USER)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);
		setResizable(false);
		setContentPane(backPanel);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fileWriting();
		fileRead();
	}
	
	private void fileWriting(){	
		int count = 0;
		//닉네임 번호
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while(true) {
				String line = reader.readLine();
				if (line == null)
					break;
				count++;						
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(Long.toString(playTime));
			writer.newLine();
			writer.write(Integer.toString(deathCount));
			writer.newLine();
			nickName += Integer.toString(count/3);
			writer.write(nickName);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void fileRead(){
		int count = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			while(true) {
				String line = reader.readLine();
				if (line == null)
					break;
				
				if(count == 0)
					info.time = Long.parseLong(line);
				
				else if(count == 1)
					info.death = Integer.parseInt(line);
				
				else {
					info.name = line;
					listInfo.add(info);
					info = new UserInfo();
					count = -1;
				}
				count ++;			
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//리스트의 크기가 2이상인 경우 정렬
		if(listInfo.size()>1) {
			bubbleSort();		
		}
	}
	
	private void bubbleSort(){
		//list 정렬
		for(int i=listInfo.size()-1; i>0; i--) {
			for(int j=0; j<i; j++) {
				if(listInfo.get(j).time > listInfo.get(j+1).time) {
					listInfo.add(j, listInfo.get(j+1));
					listInfo.remove(j+2);
				}
				
				else if(listInfo.get(j).time == listInfo.get(j+1).time) {
					if(listInfo.get(j).death > listInfo.get(j+1).death) {
						listInfo.add(j, listInfo.get(j+1));
						listInfo.remove(j+2);
					}
				}
			}
		}
	}
	
	private class myPanel extends JPanel{
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			//배경화면 그리기
			g.setColor(Color.BLACK);
	        g.fillRect(0, 0, 1000, 800);
			
	        //타이틀
			g.setColor(Color.GRAY);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("MARIO GAME", 224, 134);
			g.setColor(Color.WHITE);
			g.drawString("MARIO GAME", 220, 130);
			
			//플레이 시간 계산
			minute = (int)playTime/60000;
			second = (int)playTime/1000-minute*60;
			mSecond = (int) ((playTime/10) % 100);
			
			if(minute < 10)
				strMinute = "0" + Integer.toString(minute);
			else
				strMinute = Integer.toString(minute);
			if(second < 10)
				strSecond = "0" + Integer.toString(second);
			else
				strSecond = Integer.toString(second);
			if(mSecond < 10)
				strMSecond = "0" + Integer.toString(mSecond);
			else
				strMSecond = Integer.toString(mSecond);
			
			//플레이 시간
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.GRAY);
			g.drawString("TIME", 152, 252);
			g.drawString("TIME", 152, 502);
			g.setColor(Color.WHITE);
			g.drawString("TIME", 150, 250);
			g.drawString("TIME", 150, 500);

			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(strMinute+":"+strSecond+":"+strMSecond, 150, 300);
			
			//죽은 횟수
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.GRAY);
			g.drawString("DEATH", 452, 252);
			g.drawString("DEATH", 452, 502);
			g.setColor(Color.WHITE);
			g.drawString("DEATH", 450, 250);
			g.drawString("DEATH", 450, 500);

			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(Integer.toString(deathCount), 495, 300);
			
			//닉네임
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.GRAY);
			g.drawString("NAME", 767, 252);
			g.drawString("NAME", 767, 502);
			g.setColor(Color.WHITE);
			g.drawString("NAME", 765, 250);
			g.drawString("NAME", 765, 500);

			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(nickName, 770, 300);
			
			//랭킹
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.setColor(Color.GRAY);
			g.drawString("------ TOP 5 ------", 351, 431);
			g.setColor(Color.RED);
			g.drawString("------ TOP 5 ------", 350, 430);
			
			listLength = listInfo.size();
			if(listLength > 5)
				listLength = 5;
			
			for(int i=0; i<listLength; i++) {
				playTime = listInfo.get(i).time;
				deathCount = listInfo.get(i).death;
				
				//플레이 시간 계산
				minute = (int)playTime/60000;
				second = (int)playTime/1000-minute*60;
				mSecond = (int) ((playTime/10) % 100);
				
				if(minute < 10)
					strMinute = "0" + Integer.toString(minute);
				else
					strMinute = Integer.toString(minute);
				if(second < 10)
					strSecond = "0" + Integer.toString(second);
				else
					strSecond = Integer.toString(second);
				if(mSecond < 10)
					strMSecond = "0" + Integer.toString(mSecond);
				else
					strMSecond = Integer.toString(mSecond);
				
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.setColor(Color.WHITE);
				if(nickName.equals(listInfo.get(i).name))
					g.setColor(Color.RED);
				g.drawString(Integer.toString(i+1), 100, 550 + i*40);
				g.drawString(strMinute+":"+strSecond+":"+strMSecond, 150, 550 + i*40);
				g.drawString(Integer.toString(deathCount), 495, 550 + i*40);
				g.drawString(listInfo.get(i).name, 770, 550 + i*40);
				
			}
		}
	}
}
