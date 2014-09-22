import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Heading1;
import word.w2004.elements.Paragraph;
import word.w2004.elements.Table;
import word.w2004.elements.tableElements.TableEle;


/*
import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.elements.Image;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.elements.TableV2;
import word.w2004.elements.tableElements.TableCell;
import word.w2004.elements.tableElements.TableRow;*/

public class Test {

	/**
	 * @param args
	 */
	static Logger log = Logger.getLogger(Test.class.getName());
	public static void main(String[] args) {
		//IDocument myDoc = new Document2004();
		///myDoc.getBody().addEle(new Heading1("Heading01"));
		//myDoc.getBody().addEle(new Paragraph("This is a paragraph...")
		
		IDocument mydoc = new Document2004();
		mydoc.getBody().addEle(Paragraph.with("There is a BreakLine under this Paragraph. And of course I am a Paragraph."));

		mydoc.getBody().addEle(new BreakLine());

		mydoc.getBody().addEle(Heading1.with("I am a Heading1"));
		
		/*Image img01 = new Image("http://www.google.com.au/intl/en_com/images/srpr/logo1w.png", ImageType.WEB_URL);
		img01.setWidth("200"); // this will override the default values
		img01.setHeight("30"); // this will override the default values
		mydoc.getBody().addEle(img01);*/

		Table tbl = new Table();
		tbl.addTableEle(TableEle.TH, "Name", "Salary");
		
		mydoc.getBody().addEle(tbl);//add my table to the document

		mydoc.getFooter().showPageNumber(true); //default is true ok...


		
		
		/*IDocument myDoc = new Document2004();
        TableV2 tbl = new TableV2();
        
        tbl.addRow( TableRow.with("Table Header in all Pages", "Usefull for reports").withStyle().repeatTableHeaderOnEveryPage().create() );
        
        tbl.addRow( TableRow.with("Simple String cell", "Another simple String cell") ); 
        tbl.addRow( TableRow.with( TableCell.with(Paragraph.with("TableCell- Style to the whole cell, Par").create()), "Simple String" ).withStyle().bold().create() );
        tbl.addRow( TableRow.with("Style to the whole cell, Str", "String").withStyle().bold().create() );
        tbl.addRow( TableRow.with( TableCell.with(Paragraph.with("TableRowV2 with merge").create()).withStyle().gridSpan(2).create() ).withStyle().bold().create() );
        tbl.addRow( TableRow.with( TableCell.with(Paragraph.withPieces( ParagraphPiece.with("Paragraph with Style inside TableCell").withStyle().bold().fontSize("20").create() ).create()).withStyle().bgColor("00FFFF").create(), "String"  ));
        
        String img = Image.from_WEB_URL("http://www.google.com/images/logos/ps_logo2.png").setHeight("100").setWidth("300").create().getContent();
        tbl.addRow( TableRow.with("this google logo: ", "Image here: "+ img + " == image before") );
        
        for (int i = 0; i < 8000; i++) {
            tbl.addRow( TableRow.with("111 ", "222") );            
        }
        
        tbl.addRow( TableRow.with("LAST", "LAST") );            
                
        //myDoc.addEle(tbl.getContent());
        */
        File file = new File("D:\\a1.doc");
        if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mydoc.getContent());
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        
		/*try {
			throw new Exception();
		} catch (Exception e) {
			System.out.println("Class is : <"+e.getStackTrace()[0].getClassName()+">, Method Name is : <"+e.getStackTrace()[0].getMethodName()+">, Message : "); 
			for (final StackTraceElement ste : e.getStackTrace()) {
		        System.out.println(ste.getClassName()); 
		        System.out.println(ste.getMethodName()); 
		    }
		}*/
		
		
		/*Properties props = new Properties();
		try {
			props.load(new FileInputStream("log4j.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
		LoggerSingleton.getInstance().debug("Hello this is an debug message");
		LoggerSingleton.getInstance().info("Hello this is an info message");
		*/
		/*Fruit[] fruits = new Fruit[4];
		 
		Fruit pineappale = new Fruit("Pineapple", "Pineapple description",70); 
		Fruit apple = new Fruit("Apple", "Apple description",100); 
		Fruit orange = new Fruit("Orange", "Orange description",80); 
		Fruit banana = new Fruit("Banana", "Banana description",90); 
 
		fruits[0]=pineappale;
		fruits[1]=apple;
		fruits[2]=orange;
		fruits[3]=banana;

		Arrays.sort(fruits, new Fruit());
 
		int i=0;
		for(Fruit temp: fruits){
		   System.out.println("fruits " + ++i + " : " + temp.getFruitName() + 
			", Quantity : " + temp.getQuantity());
		}
		
		Arrays.sort(fruits, Fruit.FruitNameComparator);
 
		for(Fruit temp: fruits){
		   System.out.println("fruits " + ++i + " : " + temp.getFruitName() + 
			", Quantity : " + temp.getQuantity());
		}*/
		
		/*try {
			Date d1 = ApplicationUtil.dateFormat1.parse("2014-07-23");
			Date d2 = ApplicationUtil.dateFormat1.parse("2014-08-30");
			int x1 = (int) d1.getTime();
			int x2 = (int) d2.getTime();
			int x3 = x2 - x1;
			int x4 = x3 / (1000*60*60*24);
			int x5 = (int)(d2.getTime() - d1.getTime());
			int x6 = x5 / (1000*60*60*24);
			Calendar c1 = Calendar.getInstance(); c1.setTime(d1);
			Calendar c2 = Calendar.getInstance(); c1.setTime(d2);
			long x7 = (int)c1.getTimeInMillis() - c2.getTimeInMillis();
			long x8 = x7 / (1000*60*60*24);
			
			System.out.println(x6);
			System.out.println(x8);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		//1432840192
		/*
		int s1 = 0;
		try {
			s1 = new java.io.File("D://AdminWeb//build//classes123").list().length;
		} catch(NullPointerException e) {}
		System.out.print(s1);
		System.exit(0);
		
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.print("+0909987878".replaceFirst("^[0|+]*", ""));*/
		/*SimpleDateFormat sdfgmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdfgmt.setTimeZone(TimeZone.getTimeZone("GMT"));

	    SimpleDateFormat sdfmad = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdfmad.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

	    String inpt = "2011-23-03 16:40:44";
	    Date inptdate = null;
	    try {
	        inptdate = sdfgmt.parse(inpt);
	    } catch (ParseException e) {e.printStackTrace();}

	    System.out.println("GMT:\t\t" + sdfgmt.format(inptdate));
	    System.out.println("Europe/Madrid:\t" + sdfmad.format(inptdate));*/
		
		
		
			/*
			 String [] testArray = {"sms.register", "ride.option.sms", "ride.option.approved", 
				"ride.option.rejected", "sms.cancel.driver.admin", "sms.cancel.passenger.admin", 
				"sms.rideSeeker.unmatched.cancel", "sms.cancel", "sms.cancel.driver", "sms.forgot.password", 
				"sms.match", "sms.match.driver", "ride.option.msgBoard"};
			 for(String key:testArray) {try {
				SmsService.sendSms("8884114220", Messages.getValue(key, new Object[]{"1", "2", "3", "4", "5", "6", "7", "8"}));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); }
			}*/
		
		//System.out.println(ApplicationUtil.currentTimeStamp);
		
		/*String str1 = "25-Jun-2014 08:00 PM";
		System.out.println(str1);
		SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
		DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern9);
		Date date;
		try {
			System.out.println(dateFormat.format(new Date()));
			date = dateFormat.parse(str1);
			System.out.println(date);
			System.out.println(formatter.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*
		String str[] = "[ONCE, Two]".replace("[", "").replace("]", "").split(",");
		for(int i=0;i<str.length;i++) str[i] = str[i].trim();
		List s = Arrays.asList(str);
		
		try{
			Date date = ApplicationUtil.dateFormat3.parse("2014-06-11 00:00:00");
			
			Date fromNight = ApplicationUtil.dateFormat16.parse("20:00:00");
			Date toNight = ApplicationUtil.dateFormat16.parse("06:00:00");
			
			date = ApplicationUtil.dateFormat16.parse(ApplicationUtil.dateFormat16.format(date));
			Date fromLimit = ApplicationUtil.dateFormat16.parse("23:59");
			Date toLimit = ApplicationUtil.dateFormat16.parse("00:00");
			
			//System.out.println(date.after(fromNight) && date.before(fromLimit));
			//System.out.println(date.before(toNight) && date.after(toLimit));
		} catch(ParseException e) {}
		System.out.print(compareDates("20:00:00", "06:00:00", "15:00:00"));*/
		
	}

	public static boolean compareDates(String string1, String string2, String someRandomTime) {
		String[] arr1 = string1.split(":");
		String[] arr2 = string2.split(":");
		String[] arr3 = someRandomTime.split(":");

		int a = Integer.parseInt(arr1[0]) * 60 + Integer.parseInt(arr1[1]);
		int b = Integer.parseInt(arr2[0]) * 60 + Integer.parseInt(arr2[1]); 
		int c = Integer.parseInt(arr3[0]) * 60 + Integer.parseInt(arr3[1]);
		if(c > b && c < a) {
			return false;
		} else {
			return true;
		}
		
	}
	
}
