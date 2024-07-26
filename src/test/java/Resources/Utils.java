package Resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import groovyjarjarantlr4.v4.codegen.model.OutputFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;

	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream file = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalVariable("baseUrl")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(file))
					.addFilter(ResponseLoggingFilter.logResponseTo(file)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;
	}

	public String getGlobalVariable(String Key) throws IOException {
		Properties prop = new Properties();
		FileInputStream Fis = new FileInputStream(
				"C:\\Users\\jai.karmani\\eclipse-workspace\\APIFramework\\src\\test\\java\\Resources\\global.properties");

		prop.load(Fis);
		return prop.getProperty(Key);
	}
	
	public String getJsonPath(Response response, String Key) {
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		return js.get(Key).toString();
	}
	
}
