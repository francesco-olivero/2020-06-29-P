package it.polito.tdp.PremierLeague.model;

public class matchPlayers {
	Integer matchID1;
	Integer matchID2;
	int numeroGiocatori;
	
	
	public matchPlayers(Integer matchID1, Integer matchID2, int numeroGiocatori) {
		this.matchID1 = matchID1;
		this.matchID2 = matchID2;
		this.numeroGiocatori = numeroGiocatori;
	}
	
	
	public Integer getMatchID1() {
		return matchID1;
	}
	public void setMatchID1(Integer matchID) {
		this.matchID1 = matchID;
	}
	public Integer getMatchID2() {
		return matchID2;
	}
	public void setMatchID2(Integer matchID2) {
		this.matchID2 = matchID2;
	}
	public int getNumeroGiocatori() {
		return numeroGiocatori;
	}
	public void setNumeroGiocatori(int numeroGiocatori) {
		this.numeroGiocatori = numeroGiocatori;
	}


	@Override
	public String toString() {
		return "matchPlayers [matchID1=" + matchID1 + ", matchID2=" + matchID2 + ", numeroGiocatori=" + numeroGiocatori
				+ "]";
	}
	
	
}
