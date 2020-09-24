package com.navteq.cf.foundation.jdbi;

import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Update;

public class JdbiOutput extends JdbiIO implements Output
{
  private int batchSize = 1000;
  private int batchCount = 0;
  private PreparedBatch batch = null;
  private Update update = null;

  @Override
  public void setBatch(int batchSize)
  {
    this.batchSize = batchSize;
  }

  @Override
  public <P> int execute(P param)
  {
    if (update == null)
    {
      update = handle.createUpdate(sql);
    }
    return update.bindBean(param).execute();
  }

  @Override
  public <P> int[] batch(P param)
  {
    if (batch == null)
    {
      batch = handle.prepareBatch(sql);
    }
    batch.bindBean(param).add();
    batchCount++;
    if (batchCount >= batchSize)
    {
      return batch.execute();
    } else
    {
      return new int[0];
    }
  }

  @Override
  public void close()
  {
    if (batch != null)
    {
      batch.execute();
      batch.close();
    }
    if (update != null)
    {
      update.close();
    }
  }
}
