/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.Point;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.border.TitledBorder;
/*    */ 
/*    */ public class ResultDialog
/*    */   extends JDialog
/*    */ {
/*    */   private static final int FIELD_WIDTH = 80;
/* 19 */   private JPanel mainPanel = null;
/* 20 */   private JPanel textPanel = null;
/* 21 */   private JPanel buttonPanel = null;
/* 22 */   private JScrollPane postPane = null;
/* 23 */   private JTextArea post = null;
/* 24 */   private JButton close = null;
/*    */   
/*    */   public ResultDialog(GalleryToolWindow parent) {
/* 27 */     super(parent);
/* 28 */     setModal(true);
/* 29 */     setTitle("Poszt");
/* 30 */     setLocation(parent.getLocation().x + 30, 
/* 31 */       parent.getLocation().y + 50);
/* 32 */     setContentPane(getMainPanel());
/* 33 */     pack();
/*    */   }
/*    */   
/*    */   private JPanel bp(JComponent c, String t) {
/* 37 */     JPanel borderPanel = new JPanel(
/* 38 */       new FlowLayout(3, 5, 5));
/* 39 */     borderPanel.setBorder(new TitledBorder(t));
/* 40 */     borderPanel.add(c);
/* 41 */     return borderPanel;
/*    */   }
/*    */   
/*    */   public JPanel getMainPanel() {
/* 45 */     this.mainPanel = new JPanel(new BorderLayout(0, 0));
/* 46 */     this.mainPanel.add(getTextPanel(), "Center");
/* 47 */     this.mainPanel.add(getButtonPanel(), "South");
/* 48 */     return this.mainPanel;
/*    */   }
/*    */   
/*    */   public JPanel getTextPanel() {
/* 52 */     this.textPanel = new JPanel(new BorderLayout(0, 0));
/* 53 */     this.textPanel.add(bp(getPostPane(), "Szöveg"), "Center");
/* 54 */     return this.textPanel;
/*    */   }
/*    */   
/*    */   public JPanel getButtonPanel() {
/* 58 */     this.buttonPanel = new JPanel(new FlowLayout(4, 5, 5));
/* 59 */     this.buttonPanel.add(getClose());
/* 60 */     return this.buttonPanel;
/*    */   }
/*    */   
/*    */   public JScrollPane getPostPane() {
/* 64 */     this.postPane = new JScrollPane(20, 
/* 65 */       30);
/* 66 */     this.postPane.setViewportView(getPost());
/* 67 */     return this.postPane;
/*    */   }
/*    */   
/*    */   public JTextArea getPost() {
/* 71 */     if (this.post != null)
/* 72 */       return this.post;
/* 73 */     this.post = new JTextArea(20, 80);
/* 74 */     this.post.setFont(new JTextField().getFont());
/* 75 */     this.post.setWrapStyleWord(true);
/* 76 */     this.post.setLineWrap(true);
/* 77 */     this.post.addMouseListener(ClipBoardPopup.getInstance());
/* 78 */     return this.post;
/*    */   }
/*    */   
/*    */   public JButton getClose() {
/* 82 */     this.close = new JButton("Bezár");
/* 83 */     this.close.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 85 */         ResultDialog.this.dispose();
/*    */       }
/* 87 */     });
/* 88 */     return this.close;
/*    */   }
/*    */ }


/* Location:              d:\GalleryTool.jar!\ResultDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */