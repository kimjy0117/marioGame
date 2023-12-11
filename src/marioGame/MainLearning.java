package marioGame;

import java.awt.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainLearning extends JFrame{
	//배경
	private JLabel backgroundImg;
	//플레이어
	public Player player;
	//아이템
	private Item item;
	//적
	private Enemy enemy1;
	private Enemy enemy2;
	private FlyEnemy flyEnemy;
	//피치
	private Peach peach;
	//장애물
	private Brick brick1;
	private Brick brick2;
	private Brick brick3;
	private Brick brick4;
	private Pipe pipe;
	//맵 판별
	private int map;
	//게임 종료 판별
	private boolean finish;
	//시간
	private long playTime = 0;
	private long startTime = 0;
	//죽음 횟수
	private int deathCount = 0;
	//자동 움직임 시간 리스트에 저장
	private List<Integer> pressRightTimeList = new ArrayList<>();
	private List<Integer> rightSleepTimeList = new ArrayList<>();
	private List<Integer> upSleepTimeList1 = new ArrayList<>();
	private List<Integer> upSleepTimeList2 = new ArrayList<>();
	//랜덤값
	private Random random = new Random();
	private int rightSleepTime = random.nextInt(1000);
	private int pressRightTime = random.nextInt(1000);
	private int upSleepTime1 = random.nextInt(500);
	private int upSleepTime2 = random.nextInt(500);
	
	
	//움직임 쓰레드
	private Thread rightThread;
	private Thread upThread;
	
	//파일 경로
	private File file = new File("marioLearningFile.txt");

	AtomicInteger countRight = new AtomicInteger(0);
	AtomicInteger countUp = new AtomicInteger(0);
	
	public MainLearning() {
		frameSetting();
		map1Init();
		autoMoving();
		timer();
	}
	
	private void frameSetting() {
	    //프레임 설정
		setTitle("MARIO GAME LEARNING");
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
		
		//적 생성
		enemy1 = new Enemy(300, 680, 2300, player);
		enemy2 = new Enemy(750, 680, 2000, player);
		add(enemy1);
		add(enemy2);
		
		//벽돌 생성
		brick1 = new Brick(110, 550, player);
		brick2 = new Brick(200, 550, player);
		brick3 = new Brick(290, 550, player);
		brick4 = new Brick(750, 550, player);
		add(brick1);
		add(brick2);
		add(brick3);
		add(brick4);
		
		//파이프 생성
		pipe = new Pipe(550, 640, player);
		add(pipe);
		
		//아이템 생성
		item = new Item(295, 555, player);
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
		
		//적 생성
		enemy1 = new Enemy(800, 680, 6000, player);
		add(enemy1);
		
		//플라잉 적 생성
		flyEnemy = new FlyEnemy(400, 100, 3, player);
		add(flyEnemy);
		
		//피치 생성
		peach = new Peach(1307, 643);
		add(peach);
		
		//벽돌 생성
		brick1 = new Brick(600, 550, player);
		add(brick1);
		
		//파이프 생성
		pipe = new Pipe(340, 640, player);
		add(pipe);
		
		//아이템 생성
		item = new Item(605, 555, player);
		add(item);	
		
		checkDown();
		checkFinish();
		setVisible(true);
	}
	
    private void reGame() {
        // finish 값을 다시 false로 설정
        finish = false;
        // 기존의 컴포넌트들을 제거
        map = 1;
        //교착상태로 인해 무한 반복할 경우 대비 (400회 이상 죽을 경우 리스트 초기화)
        deathCount++;
        if(deathCount > 400) {
        	initList();
        	deathCount = 0;
        }
        // 타이머 다시 시작
        timer();
        // autoMovint다시 시작
        autoMoving();
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
    
    private void autoMoving() {    	
    	//right
    	rightThread = new Thread(()->{
    		while(map!=3) {
    			//랜덤한 시간 생성후 리스트에 저장
    			rightSleepTime = random.nextInt(1000);
    			pressRightTime = random.nextInt(1000);
    			rightSleepTimeList.add(rightSleepTime);
				pressRightTimeList.add(pressRightTime);
    			
    			try {
					Thread.sleep(rightSleepTimeList.get(countRight.get()));
				} catch (InterruptedException e) {
					return;
				}
    			
    			if(!player.isRight()&&!player.isLeft()) {
    				player.right();
    			}
    			try {
					Thread.sleep(pressRightTimeList.get(countRight.get()));
				} catch (InterruptedException e) {
					return;
				}
    			player.setRight(false);
    			countRight.incrementAndGet();
    		}
    	});
    	rightThread.start();
    	
    	//up
    	upThread = new Thread(()->{
    		while(map!=3) {
    			//랜덤한 시간 생성후 리스트에 저장
    			upSleepTime1 = random.nextInt(500);
    			upSleepTime2 = random.nextInt(500);
    			upSleepTimeList1.add(upSleepTime1);
    			upSleepTimeList2.add(upSleepTime2);
    			
				try {
					Thread.sleep(upSleepTimeList1.get(countUp.get()));
				} catch (InterruptedException e) {
					return;
				}
    			if(!player.isUp()&&!player.isDown()) {
    				player.up();
    			}
    			try {
					Thread.sleep(upSleepTimeList2.get(countUp.get()));
				} catch (InterruptedException e) {
					return;
				}
    			countUp.incrementAndGet();
    		}
    	});
    	upThread.start();
    }
    public void autoMovingInterrupt() {
        if(rightThread != null && rightThread.isAlive()) {
            rightThread.interrupt();
            player.setRight(false);
            
            if (!rightSleepTimeList.isEmpty() && !pressRightTimeList.isEmpty()) {
				//랜덤한 시간 생성후 리스트에 저장
    			rightSleepTime = random.nextInt(1000);
    			pressRightTime = random.nextInt(1000);
    			//현재 index-1에 랜덤값 set
            	rightSleepTimeList.set(countRight.get()-1, rightSleepTime);
                pressRightTimeList.set(countRight.get()-1, pressRightTime);
                
                //랜덤한 시간 생성후 리스트에 저장
    			rightSleepTime = random.nextInt(1000);
    			pressRightTime = random.nextInt(1000);
    			//현재 index에 랜덤값 set
            	rightSleepTimeList.set(countRight.get(), rightSleepTime);
                pressRightTimeList.set(countRight.get(), pressRightTime);
            }
            countRight.set(0);
        }
        
        if(upThread != null && upThread.isAlive()) {
			upThread.interrupt();
			player.setUp(false);
			
			if (!upSleepTimeList1.isEmpty() && !upSleepTimeList2.isEmpty()) {
				//랜덤한 시간 생성후 리스트에 저장
    			upSleepTime1 = random.nextInt(500);
    			upSleepTime2 = random.nextInt(500);
    			//현재 인덱스-1에 랜덤값 set
				upSleepTimeList1.set(countUp.get()-1, upSleepTime1);
				upSleepTimeList2.set(countUp.get()-1, upSleepTime2);
				
				//랜덤한 시간 생성후 리스트에 저장
    			upSleepTime1 = random.nextInt(500);
    			upSleepTime2 = random.nextInt(500);
    			//현재 인덱스에 랜덤값 set
				upSleepTimeList1.set(countUp.get()-1, upSleepTime1);
				upSleepTimeList2.set(countUp.get()-1, upSleepTime2);	
			}
			countUp.set(0);
		}
    }
    
	//벽돌 밖으로 나갈 경우 떨어지게 함
	private void checkDown() {
		new Thread(()->{
			while(!finish && player.isStatus()>0) {
				if(brick1.check() && brick2.check() && brick3.check() && brick4.check() && pipe.check()) {
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
				if(player.isLocationX()>1300 && player.isLocationX()<1305 && player.isLocationY()>500) {
					finish = true;
					player.setStatus(0);
					
					if(map == 1) {
						map2Game();
						return;
					}
					
					else if(map == 2) {
						map = 3;
	                    try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                    //리스트 값 파일에 저장
	                    saveValue();
	                    autoMovingInterrupt();
	                    //리스트 초기화
	                    initList();
	                    //죽은 횟수 초기화
	                    deathCount = 0;
	    	            // 게임 재시작
	    	            reGame();
						return;
					}
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!finish) {
	            autoMovingInterrupt();
	            try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            // 게임 재시작
	            reGame();
			}
		}).start();
	}
	
	private void timer() {
		//현재 시간
		startTime = System.currentTimeMillis();	
		new Thread(()->{
			while(map != 3 && player.isStatus() != 0) {
				playTime = System.currentTimeMillis() - startTime ;
				try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}).start();
	}
	
	private void saveValue() {
		long readTime = 100000000;
		
		//정보(playTime) 읽기
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));	
			String line = reader.readLine();
			readTime = Long.parseLong(line);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//기존 플레이 시간보다 짧을 경우 리스트 저장
		if(playTime < readTime) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(Long.toString(playTime));
				writer.newLine();
				writer.write("rightSleepTimeList");
				writer.newLine();
				for(int i=0; i<rightSleepTimeList.size(); i++) {
					writer.write(Integer.toString(rightSleepTimeList.get(i)));
					writer.newLine();
				}
				writer.write("pressRightTimeList");
				writer.newLine();
				for(int i=0; i<pressRightTimeList.size(); i++) {
					writer.write(Integer.toString(pressRightTimeList.get(i)));
					writer.newLine();
				}
				writer.write("upSleepTimeList1");
				writer.newLine();
				for(int i=0; i<upSleepTimeList1.size(); i++) {
					writer.write(Integer.toString(upSleepTimeList1.get(i)));
					writer.newLine();
				}
				writer.write("upSleepTimeList2");
				writer.newLine();
				for(int i=0; i<upSleepTimeList2.size(); i++) {
					writer.write(Integer.toString(upSleepTimeList2.get(i)));
					writer.newLine();
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initList() {
		//리스트 비워줌
		pressRightTimeList = new ArrayList<>();
		rightSleepTimeList = new ArrayList<>();
		upSleepTimeList1 = new ArrayList<>();
		upSleepTimeList2 = new ArrayList<>();
		
		//리스트에 0 저장
		rightSleepTimeList.add(0);
		pressRightTimeList.add(0);
		upSleepTimeList1.add(0);
		upSleepTimeList2.add(0);
	}
}
