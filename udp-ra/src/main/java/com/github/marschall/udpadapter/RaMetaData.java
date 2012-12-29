package com.github.marschall.udpadapter;

import javax.resource.cci.ResourceAdapterMetaData;

public class RaMetaData implements ResourceAdapterMetaData {

  @Override
  public String getAdapterVersion() {
    return "0.1.0";
  }

  @Override
  public String getAdapterVendorName() {
    return "Philippe Marschall";
  }

  @Override
  public String getAdapterName() {
    return "UDP JMS Adapter";
  }

  @Override
  public String getAdapterShortDescription() {
    return "A UDP JMS resource adapter.";
  }

  @Override
  public String getSpecVersion() {
    return "1.6";
  }

  @Override
  public String[] getInteractionSpecsSupported() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean supportsExecuteWithInputAndOutputRecord() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean supportsExecuteWithInputRecordOnly() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean supportsLocalTransactionDemarcation() {
    // TODO Auto-generated method stub
    return false;
  }

}
