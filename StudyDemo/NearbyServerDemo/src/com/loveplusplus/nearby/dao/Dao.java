package com.loveplusplus.nearby.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.loveplusplus.nearby.bean.GPS;
import com.loveplusplus.nearby.bean.UserInfo;
import com.loveplusplus.nearby.message.NearbyInfo;
import com.loveplusplus.nearby.util.DBUtil;

/**
 * 类说明
 * 
 * @author 程辉
 * @version V1.0 创建时间：2013-6-15 下午3:42:52
 */
public class Dao {

	public List<NearbyInfo> findGPSList() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<NearbyInfo> result = null;
		try {
			conn = DBUtil.getDBConn();
			String sql = "SELECT g.*,u.* FROM gps  g INNER JOIN user_info u ON g.user_id=u.id ";
			ps = conn.prepareStatement(sql);
			// ps.setString(1, key);
			rs = ps.executeQuery();

			result = new ArrayList<NearbyInfo>();
			NearbyInfo info;
			while (rs.next()) {

				info = new NearbyInfo();
				info.setDescribe(rs.getString("personal_note"));
				info.setLatitude(rs.getString("latitude"));
				info.setLongitude(rs.getString("longitude"));
				info.setName(rs.getString("name"));
				info.setPicUrl(rs.getString("pic_url"));
				int sex = rs.getInt("sex");
				if (sex == 0) {
					info.setSex("女");
				} else {
					info.setSex("男");
				}
				result.add(info);
			}

		} finally {
			DBUtil.closeDB(rs, ps, conn);
		}
		return result;
	}

	public void save(GPS gps) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConn();
			String sql = "INSERT INTO gps(user_id,longitude,latitude,time) VALUES(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, gps.getUser().getId());
			ps.setString(2, gps.getLongitude());
			ps.setString(3, gps.getLatitude());
			ps.setTimestamp(4, new java.sql.Timestamp(gps.getTime().getTime()));
			ps.executeUpdate();
		} finally {
			DBUtil.closeDB(rs, ps, conn);
		}
	}

	public void saveUser(UserInfo user) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConn();
			String sql = "INSERT INTO user_info(id,name,personal_note) VALUES(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getDescribe());

			ps.executeUpdate();

		} finally {
			DBUtil.closeDB(rs, ps, conn);
		}
	}

	public UserInfo findUser(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConn();
			String sql = "SELECT * FROM  user_info where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {

				UserInfo info = new UserInfo();
				info.setId(rs.getString("id"));
				info.setDescribe(rs.getString("personal_note"));
				info.setName(rs.getString("name"));
				return info;
			}

		} finally {
			DBUtil.closeDB(rs, ps, conn);
		}
		return null;
	}

	public void updateUser(UserInfo user) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConn();
			String sql = "UPDATE user_info SET name=? , personal_note=? WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getDescribe());
			ps.setString(3, user.getId());
			ps.executeUpdate();

		} finally {
			DBUtil.closeDB(rs, ps, conn);
		}
	}

}
