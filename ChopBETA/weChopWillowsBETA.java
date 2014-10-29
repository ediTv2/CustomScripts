//weChopWillows_V0.5
//Updated Daily - ediTv2
//Better walk methods
//Updated Anti-Ban
//Credits to Tombraider, Cake, iFlow, and Brian! Thanks for helping me out!

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.event.*;

import javax.swing.*;

import bot.script.BotScript;
import bot.script.methods.Skills;
import bot.script.enums.Skill;
import bot.script.enums.SkillData;
import bot.script.enums.Tab;
import bot.script.methods.Bank;
import bot.script.methods.Calculations;
import bot.script.methods.Camera;
import bot.script.methods.Game;
import bot.script.methods.Inventory;
import bot.script.wrappers.Component;
import bot.script.methods.Keyboard;
import bot.script.methods.Mouse;
import bot.script.methods.Methods;
import bot.script.methods.Npcs;
import bot.script.methods.Objects;
import bot.script.wrappers.Player;
import bot.script.methods.Players;
import bot.script.methods.Skills;
import bot.script.methods.Walking;
import bot.script.wrappers.GroundItem;
import bot.script.methods.GroundItems;
import bot.script.util.Filter;
import bot.script.util.Random;
import bot.script.wrappers.GameObject;
import bot.script.wrappers.Item;
import bot.script.wrappers.NPC;
import bot.script.wrappers.Path;
import bot.script.wrappers.Tile;
import bot.script.wrappers.Widget;
import bot.script.methods.Widgets;
import java.text.DecimalFormat;

public class weChopWillowsBETA extends BotScript {
	// @Manifest -Waiting for API!-

	// GUI Data
	private boolean guiWait = true, guiExit;
	private String locations, treeIDs;
	AIOCHOPGUI gui;

	// TreeIDs & Animation
		private final static int[] NORMAL_ID = {1276, 1278}; // Adding in future release!
	private final static int OAK_ID = (1281); // Adding in future release!
	private final static int[] WILLOW_ID = { 5551, 5552, 5553, 1308 }; 
	private final static int MAPLE_ID = (1307); // Adding in future release!
	private final static int YEW_ID = (1309); // Adding in future release!
	private final static int MAGIC_ID = (1306); // Adding in future release!
	private final static int CHOP_ANIMATION = 867;
	
	private int[] LOG_ID = { 1522, 1512, 1518, 1520, 1516 };
	private int[] AXE_ID = { 1360, 1351, 1352, 1349, 1350, 1353, 1354, 1355,
			1356, 1357, 1358, 1359 };
	private int[] NEST_ID = { 5071, 5072, 5073, 5074, 5075, 5070, 7413, 5076 };

	private int[] ENT_ID = { 1740, 1731, 1735, 1736, 1739, 1737 };
	private int[] RANDOM_ID = { 2539, 407, 409, 410, 408, 2469, 2470, 956,
			2540, 2536, 2537, 2538, 2458, 1056, 3117, 4375, 2476 };

	private int[] BANK_ID = { 2213 };
	Tile bankToUse;
	private int[] pathToWalk;
	private int[] treeToChop;
	Tile chopPos;

	static Tile DRAYNOR_BANK = new Tile(3092, 3245);    //done
	static Tile CATHERBAY_BANK = new Tile(2809, 3441);  //done
	static Tile ARDY_BANK = new Tile(45, 45);
	static Tile GNOME_BANK = new Tile(525, 235);
	static Tile FALADOR_BANK = new Tile(87, 234);

	static Tile DRAYNOR_WILLOWS = new Tile(3086, 3237); //done
	static Tile CATHERBAY_WILLOW = new Tile(2784, 3429);//done
	static Tile CATHERBAY_YEW = new Tile(2761, 3430);   //done
	static Tile ARDY_WILLOWS = new Tile(55, 55);
	static Tile SEERS_WILLOWS = new Tile(2711, 3509);   //done
	static Tile SEERS_MAPLES = new Tile(2726, 3500);    //done
	static Tile SEERS_YEWS = new Tile(2761, 3430);      //done
	static Tile SEERS_MAGICS = new Tile(2695, 3426);    //done
	static Tile SEERS_NORMALS = new Tile(235, 532):
	static Tile GNOME_YEWS = new Tile(523, 5123);
	static Tile GNOME_MAGICS = new Tile(634, 87234);
	static Tile RIMMINGTON_YEWS = new Tile(23523, 23523);
	static Tile VARROCK_OAKS = new Tile(999, 888);
	static Tile LUMB_NORMALS = new Tile(2353, 5436);
	

	Tile draynorPoint = new Tile(65, 65);
	Tile catherbayPoint = new Tile(100, 100);
	Tile ardyPoint = new Tile(150, 150);

	//Paths
	private final static Tile[] CATH_WILL_BANK = { new Tile(2787, 3428),
			new Tile(2790, 3432), new Tile(2791, 3433), new Tile(2796, 3433),
			new Tile(2802, 3433), new Tile(2808, 3436), new Tile(2808, 3439),
			new Tile(2809, 3441)};
    private final static Tile[] CATH_BANK_WILL = { new Tile(2809, 3441),
			new Tile(2805, 3435), new Tile(2798, 3432), new Tile(2792, 3431),
			new Tile(2787, 3428), new Tile(2784, 3429) };
    private final static Tile[] RIM_YEW_BANK = { new Tile(235, 2352)};
    private final static Tile[] RIM_BANK_YEW = { new Tile(8272, 2352)};
    private final static Tile[] GNOME_YEW_BANK = { new Tile(234, 235)};
    private final static Tile[] GNOME_BANK_YEW = { new Tile(125, 5235)};
    private final static Tile[] GNOME_MAGIC_BANK = { new Tile(980, 235)};
    private final static Tile[] GNOME_BANK_MAGIC = { new Tile(876, 543)};
    private final static Tile[] ARDY_WILL_BANK = { new Tile(234, 423)};
    private final static Tile[] ARDY_BANK_WILL = { new Tile(877, 876)};
    private final static Tile[] SEERS_WILL_BANK = { new Tile(235, 235)};
    private final static Tile[] SEERS_BANK_WILL = { new Tile(132, 235)};
    private final static Tile[] SEERS_YEW_BANK = { new Tile(235, 235)};
    private final static Tile[] SEERS_BANK_YEW = { new Tile(786, 2532)};
    private final static Tile[] VARROCK_OAK_BANK = { new Tile(23523, 235)};
    private final static Tile[] VARROCK_BANK_OAK = { new Tile(235, 235)};
    private final static Tile[] LUMB_NORMAL_BANK = { new Tile(2352, 25351)};
    private final static Tile[] LUMB_BANK_NORMAL = { new Tile(3333, 333)};
    private final static Tile[][] DRAYNOR_WILL_BANK = new Tile[][] { { DRAYNOR_BANK, DRAYNOR_WILLOWS } };
    private final static Tile[][] DRAYNOR_BANK_WILL = new Tile[][] { { DRAYNOR_WILLOWS } };
    
    
    
	// Paint, time, level, and XP data.
	long totalSeconds = 0;
	private int startLevel;
	private int currentLevel;
	private int gainedLevel;
	private int startXP;
	private int gainedXP;
	private int xpTNL;
	private int percentTNL;
	int startEXP;
	int XPgained;
	int xpPerHour;
	public int EXPgained;
	public int NextLevel;
	private int logs = 0;
	String globalday, globalhour, globalminute, globalsecond;
	boolean hide = false;

	// ********************* onStart | Display ScriptName, Creator - ediTv2, and
	// Current Version!
	@Override
	public boolean onStart() {
		gui = new AIOCHOPGUI();
		gui.setVisible(true);
		while (guiWait)
		
		System.out.println("Supported Locations:" + locations);
		System.out.println("Supported Trees:" + treeIDs);
		startTime = System.currentTimeMillis();
		startLevel = Skills.getLevel(Skill.WOODCUTTING);
		startXP = Skills.getXp(Skill.WOODCUTTING);
	    log("hi test");
		log("Created By: ediTv2");
		log("weChopWillowsv0.5");
		return true;
	}

	// ********************* Anti-Ban Version 0.1 /Push V0.2 If Stable on SVN!
	public void performAntiban() {

		int randomint = Random.nextInt(0, 5);
		switch (randomint) {
		case 1: // ********************* Move Mouse V0.1 (Fix)
			log("[Anti-v0.1] Moving mouse. N/A");
			Mouse.move(Random.nextInt(1, 200), Random.nextInt(1, 200));
			break;
		case 2: // ********************* Change Camera Angle V0.1

			log("[Anti-v0.1] Turning screen");

			Camera.setAngle(Random.nextInt(30, 70));
			sleep(800, 2400);

			break;
		case 3: // ********************* Pause v0.1

			log("[Anti-v0.1] Pausing for a few seconds.");
			sleep(1000, 2000);

			break;
		case 4: // ********************* Checking Current XP V0.1

			log("[Anti-v0.1] Checking Xp.");
			Game.openTab(Tab.SKILLS);
			sleep(800, 1000);
			Mouse.move(712 + Random.nextInt(0, 4), 377 + Random.nextInt(0, 4));
			sleep(4000, 7000);
			Game.openTab(Tab.INVENTORY);
			sleep(800, 1000);

			break;
		case 5: // ********************* Examines random item(s) in inventory.
				// Credit to: Soxs

			if (Inventory.getCount() > 2) { // examines random items in
											// inventory
				Item[] items = Inventory.getItems();
				int ran = Random.nextInt(1, Inventory.getCount());
				items[ran].interact("Examine");
			}

		default:
			break;
		}
	}

	// ********************* DoAction | Bank, Chop, and Walk | If Full Inventory
	// or Empty
	public int loop1() 
	{
		runAway();
		RandomsNear();       //add
		NestCheck();
		GameObject tree = Objects.getNearest(treeToChop);
		if ((!Inventory.isFull() && Players.getLocal().getAnimation() !=867) && (!Players.getLocal().isMoving()) && (!Players.getLocal().inCombat())) {
			cutDown();
		}
		if (Inventory.isFull() && (Npcs.getNearest(BANK_ID) == null) && (!bankToUse.isVisible()) && (!Players.getLocal().inCombat())) {
			WalktoBank();
		}
		if (!Inventory.isFull() && !chopPos.isVisible() && !tree.isVisible()) {
			performWalktoTree();
		}
		if (Inventory.isFull() && (Npcs.getNearest(BANK_ID) !=null) && (Players.getLocal().isMoving()) && (Players.getLocal().inCombat())) 

			{
			BankAll();
		}
		NPC badTree = Npcs.getNearest(777);
		if (badTree != null) {
			log("Anti Evil Tree Method! Contact ediT if there's an error.");
			cutDown();
		}
		if (Players.getLocal().inCombat()) {
			runAway();
		}

		return 1;

	}

	// ********************* Initiate walk2Bank Method

	private boolean WalktoBank() {
		Walking.walkTo(bankPos);
		sleep(3000, 5000);
		return Inventory.isFull();

	}

	// ********************* Initiate walk2Tree Method
	private boolean performWalktoTree() {
		Walking.walkTo(chopPos);
		sleep(3000, 5000);
		return !Inventory.isFull();

	}

	// ********************* Initiate Bank and BankAllExcept AXE_ID
	private void BankAll() {
		Camera.setAngle(494);
		GameObject booth = Objects.getNearest(BANK_ID);
		if (booth.isVisible() && (booth.interact("Use-quickly"))) {
			Item[] InvItem = Inventory.getItems();
		    sleep(800, 1000);
		    int i = 0;
		    while ((!isOnlyItemInInv(AXE_ID)) && (InvItem.length > 0))
		    {
			if ((InvItem !=null) && (!Hatchet(InvItem[i]))) {
				if (Bank.isOpen()) {
					InvItem[i].interact("Store All");
					sleep(300, 450);
					Bank.close();
				}
			}
		}
	}
		    
	}

	public boolean Hatchet(Item axe) {
	    for (int i = 0; i < AXE_ID.length; i++) {
	      if (axe.getId() == AXE_ID[i]) {
	        return true;
	      }
	    }
	    return false;
	  }
	
	public boolean isOnlyItemInInv(int[] id) {
		Item[] Inv = Inventory.getItems();
		if ((Inv != null) && (Inv.length == 1)) {
			for (int i : id) {
				if (Inv[0].getId() == i) {
					return true;
				}
			}

		}
		return false;
	}

	private void runAway() 
	{
		if (Players.getLocal().inCombat());
		{
		WalktoBank();
		 //INSERT THIS EDIT! INSERT!!!
		sleep(1100, 2000);
		}
	}

	// ********************* Calculate distance from & to willowTree.
	private boolean cutDown() {
		GameObject tree = Objects.getNearest(treeToChop);
		if (tree != null) {
			if (Players.getLocal().getAnimation() == 1) {
				if (chopPos.isVisible()) {
					if (Calculations.distanceBetween(tree.getLocation(),
							Players.getLocal().getLocation()) >= 5) {
						Walking.walkTo(tree.getLocation());
						Camera.turnToTile(tree.getLocation());
						sleep(900, 3500);
					}
				}
			}
		}
		// ********************* Move to TreeLoc & Initiate Chop.
		if (tree.isVisible() && chopPos.onMinimap()) {
			Mouse.move(tree.getPoint().x, tree.getPoint().y);
			tree.interact("Chop down");
			sleep(500, 3000);
			performAntiban();
		} else if (!tree.isVisible() && chopPos.onMinimap()) {
			Camera.turnToTile(tree.getLocation());
			performWalktoTree();
			Camera.setPitch(99);
			while (Players.getLocal().isMoving()) {
				sleep(50, 100);
			}
		}
		return !Inventory.isFull() && chopPos.isVisible();

	}
	
	public void NestCheck()
	{
		GroundItem birdnest = null;
	    for (int i = 0; i < NEST_ID.length; i++) {
	        if (GroundItems.getNearest(NEST_ID[i]) != null) {
	          birdnest = GroundItems.getNearest(NEST_ID[i]);
	          break;
	        }
	      }
	      if (birdnest != null) {
	        if (birdnest.interact("Take")) {
	          Item birdNest = Inventory.getItem(birdnest.getId());
	          while (birdNest == null) {
	            birdNest = Inventory.getItem(birdnest.getId());
	            sleep(100, 200);
	          }
	          birdNest.interact("Search");
	        }
	      }
	}

	// ********************* Paint Data | Paint v1.0
	private final Color color1 = new Color(255, 255, 255);
	private final BasicStroke stroke1 = new BasicStroke(1);
	private final Font font3 = new Font("Arial", 2, 13);

	Image background = getImage("http://i.imgur.com/kLlud0V.jpg"); 

	public Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			return null;
		}
	}

	private long startTime, runTime;

	long milis;
	long seconds;
	long minutes;
	long hours;

	public void dataUpdate() {
		currentLevel = Skills.getLevel(Skill.WOODCUTTING);
		gainedLevel = currentLevel - startLevel;
		gainedXP = Skills.getXp(Skill.WOODCUTTING) - startXP;
		xpTNL = Skills.xpForNextLevel(Skill.WOODCUTTING);
		runTime = System.currentTimeMillis();
		long timeRan = runTime - startTime;
		hours = timeRan / 3600000;
		timeRan = timeRan - (hours * 3600000);
		minutes = timeRan / 60000;
		timeRan = timeRan - (minutes * 60000);
		seconds = timeRan / 1000;
		timeRan = timeRan - (seconds * 1000);
	}

	public void paint(Graphics g1) {
		// updating Data
		dataUpdate();
		xpPerHour = (int) ((gainedXP) * 3600000D / (System.currentTimeMillis() - startTime));

		// Paint Source v0.1 (Future: Logs cut, Status
		// "Banking,Walking,Chopping.")
		Graphics2D g = (Graphics2D) g1;
		g.drawImage(background, 7, 345, null);
		g.setFont(font3);
		// Time @Player has been running "weChop Willows"
		g.setColor(color1);
		g.drawString(hours + ":" + minutes + ":" + seconds, 410, 374);
		// XP @Player has gained.
		g.setColor(color1);
		g.drawString("" + gainedXP + " (" + xpPerHour + "/Hr)", 431, 396);
		// Levels @Player has gained.
		g.setColor(color1);
		g.drawString("" + gainedLevel, 438, 417);
		// XP @Player has to achieve till upcoming level.
		g.setColor(color1);
		g.drawString("0" + xpTNL, 425, 438);
		// Level @Player currently has.
		g.setColor(color1);
		g.drawString("" + currentLevel + " (" + gainedLevel +")", 433, 459);
	}

	public class AIOCHOPGUI extends JFrame {
		private static final long serialVersionUID = 1L;
		public AIOCHOPGUI() {
			initComponents();
		}

		private void locationsActionPerformed(ActionEvent e) {
			if (locations.equals("Draynor")) {
			    treeIDs.removeAllItems();
			    treeIDs.addItem("Willow");
			}
			else if (locations.getSelectedObjects().equals("Catherbay")) {
				treeIDs.removeAllItems();
				treeIDs.addItem("Willow");
				treeIDs.addItem("Yew");
			}
			else if (locations.getSelectedObjects().equals("Ardougne")) {
				treeIDs.removeAllItems();
				treeIDs.addItem("Willow");
			}
			else if (locations.getSelectedObjects().equals("Seers")) {
				treeIDs.removeAllItems();
				treeIDs.addItem("Willow");
				treeIDs.addItem("Maple");
				treeIDs.addItem("Yew");
			}
			else if (locations.getSelectedItem().equals("Varrock")) {
				treeIDs.removeAllItems();
				treeIDs.addItem("N/a");
			}
			else if (locations.getSelectedItem().equals("Lumbridge")) {
				treeIDs.removeAllItems();
				treeIDs.addItem("N/a");
			}
			
			}

		private void initComponents() {
			label1 = new JLabel();
			label2 = new JLabel();
			label3 = new JLabel();
			locations = new JComboBox<>();
			treeIDs = new JComboBox<>();
			button1 = new JButton();
			label4 = new JLabel();

			//======== this ========
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("weChopAIO Settings");
			Container contentPane = getContentPane();
			contentPane.setLayout(null);

			//---- label1 ----
			label1.setText("weChopAIO Settings");
			label1.setFont(new Font("QTFrizQuad", Font.PLAIN, 20));
			contentPane.add(label1);
			label1.setBounds(new Rectangle(new Point(50, 10), label1.getPreferredSize()));

			//---- label2 ----
			label2.setText("Supported Locations:");
			label2.setFont(new Font("Miriam", Font.PLAIN, 11));
			contentPane.add(label2);
			label2.setBounds(new Rectangle(new Point(20, 55), label2.getPreferredSize()));

			//---- label3 ----
			label3.setText("Supported Trees:");
			label3.setFont(new Font("Miriam", Font.PLAIN, 11));
			contentPane.add(label3);
			label3.setBounds(new Rectangle(new Point(20, 85), label3.getPreferredSize()));

			//---- locations ----
			locations.setModel(new DefaultComboBoxModel<>(new String[] {
				"Draynor",
				"Catherbay",
				"Ardougne",
				"Seers",
				"Varrock",
				"Lumbridge"
			}));
			locations.setFont(new Font("Miriam", Font.PLAIN, 11));
			locations.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					locationsActionPerformed(e);
					locationsActionPerformed(e);
				}
			});
			contentPane.add(locations);
			locations.setBounds(150, 55, 120, 20);

			//---- treeIDs ----
			treeIDs.setModel(new DefaultComboBoxModel<>(new String[] {
				"Willow",
				"Normal",
				"Oak",
				"Maple",
				"Yew",
				"Magic",
				"N/a"
			}));
			treeIDs.setFont(new Font("Miriam", Font.PLAIN, 11));
			treeIDs.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					treeIDsActionPerformed(e);
				}
			});
			contentPane.add(treeIDs);
			treeIDs.setBounds(150, 82, 120, 20);

			//---- button1 ----
			button1.setText("Start");
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});
			contentPane.add(button1);
			button1.setBounds(105, 120, 90, 30);
			contentPane.add(label4);
			label4.setBounds(new Rectangle(new Point(90, 155), label4.getPreferredSize()));

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < contentPane.getComponentCount(); i++) {
					Rectangle bounds = contentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = contentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				contentPane.setMinimumSize(preferredSize);
				contentPane.setPreferredSize(preferredSize);
			}
			setSize(320, 190);
			setLocationRelativeTo(getOwner());
			// GEN-END:initComponents
		}

		private JLabel label1;
		private JLabel label2;
		private JLabel label3;
		private JComboBox<String> locations;
		private JComboBox<String> treeIDs;
		private JButton button1;
		private JLabel label4;
		// GEN-END:variables
	}

	// ********************* Finish, Thanks, and Update Log.
	public void onFinish() {
		log("Thanks for using weChopWillows! -ediTv2");
		log("Please report all bugs!");
		log("Test Log");
	}
}