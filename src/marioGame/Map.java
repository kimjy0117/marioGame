package marioGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Map extends JFrame{
	private JLabel backgroundImg;
	public Player player;
	private Item item;
	private Enemy enemy1;
	private Enemy enemy2;
	private Enemy enemy3;
	private Brick brick1;
	private Brick brick2;
	private Brick brick3;
	private Brick brick4;
	private Brick brick5;
	private Brick brick6;
	private Brick brick7;
	private Brick brick8;
	private Pipe pipe;
	private boolean finish;
	
	public Map() {
		frameSetting();
		map1Init();
		initListener();
	}
	
	private void map1Init() {
		finish = false;
		
	    //플레이어 생성
		player = new Player();
		add(player);
		
		//적 생성
		enemy1 = new Enemy(300, 680, 2300, player);
		enemy2 = new Enemy(670, 680, 4000, player);
		enemy3 = new Enemy(750, 680, 2000, player);
		add(enemy1);
		add(enemy2);
		add(enemy3);
		
		//벽돌 생성
		brick1 = new Brick(300, 550, player);
		brick2 = new Brick(340, 550, player);
		brick3 = new Brick(380, 550, player);
		brick4 = new Brick(420, 550, player);
		brick5 = new Brick(460, 550, player);
		brick6 = new Brick(320, 430, player);
		brick7 = new Brick(360, 430, player);
		brick8 = new Brick(400, 430, player);
		add(brick1);
		add(brick2);
		add(brick3);
		add(brick4);
		add(brick5);
		add(brick6);
		add(brick7);
		add(brick8);
		
		//파이프 생성
		pipe = new Pipe(550, 640, player);
		add(pipe);
		
		//아이템 생성
		item = new Item(365, 435, player);
		add(item);	
		
		checkDown();
		checkFinish();
		checkStatus();
		setVisible(true);
	}
	
	private void map2Init() {
		finish = false;
		
	    //플레이어 생성
		player = new Player();
		add(player);
		
		//적 생성
		enemy1 = new Enemy(300, 680, 2300, player);
		enemy2 = new Enemy(320, 680, 4000, player);
		add(enemy1);
		add(enemy2);
		
		//벽돌 생성
		brick1 = new Brick(300, 450, player);
		brick2 = new Brick(340, 450, player);
		brick3 = new Brick(380, 450, player);
		brick4 = new Brick(420, 450, player);
		brick5 = new Brick(460, 450, player);
		brick6 = new Brick(320, 230, player);
		brick7 = new Brick(360, 230, player);
		brick8 = new Brick(400, 230, player);
		add(brick1);
		add(brick2);
		add(brick3);
		add(brick4);
		add(brick5);
		add(brick6);
		add(brick7);
		add(brick8);
		
		//파이프 생성
		pipe = new Pipe(700, 640, player);
		add(pipe);
		
		//아이템 생성
		item = new Item(365, 435, player);
		add(item);	
		
		checkDown();
		checkFinish();
		checkStatus();
		setVisible(true);
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
	
    private void restartGame() {
    	System.out.println("restartGame실행");
        // finish 값을 다시 false로 설정
        finish = false;
        // 기존의 컴포넌트들을 제거
        getContentPane().removeAll();
        System.out.println("기존의 컴포넌트 제거");
        map2Init();
        // 프레임을 다시 그리도록 요청
        revalidate();
        repaint();
    }
	
	private void initListener() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {			
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						//왼쪽으로 움직이는 상태가 아닐 시 활성화
						if(!player.isLeft()) {
							player.left();
						}
						break;
						
					case KeyEvent.VK_RIGHT:
						if(!player.isRight()) {
							player.right();
						}
						break;
					
					case KeyEvent.VK_UP:
						if(!player.isUp()&&!player.isDown()) {
							player.up();
						}
						break;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						//키를 때면 왼쪽 움직임 정지
						player.setLeft(false);
						break;
						
						//키를 때면 오른쪽 움직임 정지
					case KeyEvent.VK_RIGHT:
						player.setRight(false);
						break;	
				}
			}
		});
	}
	
	//벽돌 밖으로 나갈 경우 떨어지게 함
	private void checkDown() {
		new Thread(()->{
			while(!finish) {
				if(brick1.check() && brick2.check() && brick3.check() && brick4.check() && brick5.check() && brick6.check() && brick7.check() && brick8.check() && pipe.check()) {
					if(player.isLocationY()<670 && !player.isDown() && !player.isUp()) {
						player.down();
					}
				}
				try {
					//sleep을 통해 down() 여러번 실행되는 것을 막아준다
					Thread.sleep(5);
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}
	
	private void checkFinish() {
		new Thread(()->{
			while(!finish) {
				if(player.isLocationX()>1300 && player.isLocationX()<1305 && player.isLocationY()>630) {
					finish = true;
					player.setStatus(0);
					System.out.println("피니쉬");
					restartGame();
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void checkStatus() {
		new Thread(()->{
			while(player.isStatus()!=0) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("게임 종료");
			//게임 재시작
			restartGame();			
		}).start();
	}
}
