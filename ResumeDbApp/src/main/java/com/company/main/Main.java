/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.main;

import com.company.dao.inter.CountryDaoInter;
import com.company.dao.inter.EmploymentHistoryDaoInter;
import com.company.dao.inter.SkillDaoInter;

/**
 *
 * @author Seymur
 */
public class Main {

    public static void main(String[] args) throws Exception {
        EmploymentHistoryDaoInter employmentHistory = Context.instanceEmploymentHistoryDao();
        System.out.println(employmentHistory.getAllEmploymentHistoryByUserId(7));
    }
}
