package projectview.java;
import assembler.CodeAccessException;
import assembler.DivideByZeroException;
import assembler.IllegalInstructionException;
import assembler.MachineModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import assembler.Memory;
import assembler.ParityCheckException;

public class ViewMediator {
	private MachineModel model;
	private JFrame frame;
	private StepControl stepControl;
	private CodeViewPanel codeViewPanel;
	private MemoryViewPanel memoryViewPanel1;
	private MemoryViewPanel memoryViewPanel2;
	private MemoryViewPanel memoryViewPanel3;
	private ControlPanel controlPanel;
	private ProcessorViewPanel processorPanel;
	private States currentState = States.NOTHING_LOADED;
	private FilesMgr filesMgr;
	private MenuBarBuilder menuBuilder;
	
	
	public MachineModel getModel() {
		return model;
	}
	public void setModel(MachineModel model) {
		this.model = model;
	}
	public JFrame getFrame() {
		return frame;
	}
	
	public void step(){
		if (currentState != States.PROGRAM_HALTED && currentState != States.NOTHING_LOADED) {
			try {
				model.step();
			} catch (CodeAccessException e) {
				JOptionPane.showMessageDialog(frame,
						"Illegal access to code from line " + model.getPC() + "\n"
						+ "Exception message: " + e.getMessage(),
						"Run time error",
						JOptionPane.OK_OPTION);
				System.out.println("Illegal access to code from line " +
				model.getPC()); // just for debugging
				System.out.println("Exception message: " + e.getMessage());
			} catch(ArrayIndexOutOfBoundsException e) {
				// similar JOPtionPane
				// YOU HAVE TO FILL OUT ALL THESE CATCH BLOCKS—they all have different JOptionPane
				// code with different messages appropriate to the exception
			} catch(NullPointerException e) {
				// similar JOPtionPane
			} catch(ParityCheckException e) {
				// similar JOPtionPane
			} catch(IllegalInstructionException e) {
				// similar JOPtionPane
			} catch(IllegalArgumentException e) {
				// similar JOPtionPane
			} catch(DivideByZeroException e) {
				// similar JOPtionPane
			}
			notify("");
		}
	}
	
	private void createAndShowGUI() {
		stepControl = new StepControl(this);
		filesMgr = new FilesMgr(this); 
		filesMgr.initialize();		
		codeViewPanel = new CodeViewPanel(model);
		memoryViewPanel1 = new MemoryViewPanel(model, 0, 160);
		memoryViewPanel2 = new MemoryViewPanel(model, 160, Memory.DATA_SIZE/2);
		memoryViewPanel3 = new MemoryViewPanel(model, Memory.DATA_SIZE/2, Memory.DATA_SIZE);
		controlPanel = new ControlPanel(this);
		processorPanel = new ProcessorViewPanel(model);
		menuBuilder = new MenuBarBuilder(this);
		frame = new JFrame("Simulator");
		JMenuBar bar = new JMenuBar();  
		frame.setJMenuBar(bar);         
		bar.add(menuBuilder.createFileMenu());  
		bar.add(menuBuilder.createExecuteMenu());  
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(1,1));
		content.setBackground(Color.BLACK);
		frame.setSize(1200,600);
		frame.add(codeViewPanel.createCodeDisplay(), BorderLayout.LINE_START);
		frame.add(processorPanel.createProcessorDisplay(),BorderLayout.PAGE_START);
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1,3));
		center.add(memoryViewPanel1.createMemoryDisplay());
		center.add(memoryViewPanel2.createMemoryDisplay());
		center.add(memoryViewPanel3.createMemoryDisplay());
		frame.add(center, BorderLayout.CENTER);
		frame.add(controlPanel.createControlDisplay(), BorderLayout.PAGE_END);
		// the next line will be commented or deleted later
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		frame.addWindowListener(WindowListenerFactory.windowClosingFactory(e -> exit())); 
		frame.setLocationRelativeTo(null);//centers the frame on the screen
		stepControl.start();
		currentState.enter();  
 		notify("");              
		frame.setVisible(true);
	}
	
	public void clear() {
		model.clear();
		setCurrentState(States.NOTHING_LOADED);
		currentState.enter();
		notify("Clear");
		model.setProgramSize(0);
	}
	public void toggleAutoStep() {
		stepControl.toggleAutoStep();
		if(stepControl.isAutoStepOn()) {
			setCurrentState(States.AUTO_STEPPING);
		}
		else {
			setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
		}
	}
	public void reload() {
		stepControl.setAutoStepOn(false);
		clear();
		filesMgr.finalLoad_ReloadStep();
	}
	public void setPeriod(int value) {
		stepControl.setPeriod(value);
	}
	
	public States getCurrentState() {
		return currentState;
	}
	public void setCurrentState(States s){
		if(s == States.PROGRAM_HALTED) stepControl.setAutoStepOn(false);
		currentState = s;
		s.enter();
		notify("");
	}
	
	public void makeReady(String s) {
		stepControl.setAutoStepOn(false);
		setCurrentState(States.PROGRAM_LOADED_NOT_AUTOSTEPPING);
		currentState.enter();
		notify(s);
	}
	public void execute() {
		while (currentState != States.PROGRAM_HALTED && currentState != States.NOTHING_LOADED) {
			try {
				model.step();
			} catch (CodeAccessException e) {
				JOptionPane.showMessageDialog(frame,
						"Illegal access to code from line " + model.getPC() + "\n"
						+ "Exception message: " + e.getMessage(),
						"Run time error",
						JOptionPane.OK_OPTION);
				System.out.println("Illegal access to code from line " +
				model.getPC()); // just for debugging
				System.out.println("Exception message: " + e.getMessage());
			} catch(ArrayIndexOutOfBoundsException e) {
				// similar JOPtionPane
				// YOU HAVE TO FILL OUT ALL THESE CATCH BLOCKS—they all have different JOptionPane
				// code with different messages appropriate to the exception
			} catch(NullPointerException e) {
				// similar JOPtionPane
			} catch(ParityCheckException e) {
				// similar JOPtionPane
			} catch(IllegalInstructionException e) {
				// similar JOPtionPane
			} catch(IllegalArgumentException e) {
				// similar JOPtionPane
			} catch(DivideByZeroException e) {
				// similar JOPtionPane
			}
		}
		notify("");
	}
	public void assembleFile() {
		filesMgr.assembleFile();
	}
	public void loadFile() {
		filesMgr.loadFile();
	}
	public void exit() {
		int decision = JOptionPane.showConfirmDialog(
				frame, "Do you really wish to exit?",
				"Confirmation", JOptionPane.YES_NO_OPTION);
		if (decision == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	private void notify(String str) {
		codeViewPanel.update(str);
		memoryViewPanel1.update(str);
		memoryViewPanel2.update(str);
		memoryViewPanel3.update(str);
		controlPanel.update();
		processorPanel.update();
	}

	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ViewMediator mediator = new ViewMediator();
				MachineModel model = new MachineModel(() ->
					mediator.setCurrentState(States.PROGRAM_HALTED));
				mediator.setModel(model);
				mediator.createAndShowGUI();
			}
		});
	}
	
}