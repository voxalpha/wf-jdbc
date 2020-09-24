package com.navteq.cf.foundation.workflow.test.jdbi.DeclarativeDAO;

import com.navteq.cf.foundation.workflow.test.jdbi.dao.GOTCastless;
import com.navteq.cf.foundation.workflow.test.jdbi.dao.GOTChar;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.BatchChunkSize;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.stream.Stream;

public interface GOTDAOTarget
{
  @SqlBatch("insert into castless(char_id, portrait) values (:charId, :portrait)")
  @BatchChunkSize(1000)
  void insertCastless(@BindBean List<GOTCastless> castless);

}
