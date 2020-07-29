
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class Client extends JFrame implements ActionListener {

    JPanel headerPanel;
    JTextField textField;
    JButton sendBtn;
    static JTextArea textArea;

    static Socket s;

    static DataInputStream din;
    static DataOutputStream dout;
    boolean typing;
    Client() {
        // headerPanel
        headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(7, 94, 84));
        headerPanel.setBounds(0, 0, 450, 70);
        add(headerPanel);

        // headerPanel leftArrowIcon
        ImageIcon leftArrowIcon = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image leftArrowSize = leftArrowIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon addArraowIcon = new ImageIcon(leftArrowSize);
        JLabel arrowIconLabel = new JLabel(addArraowIcon);
        arrowIconLabel.setBounds(5, 17, 30, 30);
        headerPanel.add(arrowIconLabel);
                                                                                                                                                                                                                                                                                                                                                                                                                                                    
        arrowIconLabel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
               System.exit(0);
           } 
        });

        // headerPanel videoIcon
        ImageIcon videoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image videoIconSize = videoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon addVideoIcon = new ImageIcon(videoIconSize);
        JLabel videoIconLabel = new JLabel(addVideoIcon);
        videoIconLabel.setBounds(290, 20, 30, 30);
        headerPanel.add(videoIconLabel);

        // headerPanel callIcon
        ImageIcon callIcon = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image callIconSize = callIcon.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon addCallIcon = new ImageIcon(callIconSize);
        JLabel callIconLabel = new JLabel(addCallIcon);
        callIconLabel.setBounds(350, 20, 35, 30);
        headerPanel.add(callIconLabel);

        // headerPanel threeDotIcon -> ellipsis
        ImageIcon ellipsis = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image ellipsisSize = ellipsis.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
        ImageIcon addEllipsisIcon = new ImageIcon(ellipsisSize);
        JLabel ellipsisIconLabel = new JLabel(addEllipsisIcon);
        ellipsisIconLabel.setBounds(410, 20, 13, 25);
        headerPanel.add(ellipsisIconLabel);

        // headerPanel UserImage
        ImageIcon userImage = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image userImageSize = userImage.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon addUserImage = new ImageIcon(userImageSize);
        JLabel userImageLabel = new JLabel(addUserImage);
        userImageLabel.setBounds(40, 5, 60, 60);
        headerPanel.add(userImageLabel);

        // header user name
        JLabel userName = new JLabel("Bunty");
        userName.setFont(new Font("SANS_SERIF", Font.BOLD, 18));
        userName.setForeground(Color.white);
        userName.setBounds(110, 15, 100, 18);
        headerPanel.add(userName);

        // Online Status    
        JLabel onlineStatus = new JLabel("online"); 
        onlineStatus.setFont(new Font("SANS_SERIF", Font.PLAIN, 14));
        onlineStatus.setForeground(Color.white);
        onlineStatus.setBounds(110, 35, 100, 20);
        headerPanel.add(onlineStatus);

        //---------------------------Screen Decoration---------------------------//
        // Message Field 
        textField = new JTextField();
        textField.setBounds(5,655,310,40);
        textField.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        add(textField);

        //send button
        sendBtn = new JButton("SEND");
        sendBtn.setBounds(320,655,123,40);
        sendBtn.setBackground(new Color(7,94,84));
        sendBtn.setForeground(Color.white);
        sendBtn.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        sendBtn.addActionListener(this);
        add(sendBtn);
        
        // Typing Timer
        Timer t = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!typing) {
                    onlineStatus.setText("online");
                }
            }
        });

        t.setInitialDelay(2000);
        textField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke) {
                onlineStatus.setText("typing...");
                t.stop();
                typing = true;
            }

            public void keyReleased(KeyEvent ke){
                typing = false;
                if(!t.isRunning()){
                    t.start();
                }
            }
        });

        // TextArea
        textArea = new JTextArea();
        textArea.setBounds(5,75,440,570);
        textArea.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(textArea);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(450, 700);
        setLocation(1100, 200);
        setUndecorated(true);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        try{
        String msg = textField.getText();
        textArea.setText(textArea.getText() + "\n\t\t" + msg);
        dout.writeUTF(msg);
        textField.setText("");
        } catch(Exception e){}
    }

    public static void main(String[] args) {
        new Client().setVisible(true);

        String mesgInput = "";
        try{
            s = new Socket("127.0.0.1",6001);
            
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                mesgInput = din.readUTF();
                textArea.setText(textArea.getText() + "\n" + mesgInput);
            }
        } catch(Exception e){
            
        }
    }
}