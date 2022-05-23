/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3_submit;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * @author ybtis
 */
public class hw3 extends JFrame implements ActionListener{

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    JPanel bigPanel;
    JPanel favPanel;
    JPanel bread;
    JPanel viewPanel;
    JPanel search;
    JScrollPane scroll;
    //newWindow window;
    JTextField searchText = new JTextField(20);
    JButton searchButton = new JButton();
    boolean first = true;
    JButton [] butArray ;
    JMenu myMenu1 ;
    File renameFile;
    String newName = new String();
    String Name = null;
    String Name1 = null;
    File copyFile;
    String copyName;
    String parentDir = System.getProperty("user.home");
    JMenuItem Paste;
    boolean cut = false;
    boolean rename = false;
    boolean searchB = false;
    String butPath;

    
    public static void main(String[] args) {
        hw3 gui = new hw3();
        gui.setVisible(true);
    }
    
    public hw3(){
        super("hw3");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        bigPanel = new JPanel();
        bigPanel.setPreferredSize(new Dimension(400,600));
        bigPanel.setLayout(new BorderLayout());
        bigPanel.setBackground(Color.CYAN);
        add(bigPanel,BorderLayout.CENTER);
        
        favPanel = new JPanel();
        favPanel.setPreferredSize(new Dimension(200,600));
        favPanel.setBackground(Color.yellow);
        add(favPanel,BorderLayout.WEST);
        showFavourites();
        
        
        
        viewPanel = new JPanel();
        viewPanel.addMouseListener(new MouseAdapter() {
           @Override
            public void mouseClicked(MouseEvent e) {
                Component[] compList = viewPanel.getComponents();
                for(int i = 0; i<compList.length;i++){
                    ((newButton)compList[i]).select = false;
                    ((newButton)compList[i]).setOpaque(false);
                    ((newButton)compList[i]).setContentAreaFilled(false);
                    ((newButton)compList[i]).setBorderPainted(false);
                    ((newButton)compList[i]).setBackground(Color.CYAN);
                    myMenu1.setEnabled(false);
                }
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });
        
        search = new JPanel();
        search.setPreferredSize(new Dimension(400,50));
        search.setBackground(Color.BLUE);
        add(search,BorderLayout.NORTH);
        searchButton.setText("Search");
        searchButton.setActionCommand("searchButton");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Component[] compList = viewPanel.getComponents();
                for(Component b : compList){
                    viewPanel.remove(b);
                }
                viewPanel.revalidate();
                viewPanel.repaint();

                viewPanel.setBackground(Color.LIGHT_GRAY);
                BoxLayout boxlayout = new BoxLayout(viewPanel, BoxLayout.Y_AXIS);
                viewPanel.setLayout(boxlayout);
                
                searchString();
            }
        });
        
        search.add(searchText);
        search.add(searchButton);
        search.setVisible(false);
        
        bigPanel.add(viewPanel,BorderLayout.SOUTH);

        bread = new JPanel();//to bread tha ginei textfield area
        bread.setPreferredSize(new Dimension(400,100));
        bread.setMaximumSize(new Dimension());
        bread.setBackground(Color.lightGray);
        bread.setLayout(new WrapLayout());
        newWindow(System.getProperty("user.home"));
        newbread(System.getProperty("user.home"));
        bigPanel.add(bread,BorderLayout.NORTH);
        
        scroll = new JScrollPane(viewPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bigPanel.add(scroll);
        
        
        Border border = BorderFactory.createTitledBorder("Favourites");
        favPanel.setBorder(border);
        
        
        JMenu myMenu = new JMenu("File");
        myMenu.setMnemonic(KeyEvent.VK_F);
        
        myMenu1 = new JMenu("Edit");
        myMenu1.setEnabled(false);
        myMenu1.addActionListener(this);
        myMenu1.setMnemonic(KeyEvent.VK_E);
        
        JMenuItem cut = new JMenuItem("Cut");
        cut.setBackground(Color.white);
        cut.setMnemonic(KeyEvent.VK_C);
        cut.addActionListener(this);
        myMenu1.add(cut);
        
        JMenuItem Copy = new JMenuItem("Copy");
        Copy.setBackground(Color.white);
        Copy.setMnemonic(KeyEvent.VK_C);
        Copy.addActionListener(this);
        myMenu1.add(Copy);
        
        
        Paste = new JMenuItem("Paste");
        Paste.setBackground(Color.white);
        Paste.setMnemonic(KeyEvent.VK_P);
        Paste.addActionListener(this);
        myMenu1.add(Paste);
        Paste.setEnabled(false);
        
        JMenuItem Rename = new JMenuItem("Rename");
        Rename.setBackground(Color.white);
        Rename.setMnemonic(KeyEvent.VK_R);
        Rename.addActionListener(this);
        myMenu1.add(Rename);
        
        JMenuItem Delete = new JMenuItem("Delete");
        Delete.setBackground(Color.white);
        Delete.setMnemonic(KeyEvent.VK_D);
        Delete.addActionListener(this);
        myMenu1.add(Delete);
        
        JMenuItem Favourites = new JMenuItem("Add to Favourites");
        Favourites.setBackground(Color.white);
        Favourites.setMnemonic(KeyEvent.VK_A);
        Favourites.addActionListener(this);
        myMenu1.add(Favourites);
        
        
        JMenuItem Properties = new JMenuItem("Properties");
        Properties.setBackground(Color.white);
        Properties.setMnemonic(KeyEvent.VK_P);
        Properties.addActionListener(this);
        myMenu1.add(Properties);
        
        
        
        JMenu myMenu2 = new JMenu("Search");
        myMenu2.setMnemonic(KeyEvent.VK_S);
        JMenuItem SearchItem = new JMenuItem("search");
        SearchItem.setBackground(Color.white);
        SearchItem.setMnemonic(KeyEvent.VK_N);
        SearchItem.addActionListener(this);
        
        
        
        myMenu2.add(SearchItem);
        
        
        
        
        JMenuItem newWindow = new JMenuItem("New Window");
        newWindow.setBackground(Color.white);
        newWindow.setMnemonic(KeyEvent.VK_N);
        newWindow.addActionListener(this);
        myMenu.add(newWindow);
        
        JMenuItem exit_com = new JMenuItem("Exit");
        exit_com.setBackground(Color.white);
        exit_com.setMnemonic(KeyEvent.VK_E);
        exit_com.addActionListener(this);
        myMenu.add(exit_com);
        
        
        
        
        JMenuBar bar = new JMenuBar();
        bar.add(myMenu);
        bar.add(myMenu1);
        bar.add(myMenu2);
        setJMenuBar(bar); 
        
        
        //add(bar,BorderLayout.NORTH);
        //pack();
        setVisible(true);
    }
    
    
    
    public void actionPerformed(ActionEvent e){
        String menuString = e.getActionCommand();
        if(menuString.equals("Exit")){
            this.dispose();
        }
        if(menuString.equals("New Window")){
            hw3 newWindow = new hw3();
        }
        if(menuString.equals("Delete")){
            first = true;
            Component[] compList = viewPanel.getComponents();
            for(int i = 0; i<compList.length;i++){
                if(((newButton)compList[i]).select == true){
                    Delete(((newButton)compList[i]).path);
                }
            }
        }
    
        if(menuString.equals("Rename")){
            Rename();
        }
        if(menuString.equals("Cut")){
            cut = true;
            Cut();
            
        }
        if(menuString.equals("Copy")){
            Cut();
        }
        if(menuString.equals("Paste")){
            Paste();
            if(cut){
                cut = false;
                Delete(new File(Name));
            }
            //newWindow(parentDir);
            viewPanel.revalidate();
            viewPanel.repaint();
        }
        if(menuString.equals("Properties")){
            Properties();
        }
        if(menuString.equals("Add to Favourites")){
            String xmlName = new String();
            Component [] compList = viewPanel.getComponents();
            for (Component compList1 : compList) {
                if(((newButton) compList1).select){
                    xmlName = ((newButton) compList1).path.getAbsolutePath();
                }
            }
            favourite(xmlName);
            showFavourites();
        }
        if(menuString.equals("search")){
            
                if(searchB){
                    searchB = false;
                    search.setVisible(false);
                    newWindow(System.getProperty("user.home"));
                    newbread(System.getProperty("user.home"));
                }
                else{
                    searchB = true;
                    search.setVisible(true);
                }
                repaint();
                revalidate();
        }
    }
    
    public void searchString(){
        String temp;
        String type;
        int pos;
        
        temp = searchText.getText();
        pos = temp.indexOf("type:");
        if(pos!=-1){
            type = temp.substring(pos+5);
            temp = temp.substring(0, pos-1);
            search(type,temp);
            
        }
        else{
            search(temp);
        }
    }
    
    public void search(String type,String temp){
        String tempParent;
        String lower = parentDir.toLowerCase();
        String tempLower = temp.toLowerCase();
        
        File file = new File(parentDir);
        if(file.isDirectory()){
            
            File [] array = file.listFiles();
            if(array != null){
                for(int i=0;i<array.length;i++){
                    tempParent = parentDir;
                    parentDir = array[i].getAbsolutePath();
                    search(type,temp);
                    parentDir = tempParent;
                }
            }
        }
        
        
        if(lower.contains(tempLower) && (lower.endsWith(type) || (type.equals("dir") && file.isDirectory()))){
                JButton but = new JButton(file.getAbsolutePath());
                but.setBackground(Color.LIGHT_GRAY);
                but.setAlignmentX(Component.LEFT_ALIGNMENT);
                but.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if(file.isDirectory()){
                            newWindow(file.getAbsolutePath());
                            newbread(file.getAbsolutePath());
                            
                        }
                        else if(!file.isDirectory()){
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException ex) {
                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        
                    }
                });
                
                viewPanel.add(but);
            
            }
    
    
    }
    
    public void search(String temp){
        
        String tempParent;
        String lower = parentDir.toLowerCase();
        String tempLower = temp.toLowerCase();
        
        File file = new File(parentDir);
        if(file.isDirectory()){
            
            File [] array = file.listFiles();
            if(array != null){
                for(int i=0;i<array.length;i++){
                    tempParent = parentDir;
                    parentDir = array[i].getAbsolutePath();
                    search(temp);
                    parentDir = tempParent;
                }
            }
        }
        if(lower.contains(tempLower)){
                JButton but = new JButton(file.getAbsolutePath());
                but.setBackground(Color.LIGHT_GRAY);
                but.setAlignmentX(Component.LEFT_ALIGNMENT);
                but.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if(file.isDirectory()){
                            newWindow(file.getAbsolutePath());
                            newbread(file.getAbsolutePath());
                            
                        }
                        else if(!file.isDirectory()){
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException ex) {
                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        
                    }
                });
                
                viewPanel.add(but);
            
            }
        
    
    
    }
    
    
    
    public void Delete(File path){
        if(first){
            first=false;
            JDialog frame = new JDialog();
            JButton yes = new JButton("YES");
            JButton no = new JButton("NO");
            JLabel label = new JLabel("Do you want to Delete"+" "+path.getAbsolutePath()+" "+"?");
            frame.setLayout(new BorderLayout());
            frame.add(no,BorderLayout.EAST);
            frame.add(yes,BorderLayout.WEST);
            frame.add(label,BorderLayout.NORTH);
            frame.pack();
            frame.setVisible(true);

            yes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    frame.dispose();
                    if(path.isDirectory()){
                    File [] a = path.listFiles();
                        if(a!=null){
                            for (File file : a) {
                                Delete(file);
                                path.delete();
                                
                            }
                        }
                    }
                    else{
                        path.delete();
                    }


                }
            });

            no.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    frame.dispose();
                }
            });
        
        }
        else if(!first){
            
            if(path.isDirectory()){
                    File [] a = path.listFiles();
                        if(a!=null){
                            for (File file : a) {
                                Delete(file);
                            }
                            path.delete();
                        }
            }
            else{
                path.delete();
            }
        }     
    }
    
    

    
    public void Cut(){ System.out.println(1234);
        Component[] compList = viewPanel.getComponents();
            for(int i = 0; i<compList.length;i++){
                if(((newButton)compList[i]).select == true){
                   Name = ((newButton)compList[i]).path.getAbsolutePath();
                   
                } 
            }
           
            Name1 = Name;
            Paste.setEnabled(true);
            myMenu1.setEnabled(true);
            
    }
    
    public void Paste(){
        
        File pasteFile;
        int pos;
        StringBuffer dirPath = new StringBuffer();
        String temp;
        copyFile = new File(Name);
        
        
       
        
        if(copyFile.isFile()){
            pos = Name.lastIndexOf("\\");
            if(pos == -1){
                pos = Name.lastIndexOf("/");
            }
            dirPath.append(parentDir).append(Name.substring(pos));
            dirPath.delete(0, dirPath.length());
            if(rename){
                dirPath.append(parentDir);
               
            }
            else if (!rename){  
                dirPath.append(parentDir).append(Name.substring(pos));
            }
            pasteFile = new File(dirPath.toString());
            

            if(pasteFile.exists()){
                JDialog frame = new JDialog();
                JLabel label = new JLabel("File with that name already exist. Do you want to replace it?");
                    JButton yes = new JButton("YES");
                    JButton no = new JButton("NO");
                    frame.setLayout(new BorderLayout());
                    frame.add(label,BorderLayout.NORTH);
                    frame.add(yes,BorderLayout.WEST);
                    frame.add(no,BorderLayout.EAST);
                    frame.pack();
                    frame.setVisible(true);
                    
                    yes.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            frame.dispose();
                            Path copy = Paths.get(dirPath.toString());
                            Path original = Paths.get(Name);
                            try {
                                
                                Files.copy(original,copy,StandardCopyOption.REPLACE_EXISTING);
                                viewPanel.revalidate();
                                viewPanel.repaint();
                                
                            } catch (IOException ex) {
                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    no.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        frame.dispose();
                    }
                    });
                    

            }
            else{
                Path copy = Paths.get(dirPath.toString());
                Path original = Paths.get(Name);
                try {
                    Files.copy(original,copy);
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else {
            
            File [] array = copyFile.listFiles();
            pos = Name.lastIndexOf("\\");
            if(pos == -1){
                pos = Name.lastIndexOf("/");
            }
            dirPath.delete(0, dirPath.length());
            if(rename){
                dirPath.append(parentDir);
               
            }
            else if (!rename){  
                dirPath.append(parentDir).append(Name.substring(pos));
                
            }
            File newFolder = new File(dirPath.toString());
            
            if(newFolder.exists()){
                JDialog frame = new JDialog();
                JLabel label = new JLabel("File with that name already exist. Do you want to replace it?");
                JButton yes = new JButton("YES");
                JButton no = new JButton("NO");
                frame.setLayout(new BorderLayout());
                frame.add(label,BorderLayout.NORTH);
                frame.add(yes,BorderLayout.WEST);
                frame.add(no,BorderLayout.EAST);
                frame.pack();
                frame.setVisible(true);

                yes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        
                        frame.dispose();
                        String tempParent = new String();
                        
                        if(newFolder.isDirectory()){ 
                            File [] a = newFolder.listFiles();
                            if(a!=null){
                                for (File file : a) {
                                    Delete(file);
                                    
                                }
                            }
                        }
                        else{
                            newFolder.delete();
                        }
                        if(array.length == 0){
                            return;
                        }
                        tempParent = parentDir;
                        parentDir = newFolder.getAbsolutePath();
                        for(int i=0;i<array.length;i++){
                            Name = array[i].getAbsolutePath();
                            Paste();
                            
                        }
                        parentDir = tempParent;
                    }
                });
                no.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        frame.dispose();
                    }
                });
            }
            else{
                
           
                String tempParent;
                String tempName;
                newFolder.mkdir();

                if(array.length == 0){
                    
                    return;
                }
                tempParent = parentDir;
                
                
                parentDir = newFolder.getAbsolutePath();
                
                for(int i=0;i<array.length;i++){
                    tempName = Name;
                    Name = array[i].getAbsolutePath();
                    
                    rename = false;
                    Paste();
                    Name = tempName;
                    
                }
                
                parentDir = tempParent;
            }
        }
    }
    
    
        public void Rename(){
        
        String name ;
        int pos;
        StringBuffer newPath = new StringBuffer();
        Component[] compList = viewPanel.getComponents();
            for(int i = 0; i<compList.length;i++){
                if(((newButton)compList[i]).select == true){
                   Name = ((newButton)compList[i]).path.getAbsolutePath();
                } 
            }
            
            
        pos = Name.lastIndexOf("/");
        if(pos == -1){
            pos = Name.lastIndexOf("\\");
           
        } 
       
        name = Name.substring(0, pos+1) ;//name einai to path tou mitrikou fakelou
        JDialog frame = new JDialog(this, "Rename", true);
        JTextField text = new JTextField(Name.substring(pos+1),20);
        JPanel panel = new JPanel();
        panel.add(text);
        
        JButton enter = new JButton();
        enter.setText("OK");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                newName = text.getText();
                frame.dispose();
                newPath.append(name).append(newName);
                renameFile = new File(newPath.toString());
                
                    if(!renameFile.exists()){
                        
                        //Name = newPath.toString();
                        parentDir = newPath.toString();
                        //Cut();
                        rename = true;
                        Paste();
                        Delete(new File(Name));
                        rename = false;
                    }
               
            }
            
        });
        panel.add(enter);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
        
        
    
        
        
    public void Properties(){
        boolean j = false;
        int namePos;
        File file ;
        long size;
        JCheckBox read = new JCheckBox("READABLE");
        JCheckBox write = new JCheckBox("WRITABLE");
        JCheckBox exec = new JCheckBox("EXECUTABLE");
        JPanel perm = new JPanel();
        
        
        StringBuffer prop = new StringBuffer();
        Component[] compList = viewPanel.getComponents();
        for(int i = 0; i<compList.length;i++){
            if(((newButton)compList[i]).select == true){
                Name = ((newButton)compList[i]).path.getAbsolutePath();
                j = true;
            }
        }
        if(!j){
            Name = parentDir;
        }
        file = new File(Name);
        namePos = Name.lastIndexOf("/");
        if(namePos == -1){
            namePos = Name.lastIndexOf("\\");
        }
        JDialog frame = new JDialog();
        frame.setLayout(new GridLayout(4, 1));
        JLabel name = new JLabel("1.Name:  "+Name.substring(namePos+1));
        frame.add(name);
        JLabel path = new JLabel("2.Absolute path of File:  "+Name);
        frame.add(path);
        JLabel label = new JLabel("3.Files Size is:  "+Size(file)+"  bytes");
        frame.add(label);
        perm.add(new JLabel("4."));
        perm.add(read);
        perm.add(write);
        perm.add(exec);
        frame.add(perm);
        frame.setVisible(true);
        
        frame.pack();
        
        if(file.canExecute()){
            exec.setSelected(true);
            
        }
        if(file.canRead()){
            read.setSelected(true);
            
        }
        if(file.canWrite()){
            write.setSelected(true);
            
        }
        
        if(!file.setExecutable(true)){
            exec.setEnabled(false);
        }
        if(!file.setExecutable(false)){
            exec.setEnabled(false);
        }
        if(!file.setReadable(true)){
            read.setEnabled(false);
        }
        if(!file.setReadable(false)){
            read.setEnabled(false);
        }
        if(!file.setWritable(true)){
            write.setEnabled(false);
        }
        if(!file.setWritable(false)){
            write.setEnabled(false);
        }
            
        
          ItemListener itemListener = new ItemListener() {
              
            @Override
            public void itemStateChanged(ItemEvent e) {
            Object source = e.getItemSelectable();
                

                if (source == exec ) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                         
                    }
                    else if(e.getStateChange() == ItemEvent.SELECTED){
                        file.setExecutable(true);
                    }
                       
                       
                }
                else if (source == read) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        file.setReadable(false);
                    }
                    else if(e.getStateChange() == ItemEvent.SELECTED){
                        file.setReadable(true);
                    }
                }
                else if (source == write) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        file.setWritable(false);
                    }
                    else if(e.getStateChange() == ItemEvent.SELECTED){
                        file.setWritable(true);
                    }
                    
                }
                

            }
  };
          exec.addItemListener(itemListener);
          read.addItemListener(itemListener);
          write.addItemListener(itemListener);
        
        
        
           
    
    }
    
    public long Size(File file){
        long size = 0;
        
        
        if(file.isFile()){
            size = file.length();
        }
        else{
            File [] a = file.listFiles();
            //int len = a.length;
           // System.out.println(a.length);
            if(a==null){
                return 0;
            }
            for (File a1 : a) {
                size += Size(a1);
            }
        }
        
            
            
        return size;
    }
    
    public JPopupMenu createPopupMenu() {
    JMenuItem menuItem;

    
    JPopupMenu popup = new JPopupMenu();
    menuItem = new JMenuItem("Cut");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("Copy");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("Paste");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("Rename");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("Delete");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("Add to Favourites");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    menuItem = new JMenuItem("Properties");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    return popup;

  }
    
    
    
    
    
    private class newButton extends JButton{
    
    int buttonClickedCount;
    long start,end;
    boolean select = false;
    File path ;
        public newButton(String name){
            super();
            int pos , namePos;
            String temp , fName = null ;
            path = new File(name);
            
            


            ImageIcon audioIcon = new ImageIcon("./icons/audio.png");
            ImageIcon imageIcon = new ImageIcon("./icons/image.png");
            ImageIcon docIcon = new ImageIcon("./icons/doc.png");
            ImageIcon xlxIcon = new ImageIcon("./icons/xlx.png");
            ImageIcon zipIcon = new ImageIcon("./icons/zip.png");
            if(path.isDirectory()==true){
                setIcon(new ImageIcon("./icons/folder.png"));
                namePos = name.lastIndexOf("/");
                if(namePos == -1){
                    namePos = name.lastIndexOf("\\");
                }
                fName = name.substring(namePos +1);
                //System.out.println(namePos);
            }
            else if(path.isFile()== true){
                

                namePos = name.lastIndexOf("/");
                if(namePos == -1){
                    namePos = name.lastIndexOf("\\");
                }
                fName = name.substring(namePos +1);
                pos = name.lastIndexOf(".");
                temp = name.substring(pos+1);


                switch (temp) {
                    case "audio":
                    case "mp3":
                    case "wav":
                    case "ogg":
                        setIcon(audioIcon);
                        break;
                    case "bmp":
                    case "giff":
                    case "image":
                    case "jpeg":
                    case "jpg":
                    case "png":
                        setIcon(imageIcon);
                        break;
                    case "doc":
                    case "docx":
                    case "odt":
                        setIcon(docIcon);
                        break;
                    case "ods":
                    case "xlsx":
                    case "xlx":
                        setIcon(xlxIcon);
                        break;
                    case "gz":
                    case "tar":
                    case "tgz":
                    case "zip":
                        setIcon(zipIcon);
                        break;
                    case "html":
                    case "htm":
                        setIcon(new ImageIcon("./icons/html.png"));
                        break;
                    case "xml":
                        setIcon(new ImageIcon("./icons/xml.png"));
                        break;
                    case "video":
                        setIcon(new ImageIcon("./icons/video.png"));
                        break;
                    case "txt":
                        setIcon(new ImageIcon("./icons/txt.png"));
                        break;
                    case "pdf":
                        setIcon(new ImageIcon("./icons/pdf.png"));
                        break;
                    default:
                        setIcon(new ImageIcon("./icons/question.png"));
                        break;
                }
            }

            
            setText(fName);
            setVerticalTextPosition(AbstractButton.BOTTOM);
            setHorizontalTextPosition(AbstractButton.CENTER);
            setOpaque(false);
            //setContentAreaFilled(false);
            setBorderPainted(false);
            setBackground(Color.CYAN);


            //buttonClickedCount = 0;
            boolean clicks = false;
            if(!path.isDirectory()){
                     addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                        if(e.getClickCount()==2){
                            try {
                            Desktop.getDesktop().open(path);
                            }
                            catch (IOException ex) {
                            Logger.getLogger(newButton.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        else if(e.getClickCount()==1){
                                Component[] compList = viewPanel.getComponents();
                                for(int i=0;i<compList.length;i++){
                                    compList[i].setBackground(Color.LIGHT_GRAY);
                                    ((newButton)compList[i]).setOpaque(false);
                                    ((JButton)compList[i]).setContentAreaFilled(false);
                                    ((JButton)compList[i]).setBorderPainted(false);
                                    ((JButton)compList[i]).setBackground(Color.CYAN);
                                    ((newButton)compList[i]).select = false;
                                }
                                setOpaque(true);
                                setContentAreaFilled(true);
                                setBorderPainted(true);
                                setBackground(Color.yellow);
                                select = true;
                                myMenu1.setEnabled(true);
                                viewPanel.revalidate();
                                viewPanel.repaint();
                                
                            } }
                });
            }
            else if(path.isDirectory()){
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                        if(e.getClickCount()==2){
                            
                            newWindow(path.getAbsolutePath());
                            newbread(path.getAbsolutePath());
                            bread.repaint();
                            bread.revalidate();
                        }
                        else if(e.getClickCount()==1){
                                 Component[] compList = viewPanel.getComponents();
                                for(int i=0;i<compList.length;i++){
                                    compList[i].setBackground(Color.LIGHT_GRAY);
                                    ((newButton)compList[i]).setOpaque(false);
                                    ((JButton)compList[i]).setContentAreaFilled(false);
                                    ((JButton)compList[i]).setBorderPainted(false);
                                    ((JButton)compList[i]).setBackground(Color.CYAN);
                                    ((newButton)compList[i]).select = false;
                                }
                                setOpaque(true);
                                setContentAreaFilled(true);
                                setBorderPainted(true);
                                setBackground(Color.yellow);
                                select = true;
                                myMenu1.setEnabled(true);
                                viewPanel.revalidate();
                                viewPanel.repaint();
                                
                            } }
                });
            }
            addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent ea) {
                                
                                if (ea.getButton() == MouseEvent.BUTTON3){    
                                    createPopupMenu().show(ea.getComponent(), ea.getX(), ea.getY());
                                    
                                }
                            }
                        });
            

        }
    }
    public final void newWindow(String path) {
        File c ;
        File [] a;
        File [] files;
        parentDir = path;
        int j = 0;
        
        Component[] compList = viewPanel.getComponents();
        for(Component b : compList){
            viewPanel.remove(b);
        }
        viewPanel.revalidate();
        viewPanel.repaint();
        
        viewPanel.setBackground(Color.LIGHT_GRAY);
        viewPanel.setLayout(new WrapLayout());
        
        c = new File(path);
        
        
        a = c.listFiles();
        if(a.length!=0){
            files = new File[a.length];
        }
        else{
            files = new File[0];
        }
        
            for(int i=0;i<a.length;i++){
                if(a[i].isDirectory()){
                    viewPanel.add(new newButton(a[i].getAbsolutePath()));
                }
                else{
                    files[j] = a[i];
                    j++;
                    
                }
            }
            for(int i=0;i<j;i++){
                viewPanel.add(new newButton(files[i].getAbsolutePath()));
            }
        
    }
    
    
    public void favourite(String path) {
        String temp;
        int pos;
        File file = new File("C:/Users/ybtis/.java-file-browser/properties.xml");
        File dir = new File("C:/Users/ybtis/.java-file-browser");
        try{
        
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;

            docBuilder = docFactory.newDocumentBuilder();
            
            




            if(!dir.exists()){
                dir.mkdir();
            }
            if(!file.exists()){
                file.createNewFile();
                Document doc = docBuilder.newDocument();
                Element favourites = doc.createElement("favourites");

                    file.createNewFile();
                    doc.appendChild(favourites);

                    Element directory = doc.createElement("directory");
                    favourites.appendChild(directory);


                    String home = System.getProperty("user.home");
                    pos = home.lastIndexOf("/");
                    if(pos == -1){
                        pos = home.lastIndexOf("\\");
                    }
                    directory.setAttribute("name",home.substring(pos+1));
                    directory.setAttribute("path", home);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(file);


                    transformer.transform(source, result);
            }
            else if(file.exists()){
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();
                    Element favourites;
                    pos = path.lastIndexOf("/");
                    if(pos == -1){
                        pos = path.lastIndexOf("\\");
                    }

                    favourites = doc.getDocumentElement();
                    NodeList list = doc.getElementsByTagName("directory");
                    for(int i=0;i<list.getLength();i++){
                        
                        Node node = list.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElement = (Element) node;
                            if(eElement.getAttribute("path").equals(path)){
                                return;
                            }

                        } 
                    }
                    
                    Element directory = doc.createElement("directory");
                    favourites.appendChild(directory);

                    
                    directory.setAttribute("name",path.substring(pos+1) );
                    directory.setAttribute("path", path);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File("C:/Users/ybtis/.java-file-browser/properties.xml"));

                    transformer.transform(source, result);



            }
        }
        catch (TransformerConfigurationException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void showFavourites(){
        Component [] compList = favPanel.getComponents();
        for (Component compList1 : compList) {
            favPanel.remove(compList1);
        }
        File file = new File("C:/Users/ybtis/.java-file-browser/properties.xml");
        if(!file.exists()){
            favourite(null);
        }
        try{
        
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("favourites");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
                NodeList list = doc.getElementsByTagName("directory");
                for(int i=0;i<list.getLength();i++){
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        JButton but = new JButton();
                        but.setText(eElement.getAttribute("name"));
                        but.setActionCommand(eElement.getAttribute("name"));
                        but.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                newWindow(eElement.getAttribute("path"));
                                newbread(eElement.getAttribute("path"));
                                viewPanel.repaint();
                                viewPanel.revalidate();
                                bread.repaint();
                                bread.revalidate();
                            }
                        });
                        but.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent ea) {
                                
                                if (ea.getButton() == MouseEvent.BUTTON3){ 
                                    JPopupMenu pop = new JPopupMenu();
                                    JMenuItem del = new JMenu("Delete Favourite");
                                    pop.add(del);
                                    //pop.setVisible(true);
                                    pop.show(ea.getComponent(), ea.getX(), ea.getY());
                                    del.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent ed){
                                            try {
                                                String butName = new String();
                                                butName = but.getActionCommand();
                                                
                                                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                                Document doc = dBuilder.parse(file);
                                                
                                                doc.getDocumentElement().normalize();
                                                
                                                NodeList nList = doc.getElementsByTagName("favourites");
                                                
                                                String filepath = "C:/Users/ybtis/.java-file-browser/properties.xml";
                                                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                                                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                                                Document doc1 = docBuilder.parse(filepath);
                                                
                                                
                                                
                                                
                                                for (int temp = 0; temp < nList.getLength(); temp++) {

                                                    Node nNode = nList.item(temp);
                                                    NodeList list = doc.getElementsByTagName("directory");
                                                    for(int i=0;i<list.getLength();i++){
                                                        Node node = list.item(i);
                                                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement = (Element) node;
                                                            if(eElement.getAttribute("name").equals(butName)){
                                                                
                                                                eElement.getParentNode().removeChild(eElement);
                                                                favPanel.remove(but);
                                                                favPanel.repaint();
                                                                favPanel.revalidate();
                                                            
                                                            }
                                                        }
                                                    }
                                                    
                                                    
                                                }
                                                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                                Transformer transformer = transformerFactory.newTransformer();
                                                DOMSource source = new DOMSource(doc);
                                                StreamResult result = new StreamResult(new File(filepath));
                                                transformer.transform(source, result);
                                                
                                                
                                                
                                                
                                            } catch (ParserConfigurationException ex) {
                                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (SAXException ex) {
                                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (IOException ex) {
                                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (TransformerConfigurationException ex) {
                                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (TransformerException ex) {
                                                Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            
                                        }
                                        
                                    });
                                    
                                    
                                }
                            }
                        });
                        
                        favPanel.add(but);
                        favPanel.repaint();
                        favPanel.revalidate();
                        
                    }
                }
            }
        
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(hw3.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }
    
    
    
    public void newbread(String name){
        File file = new File(name);
        String temp = new String();
        String newBut = new String();
        int pos = 0;
        int old = 0;
        String find;
        Component[] compList = bread.getComponents();
        for(Component b : compList){
            bread.remove(b);
        }
        pos = name.indexOf("/");
        find = "/";
        if(pos == -1){
            pos = name.indexOf("\\");
            find = "\\"; 
        }
        System.out.println(find);
        System.out.println(name);
        
        if(name.equals(find)){
        return;
        }
        
        while(true){
            
            newBut = name.substring(old, pos);
            
           
            
            butPath = name.substring(0, pos+1);
            
            
            bread.add(new breadBut(newBut+">",butPath));
            
            old = pos;
            pos =name.indexOf(find, pos+1);
            if(pos < 0 && newBut.equals(find)==false){
                butPath = name.substring(0, name.length());
                newBut = name.substring(old, name.length());
                bread.add(new breadBut(newBut+">",butPath));
                
                break;
            }
            //System.out.println(pos);
            //newBut = name.substring(old+1,pos);
            //System.out.println(newBut);
            
            
        }
        
            
        
    }
    
    private class breadBut extends JButton{
    
    int buttonClickedCount;
    long start,end;
    boolean select = false;
    File path ;
        
        public breadBut(String name,String path){
            super();
            setText(name);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    newbread(path);
                    bread.repaint();
                    bread.revalidate();
                    newWindow(path);
                }
            });
            
            
            
        
        }
    
    }
    
    
}
    

