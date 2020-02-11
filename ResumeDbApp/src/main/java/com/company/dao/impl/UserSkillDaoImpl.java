/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.entity.Country;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserSkillDaoInter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elxan
 */
public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter {

    private User getUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String profileDesc = rs.getString("profile_description");
        String address = rs.getString("address");
        int nationalityId = rs.getInt("nationality_id");
        int birthplaceId = rs.getInt("birthplace_Id");
        String nationalityStr = rs.getString("nationality");
        String birthplaceStr = rs.getString("birthplace");
        Date birthdate = rs.getDate("birthdate");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);

        return new User(id, name, surname, email, phone, profileDesc, address, birthdate, nationality, birthplace);
    }

    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userSkillId = rs.getInt("userSkillId");
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        return new UserSkill(userSkillId, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try (Connection c = connect()) {

            PreparedStatement stmt = c.prepareStatement("SELECT"
                    + "	us.id as userSkillId,"
                    + " u.*,"
                    + "	us.skill_id,"
                    + "	s.NAME AS skill_name,"
                    + "	us.power "
                    + "FROM"
                    + "	user_skill us"
                    + "	LEFT JOIN USER u ON us.id = u.id"
                    + "	LEFT JOIN skill s ON us.skill_id = s.id "
                    + "WHERE"
                    + "	us.user_id = ?");
            stmt.setInt(1, userId);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserSkill u = getUserSkill(rs);
                result.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public UserSkill getById(int id) {
        UserSkill us = null;
        try (Connection conn = connect()) {
            Statement statement = conn.createStatement();
            statement.execute("select * from user_skill where id = " + id);
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                us = getUserSkill(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return us;
    }

    @Override
    public boolean insertUserSkill(UserSkill us) {
        try (Connection conn = connect()) {

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_skill (skill_id, user_id, power) VALUES (?, ?, ? )");

            stmt.setInt(1, us.getSkill().getId());
            stmt.setInt(2, us.getUser().getId());
            stmt.setInt(3, us.getPower());

            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserSkill(UserSkill us) {
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement("update user_skill set user_id = ?, skill_id = ?, power = ? where id = ?");
            stmt.setInt(1, us.getUser().getId());
            stmt.setInt(2, us.getSkill().getId());
            stmt.setInt(3, us.getPower());
            return stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUserSkill(int id) {
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement("delete from user_skill where id = ?");
            stmt.setInt(1, id);
            return stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
