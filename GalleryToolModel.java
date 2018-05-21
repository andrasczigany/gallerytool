/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class GalleryToolModel
/*    */ {
/*  5 */   private static GalleryToolModel instance = null;
/*  6 */   private static GalleryToolWindow mainWindow = null;
/*    */   
/*    */ 
/*    */ 
/*    */   public static GalleryToolModel getInstance()
/*    */   {
/* 12 */     if (instance == null) {
/* 13 */       instance = new GalleryToolModel();
/*    */     }
/* 15 */     return instance;
/*    */   }
/*    */   
/*    */   public static GalleryToolModel getInstance(GalleryToolWindow window) {
/* 19 */     if (instance == null) {
/* 20 */       instance = new GalleryToolModel();
/* 21 */       mainWindow = window;
/*    */     }
/* 23 */     return instance;
/*    */   }
/*    */   
/*    */   public void compose() {
/* 27 */     ResultDialog dialog = new ResultDialog(mainWindow);
/* 28 */     dialog.getPost().setText(composePost());
/* 29 */     dialog.getPost().selectAll();
/* 30 */     dialog.getPost().copy();
/* 31 */     dialog.setVisible(true);
/*    */   }
/*    */   
/*    */   private String composePost() {
/* 35 */     PicturePanel pp = mainWindow.getBlogPicturePanel();
/* 36 */     StringBuilder free = new StringBuilder("");
/*    */     
/*    */ 
/* 39 */     StringBuilder pics = new StringBuilder("");
/* 40 */     java.io.File[] files = pp.getFileList();
/* 41 */     if (files.length > 0) {
/* 42 */       PictureHelper ph = new PictureHelper(pp.getIWebDir().getText(), pp
/* 43 */         .getIMain().getText(), pp.getISub().getText());
/* 44 */       pics.append(ph.process(files)).append("\n");
/*    */     }
/*    */     
/* 47 */     return pics;
/*    */   }
/*    */ }


/* Location:              d:\GalleryTool.jar!\GalleryToolModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */