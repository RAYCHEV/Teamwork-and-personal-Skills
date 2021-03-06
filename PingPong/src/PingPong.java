import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PingPong {
	private static PingPongPanel pingPongPanel;
	private static JFrame frame;
	private static boolean isRunning = false;
	private static boolean isSinglePlayer = true;
	private static boolean isStarted = false;

	
	public static void main(String[] args) throws InterruptedException {
		
		String[] option = {"Single Player", "Multi Player"};
		String reply = (String) JOptionPane.showInputDialog(null, "Select game", "Ping Pong Game",JOptionPane.QUESTION_MESSAGE,null, option, option[0]);
		
		if (reply == "Single Player") {
			isSinglePlayer = true;
			isStarted = true;
		}else if (reply == "Multi Player") {
			
			isSinglePlayer = false;
			isStarted = true;
		}else{
			System.exit(0);
		}
		
		
		frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pingPongPanel = new PingPongPanel();
		frame.add(pingPongPanel);
		frame.setVisible(isStarted);
		frame.addKeyListener(new PingPongListener());
		

		while (true) {
			Thread.sleep(3);
			pingPongPanel.moveBall();
			pingPongPanel.moveStick();
			if (isSinglePlayer){
				pingPongPanel.moveAIPaddle();
			}
			pingPongPanel.repaint();
		}
	}

	private static class PingPongListener implements KeyListener {
		boolean isPaused = false;	// Dani
		
		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_S || 
					e.getKeyCode() == KeyEvent.VK_W) {
				pingPongPanel.setStickStep(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP ||
					e.getKeyCode() == KeyEvent.VK_DOWN) {
				pingPongPanel.setStickStepTwo(0);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if (!isSinglePlayer && !isPaused && isRunning && e.getKeyCode() == KeyEvent.VK_UP) {
					pingPongPanel.setStickStepTwo(-2);
			} else if (!isSinglePlayer && !isPaused && isRunning && e.getKeyCode() == KeyEvent.VK_DOWN) {
					pingPongPanel.setStickStepTwo(2);
			} else if (!isPaused && isRunning && e.getKeyCode() == KeyEvent.VK_S) {
				pingPongPanel.setStickStep(2);
			} else if (!isPaused && isRunning && e.getKeyCode() == KeyEvent.VK_W) {
				pingPongPanel.setStickStep(-2);
			} else if (!isRunning && !isPaused && e.getKeyCode() == KeyEvent.VK_ENTER) {
				isRunning = true;
				pingPongPanel.centeringStickAndBall();
			} else if (isRunning && e.getKeyCode() == KeyEvent.VK_SPACE) {
				pingPongPanel.setStickStep(0);
				pingPongPanel.setStickStepTwo(0);
				pingPongPanel.setPaused(!pingPongPanel.getPaused());
				isPaused = !isPaused;
			}
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				frame.dispose(); // exit frame
				System.exit(0);
			}
		}
	}
	
	public static boolean getRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		PingPong.isRunning = isRunning;
	}
}
