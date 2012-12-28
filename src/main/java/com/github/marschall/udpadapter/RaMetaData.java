package com.github.marschall.udpadapter;

import javax.resource.cci.ResourceAdapterMetaData;

public class RaMetaData implements ResourceAdapterMetaData {

  @Override
  public String getAdapterVersion() {
    return "0.1.0";
  }

  @Override
  public String getAdapterVendorName() {
    // TODO Auto-generated method stub
    return "";
  }

  @Override
  public String getAdapterName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getAdapterShortDescription() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getSpecVersion() {
    // TODO Auto-generated method stub
    return null;
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
