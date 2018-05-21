/*    */ import java.io.File;
/*    */ 
/*    */ public class PictureHelper
/*    */ {
/*  5 */   private String mainTitlePrefix = "<p><strong>";
/*  6 */   private String subTitlePrefix = "</strong><br />";
/*  7 */   private String downloadPrefix = "</p>\n<div id=\"download\">\n<a href=\"gallery/";
/*  8 */   private String downloadPostfix = ".zip\"><img src=\"images/download.png\" alt=\"letöltés\"/></a>\n</div>\n<div id=\"gallery\">\n<ul>\n";
/*  9 */   private String prefix = "<li><img src=\"";
/* 10 */   private String midfix = "\" class=\"pic\"/><img src=\"";
/* 11 */   private String postfix = "\" class=\"mini\" width=\"120\" height=\"80\"/></li>";
/* 12 */   private String endfix = "</ul>\n</div>";
/*    */   
/* 14 */   private String webpath = "";
/* 15 */   private StringBuffer res = new StringBuffer();
/*    */   
/*    */   public PictureHelper(String wp, String mainTitle, String subTitle)
/*    */   {
/* 19 */     if (wp != null) {
/* 20 */       this.webpath = ("gallery/" + wp);
/*    */     }
/* 22 */     if ((!"".equals(this.webpath)) && (
/* 23 */       (!this.webpath.endsWith("/")) || (!this.webpath.endsWith("\\")))) {
/* 24 */       this.webpath += "/";
/*    */     }
/*    */     
/*    */ 
/* 28 */     this.res.append(this.mainTitlePrefix).append(mainTitle).append(this.subTitlePrefix).append(subTitle).append(this.downloadPrefix).append(wp).append("/").append(wp).append(this.downloadPostfix);
/*    */   }
/*    */   
/*    */   public String process(File[] files) {
/* 32 */     String fileName = "";
/*    */     
/*    */     File[] arrayOfFile;
/* 35 */     int j = (arrayOfFile = files).length; for (int i = 0; i < j; i++) { File f = arrayOfFile[i];
/* 36 */       fileName = f.getName();
/* 37 */       if (!fileName.contains("small"))
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 43 */         this.res.append(this.prefix).append(this.webpath).append(fileName).append(this.midfix).append(this.webpath).append(fileName.substring(0, fileName.length() - 4)).append("small").append(".jpg").append(this.postfix).append("\n");
/*    */       }
/*    */     }
/* 46 */     this.res.append(this.endfix);
/*    */     
/* 48 */     return this.res.toString();
/*    */   }
/*    */ }


/* Location:              d:\GalleryTool.jar!\PictureHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */