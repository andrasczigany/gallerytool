/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlexibleLayout
/*     */   implements LayoutManager2
/*     */ {
/*     */   public static final int ROW_TYPE_SSSS = 1;
/*     */   public static final int ROW_TYPE_MM = 2;
/*     */   public static final int ROW_TYPE_L = 3;
/*     */   public static final int ROW_TYPE_MSS = 4;
/*     */   public static final int ROW_TYPE_SSM = 5;
/*     */   private static final int COL_NUM = 4;
/*     */   private int rowNum;
/*  21 */   private int hGap = 3;
/*  22 */   private int vGap = 3;
/*     */   private Component[][] bodis;
/*     */   private Component[][] heads;
/*     */   private int[] rowTypes;
/*     */   
/*     */   public FlexibleLayout()
/*     */   {
/*  29 */     initilazie();
/*     */   }
/*     */   
/*     */   private void initilazie() {
/*  33 */     this.bodis = new Component[this.rowNum][4];
/*  34 */     this.heads = new Component[this.rowNum][4];
/*  35 */     this.rowTypes = new int[this.rowNum];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getLayoutAlignmentX(Container target)
/*     */   {
/*  44 */     return 0.5F;
/*     */   }
/*     */   
/*     */   public float getLayoutAlignmentY(Container target) {
/*  48 */     return 0.5F;
/*     */   }
/*     */   
/*     */   public void invalidateLayout(Container target) {}
/*     */   
/*     */   public Dimension maximumLayoutSize(Container target)
/*     */   {
/*  55 */     synchronized (target.getTreeLock()) {
/*  56 */       Dimension dim = new Dimension(0, 0);
/*  57 */       Dimension localDim = new Dimension(0, 0);
/*     */       
/*  59 */       Dimension c = null;
/*     */       
/*  61 */       for (int row = 0; row < this.rowNum; row++) {
/*  62 */         localDim.width = 0;
/*  63 */         localDim.height = 0;
/*  64 */         for (int col = 0; col < 4; col++) {
/*  65 */           c = getItemMaxSize(row, col);
/*  66 */           if (c != null) {
/*  67 */             localDim.width += c.width;
/*  68 */             localDim.height = Math.max(localDim.height, c.height);
/*     */           }
/*     */         }
/*  71 */         dim.width = Math.max(dim.width, localDim.width);
/*  72 */         dim.height += localDim.height;
/*     */       }
/*     */       
/*  75 */       Insets insets = target.getInsets();
/*  76 */       dim.width += insets.left + insets.right;
/*  77 */       dim.height += insets.top + insets.bottom;
/*     */       
/*     */ 
/*     */ 
/*  81 */       return dim;
/*     */     }
/*     */   }
/*     */   
/*     */   public void addLayoutComponent(Component comp, Object constraints) {
/*  86 */     synchronized (comp.getTreeLock())
/*     */     {
/*     */ 
/*  89 */       if (constraints == null) {
/*  90 */         constraints = new FlexibleLayout.FlexibleDefinitor(0, 0, false);
/*     */       }
/*     */       
/*  93 */       if ((constraints instanceof Point)) {
/*  94 */         Point p = (Point)constraints;
/*  95 */         constraints = new FlexibleLayout.FlexibleDefinitor(p.x, p.y);
/*     */       }
/*     */       
/*  98 */       if ((constraints instanceof FlexibleLayout.FlexibleDefinitor)) {
/*  99 */         FlexibleLayout.FlexibleDefinitor coord = (FlexibleLayout.FlexibleDefinitor)constraints;
/* 100 */         if ((coord.getCol() >= 4) || (coord.getRow() >= this.rowNum)) {
/* 101 */           throw new IllegalArgumentException(
/* 102 */             "cannot add to layout: location is outside from range " + 
/* 103 */             constraints);
/*     */         }
/* 105 */         if (coord.isBody()) {
/* 106 */           this.bodis[coord.getRow()][coord.getCol()] = comp;
/*     */         } else {
/* 108 */           this.heads[coord.getRow()][coord.getCol()] = comp;
/*     */         }
/*     */       } else {
/* 111 */         throw new IllegalArgumentException(
/* 112 */           "cannot add to layout: unknown location: " + 
/* 113 */           constraints);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeLayoutComponent(Component comp) {
/* 119 */     synchronized (comp.getTreeLock())
/*     */     {
/* 121 */       for (int x = 0; x < this.rowNum; x++) {
/* 122 */         for (int y = 0; y < 4; y++) {
/* 123 */           if (this.bodis[x][y] == comp) {
/* 124 */             this.bodis[x][y] = null;
/*     */           }
/* 126 */           if (this.heads[x][y] == comp) {
/* 127 */             this.heads[x][y] = null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void layoutContainer(Container parent) {
/* 135 */     synchronized (parent.getTreeLock())
/*     */     {
/*     */ 
/* 138 */       Insets insets = parent.getInsets();
/* 139 */       int top = insets.top;
/* 140 */       int bottom = parent.getSize().height - insets.bottom;
/* 141 */       int left = insets.left;
/* 142 */       int right = parent.getSize().width - insets.right;
/*     */       
/*     */ 
/* 145 */       int maxHeight = 0;
/* 146 */       for (int i = 0; i < this.rowNum; i++) {
/* 147 */         maxHeight += getMaximumPrefHeight(i);
/*     */       }
/* 149 */       int heightResidue = (bottom - top - maxHeight) / this.rowNum;
/*     */       
/* 151 */       FlexibleLayout.MaxValueHolder max = calculateMaxValues(left, right);
/*     */       
/* 153 */       int actualHeight = 0;
/* 154 */       Dimension actDim = null;
/*     */       
/* 156 */       int[] maxHeadPref = getMaximumHeadPrefWidth();
/*     */       
/* 158 */       int l = 0;
/* 159 */       Component c1 = null;
/* 160 */       Component c2 = null;
/* 161 */       Component c3 = null;
/* 162 */       Component c4 = null;
/*     */       
/* 164 */       Component h = null;
/*     */       
/* 166 */       for (int row = 0; row < this.rowNum; row++) {
/* 167 */         actualHeight = getMaximumPrefHeight(row) + heightResidue;
/* 168 */         if (row == this.rowNum - 1) {
/* 169 */           actualHeight = bottom - top;
/*     */         }
/* 171 */         c1 = this.bodis[row][0];
/* 172 */         c2 = this.bodis[row][1];
/* 173 */         c3 = this.bodis[row][2];
/* 174 */         c4 = this.bodis[row][3];
/*     */         
/* 176 */         switch (this.rowTypes[row]) {
/*     */         case 1: 
/* 178 */           if (c1 != null) {
/* 179 */             h = this.heads[row][0];
/* 180 */             if (h != null) {
/* 181 */               l = maxHeadPref[0];
/* 182 */               c1.setSize(max.ssss[0] - maxHeadPref[0], 
/* 183 */                 actualHeight);
/* 184 */               h.setBounds(left, top, maxHeadPref[0], actualHeight);
/*     */             } else {
/* 186 */               l = left;
/* 187 */               c1.setSize(max.ssss[0], actualHeight);
/*     */             }
/* 189 */             actDim = c1.getSize();
/* 190 */             c1.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/* 192 */           if (c2 != null) {
/* 193 */             h = this.heads[row][1];
/* 194 */             if (h != null) {
/* 195 */               l = left + max.ssss[0] + maxHeadPref[1];
/* 196 */               c2.setSize(max.ssss[1] - maxHeadPref[1], 
/* 197 */                 actualHeight);
/* 198 */               h.setBounds(left + max.ssss[0], top, 
/* 199 */                 maxHeadPref[1], actualHeight);
/*     */             } else {
/* 201 */               l = left + max.ssss[0];
/* 202 */               c2.setSize(max.ssss[1], actualHeight);
/*     */             }
/* 204 */             actDim = c2.getSize();
/* 205 */             c2.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/* 207 */           if (c3 != null) {
/* 208 */             h = this.heads[row][2];
/* 209 */             if (h != null) {
/* 210 */               l = left + max.ssss[0] + max.ssss[1] + 
/* 211 */                 maxHeadPref[2];
/* 212 */               c3.setSize(max.ssss[2] - maxHeadPref[2], 
/* 213 */                 actualHeight);
/* 214 */               h.setBounds(left + max.ssss[0] + max.ssss[1], top, 
/* 215 */                 maxHeadPref[2], actualHeight);
/*     */             } else {
/* 217 */               l = left + max.ssss[0] + max.ssss[1];
/* 218 */               c3.setSize(max.ssss[2], actualHeight);
/*     */             }
/* 220 */             actDim = c3.getSize();
/* 221 */             c3.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/* 223 */           if (c4 != null) {
/* 224 */             h = this.heads[row][3];
/* 225 */             l = left + max.ssss[0] + max.ssss[1] + max.ssss[2];
/* 226 */             if (h != null) {
/* 227 */               h.setBounds(l, top, maxHeadPref[3], actualHeight);
/* 228 */               c4.setSize(max.ssss[3] - maxHeadPref[3], 
/* 229 */                 actualHeight);
/* 230 */               l += maxHeadPref[3];
/*     */             } else {
/* 232 */               c4.setSize(max.ssss[3], actualHeight);
/*     */             }
/* 234 */             actDim = c4.getSize();
/* 235 */             c4.setBounds(l, top, right - l, actDim.height);
/*     */           }
/* 237 */           break;
/*     */         case 2: 
/* 239 */           if (c1 != null) {
/* 240 */             h = this.heads[row][0];
/* 241 */             l = left;
/* 242 */             if (h != null) {
/* 243 */               c1.setSize(max.mm[0] - maxHeadPref[0], actualHeight);
/* 244 */               h.setBounds(l, top, maxHeadPref[0], actualHeight);
/* 245 */               l += maxHeadPref[0];
/*     */             } else {
/* 247 */               c1.setSize(max.mm[0], actualHeight);
/*     */             }
/* 249 */             actDim = c1.getSize();
/* 250 */             c1.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/*     */           
/* 253 */           if (c3 != null) {
/* 254 */             h = this.heads[row][2];
/* 255 */             l = left + max.mm[0];
/* 256 */             if (h != null) {
/* 257 */               c3.setSize(max.mm[1] - maxHeadPref[2], actualHeight);
/* 258 */               h.setBounds(l, top, maxHeadPref[2], actualHeight);
/* 259 */               l += maxHeadPref[2];
/*     */             } else {
/* 261 */               c3.setSize(max.mm[1], actualHeight);
/*     */             }
/* 263 */             actDim = c3.getSize();
/* 264 */             c3.setBounds(l, top, right - l, actDim.height);
/*     */           }
/* 266 */           break;
/*     */         case 3: 
/* 268 */           if (c1 != null) {
/* 269 */             h = this.heads[row][0];
/* 270 */             l = left;
/* 271 */             if (h != null) {
/* 272 */               c1.setSize(max.l - maxHeadPref[0], actualHeight);
/* 273 */               h.setBounds(l, top, maxHeadPref[0], actualHeight);
/* 274 */               l += maxHeadPref[0];
/*     */             } else {
/* 276 */               c1.setSize(max.l, actualHeight);
/*     */             }
/* 278 */             actDim = c1.getSize();
/* 279 */             c1.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/* 281 */           break;
/*     */         case 4: 
/* 283 */           if (c1 != null) {
/* 284 */             h = this.heads[row][0];
/* 285 */             l = left;
/* 286 */             if (h != null) {
/* 287 */               c1.setSize(max.mm[0] - maxHeadPref[0], actualHeight);
/* 288 */               h.setBounds(l, top, maxHeadPref[0], actualHeight);
/* 289 */               l += maxHeadPref[0];
/*     */             } else {
/* 291 */               c1.setSize(max.mm[0], actualHeight);
/*     */             }
/* 293 */             actDim = c1.getSize();
/* 294 */             c1.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/*     */           
/* 297 */           if (c3 != null) {
/* 298 */             h = this.heads[row][2];
/* 299 */             if (h != null) {
/* 300 */               l = left + max.ssss[0] + max.ssss[1] + 
/* 301 */                 maxHeadPref[2];
/* 302 */               c3.setSize(max.ssss[2] - maxHeadPref[2], 
/* 303 */                 actualHeight);
/* 304 */               h.setBounds(left + max.ssss[0] + max.ssss[1], top, 
/* 305 */                 maxHeadPref[2], actualHeight);
/*     */             } else {
/* 307 */               l = left + max.ssss[0] + max.ssss[1];
/* 308 */               c3.setSize(max.ssss[2], actualHeight);
/*     */             }
/* 310 */             actDim = c3.getSize();
/* 311 */             c3.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/* 313 */           if (c4 != null) {
/* 314 */             h = this.heads[row][3];
/* 315 */             l = left + max.ssss[0] + max.ssss[1] + max.ssss[2];
/* 316 */             if (h != null) {
/* 317 */               h.setBounds(l, top, maxHeadPref[3], actualHeight);
/* 318 */               c4.setSize(max.ssss[3] - maxHeadPref[3], 
/* 319 */                 actualHeight);
/* 320 */               l += maxHeadPref[3];
/*     */             } else {
/* 322 */               c4.setSize(max.ssss[3], actualHeight);
/*     */             }
/* 324 */             actDim = c4.getSize();
/* 325 */             c4.setBounds(l, top, right - l, actDim.height);
/*     */           }
/* 327 */           break;
/*     */         case 5: 
/* 329 */           if (c1 != null) {
/* 330 */             h = this.heads[row][0];
/* 331 */             l = left;
/* 332 */             if (h != null) {
/* 333 */               c1.setSize(max.ssss[0] - maxHeadPref[0], 
/* 334 */                 actualHeight);
/* 335 */               h.setBounds(left, top, maxHeadPref[0], actualHeight);
/* 336 */               l += maxHeadPref[0];
/*     */             } else {
/* 338 */               c1.setSize(max.ssss[0], actualHeight);
/*     */             }
/* 340 */             actDim = c1.getSize();
/* 341 */             c1.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/* 343 */           if (c2 != null) {
/* 344 */             h = this.heads[row][1];
/* 345 */             if (h != null) {
/* 346 */               l = left + max.ssss[0] + maxHeadPref[1];
/* 347 */               c2.setSize(max.ssss[1] - maxHeadPref[1], 
/* 348 */                 actualHeight);
/* 349 */               h.setBounds(left + max.ssss[0], top, 
/* 350 */                 maxHeadPref[1], actualHeight);
/*     */             } else {
/* 352 */               l = left + max.ssss[0];
/* 353 */               c2.setSize(max.ssss[1], actualHeight);
/*     */             }
/* 355 */             actDim = c2.getSize();
/* 356 */             c2.setBounds(l, top, actDim.width, actDim.height);
/*     */           }
/* 358 */           if (c3 != null) {
/* 359 */             h = this.heads[row][2];
/* 360 */             l = left + max.mm[0];
/* 361 */             if (h != null) {
/* 362 */               c3.setSize(max.mm[1] - maxHeadPref[2], actualHeight);
/* 363 */               h.setBounds(l, top, maxHeadPref[2], actualHeight);
/* 364 */               l += maxHeadPref[2];
/*     */             } else {
/* 366 */               c3.setSize(max.mm[1], actualHeight);
/*     */             }
/* 368 */             actDim = c3.getSize();
/* 369 */             c3.setBounds(l, top, right - l, actDim.height);
/*     */           }
/*     */           break;
/*     */         }
/* 373 */         top = top + actualHeight;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void addLayoutComponent(String name, Component comp)
/*     */   {
/* 384 */     throw new IllegalArgumentException(
/* 385 */       "cannot add to layout: this method not suit for this layout manager");
/*     */   }
/*     */   
/*     */   public Dimension minimumLayoutSize(Container parent) {
/* 389 */     synchronized (parent.getTreeLock()) {
/* 390 */       Dimension dim = new Dimension(0, 0);
/* 391 */       Dimension localDim = new Dimension(0, 0);
/*     */       
/* 393 */       Dimension c = null;
/*     */       
/* 395 */       for (int row = 0; row < this.rowNum; row++) {
/* 396 */         localDim.width = 0;
/* 397 */         localDim.height = 0;
/* 398 */         for (int col = 0; col < 4; col++) {
/* 399 */           c = getItemMinSize(row, col);
/* 400 */           if (c != null) {
/* 401 */             localDim.width += c.width;
/* 402 */             localDim.height = Math.max(localDim.height, c.height);
/*     */           }
/*     */         }
/* 405 */         dim.width = Math.max(dim.width, localDim.width);
/* 406 */         dim.height += localDim.height;
/*     */       }
/*     */       
/* 409 */       Insets insets = parent.getInsets();
/* 410 */       dim.width += insets.left + insets.right;
/* 411 */       dim.height += insets.top + insets.bottom;
/*     */       
/*     */ 
/*     */ 
/* 415 */       return dim;
/*     */     }
/*     */   }
/*     */   
/*     */   public Dimension preferredLayoutSize(Container parent) {
/* 420 */     synchronized (parent.getTreeLock()) {
/* 421 */       Dimension dim = new Dimension(0, 0);
/* 422 */       Dimension localDim = new Dimension(0, 0);
/*     */       
/* 424 */       Dimension c = null;
/*     */       
/* 426 */       for (int row = 0; row < this.rowNum; row++) {
/* 427 */         localDim.width = 0;
/* 428 */         localDim.height = 0;
/* 429 */         for (int col = 0; col < 4; col++) {
/* 430 */           c = getItemPrefSize(row, col);
/* 431 */           if (c != null) {
/* 432 */             localDim.width += c.width;
/* 433 */             localDim.height = Math.max(localDim.height, c.height);
/*     */           }
/*     */         }
/* 436 */         dim.width = Math.max(dim.width, localDim.width);
/* 437 */         dim.height += localDim.height;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 442 */       dim.width = Math.max(dim.width, calculateMaxValues(0, 0).l);
/*     */       
/* 444 */       Insets insets = parent.getInsets();
/* 445 */       dim.width += insets.left + insets.right;
/* 446 */       dim.height += insets.top + insets.bottom;
/*     */       
/*     */ 
/*     */ 
/* 450 */       return dim;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRowNum()
/*     */   {
/* 459 */     return this.rowNum;
/*     */   }
/*     */   
/*     */   public void setRowNum(int rowNum) {
/* 463 */     this.rowNum = rowNum;
/* 464 */     initilazie();
/*     */   }
/*     */   
/*     */   public void setRowType(int row, int type) {
/*     */     try {
/* 469 */       if (this.rowTypes != null) {
/* 470 */         this.rowTypes[row] = type;
/*     */       } else {
/* 472 */         throw new IllegalArgumentException(
/* 473 */           "cannot set the row's type: the row set is empty");
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException e) {
/* 476 */       throw new IllegalArgumentException(
/* 477 */         "cannot set the row's type: maximum row's number is " + 
/* 478 */         this.rowNum);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int[] getMaximumHeadPrefWidth()
/*     */   {
/* 500 */     int[] w = new int[4];
/* 501 */     for (int i = 0; i < 4;) {
/* 502 */       w[(i++)] = 0;
/*     */     }
/* 504 */     Component c = null;
/* 505 */     for (int row = 0; row < this.rowNum; row++) {
/* 506 */       for (int col = 0; col < 4; col++) {
/* 507 */         c = this.heads[row][col];
/* 508 */         if (c != null) {
/* 509 */           w[col] = Math.max(w[col], c.getPreferredSize().width);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 514 */     return w;
/*     */   }
/*     */   
/*     */   private Dimension getItemPrefSize(int row, int col) {
/* 518 */     Component body = this.bodis[row][col];
/* 519 */     Component head = this.heads[row][col];
/* 520 */     if ((body == null) && (head == null))
/* 521 */       return null;
/* 522 */     Dimension dim = new Dimension();
/*     */     
/* 524 */     dim.width = 
/* 525 */       ((body != null ? body.getPreferredSize().width : 0) + (head != null ? head.getPreferredSize().width : 0));
/* 526 */     dim.height = Math.max(
/* 527 */       body != null ? body.getPreferredSize().height : 0, 
/* 528 */       head != null ? head.getPreferredSize().height : 0);
/*     */     
/*     */ 
/*     */ 
/* 532 */     return dim;
/*     */   }
/*     */   
/*     */   private Dimension getItemMinSize(int row, int col) {
/* 536 */     Component body = this.bodis[row][col];
/* 537 */     Component head = this.heads[row][col];
/* 538 */     if ((body == null) && (head == null))
/* 539 */       return null;
/* 540 */     Dimension dim = new Dimension();
/*     */     
/* 542 */     dim.width = 
/* 543 */       ((body != null ? body.getMinimumSize().width : 0) + (head != null ? head.getMinimumSize().width : 0));
/* 544 */     dim.height = Math.max(body != null ? body.getMinimumSize().height : 0, 
/* 545 */       head != null ? head.getMinimumSize().height : 0);
/*     */     
/* 547 */     return dim;
/*     */   }
/*     */   
/*     */   private Dimension getItemMaxSize(int row, int col) {
/* 551 */     Component body = this.bodis[row][col];
/* 552 */     Component head = this.heads[row][col];
/* 553 */     if ((body == null) && (head == null))
/* 554 */       return null;
/* 555 */     Dimension dim = new Dimension();
/*     */     
/* 557 */     dim.width = 
/* 558 */       ((body != null ? body.getMaximumSize().width : 0) + (head != null ? head.getMaximumSize().width : 0));
/* 559 */     dim.height = Math.max(body != null ? body.getMaximumSize().height : 0, 
/* 560 */       head != null ? head.getMaximumSize().height : 0);
/*     */     
/* 562 */     return dim;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int getMaximumPrefHeight(int row)
/*     */   {
/* 580 */     int h = 0;
/* 581 */     Dimension c = null;
/* 582 */     for (int col = 0; col < 4; col++) {
/* 583 */       c = getItemPrefSize(row, col);
/* 584 */       if (c != null) {
/* 585 */         h = Math.max(h, c.height);
/*     */       }
/*     */     }
/* 588 */     return h;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getHGap()
/*     */   {
/* 615 */     return this.hGap;
/*     */   }
/*     */   
/*     */   public void setHGap(int gap) {
/* 619 */     this.hGap = gap;
/*     */   }
/*     */   
/*     */   public int getVGap() {
/* 623 */     return this.vGap;
/*     */   }
/*     */   
/*     */   public void setVGap(int gap) {
/* 627 */     this.vGap = gap;
/*     */   }
/*     */   
/*     */   private FlexibleLayout.MaxValueHolder calculateMaxValues(int left, int right) {
/* 631 */     int[] maxSSSS = new int[4];
/* 632 */     int[] maxMM = new int[2];
/* 633 */     int maxL = 0;
/*     */     
/* 635 */     Dimension d1 = null;
/* 636 */     Dimension d2 = null;
/* 637 */     Dimension d3 = null;
/* 638 */     Dimension d4 = null;
/*     */     
/* 640 */     int[] mH = getMaximumHeadPrefWidth();
/*     */     
/* 642 */     for (int row = 0; row < this.rowNum; row++) {
/* 643 */       d1 = getItemPrefSize(row, 0);
/* 644 */       d2 = getItemPrefSize(row, 1);
/* 645 */       d3 = getItemPrefSize(row, 2);
/* 646 */       d4 = getItemPrefSize(row, 3);
/*     */       
/* 648 */       if (d1 != null) {
/* 649 */         d1.width = Math.max(
/* 650 */           d1.width, 
/* 651 */           mH[0] + (
/* 652 */           this.bodis[row][0] != null ? 
/* 653 */           this.bodis[row][0].getPreferredSize().width : 0));
/*     */       }
/* 655 */       if (d2 != null) {
/* 656 */         d2.width = Math.max(
/* 657 */           d2.width, 
/* 658 */           mH[1] + (
/* 659 */           this.bodis[row][1] != null ? 
/* 660 */           this.bodis[row][1].getPreferredSize().width : 0));
/*     */       }
/* 662 */       if (d3 != null) {
/* 663 */         d3.width = Math.max(
/* 664 */           d3.width, 
/* 665 */           mH[2] + (
/* 666 */           this.bodis[row][2] != null ? 
/* 667 */           this.bodis[row][2].getPreferredSize().width : 0));
/*     */       }
/* 669 */       if (d4 != null) {
/* 670 */         d4.width = Math.max(
/* 671 */           d4.width, 
/* 672 */           mH[3] + (
/* 673 */           this.bodis[row][3] != null ? 
/* 674 */           this.bodis[row][3].getPreferredSize().width : 0));
/*     */       }
/*     */       
/* 677 */       switch (this.rowTypes[row]) {
/*     */       case 1: 
/* 679 */         if (d1 != null) {
/* 680 */           maxSSSS[0] = Math.max(maxSSSS[0], d1.width);
/*     */         }
/* 682 */         if (d2 != null) {
/* 683 */           maxSSSS[1] = Math.max(maxSSSS[1], d2.width);
/*     */         }
/* 685 */         if ((d1 != null) && (d2 != null)) {
/* 686 */           maxMM[0] = Math.max(maxMM[0], d1.width + d2.width);
/*     */         }
/* 688 */         if (d3 != null) {
/* 689 */           maxSSSS[2] = Math.max(maxSSSS[2], d3.width);
/*     */         }
/* 691 */         if (d4 != null) {
/* 692 */           maxSSSS[3] = Math.max(maxSSSS[3], d4.width);
/*     */         }
/* 694 */         if ((d3 != null) && (d4 != null)) {
/* 695 */           maxMM[1] = Math.max(maxMM[0], d3.width + d4.width);
/*     */         }
/* 697 */         if ((d1 != null) && (d2 != null) && (d3 != null) && (d4 != null)) {
/* 698 */           maxL = Math.max(maxL, d1.width + d2.width + d3.width + 
/* 699 */             d4.width);
/*     */         }
/* 701 */         break;
/*     */       case 2: 
/* 703 */         if (d1 != null) {
/* 704 */           maxMM[0] = Math.max(maxMM[0], d1.width);
/*     */         }
/* 706 */         if (d3 != null) {
/* 707 */           maxMM[1] = Math.max(maxMM[1], d3.width);
/*     */         }
/* 709 */         if ((d1 != null) && (d3 != null)) {
/* 710 */           maxL = Math.max(maxL, d1.width + d3.width);
/*     */         }
/* 712 */         break;
/*     */       case 3: 
/* 714 */         if (d1 != null) {
/* 715 */           maxL = Math.max(maxL, d1.width);
/*     */         }
/* 717 */         break;
/*     */       case 4: 
/* 719 */         if (d1 != null) {
/* 720 */           maxMM[0] = Math.max(maxMM[0], d1.width);
/*     */         }
/* 722 */         if (d3 != null) {
/* 723 */           maxSSSS[2] = Math.max(maxSSSS[2], d3.width);
/*     */         }
/* 725 */         if (d4 != null) {
/* 726 */           maxSSSS[3] = Math.max(maxSSSS[3], d4.width);
/*     */         }
/* 728 */         if ((d3 != null) && (d4 != null)) {
/* 729 */           maxMM[1] = Math.max(maxMM[1], d3.width + d4.width);
/*     */         }
/* 731 */         if ((d1 != null) && (d3 != null) && (d4 != null)) {
/* 732 */           maxL = Math.max(maxL, d1.width + d3.width + d4.width);
/*     */         }
/* 734 */         break;
/*     */       case 5: 
/* 736 */         if (d1 != null) {
/* 737 */           maxSSSS[0] = Math.max(maxSSSS[0], d1.width);
/*     */         }
/* 739 */         if (d2 != null) {
/* 740 */           maxSSSS[1] = Math.max(maxSSSS[1], d2.width);
/*     */         }
/* 742 */         if ((d1 != null) && (d2 != null)) {
/* 743 */           maxMM[0] = Math.max(maxMM[0], d1.width + d2.width);
/*     */         }
/* 745 */         if (d3 != null) {
/* 746 */           maxMM[1] = Math.max(maxMM[1], d3.width);
/*     */         }
/*     */         
/* 749 */         if ((d1 != null) && (d2 != null) && (d3 != null)) {
/* 750 */           maxL = Math.max(maxL, d1.width + d2.width + d3.width);
/*     */         }
/*     */         
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/* 757 */     int widthResidue = 0;
/*     */     
/* 759 */     maxL = Math.max(maxL, right - left);
/*     */     
/* 761 */     if (maxMM[0] + maxMM[1] < maxL) {
/* 762 */       widthResidue = (maxL - maxMM[0] - maxMM[1]) / 2;
/* 763 */       maxMM[0] += widthResidue;
/* 764 */       maxMM[1] += widthResidue;
/*     */     }
/* 766 */     if (maxSSSS[0] + maxSSSS[1] < maxMM[0]) {
/* 767 */       widthResidue = (maxMM[0] - maxSSSS[0] - maxSSSS[1]) / 2;
/* 768 */       maxSSSS[0] += widthResidue;
/* 769 */       maxSSSS[1] += widthResidue;
/*     */     }
/* 771 */     if (maxSSSS[2] + maxSSSS[3] < maxMM[1]) {
/* 772 */       widthResidue = (maxMM[1] - maxSSSS[2] - maxSSSS[3]) / 2;
/* 773 */       maxSSSS[2] += widthResidue;
/* 774 */       maxSSSS[3] += widthResidue;
/*     */     }
/*     */     
/* 777 */     if (maxSSSS[2] + maxSSSS[3] > maxMM[1]) {
/* 778 */       maxMM[1] = (maxSSSS[2] + maxSSSS[3]);
/*     */     }
/* 780 */     if (maxSSSS[0] + maxSSSS[1] > maxMM[0]) {
/* 781 */       maxSSSS[0] += maxSSSS[1];
/*     */     }
/* 783 */     if (maxMM[0] + maxMM[1] > maxL) {
/* 784 */       maxL = maxMM[0] + maxMM[1];
/*     */     }
/*     */     
/* 787 */     return new FlexibleLayout.MaxValueHolder(maxL, maxMM, maxSSSS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private class MaxValueHolder
/*     */   {
/* 795 */     int[] ssss = new int[4];
/* 796 */     int[] mm = new int[2];
/* 797 */     int l = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public MaxValueHolder(int l, int[] mm, int[] ssss)
/*     */     {
/* 806 */       this.l = l;
/* 807 */       this.mm = mm;
/* 808 */       this.ssss = ssss;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 812 */       return 
/*     */       
/* 814 */         "max.l: " + this.l + "; max.mm: " + this.mm[0] + "," + this.mm[1] + "; max.ssss: " + this.ssss[0] + "," + this.ssss[1] + "," + this.ssss[2] + "," + this.ssss[3] + ";";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class FlexibleDefinitor
/*     */   {
/* 821 */     private boolean head = false;
/* 822 */     private int row = -1;
/* 823 */     private int col = -1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public FlexibleDefinitor(int row, int col, boolean head)
/*     */     {
/* 831 */       this.col = col;
/* 832 */       this.head = head;
/* 833 */       this.row = row;
/*     */     }
/*     */     
/*     */     public FlexibleDefinitor(int row, int col) {
/* 837 */       this.col = col;
/* 838 */       this.head = false;
/* 839 */       this.row = row;
/*     */     }
/*     */     
/*     */     public int getCol() {
/* 843 */       return this.col;
/*     */     }
/*     */     
/*     */     public boolean isHead() {
/* 847 */       return this.head;
/*     */     }
/*     */     
/*     */     public int getRow() {
/* 851 */       return this.row;
/*     */     }
/*     */     
/*     */     public boolean isBody() {
/* 855 */       return !this.head;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 859 */       return 
/* 860 */         getClass().getName() + "[" + this.row + "," + this.col + "," + (this.head ? "H" : "B") + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              d:\GalleryTool.jar!\FlexibleLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */