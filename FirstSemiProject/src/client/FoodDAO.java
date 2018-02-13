package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

public class FoodDAO {

	private BasicDataSource ds;

	public FoodDAO() {
		ds = new BasicDataSource();

		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		ds.setUsername("scott");
		ds.setPassword("tiger");

		ds.setInitialSize(25); // 25개의 Connection을 공유하면서 사용할 수 있음.
		
	}

// 런치테이블 생성 메소드
	public void createTable() {
		String sql = "create table foods(restraunt varchar(40), name varchar2(30), eatDate varchar2(30), eatTimes number(10))";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			System.out.println("테이블 생성 성공");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("테이블이 이미 존재 합니다.");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 데이터 출력 메소드
	public List<FoodDTO> select() {
		String sql = "select * from foods order by eatDate";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<FoodDTO> list = new ArrayList<FoodDTO>();

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				FoodDTO dto = new FoodDTO();

				dto.setEatDate(rs.getString("EatDate"));
				dto.setName(rs.getString("name"));
				dto.setRestraunt(rs.getString("restraunt"));

				list.add(dto);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 데이터 출력 메소드
		public List<FoodDTO> selectYesterday() {
			String sql = "select * from foods where eatDate = to_char(sysdate-1, 'yyyy/mm/dd')";

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			List<FoodDTO> list = new ArrayList<FoodDTO>();

			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();

				while (rs.next()) {
					FoodDTO dto = new FoodDTO();

					dto.setEatDate(rs.getString("EatDate"));
					dto.setName(rs.getString("name"));
					dto.setRestraunt(rs.getString("restraunt"));

					list.add(dto);
				}
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
	// 데이터 삽입 기능
	public void insertResult(FoodDTO dto) {
		
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;	
		
		try {	
			conn = ds.getConnection();
		
		if(findSame(dto)) {
			sql = "update foods set eattimes = eattimes+1, eatDate = to_char(sysdate, 'yyyy/mm/dd') where restraunt = ? and name =?";
		} else {				
			sql = "insert into foods values(?,?,to_char(sysdate, 'yyyy/mm/dd'), 1)";
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getRestraunt());
		pstmt.setString(2, dto.getName());

		
		int result = pstmt.executeUpdate();

		System.out.println("저장된 데이터 갯수 : " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void insertSubmitted(FoodDTO dto) {
		// TODO Auto-generated method stub
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;	
		
		try {	
			conn = ds.getConnection();
		
		if(findSame(dto)) {
			return;
		} else {				
			sql = "insert into foods values(?,?, null, 0)";
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getRestraunt());
		pstmt.setString(2, dto.getName());

		
		int result = pstmt.executeUpdate();

		System.out.println("저장된 데이터 갯수 : " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// 데이터 수정 기능
	public void update(FoodDTO dto) {
		Connection conn = null;
		String sql = "update foods set restraunt = ?, eatDate = ?, eatTimes= ? where name = ?";

		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getRestraunt());
			pstmt.setString(2, dto.getEatDate());
			pstmt.setInt(3, dto.getEatTimes());
			pstmt.setString(4, dto.getName());

			int result = pstmt.executeUpdate();

			System.out.println("수정 데이터 갯수 : " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 데이터 수정 기능
		public boolean findSame(FoodDTO dto) {
			Connection conn = null;
			String sql = "select * from foods where name = ? and restraunt = ?";

			PreparedStatement pstmt = null;

			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getRestraunt());

				ResultSet result = pstmt.executeQuery();
				
				if(result.next()) {
					conn.close();
					return true;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return false;
		}

	// 데이터 삭제 기능
	public void delete(String del) {
		String sql = "delete from foods where restaurant= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, del);

			int result = pstmt.executeUpdate();
			System.out.println("삭제한 데이터 수 : " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
