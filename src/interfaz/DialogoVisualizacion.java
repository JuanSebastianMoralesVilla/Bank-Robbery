package interfaz;

import javax.swing.*;

import model.Ciudad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogoVisualizacion extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -192867667145239793L;
	
	
	public static final String DIJKSTRA = "Dijkstra";
	
	public static final String WARSHALL = "Floyd Warshall";
	
	// Atributos ------------------------------------------------------------------------------
	
	/**
	 * Representa la relación con la ventana principal.
	 */
	private InterfazBankRobbery principal;
	
	/**
	 * Representa la relación con el panel que dibuja la ciudad.
	 */
	private PanelLienzo panelLienzo;
	
	/**
	 * Representa la barra desplazadora que contiene el panel lienzo.
	 */
	private JScrollPane barraDesplazadora;
	
	/**
	 * Representa el textArea donde se visualiza la solución del problema
	 */
	private JTextArea areaRespuesta;
	
	private JButton btnDijkstra;
	
	private JButton btnWarshall;
	
	
	// Constructor ----------------------------------------------------------------------------
	
	public DialogoVisualizacion(InterfazBankRobbery ventana, Ciudad ciudad) {
		super(ventana, true);
		principal = ventana;
		setTitle("Bank Robbery");
		setResizable(true);
		setIconImage(new ImageIcon("./img/ic.png").getImage());
		setSize(1170, 728);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// Creación de los componentes
		JLabel lAux = new JLabel(new ImageIcon("./img/banco.png"));	
		lAux.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		add(lAux,BorderLayout.NORTH);
		
		JPanel panelRespuesta = new JPanel();
		panelRespuesta.setPreferredSize(new Dimension(342, 600));
		panelRespuesta.setLayout(new FlowLayout());

		
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
//				lAux.getIcon().setSize(new Dimension(this., arg1));
				// Evento que se llama cuando el tamaño del JFrame a  sido modificado
			}
		});
		
		
		JLabel lAux2 = new JLabel(new ImageIcon("./img/a.png"));	
		areaRespuesta = new JTextArea("",9, 6);
		areaRespuesta.setEditable(false);
		areaRespuesta.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		areaRespuesta.setBackground(Color.WHITE);
		areaRespuesta.setFont(new Font("Consolas", Font.PLAIN, 36));
		
		btnDijkstra = new JButton(DIJKSTRA);
		btnDijkstra.setActionCommand(DIJKSTRA);
		btnDijkstra.addActionListener(this);
		btnWarshall = new JButton(WARSHALL);
		btnWarshall.setActionCommand(WARSHALL);
		btnWarshall.addActionListener(this);
		
		JScrollPane scroll = new JScrollPane(areaRespuesta);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(342, 185));
		panelRespuesta.add(btnDijkstra);
		panelRespuesta.add(btnWarshall);
		panelRespuesta.add(scroll);
		
		

		
		
		panelLienzo = new PanelLienzo(ciudad, this);
		barraDesplazadora = new JScrollPane(panelLienzo);
		barraDesplazadora.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		barraDesplazadora.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(barraDesplazadora, BorderLayout.CENTER);
		add(panelRespuesta, BorderLayout.EAST);
		setResizable(false);
	}
	
	// Métodos fundamentales ------------------------------------------------------------------
	
	// Métodos y servicios --------------------------------------------------------------------
	
	/**
	 * Método que se encarga de actualizar el área de texto que expone la solución
	 * @param texto - Es la solución para la ciudad elegida.
	 */
	public void actualizarAreaTexto(String texto) {
		areaRespuesta.setText(texto);
	}

	/**
	 * 
	 */
	public void solucionarCiudad() {
		principal.solucionarCiudad();
		panelLienzo.haySolucion(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando = e.getActionCommand();
		if (comando.equals(DIJKSTRA)) {
			System.out.println("ingreso");
			principal.solucionarCiudad();			
		}else {
			System.out.println("ingreso");
			principal.solucionarCiudadFloyd();
		}
		
	}
}
