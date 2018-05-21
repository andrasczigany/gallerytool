/*    */ import java.awt.Dimension;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ public class ButtonPanel
/*    */   extends JPanel
/*    */ {
/* 13 */   private JFrame parent = null;
/*    */   
/*    */   public ButtonPanel(JFrame p) {
/* 16 */     this.parent = p;
/*    */     
/* 18 */     JButton bCompose = new JButton("Mehet");
/* 19 */     bCompose.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 21 */         GalleryToolModel.getInstance().compose();
/*    */       }
/*    */       
/* 24 */     });
/* 25 */     JButton bExit = new JButton("Kilépés");
/* 26 */     bExit.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 28 */         ButtonPanel.this.parent.dispose();
/*    */       }
/*    */       
/* 31 */     });
/* 32 */     int buttonWidth = Math.max(bCompose.getPreferredSize().width, 
/* 33 */       bExit.getPreferredSize().width);
/* 34 */     bCompose.setPreferredSize(new Dimension(buttonWidth, 
/* 35 */       bCompose.getPreferredSize().height));
/* 36 */     bExit.setPreferredSize(new Dimension(buttonWidth, 
/* 37 */       bExit.getPreferredSize().height));
/*    */     
/* 39 */     setLayout(new FlowLayout(4, 5, 5));
/* 40 */     add(bCompose);
/* 41 */     add(bExit);
/*    */   }
/*    */ }


/* Location:              d:\GalleryTool.jar!\ButtonPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */