package camelcoredemo;


import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSet;
import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDemoCamel extends Main{

	static Logger LOG = LoggerFactory.getLogger(FileDemoCamel.class);
    public static void main(String[] args) throws Exception {
       
    	FileDemoCamel main = new FileDemoCamel();
        main.enableHangupSupport();
        main.addRouteBuilder(createRouteBuilder());
        main.bind("sampleGenerator", createDataSet());
        main.run(args);
    }
    static RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
        	
            public void configure() {
            	
            	from("file:///home/test/tmp/?delay=1000")
                .process(new Processor() {
                    public void process(Exchange msg) {
                        File file = msg.getIn().getBody(File.class);
                        System.out.println(file.getName());
                        LOG.info("Processing file: " + file);
                    }
                });
            	
              /*  from("dataset://sampleGenerator")
                .to("file://target/output");*/
            }
        };
    }
    static DataSet createDataSet() {
        return new SimpleDataSet();
    }
	
}
