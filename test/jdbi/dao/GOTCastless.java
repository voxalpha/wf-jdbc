package com.navteq.cf.foundation.workflow.test.jdbi.dao;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.sql.Blob;

public class GOTCastless
{
  private Integer charId;
  private byte[] portrait;

  public GOTCastless(Integer id, byte[] portrait)
  {
    this.charId = id;
    this.portrait = portrait;
  }

  public Integer getCharId()
  {
    return charId;
  }

  public void setCharId(Integer charId)
  {
    this.charId = charId;
  }

  public byte[] getPortrait()
  {
    return portrait;
  }

  public void setPortrait(byte[] portrait)
  {
    this.portrait = portrait;
  }
}
