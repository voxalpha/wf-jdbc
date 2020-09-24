package com.navteq.cf.foundation.workflow.test.jdbi;

import com.navteq.cf.foundation.jdbi.JDBIProcess;
import com.navteq.cf.foundation.jdbi.JDBIProcessFluent;
import com.navteq.cf.foundation.jdbi.Output;
import com.navteq.cf.foundation.workflow.InvalidConfigurationException;
import com.navteq.cf.foundation.workflow.test.jdbi.dao.GOTCastless;
import com.navteq.cf.foundation.workflow.test.jdbi.dao.GOTChar;
import java.util.stream.Stream;

public class JdbiSample extends JDBIProcessFluent
{
  @Override
  protected boolean process() throws InvalidConfigurationException
  {
    //find castleless characters and write them to the separate table
    try (
        Stream<GOTChar> input = getInput("ReadChars").getIterableResult(GOTChar.class, null).stream();
        Output output = getOutput("WriteChars");
        )
    {
      input
        .filter(c -> c.getAddress().getAddressId() == null)
        .forEach(c -> output.batch(new GOTCastless(c.getCharId(), c.getPortrait())));
    }

    //set empty portrait for arya
    try (Output output = getOutput("UpdatePortrait"))
    {
      GOTChar arya = new GOTChar();
      arya.setFirstName("Arya");
      arya.setLastName("Stark");
      arya.setPortrait(new byte[]{});
      int u = output.execute(arya);
      logger.info(String.format("%d portraits updated", u));
    }
    return true;
  }
}
