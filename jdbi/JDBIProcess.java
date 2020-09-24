package com.navteq.cf.foundation.jdbi;

import com.navtech.db.Database;
import com.navteq.cf.foundation.process.Atom;
import com.navteq.cf.foundation.workflow.InvalidConfigurationException;
import com.navteq.cf.foundation.workflow.WorkflowJDBIConnectionFactory;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class JDBIProcess extends Atom
{
  protected Map<String, Transport> transports = new HashMap<>();

  protected static class Transport
  {
    Database database;
    Handle handle;
  }

  protected void connect()
  {
    transports.forEach(
        (n, t) ->
        {
          Jdbi jdbi = Jdbi.create(new WorkflowJDBIConnectionFactory(t.database));
          jdbi.installPlugin(new SqlObjectPlugin());
          //Handle handle = Jdbi.open(new WorkflowJDBIConnectionFactory(t.database));
          Handle handle = jdbi.open();
          handle.begin();
          t.handle = handle;
        }
    );
  }

  private void disconnect()
  {
     transports.forEach(
         (n, t) ->
         {
           t.handle.commit();
           t.handle.close();
         }
     );
  }



  protected boolean process() throws InvalidConfigurationException
  {
    return true;
  }

  @Override
  protected boolean convert()
  {
    try
    {
      connect();
      return process();
    }
    catch(InvalidConfigurationException e)
    {
      logger.error(e);
      return false;
    }
    finally
    {
      disconnect();
    }
  }
}
