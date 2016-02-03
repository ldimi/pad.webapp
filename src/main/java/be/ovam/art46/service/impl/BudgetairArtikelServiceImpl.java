package be.ovam.art46.service.impl;

import be.ovam.art46.service.BudgetairArtikelService;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by koencorstjens on 25-7-13.
 */
@Service
public class BudgetairArtikelServiceImpl implements BudgetairArtikelService {
    @Autowired
    @Qualifier("sqlSession")
    SqlSession sqlSession;

    public String getBudgetairArtikel(Integer budgetairArtikel) {
        return sqlSession.selectOne("be.ovam.art46.mappers.BudgetairArtikelMapper.getCode", budgetairArtikel);
    }

}
