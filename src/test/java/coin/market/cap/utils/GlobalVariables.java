package coin.market.cap.utils;

import java.io.File;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GlobalVariables
{
	public static RequestSpecification httpRequest;
	public static Response httpResponse;
	public static String requestPayload;
	public static String guid;
	public static String title;
	public static String inputJsonFile;
    public static String baseProjectPath = File.separator+System.getProperty("user.dir");
    public static String outputFilesPath = File.separator+System.getProperty("user.dir")+ File.separator+"OutputFiles"+File.separator;
    public static String inputFilesPath = baseProjectPath+ File.separator+"src"+ File.separator+"test"+ File.separator+"resources"+ File.separator+ "inputJSONFiles" + File.separator;
    public static String devConfigFilePath = baseProjectPath+ File.separator+"src"+ File.separator+"test"+ File.separator+"java"+ File.separator+"config"+ File.separator+"dev.properties";
    public static String prodConfigFilePath = baseProjectPath+ File.separator+"src"+ File.separator+"test"+ File.separator+"java"+ File.separator+"config"+ File.separator+"prod.properties";
    public static String envConfigFilePath = baseProjectPath+ File.separator+"src"+ File.separator+"test"+ File.separator+"java"+ File.separator+"config"+ File.separator+"Application.properties";
    public static String previewConfigFilePath = baseProjectPath+ File.separator+"src"+ File.separator+"test"+ File.separator+"java"+ File.separator+"config"+ File.separator+"preview.properties";
    public static String jsonSchemaFilePath = baseProjectPath+ File.separator+"src"+ File.separator+"test"+ File.separator+"resources"+ File.separator+ "JSONSchemaFiles"+ File.separator;
    public static String newlyCreatedRequestPayloadFile;
    public static String newlyCreatedResponsePayloadFile;
}
