package com.navteq.cf.foundation.jdbi;

import org.jdbi.v3.core.result.ResultBearing;
import org.jdbi.v3.core.result.ResultIterable;

import java.util.List;

public interface Input extends IO
{
  <P> ResultBearing query(P param);
  <R,P> ResultIterable<R> getIterableResult(Class<R> dataClass, P param);
  <R,P> List<R> getBufferedResult(Class<R> dataClass, P param);
}
