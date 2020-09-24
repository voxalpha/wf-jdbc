package com.navteq.cf.foundation.jdbi;

import org.jdbi.v3.core.Handle;

public class JdbiIO implements IO
{
  private String transport;
  protected String sql;
  protected Handle handle;

  public JdbiIO()
  {

  }

  public void initialize(String transport, String sql)
  {
    this.transport = transport;
    this.sql = sql;
  }

  @Override
  public String getTransport()
  {
    return transport;
  }

  @Override
  public void connect(Handle handle)
  {
    this.handle = handle;
  }
}
