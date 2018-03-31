package hardware.user;

public class InterfaceHandler {
	
	private UserGraphical userInterface;
	
	public InterfaceHandler(){
		
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		this.userInterface = UserGraphical.getSingleton();
		this.userInterface.setVisible(true);     //Temporary true
		
	}
	
	
	public UserGraphical getUserInterface() {
		return userInterface;
	}
}