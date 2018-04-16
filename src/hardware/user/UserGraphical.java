package hardware.user;



import org.netbeans.lib.awtextra.*;
import hardware.user.Triangular.Rotation;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserGraphical extends JFrame {
	

	private JToggleButton btnCh1bk, btnCh2bk, btnCh3bk, btnCh4bk, btnCh5bk, btnCh6bk, btnCh7bk, btnCh8bk, 
    btnPower, btnTogCh1, btnTogCh2, btnTogCh3, btnTogCh4, btnTogCh5, btnTogCh6, btnTogCh7, btnTogCh8, usbBtn, btnFunction, btnPrintPwr;
    
    private JButton btnDown, btnNxt, btnPrevious, btnSwap, btnTr5, btnTrg1, btnTrg2, btnTrg3, 
    btnTrg4, btnTrg6, btnTrg7, btnTrg8, btnUp, btn3KeyPad, btn6KeyPad, btn9KeyPad, btn1KeyPad, 
    btn2KeyPad, btn4KeyPad, btn5KeyPad, btn8KeyPad, btnHashKeyPad, btn7KeyPad, btnStarKeyPad, btn0KeyPad;
    private JLabel jLabel1, lblCh5Bk, lblCh7Bk, lblCh2Bk, lblCh4Bk, lblCh6Bk, lblChanBk, jLabel2, jLabel3, jLabel4, jLabel5, lblCh8Bk, lblUsbBk, lblCh1Bk, 
    lblCh3Bk;
    
    private JPanel jPanel1, jPanel2, jPanel3, jPanel4, jPanel5, jPanel6, panelConsole;
    private JScrollPane jScrollPane1;
    private JTextArea txtAMiddle, txtAConsole, printerTextArea, txtBMiddle, txtCMiddle;
//    private JTextField printerTextField;
    private Label label1;
    private JTextPane txtPConsole;
    private JPopupMenu sensorPopupMenu;
    
    private static class SingletonUserGraphical {
    	
    	private static final UserGraphical uInterface = new UserGraphical();
    	
    }
    
    public static UserGraphical getSingleton(){
    	
    	return SingletonUserGraphical.uInterface;
    	
    }
    
    private UserGraphical() {
        initComponents();
    }
    
    private void initComponents() {
    	
    	initObjects();
    	makeBtns();
    	makeLbls();
    	makePnls();

        this.pack();
        
    }
    
    private void initObjects(){
    	
    	sensorPopupMenu = new JPopupMenu();
        btnPower = new JToggleButton();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        btn3KeyPad = new JButton();
        btn3KeyPad.setEnabled(false);
        btn1KeyPad = new JButton();
        btn1KeyPad.setEnabled(false);
        btn2KeyPad = new JButton();
        btn2KeyPad.setEnabled(false);
        btn4KeyPad = new JButton();
        btn4KeyPad.setEnabled(false);
        btn5KeyPad = new JButton();
        btn5KeyPad.setEnabled(false);
        btn6KeyPad = new JButton();
        btn6KeyPad.setEnabled(false);
        btn8KeyPad = new JButton();
        btn8KeyPad.setEnabled(false);
        btnHashKeyPad = new JButton();
        btnHashKeyPad.setEnabled(false);
        btn9KeyPad = new JButton();
        btn9KeyPad.setEnabled(false);
        btn7KeyPad = new JButton();
        btn7KeyPad.setEnabled(false);
        btnStarKeyPad = new JButton();
        btnStarKeyPad.setEnabled(false);
        btn0KeyPad = new JButton();
        btn0KeyPad.setEnabled(false);
        label1 = new Label();
        btnPrintPwr = new JToggleButton();
        btnPrintPwr.setEnabled(false);
        jLabel5 = new JLabel();
        printerTextArea = new JTextArea();
        jScrollPane1 = new JScrollPane();
        
        txtAMiddle = new JTextArea();
        txtBMiddle = new JTextArea();
        txtCMiddle = new JTextArea();
        
        jPanel1 = new JPanel();
        btnTrg1 = new JButton();
        btnTrg1.setEnabled(false);
        btnTrg3 = new JButton();
        btnTrg3.setEnabled(false);
        btnTr5 = new JButton();
        btnTr5.setEnabled(false);
        btnTrg7 = new JButton();
        btnTrg7.setEnabled(false);
        jPanel2 = new JPanel();
        btnTogCh1 = new JToggleButton();
        btnTogCh1.setEnabled(false);
        btnTogCh3 = new JToggleButton();
        btnTogCh3.setEnabled(false);
        btnTogCh5 = new JToggleButton();
        btnTogCh5.setEnabled(false);
        btnTogCh7 = new JToggleButton();
        btnTogCh7.setEnabled(false);
        jPanel3 = new JPanel();
        btnTrg2 = new JButton();
        btnTrg2.setEnabled(false);
        btnTrg4 = new JButton();
        btnTrg4.setEnabled(false);
        btnTrg6 = new JButton();
        btnTrg6.setEnabled(false);
        btnTrg8 = new JButton();
        btnTrg8.setEnabled(false);
        jPanel4 = new JPanel();
        btnTogCh2 = new JToggleButton();
        btnTogCh2.setEnabled(false);
        btnTogCh4 = new JToggleButton();
        btnTogCh4.setEnabled(false);
        btnTogCh6 = new JToggleButton();
        btnTogCh6.setEnabled(false);
        btnTogCh8 = new JToggleButton();
        btnTogCh8.setEnabled(false);
        btnSwap = new JButton();
        btnSwap.setEnabled(false);
        btnFunction = new JToggleButton();
        btnFunction.setEnabled(false);
        btnDown = new Triangular(Rotation.UP);
        btnDown.setEnabled(false);
        btnPrevious = new Triangular(Rotation.RIGHT);
        btnPrevious.setEnabled(false);
        btnNxt = new Triangular(Rotation.LEFT);
        btnNxt.setEnabled(false);
        btnUp = new Triangular(Rotation.DOWN);
        btnUp.setEnabled(false);
        jPanel5 = new JPanel();
        btnCh1bk = new JToggleButton();
        btnCh1bk.setEnabled(false);
        btnCh1bk.setName("1");			// new (used for JPopUpMenu) 
        btnCh3bk = new JToggleButton();
        btnCh3bk.setEnabled(false);
        btnCh3bk.setName("3");			// new
        btnCh5bk = new JToggleButton();
        btnCh5bk.setEnabled(false);
        btnCh5bk.setName("5");			// new
        btnCh7bk = new JToggleButton();
        btnCh7bk.setEnabled(false);
        btnCh7bk.setName("7");			// new
        jPanel6 = new JPanel();
        btnCh2bk = new JToggleButton();
        btnCh2bk.setEnabled(false);
        btnCh2bk.setName("2");			// new
        btnCh4bk = new JToggleButton();
        btnCh4bk.setEnabled(false);
        btnCh4bk.setName("4");			// new
        btnCh6bk = new JToggleButton();
        btnCh6bk.setEnabled(false);
        btnCh6bk.setName("6");			// new
        btnCh8bk = new JToggleButton();
        btnCh8bk.setEnabled(false);
        btnCh8bk.setName("8");			// new
        usbBtn = new JToggleButton();
        lblCh8Bk = new JLabel();
        lblUsbBk = new JLabel();
        lblCh1Bk = new JLabel();
        lblCh3Bk = new JLabel();
        lblCh5Bk = new JLabel();
        lblCh7Bk = new JLabel();
        lblCh2Bk = new JLabel();
        lblCh4Bk = new JLabel();
        lblCh6Bk = new JLabel();
        lblChanBk = new JLabel();
        panelConsole = new JPanel();
        txtAConsole = new JTextArea();
        txtAConsole.setWrapStyleWord(true);
        txtAConsole.setLineWrap(true);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 700));
        this.setPreferredSize(new Dimension(900, 700));
        this.getContentPane().setLayout(new AbsoluteLayout());
        
    }
    
    private void makePnls(){
        
        JScrollPane scroll1 = new JScrollPane();
        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll1.setAutoscrolls(true);
        scroll1.setViewportView(printerTextArea);
        printerTextArea.setEditable(true);
        
        getContentPane().add(scroll1, new AbsoluteConstraints(570, 70, 220, 150));

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);
        
        txtAMiddle.setColumns(8);
        txtAMiddle.setRows(5);
        txtAMiddle.setEditable(false);
        txtBMiddle.setColumns(8);
        txtBMiddle.setRows(5);
        txtBMiddle.setEditable(false);
        txtCMiddle.setColumns(8);
        txtCMiddle.setRows(5);
        txtCMiddle.setEditable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        
        panel.add(txtAMiddle, 0);
        panel.add(txtBMiddle, 1);
        panel.add(txtCMiddle, 2);
        jScrollPane1.setViewportView(panel);

        getContentPane().add(jScrollPane1, new AbsoluteConstraints(210, 250, 350, 260));

        jPanel1.setLayout(new GridLayout(0, 4, 20, 7));
        jPanel1.add(btnTrg1);
        jPanel1.add(btnTrg3);
        jPanel1.add(btnTr5);
        jPanel1.add(btnTrg7);

        getContentPane().add(jPanel1, new AbsoluteConstraints(250, 60, 240, 40));

        jPanel2.setLayout(new GridLayout(0, 4, 50, 0));
        jPanel2.add(btnTogCh1);
        jPanel2.add(btnTogCh3);
        jPanel2.add(btnTogCh5);
        jPanel2.add(btnTogCh7);

        getContentPane().add(jPanel2, new AbsoluteConstraints(260, 110, 220, 20));

        jPanel3.setLayout(new GridLayout(0, 4, 20, 7));
        jPanel3.add(btnTrg2);
        jPanel3.add(btnTrg4);
        jPanel3.add(btnTrg6);
        jPanel3.add(btnTrg8);

        getContentPane().add(jPanel3, new AbsoluteConstraints(250, 140, 240, 40));

        jPanel4.setLayout(new GridLayout(0, 4, 50, 0));
        jPanel4.add(btnTogCh2);
        jPanel4.add(btnTogCh4);
        jPanel4.add(btnTogCh6);
        jPanel4.add(btnTogCh8);

        getContentPane().add(jPanel4, new AbsoluteConstraints(260, 200, 220, 20));

        jPanel5.setLayout(new GridLayout(0, 4, 25, 0));
        jPanel5.add(btnCh1bk);
        jPanel5.add(btnCh3bk);
        jPanel5.add(btnCh5bk);
        jPanel5.add(btnCh7bk);

        getContentPane().add(jPanel5, new AbsoluteConstraints(70, 560, 140, 20));

        jPanel6.setLayout(new GridLayout(0, 4, 25, 0));
        jPanel6.add(btnCh2bk);
        jPanel6.add(btnCh4bk);
        jPanel6.add(btnCh6bk);
        jPanel6.add(btnCh8bk);

        getContentPane().add(jPanel6, new AbsoluteConstraints(70, 610, 140, 20));
        getContentPane().add(usbBtn, new AbsoluteConstraints(290, 580, 120, 20));  
        
        panelConsole.setLayout(new FlowLayout());
        txtAConsole.setBounds(0, 0, 10, 10);
        txtAConsole.setColumns(16);
        txtAConsole.setRows(3);
        JScrollPane scroll = new JScrollPane();
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setAutoscrolls(true);
        scroll.setViewportView(txtAConsole);
        txtAConsole.setEditable(false);
        getContentPane().add(scroll, new AbsoluteConstraints(10, 340, -1, -1));
        
    }
    
    private void makeBtns(){
    	
        btnPower.setText("Power");

        getContentPane().add(btnPower, new AbsoluteConstraints(50, 30, -1, -1));
    	
        btn3KeyPad.setBackground(new Color(102, 102, 102));
        btn3KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn3KeyPad.setText("3");
        btn3KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn3KeyPad.setBorderPainted(false);
        getContentPane().add(btn3KeyPad, new AbsoluteConstraints(700, 250, 50, 60));

        btn1KeyPad.setBackground(new Color(102, 102, 102));
        btn1KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn1KeyPad.setText("1");
        btn1KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn1KeyPad.setBorderPainted(false);
        getContentPane().add(btn1KeyPad, new AbsoluteConstraints(600, 250, 50, 60));

        btn2KeyPad.setBackground(new Color(102, 102, 102));
        btn2KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn2KeyPad.setText("2");
        btn2KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn2KeyPad.setBorderPainted(false);
        getContentPane().add(btn2KeyPad, new AbsoluteConstraints(650, 250, 50, 60));

        btn4KeyPad.setBackground(new Color(102, 102, 102));
        btn4KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn4KeyPad.setText("4");
        btn4KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn4KeyPad.setBorderPainted(false);
        getContentPane().add(btn4KeyPad, new AbsoluteConstraints(600, 310, 50, 60));

        btn5KeyPad.setBackground(new Color(102, 102, 102));
        btn5KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn5KeyPad.setText("5");
        btn5KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn5KeyPad.setBorderPainted(false);
        getContentPane().add(btn5KeyPad, new AbsoluteConstraints(650, 310, 50, 60));

        btn6KeyPad.setBackground(new Color(102, 102, 102));
        btn6KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn6KeyPad.setText("6");
        btn6KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn6KeyPad.setBorderPainted(false);
        getContentPane().add(btn6KeyPad, new AbsoluteConstraints(700, 310, 50, 60));

        btn8KeyPad.setBackground(new Color(102, 102, 102));
        btn8KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn8KeyPad.setText("8");
        btn8KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn8KeyPad.setBorderPainted(false);
        getContentPane().add(btn8KeyPad, new AbsoluteConstraints(650, 370, 50, 60));

        btnHashKeyPad.setBackground(new Color(102, 102, 102));
        btnHashKeyPad.setFont(new Font("Dialog", 1, 24)); 
        btnHashKeyPad.setText("#");
        btnHashKeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btnHashKeyPad.setBorderPainted(false);
        getContentPane().add(btnHashKeyPad, new AbsoluteConstraints(700, 430, 50, 60));

        btn9KeyPad.setBackground(new Color(102, 102, 102));
        btn9KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn9KeyPad.setText("9");
        btn9KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn9KeyPad.setBorderPainted(false);
        getContentPane().add(btn9KeyPad, new AbsoluteConstraints(700, 370, 50, 60));

        btn7KeyPad.setBackground(new Color(102, 102, 102));
        btn7KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn7KeyPad.setText("7");
        btn7KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn7KeyPad.setBorderPainted(false);
        getContentPane().add(btn7KeyPad, new AbsoluteConstraints(600, 370, 50, 60));

        btnStarKeyPad.setBackground(new Color(102, 102, 102));
        btnStarKeyPad.setFont(new Font("Dialog", 1, 24)); 
        btnStarKeyPad.setText("*");
        btnStarKeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btnStarKeyPad.setBorderPainted(false);
        getContentPane().add(btnStarKeyPad, new AbsoluteConstraints(600, 430, 50, 60));

        btn0KeyPad.setBackground(new Color(102, 102, 102));
        btn0KeyPad.setFont(new Font("Dialog", 1, 24)); 
        btn0KeyPad.setText("0");
        btn0KeyPad.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0)));
        btn0KeyPad.setBorderPainted(false);
        getContentPane().add(btn0KeyPad, new AbsoluteConstraints(650, 430, 50, 60));
        
        btnSwap.setText("Swap");
        getContentPane().add(btnSwap, new AbsoluteConstraints(50, 410, -1, -1));

        btnFunction.setText("Function");
        getContentPane().add(btnFunction, new AbsoluteConstraints(50, 250, 80, -1));

        btnDown.setBackground(new Color(0, 0, 0));
        btnDown.setText("");
        btnDown.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.black, Color.black, Color.black, Color.black));
        getContentPane().add(btnDown, new AbsoluteConstraints(170, 300, 20, -1));

        btnPrevious.setBackground(new Color(0, 0, 0));
        btnPrevious.setText("");
        btnPrevious.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.black, Color.black, Color.black, Color.black));
        getContentPane().add(btnPrevious, new AbsoluteConstraints(60, 300, 20, -1));

        btnNxt.setBackground(new Color(0, 0, 0));
        btnNxt.setText("");
        btnNxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.black, Color.black, Color.black, Color.black));
        getContentPane().add(btnNxt, new AbsoluteConstraints(90, 300, 20, -1));

        btnUp.setBackground(new Color(0, 0, 0));
        btnUp.setText("");
        btnUp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.black, Color.black, Color.black, Color.black));
        getContentPane().add(btnUp, new AbsoluteConstraints(150, 300, 20, -1));
        
        btnPrintPwr.setText("Printer Pwr");
        getContentPane().add(btnPrintPwr, new AbsoluteConstraints(590, 30, 170, -1));
    	
    }
    
    private void makeLbls(){
    	
        lblCh8Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh8Bk.setForeground(new Color(0, 0, 0));
        lblCh8Bk.setText("8");
        getContentPane().add(lblCh8Bk, new AbsoluteConstraints(200, 590, 10, -1));

        lblUsbBk.setFont(new Font("Dialog", 1, 12)); 
        lblUsbBk.setForeground(new Color(0, 0, 0));
        lblUsbBk.setText("USB PORT");
        getContentPane().add(lblUsbBk, new AbsoluteConstraints(420, 580, 70, -1));

        lblCh1Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh1Bk.setForeground(new Color(0, 0, 0));
        lblCh1Bk.setText("1");
        getContentPane().add(lblCh1Bk, new AbsoluteConstraints(80, 540, 10, -1));

        lblCh3Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh3Bk.setForeground(new Color(0, 0, 0));
        lblCh3Bk.setText("3");
        getContentPane().add(lblCh3Bk, new AbsoluteConstraints(120, 540, 10, -1));

        lblCh5Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh5Bk.setForeground(new Color(0, 0, 0));
        lblCh5Bk.setText("5");
        getContentPane().add(lblCh5Bk, new AbsoluteConstraints(160, 540, 10, -1));

        lblCh7Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh7Bk.setForeground(new Color(0, 0, 0));
        lblCh7Bk.setText("7");
        getContentPane().add(lblCh7Bk, new AbsoluteConstraints(200, 540, 10, -1));

        lblCh2Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh2Bk.setForeground(new Color(0, 0, 0));
        lblCh2Bk.setText("2");
        getContentPane().add(lblCh2Bk, new AbsoluteConstraints(80, 590, 10, -1));

        lblCh4Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh4Bk.setForeground(new Color(0, 0, 0));
        lblCh4Bk.setText("4");
        getContentPane().add(lblCh4Bk, new AbsoluteConstraints(120, 590, 10, -1));

        lblCh6Bk.setFont(new Font("Dialog", 1, 12)); 
        lblCh6Bk.setForeground(new Color(0, 0, 0));
        lblCh6Bk.setText("6");
        lblCh6Bk.setToolTipText("");
        getContentPane().add(lblCh6Bk, new AbsoluteConstraints(160, 590, 10, -1));

        lblChanBk.setFont(new Font("Dialog", 1, 12)); 
        lblChanBk.setForeground(new Color(0, 0, 0));
        lblChanBk.setText("CHAN");
        getContentPane().add(lblChanBk, new AbsoluteConstraints(20, 530, 40, -1));
        
        jLabel1.setText("Finish");
        getContentPane().add(jLabel1, new AbsoluteConstraints(210, 150, -1, -1));
        jLabel1.getAccessibleContext().setAccessibleName("Start");

        jLabel2.setText("Enable/Disable");
        getContentPane().add(jLabel2, new AbsoluteConstraints(160, 110, -1, -1));

        jLabel3.setText("Enable/Disable");
        getContentPane().add(jLabel3, new AbsoluteConstraints(160, 200, -1, -1));

        jLabel4.setText("Start");
        getContentPane().add(jLabel4, new AbsoluteConstraints(220, 70, -1, -1));

        label1.setText("Queue / Running / Final Time");
        getContentPane().add(label1, new AbsoluteConstraints(300, 520, 200, -1));

        jLabel5.setFont(new Font("Ebrima", 2, 24)); 
        jLabel5.setText("CHRONOTIMER 1009");
        getContentPane().add(jLabel5, new AbsoluteConstraints(210, 10, 310, -1));
    	
    }
    
    public void enableContent(){
    	
        btnCh1bk.setEnabled(!btnCh1bk.isEnabled());
    	btnCh2bk.setEnabled(!btnCh2bk.isEnabled());
    	btnCh3bk.setEnabled(!btnCh3bk.isEnabled());
    	btnCh4bk.setEnabled(!btnCh4bk.isEnabled());
    	btnCh5bk.setEnabled(!btnCh5bk.isEnabled());
    	btnCh6bk.setEnabled(!btnCh6bk.isEnabled());
    	btnCh7bk.setEnabled(!btnCh7bk.isEnabled());
    	btnCh8bk.setEnabled(!btnCh8bk.isEnabled());
    	btnTogCh1.setEnabled(!btnTogCh1.isEnabled());
    	btnTogCh2.setEnabled(!btnTogCh2.isEnabled());
    	btnTogCh3.setEnabled(!btnTogCh3.isEnabled());
    	btnTogCh4.setEnabled(!btnTogCh4.isEnabled());
    	btnTogCh5.setEnabled(!btnTogCh5.isEnabled());
    	btnTogCh6.setEnabled(!btnTogCh6.isEnabled());
    	btnTogCh7.setEnabled(!btnTogCh7.isEnabled());
    	btnTogCh8.setEnabled(!btnTogCh8.isEnabled());
    	btnFunction.setEnabled(!btnFunction.isEnabled());
    	btnDown.setEnabled(!btnDown.isEnabled());
    	btnNxt.setEnabled(!btnNxt.isEnabled());
    	btnPrevious.setEnabled(!btnPrevious.isEnabled());
    	btnSwap.setEnabled(!btnSwap.isEnabled());
    	btnTr5.setEnabled(!btnTr5.isEnabled());
    	btnTrg1.setEnabled(!btnTrg1.isEnabled());
    	btnTrg2.setEnabled(!btnTrg2.isEnabled());
    	btnTrg3.setEnabled(!btnTrg3.isEnabled());
    	btnTrg4.setEnabled(!btnTrg4.isEnabled());
    	btnTrg6.setEnabled(!btnTrg6.isEnabled());
    	btnTrg7.setEnabled(!btnTrg7.isEnabled());
    	btnTrg8.setEnabled(!btnTrg8.isEnabled());
    	btnUp.setEnabled(!btnUp.isEnabled());
    	btnPrintPwr.setEnabled(!btnPrintPwr.isEnabled());
    	btn3KeyPad.setEnabled(!btn3KeyPad.isEnabled());
    	btn6KeyPad.setEnabled(!btn6KeyPad.isEnabled());
    	btn9KeyPad.setEnabled(!btn9KeyPad.isEnabled());
    	btn1KeyPad.setEnabled(!btn1KeyPad.isEnabled());
    	btn2KeyPad.setEnabled(!btn2KeyPad.isEnabled());
    	btn4KeyPad.setEnabled(!btn4KeyPad.isEnabled());
    	btn5KeyPad.setEnabled(!btn5KeyPad.isEnabled());
    	btn8KeyPad.setEnabled(!btn8KeyPad.isEnabled());
    	btnHashKeyPad.setEnabled(!btnHashKeyPad.isEnabled());
    	btn7KeyPad.setEnabled(!btn7KeyPad.isEnabled());
    	btnStarKeyPad.setEnabled(!btnStarKeyPad.isEnabled());
    	btn0KeyPad.setEnabled(!btn0KeyPad.isEnabled());
    	
    }

    
//    private void actionListenerAssignement(){
//    	
//        btnPower.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                btnPowerActionPerformed(evt);
//            }
//        });
//    }
    
//    private void btnPowerActionPerformed(ActionEvent evt) {
//    }
    
    public void togChannelsForRace(){
    	
    	btnTogCh1.setSelected(!btnTogCh1.isSelected());
    	btnTogCh2.setSelected(!btnTogCh2.isSelected());
    	btnTogCh3.setSelected(!btnTogCh3.isSelected());
    	btnTogCh4.setSelected(!btnTogCh4.isSelected());
    	btnTogCh5.setSelected(!btnTogCh5.isSelected());
    	btnTogCh6.setSelected(!btnTogCh6.isSelected());
    	btnTogCh7.setSelected(!btnTogCh7.isSelected());
    	btnTogCh8.setSelected(!btnTogCh8.isSelected());
    	
    }


    public JToggleButton getBtnCh1bk() {
		return btnCh1bk;
	}

	public JToggleButton getBtnCh2bk() {
		return btnCh2bk;
	}

	public JToggleButton getBtnCh3bk() {
		return btnCh3bk;
	}

	public JToggleButton getBtnCh4bk() {
		return btnCh4bk;
	}

	public JToggleButton getBtnCh5bk() {
		return btnCh5bk;
	}

	public JToggleButton getBtnCh6bk() {
		return btnCh6bk;
	}

	public JToggleButton getBtnCh7bk() {
		return btnCh7bk;
	}

	public JToggleButton getBtnCh8bk() {
		return btnCh8bk;
	}

	public JToggleButton getBtnPower() {
		return btnPower;
	}

	public JToggleButton getBtnTogCh1() {
		return btnTogCh1;
	}

	public JToggleButton getBtnTogCh2() {
		return btnTogCh2;
	}

	public JToggleButton getBtnTogCh3() {
		return btnTogCh3;
	}

	public JToggleButton getBtnTogCh4() {
		return btnTogCh4;
	}

	public JToggleButton getBtnTogCh5() {
		return btnTogCh5;
	}

	public JToggleButton getBtnTogCh6() {
		return btnTogCh6;
	}

	public JToggleButton getBtnTogCh7() {
		return btnTogCh7;
	}

	public JToggleButton getBtnTogCh8() {
		return btnTogCh8;
	}

	public JToggleButton getUsbBtn() {
		return usbBtn;
	}

	public JButton getBtnDown() {
		return btnDown;
	}

	public JToggleButton getBtnFunction() {
		return btnFunction;
	}

	public JButton getBtnNxt() {
		return btnNxt;
	}

	public JButton getBtnPrevious() {
		return btnPrevious;
	}

	public JButton getBtnSwap() {
		return btnSwap;
	}

	public JButton getBtnTr5() {
		return btnTr5;
	}

	public JButton getBtnTrg1() {
		return btnTrg1;
	}

	public JButton getBtnTrg2() {
		return btnTrg2;
	}

	public JButton getBtnTrg3() {
		return btnTrg3;
	}

	public JButton getBtnTrg4() {
		return btnTrg4;
	}

	public JButton getBtnTrg6() {
		return btnTrg6;
	}

	public JButton getBtnTrg7() {
		return btnTrg7;
	}

	public JButton getBtnTrg8() {
		return btnTrg8;
	}

	public JButton getBtnUp() {
		return btnUp;
	}

	public JToggleButton getBtnPrintPwr() {
		return btnPrintPwr;
	}

	public JButton getBtn3KeyPad() {
		return btn3KeyPad;
	}

	public JButton getBtn6KeyPad() {
		return btn6KeyPad;
	}

	public JButton getBtn9KeyPad() {
		return btn9KeyPad;
	}

	public JButton getBtn1KeyPad() {
		return btn1KeyPad;
	}

	public JButton getBtn2KeyPad() {
		return btn2KeyPad;
	}

	public JButton getBtn4KeyPad() {
		return btn4KeyPad;
	}

	public JButton getBtn5KeyPad() {
		return btn5KeyPad;
	}

	public JButton getBtn8KeyPad() {
		return btn8KeyPad;
	}

	public JButton getBtnHashKeyPad() {
		return btnHashKeyPad;
	}

	public JButton getBtn7KeyPad() {
		return btn7KeyPad;
	}

	public JButton getBtnStarKeyPad() {
		return btnStarKeyPad;
	}

	public JButton getBtn0KeyPad() {
		return btn0KeyPad;
	}

	public JTextArea getTxtAMiddle() {
		return txtAMiddle;
	}
	
	public JTextArea getTxtBMiddle() {
		return txtBMiddle;
	}
	
	public JTextArea getTxtCMiddle() {
		return txtCMiddle;
	}

	public JTextArea getprinterTextArea() {
		return printerTextArea;
	}
	
	public JTextArea getTxtAConsole() {
		return txtAConsole;
	}
	
	public JPopupMenu getSensorMenu() {
		return sensorPopupMenu;
	}
	
	
}