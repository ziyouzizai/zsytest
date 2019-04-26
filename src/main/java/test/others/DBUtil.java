package test.others;

import org.apache.log4j.Logger;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 数据库工具类
 * 
 * @author zxy
 * @date 2016-12-14
 */
public class DBUtil {
	private final static Logger logger = Logger.getLogger(DBUtil.class);

	private static DBUtil instance;

	private String mysqlDriverClass = "com.mysql.jdbc.Driver";
	private String mysqlUrl = "jdbc:mysql://localhost:3306/trackingio_20181029";
	private String mysqlUsername = "root";
	private String mysqlPassword = "root";

	private Map<String, DataSource> datasourceMap;

	private DBUtil() {
	}

	public static DBUtil getInstance() {
		if (instance == null) {
			instance = new DBUtil();
		}
		return instance;
	}

	public Connection getConn(String dbType) {
		Connection conn = null;
		try {
			Class.forName(mysqlDriverClass);
			conn = DriverManager.getConnection(mysqlUrl, mysqlUsername,mysqlPassword);
		} catch (SQLException e) {
			logger.error("db can't connect to server" + "," + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public Connection getConn(String dbType,String dateSourceName) {
		Connection conn = null;
		try {
			conn = datasourceMap.get(dateSourceName).getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("db can't connect to server" + "," + e.getMessage());
		}

		return conn;
	}
	
	public void insert(String sql) {
		Connection conn = getConn("mysql");
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ResultSet query(String sql) {
		Connection conn = getConn("mysql");
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement getStatement(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return pstmt;
	}

	public void release(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				try {
					if (conn != null) {
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public void release(ResultSet rs, Statement pstmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				try {
					if (conn != null) {
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public void release(ResultSet rs, Statement st) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void release(Statement pstmt, Connection conn) {
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void ExcuteNonQueryBatch(List<String> sqlList) {
		Connection conn = null;
		Statement _Statement = null;
		try {

			if (sqlList != null && sqlList.size() > 0) {
				conn = getConn("mysql");
				conn.setAutoCommit(false);
				for (int i = 0; i < sqlList.size(); i++) {
					_Statement = conn.createStatement();

					logger.info(sqlList.get(i));
					_Statement.executeUpdate(sqlList.get(i));
					// System.out.println(sqlList.get(i));
				}
				conn.commit();
			}

		} catch (Exception e) {

			try {
				if (_Statement != null)
					_Statement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		String sql = "select * from account limit 10";
		ResultSet rs = DBUtil.getInstance().query(sql);
		while(rs.next()) {
			System.out.println(rs.getObject(1));
		}
	}
	
}
