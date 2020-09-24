package com.navteq.cf.foundation.jdbi;

import org.jdbi.v3.core.result.ResultBearing;
import org.jdbi.v3.core.result.ResultIterable;

import java.util.List;

public class JdbiInput extends JdbiIO implements Input
{
  @Override
  public <P> ResultBearing query(P param)
  {
    if (param != null)
    {
      return handle.createQuery(sql).bindBean(param);
    }
    else
    {
      return handle.createQuery(sql);
    }
  }

  @Override
  public <R, P> ResultIterable<R> getIterableResult(Class<R> dataClass, P param)
  {
    ResultBearing r = query(param);
    return r.mapToBean(dataClass);
  }

  @Override
  public <R, P> List<R> getBufferedResult(Class<R> dataClass, P param)
  {
    return getIterableResult(dataClass, param).list();
  }
}
