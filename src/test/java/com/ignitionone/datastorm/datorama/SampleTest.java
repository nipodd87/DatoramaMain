package com.ignitionone.datastorm.datorama;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.ignitionone.datastorm.datorama.datoramaUtil.SampleBean;
import com.ignitionone.datastorm.datorama.util.CSVandTextReader;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by nitin.poddar on 2/1/2017.
 */
public class SampleTest {

    @Test
    public void mainTest() throws IOException {

        ColumnPositionMappingStrategy<SampleBean> strat = new ColumnPositionMappingStrategy<SampleBean>();
        strat.setType(SampleBean.class);
        String[] columns = new String[] { "name", "address", "salary", "phone" };
        strat.setColumnMapping(columns);
        CsvToBean<SampleBean> csv = new CsvToBean<SampleBean>();

        List<SampleBean> list = csv.parse(strat, new CSVReader(new FileReader(System.getProperty("user.dir")+"/"+"SampleCSV.csv"), ','));
        for (int i=1;i<list.size();i++){
            System.out.println("Name: "+list.get(i).getName()+" Address: "+list.get(i).getAddress()+" Salary: "+list.get(i).getSalary()+" Phone: "+list.get(i).getPhone());
            double salary = Double.parseDouble(list.get(i).getSalary());
            System.out.println(salary);
        }
        /*
        CSVandTextReader csvReader = new CSVandTextReader();
        List<String> creativeConversionDestList = csvReader.getCSVData(System.getProperty("user.dir")+"/"+"SampleCsv.csv");
        System.out.println("Doe");
        System.out.println("Doe");
        System.out.println("Doe");
        */
    }
}
