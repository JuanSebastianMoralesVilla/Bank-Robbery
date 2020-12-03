package interfaz;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import listaAdyacencias.Grafo;
import listaAdyacencias.Vertice;
import mundo.Ciudad;
import mundo.Sitio;

public class PanelLienzo extends JPanel implements ActionListener, MouseListener {

	// Constantes ----------------------------------------------------------------------
	
	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Representa el radio de los círculos que representan a los sitios de la
	 * ciudad.
	 */
	public final static int RADIO_SITIOS = 50;
	
	/**
	 * Representa el color de los sitios que son neutros.
	 */
	public final static Color COLOR_NEUTROS = Color.GREEN;
	
	/**
	 * Representa el color de los bancos.
	 */
	public final static Color COLOR_BANCOS = Color.RED;
	
	/**
	 * Representa el color de las estaciones de policía.
	 */
	public final static Color COLOR_ESTACIONES = Color.CYAN;
	
	/**
	 * Representa la fuente que es usada para visualizar la información textual
	 * o numérica.
	 */
	public final static Font FUENTE = new Font("Consolas", Font.BOLD, 12);
	
	/**
	 * Representa la acción de solucionar el problema con la ciudad actual.
	 */
	public final static String SOLUCIONAR = "Solucionar";
	
	// Atributos -----------------------------------------------------------------------
	
	/**
	 * Representa la relación con la ciudad que se quiere dibujar.
	 */
	private Ciudad ciudad;
	
	/**
	 * Representa las coordenadas en el panel que se le han asignado al
	 * punto inicial de cada carretera.
	 */
	private Hashtable<Integer, String> tablaCoordenadasCarreteras;
	
	/**
	 * Representa las coordenadas en el panel que se le han asignado a cada sitio.
	 */
	private Hashtable<Integer, String> tablaCoordenadasSitios;
	
	/**
	 * Representa el menú contextual del panel que permite solucionar la ciudad.
	 */
//	private JPopupMenu menuContextual;
	
	/**
	 * Representa el item que da paso a la solución de la ciudad que se está mostrando.
	 */
	private JMenuItem itemSolucion;
	
	/**
	 * Indica si a la ciudad seleccionada ya se le ha pedido solución. 
	 */
	private boolean haySolucion;
	
	/**
	 * Representa la relación con el diálogo.
	 */
	private DialogoVisualizacion principal;
	
	// Constructor ---------------------------------------------------------------------
	
	/**
	 * Construye el panel con sus propiedades.
	 * @param ciudad - Es la ciudad que se dibujará en el panel.
	 */
	public PanelLienzo(Ciudad ciudad, DialogoVisualizacion dialogo) {
		principal = dialogo;
		this.ciudad = ciudad;
		tablaCoordenadasCarreteras = new Hashtable<>();
		tablaCoordenadasSitios = new Hashtable<>();
		setLayout(new BorderLayout());
				
		JMenuItem itemGenerador = new JMenuItem("¡Abrir Generador de casos!");
		itemGenerador.addActionListener(this);
//		itemGenerador.setActionCommand(GENERAR);
		
		itemSolucion = new JMenuItem("Dar Solución");
		itemSolucion.addActionListener(this);
		itemSolucion.setActionCommand(SOLUCIONAR);
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Solución");
//		JMenu menu1 = new JMenu("Generador");

		mb.add(menu);
//		mb.add(menu1);
		menu.add(itemSolucion);
//		menu1.add(itemGenerador);
		haySolucion = false;
		addMouseListener(this);
		setBackground(Color.WHITE);
		add(mb,BorderLayout.NORTH);
	}
	
	// Métodos fundamentales ------------------------------------------------------------
	
	/**
	 * Método que se encarga de modificar el estado del atributo que indica que hay
	 * o no una solución disponible para mostrar.
	 * @param haySolucion - Valor lógico que puede ser verdadero si hay una solución que 
	 * mostrar o falso de lo contrario.
	 */
	public void haySolucion(boolean haySolucion){
		this.haySolucion = haySolucion;
		repaint();
	}
	
	// Métodos y servicios --------------------------------------------------------------
	
	
	
	/**
	 * 	Método que se encarga de abrir un archivo con ruta dada presente en el directorio del 
	 * programa
	 */
	public void abrirGenerador()
	{
		String rutaInstrucciones = new String("Jar_generador/Generador.jar");
		try{
			Runtime.getRuntime().exec("cmd /c start "+rutaInstrucciones);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "Problemas al abrir las instrucciones", "Instrucciones", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		dibujarCiudad(g2d, ciudad);
		if(haySolucion == true){
			pintarSolucion(g);
		}
	}
	
	/**
	 * Método auxiliar encargado de gestionar todos los procedimientos para dibujar
	 * correctamente la ciudad.
	 * @param g - Es el kit de gráficos usado para dibujar sobre el panel.
	 * @param ciudad - Es la ciudad que se quiere dibujar.
	 */
	private void dibujarCiudad(Graphics g, Ciudad ciudad) {
		calcularPosicionesSitios(ciudad);
		dibujarCalles(g, ciudad);
		dibujarSitios(g, ciudad);
	}
	
	/**
	 * Método que se encarga de calcular las coordenadas X y Y de cada sitio en la
	 * circunferencia. También se encarga de cuadrar las coordenadas X y Y para escribir
	 * de manera centrada el ID del sitio, que coincide con el punto inicial de una posible
	 * carretera.
	 * @param ciudad - Es la ciudad a la que se le quieren calcular las medidas.
	 */
	private void calcularPosicionesSitios(Ciudad ciudad) {
		Grafo<Sitio> sitios = ciudad.darSitios();
		int x = 0;
		int y = 0;
		int numeroSitios = ciudad.darNumeroSitios();
		int radio = 20*numeroSitios;
		setPreferredSize(new Dimension(3*radio, 3*radio));
		x = getWidth()/2;
		y = getHeight()/2;
		double razonAvance = (double) 360 / (double) numeroSitios;
		double razon = 0.0;
		HashMap<Sitio, Vertice<Sitio>> vertices = sitios.darVertices();
		Iterator<Sitio> iterador = vertices.keySet().iterator();
		for (double i = 0.0; i < 360.0; i = i+razonAvance) {
			Sitio sitioActual = iterador.next();
			razon = Math.toRadians(i);
			String coordenadasPuntoInicialPosibleCarretera = (int) (radio*Math.cos(razon)+x+21)+" "+(int) (radio*Math.sin(razon)+y+31);
			String coordenadasSitio = (int) (radio*Math.cos(razon)+x)+" "+(int) (radio*Math.sin(razon)+y);
			tablaCoordenadasCarreteras.put(sitioActual.darID(), coordenadasPuntoInicialPosibleCarretera);
			tablaCoordenadasSitios.put(sitioActual.darID(), coordenadasSitio);
		}
	}
	
	/**
	 * Método que se encarga de dibujar las calles de la ciudad teniendo en cuenta
	 * las medidas que están en la tabla de coordenadas asociadas a las carreteras.
	 * @param g - Es el kit de gráficos usados para dibujar sobre el panel.
	 * @param ciudad - Es la ciudad a la que se le quieren dibujar las calles.
	 */
	private void dibujarCalles(Graphics g, Ciudad ciudad) {
		Graphics2D g2d = (Graphics2D) g;
		ArrayList<String> listadoCarreteras = ciudad.darListadoCarreteras();
		for (int i = 0; i < listadoCarreteras.size(); i++) {
			String carreteraActual = listadoCarreteras.get(i);
			String[] split = carreteraActual.split(" ");
			Integer sitioA = Integer.parseInt(split[0]);
			Integer sitioB = Integer.parseInt(split[1]);
			Integer distancia = Integer.parseInt(split[2]);
			String coordenadasA = tablaCoordenadasCarreteras.get(sitioA);
			String coordenadasB = tablaCoordenadasCarreteras.get(sitioB);
			split = coordenadasA.split(" ");
			int xA = Integer.parseInt(split[0]);
			int yA = Integer.parseInt(split[1]);
			split = coordenadasB.split(" ");
			int xB = Integer.parseInt(split[0]);
			int yB = Integer.parseInt(split[1]);
			g2d.drawLine(xA, yA, xB, yB);
			g2d.drawString(distancia+"", (xA+xB)/2, (yA+yB)/2);
		}
	}
	
	/**
	 * Método que se encarga de dibujar los sitios de la ciudad que se pasa 
	 * por parámetro. De acuerdo al tipo de sitio, se elige un color distinto para 
	 * el círculo que se dibuja. Si el sitio es banco, entonces el color del círculo es
	 * rojo; si es policía el círculo es cyan; si es neutro, el círculo es verde.
	 * @param g - Es el kit de gráficos usados para dibujar en el panel.
	 * @param ciudad - Es la ciudad a la que se le quieren dibujar los sitios.
	 */
	private void dibujarSitios(Graphics g, Ciudad ciudad) {
		Graphics2D g2d = (Graphics2D) g;
		Grafo<Sitio> sitios = ciudad.darSitios();
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		HashMap<Sitio, Vertice<Sitio>> vertices = sitios.darVertices();
		Iterator<Sitio> iterador = vertices.keySet().iterator();
		while (iterador.hasNext()) {
			Sitio sitioActual = iterador.next();
			Integer ID = sitioActual.darID();
			char tipoSitio = sitioActual.darTipo();
			switch (tipoSitio) {
			case Sitio.BANCO:
				g2d.setColor(COLOR_BANCOS);
				break;
			case Sitio.POLICIA:
				g2d.setColor(COLOR_ESTACIONES);
				break;
			case Sitio.NEUTRO:
				g2d.setColor(COLOR_NEUTROS);
				break;
			}
			String coordenadasSitio = tablaCoordenadasSitios.get(ID);
			String[] split = coordenadasSitio.split(" ");
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);
			g2d.fillOval(x, y, RADIO_SITIOS, RADIO_SITIOS);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x, y, RADIO_SITIOS, RADIO_SITIOS);
			coordenadasSitio = tablaCoordenadasCarreteras.get(ID);
			split = coordenadasSitio.split(" ");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			g2d.drawString(ID+"", x, y);
		}
	}
	
	/**
	 * Método que se encarga de cambiar el color a los sitios que son bancos y son solución
	 * al problema planteado.
	 * @param IDs - Son los IDs de los bancos que están más alejados pero más cercanos
	 * que cualquier estación de policía.
	 */
	private void pintarSolucion(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Grafo<Sitio> sitios = ciudad.darSitios();
		HashMap<Sitio, Vertice<Sitio>> vertices = sitios.darVertices();
		Iterator<Sitio> iterador = vertices.keySet().iterator();
		while(iterador.hasNext()){
			Sitio sitioActual = iterador.next();
			if(sitioActual.esSolucion()){
				int ID = sitioActual.darID();
				String coordenadasSitio = tablaCoordenadasSitios.get(ID);
				String[] split = coordenadasSitio.split(" ");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				g2d.setColor(Color.YELLOW);
				g2d.fillOval(x, y, RADIO_SITIOS, RADIO_SITIOS);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(x, y, RADIO_SITIOS, RADIO_SITIOS);
				coordenadasSitio = tablaCoordenadasCarreteras.get(ID);
				split = coordenadasSitio.split(" ");
				x = Integer.parseInt(split[0]);
				y = Integer.parseInt(split[1]);
				g2d.drawString(ID+"", x, y);
			}
		}
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equals(SOLUCIONAR)) {
			principal.solucionarCiudad();
		}
//		else if (comando.equals(GENERAR)) {
//			abrirGenerador();
//		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int clic = e.getButton();
		if(clic == MouseEvent.BUTTON3) {
//			int x = e.getX();
//			int y = e.getY();
//			menuContextual.show(this, x, y);
		}
	}
}
