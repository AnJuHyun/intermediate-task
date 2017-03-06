package com.tetris.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import com.tetris.util.ScreenUtil;

//처음 테트리스 프레임 띄워주는 클래스
@SuppressWarnings("serial")
public class StartMenuPopup extends JDialog {
	JButton start;
	JButton ranking;
	JButton setting;
	JButton exit;
	
	public StartMenuPopup() {
		startFrameSetting();
		startButton();
		startButtonEvents();
	}

//	테트리스 윈도우 프레임
	private void startFrameSetting() {
		setTitle("TETRIS MAIN MENU");
		setModal(true);
		setLayout(null);
		setSize(500, 400);
		setLocation(ScreenUtil.getCenterPosition(this));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
//	프레임에 내 버튼
	private void startButton() {
		start = new JButton("S T A R T");
		ranking = new JButton("R A N K I N G");
		setting = new JButton("S E T T I N G");
		exit = new JButton("E X I T");
		
		start.setBounds(150, 200, 200, 30);
		ranking.setBounds(150, 230, 200, 30);
		setting.setBounds(150, 260, 200, 30);
		exit.setBounds(150, 290, 200, 30);
		
		add(start);
		add(ranking);
		add(setting);
		add(exit);
	}

//	버튼 이벤트
	private void startButtonEvents() {
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				new TetrisView().setVisible(true);
			}
			
		});	
		
		ranking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start.requestFocus();
			}
			
		});
		
		setting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start.requestFocus();
			}
			
		});
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
	}
	
//	프레임 내에 TETRIS 문구 입혀 내보냄
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//T
		g.setColor(Color.RED);
		g.fill3DRect(40, 70, 20, 20, true);
		g.fill3DRect(60, 70, 20, 20, true);
		g.fill3DRect(80, 70, 20, 20, true);
		g.fill3DRect(60, 90, 20, 20, true);
		g.fill3DRect(60, 110, 20, 20, true);
		g.fill3DRect(60, 130, 20, 20, true);
		g.fill3DRect(60, 150, 20, 20, true);

		//E
		g.setColor(Color.ORANGE);
		g.fill3DRect(120, 70, 20, 20, true);
		g.fill3DRect(140, 70, 20, 20, true);
		g.fill3DRect(160, 70, 20, 20, true);
		g.fill3DRect(120, 90, 20, 20, true);
		g.fill3DRect(120, 110, 20, 20, true);
		g.fill3DRect(140, 110, 20, 20, true);
		g.fill3DRect(160, 110, 20, 20, true);
		g.fill3DRect(120, 130, 20, 20, true);
		g.fill3DRect(120, 150, 20, 20, true);
		g.fill3DRect(140, 150, 20, 20, true);
		g.fill3DRect(160, 150, 20, 20, true);

		//T
		g.setColor(Color.YELLOW);
		g.fill3DRect(200, 70, 20, 20, true);
		g.fill3DRect(220, 70, 20, 20, true);
		g.fill3DRect(240, 70, 20, 20, true);
		g.fill3DRect(220, 90, 20, 20, true);
		g.fill3DRect(220, 110, 20, 20, true);
		g.fill3DRect(220, 130, 20, 20, true);
		g.fill3DRect(220, 150, 20, 20, true);

		//R
		g.setColor(Color.GREEN);
		g.fill3DRect(280, 70, 20, 20, true);
		g.fill3DRect(300, 70, 20, 20, true);
		g.fill3DRect(280, 90, 20, 20, true);
		g.fill3DRect(310, 90, 20, 20, true);
		g.fill3DRect(280, 110, 20, 20, true);
		g.fill3DRect(300, 110, 20, 20, true);
		g.fill3DRect(280, 130, 20, 20, true);
		g.fill3DRect(310, 130, 20, 20, true);
		g.fill3DRect(280, 150, 20, 20, true);
		g.fill3DRect(320, 150, 20, 20, true);

		//I
		g.setColor(Color.BLUE);
		g.fill3DRect(360, 70, 20, 20, true);
		g.fill3DRect(360, 90, 20, 20, true);
		g.fill3DRect(360, 110, 20, 20, true);
		g.fill3DRect(360, 130, 20, 20, true);
		g.fill3DRect(360, 150, 20, 20, true);

		//S
		g.setColor(Color.PINK);
		g.fill3DRect(410, 70, 20, 20, true);
		g.fill3DRect(430, 70, 20, 20, true);
		g.fill3DRect(400, 90, 20, 20, true);
		g.fill3DRect(400, 110, 20, 20, true);
		g.fill3DRect(420, 110, 20, 20, true);
		g.fill3DRect(440, 110, 20, 20, true);
		g.fill3DRect(440, 130, 20, 20, true);
		g.fill3DRect(430, 150, 20, 20, true);
		g.fill3DRect(410, 150, 20, 20, true);
	}
}