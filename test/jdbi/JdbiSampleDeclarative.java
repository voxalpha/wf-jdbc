package com.navteq.cf.foundation.workflow.test.jdbi;

import com.navteq.cf.foundation.jdbi.JDBIProcessDeclarative;
import com.navteq.cf.foundation.workflow.InvalidConfigurationException;
import com.navteq.cf.foundation.workflow.test.jdbi.DeclarativeDAO.GOTDAOSource;
import com.navteq.cf.foundation.workflow.test.jdbi.DeclarativeDAO.GOTDAOTarget;
import com.navteq.cf.foundation.workflow.test.jdbi.dao.GOTCastless;
import com.navteq.cf.foundation.workflow.test.jdbi.dao.GOTChar;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JdbiSampleDeclarative extends JDBIProcessDeclarative
{
  @Override
  protected boolean process() throws InvalidConfigurationException
  {
    GOTDAOSource daoSource = registerDAO(GOTDAOSource.class);
    GOTDAOTarget daoTarget = registerDAO(GOTDAOTarget.class);

    try (
        Stream<GOTChar> input = daoSource.getChars();
        )
    {
      List<GOTCastless> l = input
        .filter(c -> c.getAddress().getAddressId() == null)
        .map(c -> new GOTCastless(c.getCharId(), c.getPortrait()))
        .collect(Collectors.toList());
      daoTarget.insertCastless(l);
    }

    //set empty portrait for arya
    GOTChar arya = new GOTChar();
    arya.setFirstName("Arya");
    arya.setLastName("Stark");
    arya.setPortrait(new byte[]{});
    int u = daoSource.updateChar(arya);
    logger.info(String.format("%d portraits updated", u));
    return true;
  }
}
