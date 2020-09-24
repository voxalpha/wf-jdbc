package com.navteq.cf.foundation.workflow.test.jdbi.dao;

import org.jdbi.v3.core.mapper.Nested;

import java.sql.Blob;

public class GOTChar
{
  private Integer char_id;
  private String firstName;
  private String lastName;
  private GOTAddress address;
  private byte[] portrait;

  public Integer getCharId()
  {
    return char_id;
  }

  public void setCharId(Integer char_id)
  {
    this.char_id = char_id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public GOTAddress getAddress()
  {
    return address;
  }

  @Nested
  public void setAddress(GOTAddress address)
  {
    this.address = address;
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
