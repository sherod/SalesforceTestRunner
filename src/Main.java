import com.sforce.soap.apex.*;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;


/**
 * Created by IntelliJ IDEA.
 * User: sherod
 * Date: 13/08/12
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    
    public static final String USERNAME = "";
    public static final String PASSWORD = "";
    public static final String SERVER = "https://cs5-api.salesforce.com";

    public static void main(String[] args) throws ConnectionException {

        ConnectorConfig config = new ConnectorConfig();

        //Set the endpoint for the login method (Enterprise WSDL)
        //This also sets, under the hood, the 'ServiceEndPoint'
        config.setAuthEndpoint(SERVER + "/services/Soap/c/25.0");

        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        //Does a login and gets you a session id into the config instance.
        EnterpriseConnection p = new EnterpriseConnection(config);


        //Now switch the service endpoint to the Apex service
        config.setServiceEndpoint(SERVER + "/services/Soap/s/25.0");

        //Build another connection with the same config
        //which importantly contains a session ID.
        SoapConnection connector = new SoapConnection(config);

        RunTestsRequest request = new RunTestsRequest();

        //Pull in the list of classes from the command line.
        request.setClasses(args);
        
        RunTestsResult r = connector.runTests(request);

        //And dump the basic info out to stdout....
        for(RunTestFailure t : r.getFailures()){
            System.out.println(t.getClass() + "." + t.getMethodName());
            //System.out.println(t.getType());
            System.out.println(t.getMessage());
            System.out.println(t.getStackTrace());
            System.out.println("*****************");
        }

    }
}
