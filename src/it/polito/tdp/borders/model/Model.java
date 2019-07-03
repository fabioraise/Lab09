package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country,DefaultEdge> grafo;
	private BordersDAO bdao;
	private List<Country> countriesList;
	private Map<Integer,Country> idMap;
	private ConnectivityInspector inspector;

	public Model() {
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		bdao = new BordersDAO();
		countriesList = new ArrayList<Country>();
		idMap = new HashMap<Integer,Country>();
		inspector = new ConnectivityInspector(grafo);
	}

	public void generaGrafo(int year) throws NumberFormatException{
		if(year < 1816 || year > 2016)
			throw new NumberFormatException();
		
		countriesList = bdao.loadAllCountries(idMap);
		
		Graphs.addAllVertices(grafo, countriesList);
		
		List<Border> borders = bdao.getCountryPairs(year, idMap);
		
		for(Border b : borders)
			grafo.addEdge(b.getC1(), b.getC2());
	}
	
	public String stampaDati() {
		String result = "";
		
		for(Country c : grafo.vertexSet())
			result += c.toString()+": "+grafo.degreeOf(c)+"\n";
		
		return result;
	}
	
	public int getNumeroComponentiConnesse() {
		return inspector.connectedSets().size();
	}

}
