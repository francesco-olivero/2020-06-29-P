package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Match, DefaultWeightedEdge> grafo;
	Map<Integer, Match> idMapMatches;
	
	// variabili per la ricorsione
	 List<Match> percorsoBest ;
	 double pesoBest;

	public Model() {
		dao = new PremierLeagueDAO();
		idMapMatches = new HashMap<Integer, Match>();
	}
	
	
	public String creaGrafo(Month month, int minuti) {
		grafo = new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(grafo, dao.getMatchesByMonth(month));
		for (Match m : dao.getMatchesByMonth(month)) {
			this.idMapMatches.put(m.getMatchID(), m);
		}
		
		//aggiungo gli archi
		for (matchPlayers mp: dao.getArchi(month, minuti)) {
			Match m1 = this.idMapMatches.get(mp.getMatchID1());
			Match m2 = this.idMapMatches.get(mp.getMatchID2());
			
			if (m1!=null && m2!=null) {
				Graphs.addEdge(this.grafo, m1, m2, mp.getNumeroGiocatori());
			}
		}
		return "Grafo creato\n"+"# VERTICI:" + this.grafo.vertexSet().size()+"\n"+"# ARCHI:" + this.grafo.edgeSet().size();
	}
	
	public String getMax(Month month, int minuti) {
		List<matchPlayers> list = new ArrayList<matchPlayers>();
		int max = Integer.MIN_VALUE;
		
		for (matchPlayers mp : this.dao.getArchi(month, minuti)) {
			if (mp.getNumeroGiocatori()>max) {
				max = mp.getNumeroGiocatori();
				list.clear();
				list.add(mp);
			}else if (mp.getNumeroGiocatori()==max) {
				list.add(mp);
			}
		}
		String ret="\n\n";
		for (matchPlayers mp : list) {
			ret+= mp.getMatchID1()+" = "+this.idMapMatches.get(mp.getMatchID1()).getTeamHomeNAME()+" vs "+this.idMapMatches.get(mp.getMatchID1()).getTeamAwayNAME()+" - "+mp.getMatchID2()+" = "+this.idMapMatches.get(mp.getMatchID2()).getTeamHomeNAME()+" vs "+this.idMapMatches.get(mp.getMatchID2()).getTeamAwayNAME()+"\n";
		}
		return ret;
	}


	public Map<Integer, Match> getIdMapMatches() {
		return idMapMatches;
	}
	
	
	public List<Match> percorsoMigliore(Match partenza, Match arrivo) {
		this.percorsoBest = null ;
		this.pesoBest = 0;
		
		List<Match> parziale = new ArrayList<Match>() ;
		parziale.add(partenza) ;
		
		cerca(parziale, 1, arrivo) ;
		
		return this.percorsoBest ;
	}
	
	private void cerca(List<Match> parziale, int livello, Match arrivo) {
		
		Match ultimo = parziale.get(parziale.size()-1) ;
		
		// caso terminale: ho trovato l'arrivo
		if(ultimo.equals(arrivo)) {
			if(this.percorsoBest==null) {
				this.percorsoBest = new ArrayList<>(parziale) ;
				this.pesoBest=getPesoParziale(parziale);
				return ;
			} else if( getPesoParziale(parziale) < this.pesoBest ) {
				this.pesoBest=getPesoParziale(parziale);
				this.percorsoBest = new ArrayList<>(parziale) ;
				return ;
			} else {
				return ;
			}
		}
			
			
			// generazione dei percorsi
			// cerca i successori di 'ultimo'
			for(DefaultWeightedEdge e: this.grafo.edgesOf(ultimo)) {
				Match prossimo = Graphs.getOppositeVertex(this.grafo, e, ultimo) ;
				
				if(!stesseSquadre(ultimo, prossimo)) {
					
					
					if(!parziale.contains(prossimo)) { // evita i cicli
						parziale.add(prossimo);
						cerca(parziale, livello + 1, arrivo);
						parziale.remove(parziale.size()-1) ;
					}
				}
			}	
		}

	public double getPesoParziale(List<Match> parziale) {
		double peso = 0;
		for (int i=0; i<parziale.size()-1; i++) { 
			DefaultWeightedEdge dwe = this.grafo.getEdge(parziale.get(i), parziale.get(i+1));
			peso+=this.grafo.getEdgeWeight(dwe);
		}
		return peso;
	}


	private boolean stesseSquadre(Match ultimo, Match prossimo) {
		if (ultimo.getTeamHomeID().equals(prossimo.getTeamHomeID()) && ultimo.getTeamAwayID().equals(prossimo.getTeamAwayID())) {
			return true;
		}
		if (ultimo.getTeamHomeID().equals(prossimo.getTeamAwayID()) && ultimo.getTeamAwayID().equals(prossimo.getTeamHomeID())) {
			return true;
		}
		return false;
	}


	public double getPesoBest() {
		return pesoBest;
	}
	
	
	
	
}
