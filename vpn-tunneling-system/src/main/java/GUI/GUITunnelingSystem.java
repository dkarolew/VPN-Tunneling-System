package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class GUITunnelingSystem extends JFrame implements ActionListener {

    private final JButton	        m_btnClient;
    private final JButton           m_btnServer;

    public GUITunnelingSystem() {
        super("VPN Tunneling System");
        this.setSize(400,100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setVisible(true);

        m_btnClient = new JButton("Client");
        m_btnServer = new JButton("Server");

        m_btnClient.addActionListener(this);
        m_btnServer.addActionListener(this);

        this.add(m_btnClient);
        this.add(m_btnServer);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == m_btnClient) displayClient();
        else if(e.getSource() == m_btnServer) displayServer();
    }

    private void displayClient() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIClient();
            }
        });
    }

    private void displayServer() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIServer();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUITunnelingSystem();
            }
        });
    }
}
