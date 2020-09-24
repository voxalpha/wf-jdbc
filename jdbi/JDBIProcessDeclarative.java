package com.navteq.cf.foundation.jdbi;

import com.navtech.util.xml.XMLTools;
import com.navteq.cf.foundation.process.TaskDefinitionElement;
import com.navteq.cf.foundation.workflow.InvalidConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

public class JDBIProcessDeclarative extends JDBIProcess
{
  private Map<String, Transport> daos = new HashMap<>();

  protected <T> T registerDAO(Class<T> daoClass)
  {
    Transport t = daos.get(daoClass.getName());
    if (t != null)
    {
      //t.handle.
      return t.handle.attach(daoClass);
    }
    else
    {
      //throw
      return null;
    }
  }

  @Override
  public void initialize(TaskDefinitionElement taskDefinitionElement) throws InvalidConfigurationException
  {
    Element processConfigElem = taskDefinitionElement.getTaskElement();
    Element transportRefElem = XMLTools.getFirstElement(processConfigElem, "TransportRef");
    String defTransport = null;
    if (transportRefElem != null)
    {
      defTransport = transportRefElem.getTextContent();
    }
    XPathFactory xPathfactory = XPathFactory.newInstance();
    XPath xpath = xPathfactory.newXPath();
    try
    {
      XPathExpression expr = xpath.compile("DataAccess");
      NodeList nl = (NodeList) expr.evaluate(processConfigElem, XPathConstants.NODESET);
      for (int i = 0; i < nl.getLength(); i++)
      {
        Node node = nl.item(i);
        String daoClass = node.getAttributes().getNamedItem("Class").getTextContent();
        if (daoClass == null)
        {
          throw new InvalidConfigurationException("DAO class is not defined");
        }

        String transport = node.getAttributes().getNamedItem("TransportRef").getTextContent();
        if (transport == null)
        {
          transport = defTransport;
        }
        if (transport == null)
        {
          throw new InvalidConfigurationException("Transport is not defined");
        }
        Transport t = new Transport();
        t.database = getConfiguration().getDatabase(transport);
        transports.put(transport, t);
        daos.put(daoClass, t);
      }
    }
    catch (XPathExpressionException e)
    {
      throw new InvalidConfigurationException(e);
    }
  }
}
