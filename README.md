# dataflowmonthly2-google-dataflow

extracted from https://github.com/GoogleCloudPlatform/DataflowJavaSDK-examples.git

run WordCount locally
mvn compile exec:java -Dexec.mainClass=com.webcomposite.dataflow.examples.WordCount -Dexec.args="--project=dataflowmonthlyhangout2 --output=/tmp/test.out"

run WordCount 
mvn compile exec:java -Dexec.mainClass=com.webcomposite.dataflow.examples.WordCount -Dexec.args="--runner=BlockingDataflowPipelineRunner --project=dataflowmonthlyhangout2 --stagingLocation=gs://dataflow-bucket1/staging "
