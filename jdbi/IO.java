package com.navteq.cf.foundation.jdbi;

import org.jdbi.v3.core.Handle;

public interface IO
{
  void initialize(String transport, String sql);
  String getTransport();
  void connect(Handle handle);
}
