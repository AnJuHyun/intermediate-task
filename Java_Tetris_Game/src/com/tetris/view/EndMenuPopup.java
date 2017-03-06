package com.tetris.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import com.tetris.util.ScreenUtil;

//테트리스 마친 후의 프레임 띄워주는 클래스
@SuppressWarnings("serial")
public class EndMenuPopup extends JDialog {
	JButton ranking;
	JButton mainMenu;
	JButton exit;

	public EndMenuPopup() {
		endFrameSetting();
		endButton();
		endButtonEvents();
	}

//	테트리스 윈도우 프레임
	private void endFrameSetting() {
		setTitle("TETRIS END MENU");
		setModal(true);
		setLayout(null);
		setSize(500, 400);
		setLocation(ScreenUtil.getCenterPosition(this));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

//	프레임에 내 버튼
	private void endButton() {
		ranking = new JButton("R A N K I N G");
		mainMenu = new JButton("M A I N M E N U");
		exit = new JButton("E X I T");
		
		ranking.setBounds(150, 220, 200, 30);
		mainMenu.setBounds(150, 250, 200, 30);
		exit.setBounds(150, 280, 200, 30);
		
		add(ranking);
		add(mainMenu);
		add(exit);
	}

//	버튼 이벤트	
	private void endButtonEvents() {
		ranking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { }
		
		});
		
		mainMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				dispose() : 해당 창만을 종료시키는 메소드(다른 윈도우 실행 창들이 종료되지 않도록)
				dispose();
				
				new StartMenuPopup().setVisible(true);
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

//	프레임 내에 T_T 문구 입혀 내보냄
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// T
		g.fill3DRect(145, 90, 20, 20, true);
		g.fill3DRect(165, 90, 20, 20, true);
		g.fill3DRect(185, 90, 20, 20, true);
		g.fill3DRect(165, 110, 20, 20, true);
		g.fill3DRect(165, 130, 20, 20, true);
		g.fill3DRect(165, 150, 20, 20, true);
		g.fill3DRect(165, 170, 20, 20, true);

		// _
		g.fill3DRect(225, 170, 20, 20, true);
		g.fill3DRect(245, 170, 20, 20, true);
		g.fill3DRect(265, 170, 20, 20, true);

		// T
		g.fill3DRect(305, 90, 20, 20, true);
		g.fill3DRect(325, 90, 20, 20, true);
		g.fill3DRect(345, 90, 20, 20, true);
		g.fill3DRect(325, 110, 20, 20, true);
		g.fill3DRect(325, 130, 20, 20, true);
		g.fill3DRect(325, 150, 20, 20, true);
		g.fill3DRect(325, 170, 20, 20, true);
	}
}