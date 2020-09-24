package com.navteq.cf.foundation.workflow.test.jdbi;

import com.navteq.cf.foundation.process.ArgParser;
import com.navteq.cf.foundation.process.TransformationManager;
import com.navteq.cf.foundation.process.TransformationResult;
import com.navteq.cf.foundation.workflow.InvalidConfigurationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Dmytro Alfymenkov
 * tests XSD validation
 */

public class JdbiTest
{

  @Test
  public void testFluent()
  {
    String[] args = {
        "-w",
        "test/etc/xml/workflow/jdbi/jdbi.xml",
        "-c",
        "test/etc/xml/workflow/GlobalTestConfig.xml",
    };

    ArgParser parser = new ArgParser(TransformationManager.class.getName());
    if (parser.parse(args))
    {
      try
      {
        TransformationManager wfController = new TransformationManager(parser.getWorkflowDoc(), parser
            .getConfigFileName());
        TransformationResult result = new TransformationResult(wfController);

        assertEquals(1, result.runDefaultTransformations());
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }
    else
    {
      fail("Couldn't parse arguments");
    }
  }

  @Test
  public void testDeclarative()
  {
    String[] args = {
        "-w",
        "test/etc/xml/workflow/jdbi/jdbi_declarative.xml",
        "-c",
        "test/etc/xml/workflow/GlobalTestConfig.xml",
    };

    ArgParser parser = new ArgParser(TransformationManager.class.getName());
    if (parser.parse(args))
    {
      try
      {
        TransformationManager wfController = new TransformationManager(parser.getWorkflowDoc(), parser
            .getConfigFileName());
        TransformationResult result = new TransformationResult(wfController);

        assertEquals(1, result.runDefaultTransformations());
      }
      catch (Exception e)
      {
        fail(e.getMessage());
      }
    }
    else
    {
      fail("Couldn't parse arguments");
    }
  }



}
