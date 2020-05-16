package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {
		
		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				idMap.put(rs.getInt("ccode"), new Country (rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme")));
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Country> getCountry(Map<Integer, Country> idMap, int anno) {

		List <Country> country = new ArrayList<>();
		String sql = "SELECT distinct state1no FROM contiguity WHERE year<=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				country.add(idMap.get(rs.getInt("state1no")));
			}
			
			conn.close();
			return country;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
}
	public List<Border> getBorder(Map<Integer, Country> idMap, List<Country> listCountry) {
	
		List <Border> border = new ArrayList<>();	
		String sql = "SELECT state1no, state2no FROM contiguity WHERE conttype=1";
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (listCountry.contains(idMap.get(rs.getInt("state1no"))) && listCountry.contains(idMap.get(rs.getInt("state2no"))))
					border.add(new Border (idMap.get(rs.getInt("state1no")), idMap.get(rs.getInt("state2no"))));
			}
			
			
			conn.close();
			return border;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
}
}
