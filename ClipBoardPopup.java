/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.text.JTextComponent;
/*    */ 
/*    */ public class ClipBoardPopup
/*    */   extends MouseAdapter
/*    */   implements ActionListener
/*    */ {
/* 13 */   private JMenuItem copy = new JMenuItem("Másolás");
/* 14 */   private JPopupMenu menu = new JPopupMenu();
/*    */   
/*    */   private JTextComponent owner;
/*    */   private static ClipBoardPopup me;
/*    */   
/*    */   private ClipBoardPopup()
/*    */   {
/* 21 */     this.copy.addActionListener(this);
/* 22 */     this.menu.add(this.copy);
/*    */   }
/*    */   
/*    */   public static ClipBoardPopup getInstance() {
/* 26 */     if (me == null) {
/* 27 */       me = new ClipBoardPopup();
/*    */     }
/* 29 */     return me;
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 33 */     if ((this.owner != null) && 
/* 34 */       (e.getSource() == this.copy)) {
/* 35 */       this.owner.setSelectionStart(0);
/* 36 */       this.owner.setSelectionEnd(this.owner.getText().length());
/* 37 */       this.owner.copy();
/*    */     }
/*    */   }
/*    */   
/*    */   public void mouseClicked(MouseEvent e)
/*    */   {
/* 43 */     if ((e.getButton() == 3) && 
/* 44 */       ((e.getSource() instanceof JTextComponent))) {
/* 45 */       this.owner = ((JTextComponent)e.getSource());
/* 46 */       this.menu.show(this.owner, e.getX(), e.getY());
/*    */     }
/*    */   }
/*    */ }


/* Location:              d:\GalleryTool.jar!\ClipBoardPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */