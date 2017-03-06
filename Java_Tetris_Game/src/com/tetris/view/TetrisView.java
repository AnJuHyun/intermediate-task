package com.tetris.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import com.tetris.constant.Constant;
import com.tetris.controller.TetrisManager;
import com.tetris.util.ScreenUtil;

//테트리스 구동되는 프레임
@SuppressWarnings("serial")
public class TetrisView extends JFrame {
	final Object object = new Object();
	
	static final int REACHED_COUNT = 2;
	
	TetrisManager tetrisManager;
	Constant.ProcessType processType;
	Constant.GameStatus gameStatus;
	int direction;
	int processReachedCaseCount;
	boolean isKeyPressed;
	long timeMilliSecond;
	
	public TetrisView () {
		tetrisFrameSetting();
		tetrisButton();
		tetrisButtonEvents();
	}
	
	private void tetrisFrameSetting() {
		setTitle("TETRIS - ING...");
		getContentPane().setLayout(null);
		setSize(800, 700);
		setLocation(ScreenUtil.getCenterPosition(this));
		setResizable(false);
	}
	
	private void tetrisButton() {
		
//		TetrisManager 클래스를 가장 낮은 스피드로 실행시킴
		tetrisManager = new TetrisManager(Constant.MIN_SPEED_LEVEL);
		
//		스레드 생성
		new Thread(new Runnable() {
			@Override
			public void run() {
//				start() : 스레드 생성
//						  run() 메소드가 호출됨 => 메인메소드로 회귀
//						  run()에서 실행된 스레드 / start()에 의한 스레드 총 2개가 되는 것임
				start();
				
				gameStatus = Constant.GameStatus.PLAYING;
				isKeyPressed = false;
				timeMilliSecond = System.currentTimeMillis();
			
//				게임이 끝난 경우
				while (gameStatus != Constant.GameStatus.END) {
//					게임 상태가 일시중지인 경우
					if (gameStatus == Constant.GameStatus.PAUSE) {
//						synchronized : 스레드 동기화(해당 작업이 끝날 때까지는 타 스레드가 사용할 수 없도록)
//						1. 특정 객체 lock : synchronized(객체의 참조변수) { }
//						2. 메소드 lock : public synchronized void method() { }
						synchronized (object) {
							try {
//								해당 스레드가 끝날 때까지 객체를 대기시킴
								object.wait();
							} catch (InterruptedException e) { }
						}
					}
					
					processType = Constant.ProcessType.AUTO;
					direction = Constant.Direction.DOWN;
				
					while (true) {
//						mIsKeyPressed는 default가 false임
//						true일 경우 false로 변환
						if (isKeyPressed) {
							isKeyPressed = false;
							
							break;
						}
						
//						mIsKeyPressed가 true가 아니고 현재시간 - 설정시간(현재시간으로 위에서 설정했음)이 설정시간보다 큰 경우
						if (!isKeyPressed && System.currentTimeMillis() - timeMilliSecond > getDownMilliSecond()) {
//							설정 초기화
							processType = Constant.ProcessType.AUTO;
							direction = Constant.Direction.DOWN;
							timeMilliSecond = System.currentTimeMillis();
							
							break;
						}
						
						try {
//							sleep() : 동기화된 다중 스레드를 시간 단위로 중지시키는 메소드
//									  파라미터로 천분의 1초를 받음
//							(Object를 중지시키는 메소드 : wait() / 반복시 시간을 통해 중지 : sleep())
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					process(processType, direction);
				}
			}
		}).start();
	}
	
//	키보드를 눌렀을 때 발생하는 이벤트
/* <Key 관련 이벤트>
 * 1. keyPressed : key 누르는 순간 발생
 * 2. keyTyped : 누른 key를 떼는 순간 발생(유니코드 키의 경우 추가 이벤트 발생할 수 있음)
 * 3. keyReleased : 누른 key를 떼는 순간
 *  - 실행 순서 : keyPressed -> keyTyped -> keyReleased
 */
	private void tetrisButtonEvents() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }

//			키 누르는 순간 발생하는 이벤트
			@Override
			public void keyPressed(KeyEvent e) {
//				위
				if (e.getKeyCode() == Constant.keyCode.UP) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.UP;
//				아래
				} else if (e.getKeyCode() == Constant.keyCode.DOWN) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.DOWN;
					timeMilliSecond = System.currentTimeMillis();
//				왼쪽
				} else if (e.getKeyCode() == Constant.keyCode.LEFT) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.LEFT;
//				오른쪽
				} else if (e.getKeyCode() == Constant.keyCode.RIGHT) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECTION;
					direction = Constant.Direction.RIGHT;
//				스페이스바
				} else if (e.getKeyCode() == Constant.keyCode.SPACE_BAR) {
					isKeyPressed = true;
					processType = Constant.ProcessType.DIRECT_DOWN;
					timeMilliSecond = System.currentTimeMillis();
//				ESC
				} else if (e.getKeyCode() == Constant.keyCode.ESC) {
					isKeyPressed = true;
					processType = Constant.ProcessType.AUTO;
					timeMilliSecond = System.currentTimeMillis();
					
					pause();
				}
			}
		});
		
//		프레임 창을 끌 때 발생하는 이벤트
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				end();
			}
			
		});
	}
	
	public void start() {
//		repaint() : 해당 컴포넌트의 paint() 메소드를 호출한 뒤 새로고침 하는 메소드
		repaint();
	}
	
	public void process(Constant.ProcessType processType, int direction) {
		System.out.println("console> TetrisView process processType : " + processType +
								", direction : " + direction);
		
		if (processType == Constant.ProcessType.DIRECTION) {
			tetrisManager.changeBoardByDirection(direction);
		} else if (processType == Constant.ProcessType.DIRECT_DOWN) {
			tetrisManager.processDirectDown();
		} else if (processType == Constant.ProcessType.AUTO) {
			tetrisManager.changeBoardByAuto();
		}
		
		
		if (tetrisManager.isReachedToBottom()) {
			if (processType == Constant.ProcessType.DIRECT_DOWN) {
				System.out.println("console> TetriwView isReachedToBottom DIRECT_DOWN");
				
				processReachedCaseCount = 0;
				
				if (tetrisManager.processReachedCase() == Constant.GameStatus.END) {
					end();
					
					new EndMenuPopup().setVisible(true);
					
					return;
				}
			} else {
				if (processReachedCaseCount == REACHED_COUNT) { 
					System.out.println("console> TetriwView isReachedToBottom PROCESS_REACHED_CASE_COUNT");
					
					if (tetrisManager.processReachedCase() == Constant.GameStatus.END) {
						end();
					
						new EndMenuPopup().setVisible(true);
						
						return;
					}
					
					processReachedCaseCount = 0;
					
				} else {
					processReachedCaseCount++;
				}
			}
		}
		
//		repaint() : 해당 컴포넌트의 paint() 메소드를 호출한 뒤 새로고침 하는 메소드
//		paint() : repaint() 메소드에 의해 호출되고 테트리스 블럭 떨어지는 틀 구성하는 메소드
		repaint();
		
		tetrisManager.processDeletingLines(getGraphics());
	}
	
//	끝남
	public void end() {
		gameStatus = Constant.GameStatus.END;
		
		System.out.println("console> TetrisView end gameStatus : " + gameStatus);
		
		dispose();
	}
	
//	일시정지
	public void pause() {
		gameStatus = Constant.GameStatus.PAUSE;
		
		System.out.println("console> TetrisView pause gameStatus : " + gameStatus);
		
		new PauseMenuPopup(this).setVisible(true);
		
//		synchronized 키워드를 이용하여 멀티 스레드가 동작하는 동안 해당 객체를 동기화시킴
//		(현재 테트리스 게임이 동작하지 않고 있기 때문에 main메소드는 돌아가지만 테트리스는 돌아가지 않음)
//		=> 테트리스 동기화
		synchronized (object) {
			object.notify();
		}
		
		if (gameStatus != Constant.GameStatus.END) {

			gameStatus = Constant.GameStatus.PLAYING;
		}
	}
	
//	프레임에 그래픽 입혀 출력(테트리스 게임 검정 틀)
	@Override
	public void paint(Graphics g) {
		Image buffer = createImage(getWidth(), getHeight());
		Graphics graphics = buffer.getGraphics();
		
		graphics.setColor(Color.black);
		
		System.out.println("console> TetrisView paint getColor : " + graphics.getColor());
		
		Font font = graphics.getFont();
		
		graphics.setFont(new Font(font.getName(), Font.BOLD, 30));
		tetrisManager.print(graphics);
		g.drawImage(buffer, 0, 0, this);
	}

	

	



	public long getDownMilliSecond() {
		return tetrisManager.getDownMilliSecond();
	}
}