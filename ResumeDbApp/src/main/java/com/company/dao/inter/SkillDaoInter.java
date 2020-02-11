/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.inter;

import com.company.entity.Skill;
import java.util.List;

/**
 *
 * @author Seymur
 */
public interface SkillDaoInter {
    public List<Skill> getAll();
    
    public Skill getById(int id);
    
    public boolean insertSkill(Skill skl);
    
    public boolean updateSkill(Skill skill);
    
    public boolean removeSkill(int id);
}
