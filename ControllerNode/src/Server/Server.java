/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Data.DiskData;
import Master.Master;
import Utility.Conversiones;
import Utility.Variables;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Fabricio
 */
public class Server extends Thread {

    private static Server INSTANCE = null;
    private DatagramSocket socket;
    private InetAddress address;

    byte[] buffer = new byte[60000];

    private Server() throws UnknownHostException, SocketException {
        this.address = InetAddress.getByName("localhost");
        this.socket = new DatagramSocket(Variables.SERVERPORTNUMBER);
        this.start();
    }

    public static Server getInstance() throws UnknownHostException, SocketException {
        if (INSTANCE == null) {
            INSTANCE = new Server();
        }

        return INSTANCE;
    }

    @Override
    public void run() {
        try {
            DatagramPacket mensaje = new DatagramPacket(buffer, buffer.length);
            DiskData diskData = DiskData.getInstance();
            while (true) {

                this.socket.receive(mensaje);
                String msg = new String(mensaje.getData(), 0, mensaje.getLength());
                Element element = Conversiones.stringToXML(msg.trim());
                String accion = element.getChild("accion").getValue();
                System.out.println("Mensaje: " + accion);
                switch (accion) {
                    case "BUSCAR":
                        System.out.println("Buscar");
                        String resultado = element.getChild("buscar").getValue();
                        System.out.println("Mensaje2: " + resultado);
                        String salida = diskData.obtenerPosiblesLibros(resultado);

                        this.enviarMensaje("resultado", salida, "RESULTADO", mensaje.getPort());

                        break;
                    case "CARGAR":
                        Master master = Master.getInstance();
                        master.obtenerArchivo(element.getChild("archivo").getValue());
                        break;
                    case "OBTENER":

                        System.out.println("Obtener");
                        String resultado2 = element.getChild("obtener").getValue();
                        System.out.println("Mensaje2: " + resultado2);
                        String salida2 = diskData.obtenerDatosLibro(resultado2);
                        this.enviarMensaje("libro", salida2, "LIBRO", mensaje.getPort());

                        break;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarMensaje(String msgName, String msg, String accion, int portNumber) throws IOException {
        Element ePacket = new Element("packet");

        Element eMsg = new Element(msgName);
        eMsg.addContent(msg);

        ePacket.addContent(eMsg);

        buffer = Conversiones.anadirAccion(ePacket, accion).getBytes();

        DatagramPacket mensaje = new DatagramPacket(buffer, buffer.length, this.address, portNumber);
        this.socket.send(mensaje);
    }//enviarMensaje
    
}//end class
