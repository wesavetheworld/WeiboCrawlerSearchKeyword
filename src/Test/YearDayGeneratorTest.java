package Test;

public class YearDayGeneratorTest {

	/**
	 * @param args
	 */
	public static String[] month={"01","02","03","04","05","06","07","08","09","10","11","12"};
	public static String[] day={"01","02","03","04","05","06","07","08","09","10",
		"11","12","13","14","15","16","17","18","19","20",
		"21","22","23","24","25","26","27","28","29","30","31"};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<=11;i++){
			for(int j=0;j<=30;j++){
				System.out.print("\""+"2015"+month[i]+day[j]+"\""+",");
			}
			System.out.println();
		}
	}

}
