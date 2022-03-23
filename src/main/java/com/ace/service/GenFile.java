package com.ace.service;

import com.ace.model.Items;
import com.ace.model.Skeleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
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
               value-> printTable(obj)
        );
        obj.orElseThrow(()-> new Exception("Json not readable"));
    }

    /**
     * Based on the
     * @param obj
     */
    private void printTable(Optional<Skeleton> obj) {

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

        Optional.ofNullable(output).ifPresentOrElse(
                value -> System.out.format("%-10s%-20s%-20s%-20s%-10s\n", "Id", "Type","Name", "Batter","Topping"),
                () -> System.out.println("No record")        );
        output.forEach(
                out -> {
                    System.out.println(out);
                }
        );
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
