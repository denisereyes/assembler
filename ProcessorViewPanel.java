package projectview.java;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import assembler.MachineModel;

public class ProcessorViewPanel {
	private MachineModel model;
	private JTextField acc = new JTextField();
	private JTextField pc = new JTextField();
	public ProcessorViewPanel(MachineModel m) {
		model = m;
	}
	public JComponent createProcessorDisplay() {
		JPanel panel = new JPanel();
		JPanel accPanel = new JPanel();
		accPanel.setLayout(new GridLayout(1,0));
		accPanel.add(new JLabel("Accumulator: ", JLabel.RIGHT));
		accPanel.add(acc);
		JPanel pcPanel = new JPanel();
		pcPanel.setLayout(new GridLayout(1,0));
		pcPanel.add(new JLabel("Program Counter: " , JLabel.RIGHT));
		pcPanel.add(pc);
		panel.add(accPanel);
		panel.add(pcPanel);
		return panel;
	}
	public void update() {
		if(model != null) {
			acc.setText("" + model.getAccum());
			pc.setText("" + model.getPC());
		}
	}
	public static void main(String[] args) {
		MachineModel model = new MachineModel(() -> {});
		ProcessorViewPanel panel = new ProcessorViewPanel(model);
		JFrame frame = new JFrame("TEST");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 60);
		frame.setLocationRelativeTo(null);
		frame.add(panel.createProcessorDisplay());
		frame.setVisible(true);
	}
}