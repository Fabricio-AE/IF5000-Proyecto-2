package GUI;

import Entity.Metadata;
import Master.Master;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdom.JDOMException;

public class Panel extends JPanel implements ActionListener{
    private JButton jbtnEnviarArchivo;
    private JTextField jtfNombreArchivo, jtfAutor, jtfFecha;
    private JLabel jlblNombre, jlblAutor, jlblFecha;

    public Panel() {
        this.setLayout(null);
        
        this.jlblNombre = new JLabel("Nombre");
        this.jlblNombre.setBounds(50, 0, 100, 20);
        this.add(this.jlblNombre);
        
        this.jtfNombreArchivo = new JTextField();
        this.jtfNombreArchivo.setBounds(50, 20, 100, 30);
        this.add(this.jtfNombreArchivo);
        
        this.jlblAutor = new JLabel("Autor");
        this.jlblAutor.setBounds(50, 60, 100, 20);
        this.add(this.jlblAutor);
        
        this.jtfAutor = new JTextField();
        this.jtfAutor.setBounds(50, 80, 100, 30);
        this.add(this.jtfAutor);
        
        this.jlblFecha = new JLabel("Fecha");
        this.jlblFecha.setBounds(50, 120, 100, 20);
        this.add(this.jlblFecha);
        
        this.jtfFecha = new JTextField();
        this.jtfFecha.setBounds(50, 140, 100, 30);
        this.add(this.jtfFecha);
        
        
        this.jbtnEnviarArchivo = new JButton("Eviar Archivo");
        this.jbtnEnviarArchivo.setBounds(35,180,130,30);
        this.jbtnEnviarArchivo.addActionListener(this);
        this.add(this.jbtnEnviarArchivo);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            Master master = Master.getInstance();
            
                Metadata metadata = new Metadata(this.jtfNombreArchivo.getText()
                        , this.jtfAutor.getText(), this.jtfFecha.getText(), "pdf");
                master.enviarArchivo(metadata);
            
       
        } catch (SocketException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}//end class
