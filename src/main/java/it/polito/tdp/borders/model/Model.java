package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	public Map <Integer, Country> idMap;
	private BordersDAO dao;
	private Graph <Country, DefaultEdge> grafo; 
	private List <Country> listCountry;
	
	public Model() {
	
		idMap = new TreeMap<>();
		dao = new BordersDAO();
		
	}
	
	public List <Country> tuttiStati () {
		dao.loadAllCountries(idMap);
		List <Country> listaCountry = new LinkedList<>(idMap.values());
		Collections.sort(listaCountry);
		return listaCountry;
	}
	
	public Graph <Country, DefaultEdge> creaGrafo(int anno) {
		grafo = new SimpleGraph <>(DefaultEdge.class);
		listCountry = new ArrayList<>(dao.getCountry(idMap, anno));

		for (Country c : dao.getCountry(idMap, anno))
			grafo.addVertex(c);
		for (Border b : dao.getBorder(idMap, listCountry))
			grafo.addEdge(b.getC1(), b.getC2());
		
		return grafo;
	}
	public Graph <Country, DefaultEdge> getGrafo() {
		return this.grafo;
	}
	
	public int vertici() {
		return grafo.vertexSet().size();
	}
	
	public int archi() {
		return grafo.edgeSet().size();
	}
	
	public int componenti () {
		ConnectivityInspector <Country, DefaultEdge> c = new ConnectivityInspector<>(grafo);
		return c.connectedSets().size();
	}
	
	public List<Country> visitaAmpiezza(Country source) {
		List<Country> visita = new ArrayList<>();
		
		GraphIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(grafo, source);
		while(bfv.hasNext()) {
			visita.add( bfv.next() ) ;
		}
		
		return visita ;
	}
}
