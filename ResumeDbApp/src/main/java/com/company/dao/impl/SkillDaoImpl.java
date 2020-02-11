/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.SkillDaoInter;
import com.company.entity.Skill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Seymur
 */
public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {

    private Skill getSkill(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Skill skill = new Skill(id, name);
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> result = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("select * from skill");
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                Skill s = getSkill(rs);
                result.add(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Skill getById(int id) {
        Skill skill = null;
        try (Connection c = connect()) {
            PreparedStatement statement = c.prepareStatement("select * from skill where id = ?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                skill = getSkill(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public boolean insertSkill(Skill skl) {
        Connection conn;
        boolean b = true;
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement("insert skill (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, skl.getName());
            b = stmt.execute();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                skl.setId(generatedKeys.getInt(1));
            }

        } catch (Exception ex) {
            System.err.println(ex);
            b = false;
        }
        return b;
    }

    @Override
    public boolean updateSkill(Skill skill) {
        try (Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("update skill set name = ? where id = ?");
            stmt.setString(1, skill.getName());
            stmt.setInt(2, skill.getId());
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeSkill(int id) {
        try (Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("delete from skill wehere id = ?");
            stmt.setInt(1, id);
            return stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
