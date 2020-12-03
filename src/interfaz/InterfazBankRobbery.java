package interfaz;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.border.*;

import mundo.Ciudad;
import mundo.Mapa;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InterfazBankRobbery extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constantes
	// ---------------------------------------------------------------------------

	/**
	 * Representa la opción de aceptar.
	 */
	public final static String ACEPTAR = "Aceptar";
	
	/**
	 * Representa la opción de examinar.
	 */
	public final static String EXAMINAR = "Examinar";

	// Atributos
	// ----------------------------------------------------------------------------

	private JTextField campoPath;

	private JButton botonExaminar;

	private JButton botonAceptar;
	
	/**
	 * Representa el diálogo para visualizar los casos de prueba.
	 */
	private DialogoCasosPrueba dialogoCasoPrueba;
	
	/**
	 * Representa la relación con el dialogo que permite visualizar las ciudades.
	 */
	private DialogoVisualizacion dialogoVisualizacion;
	
	/**
	 * Representa la relación con el mapa que tiene diferentes ciudades.
	 */
	private Mapa mapa;

	public InterfazBankRobbery(){
		setTitle("Bank Robbery");
		setIconImage(new ImageIcon("./img/ic.png").getImage());
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		
		// Componentes
		JPanel aux = new JPanel();
		aux.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lAux = new JLabel(new ImageIcon("./img/bank1.png"));
		botonExaminar = new JButton(EXAMINAR, new ImageIcon("./img/ex.png"));
		botonExaminar.setBorderPainted(false);
		botonExaminar.setBorder(new LineBorder(Color.BLACK));
		botonExaminar.setActionCommand(EXAMINAR);
		botonExaminar.addActionListener(this);
		botonAceptar = new JButton(ACEPTAR, new ImageIcon("./img/ac.png"));
		botonAceptar.setActionCommand(ACEPTAR);
		botonAceptar.setBorderPainted(false);
		botonAceptar.setBorder(new LineBorder(Color.BLACK));
		botonAceptar.addActionListener(this);
		campoPath = new JTextField();
		campoPath.setPreferredSize(new Dimension(180, 20));
		campoPath.setEditable(false);
		campoPath.setBackground(Color.WHITE);
		aux.add(botonExaminar);
		aux.add(campoPath);
		aux.add(botonAceptar);

		add(lAux, BorderLayout.NORTH);
		add(aux, BorderLayout.CENTER);
	}
	
	// Métodos fundamentales -------------------------------------------------------------------
	
	/**
	 * Se encarga de dar la ruta que hay en el campo de texto.
	 * @return Una cadena que representa la ruta del archivo.
	 */
	public String darPath(){
		return campoPath.getText();
	}
	
	/**
	 * Método que se encarga de modificar la ruta que se expondrá en el 
	 * campo de texto.
	 * @param path - Es la nueva ruta que se quiere exponer.
	 */
	public void modificarPath(String path){
		campoPath.setText(path);
	}
	
	// Métodos y servicios ---------------------------------------------------------------------

	
	/**
	 * Se encarga de abrir el selector de archivos para elegir el caso de prueba
	 * a visualizar. Luego actualiza el campo de texto con la ruta del archivo seleccionado.
	 */
	private void examinar(){
		JFileChooser selector = new JFileChooser("./casos/");
		int respuesta = selector.showOpenDialog(this);
		if(respuesta == JFileChooser.APPROVE_OPTION){
			File archivoSeleccionado = selector.getSelectedFile();
			String ruta = archivoSeleccionado.getPath();
			modificarPath(ruta);
		}
	}
	
	/**
	 * Se encarga de validar que en el campo de texto que contiene la ruta del archivo no
	 * esté vacío.
	 * @return Verdadero si el campo de texto tiene una ruta, falso de lo contrario.
	 */
	private boolean verificarPath(){
		boolean respuesta = true;
		String path = darPath();
		if(path.equals("")){
			respuesta = false;
		}
		return respuesta;
	}
	
	/**
	 * Método que se encarga de hacer el llamado al mundo para que se lea el archivo cuya
	 * ruta es la que ha dado el dialogo de carga. Muestra ventanas emergentes en caso
	 * de que la operación haya salido exitosa o haya salido mal.
	 */
	public void leerArchivo(){
		mapa = new Mapa();
		String rutaArchivo = darPath();
		File archivo = new File(rutaArchivo);
		try {
			mapa.cargarDatos(archivo);
			JOptionPane.showMessageDialog(this, "El archivo se cargó exitosamente", "Cargar Archivo", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Hubo problemas al cargar el archivo:\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Se encarga de crear el diálogo que permite visualizar los casos de prueba.
	 */
	public void crearDialogoCasoPrueba(){
		dialogoCasoPrueba = new DialogoCasosPrueba(this);
		dialogoCasoPrueba.setLocationRelativeTo(null);
		ArrayList<Ciudad> ciudades = mapa.darCiudades();
		dialogoCasoPrueba.actualizarLista(ciudades);
		dialogoCasoPrueba.setVisible(true);
	}
	
	/**
	 * Método que se encarga de crear el diálogo que permite visualizar la ciudad.
	 */
	public void crearDialogoVisualizacion() {
		Ciudad ciudadSeleccionada = dialogoCasoPrueba.darCiudadSeleccionada();
		dialogoVisualizacion = new DialogoVisualizacion(this, ciudadSeleccionada);
		dialogoVisualizacion.setLocationRelativeTo(null);
		dialogoVisualizacion.setVisible(true);
	}
	
	/**
	 * Método que se encarga de hacer los llamados al mundo para que se solucione
	 * la ciudad que trae el diálogo de casos de prueba.
	 */
	public void solucionarCiudad() {
		Ciudad seleccionada = dialogoCasoPrueba.darCiudadSeleccionada();
		String solucion = mapa.solucionarCiudad(seleccionada);
		
		String[] b = solucion.split("\n");
		String[] c = b[1].split(" ");
		int a[] = new int[c.length];
		for (int i = 0; i < a.length; i++) {
			a[i]=Integer.parseInt(c[i]);
		}
		
		Arrays.sort(a);
		solucion="";
		solucion+=b[0]+"\n";
		for (int i = 0; i < a.length; i++) {
			solucion+=a[i]+" ";
		}
		
		dialogoVisualizacion.actualizarAreaTexto(solucion);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equals(ACEPTAR)) {
			boolean esValido = verificarPath();
			if(esValido){
				leerArchivo();
				crearDialogoCasoPrueba();
			}else{
				JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un archivo primero", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}else if (comando.equals(EXAMINAR)) {
			examinar();
		}
	}
	
	public static void main(String[] args) {
		InterfazBankRobbery ventana = new InterfazBankRobbery();
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
}
