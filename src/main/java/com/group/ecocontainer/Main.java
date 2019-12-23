package com.group.ecocontainer;

import com.group.ecocontainer.dataReader.CsvReader;

public class Main {
  public static void main(String[] args) {
    CsvReader csv = new CsvReader();
    csv.readCsvToArray();
  }
}
