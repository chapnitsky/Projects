import javax.swing.JFrame;

public class Main {

	public Main() {
		JFrame frame = new JFrame();
		GamePanel panel = new GamePanel();
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Snake");
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
