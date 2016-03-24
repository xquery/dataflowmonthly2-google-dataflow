package com.webcomposite.dataflow.examples;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.io.TextIO;
import com.google.cloud.dataflow.sdk.options.*;
import com.google.cloud.dataflow.sdk.util.gcsfs.GcsPath;

/**
 * Created by jfuller on 3/24/16.
 */
public class myDataFlow {

    public static interface myDataFlowOptions extends PipelineOptions {
        @Description("Path of the file to read from")
        @Default.String("gs://dataflow-samples/shakespeare/kinglear.txt")
        String getInputFile();
        void setInputFile(String value);

        @Description("Path of the file to write to")
        @Default.InstanceFactory(OutputFactory.class)
        String getOutput();
        void setOutput(String value);

        public static class OutputFactory implements DefaultValueFactory<String> {
            @Override
            public String create(PipelineOptions options) {
                DataflowPipelineOptions dataflowOptions = options.as(DataflowPipelineOptions.class);
                if (dataflowOptions.getStagingLocation() != null) {
                    return GcsPath.fromUri(dataflowOptions.getStagingLocation())
                            .resolve("output1.txt").toString();
                } else {
                    throw new IllegalArgumentException("Must specify --output or --stagingLocation");
                }
            }
        }

    }

    public static void main(String[] args) {
        myDataFlowOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
                .as(myDataFlowOptions.class);
        Pipeline p = Pipeline.create(options);

        p.apply(TextIO.Read.named("ReadLines").from(options.getInputFile()))
                
                .apply(TextIO.Write.named("writeOutput").to(options.getOutput()));
        p.run();
    }
}
