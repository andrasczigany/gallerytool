/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class PicturePanel
/*     */   extends JPanel
/*     */ {
/*  21 */   private JFileChooser fileChooser = null;
/*  22 */   private JList list = null;
/*  23 */   private DefaultListModel listModel = null;
/*  24 */   private JScrollPane listPane = null;
/*  25 */   private JPanel southPanel = null;
/*  26 */   private JLabel lWebDir = null;
/*  27 */   private JTextField iWebDir = null;
/*  28 */   private JLabel lMain = null;
/*  29 */   private JTextField iMain = null;
/*  30 */   private JLabel lSub = null;
/*  31 */   private JTextField iSub = null;
/*  32 */   private JButton selectButton = null;
/*  33 */   private JButton clearButton = null;
/*     */   
/*     */   public PicturePanel() {
/*  36 */     setBorder(new TitledBorder("Képek"));
/*  37 */     setLayout(new BorderLayout(0, 0));
/*  38 */     add(getListPane(), "Center");
/*  39 */     add(getSouthPanel(), "South");
/*     */   }
/*     */   
/*     */   public File[] getFileList() {
/*  43 */     File[] files = new File[getList().getModel().getSize()];
/*  44 */     for (int i = 0; i < getList().getModel().getSize(); i++) {
/*  45 */       files[i] = ((File)getList().getModel().getElementAt(i));
/*     */     }
/*  47 */     return files;
/*     */   }
/*     */   
/*     */   private JScrollPane getListPane() {
/*  51 */     if (this.listPane == null) {
/*  52 */       this.listPane = new JScrollPane();
/*  53 */       this.listPane.setAutoscrolls(true);
/*  54 */       this.listPane.setWheelScrollingEnabled(true);
/*  55 */       this.listPane.setPreferredSize(new Dimension(60, 100));
/*  56 */       this.listPane.setViewportView(getList());
/*     */     }
/*  58 */     return this.listPane;
/*     */   }
/*     */   
/*     */   private JList getList() {
/*  62 */     if (this.list == null) {
/*  63 */       this.list = new JList(new DefaultListModel());
/*     */     }
/*  65 */     return this.list;
/*     */   }
/*     */   
/*     */   private JFileChooser getFileChooser() {
/*  69 */     if (this.fileChooser == null) {
/*  70 */       this.fileChooser = new JFileChooser();
/*  71 */       this.fileChooser.setDialogTitle("Képek");
/*  72 */       this.fileChooser.setFileSelectionMode(0);
/*  73 */       this.fileChooser.setMultiSelectionEnabled(true);
/*  74 */       this.fileChooser.setCurrentDirectory(new File("D:\\downloads\\"));
/*     */     }
/*  76 */     return this.fileChooser;
/*     */   }
/*     */   
/*     */   public JLabel getLWebDir() {
/*  80 */     this.lWebDir = new JLabel("Honlap mappa");
/*  81 */     return this.lWebDir;
/*     */   }
/*     */   
/*     */   public JTextField getIWebDir() {
/*  85 */     if (this.iWebDir != null)
/*  86 */       return this.iWebDir;
/*  87 */     this.iWebDir = new JTextField(10);
/*  88 */     return this.iWebDir;
/*     */   }
/*     */   
/*     */   public JLabel getLMain() {
/*  92 */     this.lMain = new JLabel("Főcím");
/*  93 */     return this.lMain;
/*     */   }
/*     */   
/*     */   public JTextField getIMain() {
/*  97 */     if (this.iMain != null)
/*  98 */       return this.iMain;
/*  99 */     this.iMain = new JTextField("", 20);
/* 100 */     return this.iMain;
/*     */   }
/*     */   
/*     */   public JLabel getLSub() {
/* 104 */     this.lSub = new JLabel("Alcím");
/* 105 */     return this.lSub;
/*     */   }
/*     */   
/*     */   public JTextField getISub() {
/* 109 */     if (this.iSub != null)
/* 110 */       return this.iSub;
/* 111 */     this.iSub = new JTextField("", 32);
/* 112 */     return this.iSub;
/*     */   }
/*     */   
/*     */   private JPanel getSouthPanel() {
/* 116 */     this.southPanel = new JPanel(new FlowLayout(3, 5, 5));
/* 117 */     this.southPanel.add(getClearButton());
/* 118 */     this.southPanel.add(getLWebDir());
/* 119 */     this.southPanel.add(getIWebDir());
/* 120 */     this.southPanel.add(getLMain());
/* 121 */     this.southPanel.add(getIMain());
/* 122 */     this.southPanel.add(getLSub());
/* 123 */     this.southPanel.add(getISub());
/* 124 */     this.southPanel.add(getSelectButton());
/* 125 */     return this.southPanel;
/*     */   }
/*     */   
/*     */   private JButton getClearButton() {
/* 129 */     this.clearButton = new JButton("Törlés");
/* 130 */     this.clearButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 132 */         PicturePanel.this.listModel = ((DefaultListModel)PicturePanel.this.getList().getModel());
/* 133 */         PicturePanel.this.listModel.clear();
/* 134 */         PicturePanel.this.getList().setModel(PicturePanel.this.listModel);
/*     */       }
/* 136 */     });
/* 137 */     return this.clearButton;
/*     */   }
/*     */   
/*     */   private JButton getSelectButton() {
/* 141 */     this.selectButton = new JButton("Képek megnyitása");
/* 142 */     this.selectButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 144 */         PicturePanel.this.openFileChooser();
/*     */       }
/* 146 */     });
/* 147 */     return this.selectButton;
/*     */   }
/*     */   
/*     */   private void openFileChooser() {
/* 151 */     if (getFileChooser().showDialog(this, "OK") != 1) {
/* 152 */       this.listModel = ((DefaultListModel)getList().getModel());
/* 153 */       for (int i = 0; i < getFileChooser().getSelectedFiles().length; i++) {
/* 154 */         this.listModel.addElement(getFileChooser().getSelectedFiles()[i]);
/*     */       }
/* 156 */       getList().setModel(this.listModel);
/*     */     }
/*     */   }
/*     */ }


/* Location:              d:\GalleryTool.jar!\PicturePanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */