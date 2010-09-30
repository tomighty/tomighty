package org.tomighty;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ChangeState;
import org.tomighty.states.InitialState;
import org.tomighty.util.New;

public class Tomighty {
	
	private JFrame window;
	private JPanel contentPane;
	private Logger logger;

	public Tomighty() {
		logger = Logger.getLogger(getClass().getName());
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		
		window = new JFrame("Tomighty");
		window.setAlwaysOnTop(true);
		window.setContentPane(contentPane);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(100, 100);
		window.setResizable(false);
		window.setSize(150, 100);
		window.setUndecorated(true);
	}
	
	public void start() {
		Bus.subscribe(new StateSwitch(), ChangeState.class);
		render(InitialState.class);
		window.setVisible(true);
	}
	
	private void render(Class<? extends State> stateClass) {
		State state = New.instanceOf(stateClass);
		Component component;
		try {
			component = state.render();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to render state: "+state, e);
			return;
		}
		contentPane.removeAll();
		contentPane.add(component, BorderLayout.CENTER);
		contentPane.validate();
	}

	private class StateSwitch implements Subscriber<ChangeState> {
		@Override
		public void receive(ChangeState message) {
			Class<? extends State> stateClass = message.getStateClass();
			render(stateClass);
		}
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Tomighty().start();
	}

}
