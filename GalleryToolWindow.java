/*    */ import java.awt.BorderLayout;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ 
/*    */ public class GalleryToolWindow
/*    */   extends JFrame
/*    */ {
/* 10 */   public final int MAIN_X = 300;
/* 11 */   public final int MAIN_Y = 200;
/*    */   
/* 13 */   private PicturePanel picturePanel = null;
/* 14 */   private ButtonPanel buttonPanel = null;
/*    */   
/*    */   public GalleryToolWindow() {
/* 17 */     GalleryToolModel.getInstance(this);
/* 18 */     initialize();
/* 19 */     pack();
/*    */   }
/*    */   
/*    */   private void initialize()
/*    */   {
/*    */     try {
/* 25 */       UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
/*    */     } catch (Exception e) {
/* 27 */       e.printStackTrace();
/*    */     }
/*    */     
/* 30 */     setTitle("GalleryTool 1.0");
/* 31 */     setDefaultCloseOperation(3);
/* 32 */     setLocation(300, 200);
/*    */     
/* 34 */     setContentPane(getMainPanel());
/*    */   }
/*    */   
/*    */   private JPanel getMainPanel() {
/* 38 */     JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
/* 39 */     mainPanel.add(getBlogPicturePanel(), "Center");
/* 40 */     mainPanel.add(getBlogButtonPanel(), "South");
/* 41 */     return mainPanel;
/*    */   }
/*    */   
/*    */   public PicturePanel getBlogPicturePanel() {
/* 45 */     if (this.picturePanel == null)
/* 46 */       this.picturePanel = new PicturePanel();
/* 47 */     return this.picturePanel;
/*    */   }
/*    */   
/*    */   public ButtonPanel getBlogButtonPanel() {
/* 51 */     if (this.buttonPanel == null)
/* 52 */       this.buttonPanel = new ButtonPanel(this);
/* 53 */     return this.buttonPanel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 60 */     new GalleryToolWindow().setVisible(true);
/*    */   }
/*    */ }


/* Location:              d:\GalleryTool.jar!\GalleryToolWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */