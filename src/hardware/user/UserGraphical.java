package hardware.user;



import org.netbeans.lib.awtextra.*;
import hardware.user.Triangular.Rotation;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGraphical extends JFrame {
	

	private JToggleButton btnCh1bk, btnCh2bk, btnCh3bk, btnCh4bk, btnCh5bk, btnCh6bk, btnCh7bk, btnCh8bk, 
    btnPower, btnTogCh1, btnTogCh2, btnTogCh3, btnTogCh4, btnTogCh5, btnTogCh6, btnTogCh7, btnTogCh8, usbBtn;
    
    private JButton btnDown, btnFunction, btnNxt, btnPrevious, btnSwap, btnTr5, btnTrg1, btnTrg2, btnTrg3, 
    btnTrg4, btnTrg6, btnTrg7, btnTrg8, btnUp, btnPrintPwr, btn3KeyPad, btn6KeyPad, btn9KeyPad, btn1KeyPad, 
    btn2KeyPad, btn4KeyPad, btn5KeyPad, btn8KeyPad, btnHashKeyPad, btn7KeyPad, btnStarKeyPad, btn0KeyPad;
    private JLabel jLabel1, lblCh5Bk, lblCh7Bk, lblCh2Bk, lblCh4Bk, lblCh6Bk, lblChanBk, jLabel2, jLabel3, jLabel4, jLabel5, lblCh8Bk, lblUsbBk, lblCh1Bk, 
    lblCh3Bk;
    
    private JPanel jPanel1, jPanel2, jPanel3, jPanel4, jPanel5, jPanel6;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private JTextField jTextField2;
    private Label label1;
    
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
    	
        btnPower = new JToggleButton();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        btn3KeyPad = new JButton();
        btn1KeyPad = new JButton();
        btn2KeyPad = new JButton();
        btn4KeyPad = new JButton();
        btn5KeyPad = new JButton();
        btn6KeyPad = new JButton();
        btn8KeyPad = new JButton();
        btnHashKeyPad = new JButton();
        btn9KeyPad = new JButton();
        btn7KeyPad = new JButton();
        btnStarKeyPad = new JButton();
        btn0KeyPad = new JButton();
        label1 = new Label();
        btnPrintPwr = new JButton();
        jLabel5 = new JLabel();
        jTextField2 = new JTextField();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jPanel1 = new JPanel();
        btnTrg1 = new JButton();
        btnTrg3 = new JButton();
        btnTr5 = new JButton();
        btnTrg7 = new JButton();
        jPanel2 = new JPanel();
        btnTogCh1 = new JToggleButton();
        btnTogCh3 = new JToggleButton();
        btnTogCh5 = new JToggleButton();
        btnTogCh7 = new JToggleButton();
        jPanel3 = new JPanel();
        btnTrg2 = new JButton();
        btnTrg4 = new JButton();
        btnTrg6 = new JButton();
        btnTrg8 = new JButton();
        jPanel4 = new JPanel();
        btnTogCh2 = new JToggleButton();
        btnTogCh4 = new JToggleButton();
        btnTogCh6 = new JToggleButton();
        btnTogCh8 = new JToggleButton();
        btnSwap = new JButton();
        btnFunction = new JButton();
        btnDown = new Triangular(Rotation.UP);
        btnPrevious = new Triangular(Rotation.RIGHT);
        btnNxt = new Triangular(Rotation.LEFT);
        btnUp = new Triangular(Rotation.DOWN);
        jPanel5 = new JPanel();
        btnCh1bk = new JToggleButton();
        btnCh3bk = new JToggleButton();
        btnCh5bk = new JToggleButton();
        btnCh7bk = new JToggleButton();
        jPanel6 = new JPanel();
        btnCh2bk = new JToggleButton();
        btnCh4bk = new JToggleButton();
        btnCh6bk = new JToggleButton();
        btnCh8bk = new JToggleButton();
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
        

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 700));
        this.setPreferredSize(new Dimension(900, 700));
        this.getContentPane().setLayout(new AbsoluteLayout());
        
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



        label1.setText("Queue /Running/ Final Time");
        getContentPane().add(label1, new AbsoluteConstraints(270, 520, 160, -1));



        jLabel5.setFont(new Font("Ebrima", 2, 24)); 
        jLabel5.setText("CHRONOTIMER 1009");
        getContentPane().add(jLabel5, new AbsoluteConstraints(210, 10, 310, -1));
    	
    }
    
    
    private void makePnls(){
    	
        jTextField2.setEditable(false);
        jTextField2.setToolTipText("");
        getContentPane().add(jTextField2, new AbsoluteConstraints(570, 70, 220, 150));

        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new AbsoluteConstraints(210, 250, 300, 260));

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
    


    protected JToggleButton getBtnCh1bk() {
		return btnCh1bk;
	}

	protected JToggleButton getBtnCh2bk() {
		return btnCh2bk;
	}

	protected JToggleButton getBtnCh3bk() {
		return btnCh3bk;
	}

	protected JToggleButton getBtnCh4bk() {
		return btnCh4bk;
	}

	protected JToggleButton getBtnCh5bk() {
		return btnCh5bk;
	}

	protected JToggleButton getBtnCh6bk() {
		return btnCh6bk;
	}

	protected JToggleButton getBtnCh7bk() {
		return btnCh7bk;
	}

	protected JToggleButton getBtnCh8bk() {
		return btnCh8bk;
	}

	protected JToggleButton getBtnPower() {
		return btnPower;
	}

	protected JToggleButton getBtnTogCh1() {
		return btnTogCh1;
	}

	protected JToggleButton getBtnTogCh2() {
		return btnTogCh2;
	}

	protected JToggleButton getBtnTogCh3() {
		return btnTogCh3;
	}

	protected JToggleButton getBtnTogCh4() {
		return btnTogCh4;
	}

	protected JToggleButton getBtnTogCh5() {
		return btnTogCh5;
	}

	protected JToggleButton getBtnTogCh6() {
		return btnTogCh6;
	}

	protected JToggleButton getBtnTogCh7() {
		return btnTogCh7;
	}

	protected JToggleButton getBtnTogCh8() {
		return btnTogCh8;
	}

	protected JToggleButton getUsbBtn() {
		return usbBtn;
	}

	protected JButton getBtnDown() {
		return btnDown;
	}

	protected JButton getBtnFunction() {
		return btnFunction;
	}

	protected JButton getBtnNxt() {
		return btnNxt;
	}

	protected JButton getBtnPrevious() {
		return btnPrevious;
	}

	protected JButton getBtnSwap() {
		return btnSwap;
	}

	protected JButton getBtnTr5() {
		return btnTr5;
	}

	protected JButton getBtnTrg1() {
		return btnTrg1;
	}

	protected JButton getBtnTrg2() {
		return btnTrg2;
	}

	protected JButton getBtnTrg3() {
		return btnTrg3;
	}

	protected JButton getBtnTrg4() {
		return btnTrg4;
	}

	protected JButton getBtnTrg6() {
		return btnTrg6;
	}

	protected JButton getBtnTrg7() {
		return btnTrg7;
	}

	protected JButton getBtnTrg8() {
		return btnTrg8;
	}

	protected JButton getBtnUp() {
		return btnUp;
	}

	protected JButton getBtnPrintPwr() {
		return btnPrintPwr;
	}

	protected JButton getBtn3KeyPad() {
		return btn3KeyPad;
	}

	protected JButton getBtn6KeyPad() {
		return btn6KeyPad;
	}

	protected JButton getBtn9KeyPad() {
		return btn9KeyPad;
	}

	protected JButton getBtn1KeyPad() {
		return btn1KeyPad;
	}

	protected JButton getBtn2KeyPad() {
		return btn2KeyPad;
	}

	protected JButton getBtn4KeyPad() {
		return btn4KeyPad;
	}

	protected JButton getBtn5KeyPad() {
		return btn5KeyPad;
	}

	protected JButton getBtn8KeyPad() {
		return btn8KeyPad;
	}

	protected JButton getBtnHashKeyPad() {
		return btnHashKeyPad;
	}

	protected JButton getBtn7KeyPad() {
		return btn7KeyPad;
	}

	protected JButton getBtnStarKeyPad() {
		return btnStarKeyPad;
	}

	protected JButton getBtn0KeyPad() {
		return btn0KeyPad;
	}

	protected JTextArea getjTextArea1() {
		return jTextArea1;
	}

	protected JTextField getjTextField2() {
		return jTextField2;
	}
}