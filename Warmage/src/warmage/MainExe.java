package warmage;

public class MainExe {

	public static void main(String arg[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame m = new MainFrame();
				m.setVisible(true);
			}
		});
	}
}
