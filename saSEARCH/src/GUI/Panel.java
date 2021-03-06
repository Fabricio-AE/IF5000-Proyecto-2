package GUI;

import saSearch.SaSEARCHConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jdom.JDOMException;

public class Panel extends JPanel implements ActionListener {

    private JButton jbtnObtenerArchivo, jbtnBuscarArchivo, jbtnSeleccionar;
    private JLabel jlblNombreArchivo;
    private JTextField jtfNombreArchivo;
    private JComboBox jcbLibros;
    private JTextArea jtaLibro;

    public Panel() {
        this.setSize(350,500);
        
        this.setLayout(null);
        
        this.jlblNombreArchivo = new JLabel("Nombre del libro");
        this.jlblNombreArchivo.setBounds(this.getWidth()/2-50, 0, 100, 20);
        this.add(this.jlblNombreArchivo);

        this.jtfNombreArchivo = new JTextField();
        this.jtfNombreArchivo.setBounds(this.getWidth()/2-50, 30, 100, 30);
        this.add(this.jtfNombreArchivo);

        this.jbtnBuscarArchivo = new JButton("Buscar Archivo");
        this.jbtnBuscarArchivo.setBounds(this.getWidth()/2-65, 70, 130, 30);
        this.jbtnBuscarArchivo.addActionListener(this);
        this.add(this.jbtnBuscarArchivo);

        this.jcbLibros = new JComboBox();
        this.jcbLibros.setBounds(25, 140, 180, 30);
        this.add(this.jcbLibros);

        this.jbtnObtenerArchivo = new JButton("Obtener Archivo");
        this.jbtnObtenerArchivo.setBounds(this.getWidth()/2-90, 180, 180, 30);
        this.jbtnObtenerArchivo.addActionListener(this);
        this.add(this.jbtnObtenerArchivo);

        this.jbtnSeleccionar = new JButton("Seleccionar");
        this.jbtnSeleccionar.setBounds(185 + 25, 140, 110, 30);
        this.jbtnSeleccionar.addActionListener(this);
        this.add(this.jbtnSeleccionar);

        this.jtaLibro = new JTextArea();
        this.jtaLibro.setBounds(this.getWidth()/2-100, 250, 200, 200);
        this.add(jtaLibro);

        //  this.jtaLibro.append("Hola \n");
        //this.jtaLibro.append("Hola \n");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            SaSEARCHConnection slave = SaSEARCHConnection.getInstance();
            if (this.jbtnObtenerArchivo == ae.getSource()) {
                String obtener = String.valueOf(this.jcbLibros.getSelectedItem());
                slave.obtenerArchivo(obtener);

                Thread.sleep(1000);
                String cargar = slave.obtenerDatos();
                this.jtaLibro.setText("");
                this.jtaLibro.append("Datos libro: \n");
                String[] parts = cargar.split(",");
                for (int i = 0; i < parts.length; i++) {
                    this.jtaLibro.append(parts[i] + " \n");
                }// for

                String ruta = "../DISK-Controller/" + obtener + ".pdf";
                VistaPDF v = new VistaPDF(ruta);

            } else if (this.jbtnBuscarArchivo == ae.getSource()) {
                this.jcbLibros.removeAllItems();
                slave.buscarArchivo(this.jtfNombreArchivo.getText());
                Thread.sleep(1000);
                String cargar = slave.obtenerResultados();

                String[] parts = cargar.split("-");
                for (int i = 0; i < parts.length; i++) {
                    this.jcbLibros.addItem(parts[i]);
                }// for

            }else{
                String obtener = String.valueOf(this.jcbLibros.getSelectedItem());
                System.out.println("Nombre: "+obtener);
                slave.cargarArchivo(obtener);
            }

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
