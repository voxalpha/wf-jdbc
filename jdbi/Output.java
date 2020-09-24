package com.navteq.cf.foundation.jdbi;

public interface Output extends IO, AutoCloseable
{
  void setBatch(int batchSize);
  <P> int execute(P param);
  <P> int[] batch(P param);
  void close();
}
