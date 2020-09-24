package com.navteq.cf.foundation.workflow.test.jdbi.dao;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class GOTAddress
{
  private Integer addressId;
  private String name;
  @ColumnName("chamber_number")
  private Integer chamber;

  public Integer getAddressId()
  {
    return addressId;
  }

  public void setAddressId(Integer addressId)
  {
    this.addressId = addressId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }


  public Integer getChamber()
  {
    return chamber;
  }

  public void setChamber(Integer chamber)
  {
    this.chamber = chamber;
  }
}
