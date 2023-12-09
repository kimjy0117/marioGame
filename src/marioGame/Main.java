package marioGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame{
	//배경
	private JLabel backgroundImg;
	//구름
	private Cloud cloud1;
	private Cloud cloud2;
	private Cloud cloud3;
	private Cloud cloud4;
	//플레이어
	public Player player;
	//피치
	private Peach peach;
	//아이템
	private Item item;
	//적
	private Enemy enemy1;
	private Enemy enemy2;
	private Enemy enemy3;
	private Enemy enemy4;
	private Enemy enemy5;
	private FlyEnemy flyEnemy1;
	private FlyEnemy flyEnemy2;
	private FlyEnemy flyEnemy3;
	private FlyEnemy flyEnemy4;
	private FlyEnemy flyEnemy5;
	private BigEnemy bigEnemy;
	//장애물
	private Brick brick1;
	private Brick brick2;
	private Brick brick3;
	private Brick brick4;
	private Brick brick5;
	private Brick brick6;
	private Brick brick7;
	private Brick brick8;
	private Brick brick9;
	private Pipe pipe;
	//맵 판별
	private int map;
	//게임 종료 판별
	private boolean finish;
	//몇 번 죽었는지
	private int deathCount = 0;
	//시간
	private long playTime = 0;
	private long startTime = 0;
	
	public Main() {
		frameSetting();
		initListener();
		map1Init();
		timer();
	}
	
	private void frameSetting() {
	    //프레임 설정
		setTitle("MARIO GAME USer");
		setSize(1500, 800);
        setResizable(false);// 프레임의 크기를 조절하지 못하게 설정
		setLayout(null); //자유롭게 그림을 그릴 수 있게 해준다.
		backgroundImg = new JLabel(new ImageIcon("images/mario_background.png"));
	    setContentPane(backgroundImg);//배경 이미지 삽입
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // JFrame 가운데 배치
	}
	
	private void map1Init() {
		map = 1;
		finish = false;
		
	    //플레이어 생성
		player = new Player();
		add(player);
		
		//구름 생성
		cloud1 = new Cloud(50, 30, 2, player);
		cloud2 = new Cloud(200, 170, 1, player);
		cloud3 = new Cloud(600, 120, 2, player);
		cloud4 = new Cloud(1000, 80, 1, player);
		add(cloud1);
		add(cloud2);
		add(cloud3);
		add(cloud4);
		
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
		brick9 = new Brick(440, 430, player);
		add(brick1);
		add(brick2);
		add(brick3);
		add(brick4);
		add(brick5);
		add(brick6);
		add(brick7);
		add(brick8);
		add(brick9);
		
		//파이프 생성
		pipe = new Pipe(550, 640, player);
		add(pipe);
		
		//아이템 생성
		item = new Item(365, 435, player);
		add(item);	
		
		checkDown();
		checkFinish();
		setVisible(true);
	}
	
	private void map2Init() {
		finish = false;
		
	    //플레이어 생성
		player = new Player();
		add(player);
		
		//구름 생성
		cloud1 = new Cloud(50, 30, 2, player);
		cloud2 = new Cloud(200, 170, 1, player);
		cloud3 = new Cloud(600, 120, 2, player);
		cloud4 = new Cloud(1000, 80, 1, player);
		add(cloud1);
		add(cloud2);
		add(cloud3);
		add(cloud4);
		
		//적 생성
		enemy1 = new Enemy(600, 680, 6000, player);
		enemy2 = new Enemy(400, 680, 3000, player);
		enemy3 = new Enemy(1000, 680, 3000, player);
		enemy4 = new Enemy(1400, 680, 2000, player);
		enemy5 = new Enemy(700, 680, 1500, player);
		add(enemy1);
		add(enemy2);
		add(enemy3);
		add(enemy4);
		add(enemy5);
		
		//플라잉 적 생성
		flyEnemy1 = new FlyEnemy(200, 100, 3, player);
		flyEnemy2 = new FlyEnemy(800, 300, 3, player);
		flyEnemy3 = new FlyEnemy(1200, 600, 2, player);
		add(flyEnemy1);
		add(flyEnemy2);
		add(flyEnemy3);
		
		//벽돌 생성
		brick1 = new Brick(420, 540, player);
		brick2 = new Brick(420, 400, player);
		add(brick1);
		add(brick2);
		
		//파이프 생성
		pipe = new Pipe(340, 640, player);
		add(pipe);
		
		//아이템 생성
		item = new Item(425, 405, player);
		add(item);	
		
		checkDown();
		checkFinish();
		setVisible(true);
	}
	
	private void map3Init() {
		finish = false;
		
	    //플레이어 생성
		player = new Player();
		add(player);

		//구름 생성
		cloud1 = new Cloud(50, 30, 2, player);
		cloud2 = new Cloud(200, 170, 1, player);
		cloud3 = new Cloud(600, 120, 2, player);
		cloud4 = new Cloud(1000, 80, 1, player);
		add(cloud1);
		add(cloud2);
		add(cloud3);
		add(cloud4);
		
		//적 생성
		enemy1 = new Enemy(300, 680, 2000, player);
		enemy2 = new Enemy(800, 680, 6000, player);
		enemy3 = new Enemy(600, 680, 3000, player);
		enemy4 = new Enemy(1000, 680, 4000, player);
		enemy5 = new Enemy(1200, 680, 2000, player);
		add(enemy1);
		add(enemy2);
		add(enemy3);
		add(enemy4);
		add(enemy5);
		
		flyEnemy1 = new FlyEnemy(1100, 650, 2, player);
		flyEnemy2 = new FlyEnemy(320, 100, 3, player);
		flyEnemy3 = new FlyEnemy(800, 400, 2, player);
		flyEnemy4 = new FlyEnemy(900, 500, 3, player);
		flyEnemy5 = new FlyEnemy(1300, 300, 2, player);
		add(flyEnemy1);
		add(flyEnemy2);
		add(flyEnemy3);
		add(flyEnemy4);
		add(flyEnemy5);
		
		//보스 생성
		bigEnemy = new BigEnemy(1200, 600, 1, player);
		add(bigEnemy);
		
		//피치 생성
		peach = new Peach(1307, 643);
		add(peach);
		
		//벽돌 생성
		brick1 = new Brick(50, 570, player);
		brick2 = new Brick(300, 550, player);
		brick3 = new Brick(400, 460, player);
		brick4 = new Brick(500, 380, player);
		brick5 = new Brick(600, 300, player);
		brick6 = new Brick(750, 300, player);
		brick7 = new Brick(850, 380, player);
		brick8 = new Brick(950, 300, player);
		brick9 = new Brick(1100, 300, player);
		add(brick1);
		add(brick2);
		add(brick3);
		add(brick4);
		add(brick5);
		add(brick6);
		add(brick7);
		add(brick8);
		add(brick9);	
		
		//파이프 생성
		pipe = new Pipe(200, 640, player);
		add(pipe);
		
		//아이템 생성
		item = new Item(55, 575, player);
		add(item);	
		
		checkDown();
		checkFinish();
		setVisible(true);
	}
	
    private synchronized void reGame() {
        // finish 값을 다시 false로 설정
        finish = false;
        // 기존의 컴포넌트들을 제거
        map = 1;
        deathCount++;
        getContentPane().removeAll();
        map1Init();
        // 프레임을 다시 그리도록 요청
        revalidate();
        repaint();
    }
    
    private void map2Game() {
    	// finish 값을 다시 false로 설정
        finish = false;
        map = 2;
        // 기존의 컴포넌트들을 제거
        getContentPane().removeAll();
        map2Init();
        // 프레임을 다시 그리도록 요청
        revalidate();
        repaint();
    }
    
    private void map3Game() {
        // finish 값을 다시 false로 설정
        finish = false;
        map = 3;
        // 기존의 컴포넌트들을 제거
        getContentPane().removeAll();
        map3Init();
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
	private synchronized void checkDown() {
		new Thread(()->{
			while(!finish && player.isStatus()>0) {
				if(brick1.check() && brick2.check() && brick3.check() && brick4.check() && brick5.check() && 
						brick6.check() && brick7.check() && brick8.check() && brick9.check() && pipe.check()) {
					if(player.isLocationY()<670 && !player.isDown() && !player.isUp()) {
						player.down();
					}
				}
				try {
					//sleep을 통해 down() 여러번 실행되는 것을 막아준다
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void checkFinish() {
		new Thread(()->{
			while(!finish && player.isStatus()>0) {
				if(player.isLocationX()>1290 && player.isLocationX()<1315 && player.isLocationY()>550) {
					finish = true;
					player.setStatus(0);
					if(map == 1) {
						map2Game();
						return;
					}
					else if(map == 2) {
						map3Game();
						return;
					}
					else if(map == 3) {
						map = 4;
	                    try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                    // 엔딩창 키기
	                    new Ending(playTime, deathCount);
	                    dispose();
						return;
						
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!finish) {
	            // 게임 재시작
	            reGame();
			}
		}).start();
	}
	
	private void timer() {
		//현재 시간
		startTime = System.currentTimeMillis();	
		new Thread(()->{
			while(map != 4) {
				playTime = System.currentTimeMillis() - startTime ;
				try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}).start();
	}	
}
