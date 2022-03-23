package com.ace.service;

import com.ace.model.Items;
import com.ace.model.Skeleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


public class GenFile {
    private static final Logger log = LogManager.getLogger(Start.class);

    /**
     * This core will be controller to run the whole programmer
     * @param url
     * @throws Exception
     */
    protected void genTable( URL url) throws Exception
    {
        Optional<Skeleton> obj = convertJsonToPojo(url);

        obj.ifPresent(
               value-> {
                   try {
                       printTable(obj);
                   } catch (URISyntaxException e) {
                      log.error(e);
                   } catch (IOException e) {
                       log.error(e);
                   }
               }
        );
        obj.orElseThrow(()-> new Exception("Json not readable"));
    }

    /**
     * Based on the
     * @param obj
     */
    private void printTable(Optional<Skeleton> obj) throws URISyntaxException, IOException {

        Items listItem = obj.get().getItems();
        List<String> output = new ArrayList<String>();
        listItem.getItem().forEach( item ->
                {
                    item.getBatters().getBatter().forEach(  batter -> {
                        if(item.getTopping()!=null && item.getTopping().size()>0){
                        item.getTopping().forEach(topping -> {
                            output.add(String.format("%-10s%-20s%-20s%-20s%-10s",item.getId(), item.getType(), item.getName(),
                                    batter.getType(), topping.getType() ));
                        });

                        }
                        else
                        {

                                output.add(String.format("%-10s%-20s%-20s%-20s%-10s",item.getId(), item.getType(), item.getName(),
                                        batter.getType(), ""));

                        }

                    });

                }

        );

        String header=String.format("%-10s%-20s%-20s%-20s%-10s\n", "Id", "Type","Name", "Batter","Topping");

        output.forEach(
                out -> {
                    System.out.println(out);
                }
        );

        writeToFile(header,output);

    }

    private void writeToFile(String header, List<String>output) throws URISyntaxException, IOException {
        URL resource = ClassLoader.getSystemResource("output.txt");
        File file = Paths.get(resource.toURI()).toFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(header);

        try(writer)
        {
            for(String temp : output)
            {
                writer.write(temp);
                writer.newLine();
            }
        }catch(Exception ex)
        {
            log.error(ex);
        }



}
    private Optional<Skeleton> convertJsonToPojo(URL url) {
        ObjectMapper mapper = new ObjectMapper();
        Skeleton tempClass = null;
        try{
            tempClass = mapper.readValue(url, Skeleton.class);
        }
        catch (Exception ex)
        {
            log.error(Arrays.stream(ex.getStackTrace()).iterator().toString());
        }
        return Optional.ofNullable(tempClass);
    }

}
