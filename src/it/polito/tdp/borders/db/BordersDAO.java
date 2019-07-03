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

	public List<Country> loadAllCountries(Map<Integer,Country> idMap) {

		String sql = "SELECT CCode, StateAbb, StateNme " + 
					 "FROM country";
		
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				// System.out.format("%d %s %s\n", rs.getInt("CCode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				Country c = new Country(rs.getInt("CCode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(c);
				idMap.put(rs.getInt("ccode"), c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int year, Map<Integer, Country> idMap) {
		
		String sql = "SELECT state1no, state2no " + 
					 "FROM contiguity " + 
					 "WHERE YEAR <= ? AND conttype = 1";
		
		List<Border> borders = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Country c1 = idMap.get(rs.getInt("state1no"));
				Country c2 = idMap.get(rs.getInt("state2no"));
				
				Border b = new Border(c1, c2);
				
				borders.add(b);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return borders;
	}
}
