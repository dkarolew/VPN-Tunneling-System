package GUI;

import server.SSLServer;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;


@SuppressWarnings("serial")
public class GUIServer extends JFrame implements ActionListener {

    // Host
    private JLabel		   m_lblHost;
    private TextField	   m_txtHost;

    // Local port
    private JLabel          m_lblLocalPort;
    private TextField       m_txtLocalPort;

    // Tunnel port
    private JLabel          m_lblTunnelPort;
    private TextField       m_txtTunnelPort;

    // Keystore
    private JLabel          m_lblKeystorePath;
    private TextField       m_txtKeystorePath;

    // Keystore password
    private JLabel          m_lblKeystorePassword;
    private JPasswordField  m_txtKeystorePassword;

    // Truststore
    private JLabel          m_lblTruststorePath;
    private TextField       m_txtTruststorePath;

    // Truststore password
    private JLabel          m_lblTruststorePassword;
    private JPasswordField  m_txtTruststorePassword;

    // Start / Stop
    private JButton         m_btnStart;
    private JButton         m_btnStop;

    private SSLServer server;

    public GUIServer() {
        super("Server");
        this.setSize(600, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        this.setVisible(true);

        // Host
        m_lblHost = new JLabel("Host:");
        m_txtHost = new TextField(10);

        // Local port
        m_lblLocalPort = new JLabel("Local port:");
        m_txtLocalPort = new TextField(10);

        // Tunnel port
        m_lblTunnelPort = new JLabel("Tunnel port:");
        m_txtTunnelPort = new TextField(10);

        // Keystore
        m_lblKeystorePath = new JLabel("Keystore:");
        m_txtKeystorePath = new TextField(20);

        // Keystore password
        m_lblKeystorePassword = new JLabel("Keystore password:");
        m_txtKeystorePassword = new JPasswordField(10);

        // Truststore
        m_lblTruststorePath = new JLabel("Truststore:");
        m_txtTruststorePath = new TextField(20);

        // Truststore password
        m_lblTruststorePassword = new JLabel("Truststore password:");
        m_txtTruststorePassword = new JPasswordField(10);

        // Start / Stop
        m_btnStart = new JButton("Start");
        m_btnStop = new JButton("Stop ");

        // Local port
        layout.putConstraint(SpringLayout.WEST, m_txtLocalPort, 70, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.EAST, m_lblLocalPort, -5, SpringLayout.WEST, m_txtLocalPort);

        // Tunnel port
        layout.putConstraint(SpringLayout.WEST, m_txtTunnelPort, 200 , SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.EAST, m_lblTunnelPort, 195, SpringLayout.WEST, m_txtLocalPort);

        // Host
        layout.putConstraint(SpringLayout.WEST, m_txtHost, 360, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.EAST, m_lblHost, 265, SpringLayout.EAST, m_txtLocalPort);

        // Keystore
        layout.putConstraint(SpringLayout.SOUTH, m_txtKeystorePath, -10, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_txtKeystorePath, 0, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_lblKeystorePath, -65, SpringLayout.WEST, m_txtKeystorePath);
        layout.putConstraint(SpringLayout.NORTH, m_lblKeystorePath, -30, SpringLayout.WEST, m_txtKeystorePath);

        // Keystore password
        layout.putConstraint(SpringLayout.SOUTH, m_txtKeystorePassword, -10, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_txtKeystorePassword, 300, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_lblKeystorePassword, -120, SpringLayout.WEST, m_txtKeystorePassword);
        layout.putConstraint(SpringLayout.NORTH, m_lblKeystorePassword, -330, SpringLayout.WEST, m_txtKeystorePassword);

        // Truststore
        layout.putConstraint(SpringLayout.SOUTH, m_txtTruststorePath, 30, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_txtTruststorePath, 0, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_lblTruststorePath, -65, SpringLayout.WEST, m_txtTruststorePath);
        layout.putConstraint(SpringLayout.NORTH, m_lblTruststorePath, 10, SpringLayout.WEST, m_txtTruststorePath);

        // Truststore password
        layout.putConstraint(SpringLayout.SOUTH, m_txtTruststorePassword, 30, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_txtTruststorePassword, 300, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_lblTruststorePassword, -125, SpringLayout.WEST, m_txtTruststorePassword);
        layout.putConstraint(SpringLayout.NORTH, m_lblTruststorePassword, -290, SpringLayout.WEST, m_txtTruststorePassword);

        // Start / Stop
        layout.putConstraint(SpringLayout.SOUTH, m_btnStart, 75, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_btnStart, 30, SpringLayout.WEST, m_txtLocalPort);
        layout.putConstraint(SpringLayout.WEST, m_btnStop, 225, SpringLayout.WEST, m_btnStart);
        layout.putConstraint(SpringLayout.NORTH, m_btnStop, 19, SpringLayout.WEST, m_btnStart);

        // Host
        this.add(m_lblHost);
        this.add(m_txtHost);

        // Local port
        this.add(m_lblLocalPort);
        this.add(m_txtLocalPort);

        // Tunnel port
        this.add(m_lblTunnelPort);
        this.add(m_txtTunnelPort);

        // Keystore
        this.add(m_lblKeystorePath);
        this.add(m_txtKeystorePath);

        // Keystore password
        this.add(m_lblKeystorePassword);
        this.add(m_txtKeystorePassword);

        // Truststore
        this.add(m_lblTruststorePath);
        this.add(m_txtTruststorePath);

        // Truststore password
        this.add(m_lblTruststorePassword);
        this.add(m_txtTruststorePassword);

        // Start / Stop
        this.add(m_btnStart);
        this.add(m_btnStop);

        m_btnStart.addActionListener(this);
        m_btnStop.addActionListener(this);

        m_btnStop.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == m_btnStart) start();
            else if (e.getSource() == m_btnStop) stop();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void start() throws IOException {
        int localPort;
        int tunnelPort;
        String keystorePath = m_txtKeystorePath.getText();
        String truststorePath = m_txtTruststorePath.getText();
        String host = m_txtHost.getText();
        char[] keystorePassword;
        char[] truststorePassword;

        m_txtKeystorePassword.setEchoChar('*');
        keystorePassword = m_txtKeystorePassword.getPassword();
        m_txtTruststorePassword.setEchoChar('*');
        truststorePassword = m_txtTruststorePassword.getPassword();

        try {
            localPort = Integer.parseInt(m_txtLocalPort.getText());
            tunnelPort = Integer.parseInt(m_txtTunnelPort.getText());
        } catch (NumberFormatException e) {
            System.out.println("Port is invalid");
            return;
        }

        if(host.length() == 0) {
            System.out.println("Host can not be empty");
            return;
        }

        m_btnStart.setEnabled(false);
        m_btnStop.setEnabled(true);

        System.out.println("Server starting...");

        // server = new SSLRelayServer("C:\\Users\\ASUS\\Desktop\\server.keystore",
        //         "C:\\Users\\ASUS\\Desktop\\server.trustore", "server_password".toCharArray(),
        //         "server_password".toCharArray(), tunnelPort, localPort);

        server = new SSLServer(keystorePath, truststorePath, truststorePassword,
                                    keystorePassword, tunnelPort, localPort, host);
    }

    private void stop() {
        server.stop();
        server.close();
        System.out.println("Server closed");
        m_btnStop.setEnabled(false);
        m_btnStart.setEnabled(true);
    }
}
