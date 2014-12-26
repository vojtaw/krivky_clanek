package cz.tul.cc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Util {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Util.class.getName());
	private static java.util.Properties props = null;// = new java.util.Properties();
	
        private String dbURL = "jdbc:derby:";
        private String dbPath = "z:\\job_bagr\\mc\\db\\mc\\";
    
        
        
	public Util() {
//		if (props == null){
//			setProperties();
//			setVariables();
//		}
            
//            try {
//            org.apache.log4j.PropertyConfigurator.configure(AppConstant.appPath + lcfFile);
//        } catch (Exception e) {
//            cat.error("Could not configure log4j:" + e.getMessage());
//        }
	}
	
        public static String getText(String key){
            return key;
        }
	
	/**********************************************************************/
	/** bezier constants **************************************************/
	/**********************************************************************/
	/**@description number of space dimension 2 for 2d space, 3 for 3D space*/
	public static int dimension = 2;
        /**@description number of steps for computation, eg. for length */
	public static int numberOfStepsForBernsteinLinear = 20;
	
	/**@desc its used for comparing double values
	 * abs(double1-double2)>DOUBLE_PRECISION --> false */
	public static  double doublePrecision = 1E-32;

	
	public void setProperties(){
		props = new java.util.Properties();
		
		try{
			InputStream is = new FileInputStream("external\\cfg\\pegas.ini");
			props.load(is);
			is.close();
		}catch(FileNotFoundException e){
			System.err.println("Could not load properties:" + e.getMessage());
		}catch(IOException e){
			System.err.println("Could not load properties:" + e.getMessage());
		}
		try {
			org.apache.log4j.PropertyConfigurator.configure(props.getProperty("lcfFile")); //"C:/eclipse/workspace/Pegas_model/ext_lib/log4j.properties"
			System.out.println(props.getProperty("lcfFile"));
		} catch (Exception e) {
			System.err.println("Could not configure log4j:" + e.getMessage());
		} 
	}

	public void setVariables(){
//		filePath = props.getProperty("filePath");
//		filamentsFileName = props.getProperty("filamentsFileName");
//		gravuresFileName = props.getProperty("gravuresFileName");
//		pointDelimiter = props.getProperty("pointDelimiter");
//		segmentDelimiter = props.getProperty("segmentDelimiter");
//		
//		filePathSSCurve = props.getProperty("filePathSSCurve");
//		ssCurvesItemDelimiter = props.getProperty("ssCurvesItemDelimiter");
//		ssCurveFileName = props.getProperty("ssCurvesFileName");
//		ssCurvesFileSuffix = props.getProperty("ssCurvesFileSuffix");
//		ssCurvesCurveName = props.getProperty("ssCurvesCurveName");
//		ssCurvesTiterName = props.getProperty("ssCurvesTiterName");
//		try{
//			numberOfFiles = Integer.parseInt(props.getProperty("numberOfFiles"));
//		}catch(NumberFormatException e){
//			numberOfFiles = 0;
//		}catch(NullPointerException e){
//			numberOfFiles = 0;
//		}catch(Exception e){
//			numberOfFiles = 0;
//		}
//		try{
//			referenceLength = Double.parseDouble(props.getProperty("referenceLength"));
//		}catch(NumberFormatException e){
//			referenceLength = 0.0;
//			logger.info(e.getMessage());
//		}catch(NullPointerException e){
//			logger.info(e.getMessage());
//			referenceLength = 0.0;
//		}catch(Exception e){
//			logger.info(e.getMessage());
//			referenceLength = 0.0;
//		}
//		logger.info("dimension:"+props.getProperty("dimension"));
//		try{
//			dimension = Integer.parseInt(props.getProperty("dimension"));
//		}catch(NumberFormatException e){
//			logger.info(e.getMessage());
//			dimension = 0;
//		}catch(NullPointerException e){
//			logger.info(e.getMessage());
//			dimension = 0;
//		}catch(Exception e){
//			logger.info(e.getMessage());
//			dimension = 0;
//		}
//		logger.info("dimension:"+dimension);
//		try{
//			numberOfSteps = Integer.parseInt(props.getProperty("numberOfSteps"));
//		}catch(NumberFormatException e){
//			numberOfSteps = 0;
//		}catch(NullPointerException e){
//			numberOfSteps = 0;
//		}catch(Exception e){
//			numberOfSteps = 0;
//		}
//		try{
//			doublePrecision = Double.parseDouble(props.getProperty("doublePrecision"));
//		}catch(NumberFormatException e){
//			doublePrecision = 1E-32;
//		}catch(NullPointerException e){
//			doublePrecision = 1E-32;
//		}catch(Exception e){
//			doublePrecision = 1E-32;
//		}
//		
//		ssCurvesTiterName = props.getProperty("ssCurvesTiterName");
//		ssCurvesDistribution = parseSSCurveDistribution(props.getProperty("ssCurvesDistribution"));
	}
	
	
	/***********************************************************/
	/** public getters and setters *****************************/
	public java.util.Properties getProps() {
		return props;
	}

}
