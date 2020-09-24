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

public interface GOTDAOSource
{
  @SqlQuery
  (
      "select c.*, a.name, a.chamber_number\n"
          + "from char c\n"
          + "left join address a on c.address_id = a.address_id"
  )
  @RegisterBeanMapper(GOTChar.class)
  Stream<GOTChar> getChars();

  @SqlUpdate
  (
      "update char set portrait = :portrait\n"
       +  "where first_name = :firstName and last_name = :lastName"
  )
  int updateChar(@BindBean GOTChar gotChar);
}
