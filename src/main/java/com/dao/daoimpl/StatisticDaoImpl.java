package com.dao.daoimpl;

import com.dao.StatisticDao;
import com.model.Statistic;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Asus
 * Date: 01.09.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
@Service
@Repository
public class StatisticDaoImpl implements StatisticDao {
    @Autowired
    SessionFactory factory;

    @Autowired
    DataSource dataSource;

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Statistic> getStatisticList() {
        return factory.getCurrentSession().createQuery("from com.model.Statistic").list();
    }

    public Statistic getStatisticById(int statisticId) {
        return (Statistic) factory.getCurrentSession().get(Statistic.class, statisticId);
    }

    public void deleteStatistic(Statistic statistic) {
        factory.getCurrentSession().delete(statistic);
    }

    public void updateStatistic(Statistic statistic) {
        factory.getCurrentSession().merge(statistic);
    }

    public void addService(Statistic statistic) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void printReport(Integer empId) {
        String fileName = "С://emp" + empId + ".xsl";
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            String sql = "select serv.naming, concat_ws(',',e.first_name, e.sur_name, e.last_name) empData, " +
                    "concat_ws(',',clt.first_name,clt.sur_name,clt.last_name) cltData , date_visit " +
                    " from statistic ststc " +
                    " left join service serv on(ststc.service_id = serv.service_id)" +
                    " left join employer e on(ststc.employer_id = e.employer_id)" +
                    " left join clients clt on(ststc.client_id = clt.client_id)" +
                    " where e.employer_id = ?" +
                    " group by serv.naming, date_visit, cltData, empData" +
                    " INTO OUTFILE ?" +
                    " FIELDS TERMINATED BY ','";

            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, empId.intValue());
            statement.setString(2, fileName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}