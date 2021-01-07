package de.cas.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.Settings;

public class DBManager {
	private static final DBManager instance = new DBManager();
	public static DBManager getInstance() {
		return instance;
	}
	
	private Connection connection;
	
	private final String tableName = CASSettings.getInstance().getProperty(Property.DATASOURCE_TABLENAME);
	
	private String strCreateTable = "CREATE TABLE " + tableName + 
									" ("+ Settings.ATR_NAME +" VARCHAR(256) NOT NULL PRIMARY KEY, " + 
									" "+ Settings.ATR_Y +" INT NOT NULL," + 
									" "+ Settings.ATR_X +" INT NOT NULL," + 
									" "+ Settings.ATR_HEIGHT +" INT NOT NULL," + 
									" "+ Settings.ATR_WIDTH +" INT NOT NULL," + 
									" "+ Settings.ATR_CELLSIZE +" INT NOT NULL," + 
									" "+ Settings.ATR_DELAY +" INT NOT NULL)";
	private String strSelectSettings = "SELECT * FROM "+tableName+" WHERE ("+ Settings.ATR_NAME +"=?)";
	private String strSelectNames = "SELECT "+ Settings.ATR_NAME +" FROM " + tableName;
	private String strInsertValues = "INSERT INTO "+tableName+" ("+Settings.ATR_NAME+", "+Settings.ATR_Y+", "+Settings.ATR_X+", "+Settings.ATR_HEIGHT+", "+Settings.ATR_WIDTH+", "+Settings.ATR_CELLSIZE+", "+Settings.ATR_DELAY+") VALUES (?, ?, ?, ?, ?, ?, ?)";
	private String strDeleteValues = "DELETE FROM "+tableName+" WHERE ("+ Settings.ATR_NAME +"=?)";
	
	String driver = CASSettings.getInstance().getProperty(Property.DATASOURCE_DRIVER);
	String url = CASSettings.getInstance().getProperty(Property.DATASOURCE_URL);
	String dbName = CASSettings.getInstance().getProperty(Property.DATASOURCE_DATABASE_NAME);
	String createStr = CASSettings.getInstance().getProperty(Property.DATASOURCE_CREATE);
	
	private DBManager(){
		 startup();
	}
	
	private void startup(){
		try {
			Class.forName(driver);
			
			connection = DriverManager.getConnection(url + dbName + ";create=" +createStr);

			if(!tableExists(tableName)){
				createTable();
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		if ("org.apache.derby.jdbc.EmbeddedDriver".equals(driver)) {
			try {
				DriverManager.getConnection(url+";shutdown=true");
				System.out.println("Database shutdown successfuly");
			} catch (SQLException e) {
				if (e.getSQLState().equals("XJ015")) {
					System.out.println("Database did not shutdown successfuly");
				}
			}
		}
	}
	
	
	public boolean tableExists(String table) throws SQLException{
		DatabaseMetaData dmd = connection.getMetaData();
		ResultSet tables = dmd.getTables(null, null, null, new String[]{"TABLE"});
		while (tables.next()) {
			if (tables.getString("TABLE_NAME").toLowerCase().equals(table.toLowerCase()))
				return true;
		}
		return false;
	}
	
	
	private boolean createTable() {
		try {
			PreparedStatement pStmt = connection.prepareStatement(strCreateTable);
			return pStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public ArrayList<String> getSettingsNames() throws SQLException {
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ArrayList<String> names = new ArrayList<String>();
		try {
			pStmt = connection.prepareStatement(strSelectNames);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				names.add(rs.getString(Settings.ATR_NAME));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeHelper(pStmt,rs);
		}
		return names;
	}
	
	
	public boolean saveSettings(Settings settings) throws SQLException {
		int res = 0;

		PreparedStatement pStmt = connection.prepareStatement(strInsertValues);

		pStmt.setString(1, settings.getName());
		pStmt.setInt(2, settings.getY());
		pStmt.setInt(3, settings.getX());
		pStmt.setInt(4, settings.getHeight());
		pStmt.setInt(5, settings.getWidth());
		pStmt.setInt(6, settings.getCellSize());
		pStmt.setInt(7, settings.getDelay());
		
		res = pStmt.executeUpdate();

		return (res)>0?true:false;
	}
	
	
	public boolean deleteSettings(String name) throws SQLException {
		PreparedStatement pStmt = null;
		int res = 0;
		try {
			pStmt = connection.prepareStatement(strDeleteValues);
			pStmt.setString(1, name);
			res = pStmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeHelper(pStmt,null);
		}
		return (res)>0?true:false;
	}
	
	
	public Settings recoverSettings(String name) {
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Settings resSettings = new Settings();
		try {
			pStmt = connection.prepareStatement(strSelectSettings);
			pStmt.setString(1, name);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				resSettings.setName(name);
				resSettings.setY(rs.getInt(Settings.ATR_Y));
				resSettings.setX(rs.getInt(Settings.ATR_X));
				resSettings.setHeight(rs.getInt(Settings.ATR_HEIGHT));
				resSettings.setWidth(rs.getInt(Settings.ATR_WIDTH));
				resSettings.setCellSize(rs.getInt(Settings.ATR_CELLSIZE));
				resSettings.setDelay(rs.getInt(Settings.ATR_DELAY));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeHelper(pStmt,rs);
		}
		return resSettings;
	}
	
	
	private void closeHelper(PreparedStatement pStmt, ResultSet rs){
		if (pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
