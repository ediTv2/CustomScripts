//weMineIronV0.01
//Updated Daily - ediTv2

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import bot.script.BotScript;
import bot.script.enums.Skill;
import bot.script.enums.SkillData;
import bot.script.enums.Tab;
import bot.script.methods.Bank;
import bot.script.methods.Calculations;
import bot.script.methods.Camera;
import bot.script.methods.Game;
import bot.script.methods.Inventory;
import bot.script.methods.Keyboard;
import bot.script.methods.Mouse;
import bot.script.methods.Npcs;
import bot.script.methods.Objects;
import bot.script.methods.Players;
import bot.script.methods.Skills;
import bot.script.methods.Walking;
import bot.script.util.Random;
import bot.script.wrappers.GameObject;
import bot.script.wrappers.NPC;
import bot.script.wrappers.Tile;
import java.text.DecimalFormat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class weMineIronBETA extends BotScript {

	Tile bank2Iron;
	Tile iron2Bank;
	Tile ironTile = new Tile (3175, 3367);
	Tile bankTile = new Tile (3182, 3436);
	private int ironID[] = {441, 11956, 11955};
	private int pickID[] = {1265, 1269, 1267, 1273, 1271, 1275, 15259};
	private int iron = 434;
	private int bankID = 11402;
	private String status;
	private String paint;
	Point m;

	//Paint Junk
	public int oresMined;
	public int gainedExp;
	public int startExp;
	public long startTime;

	//GUI
	weMineIron GUI gui;
	private boolean guiWait = true, guiExit;
	private boolean longPaint = false, boxPaint = false;

	public boolean onStart() {	
		gui = new weMineIronGUI();
		gui.setVisible(true);
		while (guiWait) {
			sleep(100);
		}
		startTime = System.currentTimeMillis();
		startExp = Skills.getXp(Skill.MINING);
		m = Mouse.getLocation();
		bank2Iron =  new Tile(3182, 3436), new Tile(3185, 3431), new Tile(3179, 3428),
				new Tile(3172, 3426), new Tile(3171, 3419), new Tile (3171, 3413),
				new Tile(3171, 3405), new Tile(3172, 3398), new Tile(3172, 3398),
				new Tile(3174, 3390), new Tile(3177, 3383), new Tile(3182, 3380),
				new Tile(3184, 3374), new Tile(3180, 3371)};	
		iron2Bank = Walking.walkTo(bank2Iron);
		if(paint == "Long Paint") {
			longPaint = true;
			boxPaint = false;
		} else {
			boxPaint = true;
			longPaint = false;
		}
		return !guiExit;
	}

	@Override
	public int loop() {
		if(random(1, 100) == 77) {
			mouse.setSpeed(random(3,4));
		}
		if(!Inventory.isFull()) {
			Walking.setRun(boxPaint);
			walkToMine();
			if(atMine()) {
				mineRocks();
			}
		} else {
			setRun();
			walkToBank();	
			if(atBank()) {
				doBank();
			}
		}
		return 0;
	}

	public void walkToMine() {
		if(Calculations.distanceTo(ironTile) >= 5) {
			status = "Walking To Mine";
			Walking.walkTo(bank2Iron);
		}
	}

	public boolean atMine() {
		if(Calculations.distanceTo(ironTile) <= 4) {
			return true;
		}
		return false;
	}

	public void doBank() {
		RSObject booth = objects.getNearest(bBooth);
		if(booth != null) {
			status = "Bank Operations";
			if(!bank.isOpen()) {
				bank.open();
				sleep(random(1000, 1250));
			} else {
				bank.depositAllExcept(pickID);
				sleep(random(1000, 1250));
			}
		}
	}

	public boolean atBank() {
		if(calc.distanceTo(bankTile) <= 4) {
			return true;
		}
		return false;
	}

	public void walkToBank() {
		if(calc.distanceTo(bankTile) >= 5) {
			status = "Walking To Bank";
			walkTo(mineToBank);
		}
	}

	public void walkTo(RSTile[] path) {
		walking.walkPathMM(path, 2, 2);
		sleep(random(1500, 2000));
	}

	public int mine() {
		RSObject clay = objects.getNearest(clayID);
		if(getMyPlayer().getAnimation() == -1) { 
			status = "Mining";
			clay.doAction("Mine"); {
				antiBan();
				status = "Mining";		
			}
		}
		return random(250,400);
	}

	public boolean mineRocks() {
		try {
			final RSObject Rock = objects.getNearest(clayID);
			if (Rock == null) {
				return false;

			} else {
				if (getMyPlayer().getAnimation() == -1 && Rock != null) {
					if (Rock.doAction("Mine")) {
						status = "Mining";
						antiBan();
						sleep(random(500, 750));
						return true;
					}
				}
			}
		} catch (Exception ignored) {

		}
		return false;
	}

		public void antiBan() {
		if (random(0, 15) == 0) {
			final char[] LR = new char[] { KeyEvent.VK_LEFT,
					KeyEvent.VK_RIGHT };
			final char[] UD = new char[] { KeyEvent.VK_DOWN,
					KeyEvent.VK_UP };
			final char[] LRUD = new char[] { KeyEvent.VK_LEFT,
					KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
					KeyEvent.VK_UP };
			final int random2 = random(0, 2);
			final int random1 = random(0, 2);
			final int random4 = random(0, 4);

			if (RandomInt(0, 3) == 0) {
				Mouse.move(0, 0);
				Keyboard.pressKey(LR[random1]);
				sleep(100, 400);
				Keyboard.pressKey(UD[random2]);
				sleep(RandomInt(300, 600));
				Keyboard.releaseKey(UD[random2]);
				sleep(RandomInt(100, 400));
				Keyboard.releaseKey(LR[random1]);

				if (RandomInt(0, 8) == 0) {
					if (Game.getTab() != null) {
						Game.openTab(QUEST);
						Mouse.move(29, 11);
						Sleep(RandomInt(2000, 3000));
					}
				

				if (random(0, 10) == 0) {
					Game.openTab(SKILLS);
				}
			} else {
				Keyboard.pressKey(LRUD[random4]);
				if (random4 > 1) {
					sleep(random(300, 600));
				} else {
					sleep(random(500, 900));
				}
				Keyboard.releaseKey(LRUD[random4]);
			}
		} else {
			sleep(random(200, 2000));
		}
	}

	private int RandomInt(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int random(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	private final Color color1 = new Color(0, 0, 0);
	private final Font font1 = new Font("Arial", 1, 13);
	private final Font font2 = new Font("Arial", 1, 11);
	private final Image img2 = getImage("http://img694.imageshack.us/img694/3061/paint1copy.png");



	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch(IOException e) {
			return null;
		}
	}
	private final Image img1 = getImage("http://img35.imageshack.us/img35/1764/paint2copy.png");


	public void onRepaint(Graphics g) {
		long millis = System.currentTimeMillis() - startTime;
		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;
		long runTime = System.currentTimeMillis() - startTime;
		int oresPerHour = 0;
		int profitPerHour = 0;
		int xpPerHour = 0;
		if (runTime / 1000 > 0) {
			oresPerHour = (int) (3600000.0 / runTime * oresMined);
			xpPerHour = (int) (3600000.0 / runTime * gainedExp);
		}
		if (startExp == 0) {
			startExp = Skills.getXp(Skill.MINING);
		}
		gainedExp = Skills.getXp(Skill.MINING) - startExp;	    

		if(boxPaint) {
			g.drawImage(img1, 555, 210, null);
			g.setFont(font1);
			g.setColor(color1);
			g.drawString("" + hours + ":" + minutes + ":" + seconds , 639, 266);
			g.drawString("" + status, 630, 289);
			g.drawString("" + gainedExp, 648, 311);
			g.drawString("" + xpPerHour, 658, 332);
			g.drawString("" + oresPerHour, 670, 353);
			g.drawString("" + gemsFound, 658, 372);
			g.drawString("" + oresMined, 652, 395);
			g.drawString("" + myprofit, 661, 419);
			g.drawString("" + profitPerHour, 679, 441);
		} else {
			//Long Paint		        
			g.drawImage(img2, 5, 270, null);
			g.setFont(font2);
			g.setColor(color1);
			g.drawString("" + profitPerHour, 458, 326);
			g.drawString("" + myprofit, 442, 307);
			g.drawString("" + gemsFound, 438, 290);
			g.drawString("" + oresMined, 312, 321);
			g.drawString("" + oresPerHour, 330, 305);
			g.drawString("" + gainedExp, 189, 306);
			g.drawString("" + xpPerHour, 200, 322);
			g.drawString("" + hours + ":" + minutes + ":" + seconds , 75, 307);
			g.drawString("" + status, 69, 321);   
		}
	}

	public void serverMessageRecieved(ServerMessageEvent e) {
		String msg = e.getMessage();
		if(msg.contains("You manage to mine")) oresMined++;
		if(msg.contains("You just found")) gemsFound++;

	}

	public class SharinganClayBankerGUI extends JFrame {

		private static final long serialVersionUID = 1L;
		public SharinganClayBankerGUI() {
			initComponents();
		}

		private void button1ActionPerformed(ActionEvent e) {
			paint = comboBox1.getSelectedItem().toString();
			guiWait = false;
			dispose();
		}

		private void initComponents() {
			label1 = new JLabel();
			label3 = new JLabel();
			label2 = new JLabel();
			comboBox1 = new JComboBox();
			label4 = new JLabel();
			button1 = new JButton();

			//======== this ========
			setTitle("IronGUI");
			Container contentPane = getContentPane();
			contentPane.setLayout(new GridBagLayout());
			((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
			((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
			((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

			//---- label1 ----
			label1.setText("IronGUI");
			label1.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPane.add(label1, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

			//---- label3 ----
			label3.setText("             ");
			label3.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPane.add(label3, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

			//---- label2 ----
			label2.setText("Display Type:");
			label2.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPane.add(label2, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

			//---- comboBox1 ----
			comboBox1.setModel(new DefaultComboBoxModel(new String[] {
					"Box Paint",
					"Long Paint"
			}));
			contentPane.add(comboBox1, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

			//---- label4 ----
			label4.setText(" ");
			label4.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPane.add(label4, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5), 0, 0));

			//---- button1 ----
			button1.setText("Initate!");
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});
			contentPane.add(button1, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
			pack();
			setLocationRelativeTo(getOwner());
			//GEN-END:initComponents
		}

		private JLabel label1;
		private JLabel label3;
		private JLabel label2;
		private JComboBox comboBox1;
		private JLabel label4;
		private JButton button1;
		//GEN-END:variables
	}


}

