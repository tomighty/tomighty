package org.tomighty;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ChangeState;
import org.tomighty.states.InitialState;
import org.tomighty.util.Images;
import org.tomighty.util.New;

public class Tomighty {
	
	private JFrame window;
	private JPanel contentPane;
	private Logger logger;
	private TrayIcon trayIcon;
	private SystemTray tray;

	public Tomighty() throws AWTException {
		logger = Logger.getLogger(getClass().getName());
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		Border outside = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		Border inside = BorderFactory.createEmptyBorder(6, 6, 6, 6);
		contentPane.setBorder(BorderFactory.createCompoundBorder(outside, inside));
		
		
		window = new JFrame("Tomighty");
		window.setAlwaysOnTop(true);
		window.setContentPane(contentPane);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(100, 100);
		window.setResizable(false);
		window.setSize(150, 100);
		window.setUndecorated(true);
		window.setIconImage(Images.get("/tomato-48x48.png"));
		
		trayIcon = new TrayIcon(Images.get("/tomato-16x16.png"));
		trayIcon.addMouseListener(new TrayClick());
		trayIcon.setToolTip("Click to show/hide Tomighty");
		
		tray = SystemTray.getSystemTray();
		tray.add(trayIcon);
		
		MenuItem closeItem = new MenuItem("Close");
		PopupMenu popupMenu = new PopupMenu();
		popupMenu.add(closeItem);
		closeItem.addActionListener(new Exit());
		trayIcon.setPopupMenu(popupMenu);
	}
	
	public void start() {
		Bus.subscribe(new StateSwitch(), ChangeState.class);
		render(InitialState.class);
		trayIcon.displayMessage(null, "Click here to show Tomighty", MessageType.NONE);
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
	
	private class TrayClick extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				boolean show = !window.isVisible();
				if(show) {
					GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();	
					Rectangle bounds = env.getMaximumWindowBounds();
					int x = e.getXOnScreen() - window.getWidth() / 2;
					int y = bounds.height - window.getHeight() - 5;
					window.setLocation(x, y);
				}
				window.setVisible(show);
			}
		}
	}
	
	private class Exit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Tomighty().start();
	}

}
